package terrainsSphere;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;

public class TerrainVertex {

	public static final Vector3f HIGHLIGHT_COLOUR = new Vector3f(1, 1, 1);

	private Vector3f position;
	private Vector3f positionWithHeight;
	private Vector3f polar;
	private Vector3f colour;
	private Vector3f filteredColour;
	private int terrainType;
	private float totalColourObjectAmount;
	// private List<Integer> terrainSecondaryTypes;
	// private List<TerrainObject> terrainObjects;
	private HashMap<Integer, TerrainObject> objects;
	

	private int index;

	// private List<TerrainVertex> neighborVertices;
	// private Set<Integer> neighborVertices;
	private List<Integer> neighborIndices;
	private List<TerrainVertex> neighborVertices;
	private List<TerrainFace> neighborFaces;

	public TerrainVertex(Vector3f position, int index) {
		this.position = position;
		this.polar = Maths.convertToPolar(position);
		this.index = index;
		// this.neighborVertices = new ArrayList<TerrainVertex>();
		// this.neighborVertices = new HashSet<Integer>();
		this.neighborIndices = new ArrayList<Integer>();
		this.neighborVertices = new ArrayList<TerrainVertex>();
		this.neighborFaces = new ArrayList<TerrainFace>();
		// this.terrainSecondaryTypes = new ArrayList<Integer>();
		this.objects = new HashMap<Integer, TerrainObject>();
		this.totalColourObjectAmount = 1;
	}

	public void addObject(TerrainObject object) {
		int type = object.getObjectType();
		float amount = object.getObjectAmount() / 3f;
		if (objects.containsKey(type)) {
			objects.get(type).setObjectAmount(objects.get(type).getObjectAmount() + amount);
		} else {
			TerrainObject objectToAdd;
			if (object.isAffectTerrainColour()) {
				objectToAdd = new TerrainObject(type, amount, object.getColour());
				//this.colour.x = (colour.x * totalColourObjectAmount + object.getColour().x * amount)/(totalColourObjectAmount + amount);
				//this.colour.y = (colour.y * totalColourObjectAmount + object.getColour().y * amount)/(totalColourObjectAmount + amount);
				///this.colour.z = (colour.z * totalColourObjectAmount + object.getColour().z * amount)/(totalColourObjectAmount + amount);
				this.colour.x = 1;
				this.colour.y = 1;
				this.colour.z = 1;
				//System.out.print(this.colour.x);
				this.totalColourObjectAmount += amount;
			} else {
				objectToAdd = new TerrainObject(type, amount);
			}
			objects.put(type, objectToAdd);
		}
	}
	
	

	public void addNeighbor(int neighborIndex) { // this add neighbor's indices
													// value
		for (int i = 0; i < this.neighborIndices.size(); i++) {
			if (this.neighborIndices.get(i) == neighborIndex) {
				return;
			}
		}
		this.neighborIndices.add(neighborIndex);
	}

	public void addNeighbor(TerrainVertex neighborVertex) { // this add
															// neighbor's
															// indices value and
															// vertex
		int neighborIndex = neighborVertex.getIndex();
		for (int i = 0; i < this.neighborIndices.size(); i++) {
			if (this.neighborIndices.get(i) == neighborIndex) {
				return;
			}
		}
		this.neighborIndices.add(neighborIndex);
		this.neighborVertices.add(neighborVertex);
	}

	public void addNeighborFaces(TerrainFace face) {
		this.neighborFaces.add(face);
	}

	// public void addNeighborVertices(TerrainVertex)

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		this.polar = Maths.convertToPolar(position);
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public int getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(int terrainType) {
		this.terrainType = terrainType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<Integer> getNeighborIndices() {
		return neighborIndices;
	}

	public Vector3f getPositionWithHeight() {
		return positionWithHeight;
	}

	public void setPositionWithHeight(Vector3f positionWithHeight) {
		this.positionWithHeight = positionWithHeight;
	}

	public Vector3f getFilteredColour() {
		return filteredColour;
	}

	public void setFilteredColour(Vector3f filteredColour) {
		this.filteredColour = filteredColour;
	}

	public List<TerrainVertex> getNeighborVertices() {
		return neighborVertices;
	}

	public HashMap<Integer, TerrainObject> getObjects() {
		return objects;
	}

	public List<TerrainFace> getNeighborFaces() {
		return neighborFaces;
	}
	
	

}
