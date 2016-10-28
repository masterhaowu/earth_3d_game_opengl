package particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import shaders.ShaderProgram;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "/particles/particleVShader.glsl";
	private static final String FRAGMENT_FILE = "/particles/particleFShader.glsl";

	private int location_numberOfRows;
	private int location_projectionMatrix;
	private int location_useColourFilter;
	private int location_filterColour;


	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_useColourFilter = super.getUniformLocation("useColourFilter");
		location_filterColour = super.getUniformLocation("filterColour");
		
	}

	@Override
	
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");
	}
	
	protected void loadFilterInfo(boolean useColourFilter, Vector3f colour){
		super.loadBoolean(location_useColourFilter, useColourFilter);
		super.loadVector(location_filterColour, colour);
	}
	

	protected void loadNumberOfRows(float numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}

	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

}
