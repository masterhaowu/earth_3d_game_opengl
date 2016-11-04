package mainGame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entityObjects.EntityObject;
import fontMeshCreator.GUIText;
import gameDataBase.EntityModelDataBase;
import gameDataBase.ObjectsNetwork;
import guiEvents.GuiEventController;
import guis.GuiObjectUnit;
import guis.GuiSphereTexture;
import guis.GuiTexture;
import mouse.HighlightedCircle;
import mouse.MouseDraggingController;
import mouse.MouseHighlightController;
import mouse.MousePickerSphere;
import renderEngine.Loader;
import terrainsSphere.ColourController;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;

public class GameEventController {

	private GuiEventController guiEventController;
	private MousePickerSphere picker;
	private ColourController colourController;
	private Loader loader;
	private TerrainSphere terrainSphere;
	private TerrainFace currentFace;
	private TerrainFace previousFace;
	private boolean changeFace;
	private boolean showCircle;
	private HighlightedCircle highlightedCircle;
	
	private boolean mousePrevious = false;
	private boolean mouseCurrent = false;
	private boolean mouseClicked = false;

	private EntityObject entityObjectToDrag;
	private EntityObject entityObjectToAdd;

	private MouseDraggingController mouseDraggingController;
	private MouseHighlightController mouseHighlightController;

	private GameEntityObjectsController gameEntityObjectsController;

	public GameEventController(MousePickerSphere picker, ColourController colourController, Loader loader,
			HighlightedCircle highlightedCircle, TerrainSphere terrainSphere,
			GameEntityObjectsController gameEntityObjectsController) {
		this.guiEventController = new GuiEventController(loader, picker, gameEntityObjectsController);
		this.picker = picker;
		this.colourController = colourController;
		this.loader = loader;
		this.currentFace = picker.getCurrentTerrainFace();
		this.previousFace = picker.getCurrentTerrainFace();
		this.changeFace = true;
		this.highlightedCircle = highlightedCircle;
		this.terrainSphere = terrainSphere;
		this.gameEntityObjectsController = gameEntityObjectsController;

		this.mouseHighlightController = new MouseHighlightController(picker, colourController, loader,
				highlightedCircle, terrainSphere);
		this.mouseDraggingController = new MouseDraggingController(picker, colourController, loader, highlightedCircle,
				terrainSphere);
		// this.showCircle = false;

	}

	public void setObjectToAdd(EntityObject entityObject) {
		this.entityObjectToAdd = entityObject;
	}

