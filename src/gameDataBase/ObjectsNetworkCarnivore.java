package gameDataBase;

import entityObjects.ObjectData;

public class ObjectsNetworkCarnivore {
	
	public static ObjectData lion1;
	
	
	public static void fillAnimalsCarnivore(){
		
		lion1 = new ObjectData(ObjectsNetwork.LION1);
		lion1.setAffectTerrainColour(false);
		lion1.setObjectInitAmount(10);
		lion1.setInitScale(1f);
		lion1.setTemperatureInfo(25, 10);
		lion1.setHumidityInfo(10, 15);
		lion1.addPrey(ObjectsNetwork.DEER1);
		//lion1.marked = true;
		ObjectsNetwork.ObjectsMap.put(ObjectsNetwork.LION1, lion1);
		
	}

}
