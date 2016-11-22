package waterSphere;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import shaders.ShaderProgram;
import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class WaterSphereShader extends ShaderProgram {

	private final static String VERTEX_FILE = "/waterSphere/waterSphereVertex.glsl";
	private final static String GEOMETRY_FILE = "/waterSphere/waterSphereGeometry.glsl";
	private final static String FRAGMENT_FILE = "/waterSphere/waterSphereFragment.glsl";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_dudvMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_normalMap;
	private int location_lightColour;
	private int location_lightPosition;
	private int location_depthMap;
	private int location_time;
	private int location_waterColour;

	public WaterSphereShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "polar");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		//location_reflectionTexture = getUniformLocation("reflectionTexture");
		//location_refractionTexture = getUniformLocation("refractionTexture");
		//location_dudvMap = getUniformLocation("dudvMap");
		location_moveFactor = getUniformLocation("moveFactor");
		location_cameraPosition = getUniformLocation("cameraPosition");
		//location_normalMap = getUniformLocation("normalMap");
		location_lightColour = getUniformLocation("lightColour");
		location_lightPosition = getUniformLocation("lightPosition");
		//location_depthMap = getUniformLocation("depthMap");
		location_time = getUniformLocation("time");
		location_waterColour = getUniformLocation("waterColour");
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_reflectionTexture, 0);
		super.loadInt(location_refractionTexture, 1);
		super.loadInt(location_dudvMap, 2);
		super.loadInt(location_normalMap, 3);
		super.loadInt(location_depthMap, 4);
	}
	
	public void loadTime(float time){
		super.loadFloat(location_time, time);
	}

	public void loadLight(Light sun){
		super.loadVector(location_lightColour, sun.getColour());
		super.loadVector(location_lightPosition, sun.getPosition());
	}
	
	public void loadMoveFactor(float moveFactor){
		super.loadFloat(location_moveFactor, moveFactor);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}
	
	public void loadWaterColour(Vector3f waterColour){
		loadVector(location_waterColour, waterColour);
	}

}
