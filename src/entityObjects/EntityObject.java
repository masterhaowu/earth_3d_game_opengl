package entityObjects;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainObject;
import terrainsSphere.TerrainSphere;
import terrainsSphere.TerrainVertex;

public class EntityObject {
	private Entity entity;
	private List<Entity> entityList;
	boolean multipleEntities;
	//private TerrainObject terrainObject;
	private TerrainFace face;
	private ObjectData objectData;
	
	
	public EntityObject(Entity entity, ObjectData objectData){
		this.entity = entity;
		this.objectData = objectData;
		this.multipleEntities = false;
		//this.face = face;
	}
	
	
	public boolean checkObjectCanExistOnTerrain(TerrainSphere terrainSphere){
		this.face = terrainSphere.getTargetFacePlucker(this.entity.getPosition());
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
		return true;
	}
	
	public void addEntity(Entity entity){
		if (multipleEntities) {
			this.entityList.add(entity);
		}
		else{
			this.entityList = new ArrayList<Entity>();
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


	public TerrainFace getFace() {
		return face;
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
	
	
	
	
	
}
