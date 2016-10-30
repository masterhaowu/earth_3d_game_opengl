package entityObjects;

import java.util.HashSet;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ObjectData { //objectData dont keep track of the amount, terrainObject does that
	
	private int objectType;
	private String objectName = "LOCKED";
	private float objectInitAmount;
	//private float objectAmount;
	private boolean affectTerrainColour;
	private Vector3f colour;
	private Vector2f entityAmountRange;
	
	private int foodSourcesCount;
	private HashSet<Integer> foodSource1;
	private float foodSource1Range;
	
	private HashSet<Integer> foodSource2;
	private float foodSource2Range;
	
	private HashSet<Integer> foodSource3;
	private float foodSource3Range;
	
	private HashSet<Integer> terrainTypes;
	
	private float initScale;
	
	public ObjectData(int objectType){
		this.objectType = objectType;
		foodSource1 = new HashSet<Integer>();
		foodSource2 = new HashSet<Integer>();
		foodSource3 = new HashSet<Integer>();
		terrainTypes = new HashSet<Integer>();
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public float getObjectInitAmount() {
		return objectInitAmount;
	}

	public void setObjectInitAmount(float objectInitAmount) {
		this.objectInitAmount = objectInitAmount;
	}

	public boolean isAffectTerrainColour() {
		return affectTerrainColour;
	}

	public void setAffectTerrainColour(boolean affectTerrainColour) {
		this.affectTerrainColour = affectTerrainColour;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public Vector2f getEntityAmountRange() {
		return entityAmountRange;
	}

	public void setEntityAmountRange(Vector2f entityAmountRange) {
		this.entityAmountRange = entityAmountRange;
	}

	public int getFoodSourcesCount() {
		return foodSourcesCount;
	}

	public void setFoodSourcesCount(int foodSourcesCount) {
		this.foodSourcesCount = foodSourcesCount;
	}

	public HashSet<Integer> getFoodSource1() {
		return foodSource1;
	}

	public void setFoodSource1(HashSet<Integer> foodSource1) {
		this.foodSource1 = foodSource1;
	}

	public float getFoodSource1Range() {
		return foodSource1Range;
	}

	public void setFoodSource1Range(float foodSource1Range) {
		this.foodSource1Range = foodSource1Range;
	}

	public HashSet<Integer> getFoodSource2() {
		return foodSource2;
	}

	public void setFoodSource2(HashSet<Integer> foodSource2) {
		this.foodSource2 = foodSource2;
	}

	public float getFoodSource2Range() {
		return foodSource2Range;
	}

	public void setFoodSource2Range(float foodSource2Range) {
		this.foodSource2Range = foodSource2Range;
	}

	public HashSet<Integer> getFoodSource3() {
		return foodSource3;
	}

	public void setFoodSource3(HashSet<Integer> foodSource3) {
		this.foodSource3 = foodSource3;
	}

	public float getFoodSource3Range() {
		return foodSource3Range;
	}

	public void setFoodSource3Range(float foodSource3Range) {
		this.foodSource3Range = foodSource3Range;
	}

	public HashSet<Integer> getTerrainTypes() {
		return terrainTypes;
	}

	public void setTerrainTypes(HashSet<Integer> terrainTypes) {
		this.terrainTypes = terrainTypes;
	}
	
	public void addTerrainType(int terrainType){
		if (!terrainTypes.contains(terrainType)) {
			terrainTypes.add(terrainType);
		}
	}

	public int getObjectType() {
		return objectType;
	}
	
	public float getInitScale() {
		return initScale;
	}

	public void setInitScale(float initScale) {
		this.initScale = initScale;
	}
	
	
	

}

