package models;

public class RawModel {
	
	private int vaoID;
	private int verticesCount;
	private int vboPositionID;
	private int vboColourID;
	
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
	
	
	
	

}
