package guis;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;

public class GuiData {

	public static final Vector3f PURE_BLACK = new Vector3f(0, 0, 0);
	public static final Vector3f WHITE = new Vector3f(0.95f, 0.98f, 0.95f);
	public static final Vector3f GREEN = new Vector3f(0.65f, 0.98f, 0.55f);
	public static final Vector3f BROWN = new Vector3f(0.78f, 0.68f, 0.35f);
	public static final Vector3f BLUE = new Vector3f(0.28f, 0.58f, 0.95f);

	public static final Vector3f GREEN_CIRCLE = new Vector3f(56 / 255f, 152 / 255f, 57 / 255f);
	public static final Vector3f BROWN_CIRCLE = new Vector3f(149 / 255f, 75 / 255f, 16 / 255f);

	private Loader loader;

	public GuiTexture terrainGuiBackground;
	public GuiTexture terrainGuiCircle;
	public GuiTexture terrainGuiIcon;

	public GuiTexture addObjectGuiBackground;
	public GuiTexture addObjectGuiCircle;
	public GuiTexture addObjectGuiIcon;

	// public GuiTexture add

	public GuiTexture blackHexTrayBot;
	public GuiTexture blackBarBot;

	public GuiTexture planetBackground;
	public GuiTexture planetCircle;
	public GuiTexture planetIcon;
	public GuiTexture planetBlueBar;

	public GuiTexture objectPanelClassBackground;
	public GuiTexture objectPanelBackground;

	public GuiTexture iTunesTesting;

	public GuiTexture gui3dTesting;

	public GuiData(Loader loader) {
		this.loader = loader;
		
		gui3dTesting = new GuiTexture(loader.loadTexture("blueCircle"), new Vector2f(-0.85f, -0.76f), 0.2f);
		
		
		
		
		planetIcon = new GuiTexture(loader.loadTexture("planet"), new Vector2f(-0.85f, -0.76f), 0.1f);
		planetIcon.useSoildColour(WHITE);
		planetIcon.useHighlightedColour(BLUE);
		planetBackground = new GuiTexture(loader.loadTexture("blackBar"), new Vector2f(-0.85f, -0.85f),
				new Vector2f(0.15f, 0.29f));
		planetCircle = new GuiTexture(loader.loadTexture("blueCircle2D"), planetIcon.getPosition(), 0.2f);
		planetBackground.setTransparency(0.5f);
		planetBlueBar = new GuiTexture(loader.loadTexture("blackBar"),
				new Vector2f(planetBackground.getPosition().x + planetBackground.getScale().x,
						planetBackground.getPosition().y),
				new Vector2f(0.01f, planetBackground.getScale().y));
		planetBlueBar.useSoildColour(BLUE);

		// iTunesTesting = new GuiTexture(loader.loadTexture("itunes"), new
		// Vector2f(-0.85f, -0.75f), 0.2f);

		terrainGuiIcon = new GuiTexture(loader.loadTexture("terrain"), new Vector2f(-0.25f, -0.85f), 0.05f);
		terrainGuiIcon.useSoildColour(WHITE);
		terrainGuiIcon.useHighlightedColour(BROWN);
		terrainGuiCircle = new GuiTexture(loader.loadTexture("blueCircle2D"), terrainGuiIcon.getPosition(), 0.1f);
		terrainGuiCircle.useSoildColour(BROWN_CIRCLE);
		addObjectGuiIcon = new GuiTexture(loader.loadTexture("leaf"), new Vector2f(0f, -0.85f), 0.05f);
		addObjectGuiIcon.useSoildColour(WHITE);
		addObjectGuiIcon.useHighlightedColour(GREEN);
		addObjectGuiCircle = new GuiTexture(loader.loadTexture("blueCircle2D"), addObjectGuiIcon.getPosition(), 0.1f);
		addObjectGuiCircle.useSoildColour(GREEN_CIRCLE);
		addObjectGuiBackground = new GuiTexture(loader.loadTexture("HexagonBorder"), new Vector2f(0f, -0.85f), 0.1f);
		addObjectGuiBackground.useSoildColour(WHITE);
		blackHexTrayBot = new GuiTexture(loader.loadTexture("blackBar"), new Vector2f(0f, -0.85f),
				new Vector2f(0.5f, 0.13f));
		blackHexTrayBot.setTransparency(0.5f);
		blackBarBot = new GuiTexture(loader.loadTexture("blackHexagon"), new Vector2f(0f, -0.9f),
				new Vector2f(1f, 0.1f));

		objectPanelBackground = new GuiTexture(loader.loadTexture("blackBar"), new Vector2f(0.2f, 0.2f),
				new Vector2f(0.8f, 0.7f));
		objectPanelBackground.setTransparency(0.5f);
	}

}
