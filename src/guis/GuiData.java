package guis;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;

public class GuiData {
	
	public static final Vector3f PURE_BLACK = new Vector3f(0, 0, 0);
	public static final Vector3f WHITE = new Vector3f(0.95f, 0.98f, 0.95f);
	public static final Vector3f GREEN = new Vector3f(0.65f, 0.98f, 0.55f);
	public static final Vector3f BROWN = new Vector3f(0.78f, 0.68f, 0.35f);
	
	private Loader loader;
	
	
	public GuiTexture terrainGuiBackground;
	public GuiTexture terrainGuiIcon;
	
	public GuiTexture addObjectGuiBackground;
	public GuiTexture addObjectGuiIcon;
	
	
	//public GuiTexture add
	
	public GuiTexture blackHexTrayBot;
	public GuiTexture blackBarBot;
	
	
	public GuiData(Loader loader){
		this.loader = loader;
		terrainGuiIcon = new GuiTexture(loader.loadTexture("terrain"), new Vector2f(-0.25f, -0.85f), 0.1f);
		terrainGuiIcon.useSoildColour(WHITE);
		terrainGuiIcon.useHighlightedColour(BROWN);
		addObjectGuiIcon = new GuiTexture(loader.loadTexture("leaf"), new Vector2f(0f, -0.85f), 0.1f);
		addObjectGuiIcon.useSoildColour(WHITE);
		addObjectGuiIcon.useHighlightedColour(GREEN);
		addObjectGuiBackground = new GuiTexture(loader.loadTexture("HexagonBorder"), new Vector2f(0f, -0.85f), 0.1f);
		addObjectGuiBackground.useSoildColour(WHITE);
		blackHexTrayBot = new GuiTexture(loader.loadTexture("blackBar"), new Vector2f(0f, -0.85f), new Vector2f(0.5f, 0.13f));
		blackHexTrayBot.setTransparency(0.5f);
		blackBarBot = new GuiTexture(loader.loadTexture("blackHexagon"), new Vector2f(0f, -0.9f), new Vector2f(1f, 0.1f));
	}

}
