package mouse;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class HighlightedCircleShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "/mouse/highlightedCircleVertexShader.glsl";
	private static final String FRAGMENT_FILE = "/mouse/highlightedCircleFragmentShader.glsl";
	
	public HighlightedCircleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_colour;

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_colour = super.getUniformLocation("circleColour");
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadColour(float r, float g, float b){
		super.loadVector(location_colour, new Vector3f(r, g, b));
	}
	
	
	
}
