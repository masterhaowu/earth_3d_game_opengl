package guis;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.RendererController;
import textures.ModelTexture;
import toolbox.Maths;

public class Gui3DObjectRenderer {

	private Gui3DObjectShader shader;

	public Gui3DObjectRenderer() {
		this.shader = new Gui3DObjectShader();
	}

	public void render(List<GuiObjectUnit> guiObjectUnits) {
		// System.out.println("render");
		for (GuiObjectUnit guiObjectUnit : guiObjectUnits) {
			// System.out.println("here");
			TexturedModel model = guiObjectUnit.getModel();
			if (model == null) {
				// System.out.println("here1");
				return;
			}
			// System.out.println("here2");
			shader.start();
			RawModel rawModel = model.getRawModel();
			RendererController.disableCulling();
			GL30.glBindVertexArray(rawModel.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			//GL11.glEnable(GL11.GL_BLEND);
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			//GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			ModelTexture texture = model.getTexture();
			if (texture.isHasTransparency()) {
				RendererController.disableCulling();
			}

			shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
			
			float scaleDown = (rawModel.getModelData().getMax().y - rawModel.getModelData().getMin().y)/2 * guiObjectUnit.getModelScaleDown();
			Vector2f offsets = guiObjectUnit.getPositionOffsets();
			Vector2f position = new Vector2f(guiObjectUnit.getMainOrb().getPosition().x + offsets.x * guiObjectUnit.getMainOrb().getScale().x,
					guiObjectUnit.getMainOrb().getPosition().y + (offsets.y - 1) * guiObjectUnit.getMainOrb().getScale().y);
			Vector2f scale = new Vector2f(guiObjectUnit.getMainOrb().getScale().x / scaleDown,
					guiObjectUnit.getMainOrb().getScale().y / scaleDown);

			Matrix4f matrix = Maths.createTransformationMatrix(position, scale);
			// matrix =
			// Maths.createTransformationMatrix(guiObjectUnit.getMainOrb().getPosition(),
			// new Vector2f(0.01f, 0.03f));
			// System.out.println(guiObjectUnit.getMainOrb().getPosition());
			shader.loadTransformation(matrix);

			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);

			RendererController.enableCulling();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			//GL11.glDisable(GL11.GL_BLEND);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
			shader.stop();
		}
	}

}
