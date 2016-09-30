package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


import models.RawModel;
import shaders.TerrainShader;
import shadows.ShadowMapMasterRenderer;
import terrains.Terrain;
//import textures.ModelTexture;
import textures.TerrainTexturePack;
import toolbox.Maths;

public class TerrainRenderer {
	
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix){
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		//shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(List<Terrain> terrains, Matrix4f toShadowSpace){
		shader.loadToShadowMapSpace(toShadowSpace);
		shader.loadMapSize(ShadowMapMasterRenderer.SHADOW_MAP_SIZE);
		for(Terrain terrain:terrains){
			prepareTerrainModel(terrain);
			loadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	
	private void prepareTerrainModel(Terrain terrain) {
		RawModel rawModel = terrain.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		//bindTextures(terrain);
		shader.loadShineVariables(5, 0.0f);

	}
	
	
	
	private void unbindTexturedModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain terrain){
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}
	

}
