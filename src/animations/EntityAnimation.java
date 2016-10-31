package animations;

import entities.Entity;
import renderEngine.DisplayManager;
import time.TimeController;

public class EntityAnimation {

	
	
	public static void updateScale(Entity entity, float amplitude, float speed){
		//System.out.println(TimeController.time);
		float updatedScale = (float) (Math.sin(2 * Math.PI * TimeController.time * speed) * amplitude + 1);
		entity.setScale(updatedScale);
	}
	
	
}
