package guiDataBase;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gameDataBase.EntityModelDataBase;
import gameDataBase.ObjectsNetwork;
import gameDataBase.ObjectsNetworkTree;
import guis.GuiObjectUnit;
import renderEngine.Loader;

public class GuiDataTrees {
	
	public List<GuiObjectUnit> guiObjectUnits;
	
	public GuiObjectUnit testingTree;
	//public GuiObjectUnit savanna;
	public GuiObjectUnit pine1;
	public GuiObjectUnit seasonalTree1;
	
	public GuiDataTrees(){
		this.guiObjectUnits = new ArrayList<GuiObjectUnit>();
		
		
	}
	
	public void loadTrees(Loader loader){
		testingTree = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		testingTree.setModel(EntityModelDataBase.testingTreeModel);
		testingTree.setName("Testing Tree");
		testingTree.setObjectUnit(ObjectsNetworkTree.simpleTree);
		this.guiObjectUnits.add(testingTree);
		
		pine1 = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		pine1.setModel(EntityModelDataBase.pineTree1Model);
		pine1.setName("Pine Tree");
		pine1.setObjectUnit(ObjectsNetworkTree.pineTree1);
		this.guiObjectUnits.add(pine1);
		
		seasonalTree1 = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		seasonalTree1.setModel(EntityModelDataBase.seasonalTree1Model);
		seasonalTree1.setName("Seasonal Tree");
		seasonalTree1.setObjectUnit(ObjectsNetworkTree.seasonalTree1);
		this.guiObjectUnits.add(seasonalTree1);
		
	}

}
