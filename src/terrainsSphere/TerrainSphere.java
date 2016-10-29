package terrainsSphere;

//import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
//import java.util.Iterator;
import java.util.List;
import java.util.Set;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Random;
import java.util.Vector;

//import javax.imageio.ImageIO;

//import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import climate.TemperatureController;
import gameDataBase.ObjectsNetwork;
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
		//this.temperatureController = temperatureController;
		// verticesList = new ArrayList<Vector3f>();
		middlePointIndexCache = new HashMap<Long, Integer>();

		heightMapController = new HeightMapController();
		//heightMapController.fillTerrainHeightMapAndTypes();
		// heightMapController.fillTerrainHeightMapWithEarth();
		heightMapController.fillTerrainHeightMapWithImage("island");

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
						
						vertexA.addNeighbor(vertexB);
						vertexB.addNeighbor(vertexA);
						vertexA.addNeighbor(vertexC);
						vertexC.addNeighbor(vertexA);
						vertexB.addNeighbor(vertexC);
						vertexC.addNeighbor(vertexB);

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

			// Vector3f colour =
			// heightMapController.accessColourBasedOnHeight(theta1, theta2);
			// terrainVerticesList.get(i).setColour(colour);
			// terrainVerticesList.get(i).setColourBasedOnHeight(colour);
			// terrainVerticesList.get(i).addObjectDirectlyToVertex(ObjectsController.snowTerrain,
			// 10, true);
			heightMapController.loadColourBasedOnHeight(theta1, theta2, terrainVerticesList.get(i));

		}
		
		for (int i = 0; i < facesCount; i++){
			finalTerrainFaces.get(i).averagePolar();
			heightMapController.updateAverageHeight(finalTerrainFaces.get(i));
			heightMapController.loadFaceTerrainType(finalTerrainFaces.get(i));
		}
		
		for (int i = 0; i < verticesCount; i++) {
			terrainVerticesList.get(i).updateTerrainColour();
		}
		for (int i = 0; i < verticesCount; i++) {
			//System.out.println(terrainVerticesList.get(i).getNeighborVertices().size());
			filterColourDefault(terrainVerticesList.get(i));
			Vector3f filteredColour = terrainVerticesList.get(i).getFilteredColour();
			//filteredColour = terrainVerticesList.get(i).getColour();
			//System.out.println(filteredColour);
			colourFinal[i * 3] = filteredColour.x;
			colourFinal[i * 3 + 1] = filteredColour.y;
			colourFinal[i * 3 + 2] = filteredColour.z;
		}
		//System.out.println("haha");
		//System.out.println(verticesCount);
		//System.out.println(testing);

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
		/*
		float offset = 2.0f * vertex.getNeighborIndices().size();
		for (int i = 0; i < vertex.getNeighborIndices().size(); i++) {
			Vector3f neighborColour = terrainVerticesList.get(vertex.getNeighborIndices().get(i)).getColour();
			tempColour.x += neighborColour.x / offset;
			tempColour.y += neighborColour.y / offset;
			tempColour.z += neighborColour.z / offset;
		}
		*/
		float offset = 2.0f * vertex.getNeighborVertices().size();
		//System.out.println(vertex.getNeighborVertices().size());
		for (int i = 0; i < vertex.getNeighborVertices().size(); i++) {
			Vector3f neighborColour = vertex.getNeighborVertices().get(i).getColour();
			//System.out.println(neighborColour);
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

		Vector3f v0 = new Vector3f(point1.x, point1.y, point1.z);
		Vector3f v1 = new Vector3f(point2.x, point2.y, point2.z);
		Vector3f v2 = new Vector3f(point3.x, point3.y, point3.z);

		Vector3f finalPos = getPreciseRayTriangleIntersectionPoint(new Vector3f(0, 0, 0), unitVector, v0, v1, v2);
		if (finalPos == null) {
			// System.out.println("bad one");
			finalPos = getAverageRayTriangleIntersectionPoint(v0, v1, v2);
		}

		finalHeight = Maths.convertToPolar(finalPos).x * scale;

		return finalHeight;
	}

	public Vector3f getPositionAdvanced(float theta1, float theta2) {
		Vector3f unitVector = Maths.convertBackToCart(new Vector3f(1, theta1, theta2));
		// List<TerrainFace> facesToCheck = initTerrainFaces;
		Vector3f finalPos = new Vector3f(0, 0, 0);
		TerrainFace targetFace = checkFacesPlucker(unitVector, initTerrainFaces);

		Vector4f point1 = verticesListWithHeight.get((int) targetFace.getIndices().x);
		Vector4f point2 = verticesListWithHeight.get((int) targetFace.getIndices().y);
		Vector4f point3 = verticesListWithHeight.get((int) targetFace.getIndices().z);
		Vector3f v0 = new Vector3f(point1.x, point1.y, point1.z);
		Vector3f v1 = new Vector3f(point2.x, point2.y, point2.z);
		Vector3f v2 = new Vector3f(point3.x, point3.y, point3.z);
		/*
		 * finalHeight.x = (point1.x + point2.x + point3.x) / 3 * scale;
		 * finalHeight.y = (point1.y + point2.y + point3.y) / 3 * scale;
		 * finalHeight.z = (point1.z + point2.z + point3.z) / 3 * scale;
		 */
		finalPos = getPreciseRayTriangleIntersectionPoint(new Vector3f(0, 0, 0), unitVector, v0, v1, v2);
		if (finalPos == null) {
			// System.out.println("bad one");
			finalPos = getAverageRayTriangleIntersectionPoint(v0, v1, v2);
		}

		finalPos.x *= scale;
		finalPos.y *= scale;
		finalPos.z *= scale;

		return finalPos;
	}

	public Vector3f getAverageRayTriangleIntersectionPoint(Vector3f v0, Vector3f v1, Vector3f v2) {
		Vector3f finalPos = new Vector3f(0, 0, 0);
		finalPos.x = (v0.x + v1.x + v2.x) / 3;
		finalPos.y = (v0.y + v1.y + v2.y) / 3;
		finalPos.z = (v0.z + v1.z + v2.z) / 3;
		return finalPos;
	}

	public Vector3f getPreciseRayTriangleIntersectionPoint(Vector3f p, Vector3f d, Vector3f v0, Vector3f v1,
			Vector3f v2) {
		// p is rayOrigin
		// d is rayDirection
		Vector3f finalPos = new Vector3f(0, 0, 0);

		Vector3f e1 = Vector3f.sub(v1, v0, null);
		Vector3f e2 = Vector3f.sub(v2, v0, null);
		float f;
		Vector3f h = Vector3f.cross(d, e2, null);
		float a = Vector3f.dot(e1, h);
		if (a > -0.00001 && a < 0.00001) {
			// System.out.println("return because a failed");
			return null;
		}
		f = 1 / a;

		Vector3f s = Vector3f.sub(p, v0, null);
		float u = f * Vector3f.dot(s, h);
		if (u < -0.00001 || u > 1.00001) {
			// System.out.println("return because u failed");
			// System.out.println(u);
			return null;
		}

		Vector3f q = Vector3f.cross(s, e1, null);
		float v = f * Vector3f.dot(d, q);

		if (v < -0.00001 || u + v > 1.00001) {
			// System.out.println("return because v and u+v failed");
			return null;
		}

		float t = f * Vector3f.dot(e2, q);

		finalPos = new Vector3f(d.x * t + p.x, d.y * t + p.y, d.z * t + p.z);

		return finalPos;
	}

	/*
	 * private TerrainFace checkFacesPluckerCameraRay(Vector3f currentRay,
	 * Vector3f cameraPos, List<TerrainFace> facesToCheck) {
	 * //System.out.println(currentRay); float[] unitLine = new float[6];
	 * //Vector3f extendedRayPoint = new Vector3f(cameraPos.x + currentRay.x *
	 * 5000, cameraPos.y + currentRay.y * 5000, cameraPos.z + currentRay.z *
	 * 5000); Maths.generateLines(getPointOnRay(currentRay, 1000, cameraPos),
	 * cameraPos, unitLine); TerrainFace cloestFace = facesToCheck.get(0);
	 * 
	 * // int i=0; for (TerrainFace face : facesToCheck) { //
	 * System.out.print("....."); // System.out.print(unitLine[0]); //
	 * System.out.print(face.getL1()[0]); Vector3f faceNormal =
	 * face.getNormal(); double dotProduct = Vector3f.dot(currentRay,
	 * faceNormal); float s1 = Maths.sideOperations(unitLine, face.getL1());
	 * float s2 = Maths.sideOperations(unitLine, face.getL2()); float s3 =
	 * Maths.sideOperations(unitLine, face.getL3()); //System.out.println("s1" +
	 * s1 + "s2" + s2 + "s3" + s3); if (dotProduct <= 0) {
	 * 
	 * if ((s1 <= 0 && s2 <= 0 && s3 <= 0) || (s1 >= 0 && s2 >= 0 && s3 >= 0)) {
	 * 
	 * cloestFace = face; //System.out.println("here"); } } // i++; }
	 * 
	 * if (cloestFace.isBottom()) { return cloestFace; } else {
	 * 
	 * return checkFacesPluckerCameraRay(currentRay, cameraPos,
	 * cloestFace.getChildren()); } }
	 */

	public Vector3f getTerrainRayIntersectionPointNoHeight(Vector3f currentRay, Vector3f cameraPos) {
		Vector3f finalPos = new Vector3f(0, 0, 0);
		Vector3f c = new Vector3f(0, 0, 0);
		Vector3f p = cameraPos;
		Vector3f pToC = Vector3f.sub(c, p, null);
		float pToPcLen = Vector3f.dot(pToC, currentRay);
		Vector3f pc = getPointOnRay(currentRay, pToPcLen, cameraPos);
		// distance from pc to i1
		Vector3f cToPc = Vector3f.sub(pc, c, null);
		float cToPcLen = cToPc.length();
		if (cToPcLen > scale) {
			return null;
		}
		float dist = (float) Math.pow((Math.pow(scale, 2) - Math.pow(cToPcLen, 2)), 0.5);
		// distance from p to i1
		float di1 = pToPcLen - dist;
		finalPos = getPointOnRay(currentRay, di1, cameraPos);

		return finalPos;
	}

	public Vector3f getTerrainRayIntersectionPointCheckingNeighborFaces(Vector3f currentRay, Vector3f cameraPos,
			int maxRange) {
		Vector3f finalPosNoHeight = new Vector3f(0, 0, 0);
		Vector3f c = new Vector3f(0, 0, 0);
		Vector3f p = cameraPos;
		Vector3f pToC = Vector3f.sub(c, p, null);
		float pToPcLen = Vector3f.dot(pToC, currentRay);
		Vector3f pc = getPointOnRay(currentRay, pToPcLen, cameraPos);
		// distance from pc to i1
		Vector3f cToPc = Vector3f.sub(pc, c, null);
		float cToPcLen = cToPc.length();
		if (cToPcLen > scale) {
			return null;
		}
		float dist = (float) Math.pow((Math.pow(scale, 2) - Math.pow(cToPcLen, 2)), 0.5);
		// distance from p to i1
		float di1 = pToPcLen - dist;
		finalPosNoHeight = getPointOnRay(currentRay, di1, cameraPos);
		TerrainFace initFace = getTargetFacePlucker(finalPosNoHeight);
		Vector3f finalPos = null;
		for (int range = 0; range <= maxRange; range++) {
			Set<TerrainFace> facesToCheck = initFace.getNeighborFaces(range);
			
			// System.out.println("checking");
			for (TerrainFace currentFace : facesToCheck) {
				Vector4f tempV0 = verticesListWithHeight.get((int) currentFace.getIndices().x);
				Vector4f tempV1 = verticesListWithHeight.get((int) currentFace.getIndices().y);
				Vector4f tempV2 = verticesListWithHeight.get((int) currentFace.getIndices().z);
				// Vector3f v0 =
				// currentFace.getNeighorVerticesDefault().get(0).getPositionWithHeight();
				// Vector3f v1 =
				// currentFace.getNeighorVerticesDefault().get(1).getPositionWithHeight();
				// Vector3f v2 =
				// currentFace.getNeighorVerticesDefault().get(2).getPositionWithHeight();
				Vector3f v0 = new Vector3f(tempV0.x * scale, tempV0.y * scale, tempV0.z * scale);
				Vector3f v1 = new Vector3f(tempV1.x * scale, tempV1.y * scale, tempV1.z * scale);
				Vector3f v2 = new Vector3f(tempV2.x * scale, tempV2.y * scale, tempV2.z * scale);

				// System.out.println(v0);
				// System.out.println(v1);
				// System.out.println(v2);

				finalPos = getPreciseRayTriangleIntersectionPoint(cameraPos, currentRay, v0, v1, v2);
				// System.out.println(finalPos);
				if (finalPos != null) {
					return finalPos;
				}
			}
		}

		return finalPos;
	}

	/*
	 * public Vector3f getTerrainPointWithCameraRay(Vector3f currentRay,
	 * Vector3f cameraPos){ //bad method currentRay = new
	 * Vector3f(currentRay.x/scale, currentRay.y/scale, currentRay.z/scale);
	 * cameraPos = new Vector3f(cameraPos.x/scale, cameraPos.y/scale,
	 * cameraPos.z/scale); TerrainFace targetFace =
	 * checkFacesPluckerCameraRay(currentRay, cameraPos, initTerrainFaces);
	 * Vector4f point1 = verticesListWithHeight.get((int)
	 * targetFace.getIndices().x); Vector4f point2 =
	 * verticesListWithHeight.get((int) targetFace.getIndices().y); Vector4f
	 * point3 = verticesListWithHeight.get((int) targetFace.getIndices().z);
	 * Vector3f finalPos = new Vector3f(0, 0, 0); finalPos.x = (point1.x +
	 * point2.x + point3.x) / 3 * scale; finalPos.y = (point1.y + point2.y +
	 * point3.y) / 3 * scale; finalPos.z = (point1.z + point2.z + point3.z) / 3
	 * * scale; return finalPos; }
	 * 
	 * public TerrainFace getTerrainFaceWithCameraRay(Vector3f currentRay,
	 * Vector3f cameraPos){ //bad method currentRay = new
	 * Vector3f(currentRay.x/scale, currentRay.y/scale, currentRay.z/scale);
	 * cameraPos = new Vector3f(cameraPos.x/scale, cameraPos.y/scale,
	 * cameraPos.z/scale); TerrainFace targetFace =
	 * checkFacesPluckerCameraRay(currentRay, cameraPos, initTerrainFaces);
	 * 
	 * return targetFace; }
	 */
	private Vector3f getPointOnRay(Vector3f ray, float distance, Vector3f camPos) {
		// Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
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

	public List<TerrainFace> getFinalTerrainFaces() {
		return finalTerrainFaces;
	}
	
	

}
