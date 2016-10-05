package terrainsSphere;

//import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.Iterator;
import java.util.List;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Random;

//import javax.imageio.ImageIO;

//import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import models.RawModel;
import objConverter.Vertex;
import renderEngine.Loader;
import toolbox.Maths;
//import terrains.HeightGenerator;
//import textures.ModelTexture;
//import textures.TerrainTexture;
//import textures.TerrainTexturePack;
//import toolbox.Maths;

public class TerrainSphere {

	// private Loader loader;
	private RawModel model;
	private float x;
	private float y;
	private float z;
	private float scale;

	private HeightMapController heightMapController;

	private int faceLoops;

	private int[] indicesFinal;
	private float[] verticesUniform;
	private float[] polarFinal;
	float[] colourFinal;

	float[] verticesFinal;

	private int index;
	// private List<Vector3f> verticesList;
	private List<Vector4f> verticesListWithHeight; // last element being the
													// radius for faster access
	private List<TerrainVertex> terrainVerticesList = new ArrayList<TerrainVertex>();
	private HashMap<Long, Integer> middlePointIndexCache;

	// private HeightGeneratorSphere heightGeneratorSphere;
	// private Random random = new Random();

	private List<TerrainFace> terrainFacesList = new ArrayList<TerrainFace>();
	private List<TerrainFace> initTerrainFaces = new ArrayList<TerrainFace>();
	private List<TerrainFace> finalTerrainFaces = new ArrayList<TerrainFace>();
	// private List<TerrainFace> terrainFacesListFinal = new
	// ArrayList<TerrainFace>();

	public TerrainSphere(Loader loader, int loops, float scale) {
		// this.loader = loader;
		this.scale = scale;
		index = 0;
		this.faceLoops = loops;
		// verticesList = new ArrayList<Vector3f>();
		middlePointIndexCache = new HashMap<Long, Integer>();

		heightMapController = new HeightMapController();
		heightMapController.fillTerrainHeightMapAndTypes();
		// heightMapController.fillTerrainHeightMapWithEarth();

		this.model = generateTerrainSphereIco(loader);
	}

