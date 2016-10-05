package models;

import org.lwjgl.util.vector.Vector3f;

import objConverter.ModelData;

public class RawModel {
	
	private int vaoID;
	private int verticesCount;
	private int vboPositionID;
	private int vboColourID;
	private Vector3f min;
	private Vector3f max;
	private boolean hasMinMax = false;
	private ModelData modelData;
	private Boolean hasModelData = false;
	
	public RawModel(int vaoID, int verticesCount){
		this.vaoID = vaoID;
		this.verticesCount = verticesCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVerticesCount() {
		return verticesCount;
	}

	public int getVboPositionID() {
		return vboPositionID;
	}

	public int getVboColourID() {
		return vboColourID;
	}

	public void setVboPositionID(int vboPositionID) {
		this.vboPositionID = vboPositionID;
	}

	public void setVboColourID(int vboColourID) {
		this.vboColourID = vboColourID;
	}

	public Vector3f getMin() {
		return min;
	}

	public void setMin(Vector3f min) {
		this.min = min;
		this.hasMinMax = true;
	}

	public Vector3f getMax() {
		return max;
	}

	public void setMax(Vector3f max) {
		this.max = max;
		this.hasMinMax = true;
	}

	public boolean isHasMinMax() {
		return hasMinMax;
	}

	public ModelData getModelData() {
		return modelData;
	}

	public void setModelData(ModelData modelData) {
		this.modelData = modelData;
		this.hasModelData = true;
	}

	public Boolean getHasModelData() {
		return hasModelData;
	}
	
	
	
	
	
	

}
