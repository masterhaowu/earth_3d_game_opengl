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
import climate.HumidityController;
import climate.TemperatureController;
import clock.GameTimeController;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.CameraCenter;
import entityGamePlay.EntityCycleController;
import entityGamePlay.EntityGrowthController;
import entityObjects.EntityObject;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.FontController;
import fontRendering.TextMapController;
import fontRendering.TextMaster;
import gameDataBase.EntityModelDataBase;
import gameDataBase.ObjectsNetwork;
import gameDataBase.PredationNetwork;
import guiDataBase.GuiData;
import guiEvents.GuiEventController;
import guis.Gui3DObjectRenderer;
import guis.Gui3DObjectShader;
import guis.Gui3DSphereRenderer;
import guis.GuiRenderer;
import guis.GuiRendererController;
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
import particles.SnowSystem;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RendererController;
import shadows.ShadowMapMasterRenderer;
//import renderEngine.OBJLoader;
import terrains.Terrain;
import terrainsSphere.ColourController;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainObject;
import terrainsSphere.TerrainSphere;
import terrainsSphere.TerrainTypeController;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import time.TimeController;
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

		// ----Game Timer-----
		GameTimeController.init();

		// -----Loader-----
		Loader loader = new Loader();
		// --------------------------Game State--------------------
		GameStateController.setCurrentState(GameStateController.PLAY_MODE_IDLE);
		GameStateController.gameModeState = GameStateController.CREATION_TERRAIN_MODE;

		// --------------------------Objects Controller
		// -------------------------------------
		ObjectsNetwork.fillObjectsController();
		// ----------Predation Network----------
		PredationNetwork.fillPredationNetwork();

		// -------------------------EntityObjectModel
		// Data-----------------------------------
		EntityModelDataBase.loadAllObjects(loader);

		// --------------------------Animation Controller ---------------------
		AnimationController animationController = new AnimationController();

		// --------------------------TerrainSphere---------------------------------------------
		TerrainSphere terrainSphere = new TerrainSphere(loader, 6, 400f);
		terrainSphere.connectAllFacesWithNeighbors(TerrainSphere.FACE_NEIGHBOR_RANGE);

		// --------------------------Game Entity Object Controller
		// --------------------------
		GameEntityObjectsController gameEntityObjectsController = new GameEntityObjectsController(animationController);

		// ---------------Game Play Controllers-----------
		// EntityGrowthController entityGrowthController = new
		// EntityGrowthController();
		EntityCycleController entityCycleController = new EntityCycleController();

		// --------------------------Font----------------------------------------------------
		// TextMaster.init(loader);
		FontController.init(loader);
		// TextMapController textMapController = new TextMapController(loader);

		// FontType font = new FontType(loader.loadFontTexture("candara"),
		// "candara");
		GUIText text = new GUIText("testing", 3f, FontController.candara, new Vector2f(0.5f, 0.5f), 0.2f, true);
		text.loadText(loader);

		// ----------------Gui Renderer--------------------------
		GuiRendererController guiRendererController = new GuiRendererController(loader);

		Random random = new Random();

		// ------------Climate Particle Systems------------------------------
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("glow"), 1, false);
		particleTexture.useColour(new Vector3f(1, 1, 1));
		SnowSystem snowSystem = new SnowSystem(particleTexture, terrainSphere);

		// ------------------TerrainTypeControllers--------------------------
		ColourController colourController = new ColourController(terrainSphere);
		TemperatureController temperatureController = new TemperatureController(terrainSphere);
		// temperatureController.updateSphereTemp();
		HumidityController humidityController = new HumidityController(terrainSphere);

		TerrainTypeController terrainTypeController = new TerrainTypeController(loader, terrainSphere, colourController,
				temperatureController, humidityController, snowSystem);
		terrainTypeController.updateAllFaces();

		// --------------------------Lights----------------------------------------------------
		List<Light> lights = new ArrayList<Light>();

		Light sun = new Light(new Vector3f(1000, 1000, 1000), new Vector3f(1.3f, 1.3f, 1.3f));
		Light sun2 = new Light(new Vector3f(1000, 0, 1000), new Vector3f(1.2f, 1.2f, 1.2f));
		Light sun3 = new Light(new Vector3f(1000, -1000, 1000), new Vector3f(1.2f, 1.2f, 1.2f));

		lights.add(sun);
		lights.add(sun2);
		lights.add(sun3);
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
		CameraCenter player = new CameraCenter(texturedModelBunny, new Vector3f(0, 0, terrainSphere.getScale() + 10), 0, 0, 0, 1f);
		// Player player = new Player(texturedModelBunny, new Vector3f(0, 0, 0),
		// 90, 180, 0, 1);
		// Player player = new Player(texturedModelBunny, new Vector3f(0, 0, 0),
		// 0, 180, 0, 1);
		// entitiesWithShadows.add(player);
		Camera camera = new Camera(player);
		camera.move();

		RendererController renderer = new RendererController(loader, camera);

		ParticleMaster.init(loader, renderer.getProjectionMatrix());

		Entity deer1Entity = new Entity(EntityModelDataBase.deer1Model, new Vector3f(0, 0, terrainSphere.getScale()),
				90, 0, 0, 5);
		// entities.add(deerEntity);
		// System.out.println(deerEntity.getModel().getRawModel().getVaoID());

		MousePickerSphere picker = new MousePickerSphere(camera, renderer.getProjectionMatrix(), terrainSphere);

		// --------------------------WaterSphere----------------------------------------------------

		WaterSphereShader waterSphereShader = new WaterSphereShader();
		WaterSphereRenderer waterSphereRenderer = new WaterSphereRenderer(loader, waterSphereShader,
				renderer.getProjectionMatrix());
		List<WaterSphere> waters = new ArrayList<WaterSphere>();
		WaterSphere water = new WaterSphere(terrainSphere, 1, loader);
		waters.add(water);

		/*
		 * ParticleSystem particleSystem = new ParticleSystem(particleTexture,
		 * 150, 35, 0.3f, 4, 1); particleSystem.randomizeRotation();
		 * particleSystem.setDirection(new Vector3f(0, 1, 0), 0.1f);
		 * particleSystem.setLifeError(0.1f);
		 * particleSystem.setSpeedError(0.5f);
		 * particleSystem.setScaleError(0.8f);
		 */
		// ParticleTexture particleTextureFire = new
		// ParticleTexture(loader.loadTexture("fire"), 8, true);
		// ParticleSystem particleSystemFire = new
		// ParticleSystem(particleTextureFire, 2, 1, 0, 6, 50);

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
		GameEventController eventController = new GameEventController(picker, colourController, loader,
				highlightedCircle, terrainSphere, gameEntityObjectsController);
				// mouseController.setObjectToAdd(tempEntityObject);

		// -------------------GUI Controller-------------------------------
		// List<GuiTexture> guis = new ArrayList<GuiTexture>();
		// GuiTexture gui3dTesting = new
		// GuiTexture(loader.loadTexture("planet"),
		// loader.loadTexture("blueCircle"), new Vector2f(-0.85f, -0.76f),
		// 0.22f);
		// System.out.println(gui3dTesting.getPosition());
		// guis.add(gui3dTesting);
		// Particle marked = new Particle(particleTexture, new Vector3f(0, 0 ,
		// 440), new Vector3f(0, 0, -1), 0, 20, 0, 1);
		// marked.marked = true;

		// --------------------------FBO----------------------------------------------------
		Fbo multisampledFbo = new Fbo(Display.getWidth(), Display.getWidth());
		Fbo outputFbo = new Fbo(Display.getWidth(), Display.getWidth(), Fbo.DEPTH_TEXTURE);
		
		Fbo depthFbo = new Fbo(Display.getWidth(), Display.getWidth(), Fbo.DEPTH_TEXTURE);
		
		PostProcessing.init(loader);

		while (!Display.isCloseRequested()) {
			TimeController.updateTime();
			GameTimeController.updateGameTime();

			player.move(terrainSphere);
			camera.move();

			// particleSystem.generateParticles(player.getPosition());
			snowSystem.updateSnowSystem();
			// particleSystemFire.generateParticles(new Vector3f(150, 10,
			// -150));

			ParticleMaster.update(camera);
			// fbo.bindFrameBuffer();
			//renderer.renderShadowMap(gameEntityObjectsController.getEntitiesWithShadows(), sun);

			animationController.update();

			eventController.updateEvents(gameEntityObjectsController.getEntityObjects());

			if (GameTimeController.basicCycleHit) {
				entityCycleController.updateList(gameEntityObjectsController.getEntityObjects());
			}
			
			depthFbo.bindFrameBuffer();
			renderer.renderScene(gameEntityObjectsController.getEntityObjects(),
					gameEntityObjectsController.getNormalMapEntities(), lights, camera, new Vector4f(0, 1, 0, 50),
					terrainSphere, player);
			//GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			waterSphereRenderer.render(waters, camera, sun);
			depthFbo.unbindFrameBuffer();
			
			

			multisampledFbo.bindFrameBuffer();
			renderer.renderScene(gameEntityObjectsController.getEntityObjects(),
					gameEntityObjectsController.getNormalMapEntities(), lights, camera, new Vector4f(0, 1, 0, 50),
					terrainSphere, player);
			// waterRenderer.render(waters, camera, sun);
			if (eventController.isShowCircle()) {
				renderer.renderHightlightedCircle(highlightedCircle, camera);
			}

			waterSphereRenderer.render(waters, camera, sun);

			// fbo.unbindFrameBuffer();
			// GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			ParticleMaster.renderParticles(camera);
			//guiRendererController.render(eventController);
			//multisampledFbo.unbindFrameBuffer();
			multisampledFbo.resolveToFbo(outputFbo);
			PostProcessing.doPostProcessing(outputFbo.getColourTexture(), depthFbo.getDepthTexture());
			//PostProcessing.doPostProcessing(depthFbo.getDepthTexture());
			//PostProcessing.doPostProcessing(renderer.getShadowMapTexture());
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			guiRendererController.render(eventController);

			// guiRenderer.render(eventController.getGuisToDisplay());
			// gui3dRenderer.render(eventController.getGuisSphere3D());
			// System.out.println(eventController.getGuiObjectUnit().size());
			// gui3dObjectRenderer.render(eventController.getGuiObjectUnit());
			// gui3dRenderer.render(eventController.getGuisSphere3D());

			// textMapController.clearMap();
			// textMapController.processListOfText(eventController.geGuiTexts());

			// textMapController.render();

			DisplayManager.updateDisplay();
		}

		PostProcessing.cleanUp();
		multisampledFbo.cleanUp();
		outputFbo.cleanUp();
		ParticleMaster.cleanUp();
		// TextMaster.cleanUp();
		// textMapController.cleanUp();
		// fbos.cleanUp();
		// waterShader.cleanUp();
		waterSphereShader.cleanUp();
		// guiRenderer.cleanUp();
		guiRendererController.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
