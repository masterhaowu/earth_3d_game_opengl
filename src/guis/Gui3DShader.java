package guis;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class Gui3DShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "/guis/gui3DVShader.glsl";
	private static final String GEOMETRY_FILE = "/guis/gui3DGShader.glsl";
    private static final String FRAGMENT_FILE = "/guis/gui3DFShader.glsl";
    
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

	public Gui3DShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
 
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
	}

	@Override
	protected void bindAttributes() {
		 super.bindAttribute(0, "position");
		
	}

}
