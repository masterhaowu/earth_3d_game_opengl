package guis;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.DisplayManager;

public class GuiTexture {
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	private float transparency;
	
	private boolean useSolidColour;
	private Vector3f colour;
	
	private boolean useHighlightedColour;
	private Vector3f highlightedColour;
	
	private boolean highlighted;
	
	private boolean contain3DModel;
	private RawModel model;
	
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
	
	
	
}
