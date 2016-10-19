package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class GuiSphereTexture {
	private List<Integer> textures;
	private List<Integer> backgroundTextures;
	private List<Vector3f> colours;
	private List<Float> scaleDowns;
	private Vector2f position;
	private Vector2f scale;

	private boolean useColours;
	private boolean highlighted;

	private int currentState = 0;
	private int nextState = 0;
	private float transition = 0;
	private float transitSpeed = 0.008f;

	public GuiSphereTexture(int texture, int backgroundTexture, float scaleDown, Vector2f position, float scaleHeight) {
		this.textures = new ArrayList<Integer>();
		this.backgroundTextures = new ArrayList<Integer>();
		this.scaleDowns = new ArrayList<Float>();
		textures.add(texture);
		backgroundTextures.add(backgroundTexture);
		scaleDowns.add(scaleDown);
		this.position = position;
		this.scale = new Vector2f(scaleHeight*DisplayManager.HEIGHT/DisplayManager.WIDTH, scaleHeight);
		this.useColours = false;
	}

	public GuiSphereTexture(int texture, int backgroundTexture, Vector3f colour, float scaleDown, Vector2f position,
			float scaleHeight) {
		this.textures = new ArrayList<Integer>();
		this.backgroundTextures = new ArrayList<Integer>();
		this.scaleDowns = new ArrayList<Float>();
		textures.add(texture);
		backgroundTextures.add(backgroundTexture);
		scaleDowns.add(scaleDown);
		this.position = position;
		this.scale = new Vector2f(scaleHeight*DisplayManager.HEIGHT/DisplayManager.WIDTH, scaleHeight);
		this.useColours = true;
		this.colours = new ArrayList<Vector3f>();
		colours.add(colour);
	}

	public void addTextureAndBackground(int texture, int background, float scaleDown) {
		textures.add(texture);
		backgroundTextures.add(background);
		scaleDowns.add(scaleDown);
	}

	public void addTextureAndColour(int texture, Vector3f colour, float scaleDown) {
		textures.add(texture);
		colours.add(colour);
		scaleDowns.add(scaleDown);
	}

	public int getCurrentTexture(int i) {
		if (i < textures.size()) {
			return textures.get(i);
		}
		return textures.get(0);
	}

	public int getNextTexture(int i) {
		if (i < textures.size()) {
			return textures.get(i);
		}
		return textures.get(0);
	}

	public int getCurrentBackgroundTexture(int i) {
		if (i < backgroundTextures.size()) {
			return backgroundTextures.get(i);
		}
		return backgroundTextures.get(0);
	}
	
	public int getNextBackgroundTexture(int i) {
		if (i < backgroundTextures.size()) {
			return backgroundTextures.get(i);
		}
		return backgroundTextures.get(0);
	}
	
	public float getCurrentScaleDown(int i){
		if (i < scaleDowns.size()){
			return scaleDowns.get(i);
		}
		return scaleDowns.get(0);
	}
	
	public float getNextScaleDown(int i){
		if (i < scaleDowns.size()){
			return scaleDowns.get(i);
		}
		return scaleDowns.get(0);
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public int getNextState() {
		return nextState;
	}

	public void setNextState(int nextState) {
		this.nextState = nextState;
		this.transition = 0;
	}

	public List<Integer> getTextures() {
		return textures;
	}

	public List<Integer> getBackgroundTextures() {
		return backgroundTextures;
	}

	public List<Vector3f> getColours() {
		return colours;
	}

	public List<Float> getScaleDowns() {
		return scaleDowns;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public boolean isUseColours() {
		return useColours;
	}

	public float getTransition() {
		return transition;
	}

	public float getTransitSpeed() {
		return transitSpeed;
	}

	public void setTransition(float transition) {
		this.transition = transition;
		if (transition > 1) {
			this.currentState = nextState;
			this.transition = 0;
		}
	}

	public void incrementTransition() {
		if (currentState == nextState) {
			return;
		}
		this.transition += transitSpeed;
		if (transition > 1) {
			this.currentState = nextState;
			this.transition = 0;
		}
	}

}
