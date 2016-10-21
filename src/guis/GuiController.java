package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import mainGame.GameStateController;
import mouse.MousePickerSphere;
import renderEngine.Loader;

public class GuiController {

	private GuiData guiData;
	private MousePickerSphere picker;
	// private List<GuiTexture> guis;
	private List<GuiTexture> guisToDisplay;
	private List<GuiSphereTexture> guisSphere3D;
	private List<GuiTexture> guisPanel;
	private List<GuiObjectUnit> guiObjectUnits;

	public GuiController(Loader loader, MousePickerSphere picker) {
		// this.guis = new ArrayList<GuiTexture>();
		this.picker = picker;
		this.guisToDisplay = new ArrayList<GuiTexture>();
		this.guisSphere3D = new ArrayList<GuiSphereTexture>();
		this.guisPanel = new ArrayList<GuiTexture>();
		this.guiObjectUnits = new ArrayList<GuiObjectUnit>();
		guiData = new GuiData(loader);

		// guisToDisplay.add(guiData.blackHexTrayBot);
		// guisToDisplay.add(guiData.addObjectGuiBackground);

		// guisToDisplay.add(guiData.planetBackground);
		// guisToDisplay.add(guiData.planetBlueBar);
		// guisToDisplay.add(guiData.planetCircle);
		// guisToDisplay.add(guiData.planetIcon);

		// guisToDisplay.add(guiData.iTunesTesting);

		GameStateController.CTState = GameStateController.CT_IDLE;

	}

	public GuiSphereTexture checkClicked() {

		Vector2f mousePos = picker.getNormalizedXY();
		/*
		 * for(GuiTexture gui:guisToDisplay){ gui.setHighlighted(false); float
		 * xDiff = Math.abs(mousePos.x - gui.getPosition().x); float yDiff =
		 * Math.abs(mousePos.y - gui.getPosition().y); if (xDiff <=
		 * gui.getScale().x && yDiff <= gui.getScale().y) {
		 * gui.setHighlighted(true); return gui; //System.out.println("gg!"); }
		 * }
		 */
		for (GuiSphereTexture gui : guisSphere3D) {
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

	public void update() {
		guisSphere3D.clear();
		guisToDisplay.clear();
		// guisToDisplay.add(guiData.planetCircle);
		// guisToDisplay.add(guiData.planetIcon);
		guisSphere3D.add(guiData.leftSphere);
		guisSphere3D.add(guiData.rightSphere);

		guisSphere3D.add(guiData.sphereMiddle);

		guisSphere3D.add(guiData.sphereLeft1);
		guisSphere3D.add(guiData.sphereLeft2);
		guisSphere3D.add(guiData.sphereLeft3);
		guisSphere3D.add(guiData.sphereRight1);
		guisSphere3D.add(guiData.sphereRight2);
		guisSphere3D.add(guiData.sphereRight3);

		if (!guisPanel.isEmpty()) {
			for (int i = 0; i < guisPanel.size(); i++) {
				guisToDisplay.add(guisPanel.get(i));
				// System.out.println(i);
			}
		}

		switch (GameStateController.currentState) {
		case GameStateController.PLAY_MODE_IDLE:
			switch (GameStateController.gameModeState) {
			case GameStateController.CREATION_TERRAIN_MODE:
				if (checkClicked() == guiData.leftSphere && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 0) {
					guiData.leftSphere.setNextState(1);
					setToolBarStates(2);
					GameStateController.gameModeState = GameStateController.RESEARCH_TERRAIN_MODE;
				} else if (checkClicked() == guiData.rightSphere && Mouse.isButtonDown(0)
						&& guiData.rightSphere.getCurrentState() == 0) {
					guiData.rightSphere.setNextState(1);
					setToolBarStates(1);
					GameStateController.gameModeState = GameStateController.CREATION_ANIMAL_MODE;
				} else if (checkClicked() == guiData.sphereLeft3 && Mouse.isButtonDown(0)
						&& guiData.sphereLeft3.getCurrentState() == 0
						&& GameStateController.CTState != GameStateController.CT_TERRAIN_TYPE) {
					guisPanel.clear();
					GameStateController.CTState = GameStateController.CT_TERRAIN_TYPE;
					guisPanel.add(guiData.panelBackground);
					
					
					guiObjectUnits.clear();
					
					for(int i=0; i<guiData.OBJECT_ROWS; i++){
						for(int j=0; j<guiData.OBJECT_COLS; j++){
							guiObjectUnits.add(guiData.guiObjects[i][j]);
						}
					}
					
					//guiObjectUnits.add(guiData.guiObjects[0][3]);
					//guisPanel.add(guiData.panelBorder);
					

				}
				break;

			case GameStateController.CREATION_ANIMAL_MODE:
				if (checkClicked() == guiData.leftSphere && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 0) {
					guiData.leftSphere.setNextState(1);
					setToolBarStates(3);
					GameStateController.gameModeState = GameStateController.RESEARCH_ANIMAL_MODE;
				} else if (checkClicked() == guiData.rightSphere && Mouse.isButtonDown(0)
						&& guiData.rightSphere.getCurrentState() == 1) {
					guiData.rightSphere.setNextState(0);
					setToolBarStates(0);
					GameStateController.gameModeState = GameStateController.CREATION_TERRAIN_MODE;
				}
				break;

			case GameStateController.RESEARCH_TERRAIN_MODE:
				if (checkClicked() == guiData.leftSphere && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 1) {
					guiData.leftSphere.setNextState(0);
					setToolBarStates(0);
					GameStateController.gameModeState = GameStateController.CREATION_TERRAIN_MODE;
				} else if (checkClicked() == guiData.rightSphere && Mouse.isButtonDown(0)
						&& guiData.rightSphere.getCurrentState() == 0) {
					guiData.rightSphere.setNextState(1);
					setToolBarStates(3);
					GameStateController.gameModeState = GameStateController.RESEARCH_ANIMAL_MODE;
				}
				break;

			case GameStateController.RESEARCH_ANIMAL_MODE:
				if (checkClicked() == guiData.leftSphere && Mouse.isButtonDown(0)
						&& guiData.leftSphere.getCurrentState() == 1) {
					guiData.leftSphere.setNextState(0);
					setToolBarStates(1);
					GameStateController.gameModeState = GameStateController.CREATION_ANIMAL_MODE;
				} else if (checkClicked() == guiData.rightSphere && Mouse.isButtonDown(0)
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

	public List<GuiTexture> getGuisToDisplay() {
		return guisToDisplay;
	}

	public List<GuiSphereTexture> getGuisSphere3D() {
		return guisSphere3D;
	}

	public List<GuiObjectUnit> getGuiObjectUnits() {
		return guiObjectUnits;
	}
	
	

}
