package gameDataBase;

import entityObjects.PredationUnit;

public class PredationNetwork {
	
	public static PredationUnit deer1ToPine1;
	public static PredationUnit deer1ToSeasonalTree1;
	public static PredationUnit lion1ToDeer1;
	
	
	public static void fillPredationNetwork(){
		deer1ToPine1 = new PredationUnit(ObjectsNetworkHerbivore.deer1, ObjectsNetworkTree.pineTree1, 0.1f, 0.3f);
		deer1ToPine1 = new PredationUnit(ObjectsNetworkHerbivore.deer1, ObjectsNetworkTree.seasonalTree1, 0.1f, 0.3f);
		lion1ToDeer1 = new PredationUnit(ObjectsNetworkCarnivore.lion1, ObjectsNetworkHerbivore.deer1, 0.1f, 0.3f);
	}

}
