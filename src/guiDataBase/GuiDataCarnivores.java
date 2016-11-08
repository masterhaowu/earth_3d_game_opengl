package guiDataBase;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gameDataBase.EntityModelDataBase;
import gameDataBase.ObjectsNetwork;
import gameDataBase.ObjectsNetworkCarnivore;
import guis.GuiObjectUnit;
import renderEngine.Loader;

public class GuiDataCarnivores {
	
	
	public List<GuiObjectUnit> guiObjectUnits;
	
	public GuiObjectUnit lion1;
	
	
	public GuiDataCarnivores(){
		this.guiObjectUnits = new ArrayList<GuiObjectUnit>();
	}
	
	public void loadCarnivores(Loader loader){
		lion1 = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		lion1.setModel(EntityModelDataBase.lion1Model);
		lion1.setName("Savanna Lion");
		lion1.setObjectUnit(ObjectsNetworkCarnivore.lion1);
		lion1.setModelScaleDown(1.55f);
		lion1.setPositionOffsets(new Vector2f(0, 0.3f));
		this.guiObjectUnits.add(lion1);
	}
	
	

}
