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
	
	
	public static final Vector3f PURPLE = new Vector3f(0.78f, 0.48f, 0.85f);
	
	public static final Vector3f GREY_LOCKED = new Vector3f(126 / 255f, 126 / 255f, 126 / 255f);

	private Loader loader;
	
	public static final float SPHERES_Y = -0.87f;
	public static final float TOOLBAR_Y = -0.7f;
	
	
	public GuiSphereTexture playSphere3D;
	
	public GuiTexture levelSphere3D;
	
	public GuiTexture sphereMiddle3D;
	public GuiTexture sphereLeft1;
	public GuiTexture sphereLeft2;
	public GuiTexture sphereLeft3;
	public GuiTexture sphereRight1;
	public GuiTexture sphereRight2;
	public GuiTexture sphereRight3;
	
	
	public GuiTexture toolMiddle;
	public GuiTexture toolLeft1;
	public GuiTexture toolLeft2;
	public GuiTexture toolLeft3;
	public GuiTexture toolLeft4;
	public GuiTexture toolRight1;
	public GuiTexture toolRight2;
	public GuiTexture toolRight3;
	public GuiTexture toolRight4;
	
	
	
	
	
	
	public GuiTexture createMode3D;
	
	public GuiTexture objectMode3D;
	
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
		
		/*
		playSphere3D = new GuiTexture(loader.loadTexture("planet"), loader.loadTexture("blueCircle"), new Vector2f(-0.85f, -0.76f), 0.22f);
		playSphere3D.setTextureSet2(loader.loadTexture("eye"), loader.loadTexture("greenCircle"));
		playSphere3D.setTextureSet3(loader.loadTexture("dna2"), loader.loadTexture("redCircle"));
		*/
		playSphere3D = new GuiSphereTexture(loader.loadTexture("planet"), loader.loadTexture("blueCircle"), 1.1f, new Vector2f(-0.85f, -0.76f), 0.22f);
		playSphere3D.addTextureAndBackground(loader.loadTexture("eye"), loader.loadTexture("greenCircle"), 1.0f);
		playSphere3D.addTextureAndBackground(loader.loadTexture("dna2"), loader.loadTexture("redCircle"), 1.1f);
		
		
		
		levelSphere3D = new GuiTexture(loader.loadTexture("mana"), loader.loadTexture("greyCircle"), new Vector2f(0.85f, -0.76f), 0.22f);
		levelSphere3D.useSoildColour(PURPLE, GREY_LOCKED, GREY_LOCKED);
		
		
		sphereMiddle3D = new GuiTexture(loader.loadTexture("play"), loader.loadTexture("greyCircle"), new Vector2f(0f, SPHERES_Y), 0.1f);
		sphereMiddle3D.set3Textures(loader.loadTexture("play"), loader.loadTexture("leaf"), loader.loadTexture("locked"));
		sphereMiddle3D.useSoildColour(BLUE, GREEN_CIRCLE, GREY_LOCKED);
		sphereMiddle3D.setScaleDown(1.4f);
		
		sphereLeft1 = new GuiTexture(loader.loadTexture("pause"), loader.loadTexture("greyCircle"), new Vector2f(-0.15f, SPHERES_Y), 0.1f);
		sphereLeft1.set3Textures(loader.loadTexture("pause"), loader.loadTexture("terrain"), loader.loadTexture("locked"));
		sphereLeft1.useSoildColour(PURPLE, BROWN_CIRCLE, GREY_LOCKED);
		sphereLeft1.setScaleDown(1.4f);
		
		sphereLeft2 = new GuiTexture(loader.loadTexture("play"), loader.loadTexture("greyCircle"), new Vector2f(-0.3f, SPHERES_Y), 0.1f);
		sphereLeft2.set3Textures(loader.loadTexture("locked"), loader.loadTexture("locked"), loader.loadTexture("locked"));
		sphereLeft2.useSoildColour(GREY_LOCKED, GREY_LOCKED, GREY_LOCKED);
		sphereLeft2.setScaleDown(1.4f);
		
		sphereLeft3 = new GuiTexture(loader.loadTexture("play"), loader.loadTexture("greyCircle"), new Vector2f(-0.45f, SPHERES_Y), 0.1f);
		sphereLeft3.set3Textures(loader.loadTexture("locked"), loader.loadTexture("locked"), loader.loadTexture("locked"));
		sphereLeft3.useSoildColour(GREY_LOCKED, GREY_LOCKED, GREY_LOCKED);
		sphereLeft3.setScaleDown(1.4f);
		
		sphereRight1 = new GuiTexture(loader.loadTexture("play"), loader.loadTexture("greyCircle"), new Vector2f(0.15f, SPHERES_Y), 0.1f);
		sphereRight1.set3Textures(loader.loadTexture("locked"), loader.loadTexture("claw"), loader.loadTexture("locked"));
		sphereRight1.useSoildColour(GREY_LOCKED, BLUE, GREY_LOCKED);
		sphereRight1.setScaleDown(1.4f);
		
		sphereRight2 = new GuiTexture(loader.loadTexture("play"), loader.loadTexture("greyCircle"), new Vector2f(0.3f, SPHERES_Y), 0.1f);
		sphereRight2.set3Textures(loader.loadTexture("locked"), loader.loadTexture("locked"), loader.loadTexture("locked"));
		sphereRight2.useSoildColour(GREY_LOCKED, GREY_LOCKED, GREY_LOCKED);
		sphereRight2.setScaleDown(1.4f);
		
		sphereRight3 = new GuiTexture(loader.loadTexture("play"), loader.loadTexture("greyCircle"), new Vector2f(0.45f, SPHERES_Y), 0.1f);
		sphereRight3.set3Textures(loader.loadTexture("locked"), loader.loadTexture("locked"), loader.loadTexture("locked"));
		sphereRight3.useSoildColour(GREY_LOCKED, GREY_LOCKED, GREY_LOCKED);
		sphereRight3.setScaleDown(1.4f);
		
		/*
		createMode3D = new GuiTexture(loader.loadTexture("eye"), loader.loadTexture("redCircle"), new Vector2f(-0.85f, -0.16f), 0.22f);
		
		
		objectMode3D = new GuiTexture(loader.loadTexture("leaf"), loader.loadTexture("greyCircle"), new Vector2f(0f, -0.76f), 0.20f);
		objectMode3D.useSoildColour(GREEN_CIRCLE);
		objectMode3D.addColour(GREEN_CIRCLE);
		objectMode3D.addColour(BROWN_CIRCLE);
		
		
		
		gui3dTesting = new GuiTexture(loader.loadTexture("blueCircle1"), new Vector2f(-0.85f, -0.76f), 0.2f);
		
		
		
		
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
		*/
	}

}
