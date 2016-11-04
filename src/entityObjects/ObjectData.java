package entityObjects;

import java.util.HashSet;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ObjectData { //objectData dont keep track of the amount, terrainObject does that
	
	private int objectType;
	private String objectName = "LOCKED";
	private float objectInitAmount;
	private float objectMaxAmount;
	private float objectExtinctAmount;
	//private float objectAmount;
	private boolean affectTerrainColour;
	private Vector3f colour;
	private Vector2f entityAmountRange;
	/*
	private int foodSourcesCount;
	private HashSet<Integer> foodSource1;
	private float foodSource1Range;
	
	private HashSet<Integer> foodSource2;
	private float foodSource2Range;
	
	private HashSet<Integer> foodSource3;
	private float foodSource3Range;
	*/
	private HashSet<Integer> terrainTypes; //this is temp, need to replace this with better solution later
	
	
	
	private boolean affectedByHumidity = false;
	private boolean affectedByTemperature = false;
	private boolean affectedByHeight = false;
	
	
	
	private float humidityOptimal;
	private float temperatureOptimal;
	private float heightOptimal;
	
	private float humidityRange;
	private float temperatureRange;
	private float heightRange;
	
	private HashSet<Integer> preys;
	private HashSet<Integer> predators;
	
	private boolean noPrey = false;

	
	private float initScale;
	
	
	
	public boolean marked = false;
	
	public ObjectData(int objectType){
		this.objectType = objectType;
		preys = new HashSet<Integer>();
		predators = new HashSet<Integer>();
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
		this.objectMaxAmount = objectInitAmount * 3f;
		this.objectExtinctAmount = objectInitAmount / 3f;
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
	
	public void setHumidityInfo(float optimal, float range){
		this.affectedByHumidity = true;
		this.humidityOptimal = optimal;
		this.humidityRange = range;
	}
	
	public void setTemperatureInfo(float optimal, float range){
		this.affectedByTemperature = true;
		this.temperatureOptimal = optimal;
		this.temperatureRange = range;
	}
	
	public void setHeightInfo(float optimal, float range){
		this.affectedByHeight = true;
		this.heightOptimal = optimal;
		this.heightRange = range;
	}
	
	
	

	public boolean isAffectedByHumidity() {
		return affectedByHumidity;
	}

	public void setAffectedByHumidity(boolean affectedByHumidity) {
		this.affectedByHumidity = affectedByHumidity;
	}

	public boolean isAffectedByTemperature() {
		return affectedByTemperature;
	}

	public void setAffectedByTemperature(boolean affectedByTemperature) {
		this.affectedByTemperature = affectedByTemperature;
	}

	public boolean isAffectedByHeight() {
		return affectedByHeight;
	}

	public void setAffectedByHeight(boolean affectedByHeight) {
		this.affectedByHeight = affectedByHeight;
	}

	public float getHumidityOptimal() {
		return humidityOptimal;
	}

	public void setHumidityOptimal(float humidityOptimal) {
		this.humidityOptimal = humidityOptimal;
	}

	public float getTemperatureOptimal() {
		return temperatureOptimal;
	}

	public void setTemperatureOptimal(float temperatureOptimal) {
		this.temperatureOptimal = temperatureOptimal;
	}

	public float getHeightOptimal() {
		return heightOptimal;
	}

	public void setHeightOptimal(float heightOptimal) {
		this.heightOptimal = heightOptimal;
	}

	public float getHumidityRange() {
		return humidityRange;
	}

	public void setHumidityRange(float humidityRange) {
		this.humidityRange = humidityRange;
	}

	public float getTemperatureRange() {
		return temperatureRange;
	}

	public void setTemperatureRange(float temperatureRange) {
		this.temperatureRange = temperatureRange;
	}

	public float getHeightRange() {
		return heightRange;
	}

	public void setHeightRange(float heightRange) {
		this.heightRange = heightRange;
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

	public float getObjectMaxAmount() {
		return objectMaxAmount;
	}

	public void setObjectMaxAmount(float objectMaxAmount) {
		this.objectMaxAmount = objectMaxAmount;
	}

	public float getObjectExtinctAmount() {
		return objectExtinctAmount;
	}

	public void setObjectExtinctAmount(float objectExtinctAmount) {
		this.objectExtinctAmount = objectExtinctAmount;
	}
	
	public void addPrey(int prey){
		preys.add(prey);
	}
	
	public void addPredator(int predator){
		predators.add(predator);
	}

	public HashSet<Integer> getPreys() {
		return preys;
	}

	public HashSet<Integer> getPredators() {
		return predators;
	}

	public boolean isNoPrey() {
		return noPrey;
	}

	public void setNoPrey(boolean noPrey) {
		this.noPrey = noPrey;
	}
	
	
	

}

