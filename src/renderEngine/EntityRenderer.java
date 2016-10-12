package renderEngine;

import java.util.List;
import java.util.Map;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;
import waterSphere.WaterSphereRenderer;

public class EntityRenderer {
	
	

	
	private StaticShader shader;
	private float time;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	
	
	
	public void render(Map<TexturedModel, List<Entity>> entities){
		updateTime();
		for(TexturedModel model:entities.keySet()){
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity:batch){
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model) {
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if (texture.isHasTransparency()) {
			RendererController.disableCulling();
		}
		shader.loadFakeLightingVariables(texture.isUseFakeLighting());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	private void unbindTexturedModel(){
		RendererController.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity){
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
		shader.loadHighlighted(entity.isHighlighted());
		if (entity.isEnableShaderAnimation()) {
			Vector3f centerPos = Vector3f.sub(entity.getModel().getRawModel().getMax(), entity.getModel().getRawModel().getMin(), null);
			shader.loadCenterPos(centerPos);
			shader.loadEnableShaderAnimation(true);
			//shader.loadTime();
		}
		else{
			shader.loadCenterPos(new Vector3f(0, 0, 0));
			shader.loadEnableShaderAnimation(false);
			//shader.loadTime(0);
		}
	}
	
	private void updateTime(){
		time+=DisplayManager.getFrameTimeSeconds() * 0.3f;
		time %= 1;
		//System.out.println(time);
		shader.loadTime(time);
	}
	
	
	
	
}
