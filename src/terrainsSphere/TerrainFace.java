package terrainsSphere;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Vector3f;

import entityObjects.EntityObject;
import entityObjects.ObjectData;
import toolbox.Maths;

public class TerrainFace {

	private Vector3f indices;
	private Vector3f normal;
	private Vector3f averagePolar = new Vector3f(0, 0, 0);
	private float[] l1;
	private float[] l2;
	private float[] l3;
	//private List<TerrainFace> neighbors;
	private List<TerrainVertex> vertices;
	private List<TerrainFace> children;
	private List<EntityObject> entityObjects;
	private List<TerrainObject> terrainObjects;
	
	private ArrayList<ArrayList<TerrainFace>> neighborFaces;
	
	
	private ObjectData terrainType;
	private ObjectData terrainTypePreview;
	
	private float height;
	private float temperature;
	private float humidity;
	private float humidityWithOffset; // this will map humidity to the correct position when calculating types
	private int distanceToWater = -1; // -1 means unchecked or need to recheck
	
	private float snowAmount = 0;
	
	private TerrainFace parent;
	private int level;
	private boolean isBottom;
	private boolean isTop;
	

	public TerrainFace(Vector3f indices) {
		this.indices = indices;
		this.isTop = true;
		this.isBottom = false;
		this.level = 0;
		this.parent = null;
		// this.neighbors = new ArrayList<TerrainFace>();
		this.vertices = new ArrayList<TerrainVertex>();
		this.children = new ArrayList<TerrainFace>();
		this.terrainObjects = new ArrayList<TerrainObject>();
		this.entityObjects = new ArrayList<EntityObject>();
	}

	public TerrainFace(Vector3f indices, TerrainFace parent, boolean isBottom) {
		this.indices = indices;
		this.isTop = false;
		this.isBottom = isBottom;
		this.level = parent.level + 1;
		this.parent = parent;
		this.vertices = new ArrayList<TerrainVertex>();
		this.entityObjects = new ArrayList<EntityObject>();
		// this.neighbors = new ArrayList<TerrainFace>();
		if (isBottom) {
			this.children = null;
		} else {
			this.children = new ArrayList<TerrainFace>();
		}
		this.terrainObjects = new ArrayList<TerrainObject>();
		parent.addChild(this);
		
	}
	
	
	
	
	public void setNormal(Vector3f normal){
		this.normal = normal;
	}
	
	public void updateLines(){
		  

		l1 = new float [6];
		l2 = new float [6];
		l3 = new float [6];
		Vector3f p1 = vertices.get(0).getPosition();
		Vector3f p2 = vertices.get(1).getPosition();
		Vector3f p3 = vertices.get(2).getPosition();
		Maths.generateLines(p1, p2, l1);
		Maths.generateLines(p2, p3, l2);
		Maths.generateLines(p3, p1, l3);
	}
	
	
	public Set<TerrainFace> getNeighborFaces(int range){
		Set<TerrainFace> neighborFacesSet = new HashSet<TerrainFace>();
		neighborFacesSet.add(this);
		for (int i=0; i<range; i++){
			//create another set in case iterator keeps going due to the fact i am adding more faces to the end
			//also i dont have to modify the original set this way
			Set<TerrainFace> incresedFaces = new HashSet<TerrainFace>();
			for (TerrainFace currentFace : neighborFacesSet){
				//first copy itself to the increased set
				if (!incresedFaces.contains(currentFace)) {
					incresedFaces.add(currentFace);
				}
				//then copy neighbors
				for (TerrainVertex vertex: vertices){ // check this!!
					List<TerrainFace> vertexNeighborFaces = vertex.getNeighborFaces();
					//System.out.println(vertexNeighborFaces.size());
					for (TerrainFace currentNeighborFace : vertexNeighborFaces){
						if (!incresedFaces.contains(currentNeighborFace)) {
							incresedFaces.add(currentNeighborFace);
						}
					}
				}
			}
			//give increasedFaces to neighborFaces
			neighborFacesSet = incresedFaces;
		}
		return neighborFacesSet;
	}
	
	
	public void connectFaceNeighborsWithRange(int range){
		this.neighborFaces = new ArrayList<ArrayList<TerrainFace>>();
		Set<TerrainFace> checkedFaces = new HashSet<>();
		checkedFaces.add(this);
		ArrayList<TerrainFace> initList = new ArrayList<TerrainFace>();
		initList.add(this);
		neighborFaces.add(initList);
		for (int i=1; i<range+1; i++){
			ArrayList<TerrainFace> currentFaceList = new ArrayList<TerrainFace>();
			ArrayList<TerrainFace> previousList = neighborFaces.get(i - 1);
			for (TerrainFace previousFace : previousList){
				List<TerrainVertex> VertexList = previousFace.getNeighorVerticesDefault();
				for (TerrainVertex vertex : VertexList){
					List<TerrainFace> newFaceList = vertex.getNeighborFaces();
					for (TerrainFace newFace : newFaceList){
						if (!checkedFaces.contains(newFace)) {
							currentFaceList.add(newFace);
							checkedFaces.add(newFace);
						}
					}
				}
			}
			neighborFaces.add(currentFaceList);
		}
	}
	
	
	
	
	public void addChild(TerrainFace child){
		this.children.add(child);
	}
	
