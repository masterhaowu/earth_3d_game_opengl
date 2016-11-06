package gameDataBase;

import entityObjects.ObjectData;

public class ObjectsNetworkTree {
	
	public static ObjectData simpleTree;
	public static ObjectData pineTree1;
	public static ObjectData seasonalTree1;
	
	public static void fillTree (){
		//pineTree1
		pineTree1 = new ObjectData(ObjectsNetwork.PINE_TREE1);
		pineTree1.setAffectTerrainColour(false);
		pineTree1.setObjectInitAmount(1000);
		pineTree1.setInitScale(2f);
		pineTree1.setNoPrey(true);
		//pineTree1.addPredator(ObjectsNetwork.DEER1);
		ObjectsNetwork.ObjectsMap.put(ObjectsNetwork.PINE_TREE1, pineTree1);
				
		//seasonalTree1
		seasonalTree1 = new ObjectData(ObjectsNetwork.SEASONAL_TREE1);
		seasonalTree1.setAffectTerrainColour(false);
		seasonalTree1.setObjectInitAmount(1000);
		seasonalTree1.setInitScale(3f);
		seasonalTree1.setTemperatureInfo(15, 10);
		seasonalTree1.setHumidityInfo(10, 15);
		seasonalTree1.setNoPrey(true);
		//seasonalTree1.addPredator(ObjectsNetwork.DEER1);
		ObjectsNetwork.ObjectsMap.put(ObjectsNetwork.SEASONAL_TREE1, seasonalTree1);
				
				
		simpleTree = new ObjectData(ObjectsNetwork.SIMPLE_TREE);
		simpleTree.setAffectTerrainColour(false);
		simpleTree.setObjectInitAmount(1000);
		simpleTree.setInitScale(2f);
		//simpleTree.addTerrainType(SNOW_TERRAIN);
		//simpleTree.addTerrainType(MOUNTAIN_TERRAIN);
		//simpleTree.addTerrainType(PLAIN_TERRAIN);
		ObjectsNetwork.ObjectsMap.put(ObjectsNetwork.SIMPLE_TREE, simpleTree);

	}

}
