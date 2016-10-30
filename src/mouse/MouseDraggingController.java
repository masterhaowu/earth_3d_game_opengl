package mouse;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entityObjects.EntityObject;
import renderEngine.Loader;
import terrainsSphere.ColourController;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;

public class MouseDraggingController {

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

	public MouseDraggingController(MousePickerSphere picker, ColourController colourController, Loader loader,
			HighlightedCircle highlightedCircle, TerrainSphere terrainSphere) {

		this.picker = picker;
		this.colourController = colourController;
		this.loader = loader;
		this.currentFace = picker.getCurrentTerrainFace();
		this.previousFace = picker.getCurrentTerrainFace();
		this.changeFace = true;
		this.highlightedCircle = highlightedCircle;
		this.terrainSphere = terrainSphere;
		// this.showCircle = false;

	}

	public void drag(EntityObject entityObject) {

		Vector3f dragPos = picker.getCurrentTerrainPoint();
		// System.out.println(dragPos);
		if (dragPos != null) {
			if (entityObject.isMultipleEntities()) {
				for (Entity entity : entityObject.getEntityList()){
					entity.setPosition(dragPos);
					entity.updateRotation();
				}
			} else {
				Entity entity = entityObject.getEntity();
				
				entity.setPosition(dragPos);
				entity.updateRotation();
			}
			highlightedCircle.setPosition(entityObject.getPosition());
			highlightedCircle.setRotX(entityObject.getRotX());
			highlightedCircle.setRotY(entityObject.getRotY());
			highlightedCircle.calculateCirclePositionOnSphere(terrainSphere);
			highlightedCircle.updatePositionVBO(loader);

			if (!entityObject.checkObjectCanExistOnTerrain(terrainSphere)) {
				highlightedCircle.setColour(highlightedCircle.HIGHLIGHT_FAIL);
			} else if (entityObject.checkObjectHasFoodAround(terrainSphere)) {
				highlightedCircle.setColour(highlightedCircle.HIGHLIGHT_GOOD);
			} else {
				highlightedCircle.setColour(highlightedCircle.HIGHLIGHT_WARNING);
			}
			// showCircle = true;
		}

	}

}
