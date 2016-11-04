package entityGamePlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entityObjects.EntityObject;
import terrainsSphere.TerrainSphere;

public class EntityHuntingController {
	private Random random;
	
	public EntityHuntingController(){
		this.random = new Random();
	}
	
	
	
	public boolean calculateEntityHuntingBasic(EntityObject entityObject){
		if (entityObject.getObjectData().isNoPrey()) {
			return true;
		}
		float captureRate = 0.1f; // change this later
		
		float amount = entityObject.getAmount();
		for (int i=0; i<TerrainSphere.FACE_NEIGHBOR_RANGE + 1; i++){
			ArrayList<EntityObject> currentRangePreys = entityObject.getPreys().get(i);
			int currentRangeSize = currentRangePreys.size();
			int startingOffset = 0;
			if (currentRangePreys.size() > 0) {
				startingOffset = random.nextInt(currentRangeSize);
			}
			for (int j=0; j<currentRangeSize; j++){
				int index = (j + startingOffset) % currentRangeSize;
				float preyAmount = currentRangePreys.get(index).getAmount();
				float chance = captureRate * preyAmount / amount;
				float capturing = random.nextFloat();
				if (chance > capturing) {
					currentRangePreys.get(index).setAmount(preyAmount - amount * captureRate * 3f);
					entityObject.setCyclesWithoutFood(0);
					return true;
				}
			}
			//float chance = captureRate * entityObject.getAmount() / 
		}
		int cyclesWithoutFood = entityObject.getCyclesWithoutFood() + 1;
		entityObject.setCyclesWithoutFood(cyclesWithoutFood);
		entityObject.setAmount(amount * 0.9f);
		//entityObject.setAmount(amount * (10 - cyclesWithoutFood) / 10f);
		return false;
	}

}
