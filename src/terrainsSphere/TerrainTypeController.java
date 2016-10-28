package terrainsSphere;

import java.util.List;

import climate.HumidityController;
import climate.TemperatureController;
import entityObjects.ObjectsNetwork;
import particles.SnowSystem;
import renderEngine.Loader;

public class TerrainTypeController {

	private Loader loader;
	private TerrainSphere terrainSphere;
	private ColourController colourController;
	private TemperatureController temperatureController;
	private HumidityController humidityController;
	
	private SnowSystem snowSystem;

	public TerrainTypeController(Loader loader, TerrainSphere terrainSphere, ColourController colourController,
			TemperatureController temperatureController, HumidityController humidityController, SnowSystem snowSystem) {
		this.loader = loader;
		this.terrainSphere = terrainSphere;
		this.colourController = colourController;
		this.temperatureController = temperatureController;
		this.humidityController = humidityController;
		temperatureController.updateSphereTemp();
		humidityController.fillDistanceToWater();
		humidityController.updateSphereHumidity();
		this.snowSystem = snowSystem;
	}

	public void updateTerrainType(TerrainFace face) {

		// test
		//temperatureController.calculateAndUpdateTemperature(face);
		float temp = face.getTemperature();
		float humid = face.getHumidity();
		float height = face.getHeight();

		if (height > 0 && height < HeightGeneratorSphere.AMPLITUDE/2f) {
			if (temp < TemperatureController.POLAR_TEMP + TemperatureController.tempRange) {
				colourController.setTerrain(face, ObjectsNetwork.iceCapTerrain);
				face.setSnowAmount(0.01f);
				snowSystem.addFace(face);
				
			} else if (temp < TemperatureController.POLAR_TEMP + TemperatureController.tempRange * 2) {
				face.setSnowAmount(0.01f * (temp - TemperatureController.POLAR_TEMP)/(0 - TemperatureController.POLAR_TEMP));
				snowSystem.addFace(face);
				if (humid < HumidityController.humidityRange) {
					colourController.setTerrain(face, ObjectsNetwork.tundraTerrain);
				}
				else{
					colourController.setTerrain(face, ObjectsNetwork.taigaTerrain);
				}
			} else if (temp < TemperatureController.POLAR_TEMP + TemperatureController.tempRange * 3) {
				if (humid < HumidityController.humidityRange) {
					colourController.setTerrain(face, ObjectsNetwork.canyonTerrain);
				} else if (humid < HumidityController.humidityRange * 2f) {
					colourController.setTerrain(face, ObjectsNetwork.steppeTerrain);
				}
				else{
					colourController.setTerrain(face, ObjectsNetwork.forestTerrain);
				}
				
				//face.setTerrainType(ObjectsNetwork.canyonTerrain);
			} else {
				//System.out.println(humid);
				if (humid < HumidityController.humidityRange) {
					colourController.setTerrain(face, ObjectsNetwork.desertTerrain);
				} else if (humid < HumidityController.humidityRange * 2f) {
					colourController.setTerrain(face, ObjectsNetwork.savannaTerrain);
				}
				else if(humid < HumidityController.humidityRange * 3f){
					colourController.setTerrain(face, ObjectsNetwork.rainForestTerrain);
				}
				else{
					colourController.setTerrain(face, ObjectsNetwork.wetlandTerrain);
				}
			}

		}
		
		else if(height > 0) {
			if (temp < 0) {
				colourController.setTerrain(face, ObjectsNetwork.iceCapTerrain);
				face.setSnowAmount(0.01f);
				snowSystem.addFace(face);
			} else{
				colourController.setTerrain(face, ObjectsNetwork.mountainTerrain);
			}
		}
		
		if (height < 0){
			colourController.setTerrain(face, ObjectsNetwork.shallowWaterTerrain);
		}

	}
	
	
	public void updateAllFaces(){
		List<TerrainFace> faces = terrainSphere.getFinalTerrainFaces();
		for (int i=0; i<faces.size(); i++){
			updateTerrainType(faces.get(i));
		}
		colourController.updateColourVBO(loader);
	}
	

}
