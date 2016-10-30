package fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontMeshCreator.TextMeshData;
import renderEngine.Loader;

public class TextMapController {
	
	private Loader loader;
	private Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private FontRenderer renderer;
	
	public TextMapController(Loader loader){
		this.renderer = new FontRenderer();
		this.loader = loader;
	}
	
	public void render(){
		renderer.render(texts);
	}
	
	public void clearMap(){
		texts.clear();
	}
	
	public void processListOfText(List<GUIText> texts){
		if (texts.size() == 0) {
			return;
		}
		for (GUIText text : texts){
			processText(text);
		}
	}
	
	public void processText(GUIText text){
		//System.out.println("here");
		FontType font = text.getFont();
		//TextMeshData data = font.loadText(text);
		//int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords()); //need change this!!
		//text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public void cleanUp(){
		renderer.cleanUp();
	}

}
