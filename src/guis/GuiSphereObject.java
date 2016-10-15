package guis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainVertex;

public class GuiSphereObject {
	
	
	private HashMap<Long, Integer> middlePointIndexCache;
	private List<Vector3f> vertices;
	private List<Vector3f> faces;
	float [] verticesFinal;
	int [] indicesFinal;
	int index = 0;
	private RawModel model;
	
	public GuiSphereObject(Loader loader) {
		middlePointIndexCache = new HashMap<Long, Integer>();
		vertices = new ArrayList<Vector3f>();
		faces = new ArrayList<Vector3f>();
		index = 0;
		this.model = generateGuiSphereIco(loader, 2);
	}
	
	private RawModel generateGuiSphereIco(Loader loader, int loops) {
		
		float t = (float) ((1.0 + Math.sqrt(5.0)) / 2.0);
		float[] verticesInit = { -1, t, 0, 1, t, 0, -1, -t, 0, 1, -t, 0, 0, -1, t, 0, 1, t, 0, -1, -t, 0, 1, -t, t, 0,
				-1, t, 0, 1, -t, 0, -1, -t, 0, 1 };

		int[] indicesInit = { 0, 11, 5, 0, 5, 1, 0, 1, 7, 0, 7, 10, 0, 10, 11, 1, 5, 9, 5, 11, 4, 11, 10, 2, 10, 7, 6,
				7, 1, 8, 3, 9, 4, 3, 4, 2, 3, 2, 6, 3, 6, 8, 3, 8, 9, 4, 9, 5, 2, 4, 11, 6, 2, 10, 8, 6, 7, 9, 8, 1 };

		for (int i = 0; i < 12; i++) {
			addVertex(new Vector3f(verticesInit[i * 3], verticesInit[i * 3 + 1], verticesInit[i * 3 + 2]));
			//vertices.add(new Vector3f(verticesInit[i * 3], verticesInit[i * 3 + 1], verticesInit[i * 3 + 2]));
		}
		
		for (int i = 0; i < 20; i++) {
			
			faces.add(new Vector3f(indicesInit[i * 3], indicesInit[i * 3 + 1], indicesInit[i * 3 + 2]));
		}
		
		for (int i = 0; i < loops; i++) {
			
			int listSize = faces.size();
			List<Vector3f> faces2 = new ArrayList<Vector3f>();
			for (int j = 0; j < listSize; j++) {
				int a = getMiddlePoint((int )faces.get(j).x, (int) faces.get(j).y);
				int b = getMiddlePoint((int )faces.get(j).y, (int) faces.get(j).z);
				int c = getMiddlePoint((int )faces.get(j).z, (int) faces.get(j).x);
				
				
				faces2.add(new Vector3f((int)faces.get(j).x, a, c));
				faces2.add(new Vector3f((int)faces.get(j).y, b, a));
				faces2.add(new Vector3f((int)faces.get(j).z, c, b));
				faces2.add(new Vector3f(a, b, c));
				
			}
			
			
			faces = faces2;
			
			
		}
		int verticesCount = vertices.size();
		int facesCount = faces.size();
		System.out.println(facesCount);
		verticesFinal = new float[verticesCount * 3];
		indicesFinal = new int[facesCount * 3];
		
		for (int i = 0; i < facesCount; i++) {
			indicesFinal[i * 3] = (int) faces.get(i).x;
			indicesFinal[i * 3 + 1] = (int) faces.get(i).y;
			indicesFinal[i * 3 + 2] = (int) faces.get(i).z;
		}
		
		for (int i = 0; i < verticesCount; i++) {
			verticesFinal[i * 3] = vertices.get(i).x;
			verticesFinal[i * 3 + 1] = vertices.get(i).y;
			verticesFinal[i * 3 + 2] = vertices.get(i).z;
		}
		
		return loader.loadToVAO(verticesFinal, indicesFinal, 3);
	}
	
	private int addVertex(Vector3f p) {
		float length = (float) Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
		vertices.add(new Vector3f(p.x / length, p.y / length, p.z / length));
		return index++;
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
		Vector3f point1 = vertices.get(p1);
		Vector3f point2 = vertices.get(p2);
		Vector3f middle = new Vector3f((point1.x + point2.x) / 2.0f, (point1.y + point2.y) / 2.0f,
				(point1.z + point2.z) / 2.0f);

		// add vertex makes sure point is on unit sphere
		int i = addVertex(middle);

		// store it, return index
		middlePointIndexCache.put(key, i);
		return i;
	}

	public RawModel getModel() {
		return model;
	}
	
	

}
