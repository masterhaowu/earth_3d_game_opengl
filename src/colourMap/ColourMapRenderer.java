package colourMap;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.RendererController;
import shadows.ShadowShader;
import toolbox.Maths;

public class ColourMapRenderer {
	
	//private Matrix4f projectionViewMatrix;
	private ColourMapShader shader;
	
	
	
	public ColourMapRenderer(ColourMapShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	
	protected void renderWithTrans(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			RawModel rawModel = model.getRawModel();
			bindModel(rawModel);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
			if (model.getTexture().isHasTransparency()) {
				RendererController.disableCulling();
			}
			for (Entity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVerticesCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			if (model.getTexture().isHasTransparency()) {
				RendererController.enableCulling();
			}
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	protected void render(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			RawModel rawModel = model.getRawModel();
			bindModel(rawModel);
			
			for (Entity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVerticesCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	
	
	private void bindModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}
	
	
	private void prepareInstance(Entity entity) {
		Matrix4f modelMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(modelMatrix);
		int colourID = entity.getColourID();
		int r = (colourID & 0x000000FF) >>  0;
		int g = (colourID & 0x0000FF00) >>  8;
		int b = (colourID & 0x00FF0000) >> 16;
		shader.loadPickingColour(r, g, b);
	}
	
	

}
