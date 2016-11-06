package mainGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import animations.AnimationController;
import entities.Entity;
import entityObjects.EntityObject;
import entityObjects.ObjectData;
import entityObjects.PredationUnit;
import gameDataBase.ObjectsNetwork;
import models.TexturedModel;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;

public class GameEntityObjectsController {
	
	public static int currentObjectID = 0;
	
	
	private List<EntityObject> entityObjects;
	private List<Entity> normalMapEntities;
	private List<Entity> entitiesWithShadows;
	
	
	//private Map<Integer, EntityObject>
	
	private AnimationController animationController;
	//private TerrainSphere terrainSphere;
	
	
	
	public GameEntityObjectsController(AnimationController animationController){
		entityObjects = new ArrayList<EntityObject>();
		normalMapEntities = new ArrayList<Entity>();
		entitiesWithShadows = new ArrayList<Entity>();
		this.animationController = animationController;
		
	}
	
	
	
	public EntityObject createEntityObject(TexturedModel texturedModel, ObjectData objectData){
		Entity newEntitiy = new Entity(texturedModel, new Vector3f(0, 0, -5), 90, 0, 0, objectData.getInitScale());
		EntityObject newEntityObject = new EntityObject(newEntitiy, objectData);
		//entityObjects.add(newEntityObject);
		return newEntityObject;
	}
	
	public EntityObject createEntityObjectAndAddToList(TexturedModel texturedModel, ObjectData objectData){
		/*
		if (texturedModel == null) {
			System.out.println("model is null");
		}
		if (objectData == null) {
			System.out.println("objectData is null");
		}
		*/
		Entity newEntitiy = new Entity(texturedModel, new Vector3f(0, 0, -5), 90, 0, 0, objectData.getInitScale());
		//System.out.println(objectData.getInitScale());
		EntityObject newEntityObject = new EntityObject(newEntitiy, objectData);
		//newEntityObject.updateFace(terrainSphere);
		//newEntityObject.setFixed(true);
		entityObjects.add(newEntityObject);
		return newEntityObject;
	}
	
	public void connectFoodChain(EntityObject predater, EntityObject prey, int range){
		predater.getPreys().get(range).add(prey);
		prey.getPredators().get(range).add(predater);
	}
	
	
	public void checkNeighborFoodSourcesAndConnect(EntityObject entityObject, TerrainSphere terrainSphere){
		//HashSet<Integer> preys = entityObject.getObjectData().getPreys();
		HashMap<Integer, PredationUnit> preys = entityObject.getObjectData().getPreys();
		HashSet<Integer> predators = entityObject.getObjectData().getPredators();
		//ArrayList<ArrayList<EntityObject>> preys = entityObject.getPreys()
		TerrainFace currentFace = entityObject.getFace();
		ArrayList<ArrayList<TerrainFace>> neighborFaces = currentFace.getNeighborFaces();
		for (int i=0; i<TerrainSphere.FACE_NEIGHBOR_RANGE + 1; i++){
			ArrayList<TerrainFace> faces = neighborFaces.get(i);
			for (TerrainFace checkFace : faces){
				List<EntityObject> objects = checkFace.getEntityObjects();
				for (EntityObject checkObject : objects){
					int objectType = checkObject.getObjectData().getObjectType();
					
					if (preys.containsKey(objectType)) {
						//System.out.println(i);
						entityObject.getPreys().get(i).add(checkObject);
						checkObject.getPredators().get(i).add(entityObject);
					}
					if (predators.contains(objectType)) {
						//System.out.println(i);
						entityObject.getPredators().get(i).add(checkObject);
						checkObject.getPreys().get(i).add(entityObject);
					}
				}
			}
		}
	
	}
	
	
	public void addEntityObject(EntityObject entityObject){
		entityObjects.add(entityObject);
	}
	




	public List<EntityObject> getEntityObjects() {
		return entityObjects;
	}



	public List<Entity> getNormalMapEntities() {
		return normalMapEntities;
	}



	public List<Entity> getEntitiesWithShadows() {
		return entitiesWithShadows;
	}
	
	
	
	
	
	
	
	

}
