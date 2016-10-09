package mainGame;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class EntityObjectModelData {
	
	
	
	//private static Loader loader;
	
	public static TexturedModel testingTreeModel;
	
	
	
	
	public static TexturedModel deer1Model;
	
	
	
	
	public static void loadAllObjects(Loader loader){
		loadDeer1Data(loader);
		loadTestingTreeData(loader);
	}

	
	public static void loadDeer1Data(Loader loader){
		ModelData deerData = OBJFileLoader.loadOBJ("deer");
		RawModel deerRawModel = loader.loadToVAOwithData(deerData.getVertices(), deerData.getTextureCoords(), deerData.getNormals(), deerData.getIndices(), deerData);
		deer1Model = new TexturedModel(deerRawModel, new ModelTexture(loader.loadTexture("deerFlipped")));
	}
	
	
	public static void loadTestingTreeData(Loader loader){
		ModelData data = OBJFileLoader.loadOBJ("tree2");
		RawModel model = loader.loadToVAOwithData(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices(), data);
		
		testingTreeModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("simpleTree")));
	}
	
}
