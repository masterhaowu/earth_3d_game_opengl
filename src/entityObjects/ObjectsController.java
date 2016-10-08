package entityObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.lwjgl.util.vector.Vector3f;

public class ObjectsController {
	//basic terrain objects that only has colour
	public static final int SNOW_TERRAIN = 20;
	public static final int MOUNTAIN_TERRAIN = 21;
	public static final int CLIFF_TERRAIN = 22;
	public static final int PLAIN_TERRAIN = 23;
	public static final int SHALLOW_WATER_TERRAIN = -21;
	public static final int DEEP_WATER_TERRAIN = -22;
	
	
	
	//objects that affects terrain colour
	public static final int SHORT_GRASS = 500;
	
	//objects
	public static final int SIMPLE_TREE = 1000;
	
	
	
	//basic terrain objects that only has colour
	public static final Vector3f SNOW_COLOUR = new Vector3f(1, 1, 1);
	public static final Vector3f MOUNTAIN_COLOUR = new Vector3f((float) 90 / 255, (float) 90 / 255, (float) 90 / 255);
	public static final Vector3f CLIFF_COLOUR = new Vector3f((float) 134 / 255, (float) 95 / 255, (float) 47 / 255);
	public static final Vector3f SHALLOW_WATER_COLOUR = new Vector3f((float) 144 / 255, (float) 208 / 255,
			(float) 177 / 255);
	public static final Vector3f DEEEP_WATER_COLOUR = new Vector3f((float) 49 / 255, (float) 67 / 255,
			(float) 178 / 255);
	public static final Vector3f PLAIN_COLOUR = new Vector3f((float) 158 / 255, (float) 149 / 255, (float) 100 / 255);
	
	//objects that affects terrain colour
	public static final Vector3f GRASS_OBJECT_COLOUR = new Vector3f((float) 181 / 255, (float) 215 / 255, (float) 101 / 255);
	
	
	
	public static ObjectData snowTerrain;
	public static ObjectData mountainTerrain;
	public static ObjectData plainTerrain;
	public static ObjectData cliffTerrain;
	public static ObjectData shallowWaterTerrain;
	public static ObjectData deepWaterTerrain;
	
	
	
	public static ObjectData simpleTree;
	
	
	
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
		
		
		simpleTree = new ObjectData(SIMPLE_TREE);
		simpleTree.setAffectTerrainColour(false);
		simpleTree.setObjectInitAmount(1000);
		simpleTree.addTerrainType(SNOW_TERRAIN);
		simpleTree.addTerrainType(MOUNTAIN_TERRAIN);
		simpleTree.addTerrainType(PLAIN_TERRAIN);
		ObjectsMap.put(SIMPLE_TREE, simpleTree);
	}

}
