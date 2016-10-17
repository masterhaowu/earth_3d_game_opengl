package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.DisplayManager;

public class GuiTexture {
	
	private int texture;
	private int texture2;
	private int texture3;
	private int backgroundTexture;
	private int backgroundTexture2;
	private int backgroundTexture3;
	private Vector2f position;
	private Vector2f scale;
	private float transparency;
	
	private boolean useSolidColour;
	private Vector3f colour;
	
	private List<Vector3f> colours;
	
	private boolean useHighlightedColour;
	private Vector3f highlightedColour;
	
	private boolean highlighted;
	
	//private boolean use
	
	private boolean contain3DModel;
	//private RawModel model;
	
	private int currentState = 0;
	private int nextState = 0;
	private float transition = 0;
	private float transitSpeed = 0.003f;
	
	
	
	public GuiTexture(int texture, int backgroundTexture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.texture2 = texture;
		this.texture3 = texture;
		this.backgroundTexture = backgroundTexture;
		this.backgroundTexture2 = backgroundTexture;
		this.backgroundTexture3 = backgroundTexture;
		this.position = position;
		this.scale = scale;
		this.transparency = 1.0f;
		this.useSolidColour = false;
		this.colour = new Vector3f(0, 0, 0);
		this.useHighlightedColour = false;
		this.highlightedColour = new Vector3f(0, 0, 0);
		this.highlighted = false;
		this.contain3DModel = true;
		this.colours = new ArrayList<Vector3f>();
		
	}
	
	public GuiTexture(int texture, int backgroundTexture, Vector2f position, float scaleHeight) {
		this.texture = texture;
		this.texture2 = texture;
		this.texture3 = texture;
		this.backgroundTexture = backgroundTexture;
		this.backgroundTexture2 = backgroundTexture;
		this.backgroundTexture3 = backgroundTexture;
		this.position = position;
		//System.out.println(DisplayManager.HEIGHT/DisplayManager.WIDTH);
		this.scale = new Vector2f(scaleHeight*DisplayManager.HEIGHT/DisplayManager.WIDTH, scaleHeight);
		this.transparency = 1.0f;
		this.useSolidColour = false;
		this.colour = new Vector3f(0, 0, 0);
		this.useHighlightedColour = false;
		this.highlightedColour = new Vector3f(0, 0, 0);
		this.highlighted = false;
		this.contain3DModel = true;
		this.colours = new ArrayList<Vector3f>();
	}
	
	
	public GuiTexture(int texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.transparency = 1.0f;
		this.useSolidColour = false;
		this.colour = new Vector3f(0, 0, 0);
		this.useHighlightedColour = false;
		this.highlightedColour = new Vector3f(0, 0, 0);
		this.highlighted = false;
		this.contain3DModel = false;
	}
	
	public GuiTexture(int texture, Vector2f position, float scaleHeight) {
		this.texture = texture;
		this.position = position;
		//System.out.println(DisplayManager.HEIGHT/DisplayManager.WIDTH);
		this.scale = new Vector2f(scaleHeight*DisplayManager.HEIGHT/DisplayManager.WIDTH, scaleHeight);
		this.transparency = 1.0f;
		this.useSolidColour = false;
		this.colour = new Vector3f(0, 0, 0);
		this.useHighlightedColour = false;
		this.highlightedColour = new Vector3f(0, 0, 0);
		this.highlighted = false;
		this.contain3DModel = false;
	}
	
	public void useSoildColour(Vector3f colour){
		this.useSolidColour = true;
		this.colour = colour;
	}
	
	public void useHighlightedColour(Vector3f colour){
		this.useHighlightedColour = true;
		this.highlightedColour = colour;
	}
	
	public int getTexture() {
		return texture;
	}
	public Vector2f getPosition() {
		return position;
	}
	public Vector2f getScale() {
		return scale;
	}
	public float getTransparency() {
		return transparency;
	}
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}

	public boolean isUseSolidColour() {
		return useSolidColour;
	}

	public Vector3f getColour() {
		return colour;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public boolean isUseHighlightedColour() {
		return useHighlightedColour;
	}

	public Vector3f getHighlightedColour() {
		return highlightedColour;
	}

	public boolean isContain3DModel() {
		return contain3DModel;
	}

	public void setContain3DModel(boolean contain3dModel) {
		contain3DModel = contain3dModel;
	}

	public int getBackgroundTexture() {
		return backgroundTexture;
	}
	
	
	public void addColour(Vector3f colour){
		colours.add(colour);
	}
	
	public List<Vector3f> getColours(){
		return this.colours;
	}
	
	public void setTextureSet2(int texture2, int backgroundTexture2){
		this.backgroundTexture2 = backgroundTexture2;
		this.texture2 = texture2;
	}
	
	public void setTextureSet3(int texture3, int backgroundTexture3){
		this.backgroundTexture3 = backgroundTexture3;
		this.texture3 = texture3;
	}

	public int getTexture2() {
		return texture2;
	}

	public int getBackgroundTexture2() {
		return backgroundTexture2;
	}
	
	

	public int getTexture3() {
		return texture3;
	}

	public int getBackgroundTexture3() {
		return backgroundTexture3;
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
	}

	public float getTransition() {
		return transition;
	}

	public void setTransition(float transition) {
		this.transition = transition;
		if (transition > 1) {
			this.currentState = nextState;
			this.transition = 0;
		}
	}
	
	public void incrementTransition() {
		this.transition += transitSpeed;
		if (transition > 1) {
			this.currentState = nextState;
			this.transition = 0;
		}
	}
	
	
	
	
	
	
	
	
	
}
