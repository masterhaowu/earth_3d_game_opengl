package toolbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Entity;
import models.RawModel;
import renderEngine.Loader;
import terrainsSphere.TerrainSphere;

public class HighlightedCircle {
	private List<Vector3f> verticesListInVector = new ArrayList<Vector3f>();
	private float[] vertices;
	private RawModel model;
	private Vector3f position;
	//private TerrainSphere terrainSphere;
	private float rotX;
	private float rotY;
	private float rotZ;
	private float scale;
	private float Yoffset;
	
	
	public void updatePositionVBO(Loader loader){
		int vbo = model.getVboPositionID();
		loader.updatePositionData(vbo, vertices);
	}
	
	public void calculateCirclePositionOnSphere(TerrainSphere terrainSphere){
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(this.getPosition(),
				this.getRotX(), this.getRotY(), this.getRotZ(),
				this.getScale());
		//applyTransformationMatrix(transformationMatrix);
		applyTransformationMatrixAndClampToSphere(transformationMatrix, terrainSphere);

	}
	
	private void applyTransformationMatrix(Matrix4f transformationMatrix){
		int verticesPointer = 0;
		for (Vector3f vertex: verticesListInVector){
			Vector4f tempVertex = new Vector4f(vertex.x, vertex.y, vertex.z, 1.0f);
			Matrix4f.transform(transformationMatrix, tempVertex, tempVertex);
			//vertex.x = tempVertex.x;
			//vertex.y = tempVertex.y;
			//vertex.z = tempVertex.z;
			vertices[verticesPointer*3] = tempVertex.x;
			vertices[verticesPointer*3 + 1] = tempVertex.y;
			vertices[verticesPointer*3 + 2] = tempVertex.z;
			verticesPointer++;
			//System.out.println(tempVertex.z);
		}
		//updateVertices();
	}
	
	private void applyTransformationMatrixAndClampToSphere(Matrix4f transformationMatrix, TerrainSphere terrainSphere){
		int verticesPointer = 0;
		for (Vector3f vertex: verticesListInVector){
			Vector4f tempVertex = new Vector4f(vertex.x, vertex.y, vertex.z, 1.0f);
			Matrix4f.transform(transformationMatrix, tempVertex, tempVertex);
			Vector3f tempPolar = Maths.convertToPolar(new Vector3f(tempVertex.x, tempVertex.y, tempVertex.z));
			float tempHeight = terrainSphere.getHeightAdvanced(tempPolar.y, tempPolar.z);
			Vector3f clampedVertex = Maths.convertBackToCart(new Vector3f((tempHeight + Yoffset), tempPolar.y, tempPolar.z));
			vertices[verticesPointer*3] = clampedVertex.x;
			vertices[verticesPointer*3 + 1] = clampedVertex.y;
			vertices[verticesPointer*3 + 2] = clampedVertex.z;
			verticesPointer++;

		}
	}
	
	
	
	public HighlightedCircle(Loader loader, float r1, float r2, int segements){
		this.position = new Vector3f(0, 0, 450);
		this.scale = 1;
		this.rotX = 90;
		this.rotY = 0;
		this.rotZ = 0;
		this.Yoffset = 2;
		
		float intervalAngel = (float) Math.PI * 2 / segements;
		//int vertexPointer = 0;
		float currentAngle = 0;
		
		List<Vector3f> innerPoints = new ArrayList<Vector3f>();
		List<Vector3f> outerPoints = new ArrayList<Vector3f>();
		
		for (int i=0; i<segements; i++){
			
			Vector3f currentInner = new Vector3f((float)(r1 * Math.cos(currentAngle)), Yoffset, (float)(r1 * Math.sin(currentAngle)));
			Vector3f currentOuter = new Vector3f((float)(r2 * Math.cos(currentAngle)), Yoffset, (float)(r2 * Math.sin(currentAngle)));
			innerPoints.add(currentInner);
			outerPoints.add(currentOuter);
			currentAngle += intervalAngel;
			
		}
		
		//add the extra first terms for easier access later
		Vector3f initInner = innerPoints.get(0);
		innerPoints.add(initInner);
		Vector3f initOuter = outerPoints.get(0);
		outerPoints.add(initOuter);
		
		for (int i=0; i<segements; i++){
			verticesListInVector.add(innerPoints.get(i));
			verticesListInVector.add(outerPoints.get(i+1));
			verticesListInVector.add(outerPoints.get(i));
			verticesListInVector.add(innerPoints.get(i));
			verticesListInVector.add(innerPoints.get(i+1));
			verticesListInVector.add(outerPoints.get(i+1));
		}
		
		vertices = new float [verticesListInVector.size() * 3];
		
		for (int i=0; i<verticesListInVector.size(); i++){
			vertices[i*3] = verticesListInVector.get(i).x;
			vertices[i*3 + 1] = verticesListInVector.get(i).y;
			vertices[i*3 + 2] = verticesListInVector.get(i).z;
		}
		
		
		//float[] verticesTesting = { -100, 0, -100, -100, 0,  100, 100, 0, -100, 100, 0, -100, -100, 0, 100, 100, 0, 100 };
		
		
		
		this.model = loader.loadToVAOPosition(vertices, 3);
		//this.model = loader.loadToVAO(vertices, 3);
		
		
	}
	
	public void setToEntityPos(Entity entity){
		this.position = entity.getPosition();
		this.rotX = entity.getRotX();
		this.rotY = entity.getRotY();
		this.rotZ = entity.getRotZ();
		this.scale = entity.getScale();
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public List<Vector3f> getVerticesListInVector() {
		return verticesListInVector;
	}

	public void setVerticesListInVector(List<Vector3f> verticesListInVector) {
		this.verticesListInVector = verticesListInVector;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public RawModel getModel() {
		return model;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
	
	
	

}
