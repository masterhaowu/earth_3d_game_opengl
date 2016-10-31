package guiDataBase;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import gameDataBase.ObjectsNetwork;
import guis.GuiObjectUnit;
import guis.GuiSphereTexture;
import guis.GuiTexture;
import renderEngine.Loader;
import toolbox.Maths;

public class GuiData {

	public static final Vector3f PURE_BLACK = new Vector3f(0, 0, 0);
	public static final Vector3f WHITE = new Vector3f(0.95f, 0.98f, 0.95f);
	// public static final Vector3f GREEN = new Vector3f(0.65f, 0.98f, 0.55f);
	public static final Vector3f BROWN = new Vector3f(0.78f, 0.68f, 0.35f);
	// public static final Vector3f BLUE = new Vector3f(0.28f, 0.58f, 0.95f);

	public static final Vector3f GREEN_CIRCLE = new Vector3f(56 / 255f, 152 / 255f, 57 / 255f);
	public static final Vector3f BROWN_CIRCLE = new Vector3f(149 / 255f, 75 / 255f, 16 / 255f);
	public static final Vector3f YELLOW_CIRCLE = new Vector3f(149 / 255f, 180 / 255f, 16 / 255f);

	public static final Vector3f BLUE = new Vector3f(28 / 255f, 74 / 255f, 246 / 255f);
	public static final Vector3f GREEN = new Vector3f(77 / 255f, 184 / 255f, 45 / 255f);
	public static final Vector3f RED = new Vector3f(231 / 255f, 74 / 255f, 45 / 255f);
	public static final Vector3f YELLOW = new Vector3f(253 / 255f, 220 / 255f, 100 / 255f);

	public static Vector3f blueToGreen = Vector3f.sub(GREEN, BLUE, null);
	public static Vector3f blueToGreenInc = Maths.vectorMult(blueToGreen, 1f / 8f);
	public static Vector3f blueToRed = Vector3f.sub(RED, BLUE, null);
	public static Vector3f blueToRedInc = Maths.vectorMult(blueToRed, 1f / 8f);

	public static Vector3f YellowToGreen = Vector3f.sub(GREEN, YELLOW, null);
	public static Vector3f YellowToGreenInc = Maths.vectorMult(YellowToGreen, 1f / 8f);
	public static Vector3f YellowToRed = Vector3f.sub(RED, YELLOW, null);
	public static Vector3f YellowToRedInc = Maths.vectorMult(YellowToRed, 1f / 8f);

	public static final Vector3f PURPLE = new Vector3f(0.78f, 0.48f, 0.85f);

	public static final Vector3f GREY_LOCKED = new Vector3f(126 / 255f, 126 / 255f, 126 / 255f);

	private Loader loader;

	public static final float SPHERES_Y = -0.85f;
	public static final float TOOLBAR_Y = -0.7f;
	
	public static final int OBJECT_ROWS = 3;
	public static final int OBJECT_COLS = 4;
	
	public static final float PANEL_PADDING_TOP = 0.2f;
	public static final float PANEL_PADDING_BOTTOM = 0.2f;
	public static final float PANEL_PADDING_LEFT = 0.1f;
	public static final float PANEL_PADDING_RIGHT = 0.1f;

	public GuiSphereTexture leftSphere;

	public GuiSphereTexture rightSphere;

	public GuiSphereTexture sphereMiddle;
	public GuiSphereTexture sphereLeft1;
	public GuiSphereTexture sphereLeft2;
	public GuiSphereTexture sphereLeft3;
	public GuiSphereTexture sphereRight1;
	public GuiSphereTexture sphereRight2;
	public GuiSphereTexture sphereRight3;

	public GuiTexture panelBackground;
	public GuiTexture panelBorder;

	
	
	//public GuiObjectUnit[][] guiObjects;
	public Vector2f [][] guiObjectUnitPositions; // this depends on row and col
	public Vector2f guiObjectUnitScale;

	// public GuiTexture add
	
	public GuiDataTerrains guiDataTerrains;
	public GuiDataGrasses guiDataGrasses;
	public GuiDataTrees guiDataTrees;




	public GuiTexture objectPanelClassBackground;
	public GuiTexture objectPanelBackground;

	public GuiTexture iTunesTesting;

	public GuiTexture gui3dTesting;

