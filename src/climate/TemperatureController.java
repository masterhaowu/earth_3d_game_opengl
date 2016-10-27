package climate;

import java.util.List;

import terrainsSphere.HeightGeneratorSphere;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;

public class TemperatureController {
	
	public static final float POLAR_TEMP = -30;
	public static final float EQUATOR_TEMP = 50;
	public static final float PEAK_TEMP_DIFF = 50; //temperature difference between highest point and 0
	public static final float BOTTOM_TEMP_DIFF = 50; 
	
	public static float tempRange = (EQUATOR_TEMP - POLAR_TEMP) / 4f;
	
	public static float frigidCenter = (EQUATOR_TEMP - POLAR_TEMP) / 8f;
	public static float borealCenter = (EQUATOR_TEMP - POLAR_TEMP) * 3f / 8f;
	public static float temperateCenter = (EQUATOR_TEMP - POLAR_TEMP) * 5f / 8f;
	public static float tropicCenter = (EQUATOR_TEMP - POLAR_TEMP) * 7f / 8f;
	
	private TerrainSphere terrainSphere;
	
	public TemperatureController(TerrainSphere terrainSphere){
		this.terrainSphere = terrainSphere;
	}
	
	
	public void calculateAndUpdateTemperature(TerrainFace face){
		
		float temperature = 0;
		
		float lat = face.getAveragePolar().y;
		
		//System.out.println(face.getNeighorVerticesDefault().get(0).getPolar());
		lat = (float) Math.toDegrees(lat);
		//System.out.println(lat);
		lat = Math.abs(lat);
		
		
		
		temperature = EQUATOR_TEMP - (EQUATOR_TEMP - POLAR_TEMP) * lat / 90;
		//System.out.println(temperature);
		float height = face.getHeight();
		
		if (height >= 0) {
			temperature -= PEAK_TEMP_DIFF * height / HeightGeneratorSphere.AMPLITUDE / 3f;
		}
		else {
			temperature -= BOTTOM_TEMP_DIFF * (-height) / HeightGeneratorSphere.AMPLITUDE / 3f;
		}
		
		face.setTemperature(temperature);
		
		//return 0;
	}
	
	public void updateSphereTemp(){
		List<TerrainFace> faces = terrainSphere.getFinalTerrainFaces();
		for (int i=0; i<faces.size(); i++){
			calculateAndUpdateTemperature(faces.get(i));
		}
	}
	
	

}
