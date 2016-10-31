package guis;

import fontRendering.TextMapController;
import mainGame.GameEventController;
import renderEngine.Loader;

public class GuiRendererController {
	
	private GuiRenderer gui2dRenderer;
	private Gui3DObjectRenderer gui3dObjectRenderer;
	private Gui3DSphereRenderer gui3dSphereRenderer;
	private TextMapController textMapController;
	
	
	public GuiRendererController(Loader loader){
		this.gui2dRenderer = new GuiRenderer(loader);
		this.gui3dSphereRenderer = new Gui3DSphereRenderer(loader);
		this.gui3dObjectRenderer = new Gui3DObjectRenderer();
		this.textMapController = new TextMapController(loader);
	}
	
	public void render(GameEventController eventController){
		textMapController.clearMap();
		gui3dSphereRenderer.render(eventController.getLevel1Spheres());
		gui2dRenderer.render(eventController.getLevel2Panel());
		gui2dRenderer.render(eventController.getLevel2textures());
		gui3dObjectRenderer.render(eventController.getGuiObjectUnit());
		gui3dSphereRenderer.render(eventController.getLevel2Spheres());
		textMapController.processListOfText(eventController.getLevel2Texts());
		textMapController.render();
	}
	
	public void cleanUp(){
		gui2dRenderer.cleanUp();
		gui3dSphereRenderer.cleanUp();
		textMapController.cleanUp();
	}

}
