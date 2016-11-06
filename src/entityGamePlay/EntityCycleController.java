package entityGamePlay;

import java.util.List;
import java.util.ListIterator;

import entityObjects.EntityObject;

public class EntityCycleController {
	public static final float SMOOTH_FACTOR = 0.01f;
	
	private EntityGrowthController entityGrowthController;
	private EntityHuntingController entityHuntingController;
	
	
	
	public EntityCycleController(){
		this.entityGrowthController = new EntityGrowthController();
		this.entityHuntingController = new EntityHuntingController();
	}
	
	
	public void updateList(List<EntityObject> entityObjects){
		
		//for (EntityObject entityObject : entityObjects){
		ListIterator<EntityObject> iterator = entityObjects.listIterator();
		//for (int i=0; i<entityObjects.size(); i++){
		while(iterator.hasNext()){
			EntityObject entityObject = iterator.next();
			//EntityObject entityObject = entityObjects.get(i);
			if (!entityObject.isFixed()) {
				continue;
			}
			boolean capturedFood = entityHuntingController.calculateEntityHuntingBasic(entityObject);
			if (capturedFood) {
				entityGrowthController.calculateNetGrowthBasic(entityObject);
			}
			if (entityObject.getObjectData().marked) {
				System.out.println(entityObject.getObjectData().getObjectName() + ":  " +  entityObject.getAmount());
			}
			checkUpperBound(entityObject);
			if (checkExtinction(entityObject)){
				//entityObjects.remove(i);
				//i--;
				iterator.remove();
			};
			//System.out.println(entityObject.getAmount());
		}
		
	}
	
	
	
	public void checkUpperBound(EntityObject entityObject){
		float upperBound = entityObject.getObjectData().getObjectMaxAmount();
		if (entityObject.getAmount() > upperBound) {
			entityObject.setAmount(upperBound);
		}
	}
	
	public boolean checkExtinction(EntityObject entityObject){
		float extinctionLine = entityObject.getObjectData().getObjectExtinctAmount();
		if (entityObject.getAmount() < extinctionLine) {
			entityObject.setAmount(extinctionLine);
			entityObject.removeCurrentObjectAndConnections();
			return true;
		}
		return false;
	}

}
