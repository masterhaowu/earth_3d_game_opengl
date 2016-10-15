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
import renderEngine.Loader;
import toolbox.Maths;

public class Gui3DRenderer {
	
	private final RawModel model;
	private Gui3DShader shader;
	
	public Gui3DRenderer(Loader loader, Matrix4f projectionMatrix){
		//float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		//quad = loader.loadToVAO(positions, 2);
		GuiSphereObject guiSphereObject = new GuiSphereObject(loader);
		model = guiSphereObject.getModel();
		shader = new Gui3DShader();
		//shader.start();
		//shader.loadProjectionMatrix(projectionMatrix);
		//shader.stop();
	}
	
	public void render(List<GuiTexture> guis, Camera camera){
		shader.start();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		//shader.loadViewMatrix(viewMatrix);
		for(GuiTexture gui:guis){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			//Matrix4f matrix = Maths.createTransformationMatrix(new Vector3f(0, 0, 450), 0, 0, 0, 50);
			shader.loadTransformation(matrix);
			//shader.loadTransparency(gui.getTransparency());
			//shader.loadColourInfo(gui.isUseSolidColour(), gui.getColour());
			//shader.loadHighlightedColourInfo(gui.isUseHighlightedColour(), gui.getHighlightedColour());
			//shader.loadHighlighted(gui.isHighlighted());
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}

}
