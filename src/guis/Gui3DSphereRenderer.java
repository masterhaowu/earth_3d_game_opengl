package guis;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RendererController;
import toolbox.Maths;

public class Gui3DSphereRenderer {

	private static final float ROTATION_SPEED = 0.03f;

	private final RawModel model;
	private Gui3DSphereShader shader;

	private float time;
	private float testing = 0;

	public Gui3DSphereRenderer(Loader loader, Matrix4f projectionMatrix) {
		// float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		// quad = loader.loadToVAO(positions, 2);
		GuiSphereObject guiSphereObject = new GuiSphereObject(loader);
		model = guiSphereObject.getModel();
		shader = new Gui3DSphereShader();
		// shader.start();
		// shader.loadProjectionMatrix(projectionMatrix);
		// shader.stop();
	}

	public void render(List<GuiSphereTexture> guis) {
		updateTime();
		shader.start();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.connectTextureUnits();
		// RendererController.enableCulling();
		// Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		// shader.loadViewMatrix(viewMatrix);
		for (GuiSphereTexture gui : guis) {
			int currentState = gui.getCurrentState();
			int nextState = gui.getNextState();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getCurrentBackgroundTexture(currentState));
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getCurrentTexture(currentState));
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getNextBackgroundTexture(nextState));
			GL13.glActiveTexture(GL13.GL_TEXTURE3);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getNextTexture(nextState));
			
			shader.loadTransparency(gui.getTransparency());
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			// Matrix4f matrix = Maths.createTransformationMatrix(new
			// Vector3f(0, 0, 450), 0, 0, 0, 50);
			shader.loadTransformation(matrix);
			if (gui.isAutoRotate()) {
				Matrix4f rotation = Maths.createTransformationMatrix(new Vector3f(0, 0, 0), -time * 360, 0, 0, 1);
				shader.loadRotationMatrix(rotation);
			}
			else{
				Matrix4f rotation = Maths.createTransformationMatrix(new Vector3f(0, 0, 0), 0, 0, 0, 1);
				shader.loadRotationMatrix(rotation);
			}
			

			if (gui.isUseColours()) {
				shader.loadStates(gui.isUseColours(), gui.getTransition(), gui.getColours().get(currentState),
						gui.getColours().get(nextState), gui.getCurrentScaleDown(currentState),
						gui.getNextScaleDown(nextState));
			} else {
				shader.loadStates(gui.isUseColours(), gui.getTransition(), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0),
						gui.getCurrentScaleDown(currentState), gui.getNextScaleDown(nextState));
			}
			gui.incrementTransition();
			// shader.loadTransparency(gui.getTransparency());
			// shader.loadColourInfo(gui.isUseSolidColour(), gui.getColour());
			// shader.loadHighlightedColourInfo(gui.isUseHighlightedColour(),
			// gui.getHighlightedColour());
			// shader.loadHighlighted(gui.isHighlighted());
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

	private void updateTime() {
		time += DisplayManager.getFrameTimeSeconds() * ROTATION_SPEED;
		time %= 1;
	}

}
