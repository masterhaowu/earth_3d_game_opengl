package guis;
 
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import shaders.ShaderProgram;
 
public class GuiShader extends ShaderProgram{
     
    private static final String VERTEX_FILE = "/guis/guiVertexShader.glsl";
    private static final String FRAGMENT_FILE = "/guis/guiFragmentShader.glsl";
     
    private int location_transformationMatrix;
    private int location_transparency;
    private int location_useSolidColour;
    private int location_solidColour;
    private int location_useHighlightedColour;
    private int location_highlightedColour;
    private int location_highlighted;
 
    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
    
    public void loadTransparency(float transparency){
    	super.loadFloat(location_transparency, transparency);
    }
    
    public void loadColourInfo(boolean useSolidColour, Vector3f colour){
    	super.loadBoolean(location_useSolidColour, useSolidColour);
    	super.loadVector(location_solidColour, colour);
    }
    
    public void loadHighlightedColourInfo(boolean useHighlightedColour, Vector3f colour){
    	super.loadBoolean(location_useHighlightedColour, useHighlightedColour);
    	super.loadVector(location_highlightedColour, colour);
    }
    
    public void loadHighlighted(boolean highlighted){
    	super.loadBoolean(location_highlighted, highlighted);
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_transparency = super.getUniformLocation("transparency");
        location_useSolidColour = super.getUniformLocation("useSolidColour");
        location_solidColour = super.getUniformLocation("solidColour");
        location_useHighlightedColour = super.getUniformLocation("useHighlightedColour");
        location_highlightedColour = super.getUniformLocation("highlightedColour");
        location_highlighted = super.getUniformLocation("highlighted");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
     
     
     
 
}