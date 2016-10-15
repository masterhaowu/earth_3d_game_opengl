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
	
	public GuiController(Loader loader, MousePickerSphere picker){
		//this.guis = new ArrayList<GuiTexture>();
		this.picker = picker;
		this.guisToDisplay = new ArrayList<GuiTexture>();
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
	
	
	public GuiTexture checkClicked(){
		Vector2f mousePos = picker.getNormalizedXY();
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
		return null;
	}
	
	
	public void update(){
		guisToDisplay.clear();
		guisToDisplay.add(guiData.planetCircle);
		guisToDisplay.add(guiData.planetIcon);
		
		switch (GameStateController.currentState) {
		case GameStateController.CREATION_MODE_IDLE:
			guisToDisplay.add(guiData.addObjectGuiCircle);
			guisToDisplay.add(guiData.addObjectGuiIcon);
			guisToDisplay.add(guiData.terrainGuiCircle);
			guisToDisplay.add(guiData.terrainGuiIcon);
			if (checkClicked() == guiData.addObjectGuiCircle && Mouse.isButtonDown(0)) {
				GameStateController.currentState = GameStateController.CREATION_MODE_ADD_OBJECT;
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
	
	
	
	
	
	

}
