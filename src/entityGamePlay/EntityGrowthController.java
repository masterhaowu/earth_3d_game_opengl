package entityGamePlay;

import java.awt.font.NumericShaper.Range;
import java.util.List;

import entityObjects.EntityObject;
import entityObjects.ObjectData;
import terrainsSphere.TerrainFace;

public class EntityGrowthController {
	
	public static final float MAX_FACTOR = 0.01f;
	
	public EntityGrowthController(){
		
	}
	
	public void update(){
		
		
	}
	
	public void updateList(List<EntityObject> entityObjects){
		for (EntityObject entityObject : entityObjects){
			calculateNetGrowthBasic(entityObject);
		}
	}
	
	
	
	public void calculateNetGrowthBasic(EntityObject entityObject){ //this is natural growth minus natural death given the enviornment
		TerrainFace face = entityObject.getFace();
		if (face == null) {
			return;
		}
		
		float initAmount = entityObject.getAmount();
		float temp = face.getTemperature();
		float humidity = face.getHumidity();
		float height = face.getHeight();
		
		float tempFactor = 1;
		float humidityFactor = 1;
		float heightFactor = 1;
		
		ObjectData objectData = entityObject.getObjectData();
		float tempOptimal = objectData.getTemperatureOptimal();
		float tempRange = objectData.getTemperatureRange();
		float humidityOptimal = objectData.getHumidityOptimal();
		float humidityRange = objectData.getHumidityRange();
		float heightOptimal = objectData.getHeightOptimal();
		float heightRange = objectData.getHeightRange();
		
		float individualSmoothFactor = (float) Math.pow(EntityCycleController.SMOOTH_FACTOR, 0.5);
		float smoothedMaxFactor = entityObject.getObjectData().getInitMaxGrowthRate() * individualSmoothFactor;
		
		float tempDiff = Math.abs(temp - tempOptimal);
		if (objectData.isAffectedByTemperature()) {
			tempFactor = 1 + smoothedMaxFactor - tempDiff/tempRange * tempDiff/tempRange * smoothedMaxFactor;
			if (tempFactor < 0) {
				tempFactor = 0;
			}
		}
		
		float humidDiff = Math.abs(humidity - humidityOptimal);
		if (objectData.isAffectedByHumidity()){
			humidityFactor = 1 + smoothedMaxFactor - humidDiff/humidityRange * humidDiff/humidityRange * smoothedMaxFactor;
		}
		
		float totalFactor = tempFactor * humidityFactor * heightFactor;
		entityObject.setAmount(initAmount * totalFactor);
		//System.out.println(entityObject.getAmount());
	}

}
