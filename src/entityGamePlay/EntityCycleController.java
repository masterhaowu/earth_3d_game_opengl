package entityGamePlay;

import java.util.List;

import entityObjects.EntityObject;

public class EntityCycleController {
	
	private EntityGrowthController entityGrowthController;
	
	public EntityCycleController(){
		this.entityGrowthController = new EntityGrowthController();
	}
	
	
	public void updateList(List<EntityObject> entityObjects){
		for (EntityObject entityObject : entityObjects){
			entityGrowthController.calculateNetGrowthBasic(entityObject);
			checkUpperBound(entityObject);
			checkExtinction(entityObject);
			//System.out.println(entityObject.getAmount());
		}
		
	}
	
	
	
	public void checkUpperBound(EntityObject entityObject){
		float upperBound = entityObject.getObjectData().getObjectMaxAmount();
		if (entityObject.getAmount() > upperBound) {
			entityObject.setAmount(upperBound);
		}
	}
	
	public void checkExtinction(EntityObject entityObject){
		float extinctionLine = entityObject.getObjectData().getObjectExtinctAmount();
		if (entityObject.getAmount() < extinctionLine) {
			entityObject.setAmount(extinctionLine);
		}
	}

}
