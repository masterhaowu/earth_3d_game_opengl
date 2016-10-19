package mainGame;

public class GameStateController {
	
	
	public static final int IDEL_TESTING = 0;
	public static final int DRAGGING_ENTITY_OBJECT = 120;
	
	
	public static final int PLAY_MODE_IDLE = 200;
	
	public static final int CREATION_MODE_IDLE = 300;
	public static final int CREATION_MODE_ADD_OBJECT = 310;
	
	public static final int RESEARCH_MODE_IDLE = 700;
	
	
	//public static final int ADD_OBJECT_CLASS1 = 320;
	//public static final int ADD_OBJECT_CLASS2 = 321;
	
	public static final int ADD_OBJECT_DRAGGING = 350;
	
	
	public static int currentState;
	
	public static int currentInfoState;


	public static int getCurrentState() {
		return currentState;
	}


	public static void setCurrentState(int currentState) {
		GameStateController.currentState = currentState;
	}
	
	
	
	
	
	

}