	public void addTerrainObject(TerrainObject terrainObjectobject){
		this.terrainObjects.add(terrainObjectobject);
	}

	public Vector3f getIndices() {
		return indices;
	}

	public List<TerrainFace> getChildren() {
		return children;
	}

	public TerrainFace getParent() {
		return parent;
	}

	public int getLevel() {
		return level;
	}

	public boolean isBottom() {
		return isBottom;
	}

	public boolean isTop() {
		return isTop;
	}
	
	public Vector3f getNormal(){
		return normal;
	}
	
	public void addVertex(TerrainVertex vertex){
		vertices.add(vertex);
	}
	
	public List<TerrainVertex> getNeighorVerticesDefault(){
		return vertices;
	}

	public float[] getL1() {
		return l1;
	}

	public float[] getL2() {
		return l2;
	}

	public float[] getL3() {
		return l3;
	}

	public void setL1(float[] l1) {
		this.l1 = l1;
	}

	public void setL2(float[] l2) {
		this.l2 = l2;
	}

	public void setL3(float[] l3) {
		this.l3 = l3;
	}

	public ObjectData getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(ObjectData terrainType) {
		this.terrainType = terrainType;
	}

	public ObjectData getTerrainTypePreview() {
		return terrainTypePreview;
	}

	public void setTerrainTypePreview(ObjectData terrainTypePreview) {
		this.terrainTypePreview = terrainTypePreview;
	}

	public Vector3f getAveragePolar() {
		return averagePolar;
	}
	
	public void averagePolar(){
		averagePolar = new Vector3f(0, 0, 0);
		for (int i=0; i<vertices.size(); i++){
			Vector3f.add(averagePolar, vertices.get(i).getPolar(), averagePolar);
		}
		//System.out.println(vertices.size());
		averagePolar.x /= vertices.size();
		averagePolar.y /= vertices.size();
		averagePolar.z /= vertices.size();
		//System.out.println(averagePolar);
	}
	
	public Vector3f getAveragePolarWithHeight(){
		return new Vector3f(averagePolar.x + height/HeightMapController.HEIGHT_SCALE, averagePolar.y, averagePolar.z);
		//System.out.println(averagePolar);
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getDistanceToWater() {
		return distanceToWater;
	}

	public void setDistanceToWater(int distanceToWater) {
		this.distanceToWater = distanceToWater;
	}

	public float getHumidityWithOffset() {
		return humidityWithOffset;
	}

	public void setHumidityWithOffset(float humidityWithOffset) {
		this.humidityWithOffset = humidityWithOffset;
	}

	public float getSnowAmount() {
		return snowAmount;
	}

	public void setSnowAmount(float snowAmount) {
		this.snowAmount = snowAmount;
	}

	public List<EntityObject> getEntityObjects() {
		return entityObjects;
	}
	
	
	public void addEntityObject(EntityObject entityObject){
		entityObjects.add(entityObject);
	}

	public ArrayList<ArrayList<TerrainFace>> getNeighborFaces() {
		return neighborFaces;
	}
	
	
	

}