	private RawModel generateTerrainSphereIco(Loader loader) {
		float t = (float) ((1.0 + Math.sqrt(5.0)) / 2.0);
		// float[] vertices = new float[12 * 3];
		// float[] indices = new float[20 * 3];
		float[] verticesInit = { -1, t, 0, 1, t, 0, -1, -t, 0, 1, -t, 0, 0, -1, t, 0, 1, t, 0, -1, -t, 0, 1, -t, t, 0,
				-1, t, 0, 1, -t, 0, -1, -t, 0, 1 };

		int[] indicesInit = { 0, 11, 5, 0, 5, 1, 0, 1, 7, 0, 7, 10, 0, 10, 11, 1, 5, 9, 5, 11, 4, 11, 10, 2, 10, 7, 6,
				7, 1, 8, 3, 9, 4, 3, 4, 2, 3, 2, 6, 3, 6, 8, 3, 8, 9, 4, 9, 5, 2, 4, 11, 6, 2, 10, 8, 6, 7, 9, 8, 1 };

		for (int i = 0; i < 12; i++) {
			addVertex(new Vector3f(verticesInit[i * 3], verticesInit[i * 3 + 1], verticesInit[i * 3 + 2]));
		}

		// List<Vector3f> faces = new ArrayList<Vector3f>();
		for (int i = 0; i < 20; i++) {
			// faces.add(new Vector3f(indicesInit[i * 3], indicesInit[i * 3 +
			// 1], indicesInit[i * 3 + 2]));
			TerrainFace initFace = new TerrainFace(
					new Vector3f(indicesInit[i * 3], indicesInit[i * 3 + 1], indicesInit[i * 3 + 2]));
			updateNormal(initFace);
			updateLines(initFace);
			terrainFacesList.add(initFace);
			initTerrainFaces.add(initFace);
		}

		// int facesCount = 0;
		// List<Vector3f> finalFaces = new ArrayList<Vector3f>();

		for (int i = 0; i < faceLoops; i++) {

			
			int listSize = terrainFacesList.size();

			// for (TerrainFace face : terrainFacesList){
			for (int j = 0; j < listSize; j++) {
				TerrainFace face = terrainFacesList.get(j);
				if (face.getLevel() == i) {
					int a = getMiddlePoint((int) face.getIndices().x, (int) face.getIndices().y);
					int b = getMiddlePoint((int) face.getIndices().y, (int) face.getIndices().z);
					int c = getMiddlePoint((int) face.getIndices().z, (int) face.getIndices().x);

					boolean isFinal = false;
					if (i == faceLoops - 1) {
						isFinal = true;
						// facesCount += 4;

						// connect the neighbors
						TerrainVertex vertexX = terrainVerticesList.get((int) face.getIndices().x);
						TerrainVertex vertexY = terrainVerticesList.get((int) face.getIndices().y);
						TerrainVertex vertexZ = terrainVerticesList.get((int) face.getIndices().z);
						TerrainVertex vertexA = terrainVerticesList.get(a);
						TerrainVertex vertexB = terrainVerticesList.get(b);
						TerrainVertex vertexC = terrainVerticesList.get(c);

						vertexX.addNeighbor(vertexA);
						vertexA.addNeighbor(vertexX);
						vertexX.addNeighbor(vertexC);
						vertexC.addNeighbor(vertexX);

						vertexY.addNeighbor(vertexA);
						vertexA.addNeighbor(vertexY);
						vertexY.addNeighbor(vertexB);
						vertexB.addNeighbor(vertexY);

						vertexZ.addNeighbor(vertexB);
						vertexB.addNeighbor(vertexZ);
						vertexZ.addNeighbor(vertexC);
						vertexC.addNeighbor(vertexZ);

					}

					TerrainFace triangle1 = new TerrainFace(new Vector3f(face.getIndices().x, a, c), face, isFinal);
					TerrainFace triangle2 = new TerrainFace(new Vector3f(face.getIndices().y, b, a), face, isFinal);
					TerrainFace triangle3 = new TerrainFace(new Vector3f(face.getIndices().z, c, b), face, isFinal);
					TerrainFace triangle4 = new TerrainFace(new Vector3f(a, b, c), face, isFinal);
					updateNormal(triangle1);
					updateNormal(triangle2);
					updateNormal(triangle3);
					updateNormal(triangle4);
					updateLines(triangle1);
					updateLines(triangle2);
					updateLines(triangle3);
					updateLines(triangle4);
					if (isFinal) {
						finalTerrainFaces.add(triangle1);
						finalTerrainFaces.add(triangle2);
						finalTerrainFaces.add(triangle3);
						finalTerrainFaces.add(triangle4);
						terrainFacesList.add(triangle1);
						terrainFacesList.add(triangle2);
						terrainFacesList.add(triangle3);
						terrainFacesList.add(triangle4);
					} else {
						terrainFacesList.add(triangle1);
						terrainFacesList.add(triangle2);
						terrainFacesList.add(triangle3);
						terrainFacesList.add(triangle4);
					}

				}
			}
		}

		int verticesCount = terrainVerticesList.size();
		int facesCount = finalTerrainFaces.size();

		for (int i = 0; i < facesCount; i++) {
			TerrainFace currentFace = finalTerrainFaces.get(i);
			Vector3f currentIndices = currentFace.getIndices();
			TerrainVertex vertex1 = terrainVerticesList.get((int) currentIndices.x);
			TerrainVertex vertex2 = terrainVerticesList.get((int) currentIndices.y);
			TerrainVertex vertex3 = terrainVerticesList.get((int) currentIndices.z);

			currentFace.addVertex(vertex1);
			currentFace.addVertex(vertex2);
			currentFace.addVertex(vertex3);

			// currentFace.updateLines();

			vertex1.addNeighborFaces(currentFace);
			vertex2.addNeighborFaces(currentFace);
			vertex3.addNeighborFaces(currentFace);

		}

		colourFinal = new float[verticesCount * 3];
		verticesUniform = new float[verticesCount * 3];
		verticesFinal = new float[verticesCount * 3];
		polarFinal = new float[verticesCount * 3];
		verticesListWithHeight = new ArrayList<Vector4f>();

		indicesFinal = new int[facesCount * 3];
		for (int i = 0; i < facesCount; i++) {
			indicesFinal[i * 3] = (int) finalTerrainFaces.get(i).getIndices().x;
			indicesFinal[i * 3 + 1] = (int) finalTerrainFaces.get(i).getIndices().y;
			indicesFinal[i * 3 + 2] = (int) finalTerrainFaces.get(i).getIndices().z;
		}

		Vector3f[] verticesPolerCoords = new Vector3f[verticesCount];
		// x is the length r
		// y is theta1, the projection to xz plane
		// z is theta2, the projection to x axis
		// needs to do extra check since arc sin gives -pi/2 to pi/2 result
		for (int i = 0; i < verticesCount; i++) {
			verticesPolerCoords[i] = new Vector3f(0, 0, 0);
			float xVal = terrainVerticesList.get(i).getPosition().x;
			float yVal = terrainVerticesList.get(i).getPosition().y;
			float zVal = terrainVerticesList.get(i).getPosition().z;
			verticesUniform[i * 3] = xVal;
			verticesUniform[i * 3 + 1] = yVal;
			verticesUniform[i * 3 + 2] = zVal;
			float radius = (float) Math.sqrt(xVal * xVal + yVal * yVal + zVal * zVal);
			float rOnXZ = (float) Math.sqrt(xVal * xVal + zVal * zVal);
			verticesPolerCoords[i].x = radius;
			verticesPolerCoords[i].y = (float) Math.asin(yVal / radius);
			if (rOnXZ == 0) {
				verticesPolerCoords[i].z = 0;
				if (yVal > 0) {
					verticesPolerCoords[i].y = (float) (Math.PI / 2);
				} else {
					verticesPolerCoords[i].y = (float) (-Math.PI / 2);
				}
			} else {
				verticesPolerCoords[i].z = (float) Math.asin(zVal / rOnXZ);
			}
			if (xVal < 0) {
				verticesPolerCoords[i].z = (float) (Math.PI - verticesPolerCoords[i].z);
			}
			
			polarFinal[i * 3] = verticesPolerCoords[i].x;
			polarFinal[i * 3 + 1] = verticesPolerCoords[i].y;
			polarFinal[i * 3 + 2] = verticesPolerCoords[i].z;
		}

		// convert back to XYZ space
		for (int i = 0; i < verticesCount; i++) {
			float radius = verticesPolerCoords[i].x;
			float theta1 = verticesPolerCoords[i].y;
			float theta2 = verticesPolerCoords[i].z;
	
			float heightToCheck = heightMapController.accessHeightMap(theta1, theta2);

			radius += heightToCheck / HeightMapController.HEIGHT_SCALE;
			float rOnXZ = (float) (radius * Math.cos(theta1));
			verticesFinal[i * 3] = (float) (rOnXZ * Math.cos(theta2));
			polarFinal[i * 3] = radius;
			verticesFinal[i * 3 + 1] = (float) (radius * Math.sin(theta1));
			verticesFinal[i * 3 + 2] = (float) (rOnXZ * Math.sin(theta2));
			verticesListWithHeight.add(
					new Vector4f(verticesFinal[i * 3], verticesFinal[i * 3 + 1], verticesFinal[i * 3 + 2], radius));

			Vector3f colour = heightMapController.accessColourBasedOnHeight(theta1, theta2);
			terrainVerticesList.get(i).setColour(colour);
			
		}

		for (int i = 0; i < verticesCount; i++) {
			filterColourDefault(terrainVerticesList.get(i));
			Vector3f filteredColour = terrainVerticesList.get(i).getFilteredColour();
			
			colourFinal[i * 3] = filteredColour.x;
			colourFinal[i * 3 + 1] = filteredColour.y;
			colourFinal[i * 3 + 2] = filteredColour.z;
		}

		

		return loader.loadToVAOPositionAndColour(verticesFinal, colourFinal, indicesFinal, 3);

		// return loader.loadToVAO(verticesInit, indicesInit, 3);

	}

