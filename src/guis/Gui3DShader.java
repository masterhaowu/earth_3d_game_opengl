package guis;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import shaders.ShaderProgram;

public class Gui3DShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "/guis/gui3DVShader.glsl";
	private static final String GEOMETRY_FILE = "/guis/gui3DGShader.glsl";
    private static final String FRAGMENT_FILE = "/guis/gui3DFShader.glsl";
    
    private int location_transformationMatrix;
    private int location_rotationMatrix;
    private int location_backgroundTexture1;
    private int location_iconTexture1;
    private int location_backgroundTexture2;
    private int location_iconTexture2;
    private int location_useColourFilter;
    private int location_colourFilter1;
    private int location_colourFilter2;
 
    private int location_stateTransition;
    private int location_scaleDown1;
    private int location_scaleDown2;
    

	public Gui3DShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
 
    }
	
	public void loadRotationMatrix(Matrix4f matrix){
		super.loadMatrix(location_rotationMatrix, matrix);
	}
	
	
	
	public void connectTextureUnits(){
		super.loadInt(location_backgroundTexture1, 0);
		super.loadInt(location_iconTexture1, 1);
		super.loadInt(location_backgroundTexture2, 2);
		super.loadInt(location_iconTexture2, 3);
	}
	
	
	
	public void loadStates(boolean useColourFilter, float transition, Vector3f currentColour, Vector3f nextColour, float currentScale, float nextScale){
		super.loadBoolean(location_useColourFilter, useColourFilter);
		super.loadFloat(location_stateTransition, transition);
		super.loadVector(location_colourFilter1, currentColour);
		super.loadVector(location_colourFilter2, nextColour);
		super.loadFloat(location_scaleDown1, currentScale);
		super.loadFloat(location_scaleDown2, nextScale);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_rotationMatrix = super.getUniformLocation("rotationMatrix");
		location_backgroundTexture1 = super.getUniformLocation("backgroundTexture1");
		location_iconTexture1 = super.getUniformLocation("iconTexture1");
		location_backgroundTexture2 = super.getUniformLocation("backgroundTexture2");
		location_iconTexture2 = super.getUniformLocation("iconTexture2");
		location_useColourFilter = super.getUniformLocation("useColourFilter");	
		location_stateTransition = super.getUniformLocation("stateTransition");
		location_scaleDown1 = super.getUniformLocation("scaleDown1");
		location_scaleDown2 = super.getUniformLocation("scaleDown2");
		location_colourFilter1 = super.getUniformLocation("colourFilter1");
		location_colourFilter2 = super.getUniformLocation("colourFilter2");
		
	}

	@Override
	protected void bindAttributes() {
		 super.bindAttribute(0, "position");
		
	}

}
