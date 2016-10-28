package mainGame;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entityObjects.EntityObject;
import entityObjects.ObjectsNetwork;
import guis.GuiController;
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

	private GuiController guiController;
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
		this.guiController = new GuiController(loader, picker);
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
		guiController.update(mouseClicked);
		switch (GameStateController.currentState) {
		case GameStateController.PLAY_MODE_IDLE:

			switch (GameStateController.gameModeState) {
			case GameStateController.CREATION_TERRAIN_MODE:

				switch (GameStateController.CTState) {
				case GameStateController.CT_TERRAIN_DRAGGING:
					//System.out.println("here");
					currentFace = picker.getCurrentTerrainFace();
					// colourController.addObjectToFace(face, object, loader);
					/*
					colourController.addObjectToFace(currentFace, guiController.getTerrainToReturn(),
							guiController.getTerrainToReturn().getObjectInitAmount(),
							guiController.getTerrainToReturn().isAffectTerrainColour(), 1);
					*/
					/*
					colourController.restoreFaceColour(previousFace);
					
					colourController.addObjectToFacePreview(currentFace, guiController.getTerrainToReturn(),
							guiController.getTerrainToReturn().getObjectInitAmount());
					colourController.updateColourVBO(loader);
					*/
					if (Mouse.isButtonDown(0)) {
						colourController.setTerrain(currentFace, guiController.getTerrainToReturn());
						colourController.setPreviewTerrain(currentFace, guiController.getTerrainToReturn());
						//System.out.println("here");
						colourController.updateColourVBO(loader);
						
					}
					
					colourController.restoreFaceColour(previousFace);
					colourController.setPreviewTerrain(currentFace, guiController.getTerrainToReturn());
					colourController.updateColourVBO(loader);
					
					
					
					previousFace = currentFace;
					
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
						.createEntityObjectAndAddToList(EntityObjectModelData.lowGrass1Model, ObjectsNetwork.lowGrass1);
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

	public List<GuiTexture> getGuisToDisplay() {
		List<GuiTexture> guisToDisplay = guiController.getGuisToDisplay();
		List<GuiObjectUnit> guiObjectUnits = guiController.getGuiObjectUnits();
		for (int i = 0; i < guiObjectUnits.size(); i++) {
			GuiObjectUnit currentObject = guiObjectUnits.get(i);
			List<GuiTexture> objectGuiTextures = currentObject.getGuiTextures();
			for (int j = 0; j < objectGuiTextures.size(); j++) {
				guisToDisplay.add(objectGuiTextures.get(j));
			}
		}
		return guisToDisplay;
	}

	public List<GuiSphereTexture> getGuisSphere3D() {
		List<GuiSphereTexture> guiSphereTextures = guiController.getGuisSphere3D();
		List<GuiObjectUnit> guiObjectUnits = guiController.getGuiObjectUnits();
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
