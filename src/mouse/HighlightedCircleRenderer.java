package mouse;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import models.RawModel;
import shaders.StaticShader;
import toolbox.Maths;

public class HighlightedCircleRenderer {

	private HighlightedCircleShader shader;

	public HighlightedCircleRenderer(HighlightedCircleShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	
	public void render(HighlightedCircle hightlightedCircle) {
		prepare(hightlightedCircle);
		//GL11.glDrawElements(GL11.GL_TRIANGLES, hightlightedCircle.getModel().getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
		//System.out.println(hightlightedCircle.getModel().getVerticesCount());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, hightlightedCircle.getModel().getVerticesCount());
		finish();
	}

	public void prepare(HighlightedCircle hightlightedCircle) {
		RawModel rawModel = hightlightedCircle.getModel();
		//System.out.println(rawModel.getVaoID());
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		shader.loadColour(0.92f, 0.87f, 0.2f);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(hightlightedCircle.getPosition(),
				hightlightedCircle.getRotX(), hightlightedCircle.getRotY(), hightlightedCircle.getRotZ(),
				hightlightedCircle.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}

	public void finish() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

}
