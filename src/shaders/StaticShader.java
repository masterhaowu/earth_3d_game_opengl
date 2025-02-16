package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {
	
	private static final int MAX_LIGHT = 4;
	
	private static final String VERTEX_FILE = "/shaders/vertexShader.glsl";
	private static final String GEOMETRY_FILE = "/shaders/geometryShader.glsl";
	private static final String FRAGMENT_FILE = "/shaders/fragmentShader.glsl";
	
	
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColour;
	private int location_numberOfRows;
	private int location_offset;
	private int location_plane;
	private int location_highlighted;
	private int location_centerPosition;
	private int location_enableShaderAnimation;
	private int location_time;
	
	

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
		
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		//super.bindAttribute(2, "normal");
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColour = super.getUniformLocation("skyColour");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_plane = super.getUniformLocation("plane");
		location_highlighted = super.getUniformLocation("highlighted");
		location_centerPosition = super.getUniformLocation("centerPosition");
		location_enableShaderAnimation = super.getUniformLocation("enableShaderAnimation");
		location_time = super.getUniformLocation("time");
		location_lightPosition = new int [MAX_LIGHT];
		location_lightColour = new int [MAX_LIGHT];
		location_attenuation = new int [MAX_LIGHT];
		for (int i=0; i<MAX_LIGHT; i++){
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
		
	}
	
	public void loadEnableShaderAnimation(boolean animation){
		super.loadBoolean(location_enableShaderAnimation, animation);
	}
	
	public void loadCenterPos(Vector3f centerPos){
		super.loadVector(location_centerPosition, centerPos);
	}
	
	public void loadTime(float time){
		super.loadFloat(location_time, time);
	}
	
	public void loadHighlighted(boolean highlighted){
		super.loadBoolean(location_highlighted, highlighted);
	}
	
	
	public void loadClipPlane(Vector4f plane){
		super.load4DVector(location_plane, plane);
	}
	
	public void loadNumberOfRows(int numberOfRows){
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	public void loadOffset(float x, float y){
		super.load2DVector(location_offset, new Vector2f(x,y));
	}
	
	public void loadSkyColour(float r, float g, float b){
		super.loadVector(location_skyColour, new Vector3f(r, g, b));
	}
	
	public void loadFakeLightingVariables(boolean useFakeLight) {
		super.loadBoolean(location_useFakeLighting, useFakeLight);
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights){
		for (int i=0; i<MAX_LIGHT; i++){
			if (i < lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColour[i], lights.get(i).getColour());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			}else{
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
