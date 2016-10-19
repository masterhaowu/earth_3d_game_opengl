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
	//private List<GuiTexture> guis;
	private List<GuiTexture> guisToDisplay;
	private List<GuiSphereTexture> guisSphere3D;
	
	public GuiController(Loader loader, MousePickerSphere picker){
		//this.guis = new ArrayList<GuiTexture>();
		this.picker = picker;
		this.guisToDisplay = new ArrayList<GuiTexture>();
		this.guisSphere3D = new ArrayList<GuiSphereTexture>();
		guiData = new GuiData(loader);
		
		//guisToDisplay.add(guiData.blackHexTrayBot);
		//guisToDisplay.add(guiData.addObjectGuiBackground);
		guisToDisplay.add(guiData.addObjectGuiCircle);
		guisToDisplay.add(guiData.addObjectGuiIcon);
		guisToDisplay.add(guiData.terrainGuiCircle);
		guisToDisplay.add(guiData.terrainGuiIcon);
		
		//guisToDisplay.add(guiData.planetBackground);
		//guisToDisplay.add(guiData.planetBlueBar);
		guisToDisplay.add(guiData.planetCircle);
		guisToDisplay.add(guiData.planetIcon);
		
		//guisToDisplay.add(guiData.iTunesTesting);
		
	}
	
	
	public GuiSphereTexture checkClicked(){
		
		Vector2f mousePos = picker.getNormalizedXY();
		/*
		for(GuiTexture gui:guisToDisplay){
			gui.setHighlighted(false);
			float xDiff = Math.abs(mousePos.x - gui.getPosition().x);
			float yDiff = Math.abs(mousePos.y - gui.getPosition().y);
			if (xDiff <= gui.getScale().x && yDiff <= gui.getScale().y) {
				gui.setHighlighted(true);
				return gui;
				//System.out.println("gg!");
			}
		}
		*/
		for(GuiSphereTexture gui:guisSphere3D){
			gui.setHighlighted(false);
			float xDiff = Math.abs(mousePos.x - gui.getPosition().x);
			float yDiff = Math.abs(mousePos.y - gui.getPosition().y);
			if (xDiff <= gui.getScale().x && yDiff <= gui.getScale().y) {
				gui.setHighlighted(true);
				return gui;
				//System.out.println("gg!");
			}
		}
		return null;
	}
	
	
	public void update(){
		guisSphere3D.clear();
		guisToDisplay.clear();
		//guisToDisplay.add(guiData.planetCircle);
		//guisToDisplay.add(guiData.planetIcon);
		guisSphere3D.add(guiData.playSphere3D);
		/*
		guisSphere3D.add(guiData.levelSphere3D);
		guisSphere3D.add(guiData.sphereMiddle3D);
		
		guisSphere3D.add(guiData.sphereLeft1);
		guisSphere3D.add(guiData.sphereLeft2);
		guisSphere3D.add(guiData.sphereLeft3);
		guisSphere3D.add(guiData.sphereRight1);
		guisSphere3D.add(guiData.sphereRight2);
		guisSphere3D.add(guiData.sphereRight3);
		*/
		switch (GameStateController.currentState) {
		case GameStateController.PLAY_MODE_IDLE:
			
			if (checkClicked() == guiData.playSphere3D && Mouse.isButtonDown(0) && guiData.playSphere3D.getCurrentState() == 0) {
				//last parameter of currentstate == 0 is to make sure the animation is displayed before the user can rotate the state again.
				//System.out.println("here");
				guiData.playSphere3D.setNextState(1);
				guiData.sphereMiddle3D.setNextState(1);
				guiData.sphereLeft1.setNextState(1);
				guiData.sphereLeft2.setNextState(1);
				guiData.sphereLeft3.setNextState(1);
				guiData.sphereRight1.setNextState(1);
				guiData.sphereRight2.setNextState(1);
				guiData.sphereRight3.setNextState(1);
				GameStateController.currentState = GameStateController.CREATION_MODE_IDLE;
			}
			break;
		case GameStateController.CREATION_MODE_IDLE:
			
			if (checkClicked() == guiData.playSphere3D && Mouse.isButtonDown(0) && guiData.playSphere3D.getCurrentState() == 1) {
				guiData.playSphere3D.setNextState(2);
				guiData.sphereMiddle3D.setNextState(2);
				guiData.sphereLeft1.setNextState(2);
				guiData.sphereLeft2.setNextState(2);
				guiData.sphereLeft3.setNextState(2);
				guiData.sphereRight1.setNextState(2);
				guiData.sphereRight2.setNextState(2);
				guiData.sphereRight3.setNextState(2);
				GameStateController.currentState = GameStateController.RESEARCH_MODE_IDLE;
			}
			break;
		case GameStateController.RESEARCH_MODE_IDLE:
			
			if (checkClicked() == guiData.playSphere3D && Mouse.isButtonDown(0) && guiData.playSphere3D.getCurrentState() == 2) {
				guiData.playSphere3D.setNextState(0);
				guiData.sphereMiddle3D.setNextState(0);
				guiData.sphereLeft1.setNextState(0);
				guiData.sphereLeft2.setNextState(0);
				guiData.sphereLeft3.setNextState(0);
				guiData.sphereRight1.setNextState(0);
				guiData.sphereRight2.setNextState(0);
				guiData.sphereRight3.setNextState(0);
				GameStateController.currentState = GameStateController.PLAY_MODE_IDLE;
			}
			break;
		case GameStateController.CREATION_MODE_ADD_OBJECT:
			guisToDisplay.add(guiData.addObjectGuiCircle);
			guisToDisplay.add(guiData.addObjectGuiIcon);
			guisToDisplay.add(guiData.terrainGuiCircle);
			guisToDisplay.add(guiData.terrainGuiIcon);
			guisToDisplay.add(guiData.objectPanelBackground);
			break;

		default:
			break;
		}
		/*
		if (GameStateController.currentState == GameStateController.CREATION_MODE_IDLE) {
			guisToDisplay.add(guiData.addObjectGuiCircle);
			guisToDisplay.add(guiData.addObjectGuiIcon);
			guisToDisplay.add(guiData.terrainGuiCircle);
			guisToDisplay.add(guiData.terrainGuiIcon);
		}
		Vector2f mousePos = picker.getNormalizedXY();
		for(GuiTexture gui:guisToDisplay){
			gui.setHighlighted(false);
			float xDiff = Math.abs(mousePos.x - gui.getPosition().x);
			float yDiff = Math.abs(mousePos.y - gui.getPosition().y);
			if (xDiff <= gui.getScale().x && yDiff <= gui.getScale().y) {
				gui.setHighlighted(true);
				//System.out.println("gg!");
			}
		}
		*/
	}

	public List<GuiTexture> getGuisToDisplay() {
		return guisToDisplay;
	}


	public List<GuiSphereTexture> getGuisSphere3D() {
		return guisSphere3D;
	}
	
	
	
	
	
	
	
	

}
