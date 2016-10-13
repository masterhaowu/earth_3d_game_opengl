package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

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
		
		guisToDisplay.add(guiData.blackHexTrayBot);
		//guisToDisplay.add(guiData.addObjectGuiBackground);
		guisToDisplay.add(guiData.addObjectGuiIcon);
		guisToDisplay.add(guiData.terrainGuiIcon);
		
	}
	
	
	public void update(){
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
	}

	public List<GuiTexture> getGuisToDisplay() {
		return guisToDisplay;
	}
	
	
	
	
	
	

}
