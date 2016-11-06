package entityGamePlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entityObjects.EntityObject;
import entityObjects.PredationUnit;
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
		//float captureRate = 0.1f; // change this later
		
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
				EntityObject currentPrey = currentRangePreys.get(index);
				float preyAmount = currentPrey.getAmount();
				PredationUnit predationUnit = entityObject.getObjectData().getPreys().get(currentPrey.getObjectData().getObjectType());
				float chance = predationUnit.getBaseCaptureRate() * preyAmount / amount / (i + 2);
				float capturing = random.nextFloat();
				if (chance > capturing) {
					currentRangePreys.get(index).setAmount(preyAmount - amount * predationUnit.getBaseKillRate() * EntityCycleController.SMOOTH_FACTOR);
					entityObject.setCyclesWithoutFood(0);
					return true;
				}
			}
			//float chance = captureRate * entityObject.getAmount() / 
		}
		int cyclesWithoutFood = entityObject.getCyclesWithoutFood() + 1;
		entityObject.setCyclesWithoutFood(cyclesWithoutFood);
		//entityObject.setAmount(amount * 0.9f);
		entityObject.setAmount(amount * (1 - cyclesWithoutFood * cyclesWithoutFood * EntityCycleController.SMOOTH_FACTOR / 5f));
		return false;
	}

}
