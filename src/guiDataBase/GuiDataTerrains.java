package guiDataBase;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gameDataBase.ObjectsNetwork;
import guis.GuiObjectUnit;
import renderEngine.Loader;

public class GuiDataTerrains {

	public List<GuiObjectUnit> guiObjectUnits;
	
	public GuiObjectUnit plain;
	public GuiObjectUnit savanna;
	
	public GuiDataTerrains(){
		this.guiObjectUnits = new ArrayList<GuiObjectUnit>();
		
		
	}
	
	public void loadTerrains(Loader loader){
		plain = new GuiObjectUnit(new Vector2f(0, 0),new Vector2f(1, 1), loader);
		plain.setTerrainUnit(ObjectsNetwork.plainTerrain);
		this.guiObjectUnits.add(plain);
		
		savanna = new GuiObjectUnit(new Vector2f(0, 0),new Vector2f(1, 1), loader);
		savanna.setTerrainUnit(ObjectsNetwork.savannaTerrain);
		this.guiObjectUnits.add(savanna);
	}
	
	
	
	
}

