package guis;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class Gui3DObjectShader extends ShaderProgram {

	
	private static final String VERTEX_FILE = "/guis/guiObject3DVShader.glsl";
	private static final String GEOMETRY_FILE = "/guis/guiObject3DGShader.glsl";
	private static final String FRAGMENT_FILE = "/guis/guiObject3DFShader.glsl";
	
	
	private int location_transformationMatrix;
    private int location_rotationMatrix;
    private int location_shineDamper;
    private int location_reflectivity;
	
	
	
	public Gui3DObjectShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
 
    }
	
	public void loadRotationMatrix(Matrix4f matrix){
		super.loadMatrix(location_rotationMatrix, matrix);
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	



	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_rotationMatrix = super.getUniformLocation("rotationMatrix");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_shineDamper = super.getUniformLocation("shineDamper");
		
	}



	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		
	}
}
