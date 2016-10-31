package time;

import renderEngine.DisplayManager;

public class TimeController {
	
	public static float time;
	
	public static void updateTime(){
		time += DisplayManager.getFrameTimeSeconds() * 0.3f;
		time %= 1;
	}

}