	private int addVertex(Vector3f p) {
		float length = (float) Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
		
		TerrainVertex vertex = new TerrainVertex(new Vector3f(p.x / length, p.y / length, p.z / length), index);
		terrainVerticesList.add(vertex);
		return index++;
	}

	private void updateLines(TerrainFace face) {
		Vector3f p1 = terrainVerticesList.get((int) face.getIndices().x).getPosition();
		Vector3f p2 = terrainVerticesList.get((int) face.getIndices().y).getPosition();
		Vector3f p3 = terrainVerticesList.get((int) face.getIndices().z).getPosition();

		float[] l1 = new float[6];
		float[] l2 = new float[6];
		float[] l3 = new float[6];

		Maths.generateLines(p1, p2, l1);
		Maths.generateLines(p2, p3, l2);
		Maths.generateLines(p3, p1, l3);

		face.setL1(l1);
		face.setL2(l2);
		face.setL3(l3);
	}

	private void updateNormal(TerrainFace face) {
		// Set Vector U to (Triangle.p2 minus Triangle.p1)
		// Set Vector V to (Triangle.p3 minus Triangle.p1)

		// Set Normal.x to (multiply U.y by V.z) minus (multiply U.z by V.y)
		// Set Normal.y to (multiply U.z by V.x) minus (multiply U.x by V.z)
		// Set Normal.z to (multiply U.x by V.y) minus (multiply U.y by V.x)

		// Vector3f p1 = verticesList.get((int)face.getIndices().x);
		// Vector3f p2 = verticesList.get((int)face.getIndices().y);
		// Vector3f p3 = verticesList.get((int)face.getIndices().z);
		Vector3f p1 = terrainVerticesList.get((int) face.getIndices().x).getPosition();
		Vector3f p2 = terrainVerticesList.get((int) face.getIndices().y).getPosition();
		Vector3f p3 = terrainVerticesList.get((int) face.getIndices().z).getPosition();

		Vector3f u = Vector3f.sub(p2, p1, null);
		Vector3f v = Vector3f.sub(p3, p1, null);

		Vector3f normal = new Vector3f(0, 0, 0);

		normal.x = u.y * v.z - u.z * v.y;
		normal.y = u.z * v.x - u.x * v.z;
		normal.z = u.x * v.y - u.y * v.x;

		float length = (float) Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z);

