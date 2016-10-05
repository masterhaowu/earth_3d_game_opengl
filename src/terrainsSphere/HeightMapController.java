package terrainsSphere;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.util.ResourceLoader;

public class HeightMapController {
	public static final int SNOW_TERRAIN = 0;
	public static final int MOUNTAIN_TERRAIN = 1;
	public static final int GRASS_TERRAIN = 2;
	public static final int CLIFF_TERRAIN = 3;
	public static final int SHALLOW_WATER_TERRAIN = 4;
	public static final int DEEP_WATER_TERRAIN = 5;
	public static final int PLAIN_TERRAIN = 6;

	public static final Vector3f SNOW_COLOUR = new Vector3f(1, 1, 1);
	public static final Vector3f MOUNTAIN_COLOUR = new Vector3f((float) 90 / 255, (float) 90 / 255, (float) 90 / 255);
	public static final Vector3f GRASS_COLOUR = new Vector3f((float) 181 / 255, (float) 215 / 255, (float) 101 / 255);
	public static final Vector3f CLIFF_COLOUR = new Vector3f((float) 134 / 255, (float) 95 / 255, (float) 47 / 255);
	public static final Vector3f SHALLOW_WATER_COLOUR = new Vector3f((float) 144 / 255, (float) 208 / 255,
			(float) 177 / 255);
	public static final Vector3f DEEEP_WATER_COLOUR = new Vector3f((float) 49 / 255, (float) 67 / 255,
			(float) 178 / 255);
	public static final Vector3f PLAIN_COLOUR = new Vector3f((float) 158 / 255, (float) 149 / 255, (float) 100 / 255);

	public static final Vector3f[] terrainColourArray = { SNOW_COLOUR, MOUNTAIN_COLOUR, GRASS_COLOUR, CLIFF_COLOUR,
			SHALLOW_WATER_COLOUR, DEEEP_WATER_COLOUR, PLAIN_COLOUR };

	public static final int HEIGHT_MAP_WIDTH = 720;
	public static final int HEIGHT_MAP_HEIGHT = 360;
	public static final float HEIGHT_SCALE = 100;

	private float[][] heights;
	private int[][] terrainTypes;

	private HeightGeneratorSphere heightGeneratorSphere;

	public HeightMapController() {
		heights = new float[HEIGHT_MAP_HEIGHT][HEIGHT_MAP_WIDTH];
		terrainTypes = new int[HEIGHT_MAP_HEIGHT][HEIGHT_MAP_WIDTH];
		heightGeneratorSphere = new HeightGeneratorSphere(HEIGHT_MAP_HEIGHT, HEIGHT_MAP_WIDTH);
	}

