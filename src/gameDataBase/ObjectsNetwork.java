package gameDataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.lwjgl.util.vector.Vector3f;

import entityObjects.ObjectData;

public class ObjectsNetwork {
	//basic terrain objects that only has colour
	
	public static final int ICE_CAP_TERRAIN = 100;
	public static final int TUNDRA_TERRAIN = 101;
	public static final int TAIGA_TERRAIN = 102;
	public static final int CANYON_TERRAIN = 103;
	public static final int STEPPE_TERRAIN = 104;
	public static final int FOREST_TERRAIN = 105;
	public static final int DESERT_TERRAIN = 106;
	public static final int SAVANNA_TERRAIN = 107;
	public static final int WETLAND_TERRAIN = 108;
	public static final int RAIN_FOREST_TERRAIN = 109;
	
	
	public static final Vector3f ICE_CAP_COLOUR = new Vector3f((float) 160 / 255, (float) 160 / 255, (float) 160 / 255);
	public static final Vector3f TUNDRA_COLOUR = new Vector3f((float) 135 / 255, (float) 160 / 255, (float) 145 / 255);
	public static final Vector3f TAIGA_COLOUR = new Vector3f((float) 115 / 255, (float) 160 / 255, (float) 135 / 255);
	public static final Vector3f CANYON_COLOUR = new Vector3f((float) 160 / 255, (float) 109 / 255, (float) 69 / 255);
	public static final Vector3f STEPPE_COLOUR = new Vector3f((float) 91 / 255, (float) 122 / 255, (float) 29 / 255);
	public static final Vector3f FOREST_COLOUR = new Vector3f((float) 39 / 255, (float) 124 / 255, (float) 20 / 255);
	public static final Vector3f DESERT_COLOUR = new Vector3f((float) 144 / 255, (float) 132 / 255, (float) 45 / 255);
	public static final Vector3f SAVANNA_COLOUR = new Vector3f((float) 100 / 255, (float) 100 / 255, (float) 35 / 255);
	public static final Vector3f WETLAND_COLOUR = new Vector3f((float) 30 / 255, (float) 129 / 255, (float) 90 / 255);
	public static final Vector3f RAIN_FOREST_COLOUR = new Vector3f((float) 25 / 255, (float) 120 / 255, (float) 70 / 255);
	
	
	
	
	
	public static final int SNOW_TERRAIN = 20;
	public static final int MOUNTAIN_TERRAIN = 21;
	public static final int CLIFF_TERRAIN = 22;
	public static final int PLAIN_TERRAIN = 23;
	//public static final int SAVANNA_TERRAIN = 24;
	public static final int SHALLOW_WATER_TERRAIN = -21;
	public static final int DEEP_WATER_TERRAIN = -22;
	
	
	
	//objects that affects terrain colour
	public static final int SHORT_GRASS = 500;
	public static final int LOW_GRASS1 = 501;
	
	//objects
	
	public static final int LOW_GRASS3 = 503;
	
	public static final int PINE_TREE1 = 1001;
	public static final int SEASONAL_TREE1 = 1011;
	public static final int SIMPLE_TREE = 1000;
	
	
	public static final int LION = 5000;
	
	
	
	//basic terrain objects that only has colour
	public static final Vector3f SNOW_COLOUR = new Vector3f(1, 1, 1);
	public static final Vector3f MOUNTAIN_COLOUR = new Vector3f((float) 90 / 255, (float) 90 / 255, (float) 90 / 255);
	public static final Vector3f CLIFF_COLOUR = new Vector3f((float) 134 / 255, (float) 95 / 255, (float) 47 / 255);
	public static final Vector3f SHALLOW_WATER_COLOUR = new Vector3f((float) 20 / 255, (float) 56 / 255,
			(float) 78 / 255);
	public static final Vector3f DEEEP_WATER_COLOUR = new Vector3f((float) 04 / 255, (float) 14 / 255,
			(float) 128 / 255);
	public static final Vector3f PLAIN_COLOUR = new Vector3f((float) 158 / 255, (float) 149 / 255, (float) 100 / 255);
	
	
	
	//objects that affects terrain colour
	public static final Vector3f GRASS_OBJECT_COLOUR = new Vector3f((float) 181 / 255, (float) 215 / 255, (float) 101 / 255);
	public static final Vector3f LOW_GRASS1_OBJECT_COLOUR = new Vector3f((float) 181 / 255, (float) 215 / 255, (float) 101 / 255);
	
	
	
	public static ObjectData snowTerrain;
	public static ObjectData mountainTerrain;
	public static ObjectData plainTerrain;
	public static ObjectData cliffTerrain;
	
	
	public static ObjectData iceCapTerrain;
	public static ObjectData tundraTerrain;
	public static ObjectData taigaTerrain;
	public static ObjectData canyonTerrain;
	public static ObjectData steppeTerrain;
	public static ObjectData forestTerrain;
	public static ObjectData desertTerrain;
	public static ObjectData savannaTerrain;
	public static ObjectData wetlandTerrain;
	public static ObjectData rainForestTerrain;
	
	
	
