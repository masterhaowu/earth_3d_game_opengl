package mainGame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import animations.AnimationController;
import entities.Entity;
import entityObjects.EntityObject;
import entityObjects.ObjectData;
import gameDataBase.ObjectsNetwork;
import models.TexturedModel;

public class GameEntityObjectsController {
	
	
	private List<EntityObject> entityObjects;
	private List<Entity> normalMapEntities;
	private List<Entity> entitiesWithShadows;
	
	private AnimationController animationController;
	
	
	
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
		Entity newEntitiy = new Entity(texturedModel, new Vector3f(0, 0, -5), 90, 0, 0, objectData.getInitScale());
		EntityObject newEntityObject = new EntityObject(newEntitiy, objectData);
		entityObjects.add(newEntityObject);
		return newEntityObject;
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
