package fontRendering;

import java.awt.Font;

import fontMeshCreator.FontType;
import renderEngine.Loader;

public class FontController {

	public static FontType candara;
	
	public static void init(Loader loader){
		candara = new FontType(loader.loadFontTexture("candara"), "candara");
	}
}
