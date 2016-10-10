package mainGame;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class EntityObjectModelData {

	// private static Loader loader;

	public static TexturedModel testingTreeModel;

	public static TexturedModel lowGrass1Model;

	public static TexturedModel deer1Model;

	public static void loadAllObjects(Loader loader) {
		loadLowGrass1Data(loader);
		loadDeer1Data(loader);
		loadTestingTreeData(loader);
	}

	public static void loadLowGrass1Data(Loader loader) {
		ModelData lowGrass1Data = OBJFileLoader.loadOBJ("lowGrass1");
		RawModel lowGrass1RawModel = loader.loadToVAOwithData(lowGrass1Data.getVertices(),
				lowGrass1Data.getTextureCoords(), lowGrass1Data.getNormals(), lowGrass1Data.getIndices(), lowGrass1Data);
		lowGrass1Model = new TexturedModel(lowGrass1RawModel, new ModelTexture(loader.loadTexture("lowGrass1")));
	}

	public static void loadDeer1Data(Loader loader) {
		ModelData deerData = OBJFileLoader.loadOBJ("deer");
		RawModel deerRawModel = loader.loadToVAOwithData(deerData.getVertices(), deerData.getTextureCoords(),
				deerData.getNormals(), deerData.getIndices(), deerData);
		deer1Model = new TexturedModel(deerRawModel, new ModelTexture(loader.loadTexture("deerFlipped")));
	}

	public static void loadTestingTreeData(Loader loader) {
		ModelData data = OBJFileLoader.loadOBJ("simpleTree");
		RawModel model = loader.loadToVAOwithData(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices(), data);

		testingTreeModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("simpleTree")));
	}

}
