package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entityObjects.EntityObject;
import models.TexturedModel;
import mouse.HighlightedCircle;
import mouse.HighlightedCircleRenderer;
import mouse.HighlightedCircleShader;
import normalMappingRenderer.NormalMappingRenderer;
import shaders.StaticShader;
import shaders.TerrainShader;
import shadows.ShadowMapMasterRenderer;
import skybox.SkyboxRednerer;
import terrains.Terrain;
import terrainsSphere.TerrainSphere;
import terrainsSphere.TerrainSphereRenderer;
import terrainsSphere.TerrainSphereShader;

public class RendererController {

	public static final float FOV = 50;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 2000;

	public static final float RED = 0.4f;
	public static final float GREEN = 0.4f;
	public static final float BLUE = 0.4f;

	private Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;

	//private TerrainRenderer terrainRenderer;
	//private TerrainShader terrainShader = new TerrainShader();
	
	private TerrainSphereRenderer terrainSphereRenderer;
	private TerrainSphereShader terrainSphereShader = new TerrainSphereShader();

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Entity>> normalMapEntities = new HashMap<TexturedModel, List<Entity>>();
	//private List<Terrain> terrains = new ArrayList<Terrain>();

	private SkyboxRednerer skyboxRednerer;

	private NormalMappingRenderer normalMappingRenderer;
	
	private ShadowMapMasterRenderer shadowMapRenderer;
	
	private HighlightedCircleShader highlightedCircleShader = new HighlightedCircleShader();
	private HighlightedCircleRenderer highlightedCircleRenderer;

	public RendererController(Loader loader, Camera camera) {
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		//terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		terrainSphereRenderer = new TerrainSphereRenderer(terrainSphereShader, projectionMatrix);
		skyboxRednerer = new SkyboxRednerer(loader, projectionMatrix);
		normalMappingRenderer = new NormalMappingRenderer(projectionMatrix);
		shadowMapRenderer = new ShadowMapMasterRenderer(camera);
		highlightedCircleRenderer = new HighlightedCircleRenderer(highlightedCircleShader, projectionMatrix);
	}

	public void renderScene(List<EntityObject> entityObjects, List<Entity> normalMapEntities,
			List<Light> lights, Camera camera, Vector4f clipPlane, TerrainSphere terrainSphere) {

		for (EntityObject entityObject : entityObjects) {
			Entity entity = entityObject.getEntity();
			processEntity(entity);
		}
		for (Entity entity : normalMapEntities) {
			processNormalMapEntity(entity);
		}
		render(lights, camera, clipPlane, terrainSphere);

	}

	public void render(List<Light> lights, Camera camera, Vector4f clipPlane, TerrainSphere terrainSphere) {
		prepare();
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		normalMappingRenderer.render(normalMapEntities, clipPlane, lights, camera);
		//terrainShader.start();
		//terrainShader.loadClipPlane(clipPlane);
		//terrainShader.loadSkyColour(RED, GREEN, BLUE);
		//terrainShader.loadLights(lights);
		//terrainShader.loadViewMatrix(camera);
		//terrainShader.loadTerrainGlobalOffset(0.5f);
		//terrainRenderer.render(terrains, shadowMapRenderer.getToShadowMapSpaceMatrix());
		//terrainShader.stop();
		terrainSphereShader.start();
		terrainSphereShader.loadClipPlane(clipPlane);
		terrainSphereShader.loadSkyColour(RED, GREEN, BLUE);
		terrainSphereShader.loadLights(lights);
		terrainSphereShader.loadViewMatrix(camera);
		terrainSphereShader.loadTerrainGlobalOffset(0.5f);
		terrainSphereRenderer.render(terrainSphere, shadowMapRenderer.getToShadowMapSpaceMatrix());
		terrainSphereShader.stop();
		//skyboxRednerer.render(camera, RED, GREEN, BLUE);
		entities.clear();
		//terrains.clear();
		normalMapEntities.clear();
	}
	
	public void renderHightlightedCircle(HighlightedCircle hightlightedCircle, Camera camera){
		disableCulling();
		highlightedCircleShader.start();
		highlightedCircleShader.loadViewMatrix(camera);
		highlightedCircleRenderer.render(hightlightedCircle);
		highlightedCircleShader.stop();
		enableCulling();
	}

	//public void processTerrain(Terrain terrain) {
	//	terrains.add(terrain);
	//}

	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	public void processNormalMapEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = normalMapEntities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			normalMapEntities.put(entityModel, newBatch);
		}
	}

	public void cleanUp() {
		shader.cleanUp();
		//terrainShader.cleanUp();
		terrainSphereShader.cleanUp();
		normalMappingRenderer.cleanUp();
		shadowMapRenderer.cleanUp();
		highlightedCircleShader.cleanUp();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public void renderShadowMap(List<Entity> entitieList, Light sun){
		for (Entity entity:entitieList){
			processEntity(entity);
		}
		shadowMapRenderer.render(entities, sun);
		entities.clear();
	}
	
	public int getShadowMapTexture(){
		return shadowMapRenderer.getShadowMap();
	}
	/*
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * FAR_PLANE * NEAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
	*/
	
	private void createProjectionMatrix(){
    	projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
    }

}
