package mouse;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entityObjects.EntityObject;
import entityObjects.ObjectsNetwork;
import mainGame.EntityObjectModelData;
import mainGame.GameEntityObjectsController;
import mainGame.GameStateController;
import renderEngine.Loader;
import terrainsSphere.ColourController;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;

public class MouseController {

	private MousePickerSphere picker;
	private ColourController colourController;
	private Loader loader;
	private TerrainSphere terrainSphere;
	private TerrainFace currentFace;
	private TerrainFace previousFace;
	private boolean changeFace;
	private boolean showCircle;
	private HighlightedCircle highlightedCircle;

	private EntityObject entityObjectToDrag;
	private EntityObject entityObjectToAdd;

	private MouseDraggingController mouseDraggingController;
	private MouseHighlightController mouseHighlightController;
	
	private GameEntityObjectsController gameEntityObjectsController;

	public MouseController(MousePickerSphere picker, ColourController colourController, Loader loader,
			HighlightedCircle highlightedCircle, TerrainSphere terrainSphere, GameEntityObjectsController gameEntityObjectsController) {

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

	public void updateMouse(List<EntityObject> entityObjects) {
		picker.updateOptimized();
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
				entityObjectToDrag = gameEntityObjectsController.createEntityObjectAndAddToList(EntityObjectModelData.lowGrass1Model, ObjectsNetwork.lowGrass1);
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

	public boolean isShowCircle() {
		return showCircle;
	}

}
