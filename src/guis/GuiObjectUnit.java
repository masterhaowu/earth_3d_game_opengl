package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;

public class GuiObjectUnit {
	
	
	private int unitID;
	
	private Vector2f position;
	private Vector2f scale;
	
	private List<GuiTexture> guiTextures;
	private List<GuiSphereTexture> guiSphereTextures;
	
	public GuiObjectUnit(GuiTexture unitBackground, GuiTexture unitPicture, GuiTexture unitName,
			GuiTexture unitDescriptions, GuiTexture unitAddButton, GuiTexture unitInfoButton) {
		

	}
	
	
	public GuiObjectUnit(Vector2f position, Vector2f scale, Loader loader){
		this.guiTextures = new ArrayList<GuiTexture>();
		this.guiSphereTextures = new ArrayList<GuiSphereTexture>();
		//guiSphereTextures objectOrb = new GuiSphereTexture(, backgroundTexture, scaleDown, position, scaleHeight)
		GuiTexture background = new GuiTexture(loader.loadTexture("whiteBackground"), position, scale);
		background.useSoildColour(new Vector3f(0, 0, 0));
		background.setTransparency(0.5f);
		this.guiTextures.add(background);
	}


	public Vector2f getPosition() {
		return position;
	}


	public Vector2f getScale() {
		return scale;
	}


	public List<GuiTexture> getGuiTextures() {
		return guiTextures;
	}


	public List<GuiSphereTexture> getGuiSphereTextures() {
		return guiSphereTextures;
	}
	
	

}
