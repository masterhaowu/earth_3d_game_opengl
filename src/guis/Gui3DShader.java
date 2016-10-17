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
    //private int location_projectionMatrix;
    //private int location_viewMatrix;
    private int location_backgroundTexture;
    private int location_iconTexture;
    private int location_backgroundTexture2;
    private int location_iconTexture2;
    private int location_backgroundTexture3;
    private int location_iconTexture3;
    private int location_useColourFilter;
    private int location_colourFilter[];
    private int location_currentState;
    private int location_nextState;
    private int location_stateTransition;
    

	public Gui3DShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
 
    }
	
	public void loadRotationMatrix(Matrix4f matrix){
		super.loadMatrix(location_rotationMatrix, matrix);
	}
	
	public void loadColourFilterInfo(boolean useFilter, List<Vector3f> colours){
		super.loadBoolean(location_useColourFilter, useFilter);
		//super.loadVector(location_colourFilter, colour);
		for (int i=0; i<3; i++){
			if (i < colours.size()) {
				super.loadVector(location_colourFilter[i], colours.get(i));
			}
			else{
				super.loadVector(location_colourFilter[i], new Vector3f(0, 0, 0));
			}
		}
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_iconTexture, 1);
		super.loadInt(location_backgroundTexture2, 2);
		super.loadInt(location_iconTexture2, 3);
		super.loadInt(location_backgroundTexture3, 4);
		super.loadInt(location_iconTexture3, 5);
	}
	
	public void loadSingleState(int state){
		super.loadInt(location_currentState, state);
		super.loadInt(location_nextState, state);
		super.loadFloat(location_stateTransition, 0);
	}
	
	public void loadStates(int currentState, int nextState, float value){
		super.loadInt(location_currentState, currentState);
		super.loadInt(location_nextState, nextState);
		super.loadFloat(location_stateTransition, value);
	}
	
	
	/*
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix){
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	*/
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_rotationMatrix = super.getUniformLocation("rotationMatrix");
		//location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		//location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_iconTexture = super.getUniformLocation("iconTexture");
		location_backgroundTexture2 = super.getUniformLocation("backgroundTexture2");
		location_iconTexture2 = super.getUniformLocation("iconTexture2");
		location_backgroundTexture3 = super.getUniformLocation("backgroundTexture3");
		location_iconTexture3 = super.getUniformLocation("iconTexture3");
		location_useColourFilter = super.getUniformLocation("useColourFilter");
		//location_colourFilter = super.getUniformLocation("colourFilter");
		location_currentState = super.getUniformLocation("currentState");
		location_nextState = super.getUniformLocation("nextState");
		location_stateTransition = super.getUniformLocation("stateTransition");
		
		location_colourFilter = new int[3];
		for (int i=0; i<3; i++){
			location_colourFilter[i] = super.getUniformLocation("colourFilter[" + i + "]");
		}
	}

	@Override
	protected void bindAttributes() {
		 super.bindAttribute(0, "position");
		
	}

}