	public static ObjectData shallowWaterTerrain;
	public static ObjectData deepWaterTerrain;
	
	
	public static ObjectData lowGrass1;
	public static ObjectData lowGrass3;
	
	
	public static ObjectData simpleTree;
	public static ObjectData pineTree1;
	public static ObjectData seasonalTree1;
	
	
	public static Map<Integer, ObjectData> ObjectsMap = new HashMap<Integer, ObjectData>();
	
	public static ObjectData getObjectDataBasedOnType(int objectType){
		if (ObjectsMap.containsKey(objectType)) {
			return ObjectsMap.get(objectType);
		}
		else {
			return null;
		}
	}
	
	
	public static void fillObjectsController(){
		//snowTerrain
		snowTerrain = new ObjectData(SNOW_TERRAIN);
		snowTerrain.setColour(SNOW_COLOUR);
		snowTerrain.setAffectTerrainColour(true);
		snowTerrain.setObjectInitAmount(100);
		ObjectsMap.put(SNOW_TERRAIN, snowTerrain);
		
		//mountainTerrain
		mountainTerrain = new ObjectData(MOUNTAIN_TERRAIN);
		mountainTerrain.setColour(MOUNTAIN_COLOUR);
		mountainTerrain.setAffectTerrainColour(true);
		mountainTerrain.setObjectInitAmount(100);
		ObjectsMap.put(MOUNTAIN_TERRAIN, mountainTerrain);
		
		//plainTerrain
		plainTerrain = new ObjectData(PLAIN_TERRAIN);
		plainTerrain.setColour(PLAIN_COLOUR);
		plainTerrain.setAffectTerrainColour(true);
		plainTerrain.setObjectInitAmount(100);
		ObjectsMap.put(PLAIN_TERRAIN, plainTerrain);
		
		
		
		
		
		
		
		
		
		
		//iceCapTerrain
		iceCapTerrain = new ObjectData(ICE_CAP_TERRAIN);
		iceCapTerrain.setColour(ICE_CAP_COLOUR);
		iceCapTerrain.setAffectTerrainColour(true);
		iceCapTerrain.setObjectInitAmount(100);
		ObjectsMap.put(ICE_CAP_TERRAIN, iceCapTerrain);
		
		//tundraTerrain
		tundraTerrain = new ObjectData(TUNDRA_TERRAIN);
		tundraTerrain.setColour(TUNDRA_COLOUR);
		tundraTerrain.setAffectTerrainColour(true);
		tundraTerrain.setObjectInitAmount(100);
		ObjectsMap.put(TUNDRA_TERRAIN, tundraTerrain);
		
		//taigaTerrain
		taigaTerrain = new ObjectData(TAIGA_TERRAIN);
		taigaTerrain.setColour(TAIGA_COLOUR);
		taigaTerrain.setAffectTerrainColour(true);
		taigaTerrain.setObjectInitAmount(100);
		ObjectsMap.put(TAIGA_TERRAIN, taigaTerrain);
		
		//canyonTerrain
		canyonTerrain = new ObjectData(CANYON_TERRAIN);
		canyonTerrain.setColour(CANYON_COLOUR);
		canyonTerrain.setAffectTerrainColour(true);
		canyonTerrain.setObjectInitAmount(100);
		ObjectsMap.put(CANYON_TERRAIN, canyonTerrain);
		
		//steppeTerrain
		steppeTerrain = new ObjectData(STEPPE_TERRAIN);
		steppeTerrain.setColour(STEPPE_COLOUR);
		steppeTerrain.setAffectTerrainColour(true);
		steppeTerrain.setObjectInitAmount(100);
		ObjectsMap.put(STEPPE_TERRAIN, steppeTerrain);
		
		//forestTerrain
		forestTerrain = new ObjectData(FOREST_TERRAIN);
		forestTerrain.setColour(FOREST_COLOUR);
		forestTerrain.setAffectTerrainColour(true);
		forestTerrain.setObjectInitAmount(100);
		ObjectsMap.put(FOREST_TERRAIN, forestTerrain);
		
		//desertTerrain
		desertTerrain = new ObjectData(DESERT_TERRAIN);
		desertTerrain.setColour(DESERT_COLOUR);
		desertTerrain.setAffectTerrainColour(true);
		desertTerrain.setObjectInitAmount(100);
		ObjectsMap.put(DESERT_TERRAIN, desertTerrain);
		
		//savannaTerrain
		savannaTerrain = new ObjectData(SAVANNA_TERRAIN);
		savannaTerrain.setColour(SAVANNA_COLOUR);
		savannaTerrain.setAffectTerrainColour(true);
		savannaTerrain.setObjectInitAmount(100);
		ObjectsMap.put(SAVANNA_TERRAIN, savannaTerrain);
		
		//wetlandTerrain
		wetlandTerrain = new ObjectData(WETLAND_TERRAIN);
		wetlandTerrain.setColour(WETLAND_COLOUR);
		wetlandTerrain.setAffectTerrainColour(true);
		wetlandTerrain.setObjectInitAmount(100);
		ObjectsMap.put(WETLAND_TERRAIN, wetlandTerrain);
		
		//rainForestTerrain
		rainForestTerrain = new ObjectData(RAIN_FOREST_TERRAIN);
		rainForestTerrain.setColour(RAIN_FOREST_COLOUR);
		rainForestTerrain.setAffectTerrainColour(true);
		rainForestTerrain.setObjectInitAmount(100);
		ObjectsMap.put(RAIN_FOREST_TERRAIN, rainForestTerrain);
		
		
		
		
		
		
		
		
		
		
		
		
		
		//cliffTerrain
		cliffTerrain = new ObjectData(CLIFF_TERRAIN);
		cliffTerrain.setColour(CLIFF_COLOUR);
		cliffTerrain.setAffectTerrainColour(true);
		cliffTerrain.setObjectInitAmount(100);
		ObjectsMap.put(CLIFF_TERRAIN, cliffTerrain);
		
		
		//shallowWaterTerrain
		shallowWaterTerrain = new ObjectData(SHALLOW_WATER_TERRAIN);
		shallowWaterTerrain.setColour(SHALLOW_WATER_COLOUR);
		shallowWaterTerrain.setAffectTerrainColour(true);
		shallowWaterTerrain.setObjectInitAmount(100);
		ObjectsMap.put(SHALLOW_WATER_TERRAIN, shallowWaterTerrain);
		
		//deepWaterTerrain
		deepWaterTerrain = new ObjectData(DEEP_WATER_TERRAIN);
		deepWaterTerrain.setColour(DEEEP_WATER_COLOUR);
		deepWaterTerrain.setAffectTerrainColour(true);
		deepWaterTerrain.setObjectInitAmount(100);
		ObjectsMap.put(DEEP_WATER_TERRAIN, deepWaterTerrain);
		
		
		//lowGrass1
		lowGrass1 = new ObjectData(LOW_GRASS1);
		lowGrass1.setColour(LOW_GRASS1_OBJECT_COLOUR);
		lowGrass1.setAffectTerrainColour(true);
		lowGrass1.setObjectInitAmount(1000);
		lowGrass1.setInitScale(0.2f);
		lowGrass1.addTerrainType(PLAIN_TERRAIN);
		lowGrass1.addTerrainType(MOUNTAIN_TERRAIN);
		ObjectsMap.put(LOW_GRASS1, lowGrass1);
		
		
		//lowGrass3
		lowGrass3 = new ObjectData(LOW_GRASS3);
		lowGrass3.setAffectTerrainColour(false);
		lowGrass3.setObjectInitAmount(1000);
		lowGrass3.setInitScale(0.3f);
		lowGrass3.addTerrainType(SAVANNA_TERRAIN);
		lowGrass3.addTerrainType(TUNDRA_TERRAIN);
		lowGrass3.addTerrainType(STEPPE_TERRAIN);
		ObjectsMap.put(LOW_GRASS3, lowGrass3);
		
		//pineTree1
		pineTree1 = new ObjectData(PINE_TREE1);
		pineTree1.setAffectTerrainColour(false);
		pineTree1.setObjectInitAmount(1000);
		pineTree1.setInitScale(2f);
		pineTree1.addTerrainType(TAIGA_TERRAIN);
		pineTree1.addTerrainType(TUNDRA_TERRAIN);
		pineTree1.addTerrainType(FOREST_TERRAIN);
		ObjectsMap.put(PINE_TREE1, pineTree1);
		
		//seasonalTree1
		seasonalTree1 = new ObjectData(SEASONAL_TREE1);
		seasonalTree1.setAffectTerrainColour(false);
		seasonalTree1.setObjectInitAmount(1000);
		seasonalTree1.setInitScale(3f);
		seasonalTree1.setTemperatureInfo(15, 10);
		seasonalTree1.setHumidityInfo(10, 15);
		seasonalTree1.addTerrainType(MOUNTAIN_TERRAIN);
		seasonalTree1.addTerrainType(FOREST_TERRAIN);
		seasonalTree1.addTerrainType(STEPPE_TERRAIN);
		seasonalTree1.addTerrainType(CANYON_TERRAIN);
		ObjectsMap.put(SEASONAL_TREE1, seasonalTree1);
		
		
		simpleTree = new ObjectData(SIMPLE_TREE);
		simpleTree.setAffectTerrainColour(false);
		simpleTree.setObjectInitAmount(1000);
		simpleTree.setInitScale(2f);
		simpleTree.addTerrainType(SNOW_TERRAIN);
		simpleTree.addTerrainType(MOUNTAIN_TERRAIN);
		simpleTree.addTerrainType(PLAIN_TERRAIN);
		ObjectsMap.put(SIMPLE_TREE, simpleTree);
	}

}
