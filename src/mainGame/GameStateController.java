package mainGame;

public class GameStateController {
	
	
	public static final int IDEL_TESTING = 0;
	public static final int DRAGGING_ENTITY_OBJECT = 120;
	
	
	public static int currentState;


	public static int getCurrentState() {
		return currentState;
	}


	public static void setCurrentState(int currentState) {
		GameStateController.currentState = currentState;
	}
	
	
	
	
	
	

}