	public void updateEvents(List<EntityObject> entityObjects) {
		updateMouse();
		picker.updateOptimized();
		mouseClicked = guiEventController.update(mouseClicked);
		switch (GameStateController.currentState) {
		case GameStateController.PLAY_MODE_IDLE:

			switch (GameStateController.gameModeState) {
			case GameStateController.CREATION_TERRAIN_MODE:

				switch (GameStateController.CTState) {
				case GameStateController.CT_TERRAIN_DRAGGING:
					//System.out.println("here");
					currentFace = picker.getCurrentTerrainFace();
					
					if (Mouse.isButtonDown(0)) {
						colourController.setTerrain(currentFace, guiEventController.getTerrainToReturn());
						colourController.setPreviewTerrain(currentFace, guiEventController.getTerrainToReturn());
						//System.out.println("here");
						colourController.updateColourVBO(loader);
						
					}
					
					colourController.restoreFaceColour(previousFace);
					colourController.setPreviewTerrain(currentFace, guiEventController.getTerrainToReturn());
					colourController.updateColourVBO(loader);
					
					
					
					previousFace = currentFace;
					
					break;
					
				case GameStateController.CT_GRASS_DRAGGING:
					entityObjectToDrag = guiEventController.getEntityObjectToReturn();
					this.showCircle = true;
					mouseDraggingController.drag(entityObjectToDrag);
					if (mouseClicked) {
						this.showCircle = false;
						//entityObjectToDrag.setFixed(true);
						entityObjectToDrag.connectFaceWithEntity();
						gameEntityObjectsController.checkNeighborFoodSourcesAndConnect(entityObjectToDrag, terrainSphere);
						GameStateController.CTState = GameStateController.CT_IDLE;
					}
					break;
					
				case GameStateController.CT_TREE_DRAGGING:
					entityObjectToDrag = guiEventController.getEntityObjectToReturn();
					this.showCircle = true;
					mouseDraggingController.drag(entityObjectToDrag);
					if (mouseClicked) {
						this.showCircle = false;
						//entityObjectToDrag.setFixed(true);
						entityObjectToDrag.connectFaceWithEntity();
						gameEntityObjectsController.checkNeighborFoodSourcesAndConnect(entityObjectToDrag, terrainSphere);
						GameStateController.CTState = GameStateController.CT_IDLE;
					}
					break;

				default:
					break;
				}

				break;
			case GameStateController.CREATION_ANIMAL_MODE:
				switch (GameStateController.CAState) {
				case GameStateController.CA_HERBIVORE_DRAGGING:
					entityObjectToDrag = guiEventController.getEntityObjectToReturn();
					this.showCircle = true;
					mouseDraggingController.drag(entityObjectToDrag);
					if (mouseClicked) {
						this.showCircle = false;
						entityObjectToDrag.connectFaceWithEntity();
						gameEntityObjectsController.checkNeighborFoodSourcesAndConnect(entityObjectToDrag, terrainSphere);
						GameStateController.CAState = GameStateController.CA_IDLE;
					}
					break;
				case GameStateController.CA_CARNIVORE_DRAGGING:
					entityObjectToDrag = guiEventController.getEntityObjectToReturn();
					this.showCircle = true;
					mouseDraggingController.drag(entityObjectToDrag);
					if (mouseClicked) {
						this.showCircle = false;
						entityObjectToDrag.connectFaceWithEntity();
						gameEntityObjectsController.checkNeighborFoodSourcesAndConnect(entityObjectToDrag, terrainSphere);
						GameStateController.CAState = GameStateController.CA_IDLE;
					}
					break;
					

				default:
					break;
				}
				break;
			default:
				break;
			}

			break;
			
		
		default:
			break;
		}

		if (GameStateController.currentState == GameStateController.IDEL_TESTING) {

			mouseHighlightController.checkMousePicking(entityObjects);
			highlightedCircle.setColour(highlightedCircle.HIGHLIGHT_DEFAULT);
			EntityObject tempEntityObject = mouseHighlightController.getCurrentEntityObject();
			if (tempEntityObject != null && Keyboard.isKeyDown(Keyboard.KEY_G)) {
				entityObjectToDrag = tempEntityObject;
				this.showCircle = true;
				GameStateController.setCurrentState(GameStateController.DRAGGING_ENTITY_OBJECT);
			} else {
				this.showCircle = mouseHighlightController.isShowCircle();
			}

			// testing
			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				entityObjectToDrag = gameEntityObjectsController
						.createEntityObjectAndAddToList(EntityModelDataBase.lowGrass1Model, ObjectsNetwork.lowGrass1);
				entityObjectToDrag.getEntity().randomRotZ();
				// System.out.println(entityObjectToDrag.getEntity().getRotZ());
				this.showCircle = true;
				GameStateController.setCurrentState(GameStateController.DRAGGING_ENTITY_OBJECT);
			}

		} else if (GameStateController.currentState == GameStateController.DRAGGING_ENTITY_OBJECT) {
			// highlightedCircle.setColour(highlightedCircle.HIGHLIGHT_GOOD);
			if (entityObjectToDrag != null) {
				// System.out.println("should be dragging");
				mouseDraggingController.drag(entityObjectToDrag);

				if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
					GameStateController.setCurrentState(GameStateController.IDEL_TESTING);
					// System.out.println(entityObjectToDrag.getRotZ());
					this.showCircle = false;
					TerrainFace currentFace = entityObjectToDrag.getFace(terrainSphere);
					colourController.addObjectToFace(currentFace, entityObjectToDrag.getObjectData(),
							entityObjectToDrag.getObjectData().getObjectInitAmount(),
							entityObjectToDrag.getObjectData().isAffectTerrainColour(), 1);
					colourController.updateColourVBO(loader);
				}
			}
		}
	}

	public List<GuiTexture> getGuisToDisplay() { //not good method! no levels
		List<GuiTexture> guisToDisplay = guiEventController.getLevel2Panel();
		List<GuiObjectUnit> guiObjectUnits = guiEventController.getLevel2ObjectUnits();
		for (int i = 0; i < guiObjectUnits.size(); i++) {
			GuiObjectUnit currentObject = guiObjectUnits.get(i);
			List<GuiTexture> objectGuiTextures = currentObject.getGuiTextures();
			//System.out.println(objectGuiTextures.size());
			for (int j = 0; j < objectGuiTextures.size(); j++) {
				guisToDisplay.add(objectGuiTextures.get(j));
			}
		}
		return guisToDisplay;
	}

	public List<GuiSphereTexture> getGuisSphere3D() { //not good method! no levels
		List<GuiSphereTexture> guiSphereTextures = guiEventController.getLevel1Spheres();
		List<GuiObjectUnit> guiObjectUnits = guiEventController.getLevel2ObjectUnits();
		for (int i = 0; i < guiObjectUnits.size(); i++) {
			GuiObjectUnit currentObject = guiObjectUnits.get(i);
			List<GuiSphereTexture> objectGuiSphereTextures = currentObject.getGuiSphereTextures();
			for (int j = 0; j < objectGuiSphereTextures.size(); j++) {
				guiSphereTextures.add(objectGuiSphereTextures.get(j));
			}
		}
		return guiSphereTextures;
		// return guiController.getGuisSphere3D();
	}
	
	public List<GUIText> geGuiTexts(){ //not good method! no levels
		List<GUIText> guiTexts = guiEventController.getLevel2Texts();
		List<GuiObjectUnit> guiObjectUnits = guiEventController.getLevel2ObjectUnits();
		for (int i = 0; i < guiObjectUnits.size(); i++) {
			GuiObjectUnit currentObject = guiObjectUnits.get(i);
			GUIText objectGuiText = currentObject.getNameText();
			guiTexts.add(objectGuiText);
		}
		//System.out.println(guiTexts.size());
		return guiTexts;
	}
	
	public List<GUIText> getLevel2Texts(){
		List<GUIText> guiTexts = guiEventController.getLevel2Texts();
		List<GuiObjectUnit> guiObjectUnits = guiEventController.getLevel2ObjectUnits();
		for (int i = 0; i < guiObjectUnits.size(); i++) {
			GuiObjectUnit currentObject = guiObjectUnits.get(i);
			GUIText objectGuiText = currentObject.getNameText();
			guiTexts.add(objectGuiText);
		}
		//System.out.println(guiTexts.size());
		return guiTexts;
	}
	
	public List<GuiSphereTexture> getLevel1Spheres() { 
		List<GuiSphereTexture> guiSpheres = guiEventController.getLevel1Spheres();
		return guiSpheres;
	}
	
	public List<GuiSphereTexture> getLevel2Spheres() { 
		List<GuiSphereTexture> guiSpheres = new ArrayList<GuiSphereTexture>();
		List<GuiObjectUnit> guiObjectUnits = guiEventController.getLevel2ObjectUnits();
		for (int i = 0; i < guiObjectUnits.size(); i++) {
			GuiObjectUnit currentObject = guiObjectUnits.get(i);
			List<GuiSphereTexture> objectGuiSphereTextures = currentObject.getGuiSphereTextures();
			for (int j = 0; j < objectGuiSphereTextures.size(); j++) {
				guiSpheres.add(objectGuiSphereTextures.get(j));
			}
		}
		return guiSpheres;
	}
	
	public List<GuiTexture> getLevel2Panel(){
		List<GuiTexture> guiTextures = guiEventController.getLevel2Panel();
		return guiTextures;
	}
	
	public List<GuiTexture> getLevel2textures(){
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		List<GuiObjectUnit> guiObjectUnits = guiEventController.getLevel2ObjectUnits();
		for (int i = 0; i < guiObjectUnits.size(); i++) {
			GuiObjectUnit currentObject = guiObjectUnits.get(i);
			List<GuiTexture> objectGuiTextures = currentObject.getGuiTextures();
			for (int j = 0; j < objectGuiTextures.size(); j++) {
				guiTextures.add(objectGuiTextures.get(j));
			}
		}
		return guiTextures;
	}
	
	
	
	
	
	public List<GuiObjectUnit> getGuiObjectUnit(){ //not good method! no levels
		return guiEventController.getLevel2ObjectUnits();
	}

	public boolean isShowCircle() {
		return showCircle;
	}
	
	
	public void updateMouse(){
		if (Mouse.isButtonDown(0)) {
			mouseCurrent = true;
		}
		else{
			mouseCurrent = false;
		}
		if (mouseCurrent == true && mousePrevious == false) {
			mouseClicked = true;
			//System.out.println("click");
		}
		else{
			mouseClicked = false;
		}
		mousePrevious = mouseCurrent;
		
	}

}
