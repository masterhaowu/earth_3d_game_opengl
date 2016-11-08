package guiEvents;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import entityObjects.EntityObject;
import entityObjects.ObjectData;
import fontMeshCreator.GUIText;
import guiDataBase.GuiData;
import guis.GuiObjectUnit;
import guis.GuiSphereTexture;
import guis.GuiTexture;
import mainGame.GameEntityObjectsController;
import mainGame.GameStateController;
import models.TexturedModel;
import mouse.MousePickerSphere;

public class GuiCreationAnimalEventController {
	
	private GameEntityObjectsController gameEntityObjectsController;
	private MousePickerSphere picker;
	
	
	private ObjectData entityObjectData;
	private TexturedModel texturedModel;
	private EntityObject entityObjectToReturn;

	public GuiCreationAnimalEventController(GameEntityObjectsController gameEntityObjectsController, MousePickerSphere picker) {
		this.gameEntityObjectsController = gameEntityObjectsController;
		this.picker = picker;
	}

	
	public boolean updateCreationAnimalState(Boolean mouseClicked, GuiData guiData, List<GuiSphereTexture> level1Spheres,
			List<GuiTexture> level2Panel, List<GuiObjectUnit> level2ObjectUnits, List<GUIText> level2Texts) {
		
		if (GuiEventTools.checkSingleSphere(guiData.leftSphere, picker) && Mouse.isButtonDown(0)
				&& guiData.leftSphere.getCurrentState() == 0) {
			guiData.leftSphere.setNextState(1);
			GuiEventTools.setToolBarStates(guiData, 3);
			GameStateController.gameModeState = GameStateController.RESEARCH_ANIMAL_MODE;
		} else if (GuiEventTools.checkSingleSphere(guiData.rightSphere, picker) && Mouse.isButtonDown(0)
				&& guiData.rightSphere.getCurrentState() == 1) {
			guiData.rightSphere.setNextState(0);
			GuiEventTools.setToolBarStates(guiData, 0);
			GameStateController.gameModeState = GameStateController.CREATION_TERRAIN_MODE;
		} else if (GuiEventTools.checkSingleSphere(guiData.sphereMiddle, picker) && mouseClicked
				&& GameStateController.CAState != GameStateController.CA_HERBIVORE) {
			mouseClicked = false;
			level2Panel.clear();
			GameStateController.CAState = GameStateController.CA_HERBIVORE;
			level2Panel.add(guiData.panelBackground);

			level2ObjectUnits.clear();

			for (int i = 0; i < GuiData.OBJECT_ROWS; i++) {
				for (int j = 0; j < GuiData.OBJECT_COLS; j++) {
					// guiObjectUnits.add(guiData.guiObjects[i][j]);
					int index = i * GuiData.OBJECT_ROWS + j;
					if (index < guiData.guiDataHerbivores.guiObjectUnits.size()) {
						GuiObjectUnit currentUnit = guiData.guiDataHerbivores.guiObjectUnits.get(index);
						currentUnit.updatePositionAndScale(guiData.guiObjectUnitPositions[i][j],
								guiData.guiObjectUnitScale);
						level2ObjectUnits.add(currentUnit);
					}
				}
			}
		} else if (GuiEventTools.checkSingleSphere(guiData.sphereRight2, picker) && mouseClicked
				&& GameStateController.CAState != GameStateController.CA_CARNIVORE) {
			mouseClicked = false;
			level2Panel.clear();
			GameStateController.CAState = GameStateController.CA_CARNIVORE;
			level2Panel.add(guiData.panelBackground);

			level2ObjectUnits.clear();

			for (int i = 0; i < GuiData.OBJECT_ROWS; i++) {
				for (int j = 0; j < GuiData.OBJECT_COLS; j++) {
					// guiObjectUnits.add(guiData.guiObjects[i][j]);
					int index = i * GuiData.OBJECT_ROWS + j;
					if (index < guiData.guiDataCarnivores.guiObjectUnits.size()) {
						GuiObjectUnit currentUnit = guiData.guiDataCarnivores.guiObjectUnits.get(index);
						currentUnit.updatePositionAndScale(guiData.guiObjectUnitPositions[i][j],
								guiData.guiObjectUnitScale);
						level2ObjectUnits.add(currentUnit);
					}
				}
			}
		}

		switch (GameStateController.CAState) {
		case GameStateController.CA_HERBIVORE:
			if (GuiEventTools.checkSingleSphere(guiData.sphereMiddle, picker) && mouseClicked) {
				mouseClicked = false;
				GameStateController.CAState = GameStateController.CA_IDLE;
				level2ObjectUnits.clear();
				level2Panel.clear();
			}
			for (int i = 0; i < level2ObjectUnits.size(); i++) {
				if (GuiEventTools.checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground(), picker) && mouseClicked) {
					mouseClicked = false;

					entityObjectData = level2ObjectUnits.get(i).getObjectData();
					texturedModel = level2ObjectUnits.get(i).getModel();

					entityObjectToReturn = gameEntityObjectsController
							.createEntityObjectAndAddToList(texturedModel, entityObjectData);
					entityObjectToReturn.getEntity().randomRotationOnSphere();
					GameStateController.CAState = GameStateController.CA_HERBIVORE_DRAGGING;
					// guiObjectUnits.clear();
				}
			}
			break;

		case GameStateController.CA_HERBIVORE_DRAGGING:
			level2ObjectUnits.clear();
			level2Panel.clear();
			break;
		case GameStateController.CA_CARNIVORE:
			if (GuiEventTools.checkSingleSphere(guiData.sphereRight2, picker) && mouseClicked) {
				mouseClicked = false;
				GameStateController.CAState = GameStateController.CA_IDLE;
				level2ObjectUnits.clear();
				level2Panel.clear();
			}
			for (int i = 0; i < level2ObjectUnits.size(); i++) {
				if (GuiEventTools.checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground(), picker) && mouseClicked) {
					mouseClicked = false;

					entityObjectData = level2ObjectUnits.get(i).getObjectData();
					texturedModel = level2ObjectUnits.get(i).getModel();

					entityObjectToReturn = gameEntityObjectsController
							.createEntityObjectAndAddToList(texturedModel, entityObjectData);
					entityObjectToReturn.getEntity().randomRotationOnSphere();
					GameStateController.CAState = GameStateController.CA_CARNIVORE_DRAGGING;
					// guiObjectUnits.clear();
				}
			}
			break;

		case GameStateController.CA_CARNIVORE_DRAGGING:
			level2ObjectUnits.clear();
			level2Panel.clear();
			break;

		default:
			break;
		}

		
		return mouseClicked;
	}
	
	
	
	public ObjectData getEntityObjectData() {
		return entityObjectData;
	}

	public TexturedModel getTexturedModel() {
		return texturedModel;
	}

	public EntityObject getEntityObjectToReturn() {
		return entityObjectToReturn;
	}
	
	

}
