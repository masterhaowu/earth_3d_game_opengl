package terrainsSphere;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entityObjects.ObjectData;
import renderEngine.Loader;
import terrains.Terrain;

public class ColourController {
	
	private TerrainSphere terrainSphere;
	//private float[] colourFinal;
	
	public ColourController(TerrainSphere terrainSphere){
		this.terrainSphere = terrainSphere;
		//this.colourFinal = terrainSphere.getColourFinal();
	}
	
	public void updateColourVBO(Loader loader){
		int vbo = terrainSphere.getModel().getVboColourID();
		//float[] testingColour = new float[colourFinal.length];
		//for (int i = 0; i < colourFinal.length; i++) {
		//	colourFinal[i] = colourFinal[i]/2;
		//}
		
		loader.updateColourData(vbo, terrainSphere.getColourFinal());
	}
	
	
	public void setFaceColour(TerrainFace face, Vector3f colour, Loader loader) {
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();

		for (int i=0; i<neighborVertices.size(); i++){
			setVertexColour(neighborVertices.get(i), colour);
		}
		updateColourVBO(loader);
	}
	
	public void addObjectToFace(TerrainFace face, TerrainObject object, Loader loader){
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();

		for (int i=0; i<neighborVertices.size(); i++){
		//for (int i=0; i<1; i++){
			TerrainVertex vertex = neighborVertices.get(i);
			vertex.addObject(object);
			updateVertexColour(vertex);
		}
		//updateColourVBO(loader);
	}
	/*
	public void addObjectToFacePreview(TerrainFace face, ObjectData objectData, float amount){
		if (face == null) {
			return;
		}
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();
		for (int i=0; i<neighborVertices.size(); i++){
			//for (int i=0; i<1; i++){
				TerrainVertex vertex = neighborVertices.get(i);
				vertex.previewColour(objectData, amount);
				//vertex.addObjectDirectlyToVertex(objectData, amount, computeColour);
				//updateVertexColour(vertex);
				updateVertexColourPreview(vertex);
			}
	}
	*/
	public void setTerrain(TerrainFace face, ObjectData objectData){
		if (face == null) {
			return;
		}
		//face.setTerrainTypePreview(face.getTerrainType());
		face.setTerrainType(objectData);
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();
		for (int i=0; i<neighborVertices.size(); i++){
			TerrainVertex vertex = neighborVertices.get(i);
			updateVertexColour(vertex);
		}
		
	}
	
	
	public void setPreviewTerrain(TerrainFace face, ObjectData objectData){
		if (face == null) {
			return;
		}
		face.setTerrainTypePreview(face.getTerrainType());
		face.setTerrainType(objectData);
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();
		for (int i=0; i<neighborVertices.size(); i++){
			TerrainVertex vertex = neighborVertices.get(i);
			updateVertexColourPreview(vertex);
		}
		
	}
	
	public void restoreFaceColour(TerrainFace face){
		if (face == null) {
			return;
		}
		face.setTerrainType(face.getTerrainTypePreview());
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();
		for (int i=0; i<neighborVertices.size(); i++){
			//for (int i=0; i<1; i++){
				TerrainVertex vertex = neighborVertices.get(i);
				restoreVertexColour(vertex);
		}
	}
	
	/*
	public void restoreFaceColour(TerrainFace face){
		if (face == null) {
			return;
		}
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();
		for (int i=0; i<neighborVertices.size(); i++){
			//for (int i=0; i<1; i++){
				TerrainVertex vertex = neighborVertices.get(i);
				restoreVertexColour(vertex);
		}
	}
	*/
	
	public void addObjectToFace(TerrainFace face, ObjectData objectData, float amount, boolean computeColour, int range){
		if (face == null) {
			return;
		}
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();
		for (int i=0; i<neighborVertices.size(); i++){
			//for (int i=0; i<1; i++){
				TerrainVertex vertex = neighborVertices.get(i);
				vertex.addObjectDirectlyToVertex(objectData, amount, computeColour);
				updateVertexColour(vertex);
			}
	}
	
	public void updateVertexColourPreview(TerrainVertex vertex){
		//store the vertex colour into colourFinal
		//Vector3f colour = vertex.getColourPreview();
		vertex.updateTerrainColour();
		Vector3f colour = vertex.getColour();
		int i = vertex.getIndex();
		terrainSphere.getColourFinal()[i * 3] = colour.x;
		terrainSphere.getColourFinal()[i * 3 + 1] = colour.y;
		terrainSphere.getColourFinal()[i * 3 + 2] = colour.z;
		
		
	}
	
	
	public void updateVertexColour(TerrainVertex vertex){
		//store the vertex colour into colourFinal
		vertex.updateTerrainColour();
		Vector3f colour = vertex.getColour();
		int i = vertex.getIndex();
		terrainSphere.getColourFinal()[i * 3] = colour.x;
		terrainSphere.getColourFinal()[i * 3 + 1] = colour.y;
		terrainSphere.getColourFinal()[i * 3 + 2] = colour.z;
		//colourFinal[i * 3] = 0;
		//colourFinal[i * 3 + 1] = colour.y;
		//colourFinal[i * 3 + 2] = colour.z;
		
	}
	/*
	public void restoreVertexColour(TerrainVertex vertex){
		vertex.setColourPreview(new Vector3f(0, 0, 0));
		Vector3f colour = vertex.getColour();
		int i = vertex.getIndex();
		terrainSphere.getColourFinal()[i * 3] = colour.x;
		terrainSphere.getColourFinal()[i * 3 + 1] = colour.y;
		terrainSphere.getColourFinal()[i * 3 + 2] = colour.z;
		
		
	}
	*/
	
	public void restoreVertexColour(TerrainVertex vertex){
		//vertex.setColourPreview(new Vector3f(0, 0, 0));
		vertex.updateTerrainColour();
		Vector3f colour = vertex.getColour();
		int i = vertex.getIndex();
		terrainSphere.getColourFinal()[i * 3] = colour.x;
		terrainSphere.getColourFinal()[i * 3 + 1] = colour.y;
		terrainSphere.getColourFinal()[i * 3 + 2] = colour.z;
		
		
	}
	
	public void simpleResetColour(TerrainFace face, Loader loader){
		List<TerrainVertex> neighborVertices = face.getNeighorVerticesDefault();

		for (int i=0; i<neighborVertices.size(); i++){
			TerrainVertex vertex = neighborVertices.get(i);
			simpleResetColour(vertex);
		}
		updateColourVBO(loader);
		
	}
	
	public void simpleResetColour(TerrainVertex vertex){
		Vector3f colour = vertex.getFilteredColour();
		int i = vertex.getIndex();
		terrainSphere.getColourFinal()[i * 3] = colour.x;
		terrainSphere.getColourFinal()[i * 3 + 1] = colour.y;
		terrainSphere.getColourFinal()[i * 3 + 2] = colour.z;
	}
	

	public void setVertexColour(TerrainVertex vertex, Vector3f colour){
		vertex.setColour(colour);
		int i = vertex.getIndex();
		//System.out.print("updating index " + i + " \n");
		terrainSphere.getColourFinal()[i * 3] = colour.x;
		terrainSphere.getColourFinal()[i * 3 + 1] = colour.y;
		terrainSphere.getColourFinal()[i * 3 + 2] = colour.z;
		
	}
	
	
	

}
