package waterSphere;

import models.RawModel;
import renderEngine.Loader;
import terrainsSphere.TerrainSphere;

public class WaterSphere {
	
	
	private RawModel model;
	private float waterHeightScale;
	
	private int[] indices;
	private float[] vertices;
	private float[] polar;
	
	private int[] indicesFinal;
	private float[] verticesFinal;
	private float[] polarFinal;
	
	public WaterSphere(TerrainSphere terrainSphere, float scale, Loader loader){
		this.waterHeightScale = scale;
		this.waterHeightScale = 1;
		this.model = generateWaterTiles(terrainSphere, loader);
	}
	
	public RawModel getModel(){
		return model;
	}

	
	
	private RawModel generateWaterTiles(TerrainSphere terrainSphere, Loader loader){
		
		indices = terrainSphere.getIndicesFinal();
		vertices = terrainSphere.getVerticesUniform();
		polar = terrainSphere.getPolarFinal();
		
		indicesFinal = new int[indices.length];
		verticesFinal = new float[vertices.length];
		polarFinal = new float[vertices.length];
		
		for (int i=0; i<indices.length; i++){
			indicesFinal[i] = indices[i];
		}
		for (int i=0; i<vertices.length; i++){
			verticesFinal[i] = vertices[i] * waterHeightScale;
			if (i%3 == 0) {
				polarFinal[i] = polar[i] * waterHeightScale;
			}
			else{
			polarFinal[i] = polar[i];
			}
		}
		
		return loader.loadToVAO(verticesFinal, polarFinal, indicesFinal, 3);
	}



}
