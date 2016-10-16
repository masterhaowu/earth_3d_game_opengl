package mainGame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import animations.AnimationController;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import entityObjects.EntityObject;
import entityObjects.ObjectsNetwork;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.Gui3DRenderer;
import guis.GuiController;
import guis.GuiData;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import mouse.HighlightedCircle;
import mouse.MouseHighlightController;
import mouse.MousePickerSphere;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import particles.Particle;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RendererController;
//import renderEngine.OBJLoader;
import terrains.Terrain;
import terrainsSphere.ColourController;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainObject;
import terrainsSphere.TerrainSphere;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Maths;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import waterSphere.WaterSphere;
import waterSphere.WaterSphereRenderer;
import waterSphere.WaterSphereShader;

public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DisplayManager.createDisplay();

		Loader loader = new Loader();
		// --------------------------Game State--------------------
		GameStateController.setCurrentState(GameStateController.CREATION_MODE_IDLE);

		// --------------------------Objects Controller
		// -------------------------------------
		ObjectsNetwork.fillObjectsController();

		// -------------------------EntityObjectModel
		// Data-----------------------------------
		EntityObjectModelData.loadAllObjects(loader);

		// --------------------------Animation Controller ---------------------
		AnimationController animationController = new AnimationController();

		// --------------------------Game Entity Object Controller
		// --------------------------
		GameEntityObjectsController gameEntityObjectsController = new GameEntityObjectsController(animationController);

		

		// --------------------------Font----------------------------------------------------
		TextMaster.init(loader);

		FontType font = new FontType(loader.loadFontTexture("candara"), "candara");
		GUIText text = new GUIText("Game Testing", 1, font, new Vector2f(0.8f, 0.95f), 0.2f, true);

		text.setColour(1.0f, 1.0f, 1.0f);

		// staticModel.getTexture().setReflectivity(0.2f);
		// staticModel.getTexture().setShineDamper(2.0f);
		// Entity entity = new Entity(texturedModel, new Vector3f(0, -5, -25),
		// 0, 0, 0, 1);

		ModelData dataPlant = OBJFileLoader.loadOBJ("grassModel");
		RawModel modelPlant = loader.loadToVAO(dataPlant.getVertices(), dataPlant.getTextureCoords(),
				dataPlant.getNormals(), dataPlant.getIndices());
		// RawModel modelPlant = OBJLoader.loadObjModel("grassModel", loader);
		TexturedModel texturedModelPlant = new TexturedModel(modelPlant,
				new ModelTexture(loader.loadTexture("grassTexture")));
		texturedModelPlant.getTexture().setHasTransparency(true);
		texturedModelPlant.getTexture().setUseFakeLighting(true);

		ModelData dataFern = OBJFileLoader.loadOBJ("fern");
		RawModel modelFern = loader.loadToVAO(dataFern.getVertices(), dataFern.getTextureCoords(),
				dataFern.getNormals(), dataFern.getIndices());
		// RawModel modelFern = OBJLoader.loadObjModel("fern", loader);
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		TexturedModel texturedModelFern = new TexturedModel(modelFern, fernTextureAtlas);
		texturedModelFern.getTexture().setHasTransparency(true);

		ModelData dataLamp = OBJFileLoader.loadOBJ("lamp");
		RawModel modelLamp = loader.loadToVAO(dataLamp.getVertices(), dataLamp.getTextureCoords(),
				dataLamp.getNormals(), dataLamp.getIndices());
		// ModelTexture lamp = new ModelTexture(loader.loadTexture("lamp"));
		TexturedModel lamp = new TexturedModel(modelLamp, new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);

		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10.0f);
		barrelModel.getTexture().setReflectivity(0.5f);

		TexturedModel crateModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("crate", loader),
				new ModelTexture(loader.loadTexture("crate")));
		crateModel.getTexture().setNormalMap(loader.loadTexture("crateNormal"));
		crateModel.getTexture().setShineDamper(5.0f);
		crateModel.getTexture().setReflectivity(0.2f);

		TexturedModel boulderModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("boulder", loader),
				new ModelTexture(loader.loadTexture("boulder")));
		boulderModel.getTexture().setNormalMap(loader.loadTexture("boulderNormal"));
		boulderModel.getTexture().setShineDamper(2.0f);
		boulderModel.getTexture().setReflectivity(0.01f);

		// --------------------------Terrain----------------------------------------------------
		/*
		 * TerrainTexture backgroundTexture = new
		 * TerrainTexture(loader.loadTexture("dirt")); TerrainTexture rTexture =
		 * new TerrainTexture(loader.loadTexture("grassy")); TerrainTexture
		 * gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		 * TerrainTexture bTexture = new
		 * TerrainTexture(loader.loadTexture("dirt"));
		 * 
		 * TerrainTexturePack texturePack = new
		 * TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		 * 
		 * TerrainTexture blendMap = new
		 * TerrainTexture(loader.loadTexture("blendMap"));
		 * 
		 * Terrain terrain = new Terrain(10, 9, loader, texturePack, blendMap,
		 * "lake"); List<Terrain> terrains = new ArrayList<Terrain>();
		 * terrains.add(terrain);
		 */
		// Terrain terrain2 = new Terrain(1, 1, loader, texturePack, blendMap,
		// "lake");
		// terrains.add(terrain2);

		// List<Entity> entities = new ArrayList<Entity>();

		// Entity barrel = new Entity(barrelModel, new Vector3f(8400, 15, 7550),
		// 0, 0, 0, 1);
		// normalMapEntities.add(barrel);
		// Entity crate = new Entity(crateModel, new Vector3f(130, 15, -50), 0,
		// 0, 0, 0.1f);
		// normalMapEntities.add(crate);

		Random random = new Random();
		/*
		 * for (int i = 0; i < 100; i++) { float x = random.nextFloat() * 800;
		 * float z = random.nextFloat() * 800; float y =
		 * terrain.getHeightOfTerrain(x, z); Entity tempEntitiy = new
		 * Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0, 1);
		 * //entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0,
		 * 0, 1)); //entities.add(tempEntitiy);
		 * //entitiesWithShadows.add(tempEntitiy);
		 * 
		 * } for (int i = 0; i < 100; i++) { float x = random.nextFloat() * 800
		 * + 8000; float z = random.nextFloat() * 800 + 7200; float y =
		 * terrain.getHeightOfTerrain(x, z); float rotX = random.nextFloat() *
		 * 180; float rotY = random.nextFloat() * 180; float rotZ =
		 * random.nextFloat() * 180; Entity tempEntity = new
		 * Entity(boulderModel, new Vector3f(x, y, z), rotX, rotY, rotZ, 1);
		 * //normalMapEntities.add(new Entity(boulderModel, new Vector3f(x, y,
		 * z), rotX, rotY, rotZ, 1)); //normalMapEntities.add(tempEntity);
		 * //entitiesWithShadows.add(tempEntity); }
		 */

		// --------------------------TerrainSphere---------------------------------------------
		TerrainSphere terrainSphere = new TerrainSphere(loader, 6, 400f);
		ColourController colourController = new ColourController(terrainSphere);

		// --------------------------Lights----------------------------------------------------
		List<Light> lights = new ArrayList<Light>();

		Light sun = new Light(new Vector3f(1840, -1700, 1760), new Vector3f(1.6f, 1.6f, 1.6f));
		Light sun2 = new Light(new Vector3f(1840, 1700, 1760), new Vector3f(1.2f, 1.2f, 1.2f));

		lights.add(sun);
		lights.add(sun2);
		// lights.add(sun2);
		// lights.add(new Light(new Vector3f(8410,
		// terrain.getHeightOfTerrain(8410, 7610) + 10f, 7610), new Vector3f(2,
		// 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		// lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2,
		// 2), new Vector3f(1, 0.01f, 0.002f)));
		// lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2,
		// 0), new Vector3f(1, 0.01f, 0.002f)));

		// entities.add(new Entity(lamp, new Vector3f(8410,
		// terrain.getHeightOfTerrain(8410, 7610), 7610), 0, 0, 0, 1));
		// entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0,
		// 1));
		// entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0,
		// 0, 1));
		// Terrain terrain = new Terrain(0, -1, loader, new
		// ModelTexture(loader.loadTexture("grass")));
		// Terrain terrain2 = new Terrain(-1, -1, loader, new
		// ModelTexture(loader.loadTexture("grass")));

		// Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap,
		// "heightmap");

		// --------------------------Player----------------------------------------------------
		ModelData dataBunny = OBJFileLoader.loadOBJ("person");
		RawModel modelBunny = loader.loadToVAO(dataBunny.getVertices(), dataBunny.getTextureCoords(),
				dataBunny.getNormals(), dataBunny.getIndices());
		TexturedModel texturedModelBunny = new TexturedModel(modelBunny,
				new ModelTexture(loader.loadTexture("playerTexture")));

		// Player player = new Player(texturedModelBunny, new Vector3f(8400, 0,
		// 7600), 0, 180, 0, 1);
		Player player = new Player(texturedModelBunny, new Vector3f(0, 0, terrainSphere.getScale() + 10), 0, 0, 0, 1f);
		// Player player = new Player(texturedModelBunny, new Vector3f(0, 0, 0),
		// 90, 180, 0, 1);
		// Player player = new Player(texturedModelBunny, new Vector3f(0, 0, 0),
		// 0, 180, 0, 1);
		// entitiesWithShadows.add(player);
		Camera camera = new Camera(player);
		camera.move();

		RendererController renderer = new RendererController(loader, camera);

		ParticleMaster.init(loader, renderer.getProjectionMatrix());

		Entity deer1Entity = new Entity(EntityObjectModelData.deer1Model, new Vector3f(0, 0, terrainSphere.getScale()),
				90, 0, 0, 5);
				// entities.add(deerEntity);
				// System.out.println(deerEntity.getModel().getRawModel().getVaoID());

		// --------------------------EntitySphere----------------------------------------------------
		for (int i = 0; i < 60; i++) {
			float theta1 = (float) (random.nextFloat() * Math.PI * 2 - Math.PI);
			float theta2 = (float) (random.nextFloat() * Math.PI - Math.PI / 2);
			// theta1 = 0;
			// theta2 = (float) (Math.PI/2 - 0.1 * i);
			// float y = terrain.getHeightOfTerrain(x, z);
			// float radius = terrainSphere.getHeight(theta1, theta2);

			// float radius = terrainSphere.getHeightAdvanced(theta1, theta2);
			// Vector3f entityPos = Maths.convertBackToCart(new Vector3f(radius,
			// theta1, theta2));
			Vector3f entityPos = terrainSphere.getPositionAdvanced(theta1, theta2);
			// Entity tempEntitiy = new
			// Entity(EntityObjectModelData.testingTreeModel, entityPos, 90, 0,
			// 0, 2f);
			EntityObject tempEntityObject = gameEntityObjectsController
					.createEntityObject(EntityObjectModelData.testingTreeModel, ObjectsNetwork.simpleTree);
			// tempEntitiy.updateRotation();
			// entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0,
			// 0, 1));
			// EntityObject tempEntityObject = new EntityObject(tempEntitiy,
			// ObjectsNetwork.simpleTree);
			tempEntityObject.getEntity().setPosition(entityPos);
			tempEntityObject.getEntity().updateRotation();
			// tempEntityObject.getEntity().setEnableShaderAnimation(false);
			tempEntityObject.getEntity().setupScaleAnimation(0.02f, 1f);
			animationController.addEntityWithScaleAnimation(tempEntityObject.getEntity(), 0.02f, 1f);
			if (tempEntityObject.checkObjectCanExistOnTerrain(terrainSphere)) {
				// entities.add(tempEntitiy);
				// entityObjects.add(tempEntityObject);
				// System.out.println("here");
				gameEntityObjectsController.addEntityObject(tempEntityObject);
			}

			// entitiesWithShadows.add(tempEntitiy);

		}
		// Entity tempEntitiy = new Entity(EntityObjectModelData.lowGrass1Model,
		// new Vector3f(0, 0, -5), 90, 0, 0, 0.2f);
		// EntityObject tempEntityObject = new EntityObject(tempEntitiy,
		// ObjectsNetwork.lowGrass1);
		// entityObjects.add(tempEntityObject);

		// --------------------------GUI----------------------------------------------------
		// List<GuiTexture> guis = new ArrayList<GuiTexture>();
		// GuiTexture gui = new GuiTexture(loader.loadTexture("greyLowPoly"),
		// new Vector2f(0f, 0f), new Vector2f(0.75f, 0.75f));
		// guis.add(gui);
		// GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(),
		// new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
		// guis.add(shadowMap);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		Gui3DRenderer gui3dRenderer = new Gui3DRenderer(loader, renderer.getProjectionMatrix());

		// --------------------------MousePicker----------------------------------------------------
		// MousePicker picker = new MousePicker(camera,
		// renderer.getProjectionMatrix(), terrain);

		Entity thirdLamp = new Entity(lamp, new Vector3f(293, -6.8f, -305), 90, 0, 0, 1);
		Entity secondLamp = new Entity(lamp, new Vector3f(293, -6.8f, -305), 90, 0, 0, 1);
		Entity firstLamp = new Entity(lamp, new Vector3f(293, -6.8f, -305), 90, 0, 0, 1);
		// Light thirdLight = new Light(new Vector3f(293, 7, -305), new
		// Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f));

		// entities.add(thirdLamp);
		// entities.add(secondLamp);
		// entities.add(firstLamp);
		// lights.add(thirdLight);
		MousePickerSphere picker = new MousePickerSphere(camera, renderer.getProjectionMatrix(), terrainSphere);

		// --------------------------Water----------------------------------------------------
		/*
		 * WaterFrameBuffers fbos = new WaterFrameBuffers();
		 * 
		 * WaterShader waterShader = new WaterShader(); WaterRenderer
		 * waterRenderer = new WaterRenderer(loader, waterShader,
		 * renderer.getProjectionMatrix(), fbos); List<WaterTile> waters = new
		 * ArrayList<WaterTile>(); WaterTile water = new WaterTile(8400, 7600,
		 * 0, loader); waters.add(water);
		 */

		// --------------------------WaterSphere----------------------------------------------------

		WaterSphereShader waterSphereShader = new WaterSphereShader();
		WaterSphereRenderer waterSphereRenderer = new WaterSphereRenderer(loader, waterSphereShader,
				renderer.getProjectionMatrix());
		List<WaterSphere> waters = new ArrayList<WaterSphere>();
		WaterSphere water = new WaterSphere(terrainSphere, 1, loader);
		waters.add(water);

		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("particleAtlas"), 4, false);

		ParticleSystem particleSystem = new ParticleSystem(particleTexture, 150, 35, 0.3f, 4, 1);
		particleSystem.randomizeRotation();
		particleSystem.setDirection(new Vector3f(0, 1, 0), 0.1f);
		particleSystem.setLifeError(0.1f);
		particleSystem.setSpeedError(0.5f);
		particleSystem.setScaleError(0.8f);

		ParticleTexture particleTextureFire = new ParticleTexture(loader.loadTexture("fire"), 8, true);
		ParticleSystem particleSystemFire = new ParticleSystem(particleTextureFire, 2, 1, 0, 6, 50);

		// --------------------------FBO----------------------------------------------------
		Fbo fbo = new Fbo(Display.getWidth(), Display.getWidth(), Fbo.DEPTH_RENDER_BUFFER);
		PostProcessing.init(loader);

		// terrainSphere.updataColourVBO(loader);
		// TerrainFace testFace =
		// terrainSphere.getTargetFace(player.getPolar().y,
		// player.getPolar().z);
		// terrainSphere.setFaceColour(testFace, new Vector3f(0, 0, 0), loader);

		// TerrainFace testFace =
		// terrainSphere.getTargetFace(player.getPolar().y,
		// player.getPolar().z);
		// terrainSphere.addObjectToFace(testFace, highlightObject, loader);

		boolean test = true;

		// currentFace = terrainSphere.getTargetFacePlucker(player.getPolar().y,
		// player.getPolar().z);
		/*
		 * for (int i=-180; i<180; i++){ for(int j=-180; j<540; j++){
		 * currentFace =
		 * terrainSphere.getTargetFacePlucker((float)Math.toRadians((float)i/2f)
		 * , (float)Math.toRadians((float)j/2));
		 * terrainSphere.addObjectToFace(currentFace, highlightObject, loader);
		 * } }
		 */

		// ----------------highlightedCircle----------------------
		HighlightedCircle highlightedCircle = new HighlightedCircle(loader, 2, 3, 36);
		highlightedCircle.setPosition(new Vector3f(0, 0, 450));
		highlightedCircle.setScale(1);
		highlightedCircle.calculateCirclePositionOnSphere(terrainSphere);
		highlightedCircle.updatePositionVBO(loader);

		// -------------------mouseHighlightController---------------------
		// MouseHighlightController mouseHighlightController = new
		// MouseHighlightController(picker, colourController, loader,
		// highlightedCircle, terrainSphere);

		// -------------------Event Controller ----------------------------
		GameEventController eventController = new GameEventController(picker, colourController, loader, highlightedCircle,
				terrainSphere, gameEntityObjectsController);
		// mouseController.setObjectToAdd(tempEntityObject);
		
		// -------------------GUI Controller-------------------------------
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui3dTesting = new GuiTexture(loader.loadTexture("planet"), loader.loadTexture("blueCircle"), new Vector2f(-0.85f, -0.76f), 0.2f);
		//System.out.println(gui3dTesting.getPosition());
		guis.add(gui3dTesting);

		while (!Display.isCloseRequested()) {
			// float newHeight = (float) (water.getHeight() + 0.1 *
			// DisplayManager.getFrameTimeSeconds());
			// water.setHeight(newHeight);
			// entity.increasePosition((float) 0, 0, 0);
			// entity.increaseRotation(0, 1, 0);
			// barrel.setRotY(barrel.getRotY() + 2f *
			// DisplayManager.getFrameTimeSeconds());
			// barrel.increaseRotation(0, 1, 0);
			// crate.increaseRotation(0, 1, 0);
			// picker.update2();
			// player.move(terrains, picker);
			player.move(terrainSphere);
			camera.move();

			particleSystem.generateParticles(player.getPosition());
			particleSystemFire.generateParticles(new Vector3f(150, 10, -150));

			ParticleMaster.update(camera);

			renderer.renderShadowMap(gameEntityObjectsController.getEntitiesWithShadows(), sun);
			// Vector3f terrainPoint = picker.getCurrentTerrainPoint();
			// System.out.println(entities.size());
			// mouseHighlightController.checkMousePicking(entityObjects);

			animationController.update();

			eventController.updateMouse(gameEntityObjectsController.getEntityObjects());
			
			// System.out.println(terrainPoint);

			// if (terrainPoint != null) {
			// thirdLamp.setPosition(terrainPoint);

			// }

			// System.out.println(picker.getCurrentRay());
			/*
			 * GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			 * 
			 * 
			 * fbos.bindRefractionFrameBuffer(); renderer.processEntity(player);
			 * renderer.renderScene(entities, normalMapEntities, terrains,
			 * lights, camera, new Vector4f(0, -1, 0, water.getHeight() + 1f),
			 * terrainSphere); // renderer.processEntity(player);
			 * fbos.bindReflectionFrameBuffer();
			 * 
			 * float distance = 2f * (camera.getPosition().y -
			 * water.getHeight()); camera.getPosition().y -= distance;
			 * camera.invertPitch(); camera.invertRoll();
			 * renderer.processEntity(player); renderer.renderScene(entities,
			 * normalMapEntities, terrains, lights, camera, new Vector4f(0, 1,
			 * 0, -water.getHeight() - 1f ), terrainSphere);
			 * camera.getPosition().y += distance; camera.invertPitch();
			 * camera.invertRoll(); fbos.unbindCurrentFrameBuffer();
			 * 
			 * GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			 */
			// GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			// renderer.processEntity(player);

			// fbo.bindFrameBuffer();

			renderer.renderScene(gameEntityObjectsController.getEntityObjects(),
					gameEntityObjectsController.getNormalMapEntities(), lights, camera, new Vector4f(0, 1, 0, 50),
					terrainSphere);
			// waterRenderer.render(waters, camera, sun);
			if (eventController.isShowCircle()) {
				renderer.renderHightlightedCircle(highlightedCircle, camera);
			}

			waterSphereRenderer.render(waters, camera, sun);

			// fbo.unbindFrameBuffer();
			// GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			// ParticleMaster.renderParticles(camera);
			// PostProcessing.doPostProcessing(fbo.getColourTexture());

			//guiRenderer.render(eventController.getGuisToDisplay());
			gui3dRenderer.render(guis, camera);

			TextMaster.render();

			DisplayManager.updateDisplay();
		}

		PostProcessing.cleanUp();
		fbo.cleanUp();
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		// fbos.cleanUp();
		// waterShader.cleanUp();
		waterSphereShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