	public void fillTerrainHeightMapWithEarth() {

		try {
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/res/earth.png"));
			System.out.print(img.getWidth());
			System.out.print(img.getHeight());
			for (int i = 0; i < HEIGHT_MAP_HEIGHT; i++) {
				for (int j = 0; j < HEIGHT_MAP_WIDTH; j++) {
					float height;
					
					height = 0xFF & img.getRGB(HEIGHT_MAP_WIDTH - j - 1, HEIGHT_MAP_HEIGHT - i - 1);
					
					
					height = height / 255 * HeightGeneratorSphere.AMPLITUDE;
					
					
					//height = height - 3.9f;

					heights[i][j] = height;
					int terrainType = 0;
					if (height > HeightGeneratorSphere.AMPLITUDE * 0.45) {
						terrainType = SNOW_TERRAIN;
						// terrainTypes[j][i] = SNOW_TERRAIN;
					} else if (height > HeightGeneratorSphere.AMPLITUDE * 0.18) {
						terrainType = MOUNTAIN_TERRAIN;
					} else if (height > HeightGeneratorSphere.AMPLITUDE * 0.02) {
						terrainType = PLAIN_TERRAIN;
					} else if (height > -HeightGeneratorSphere.AMPLITUDE * 0.05) {
						terrainType = CLIFF_TERRAIN;
					} else if (height > -HeightGeneratorSphere.AMPLITUDE * 0.18) {
						terrainType = SHALLOW_WATER_TERRAIN;
					} else {
						terrainType = DEEP_WATER_TERRAIN;
					}
					terrainTypes[i][j] = terrainType;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fillTerrainHeightMapAndTypes() {

		// heights = new float[HEIGHT_MAP_HEIGHT][HEIGHT_MAP_WIDTH];
		// terrainTypes = new int[HEIGHT_MAP_HEIGHT][HEIGHT_MAP_WIDTH];
		for (int i = 0; i < HEIGHT_MAP_HEIGHT; i++) {
			for (int j = 0; j < HEIGHT_MAP_WIDTH; j++) {
				float height;
				if (i < HEIGHT_MAP_HEIGHT * 1 / 8 || i > HEIGHT_MAP_HEIGHT * 7 / 8) {
					height = heightGeneratorSphere.generateHeight(i, j / 8);
				}
				// else if(i > HEIGHT_MAP_HEIGHT * 7/8){

				// }
				else {
					height = heightGeneratorSphere.generateHeight(i, j);
					// height = HeightGeneratorSphere.AMPLITUDE/8f;
				}
				// float factor = (float) Math.abs((i - (0.5 *
				// HEIGHT_MAP_HEIGHT))/HEIGHT_MAP_HEIGHT) * 16;
				// height = heightGeneratorSphere.generateHeight(i,
				// (int)(j/factor));

				heights[i][j] = height;
				int terrainType = 0;
				if (height > HeightGeneratorSphere.AMPLITUDE * 0.45) {
					terrainType = SNOW_TERRAIN;
					// terrainTypes[j][i] = SNOW_TERRAIN;
				} else if (height > HeightGeneratorSphere.AMPLITUDE * 0.18) {
					terrainType = MOUNTAIN_TERRAIN;
				} else if (height > HeightGeneratorSphere.AMPLITUDE * 0.02) {
					terrainType = PLAIN_TERRAIN;
				} else if (height > -HeightGeneratorSphere.AMPLITUDE * 0.05) {
					terrainType = CLIFF_TERRAIN;
				} else if (height > -HeightGeneratorSphere.AMPLITUDE * 0.18) {
					terrainType = SHALLOW_WATER_TERRAIN;
				} else {
					terrainType = DEEP_WATER_TERRAIN;
				}
				terrainTypes[i][j] = terrainType;
			}
		}
	}

	public float accessHeightMap(float theta1, float theta2) {

		int width = (int) ((theta2 + Math.PI / 2) * HEIGHT_MAP_WIDTH / (2 * Math.PI));
		int height = (int) ((theta1 + Math.PI / 2) * HEIGHT_MAP_HEIGHT / (Math.PI));
		// if (width < 0 || width >= HEIGHT_MAP_WIDTH || height < 0 || height >=
		// HEIGHT_MAP_HEIGHT) {
		// return 0;
		// }
		while (width < 0) {
			width += HEIGHT_MAP_WIDTH;
		}
		while (width >= HEIGHT_MAP_WIDTH) {
			width -= HEIGHT_MAP_WIDTH;
		}
		while (height < 0) {
			height += HEIGHT_MAP_HEIGHT;
		}
		while (height >= HEIGHT_MAP_HEIGHT) {
			height -= HEIGHT_MAP_HEIGHT;
		}
		// return heightGeneratorSphere.generateHeight(width, height);
		return heights[height][width];
	}
	/*
	 * public float getHeight(float theta1, float theta2) { int width = (int)
	 * ((theta2 + Math.PI / 2) * HEIGHT_MAP_WIDTH / (2 * Math.PI)); int height =
	 * (int) ((theta1 + Math.PI / 2) * HEIGHT_MAP_HEIGHT / (Math.PI)); while
	 * (width < 0) { width += HEIGHT_MAP_WIDTH; } while (width >=
	 * HEIGHT_MAP_WIDTH) { width -= HEIGHT_MAP_WIDTH; } while (height < 0) {
	 * height += HEIGHT_MAP_HEIGHT; } while (height >= HEIGHT_MAP_HEIGHT) {
	 * height -= HEIGHT_MAP_HEIGHT; } // return
	 * heightGeneratorSphere.generateHeight(width, height); float sphereHeight =
	 * (heights[height][width] / HEIGHT_SCALE + 1) * scale; return sphereHeight;
	 * }
	 */

	public Vector3f accessColourBasedOnHeight(float theta1, float theta2) {
		int width = (int) ((theta2 + Math.PI / 2) * HEIGHT_MAP_WIDTH / (2 * Math.PI));
		int height = (int) ((theta1 + Math.PI / 2) * HEIGHT_MAP_HEIGHT / (Math.PI));
		if (width < 0 || width >= HEIGHT_MAP_WIDTH || height < 0 || height >= HEIGHT_MAP_HEIGHT) {
			return new Vector3f(0, 0, 0);
		}
		// return heightGeneratorSphere.generateHeight(width, height);
		return terrainColourArray[terrainTypes[height][width]];
	}

}
