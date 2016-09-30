package waterSphere;

import java.util.List;

import models.RawModel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class WaterSphereRenderer {
	
	private static final String DUDV_MAP = "waterDUDV"; 
	private static final String NORMAL_MAP = "matchingNormalMap"; 
	private static final float WAVE_SPEED = 0.3f;

	//private RawModel quad;
	private WaterSphereShader shader;
	private WaterSphereFrameBuffers fbos;
	
	private int dudvTexture;
	private int normalTexture;
	
	private float moveFactor = 0;
	private float time = 0;

	public WaterSphereRenderer(Loader loader, WaterSphereShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		//this.fbos = fbos;
		//dudvTexture = loader.loadTexture(DUDV_MAP);
		//normalTexture = loader.loadTexture(NORMAL_MAP);
		shader.start();
		//shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		//setUpVAO(loader);
	}

	public void render(List<WaterSphere> water, Camera camera, Light sun) {
		
		//prepareRender(camera, sun);	
		for (WaterSphere tile : water) {
			prepareRender(camera, sun, tile);	
			Matrix4f modelMatrix = Maths.createTransformationMatrix(
					new Vector3f(0, 0, 0), 0, 0, 0,
					400);
			shader.loadModelMatrix(modelMatrix);
			//System.out.print(tile.getModel().getVerticesCount() + "\n");
			//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, tile.getModel().getVerticesCount());
			GL11.glDrawElements(GL11.GL_TRIANGLES, tile.getModel().getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		unbind();
	}
	
	private void prepareRender(Camera camera, Light sun, WaterSphere tile){
		shader.start();
		shader.loadViewMatrix(camera);
		moveFactor += WAVE_SPEED * DisplayManager.getFrameTimeSeconds();
		moveFactor %= 1;
		shader.loadMoveFactor(moveFactor);
		shader.loadLight(sun);
		updateTime();
		GL30.glBindVertexArray(tile.getModel().getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		/*
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, dudvTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, normalTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionDepthTexture());
		*/
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void unbind(){
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	//private void setUpVAO(Loader loader) {
		// Just x and z vectex positions here, y is set to 0 in v.shader
		//float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		//quad = loader.loadToVAO(vertices, 2);
	//}
	
	private void updateTime(){
		time+=DisplayManager.getFrameTimeSeconds()*WAVE_SPEED;
		time %= 1;
		shader.loadTime(time);
	}

}
