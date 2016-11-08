package skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Player;
import models.RawModel;
import renderEngine.Loader;
import renderEngine.RendererController;
import terrainsSphere.TerrainSphere;
import toolbox.Maths;

public class AtomsphereRenderer {

	private static final float[] POSITIONS = { -1, 1, 0, -1, -1, 0, 1, 1, 0, 1, -1, 0 };
	private RawModel quad;
	private int texture;
	private AtomsphereShader shader;

	public AtomsphereRenderer(Loader loader, Matrix4f projectionMatrix) {
		quad = loader.loadToVAO(POSITIONS, 3);
		texture = loader.loadTexture("atomsphere");
		shader = new AtomsphereShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Camera camera, float r, float g, float b, Player player) {
		shader.start();
		RendererController.disableCulling();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		shader.loadViewMatrix(viewMatrix);
		Vector3f polarPos = player.getPolar();
		// polarPos.x = 100;
		Vector3f mirrorPos = Maths.convertBackToCart(new Vector3f(200, polarPos.y, polarPos.z));
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(
				new Vector3f(-mirrorPos.x, -mirrorPos.y, -mirrorPos.z), player.getRotX() - camera.getPitchOffset(), player.getRotY(),
				player.getRotZ(), 1600 - 2 * camera.getDistanceFromPlayer());
		// Matrix4f transformationMatrix = Maths.createTransformationMatrix(new
		// Vector3f(0, 0, 0), 0, 0, 0, 500);
		shader.loadTransformationMatrix(transformationMatrix);
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVerticesCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL11.glDisable(GL11.GL_BLEND);
		RendererController.enableCulling();
		shader.stop();
	}

}
