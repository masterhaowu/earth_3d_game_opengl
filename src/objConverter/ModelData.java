package objConverter;

import org.lwjgl.util.vector.Vector3f;

public class ModelData {

	private float[] vertices;
	private float[] textureCoords;
	private float[] normals;
	private int[] indices;
	private float furthestPoint;
	private Vector3f min;
	private Vector3f max;

	public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
			float furthestPoint) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.furthestPoint = furthestPoint;
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public int[] getIndices() {
		return indices;
	}

	public float getFurthestPoint() {
		return furthestPoint;
	}

	public Vector3f getMin() {
		return min;
	}

	public void setMin(Vector3f min) {
		this.min = min;
	}

	public Vector3f getMax() {
		return max;
	}

	public void setMax(Vector3f max) {
		this.max = max;
	}
	
	

}