		normal.x /= length;
		normal.y /= length;
		normal.z /= length;

		face.setNormal(normal);

	}

	private int getMiddlePoint(int p1, int p2) {
		// first check if we have it already

		boolean firstIsSmaller = p1 < p2;
		long smallerIndex = firstIsSmaller ? p1 : p2;
		long greaterIndex = firstIsSmaller ? p2 : p1;
		long key = (smallerIndex << 32) + greaterIndex;

		int ret;
		if (middlePointIndexCache.get(key) != null) {
			ret = middlePointIndexCache.get(key);
			return ret;
		}

		// not in cache, calculate it
		Vector3f point1 = terrainVerticesList.get(p1).getPosition();
		Vector3f point2 = terrainVerticesList.get(p2).getPosition();
		Vector3f middle = new Vector3f((point1.x + point2.x) / 2.0f, (point1.y + point2.y) / 2.0f,
				(point1.z + point2.z) / 2.0f);

		// add vertex makes sure point is on unit sphere
		int i = addVertex(middle);

		// store it, return index
		middlePointIndexCache.put(key, i);
		return i;
	}

	private void filterColourDefault(TerrainVertex vertex) {
		Vector3f tempColour = new Vector3f(0, 0, 0);
		tempColour.x = vertex.getColour().x / 2.0f;
		tempColour.y = vertex.getColour().y / 2.0f;
		tempColour.z = vertex.getColour().z / 2.0f;

		float offset = 2.0f * vertex.getNeighborIndices().size();
		for (int i = 0; i < vertex.getNeighborIndices().size(); i++) {
			Vector3f neighborColour = terrainVerticesList.get(vertex.getNeighborIndices().get(i)).getColour();
			tempColour.x += neighborColour.x / offset;
			tempColour.y += neighborColour.y / offset;
			tempColour.z += neighborColour.z / offset;
		}

		vertex.setFilteredColour(tempColour);
	}

	/*
	 * private TerrainFace checkFaces(Vector3f unitVector, List<TerrainFace>
	 * facesToCheck) { double maxDot = -100; TerrainFace cloestFace =
	 * facesToCheck.get(0);
	 * 
	 * 
	 * // int i=0; for (TerrainFace face : facesToCheck) { Vector3f faceNormal =
	 * face.getNormal(); double dotProduct = Vector3f.dot(unitVector,
	 * faceNormal); //System.out.println("i is: " + i + " and dot is: " +
	 * dotProduct + "\n"); if (dotProduct > maxDot) { maxDot = dotProduct;
	 * cloestFace = face; // System.out.println("normal is: " +
	 * cloestFace.getNormal() + // "\n"); } //i++; }
	 * 
	 * 
	 * 
	 * if (cloestFace.isBottom()) { return cloestFace; } else {
	 * 
	 * return checkFaces(unitVector, cloestFace.getChildren()); } }
	 * 
	 */

	private TerrainFace checkFacesPlucker(Vector3f unitVector, List<TerrainFace> facesToCheck) {
		float[] unitLine = new float[6];
		Maths.generateLines(unitVector, new Vector3f(0, 0, 0), unitLine);
		TerrainFace cloestFace = facesToCheck.get(0);

		// int i=0;
		for (TerrainFace face : facesToCheck) {
			// System.out.print(".....");
			// System.out.print(unitLine[0]);
			// System.out.print(face.getL1()[0]);
			Vector3f faceNormal = face.getNormal();
			double dotProduct = Vector3f.dot(unitVector, faceNormal);
			float s1 = Maths.sideOperations(unitLine, face.getL1());
			float s2 = Maths.sideOperations(unitLine, face.getL2());
			float s3 = Maths.sideOperations(unitLine, face.getL3());
			if (dotProduct >= 0) {

				if ((s1 <= 0 && s2 <= 0 && s3 <= 0) || (s1 >= 0 && s2 >= 0 && s3 >= 0)) {

					cloestFace = face;

				}
			}
			// i++;
		}

		if (cloestFace.isBottom()) {
			return cloestFace;
		} else {

			return checkFacesPlucker(unitVector, cloestFace.getChildren());
		}
	}

	/*
	 * public TerrainFace getTargetFace(float theta1, float theta2) { Vector3f
	 * unitVector = Maths.convertBackToCart(new Vector3f(1, theta1, theta2));
	 * 
	 * TerrainFace targetFace = checkFaces(unitVector, initTerrainFaces);
	 * 
	 * return targetFace; }
	 */

	public TerrainFace getTargetFacePlucker(float theta1, float theta2) {
		Vector3f unitVector = Maths.convertBackToCart(new Vector3f(1, theta1, theta2));

		TerrainFace targetFace = checkFacesPlucker(unitVector, initTerrainFaces);

		return targetFace;
	}

	public TerrainFace getTargetFacePlucker(Vector3f position) {
		// Vector3f unitVector = Maths.convertBackToCart(new Vector3f(1, theta1,
		// theta2));
		float length = (float) Math.sqrt(position.x * position.x + position.y * position.y + position.z * position.z);
		Vector3f unitVector = new Vector3f(position.x / length, position.y / length, position.z / length);
		TerrainFace targetFace = checkFacesPlucker(unitVector, initTerrainFaces);

		return targetFace;
	}

	public float getHeightAdvanced(float theta1, float theta2) {
		Vector3f unitVector = Maths.convertBackToCart(new Vector3f(1, theta1, theta2));
		// List<TerrainFace> facesToCheck = initTerrainFaces;
		float finalHeight = 0;
		TerrainFace targetFace = checkFacesPlucker(unitVector, initTerrainFaces);

		Vector4f point1 = verticesListWithHeight.get((int) targetFace.getIndices().x);
		Vector4f point2 = verticesListWithHeight.get((int) targetFace.getIndices().y);
		Vector4f point3 = verticesListWithHeight.get((int) targetFace.getIndices().z);

		finalHeight = (point1.w + point2.w + point3.w) / 3 * scale;

		return finalHeight;
	}

	public Vector3f getHeightPosAdvanced(float theta1, float theta2) {
		Vector3f unitVector = Maths.convertBackToCart(new Vector3f(1, theta1, theta2));
		// List<TerrainFace> facesToCheck = initTerrainFaces;
		Vector3f finalHeight = new Vector3f(0, 0, 0);
		TerrainFace targetFace = checkFacesPlucker(unitVector, initTerrainFaces);

		Vector4f point1 = verticesListWithHeight.get((int) targetFace.getIndices().x);
		Vector4f point2 = verticesListWithHeight.get((int) targetFace.getIndices().y);
		Vector4f point3 = verticesListWithHeight.get((int) targetFace.getIndices().z);

		finalHeight.x = (point1.x + point2.x + point3.x) / 3 * scale;
		finalHeight.y = (point1.y + point2.y + point3.y) / 3 * scale;
		finalHeight.z = (point1.z + point2.z + point3.z) / 3 * scale;

		return finalHeight;
	}

	public RawModel getModel() {
		return model;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getScale() {
		return scale;
	}

	public int[] getIndicesFinal() {
		return indicesFinal;
	}

	public float[] getVerticesUniform() {
		return verticesUniform;
	}

	public float[] getPolarFinal() {
		return polarFinal;
	}

	public float[] getVerticesFinal() {
		return verticesFinal;
	}

	public List<Vector4f> getVerticesListWithHeight() {
		return verticesListWithHeight;
	}

	public float[] getColourFinal() {
		return colourFinal;
	}

}
