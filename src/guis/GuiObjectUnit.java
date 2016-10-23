package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entityObjects.EntityObject;
import entityObjects.ObjectData;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import renderEngine.Loader;

public class GuiObjectUnit {

	private int unitID;

	private Vector2f position;
	private Vector2f scale;
	
	private GuiTexture background;
	private GuiSphereTexture mainOrb;
	private FontType font;
	private GUIText name;
	private GuiSphereTexture comfirm;
	private GuiSphereTexture info;

	private List<GuiTexture> guiTextures;
	private List<GuiSphereTexture> guiSphereTextures;
	
	private boolean terrain = false;
	
	private ObjectData objectData;
	
	private EntityObject entityObject;
	

	public GuiObjectUnit(GuiTexture unitBackground, GuiTexture unitPicture, GuiTexture unitName,
			GuiTexture unitDescriptions, GuiTexture unitAddButton, GuiTexture unitInfoButton) {

	}

	public GuiObjectUnit(Vector2f position, Vector2f scale, Loader loader) {
		this.guiTextures = new ArrayList<GuiTexture>();
		this.guiSphereTextures = new ArrayList<GuiSphereTexture>();
		// guiSphereTextures objectOrb = new GuiSphereTexture(,
		// backgroundTexture, scaleDown, position, scaleHeight)
		this.background = new GuiTexture(loader.loadTexture("whiteBackground"), position, scale);
		background.useSoildColour(new Vector3f(0, 0, 0));
		background.setTransparency(0.1f);
		this.guiTextures.add(background);

		this.mainOrb = new GuiSphereTexture(loader.loadTexture("locked"), loader.loadTexture("pinkCircle2"),
				new Vector3f(0, 0, 0), 1.0f, new Vector2f(position.x, position.y - scale.y * 0.15f), scale.y * 0.8f);
		mainOrb.disableAutoRotate();
		this.guiSphereTextures.add(mainOrb);
		
		this.comfirm = new GuiSphereTexture(loader.loadTexture("locked"), loader.loadTexture("greyCircle"),
				GuiData.GREEN, 1.0f, new Vector2f(position.x + scale.x * 0.7f, position.y - scale.y * 0.7f), scale.y * 0.3f);
		this.guiSphereTextures.add(comfirm);
		
		this.info = new GuiSphereTexture(loader.loadTexture("locked"), loader.loadTexture("greyCircle"),
				GuiData.BLUE, 1.0f, new Vector2f(position.x - scale.x * 0.7f, position.y - scale.y * 0.7f), scale.y * 0.3f);
		this.guiSphereTextures.add(info);
		
		this.font = new FontType(loader.loadFontTexture("candara"), "candara");
		//this.name = new GUIText("testing", 1f, font, new Vector2f((position.x - scale.x + 1)/2f, (position.y - scale.y + 1)/2f), scale.x, true);
		
	}
	
	public void updatePositionAndScale(Vector2f position, Vector2f scale){
		this.position = position;
		this.scale = scale;
		this.background.setPosition(position);
		this.background.setScale(scale);
		this.mainOrb.setPosition(new Vector2f(position.x, position.y - scale.y * 0.15f));
		this.mainOrb.setScale(scale. y * 0.8f);
		this.comfirm.setPosition(new Vector2f(position.x + scale.x * 0.7f, position.y - scale.y * 0.7f));
		this.comfirm.setScale(scale.y * 0.3f);
		this.info.setPosition(new Vector2f(position.x - scale.x * 0.7f, position.y - scale.y * 0.7f));
		this.info.setScale(scale.y * 0.3f);
	}
	
	
	public void setTerrainUnit(ObjectData terrainData){
		this.terrain = true;
		this.objectData = terrainData;
	}
	
	

	public GuiSphereTexture getMainOrb() {
		return mainOrb;
	}

	public GuiSphereTexture getComfirm() {
		return comfirm;
	}

	public boolean isTerrain() {
		return terrain;
	}

	public ObjectData getObjectData() {
		return objectData;
	}

	public EntityObject getEntityObject() {
		return entityObject;
	}
	/*
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	*/
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