	public GuiData(Loader loader) {
		
		guiDataTerrains = new GuiDataTerrains();
		guiDataTerrains.loadTerrains(loader);
		
		guiDataGrasses = new GuiDataGrasses();
		guiDataGrasses.loadGrasses(loader);
		
		guiDataTrees = new GuiDataTrees();
		guiDataTrees.loadTrees(loader);
		
		
		Vector3f CT1 = new Vector3f(BLUE.x + blueToGreenInc.x, BLUE.y + blueToGreenInc.y, BLUE.z + blueToGreenInc.z);
		Vector3f CT2 = new Vector3f(BLUE.x + blueToGreenInc.x * 2, BLUE.y + blueToGreenInc.y * 2,
				BLUE.z + blueToGreenInc.z * 2);
		Vector3f CT3 = new Vector3f(BLUE.x + blueToGreenInc.x * 3, BLUE.y + blueToGreenInc.y * 3,
				BLUE.z + blueToGreenInc.z * 3);
		Vector3f CT4 = new Vector3f(BLUE.x + blueToGreenInc.x * 4, BLUE.y + blueToGreenInc.y * 4,
				BLUE.z + blueToGreenInc.z * 4);
		Vector3f CT5 = new Vector3f(BLUE.x + blueToGreenInc.x * 5, BLUE.y + blueToGreenInc.y * 5,
				BLUE.z + blueToGreenInc.z * 5);
		Vector3f CT6 = new Vector3f(BLUE.x + blueToGreenInc.x * 6, BLUE.y + blueToGreenInc.y * 6,
				BLUE.z + blueToGreenInc.z * 6);
		Vector3f CT7 = new Vector3f(BLUE.x + blueToGreenInc.x * 7, BLUE.y + blueToGreenInc.y * 7,
				BLUE.z + blueToGreenInc.z * 7);

		Vector3f CA1 = new Vector3f(BLUE.x + blueToRedInc.x, BLUE.y + blueToRedInc.y, BLUE.z + blueToRedInc.z);
		Vector3f CA2 = new Vector3f(BLUE.x + blueToRedInc.x * 2, BLUE.y + blueToRedInc.y * 2,
				BLUE.z + blueToRedInc.z * 2);
		Vector3f CA3 = new Vector3f(BLUE.x + blueToRedInc.x * 3, BLUE.y + blueToRedInc.y * 3,
				BLUE.z + blueToRedInc.z * 3);
		Vector3f CA4 = new Vector3f(BLUE.x + blueToRedInc.x * 4, BLUE.y + blueToRedInc.y * 4,
				BLUE.z + blueToRedInc.z * 4);
		Vector3f CA5 = new Vector3f(BLUE.x + blueToRedInc.x * 5, BLUE.y + blueToRedInc.y * 5,
				BLUE.z + blueToRedInc.z * 5);
		Vector3f CA6 = new Vector3f(BLUE.x + blueToRedInc.x * 6, BLUE.y + blueToRedInc.y * 6,
				BLUE.z + blueToRedInc.z * 6);
		Vector3f CA7 = new Vector3f(BLUE.x + blueToRedInc.x * 7, BLUE.y + blueToRedInc.y * 7,
				BLUE.z + blueToRedInc.z * 7);

		Vector3f RT1 = new Vector3f(YELLOW.x + YellowToGreenInc.x, YELLOW.y + YellowToGreenInc.y,
				YELLOW.z + YellowToGreenInc.z);
		Vector3f RT2 = new Vector3f(YELLOW.x + YellowToGreenInc.x * 2, YELLOW.y + YellowToGreenInc.y * 2,
				YELLOW.z + YellowToGreenInc.z * 2);
		Vector3f RT3 = new Vector3f(YELLOW.x + YellowToGreenInc.x * 3, YELLOW.y + YellowToGreenInc.y * 3,
				YELLOW.z + YellowToGreenInc.z * 3);
		Vector3f RT4 = new Vector3f(YELLOW.x + YellowToGreenInc.x * 4, YELLOW.y + YellowToGreenInc.y * 4,
				YELLOW.z + YellowToGreenInc.z * 4);
		Vector3f RT5 = new Vector3f(YELLOW.x + YellowToGreenInc.x * 5, YELLOW.y + YellowToGreenInc.y * 5,
				YELLOW.z + YellowToGreenInc.z * 5);
		Vector3f RT6 = new Vector3f(YELLOW.x + YellowToGreenInc.x * 6, YELLOW.y + YellowToGreenInc.y * 6,
				YELLOW.z + YellowToGreenInc.z * 6);
		Vector3f RT7 = new Vector3f(YELLOW.x + YellowToGreenInc.x * 7, YELLOW.y + YellowToGreenInc.y * 7,
				YELLOW.z + YellowToGreenInc.z * 7);

		Vector3f RA1 = new Vector3f(YELLOW.x + YellowToRedInc.x, YELLOW.y + YellowToRedInc.y,
				YELLOW.z + YellowToRedInc.z);
		Vector3f RA2 = new Vector3f(YELLOW.x + YellowToRedInc.x * 2, YELLOW.y + YellowToRedInc.y * 2,
				YELLOW.z + YellowToRedInc.z * 2);
		Vector3f RA3 = new Vector3f(YELLOW.x + YellowToRedInc.x * 3, YELLOW.y + YellowToRedInc.y * 3,
				YELLOW.z + YellowToRedInc.z * 3);
		Vector3f RA4 = new Vector3f(YELLOW.x + YellowToRedInc.x * 4, YELLOW.y + YellowToRedInc.y * 4,
				YELLOW.z + YellowToRedInc.z * 4);
		Vector3f RA5 = new Vector3f(YELLOW.x + YellowToRedInc.x * 5, YELLOW.y + YellowToRedInc.y * 5,
				YELLOW.z + YellowToRedInc.z * 5);
		Vector3f RA6 = new Vector3f(YELLOW.x + YellowToRedInc.x * 6, YELLOW.y + YellowToRedInc.y * 6,
				YELLOW.z + YellowToRedInc.z * 6);
		Vector3f RA7 = new Vector3f(YELLOW.x + YellowToRedInc.x * 7, YELLOW.y + YellowToRedInc.y * 7,
				YELLOW.z + YellowToRedInc.z * 7);

		int locked = loader.loadTexture("locked");
		this.loader = loader;

		leftSphere = new GuiSphereTexture(loader.loadTexture("eye"), loader.loadTexture("blueCircle"), 1.02f,
				new Vector2f(-0.85f, -0.76f), 0.22f);
		leftSphere.addTextureAndBackground(loader.loadTexture("dna2"), loader.loadTexture("yellowCircle"), 1.1f);
		// leftSphere.addTextureAndBackground(loader.loadTexture("dna2"),
		// loader.loadTexture("redCircle"), 1.1f);

		rightSphere = new GuiSphereTexture(loader.loadTexture("leaf"), loader.loadTexture("greenCircle"), 1.3f,
				new Vector2f(0.85f, -0.76f), 0.22f);
		rightSphere.addTextureAndBackground(loader.loadTexture("claw"), loader.loadTexture("redCircle"), 1.1f);
		// levelSphere3D.addTextureAndColour(texture, colour, scaleDown);
		// levelSphere3D.useSoildColour(PURPLE, GREY_LOCKED, GREY_LOCKED);

		float toolBarScale = 0.12f;
		float toolBarPadding = 0.17f;

		sphereMiddle = new GuiSphereTexture(loader.loadTexture("reef"), loader.loadTexture("greyCircle"), CT4, 1.2f,
				new Vector2f(0f, SPHERES_Y), toolBarScale);
		sphereMiddle.addTextureAndColour(loader.loadTexture("deer3"), CA4, 0.9f);
		sphereMiddle.addTextureAndColour(locked, RT4, 1.4f);
		sphereMiddle.addTextureAndColour(locked, RA4, 1.4f);

		sphereLeft1 = new GuiSphereTexture(loader.loadTexture("shell"), loader.loadTexture("greyCircle"), CT3, 1.15f,
				new Vector2f(-toolBarPadding, SPHERES_Y), toolBarScale);
		sphereLeft1.addTextureAndColour(loader.loadTexture("reptile"), CA3, 1.0f);
		sphereLeft1.addTextureAndColour(locked, RT3, 1.4f);
		sphereLeft1.addTextureAndColour(locked, RA3, 1.4f);

		sphereLeft2 = new GuiSphereTexture(loader.loadTexture("rock"), loader.loadTexture("greyCircle"), CT2, 1.2f,
				new Vector2f(-toolBarPadding * 2f, SPHERES_Y), toolBarScale);
		sphereLeft2.addTextureAndColour(loader.loadTexture("eagle2"), CA2, 1.05f);
		sphereLeft2.addTextureAndColour(locked, RT2, 1.4f);
		sphereLeft2.addTextureAndColour(locked, RA2, 1.4f);

		sphereLeft3 = new GuiSphereTexture(loader.loadTexture("mountain2"), loader.loadTexture("greyCircle"), CT1, 1.1f,
				new Vector2f(-toolBarPadding * 3f, SPHERES_Y), toolBarScale);
		sphereLeft3.addTextureAndColour(loader.loadTexture("fish2"), CA1, 1.25f);
		sphereLeft3.addTextureAndColour(locked, RT1, 1.4f);
		sphereLeft3.addTextureAndColour(locked, RA1, 1.4f);

		sphereRight1 = new GuiSphereTexture(loader.loadTexture("grass4"), loader.loadTexture("greyCircle"), CT5, 1.1f,
				new Vector2f(toolBarPadding, SPHERES_Y), toolBarScale);
		sphereRight1.addTextureAndColour(loader.loadTexture("bear9"), CA5, 1.0f);
		sphereRight1.addTextureAndColour(locked, RT5, 1.4f);
		sphereRight1.addTextureAndColour(locked, RA5, 1.4f);

		sphereRight2 = new GuiSphereTexture(loader.loadTexture("flower2"), loader.loadTexture("greyCircle"), CT6, 1.1f,
				new Vector2f(toolBarPadding * 2f, SPHERES_Y), toolBarScale);
		sphereRight2.addTextureAndColour(loader.loadTexture("lion3"), CA6, 1.0f);
		sphereRight2.addTextureAndColour(locked, RT6, 1.4f);
		sphereRight2.addTextureAndColour(locked, RA6, 1.4f);

		sphereRight3 = new GuiSphereTexture(loader.loadTexture("tree5"), loader.loadTexture("greyCircle"), CT7, 1.15f,
				new Vector2f(toolBarPadding * 3f, SPHERES_Y), toolBarScale);
		sphereRight3.addTextureAndColour(loader.loadTexture("dragon"), CA7, 1.2f);
		sphereRight3.addTextureAndColour(locked, RT7, 1.4f);
		sphereRight3.addTextureAndColour(locked, RA7, 1.4f);

		panelBackground = new GuiTexture(loader.loadTexture("whiteBackground"), new Vector2f(0, 0.2f),
				new Vector2f(0.7f, 0.7f));
		panelBackground.useSoildColour(new Vector3f(0, 0, 0));
		panelBackground.setTransparency(0.5f);
		panelBackground.useBorder(new Vector3f(0, 0, 0), 0.005f, 0.3f);
		
		float Xleft = panelBackground.getPosition().x - panelBackground.getScale().x + panelBackground.getScale().x * PANEL_PADDING_LEFT;
		float Ytop = panelBackground.getPosition().y + panelBackground.getScale().y - panelBackground.getScale().y * PANEL_PADDING_TOP;
		float Xscale = panelBackground.getScale().x/(OBJECT_COLS + 0.1f) * (1 - (PANEL_PADDING_LEFT + PANEL_PADDING_RIGHT)/2f);
		float Yscale = panelBackground.getScale().y/(OBJECT_ROWS + 0.1f) * (1 - (PANEL_PADDING_TOP + PANEL_PADDING_BOTTOM)/2f);
		
		//guiObjects = new GuiObjectUnit[OBJECT_ROWS][];
		guiObjectUnitScale = new Vector2f(Xscale, Yscale);
		guiObjectUnitPositions = new Vector2f[OBJECT_ROWS][];
		for(int i = 0; i<OBJECT_ROWS; i++){
			//guiObjects[i] = new GuiObjectUnit[OBJECT_COLS];
			guiObjectUnitPositions[i] = new Vector2f[OBJECT_COLS];
			float currentY = Ytop - panelBackground.getScale().y * (2 * i + 1) / OBJECT_ROWS * (1 - (PANEL_PADDING_TOP + PANEL_PADDING_BOTTOM)/2f);
			//System.out.println(currentY);
			//currentY = 0.5f;
			for(int j=0; j<OBJECT_COLS; j++){
				float currentX = Xleft + panelBackground.getScale().x * (2 * j + 1) / OBJECT_COLS * (1 - (PANEL_PADDING_LEFT + PANEL_PADDING_RIGHT)/2f);
				//guiObjects[i][j] = new GuiObjectUnit(new Vector2f(currentX, currentY),
				//		new Vector2f(Xscale, Yscale), loader);
				//guiObjects[i][j].setTerrainUnit(ObjectsNetwork.snowTerrain); //testing stuff
				guiObjectUnitPositions[i][j] = new Vector2f(currentX, currentY);
				
			}
		}
		
		/*
		float unitX = panelBackground.getPosition().x + panelBackground.getScale().x / 3;
		//float Ybot = panelBackground.getPosition().y - panelBackground.getScale().y;
		guiObjectUnitTesting11 = new GuiObjectUnit(new Vector2f(0, Ybot + panelBackground.getScale().y * 5 / 3),
				new Vector2f(panelBackground.getScale().x/3, panelBackground.getScale().y / 4), loader);
		guiObjectUnitTesting12 = new GuiObjectUnit(new Vector2f(0, Ybot + panelBackground.getScale().y * 3 / 3),
				new Vector2f(panelBackground.getScale().x/3, panelBackground.getScale().y / 4), loader);
		guiObjectUnitTesting13 = new GuiObjectUnit(new Vector2f(0, Ybot + panelBackground.getScale().y * 1 / 3),
				new Vector2f(panelBackground.getScale().x/3, panelBackground.getScale().y / 4), loader);
	*/
		/*
		panelBorder = new GuiTexture(loader.loadTexture("blackBorder"), panelBackground.getPosition(),
				panelBackground.getScale());
		panelBorder.useSoildColour(new Vector3f(0.3f, 0.3f, 0.3f));
		panelBorder.setTransparency(0.8f);
		*/

	}

}
