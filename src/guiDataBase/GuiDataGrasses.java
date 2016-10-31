package guiDataBase;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gameDataBase.EntityModelDataBase;
import gameDataBase.ObjectsNetwork;
import guis.GuiObjectUnit;
import renderEngine.Loader;

public class GuiDataGrasses {
	
	public List<GuiObjectUnit> guiObjectUnits;
	
	public GuiObjectUnit lowGrass;
	public GuiObjectUnit lowGrass3;
	
	public GuiDataGrasses(){
		this.guiObjectUnits = new ArrayList<GuiObjectUnit>();
	}
	
	public void loadGrasses(Loader loader){
		lowGrass = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		lowGrass.setModel(EntityModelDataBase.lowGrass1Model);
		lowGrass.setName("Low Grass");
		lowGrass.setObjectUnit(ObjectsNetwork.lowGrass1);
		lowGrass.setModelScaleDown(1.5f);
		lowGrass.setPositionOffsets(new Vector2f(0, 0.3f));
		this.guiObjectUnits.add(lowGrass);
		
		lowGrass3 = new GuiObjectUnit(new Vector2f(0, 0), new Vector2f(0, 0), loader);
		lowGrass3.setModel(EntityModelDataBase.lowGrass3Model);
		lowGrass3.setName("Low Grass Spiky");
		lowGrass3.setObjectUnit(ObjectsNetwork.lowGrass3);
		lowGrass3.setModelScaleDown(1.5f);
		lowGrass3.setPositionOffsets(new Vector2f(0, 0.3f));
		this.guiObjectUnits.add(lowGrass3);
	}

}
