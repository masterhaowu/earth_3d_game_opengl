package gameDataBase;

import entityObjects.ObjectData;

public class ObjectsNetworkHerbivore {
	
	
	public static ObjectData deer1;
	
	public static void fillAnimalsHerbivore(){
		deer1 = new ObjectData(ObjectsNetwork.DEER1);
		deer1.setAffectTerrainColour(false);
		deer1.setObjectInitAmount(100);
		deer1.setInitScale(1.3f);
		deer1.setTemperatureInfo(25, 10);
		deer1.setHumidityInfo(10, 15);
		deer1.addPrey(ObjectsNetwork.SEASONAL_TREE1);
		deer1.addPrey(ObjectsNetwork.PINE_TREE1);
		//deer1.setNoPrey(true);
		deer1.addPredator(ObjectsNetwork.LION1);
		deer1.marked = true;
		ObjectsNetwork.ObjectsMap.put(ObjectsNetwork.DEER1, deer1);
	}

}
