package guiEvents;

import java.util.ArrayList;
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
import renderEngine.Loader;

public class GuiEventController {
	public static final int RETURN_NULL = 0;
	public static final int RETURN_TERRAIN = 1;
	public static final int RETURN_OBJECT = 2;
	public int dataTypeToReturn = 0;

	private GameEntityObjectsController gameEntityObjectsController;

	private GuiData guiData;
	private MousePickerSphere picker;
	// private List<GuiTexture> guis;
	// private List<GuiTexture> guisToDisplay;
	private List<GuiSphereTexture> level1Spheres;
	private List<GuiTexture> level2Panel;
	private List<GuiObjectUnit> level2ObjectUnits;

	private List<GUIText> level2Texts;

	private ObjectData terrainToReturn;
	private ObjectData entityObjectData;
	private TexturedModel texturedModel;
	private EntityObject entityObjectToReturn;

	private boolean clickDisabled = false;

	public GuiEventController(Loader loader, MousePickerSphere picker,
			GameEntityObjectsController gameEntityObjectsController) {
		this.gameEntityObjectsController = gameEntityObjectsController;
		// this.guis = new ArrayList<GuiTexture>();
		this.picker = picker;
		// this.guisToDisplay = new ArrayList<GuiTexture>();
		this.level1Spheres = new ArrayList<GuiSphereTexture>();
		this.level2Panel = new ArrayList<GuiTexture>();
		this.level2ObjectUnits = new ArrayList<GuiObjectUnit>();
		this.level2Texts = new ArrayList<GUIText>();
		guiData = new GuiData(loader);

		

		GameStateController.CTState = GameStateController.CT_IDLE;

	}

	public GuiSphereTexture checkMouseoverSphere() { // this method do not check
														// spheres in guiUnits

		Vector2f mousePos = picker.getNormalizedXY();
		/*
		 * for(GuiTexture gui:guisToDisplay){ gui.setHighlighted(false); float
		 * xDiff = Math.abs(mousePos.x - gui.getPosition().x); float yDiff =
		 * Math.abs(mousePos.y - gui.getPosition().y); if (xDiff <=
		 * gui.getScale().x && yDiff <= gui.getScale().y) {
		 * gui.setHighlighted(true); return gui; //System.out.println("gg!"); }
		 * }
		 */
		for (GuiSphereTexture gui : level1Spheres) {
			gui.setHighlighted(false);
			float xDiff = Math.abs(mousePos.x - gui.getPosition().x);
			float yDiff = Math.abs(mousePos.y - gui.getPosition().y);
			if (xDiff <= gui.getScale().x && yDiff <= gui.getScale().y) {
				gui.setHighlighted(true);
				return gui;
				// System.out.println("gg!");
			}
		}
		return null;
	}

	public boolean checkSingleSphere(GuiSphereTexture gui) {
		Vector2f mousePos = picker.getNormalizedXY();
		gui.setHighlighted(false);
		float xDiff = Math.abs(mousePos.x - gui.getPosition().x);
		float yDiff = Math.abs(mousePos.y - gui.getPosition().y);
		if (xDiff <= gui.getScale().x && yDiff <= gui.getScale().y) {
			gui.setHighlighted(true);
			return true;
			// System.out.println("gg!");
		}
		return false;
	}

	public boolean checkSingleGui(GuiTexture gui) {
		Vector2f mousePos = picker.getNormalizedXY();
		gui.setHighlighted(false);
		float xDiff = Math.abs(mousePos.x - gui.getPosition().x);
		float yDiff = Math.abs(mousePos.y - gui.getPosition().y);
		if (xDiff <= gui.getScale().x && yDiff <= gui.getScale().y) {
			gui.setHighlighted(true);
			return true;
			// System.out.println("gg!");
		}
		return false;
	}

