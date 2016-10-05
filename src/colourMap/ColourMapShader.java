package colourMap;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class ColourMapShader extends ShaderProgram {

	private static final String VERTEX_FILE = "/colourMap/colourMapVertexShader.glsl";
	private static final String FRAGMENT_FILE = "/colourMap/colourMapFragmentShader.glsl";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_outputR;
	private int location_outputG;
	private int location_outputB;
	
	protected ColourMapShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_outputR = super.getUniformLocation("outputR");
		location_outputG = super.getUniformLocation("outputG");
		location_outputB = super.getUniformLocation("outputB");
		
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
	
	
	protected void loadPickingColour(int r, int g, int b){
		super.loadInt(location_outputR, r);
		super.loadInt(location_outputG, g);
		super.loadInt(location_outputB, b);
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		
	}
}
