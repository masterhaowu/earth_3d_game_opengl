package postProcessing;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import renderEngine.Loader;

public class PostProcessing {
	
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };	
	private static RawModel quad;
	private static ContrastChanger contrastChanger;
	private static BlurEffect blurEffectHorizontal;
	private static BlurEffect blurEffectVertical;
	private static BlurMixer blurMixer;
	

	public static void init(Loader loader){
		quad = loader.loadToVAO(POSITIONS, 2);
		contrastChanger = new ContrastChanger();
		blurEffectHorizontal = new BlurEffect(Display.getWidth()/4, Display.getHeight()/4, true);
		blurEffectVertical = new BlurEffect(Display.getWidth()/6, Display.getHeight()/6, false);
		blurMixer = new BlurMixer(Display.getWidth(), Display.getHeight());
	}
	
	public static void doPostProcessing(int colourTexture, int depthTexture){
		start();
		blurEffectHorizontal.render(colourTexture);
		blurEffectVertical.render(blurEffectHorizontal.getRenderedTexture());
		//blurEffectVertical.render(colourTexture);
		blurMixer.render(colourTexture, blurEffectVertical.getRenderedTexture(), depthTexture);
		contrastChanger.render(blurMixer.getRenderedTexture());
		//contrastChanger.render(blurEffectVertical.getRenderedTexture());
		end();
	}
	
	public static void cleanUp(){
		contrastChanger.cleanUp();
	}
	
	private static void start(){
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	private static void end(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}


}
