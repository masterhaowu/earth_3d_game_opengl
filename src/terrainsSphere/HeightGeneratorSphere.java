package terrainsSphere;
 
import java.util.Random;
 
public class HeightGeneratorSphere {
 
    public static final float AMPLITUDE = 10f;
    private static final int OCTAVES = 3;
    private static final float ROUGHNESS = 0.3f;
 
    private Random random = new Random();
    private int seed;
    private int xOffset = 0;
    private int zOffset = 0;
    
    private int mapHeight;
    private int mapWidth;
    
    //private float[][] noises;
    //private boolean hasNoises = false;
 
    public HeightGeneratorSphere() {
        //this.seed = random.nextInt(1000000000);
    }
    
    public HeightGeneratorSphere(int mapHeight, int mapWidth) {
    	this.mapHeight = mapHeight;
    	this.mapWidth = mapWidth;
        //this.seed = random.nextInt(1000000000);
    }
     
    //only works with POSITIVE gridX and gridZ values!
    public HeightGeneratorSphere(int gridX, int gridZ, int vertexCount, int seed) {
        this.seed = seed;
        //this.noises = new float[vertexCount][vertexCount];
        xOffset = (gridX) * (vertexCount-1);
        zOffset = (gridZ) * (vertexCount-1);
    }
    
    public HeightGeneratorSphere(int seed){
    	this.seed = seed;
    }
    
 
    public float generateHeight(int x, int z) {
        float total = 0;
        float d = (float) Math.pow(2, OCTAVES-1);
        for(int i=0;i<OCTAVES;i++){
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
            total += getInterpolatedNoise((x+xOffset)*freq, (z + zOffset)*freq) * amp;
        }
        return total;
        //return -10;
    }
     
    private float getInterpolatedNoise(float x, float z){
        int intX = (int) x;
        int intZ = (int) z;
        float fracX = x - intX;
        float fracZ = z - intZ;
        //if (fracX < 0) {
		//	System.out.print("bad\n");
		//}
        
         
        float v1 = getSmoothNoise(intX, intZ);
        float v2 = getSmoothNoise(intX + 1, intZ);
        float v3 = getSmoothNoise(intX, intZ + 1);
        float v4 = getSmoothNoise(intX + 1, intZ + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracZ);
    }
     
    private float interpolate(float a, float b, float blend){
        double theta = blend * Math.PI;
        float f = (float)(1f - Math.cos(theta)) * 0.5f;
        return a * (1f - f) + b * f;
    }
 
    private float getSmoothNoise(int x, int z) {
        float corners = (getNoise(x - 1, z - 1) + getNoise(x + 1, z - 1) + getNoise(x - 1, z + 1)
                + getNoise(x + 1, z + 1)) / 16f;
        float sides = (getNoise(x - 1, z) + getNoise(x + 1, z) + getNoise(x, z - 1)
                + getNoise(x, z + 1)) / 8f;
        float center = getNoise(x, z) / 4f;
        return corners + sides + center;
    }
 
    private float getNoise(int x, int z) {
    	if (x<0) {
			x += mapHeight;
		}
    	if (x>=mapHeight) {
			x -= mapHeight;
		}
    	if (z<0){
    		z += mapWidth;
    	}
    	if (z>=mapWidth){
    		z -= mapWidth;
    	}
        random.setSeed(x * 49632 + z * 325176 + seed);
        //random.setSeed(x * 49632 + z + seed);
        return random.nextFloat() * 2f - 1f;
    	//if (!hasNoises) {
		//	noises[x][z] = random.nextFloat() * 2f - 1f;
		//}
    	
    	//return noises[x][z];
    }
    
    public float getNoise(float x, float z) {
        random.setSeed((int)(x * 49632) + (int)(z * 325176) + seed);
        //random.setSeed(x * 49632 + z + seed);
        return random.nextFloat() * 2f - 1f;
    	//if (!hasNoises) {
		//	noises[x][z] = random.nextFloat() * 2f - 1f;
		//}
    	
    	//return noises[x][z];
    }
 
}