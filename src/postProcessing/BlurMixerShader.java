package postProcessing;

import shaders.ShaderProgram;

public class BlurMixerShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "/postProcessing/blurMixerVertex.glsl";
	private static final String FRAGMENT_FILE = "/postProcessing/blurMixerFragment.glsl";
	
	
	private int location_originalColourTexture;
	private int location_blurredColourTexture;
	private int location_depthTexture;
	private int location_far;
	private int location_near;
	
	public BlurMixerShader(){
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void connectTextures(){
		super.loadInt(location_originalColourTexture, 0);
		super.loadInt(location_blurredColourTexture, 1);
		super.loadInt(location_depthTexture, 2);
	}
	
	public void loadViewPlanes(float far, float near){
		super.loadFloat(location_far, far);
		super.loadFloat(location_near, near);
	}


	@Override
	protected void getAllUniformLocations() {
		location_originalColourTexture = super.getUniformLocation("originalColourTexture");
		location_blurredColourTexture = super.getUniformLocation("blurredColourTexture");
		location_depthTexture = super.getUniformLocation("depthTexture");
		location_far = super.getUniformLocation("far");
		location_near = super.getUniformLocation("near");
		
	}


	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
