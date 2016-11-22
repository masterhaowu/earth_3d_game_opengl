package skybox;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class AtomsphereShader extends ShaderProgram {
	private static final String VERTEX_FILE = "/skybox/atomsphereVertexShader.glsl";
	private static final String FRAGMENT_FILE = "/skybox/atomsphereFragmentShader.glsl";
	
	
	private int location_projectionMatrix;
	//private int location_viewMatrix;
	//private int location_transformationMatrix;
	
	private int location_modelViewMatrix;
	
	private int location_terrainScale;
	private int location_atomScale;
	
	//private int location_zoom;
	
	public AtomsphereShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix){
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	/*
	public void loadTransformationMatrix(Matrix4f transformationMatrix){
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix){
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	*/
	public void loadModelViewMatrix(Matrix4f modelViewMatrix){
		super.loadMatrix(location_modelViewMatrix, modelViewMatrix);
	}
	
	public void loadScales(float terrainScale, float atomScale){
		super.loadFloat(location_terrainScale, terrainScale);
		super.loadFloat(location_atomScale, atomScale);
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		//location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		//location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		//location_zoom = super.getUniformLocation("zoom");
		location_terrainScale = super.getUniformLocation("terrainScale");
		location_atomScale = super.getUniformLocation("atomScale");
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
