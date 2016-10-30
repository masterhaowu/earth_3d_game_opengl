package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gameDataBase.EntityModelDataBase;
import gameDataBase.ObjectsNetwork;
import renderEngine.Loader;

public class GuiDataTrees {
	
public List<GuiObjectUnit> guiObjectUnits;
	
	public GuiObjectUnit testingTree;
	//public GuiObjectUnit savanna;
	
	public GuiDataTrees(){
		this.guiObjectUnits = new ArrayList<GuiObjectUnit>();
		
		
	}
	
	public void loadTrees(Loader loader){
		testingTree = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		testingTree.setModel(EntityModelDataBase.testingTreeModel);
		testingTree.setName("Testing Tree");
		testingTree.setObjectUnit(ObjectsNetwork.simpleTree);
		this.guiObjectUnits.add(testingTree);
	}

}
