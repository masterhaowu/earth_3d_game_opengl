package entityObjects;

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
	
	
	
}
