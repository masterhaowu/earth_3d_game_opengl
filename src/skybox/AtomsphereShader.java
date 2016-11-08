package skybox;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class AtomsphereShader extends ShaderProgram {
	private static final String VERTEX_FILE = "/skybox/atomsphereVertexShader.glsl";
	private static final String FRAGMENT_FILE = "/skybox/atomsphereFragmentShader.glsl";
	
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_transformationMatrix;
	
	public AtomsphereShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix){
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix){
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadViewMatrix(Matrix4f viewMatrix){
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
