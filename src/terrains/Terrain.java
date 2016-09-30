package terrains;

//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.Random;

//import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
//import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Maths;

public class Terrain {
	private static final float SIZE = 800;
	// private static final float MAX_HEIGHT = 40;
	// private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	private static final int SEED = new Random().nextInt(1000000000);
	private static final int VERTEX_COUNT = 64;

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

	// private float
	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;

	private HeightGenerator generator;

	private float[][] heights;
	private int[][] terrainTypes;
	private Vector3f[][] terrainColours;

	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap,
			String heightMap) {
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		generator = new HeightGenerator(gridX, gridZ, VERTEX_COUNT, SEED);
		this.model = generateTerrain(loader, heightMap);

	}

	public float getSize() {
		return SIZE;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	public float getHeightOfTerrain(float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX < 0 || gridX >= heights.length - 1 || gridZ < 0 || gridZ >= heights.length - 1) {
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord)) {
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0),
					new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1),
					new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0),
					new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1),
					new Vector2f(xCoord, zCoord));
		}
		return answer;
	}

	public int getTypeOfTerrain(float worldX, float worldZ) {

		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX < 0 || gridX >= heights.length - 1 || gridZ < 0 || gridZ >= heights.length - 1) {
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		int answer;
		if (xCoord <= (1 - zCoord)) {
			answer = terrainTypes[gridX][gridZ];
		} else {
			answer = terrainTypes[gridX + 1][gridZ + 1];
		}
		return answer;
	}

	private Vector3f calculateFilteredColour(int gridX, int gridZ, int filterRange) {

		Vector3f totalColour = new Vector3f(0, 0, 0);
		for (int i = 0; i < filterRange * 2 + 1; i++) {
			for (int j = 0; j < filterRange * 2 + 1; j++) {
				Vector3f tempColour = getColourBasedOnType(gridX + i - 1, gridZ + j - 1);
				if (tempColour.x < 0) {
					tempColour = getColourBasedOnType(gridX, gridZ);
				}
				Vector3f.add(tempColour, totalColour, totalColour);
			}
		}
		totalColour.x /= ((filterRange * 2 + 1) * (filterRange * 2 + 1));
		totalColour.y /= ((filterRange * 2 + 1) * (filterRange * 2 + 1));
		totalColour.z /= ((filterRange * 2 + 1) * (filterRange * 2 + 1));
		return totalColour;
	}

	private Vector3f getColourBasedOnType(int gridX, int gridZ) {
		Vector3f totalColour = new Vector3f(0, 0, 0);
		if (gridX < 0 || gridX >= heights.length - 1 || gridZ < 0 || gridZ >= heights.length - 1) {
			totalColour.x = -1;
			return totalColour;
		}
		totalColour = terrainColourArray[terrainTypes[gridX][gridZ]];

		return totalColour;
	}

	private RawModel generateTerrain(Loader loader, String heightMap) {

		/*
		 * BufferedImage image = null; try { InputStreamReader isr = new
		 * InputStreamReader(Class.class.getResourceAsStream("/res/" + heightMap
		 * + ".png")); image = ImageIO.read(new File("/res/" + heightMap +
		 * ".png")); //image = new BufferedImage(width, height, imageType) }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		terrainTypes = new int[VERTEX_COUNT][VERTEX_COUNT];
		terrainColours = new Vector3f[VERTEX_COUNT][VERTEX_COUNT];

		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		// float[] coloursPreFilter = new float[count * 3];
		float[] colours = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				float height = getHeight(j, i, generator);
				heights[j][i] = height;
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, generator);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				int terrainType = 0;
				if (height > HeightGenerator.AMPLITUDE * 0.45) {
					terrainType = SNOW_TERRAIN;
					// terrainTypes[j][i] = SNOW_TERRAIN;
				} else if (height > HeightGenerator.AMPLITUDE * 0.18) {
					terrainType = MOUNTAIN_TERRAIN;
				} else if (height > HeightGenerator.AMPLITUDE * 0.02) {
					terrainType = PLAIN_TERRAIN;
				} else if (height > -HeightGenerator.AMPLITUDE * 0.05) {
					terrainType = CLIFF_TERRAIN;
				} else if (height > -HeightGenerator.AMPLITUDE * 0.18) {
					terrainType = SHALLOW_WATER_TERRAIN;
				} else {
					terrainType = DEEP_WATER_TERRAIN;
				}
				terrainTypes[j][i] = terrainType;
				// colours[vertexPointer * 3] = (height +
				// HeightGenerator.AMPLITUDE)/(2f * HeightGenerator.AMPLITUDE);
				// colours[vertexPointer * 3 + 1] = (height +
				// HeightGenerator.AMPLITUDE)/(2f * HeightGenerator.AMPLITUDE);
				// colours[vertexPointer * 3 + 2] = (height +
				// HeightGenerator.AMPLITUDE)/(2f * HeightGenerator.AMPLITUDE);

				// colours[vertexPointer * 3] =
				// terrainColourArray[terrainTypes[j][i]].x;
				// colours[vertexPointer * 3 + 1] =
				// terrainColourArray[terrainTypes[j][i]].y;
				// colours[vertexPointer * 3 + 2] =
				// terrainColourArray[terrainTypes[j][i]].z;

				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}

		// fill the colours of the terrain
		vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				// vertexPointer = i * VERTEX_COUNT + j;
				Vector3f filteredColour = calculateFilteredColour(j, i, 1);
				// Vector3f filteredColour = new Vector3f(0,0,0);

				// filteredColour = terrainColourArray[terrainTypes[j][i]];

				colours[vertexPointer * 3] = filteredColour.x;
				colours[vertexPointer * 3 + 1] = filteredColour.y;
				colours[vertexPointer * 3 + 2] = filteredColour.z;
				vertexPointer++;
			}
		}

		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;

				if ((pointer / 6) % 2 == 1) {
					indices[pointer++] = topLeft;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = topRight;
					indices[pointer++] = topRight;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = bottomRight;
				} else {
					indices[pointer++] = topRight;
					indices[pointer++] = topLeft;
					indices[pointer++] = bottomRight;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = bottomRight;
					indices[pointer++] = topLeft;
				}

				// indices[pointer++] = topLeft;
				// indices[pointer++] = bottomLeft;
				// indices[pointer++] = topRight;
				// indices[pointer++] = topRight;
				// indices[pointer++] = bottomLeft;
				// indices[pointer++] = bottomRight;

			}
		}
		return loader.loadToVAO(vertices, textureCoords, colours, normals, indices);
	}

	private Vector3f calculateNormal(int x, int z, HeightGenerator generator) {
		float heightL = getHeight(x - 1, z, generator);
		float heightR = getHeight(x + 1, z, generator);
		float heightD = getHeight(x, z - 1, generator);
		float heightU = getHeight(x, z + 1, generator);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}

	private float getHeight(int x, int z, HeightGenerator generator) {

		return generator.generateHeight(x, z);
	}

}
