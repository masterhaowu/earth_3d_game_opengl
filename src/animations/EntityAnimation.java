package animations;

import entities.Entity;
import renderEngine.DisplayManager;

public class EntityAnimation {

	
	
	public static void updateScale(Entity entity, float time, float amplitude, float speed){
		float updatedScale = (float) (Math.sin(2 * Math.PI * time * speed) * amplitude + 1);
		entity.setScale(updatedScale);
	}
	
	
}
