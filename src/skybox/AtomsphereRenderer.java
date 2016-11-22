package skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.CameraCenter;
import models.RawModel;
import renderEngine.Loader;
import renderEngine.RendererController;
import terrainsSphere.TerrainSphere;
import toolbox.Maths;

public class AtomsphereRenderer {

	//private static final float[] POSITIONS = { -1, 1, 0, -1, -1, 0, 1, 1, 0, 1, -1, 0 };
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private RawModel quad;
	private int texture;
	private AtomsphereShader shader;

	// private float testingRot = 0f;

	public AtomsphereRenderer(Loader loader, Matrix4f projectionMatrix) {
		quad = loader.loadToVAO(POSITIONS, 2);
		texture = loader.loadTexture("atomsphere");
		shader = new AtomsphereShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Camera camera, float r, float g, float b, CameraCenter player) {
		shader.start();
		RendererController.disableCulling();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		// shader.loadViewMatrix(viewMatrix);
		Vector3f polarPos = player.getPolar();
		polarPos = camera.getPolar();
		// polarPos.x = 100;
		Vector3f mirrorPos = Maths.convertBackToCart(new Vector3f(200, polarPos.y, polarPos.z));
		// Matrix4f transformationMatrix = Maths.createTransformationMatrix(
		// new Vector3f(-mirrorPos.x, -mirrorPos.y, -mirrorPos.z),
		// player.getRotX() - camera.getPitchOffset(), player.getRotY(),
		// player.getRotZ(), 1600 - 2 * camera.getDistanceFromPlayer());
		// testingRot += 0.1f;
		// Matrix4f transformationMatrix = Maths.createTransformationMatrix(new
		// Vector3f(0, 0, 0), 0, 0, 0, 500);
		// shader.loadTransformationMatrix(transformationMatrix);
		float atomScale = 1600 - 2 * camera.getDistanceFromPlayer();
		Matrix4f modelViewMatrix = createModelViewMatrix(new Vector3f(-mirrorPos.x, -mirrorPos.y, -mirrorPos.z), 0,
				atomScale, viewMatrix);
		shader.loadModelViewMatrix(modelViewMatrix);
		shader.loadScales(400.0f, atomScale);
		//System.out.println(400/atomScale);
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

	private Matrix4f createModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix) {
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), modelMatrix, modelMatrix);
		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix, modelMatrix, null);
		// storeMatrixData(modelViewMatrix, vboData);
		return modelViewMatrix;
	}

}
