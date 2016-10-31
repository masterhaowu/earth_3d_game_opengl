package gameDataBase;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class EntityModelDataBase {

	// private static Loader loader;

	public static TexturedModel testingTreeModel;

	public static TexturedModel lowGrass1Model;
	public static TexturedModel lowGrass3Model;
	
	public static TexturedModel pineTree1Model;
	public static TexturedModel seasonalTree1Model;
	

	public static TexturedModel deer1Model;

	public static void loadAllObjects(Loader loader) {
		loadLowGrass1Data(loader);
		loadLowGrass3Data(loader);
		loadPineTree1Data(loader);
		loadSeasonalTree1Data(loader);
		loadDeer1Data(loader);
		loadTestingTreeData(loader);
	}

	public static void loadLowGrass1Data(Loader loader) {
		ModelData lowGrass1Data = OBJFileLoader.loadOBJ("lowGrass1");
		RawModel lowGrass1RawModel = loader.loadToVAOwithData(lowGrass1Data.getVertices(),
				lowGrass1Data.getTextureCoords(), lowGrass1Data.getIndices(), lowGrass1Data);
		lowGrass1Model = new TexturedModel(lowGrass1RawModel, new ModelTexture(loader.loadTexture("lowGrass1")));
	}
	
	public static void loadLowGrass3Data(Loader loader) {
		ModelData lowGrass3Data = OBJFileLoader.loadOBJ("grass3");
		RawModel lowGrass3RawModel = loader.loadToVAOwithData(lowGrass3Data.getVertices(),
				lowGrass3Data.getTextureCoords(), lowGrass3Data.getIndices(), lowGrass3Data);
		lowGrass3Model = new TexturedModel(lowGrass3RawModel, new ModelTexture(loader.loadTexture("grass3")));
	}
	
	public static void loadPineTree1Data(Loader loader) {
		ModelData pineTree1Data = OBJFileLoader.loadOBJ("pine1");
		RawModel pineTree1RawModel = loader.loadToVAOwithData(pineTree1Data.getVertices(),
				pineTree1Data.getTextureCoords(), pineTree1Data.getIndices(), pineTree1Data);
		pineTree1Model = new TexturedModel(pineTree1RawModel, new ModelTexture(loader.loadTexture("pine1")));
	}
	
	public static void loadSeasonalTree1Data(Loader loader) {
		ModelData seasonalTree1Data = OBJFileLoader.loadOBJ("seasonalTree1");
		RawModel seasonalTree1RawModel = loader.loadToVAOwithData(seasonalTree1Data.getVertices(),
				seasonalTree1Data.getTextureCoords(), seasonalTree1Data.getIndices(), seasonalTree1Data);
		seasonalTree1Model = new TexturedModel(seasonalTree1RawModel, new ModelTexture(loader.loadTexture("seasonalTree1")));
	}
	

	public static void loadDeer1Data(Loader loader) {
		ModelData deerData = OBJFileLoader.loadOBJ("deer");
		RawModel deerRawModel = loader.loadToVAOwithData(deerData.getVertices(), deerData.getTextureCoords(), deerData.getIndices(), deerData);
		deer1Model = new TexturedModel(deerRawModel, new ModelTexture(loader.loadTexture("deerFlipped")));
	}

	public static void loadTestingTreeData(Loader loader) {
		ModelData data = OBJFileLoader.loadOBJ("simpleTree");
		RawModel model = loader.loadToVAOwithData(data.getVertices(), data.getTextureCoords(),
				data.getIndices(), data);

		testingTreeModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("simpleTree")));
	}

}
