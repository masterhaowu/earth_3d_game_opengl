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

public class GuiCreationTerrainEventController {
	
	private GameEntityObjectsController gameEntityObjectsController;
	private MousePickerSphere picker;
	
	
	private ObjectData entityObjectData;
	private TexturedModel texturedModel;
	private EntityObject entityObjectToReturn;

	public GuiCreationTerrainEventController(GameEntityObjectsController gameEntityObjectsController, MousePickerSphere picker) {
		this.gameEntityObjectsController = gameEntityObjectsController;
		this.picker = picker;
	}

	public boolean updateCreationTerrainState(Boolean mouseClicked, GuiData guiData, List<GuiSphereTexture> level1Spheres,
			List<GuiTexture> level2Panel, List<GuiObjectUnit> level2ObjectUnits, List<GUIText> level2Texts) {
		

		
		if (GuiEventTools.checkSingleSphere(guiData.leftSphere, picker) && Mouse.isButtonDown(0)
				&& guiData.leftSphere.getCurrentState() == 0) {
			guiData.leftSphere.setNextState(1);
			GuiEventTools.setToolBarStates(guiData, 2);
			GameStateController.gameModeState = GameStateController.RESEARCH_TERRAIN_MODE;
		} else if (GuiEventTools.checkSingleSphere(guiData.rightSphere, picker) && Mouse.isButtonDown(0)
				&& guiData.rightSphere.getCurrentState() == 0) {
			guiData.rightSphere.setNextState(1);
			GuiEventTools.setToolBarStates(guiData, 1);
			GameStateController.gameModeState = GameStateController.CREATION_ANIMAL_MODE;
		}  else if (GuiEventTools.checkSingleSphere(guiData.sphereRight1, picker) && Mouse.isButtonDown(0) && mouseClicked
				&& GameStateController.CTState != GameStateController.CT_GRASS) {
			mouseClicked = false;
			level2Panel.clear();
			GameStateController.CTState = GameStateController.CT_GRASS;
			level2Panel.add(guiData.panelBackground);

			level2ObjectUnits.clear();

			for (int i = 0; i < GuiData.OBJECT_ROWS; i++) {
				for (int j = 0; j < GuiData.OBJECT_COLS; j++) {
					// guiObjectUnits.add(guiData.guiObjects[i][j]);
					int index = i * GuiData.OBJECT_ROWS + j;
					if (index < guiData.guiDataGrasses.guiObjectUnits.size()) {
						GuiObjectUnit currentUnit = guiData.guiDataGrasses.guiObjectUnits.get(index);
						currentUnit.updatePositionAndScale(guiData.guiObjectUnitPositions[i][j],
								guiData.guiObjectUnitScale);
						level2ObjectUnits.add(currentUnit);
					}
				}
			}

		} else if (GuiEventTools.checkSingleSphere(guiData.sphereRight3, picker) && Mouse.isButtonDown(0) && mouseClicked
				&& GameStateController.CTState != GameStateController.CT_TREE) {
			mouseClicked = false;
			level2Panel.clear();
			GameStateController.CTState = GameStateController.CT_TREE;
			
			level2Panel.add(guiData.panelBackground);

			level2ObjectUnits.clear();

			for (int i = 0; i < GuiData.OBJECT_ROWS; i++) {
				for (int j = 0; j < GuiData.OBJECT_COLS; j++) {
					// guiObjectUnits.add(guiData.guiObjects[i][j]);
					int index = i * GuiData.OBJECT_ROWS + j;
					if (index < guiData.guiDataTrees.guiObjectUnits.size()) {
						GuiObjectUnit currentUnit = guiData.guiDataTrees.guiObjectUnits.get(index);
						currentUnit.updatePositionAndScale(guiData.guiObjectUnitPositions[i][j],
								guiData.guiObjectUnitScale);
						level2ObjectUnits.add(currentUnit);
					}
				}
			}

		}

		switch (GameStateController.CTState) {
		case GameStateController.CT_IDLE:
			
			break;
		
		case GameStateController.CT_GRASS:

			if (GuiEventTools.checkSingleSphere(guiData.sphereRight3, picker) && mouseClicked) {
				mouseClicked = false;
				GameStateController.CTState = GameStateController.CT_IDLE;
				level2ObjectUnits.clear();
				level2Panel.clear();
			}
			for (int i = 0; i < level2ObjectUnits.size(); i++) {
				if (GuiEventTools.checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground(), picker) && mouseClicked) {
					mouseClicked = false;

					entityObjectData = level2ObjectUnits.get(i).getObjectData();
					texturedModel = level2ObjectUnits.get(i).getModel();

					entityObjectToReturn = gameEntityObjectsController.createEntityObjectAndAddToList(texturedModel,
							entityObjectData);
					entityObjectToReturn.getEntity().randomRotationOnSphere();
					GameStateController.CTState = GameStateController.CT_GRASS_DRAGGING;
				}
			}
			break;

		case GameStateController.CT_GRASS_DRAGGING:
			level2ObjectUnits.clear();
			level2Panel.clear();
			break;

		case GameStateController.CT_TREE:

			if (GuiEventTools.checkSingleSphere(guiData.sphereRight3, picker) && mouseClicked) {
				mouseClicked = false;
				GameStateController.CTState = GameStateController.CT_IDLE;
				level2ObjectUnits.clear();
				level2Panel.clear();
			}
			for (int i = 0; i < level2ObjectUnits.size(); i++) {
				if (GuiEventTools.checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground(), picker) && mouseClicked) {
					mouseClicked = false;
					// dataTypeToReturn = RETURN_TERRAIN;
					// terrainToReturn =
					// guiObjectUnits.get(i).getObjectData();
					// entityObjectToReturn =
					// guiObjectUnits.get(i).getEntityObject();
					entityObjectData = level2ObjectUnits.get(i).getObjectData();
					texturedModel = level2ObjectUnits.get(i).getModel();

					entityObjectToReturn = gameEntityObjectsController.createEntityObjectAndAddToList(texturedModel,
							entityObjectData);
					entityObjectToReturn.getEntity().randomRotationOnSphere();
					GameStateController.CTState = GameStateController.CT_TREE_DRAGGING;
					// guiObjectUnits.clear();
				}
			}
			break;

		case GameStateController.CT_TREE_DRAGGING:
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
