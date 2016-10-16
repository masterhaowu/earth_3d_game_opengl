package guis;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class Gui3DShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "/guis/gui3DVShader.glsl";
	private static final String GEOMETRY_FILE = "/guis/gui3DGShader.glsl";
    private static final String FRAGMENT_FILE = "/guis/gui3DFShader.glsl";
    
    private int location_transformationMatrix;
    //private int location_projectionMatrix;
    //private int location_viewMatrix;
    private int location_backgroundTexture;
    private int location_iconTexture;

	public Gui3DShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
 
    }
	
	public void connectTextureUnits(){
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_iconTexture, 1);
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
		//location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		//location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_iconTexture = super.getUniformLocation("iconTexture");
	}

	@Override
	protected void bindAttributes() {
		 super.bindAttribute(0, "position");
		
	}

}
