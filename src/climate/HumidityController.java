package climate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;

public class HumidityController {
	
	public static final int WATER_THRESHOLD = 15; //above this value humidity will be 0
	
	public static float humidityRange = 40;
	
	
	private TerrainSphere terrainSphere;
	
	
	
	
	
	
	
	public HumidityController(TerrainSphere terrainSphere){
		this.terrainSphere = terrainSphere;
		
	}
	
	
	public void calculateAndUpdateHumidity(TerrainFace face){
		float temp = face.getTemperature();
		float maxHumidity = (temp - TemperatureController.POLAR_TEMP) * humidityRange/TemperatureController.tempRange;
		//System.out.println(maxHumidity);
		//for now humidity is simply calculated based on the distance to the water

		float humidity = maxHumidity * (1 - face.getDistanceToWater()/(float)WATER_THRESHOLD);
		//if (temp > 30) {
		//	System.out.println(humidity);
		//}
		//System.out.println(humidity);
		face.setHumidity(humidity);
		face.setHumidityWithOffset((1 - face.getDistanceToWater()/(float)WATER_THRESHOLD));
	}
	
	
	public void updateSphereHumidity(){
		List<TerrainFace> faces = terrainSphere.getFinalTerrainFaces();
		for(int i=0; i<faces.size(); i++){
			calculateAndUpdateHumidity(faces.get(i));
		}
	}
	
	
	public void fillDistanceToWater(){
		List<TerrainFace> initFace = new ArrayList<TerrainFace>();
		List<TerrainFace> allFaces = terrainSphere.getFinalTerrainFaces();
		//fill initFace
		for (int i=0; i<allFaces.size(); i++){
			if (allFaces.get(i).getDistanceToWater() == 0) {
				initFace.add(allFaces.get(i));
			} else{
				allFaces.get(i).setDistanceToWater(WATER_THRESHOLD);
			}
		}
		
		//start filling water
		List<TerrainFace> faces = initFace;
		for (int i=0; i<WATER_THRESHOLD; i++){
			List<TerrainFace> nextFaces = new ArrayList<TerrainFace>();
			for(int j=0; j<faces.size(); j++){
				TerrainFace currentFace = faces.get(j);
				Set<TerrainFace> neighborFaces = currentFace.getNeighborFaces(1);
				for (TerrainFace face:neighborFaces){
					if (face.getDistanceToWater() == WATER_THRESHOLD) {
						face.setDistanceToWater(i + 1);
						nextFaces.add(face);
						
					}
				}
				
			}
			faces = nextFaces;
		}
	}
	

}
