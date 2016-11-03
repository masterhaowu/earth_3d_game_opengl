package entityObjects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import gameDataBase.ObjectsNetwork;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainObject;
import terrainsSphere.TerrainSphere;
import terrainsSphere.TerrainVertex;

public class EntityObject {
	public static final int PREY_LEVELS_THRESHOLD = 4;
	
	private Entity entity;
	private List<Entity> entityList; //first one will be base position
	boolean multipleEntities;
	//private TerrainObject terrainObject;
	private TerrainFace face;
	private ObjectData objectData;
	//private float initScale;
	//private Vector3f position;
	private float amount = 0;
	private boolean fixed = false;
	
	
	private ArrayList<ArrayList<EntityObject>> preys;
	private ArrayList<ArrayList<EntityObject>> predators;
	
	
	
	public EntityObject(Entity entity, ObjectData objectData){
		this.entity = entity;
		this.objectData = objectData;
		this.multipleEntities = false;
		this.amount = objectData.getObjectInitAmount();
		//this.face = face;
		initEntityLists();
	}
	
	public EntityObject(List<Entity> entities, ObjectData objectData){
		this.entityList = entities;
		this.objectData = objectData;
		this.multipleEntities = true;
		this.amount = objectData.getObjectInitAmount();
		//this.face = face;
		initEntityLists();
	}
	
	public void initEntityLists(){
		preys = new ArrayList<ArrayList<EntityObject>>();
		for (int i = 0; i < PREY_LEVELS_THRESHOLD; i++) {
			ArrayList<EntityObject> tempList = new ArrayList<EntityObject>();
			preys.add(tempList);
		}
		predators = new ArrayList<ArrayList<EntityObject>>();
		for (int i = 0; i < PREY_LEVELS_THRESHOLD; i++) {
			ArrayList<EntityObject> tempList = new ArrayList<EntityObject>();
			predators.add(tempList);
		}
	}
	
	
	public boolean checkObjectHasFoodAround(TerrainSphere terrainSphere){
		//need implement later
		return true;
	}
	
	public boolean checkObjectAboveWater(TerrainSphere terrainSphere){
		this.face = terrainSphere.getTargetFacePlucker(this.entity.getPosition());
		if (face.getHeight() > 0) {
			return true;
		}
		return false;
	}
	
	
	public boolean checkObjectCanExistOnTerrain(TerrainSphere terrainSphere){
		this.face = terrainSphere.getTargetFacePlucker(this.entity.getPosition());
		/*
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();
		for (TerrainVertex vertex:neighborVertices){
			boolean hasTerrainType = false;
			for(int desiredTerrainType : objectData.getTerrainTypes()){
				if (vertex.getObjects().containsKey(desiredTerrainType)) {
					hasTerrainType = true;
				}
			}
			if (!hasTerrainType) {
				return false;
			}
		}
		*/
		for(int desiredTerrainType : objectData.getTerrainTypes()){
			if (this.face.getTerrainType().getObjectType() == desiredTerrainType) {
				return true;
			}
		}
		return false;
	}
	
	public void addEntity(Entity entity){
		if (multipleEntities) {
			this.entityList.add(entity);
		}
		else{ //this can only happen if the entity contains one entity
			this.entityList = new ArrayList<Entity>();
			this.entityList.add(this.entity);
			this.entityList.add(entity);
			this.multipleEntities = true;
		}
	}


	public Entity getEntity() {
		return entity;
	}


	public void setEntity(Entity entity) {
		this.entity = entity;
	}


	public List<Entity> getEntityList() {
		return entityList;
	}


	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}


	public boolean isMultipleEntities() {
		return multipleEntities;
	}


	public void setMultipleEntities(boolean multipleEntities) {
		this.multipleEntities = multipleEntities;
	}


	public TerrainFace getFace(TerrainSphere terrainSphere) {
		return terrainSphere.getTargetFacePlucker(this.getPosition());
	}


	public void setFace(TerrainFace face) {
		this.face = face;
	}


	public ObjectData getObjectData() {
		return objectData;
	}


	public void setObjectData(ObjectData objectData) {
		this.objectData = objectData;
	}
	
	
	public Vector3f getPosition(){
		if (multipleEntities) {
			return entityList.get(0).getPosition();
		}
		else{
			return entity.getPosition();
		}
	}
	
	public Vector3f getPolar(){
		if (multipleEntities) {
			return entityList.get(0).getPolar();
		}
		else{
			return entity.getPolar();
		}
	}
	
	public float getRotX(){
		if (multipleEntities) {
			return entityList.get(0).getRotX();
		}
		else{
			return entity.getRotX();
		}
	}
	
	public float getRotY(){
		if (multipleEntities) {
			return entityList.get(0).getRotY();
		}
		else{
			return entity.getRotY();
		}
	}
	
	public float getRotZ(){
		if (multipleEntities) {
			return entityList.get(0).getRotZ();
		}
		else{
			return entity.getRotZ();
		}
	}
	
	public float getScale(){
		if (multipleEntities) {
			return entityList.get(0).getScale();
		}
		else{
			return entity.getScale();
		}
	}
	
	public boolean isHasMinMax(){
		if (multipleEntities) {
			return entityList.get(0).getModel().getRawModel().isHasMinMax();
		}
		else{
			return entity.getModel().getRawModel().isHasMinMax();
		}
	}
	
	public void setHighlighted(boolean highlighted){
		if (multipleEntities) {
			for (Entity entity:entityList){
				entity.setHighlighted(highlighted);
			}
		}
		else{
			entity.setHighlighted(highlighted);
		}
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public TerrainFace getFace() {
		return face;
	}

	public void updateFace(TerrainSphere terrainSphere){
		this.face = terrainSphere.getTargetFacePlucker(this.entity.getPosition());
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	public void connectFaceWithEntity(){
		this.fixed = true;
		this.face.addEntityObject(this);
	}
	
	
	
	
}
