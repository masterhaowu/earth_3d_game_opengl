package water;

import models.RawModel;
import renderEngine.Loader;

public class WaterTile {
	
	public static final float TILE_SIZE = 800;
	public static final int VERTEX_COUNT = 64;
	
	private float height;
	private float x,z;
	
	private RawModel model;
	
	public WaterTile(float centerX, float centerZ, float height, Loader loader){
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
		this.model = generateWaterTiles(loader);
	}
	
	public RawModel getModel(){
		return model;
	}

	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}
	
	private RawModel generateWaterTiles(Loader loader){
		float[] positions = new float[VERTEX_COUNT * VERTEX_COUNT * 2];
		int pointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				positions[pointer++] = j * TILE_SIZE / ((float)VERTEX_COUNT - 1f) - TILE_SIZE/2;
				positions[pointer++] = i * TILE_SIZE / ((float)VERTEX_COUNT - 1f) - TILE_SIZE/2;
			}
		}
		
		pointer = 0;
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
				
				
			
				
			}
		}
		
		return loader.loadToVAO(positions, indices, 2);
	}



}
