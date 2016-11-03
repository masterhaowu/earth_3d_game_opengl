package clock;

import renderEngine.DisplayManager;

public class GameTimeController {
	
	public static final float GAME_BAISC_CYCLE = 1f;
	
	
	public static double gameTimeInRealWorld = 0;
	public static double gameTime = 0;
	public static float speedModifier = 1;
	
	
	public static float gameBasicCycle = 0;
	public static boolean basicCycleHit = false;
	
	
	
	public static void init(){
		gameTimeInRealWorld = 0;
		gameTime = 0;
		gameBasicCycle = 0;
	}
	
	public static void updateGameTime(){
		double timeDiff = DisplayManager.getFrameTimeSeconds();
		gameTimeInRealWorld += timeDiff;
		gameTime += timeDiff * speedModifier;
		basicCycleHit = false;
		gameBasicCycle += timeDiff * speedModifier;
		if (gameBasicCycle > GAME_BAISC_CYCLE) {
			basicCycleHit = true;
			gameBasicCycle -= GAME_BAISC_CYCLE;
		}
		
		return;
	}

}
