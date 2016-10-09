package mouse;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entityObjects.EntityObject;
import renderEngine.Loader;
import terrainsSphere.ColourController;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainObject;
import terrainsSphere.TerrainSphere;

public class MouseHighlightController {
	
	public TerrainObject highlightObject = new TerrainObject(0, 2, new Vector3f(1, 1, 1));
	
	
	private MousePickerSphere picker;
	private ColourController colourController;
	private Loader loader;
	private TerrainSphere terrainSphere;
	private TerrainFace currentFace;
	private TerrainFace previousFace;
	private boolean changeFace;
	private boolean showCircle;
	private HighlightedCircle highlightedCircle;
	
	private EntityObject currentEntityObject;
	

	
	
	public MouseHighlightController(MousePickerSphere picker, ColourController colourController, Loader loader, HighlightedCircle highlightedCircle, TerrainSphere terrainSphere){
		this.picker = picker;
		this.colourController = colourController;
		this.loader = loader;
		this.currentFace = picker.getCurrentTerrainFace();
		this.previousFace = picker.getCurrentTerrainFace();
		this.changeFace = true;
		this.highlightedCircle = highlightedCircle;
		this.terrainSphere = terrainSphere;
		this.showCircle = false;
		
	}
	

	public void checkMousePicking(List<EntityObject> entityObjects) {
		
		//System.out.println("here");
		currentFace = picker.getCurrentTerrainFace();
		// Entity currentEntity =
		// picker.checkSelectedEntitySimpleMethod(entities);
		// Entity currentEntity = picker.checkSelectedEntityOBBMethod(entities);
		// Entity currentEntity =
		// picker.checkSelectedEntityCenterDistanceMethod(entities);
		currentEntityObject = picker.checkSelectedEntityAllMeshMethodWithThreshold(entityObjects, 10);
		
		

		if (currentEntityObject != null) {
			Entity currentEntity = currentEntityObject.getEntity();
			highlightedCircle.setPosition(currentEntity.getPosition());
			highlightedCircle.setRotX(currentEntity.getRotX());
			highlightedCircle.setRotY(currentEntity.getRotY());
			highlightedCircle.calculateCirclePositionOnSphere(terrainSphere);
			highlightedCircle.updatePositionVBO(loader);
			showCircle = true;
		}
		else{
			showCircle = false;
		}
		// System.out.println(currentEntity.getModel().getRawModel().);
		// System.out.println(currentEntity.getModel().getRawModel().getMax());

		// currentFace =
		// terrainSphere.getTargetFacePlucker(player.getPolar().y,player.getPolar().z);
		// terrainSphere.updateColourVBO(loader);

		// currentFace.getNeighorVerticesDefault().get(0).setColour(new
		// Vector3f(0, 0, 0));
		// terrainSphere.setVertexColour(currentFace.getNeighorVerticesDefault().get(0),
		// new Vector3f(0, 0, 0));
		// terrainSphere.updateColourVBO(loader);

		if (currentFace != previousFace) {
			changeFace = true;

		}

		if (changeFace && currentFace != null && previousFace != null) {
			//System.out.println("here");
			colourController.simpleResetColour(previousFace, loader);
			colourController.addObjectToFace(currentFace, highlightObject, loader);
			// currentFace =
			// terrainSphere.getTargetFacePlucker(player.getPolar().y,player.getPolar().z);
			colourController.updateColourVBO(loader);

			changeFace = false;
		}

		previousFace = currentFace;

	}


	public boolean isShowCircle() {
		return showCircle;
	}


	public EntityObject getCurrentEntityObject() {
		return currentEntityObject;
	}


	
	
	
	

}