	public boolean update(boolean mouseClicked) {
		dataTypeToReturn = RETURN_NULL;
		level1Spheres.clear();
		// guisToDisplay.clear();
		level2Texts.clear();
		// guisToDisplay.add(guiData.planetCircle);
		// guisToDisplay.add(guiData.planetIcon);
		level1Spheres.add(guiData.leftSphere);
		level1Spheres.add(guiData.rightSphere);

		level1Spheres.add(guiData.sphereMiddle);

		level1Spheres.add(guiData.sphereLeft1);
		level1Spheres.add(guiData.sphereLeft2);
		level1Spheres.add(guiData.sphereLeft3);
		level1Spheres.add(guiData.sphereRight1);
		level1Spheres.add(guiData.sphereRight2);
		level1Spheres.add(guiData.sphereRight3);
		/*
		 * if (!guisPanel.isEmpty()) { for (int i = 0; i < guisPanel.size();
		 * i++) { guisToDisplay.add(guisPanel.get(i)); // System.out.println(i);
		 * } }
		 */

		switch (GameStateController.currentState) {
		case GameStateController.PLAY_MODE_IDLE:
			switch (GameStateController.gameModeState) {
			case GameStateController.CREATION_TERRAIN_MODE:
				if (checkSingleSphere(guiData.leftSphere) && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 0) {
					guiData.leftSphere.setNextState(1);
					setToolBarStates(2);
					GameStateController.gameModeState = GameStateController.RESEARCH_TERRAIN_MODE;
				} else if (checkSingleSphere(guiData.rightSphere) && Mouse.isButtonDown(0)
						&& guiData.rightSphere.getCurrentState() == 0) {
					guiData.rightSphere.setNextState(1);
					setToolBarStates(1);
					GameStateController.gameModeState = GameStateController.CREATION_ANIMAL_MODE;
				} else if (checkSingleSphere(guiData.sphereLeft3) && Mouse.isButtonDown(0) && !clickDisabled
						&& GameStateController.CTState != GameStateController.CT_TERRAIN_TYPE) {

					level2Panel.clear();
					GameStateController.CTState = GameStateController.CT_TERRAIN_TYPE;
					clickDisabled = true;
					level2Panel.add(guiData.panelBackground);

					level2ObjectUnits.clear();

					for (int i = 0; i < GuiData.OBJECT_ROWS; i++) {
						for (int j = 0; j < GuiData.OBJECT_COLS; j++) {
							// guiObjectUnits.add(guiData.guiObjects[i][j]);
							int index = i * GuiData.OBJECT_ROWS + j;
							if (index < guiData.guiDataTerrains.guiObjectUnits.size()) {
								GuiObjectUnit currentUnit = guiData.guiDataTerrains.guiObjectUnits.get(index);
								currentUnit.updatePositionAndScale(guiData.guiObjectUnitPositions[i][j],
										guiData.guiObjectUnitScale);
								level2ObjectUnits.add(currentUnit);
							}
						}
					}

				} else if (checkSingleSphere(guiData.sphereRight1) && Mouse.isButtonDown(0) && mouseClicked
						&& GameStateController.CTState != GameStateController.CT_GRASS) {
					mouseClicked = false;
					level2Panel.clear();
					GameStateController.CTState = GameStateController.CT_GRASS;
					clickDisabled = true;
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

				} else if (checkSingleSphere(guiData.sphereRight3) && Mouse.isButtonDown(0) && mouseClicked
						&& GameStateController.CTState != GameStateController.CT_TREE) {
					mouseClicked = false;
					level2Panel.clear();
					GameStateController.CTState = GameStateController.CT_TREE;
					clickDisabled = true;
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
					if (!Mouse.isButtonDown(0)) {
						clickDisabled = false;
					}
					break;
				case GameStateController.CT_TERRAIN_TYPE:
					if (!Mouse.isButtonDown(0)) {
						clickDisabled = false;
					}
					if (checkSingleSphere(guiData.sphereLeft3) && Mouse.isButtonDown(0) && !clickDisabled) {
						GameStateController.CTState = GameStateController.CT_IDLE;
						clickDisabled = true;
						level2ObjectUnits.clear();
						level2Panel.clear();
					}

					for (int i = 0; i < level2ObjectUnits.size(); i++) {
						if (checkSingleSphere(level2ObjectUnits.get(i).getComfirm()) && Mouse.isButtonDown(0)) {
							dataTypeToReturn = RETURN_TERRAIN;
							terrainToReturn = level2ObjectUnits.get(i).getObjectData();
							GameStateController.CTState = GameStateController.CT_TERRAIN_DRAGGING;
							// guiObjectUnits.clear();
						}
					}
					break;

				case GameStateController.CT_TERRAIN_DRAGGING:
					level2ObjectUnits.clear();
					level2Panel.clear();
					break;

				case GameStateController.CT_GRASS:

					if (checkSingleSphere(guiData.sphereRight3) && mouseClicked) {
						mouseClicked = false;
						GameStateController.CTState = GameStateController.CT_IDLE;
						level2ObjectUnits.clear();
						level2Panel.clear();
					}
					for (int i = 0; i < level2ObjectUnits.size(); i++) {
						if (checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground()) && mouseClicked) {
							mouseClicked = false;

							entityObjectData = level2ObjectUnits.get(i).getObjectData();
							texturedModel = level2ObjectUnits.get(i).getModel();

							entityObjectToReturn = gameEntityObjectsController
									.createEntityObjectAndAddToList(texturedModel, entityObjectData);
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

					if (checkSingleSphere(guiData.sphereRight3) && mouseClicked) {
						mouseClicked = false;
						GameStateController.CTState = GameStateController.CT_IDLE;
						level2ObjectUnits.clear();
						level2Panel.clear();
					}
					for (int i = 0; i < level2ObjectUnits.size(); i++) {
						if (checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground()) && mouseClicked) {
							mouseClicked = false;
							// dataTypeToReturn = RETURN_TERRAIN;
							// terrainToReturn =
							// guiObjectUnits.get(i).getObjectData();
							// entityObjectToReturn =
							// guiObjectUnits.get(i).getEntityObject();
							entityObjectData = level2ObjectUnits.get(i).getObjectData();
							texturedModel = level2ObjectUnits.get(i).getModel();

							entityObjectToReturn = gameEntityObjectsController
									.createEntityObjectAndAddToList(texturedModel, entityObjectData);
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
				break;

			case GameStateController.CREATION_ANIMAL_MODE:
				if (checkSingleSphere(guiData.leftSphere) && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 0) {
					guiData.leftSphere.setNextState(1);
					setToolBarStates(3);
					GameStateController.gameModeState = GameStateController.RESEARCH_ANIMAL_MODE;
				} else if (checkSingleSphere(guiData.rightSphere) && Mouse.isButtonDown(0)
						&& guiData.rightSphere.getCurrentState() == 1) {
					guiData.rightSphere.setNextState(0);
					setToolBarStates(0);
					GameStateController.gameModeState = GameStateController.CREATION_TERRAIN_MODE;
				} else if (checkSingleSphere(guiData.sphereMiddle) && mouseClicked
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
				}else if (checkSingleSphere(guiData.sphereRight2) && mouseClicked
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
					if (checkSingleSphere(guiData.sphereMiddle) && mouseClicked) {
						mouseClicked = false;
						GameStateController.CAState = GameStateController.CA_IDLE;
						level2ObjectUnits.clear();
						level2Panel.clear();
					}
					for (int i = 0; i < level2ObjectUnits.size(); i++) {
						if (checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground()) && mouseClicked) {
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
					if (checkSingleSphere(guiData.sphereRight2) && mouseClicked) {
						mouseClicked = false;
						GameStateController.CAState = GameStateController.CA_IDLE;
						level2ObjectUnits.clear();
						level2Panel.clear();
					}
					for (int i = 0; i < level2ObjectUnits.size(); i++) {
						if (checkSingleGui(level2ObjectUnits.get(i).getComfirmBackground()) && mouseClicked) {
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

				break;

			case GameStateController.RESEARCH_TERRAIN_MODE:
				if (checkSingleSphere(guiData.leftSphere) && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 1) {
					guiData.leftSphere.setNextState(0);
					setToolBarStates(0);
					GameStateController.gameModeState = GameStateController.CREATION_TERRAIN_MODE;
				} else if (checkSingleSphere(guiData.rightSphere) && Mouse.isButtonDown(0)
						&& guiData.rightSphere.getCurrentState() == 0) {
					guiData.rightSphere.setNextState(1);
					setToolBarStates(3);
					GameStateController.gameModeState = GameStateController.RESEARCH_ANIMAL_MODE;
				}
				break;

			case GameStateController.RESEARCH_ANIMAL_MODE:
				if (checkSingleSphere(guiData.leftSphere) && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 1) {
					guiData.leftSphere.setNextState(0);
					setToolBarStates(1);
					GameStateController.gameModeState = GameStateController.CREATION_ANIMAL_MODE;
				} else if (checkSingleSphere(guiData.rightSphere) && Mouse.isButtonDown(0)
						&& guiData.rightSphere.getCurrentState() == 1) {
					guiData.rightSphere.setNextState(0);
					setToolBarStates(2);
					GameStateController.gameModeState = GameStateController.RESEARCH_TERRAIN_MODE;
				}
				break;

			default:
				break;
			}

			break;

		default:
			break;
		}
		/*
		 * if (GameStateController.currentState ==
		 * GameStateController.CREATION_MODE_IDLE) {
		 * guisToDisplay.add(guiData.addObjectGuiCircle);
		 * guisToDisplay.add(guiData.addObjectGuiIcon);
		 * guisToDisplay.add(guiData.terrainGuiCircle);
		 * guisToDisplay.add(guiData.terrainGuiIcon); } Vector2f mousePos =
		 * picker.getNormalizedXY(); for(GuiTexture gui:guisToDisplay){
		 * gui.setHighlighted(false); float xDiff = Math.abs(mousePos.x -
		 * gui.getPosition().x); float yDiff = Math.abs(mousePos.y -
		 * gui.getPosition().y); if (xDiff <= gui.getScale().x && yDiff <=
		 * gui.getScale().y) { gui.setHighlighted(true);
		 * //System.out.println("gg!"); } }
		 */

		return mouseClicked;
	}

	private void setToolBarStates(int i) {
		guiData.sphereMiddle.setNextState(i);
		guiData.sphereLeft1.setNextState(i);
		guiData.sphereLeft2.setNextState(i);
		guiData.sphereLeft3.setNextState(i);
		guiData.sphereRight1.setNextState(i);
		guiData.sphereRight2.setNextState(i);
		guiData.sphereRight3.setNextState(i);
	}
	/*
	 * public List<GuiTexture> getGuisToDisplay() { return guisToDisplay; }
	 */

	public List<GuiSphereTexture> getLevel1Spheres() {
		return level1Spheres;
	}

	public List<GuiObjectUnit> getLevel2ObjectUnits() {
		return level2ObjectUnits;
	}

	public ObjectData getTerrainToReturn() {
		return terrainToReturn;
	}

	public EntityObject getEntityObjectToReturn() {
		return entityObjectToReturn;
	}

	public List<GUIText> getLevel2Texts() {
		return level2Texts;
	}

	public List<GuiTexture> getLevel2Panel() {
		return level2Panel;
	}

}
