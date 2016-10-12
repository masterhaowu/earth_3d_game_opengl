package animations;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import renderEngine.DisplayManager;

public class AnimationController {

	private float time;
	private List<Entity> entitiesWithScaleAnimations;
	private List<Float> entitiesWithScaleAnimationsAmplitude;
	private List<Float> entitiesWithScaleAnimationsSpeed;

	public AnimationController() {
		time = 0;

		entitiesWithScaleAnimations = new ArrayList<Entity>();
		entitiesWithScaleAnimationsAmplitude = new ArrayList<Float>();
		entitiesWithScaleAnimationsSpeed = new ArrayList<Float>();
	}

	public void update() {
		updateTime();
		updateScaleAnimations();
	}

	public void updateScaleAnimations() {
		for (int i = 0; i < entitiesWithScaleAnimations.size(); i++) {
			EntityAnimation.updateScale(entitiesWithScaleAnimations.get(i), time,
					entitiesWithScaleAnimationsAmplitude.get(i), entitiesWithScaleAnimationsSpeed.get(i));
		}
	}

	public void addEntityWithScaleAnimation(Entity entity, float amplitude, float speed) {
		entitiesWithScaleAnimations.add(entity);
		entitiesWithScaleAnimationsAmplitude.add(amplitude);
		entitiesWithScaleAnimationsSpeed.add(speed);
	}

	private void updateTime() {
		time += DisplayManager.getFrameTimeSeconds() * 0.3f;
		time %= 1;
		// shader.loadTime(time);
	}

}
