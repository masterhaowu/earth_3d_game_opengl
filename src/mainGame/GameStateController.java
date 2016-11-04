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
	
	public static int gameModeState; // this depends on the left and right spheres of main game
	
	public static int CTState; //creation_terrain mode
	public static int CAState; //creation_animal mode
	
	public static int gameLeftModeState;
	public static int gameRightModeState;
	//public static final int 
	public static final int CREATION_MODE = 1;
	public static final int RESEARCH_MODE = 2;
	public static final int TERRAIN_MODE = 1;
	public static final int ANIMAL_MODE = 2;
	
	public static final int CREATION_TERRAIN_MODE = 1;
	public static final int CREATION_ANIMAL_MODE = 2;
	public static final int RESEARCH_TERRAIN_MODE = 3;
	public static final int RESEARCH_ANIMAL_MODE = 4;
	
	public static final int CT_IDLE = 0;
	public static final int CT_TERRAIN_TYPE = 1;
	
	public static final int CT_TERRAIN_DRAGGING = 2;
	public static final int CT_GRASS = 15;
	public static final int CT_GRASS_DRAGGING = 16;
	public static final int CT_TREE = 20;
	public static final int CT_TREE_DRAGGING = 21;
	
	public static final int CA_IDLE = 0;
	
	public static final int CA_HERBIVORE = 15;
	public static final int CA_HERBIVORE_DRAGGING = 16;
	public static final int CA_CARNIVORE = 20;
	public static final int CA_CARNIVORE_DRAGGING = 21;
	
	
	
	public static int currentInfoState;
	
	


	public static int getCurrentState() {
		return currentState;
	}


	public static void setCurrentState(int currentState) {
		GameStateController.currentState = currentState;
	}
	
	
	
	
	
	

}
