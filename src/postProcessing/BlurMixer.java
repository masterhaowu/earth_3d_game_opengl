package postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import renderEngine.RendererController;

public class BlurMixer {
	
	private	ImageRenderer imageRenderer;
	private BlurMixerShader blurMixerShader;
	
	
	public BlurMixer(int width, int height){
		imageRenderer = new ImageRenderer(width, height);
		blurMixerShader = new BlurMixerShader();
		blurMixerShader.start();
		blurMixerShader.connectTextures();
		blurMixerShader.loadViewPlanes(RendererController.FAR_PLANE, RendererController.NEAR_PLANE);
		blurMixerShader.stop();
	}
	
	public void render(int originalColourTexture, int blurredColourTexture, int depthTexture){
		blurMixerShader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, originalColourTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, blurredColourTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthTexture);
		imageRenderer.renderQuad();
		blurMixerShader.stop();
	}
	
	public int getRenderedTexture(){
		return imageRenderer.getOutputTexture();
	}
	
	
	public void cleanUp(){
		imageRenderer.cleanUp();
		blurMixerShader.cleanUp();
	}

}
