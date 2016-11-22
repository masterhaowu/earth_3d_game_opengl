package postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class BlurEffect {
	
	private ImageRenderer imageRenderer;
	private BlurEffectShader blurShader;
	private boolean horizontal = true;
	
	public BlurEffect(int width, int height, boolean isHorizontal){
		this.horizontal = isHorizontal;
		imageRenderer = new ImageRenderer(width, height);
		blurShader = new BlurEffectShader();
		blurShader.start();
		if (isHorizontal) {
			blurShader.loadHorizontalInfo(width);
		} else{
			blurShader.loadVerticalInfo(height);
		}
		
		blurShader.connectTextureUnits();
		blurShader.stop();
	}
	
	public void render(int colourTexture){
		blurShader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colourTexture);
		//GL13.glActiveTexture(GL13.GL_TEXTURE1);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthTexture);
		imageRenderer.renderQuad();
		blurShader.stop();
	}
	
	public int getRenderedTexture(){
		return imageRenderer.getOutputTexture();
	}
	
	
	public void cleanUp(){
		imageRenderer.cleanUp();
		blurShader.cleanUp();
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	

}
