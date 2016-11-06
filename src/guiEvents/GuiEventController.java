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

	private GuiCreationTerrainEventController guiCreationTerrainEventController;
	private GuiCreationAnimalEventController guiCreationAnimalEventController;

	public GuiEventController(Loader loader, MousePickerSphere picker,
			GameEntityObjectsController gameEntityObjectsController) {
		this.gameEntityObjectsController = gameEntityObjectsController;

		this.guiCreationTerrainEventController = new GuiCreationTerrainEventController(gameEntityObjectsController,
				picker);
		this.guiCreationAnimalEventController = new GuiCreationAnimalEventController(gameEntityObjectsController,
				picker);

		this.picker = picker;
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
				mouseClicked = guiCreationTerrainEventController.updateCreationTerrainState(mouseClicked, guiData,
						level1Spheres, level2Panel, level2ObjectUnits, level2Texts);

				entityObjectToReturn = guiCreationTerrainEventController.getEntityObjectToReturn();
				break;

			case GameStateController.CREATION_ANIMAL_MODE:
				mouseClicked = guiCreationAnimalEventController.updateCreationAnimalState(mouseClicked, guiData,
						level1Spheres, level2Panel, level2ObjectUnits, level2Texts);
				entityObjectToReturn = guiCreationAnimalEventController.getEntityObjectToReturn();
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
