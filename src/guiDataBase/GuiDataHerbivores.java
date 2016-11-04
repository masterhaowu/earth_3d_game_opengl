package guiDataBase;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gameDataBase.EntityModelDataBase;
import gameDataBase.ObjectsNetworkHerbivore;
import gameDataBase.ObjectsNetworkCarnivore;
import guis.GuiObjectUnit;
import renderEngine.Loader;

public class GuiDataHerbivores {
	
	public List<GuiObjectUnit> guiObjectUnits;
	
	public GuiObjectUnit deer1;
	
	public GuiDataHerbivores(){
		this.guiObjectUnits = new ArrayList<GuiObjectUnit>();
	}
	
	public void loadHerbivores(Loader loader){
		deer1 = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		deer1.setModel(EntityModelDataBase.deer1Model);
		deer1.setName("Forrest Deer");
		deer1.setObjectUnit(ObjectsNetworkHerbivore.deer1);
		this.guiObjectUnits.add(deer1);
	}

}
