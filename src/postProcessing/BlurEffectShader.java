package postProcessing;

import shaders.ShaderProgram;

public class BlurEffectShader extends ShaderProgram {

	private static final String VERTEX_FILE = "/postProcessing/blurVertex.glsl";
	private static final String FRAGMENT_FILE = "/postProcessing/blurFragment.glsl";
	
	
	private int location_colourTexture;
	//private int location_depthTexture;
	private int location_targetWidth;
	private int location_targetHeight;
	private int location_horizontal;
	
	public BlurEffectShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_colourTexture, 0);
		//super.loadInt(location_depthTexture, 1);
	}
	
	
	public void loadHorizontalInfo(float targetWidth){
		super.loadFloat(location_targetWidth, targetWidth);
		super.loadBoolean(location_horizontal, true);
	}
	
	public void loadVerticalInfo(float targetHeight){
		super.loadFloat(location_targetHeight, targetHeight);
		super.loadBoolean(location_horizontal, false);
	}
	
	
	@Override
	protected void getAllUniformLocations() {	
		location_targetWidth = super.getUniformLocation("targetWidth");
		location_targetHeight = super.getUniformLocation("targetHeight");
		location_colourTexture = super.getUniformLocation("colourTexture");
		//location_depthTexture = super.getUniformLocation("depthTexture");
		location_horizontal = super.getUniformLocation("horizontal");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	
}
