package entities;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import toolbox.Maths;

public class Entity {
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float rotXOffset;
	private float rotYOffset = 0; // this will be the rotation of the entity wrt the sphere
	private float scale;
	private Vector3f polar;
	
	private int textureIndex = 0;
	private boolean test = true;
	
	private int colourID;
	private boolean highlighted = false;
	
	private boolean enableShaderAnimation = false;
	private boolean scaleAnimation;
	private float scaleAnimationAmplitude;
	private float scaleAnimationSpeed;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.rotXOffset = rotX;
		this.scale = scale;
		this.polar = Maths.convertToPolar(position);
		this.test = true;
		this.enableShaderAnimation = false;
		this.scaleAnimation = false;
	}
	
	
	public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.textureIndex = index;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		
		
	}
	
	
	public float getTextureXOffset(){
		int column = textureIndex%model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset(){
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}
	
	public void increasePosition(float dx, float dy, float dz){
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
		Vector3f newPolar = Maths.convertToPolar(this.position);
		this.polar.x = newPolar.x;
		this.polar.y = newPolar.y;
		this.polar.z = newPolar.z;
	}
	
	public void increaseRotation(float dx, float dy, float dz){
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	public void updateRotation(){
		this.rotX = - (float) (this.polar.y * 180 / Math.PI) + rotXOffset;
		this.rotY = - (float) ((this.polar.z * 180) / Math.PI - 90);
		//System.out.print(this.polar.z*260);
		
		//System.out.print("rotation x is: " + rotX + " and rotation y is: " + rotY + "\n");
	}
	
	public void randomRotZ(){
		Random random = new Random();
		this.rotZ =  (float) (random.nextFloat() * 360);
	}
	
	public void randomRotationOnSphere(){
		Random random = new Random();
		this.rotYOffset =  (float) (random.nextFloat() * 360);
	}
	
	public void increasePolar(float dr, float dt1, float dt2){
		this.polar.x += dr;
		this.polar.y += dt1;
		this.polar.z += dt2;
		Vector3f newPos = Maths.convertBackToCart(this.polar);
		this.position.x = newPos.x;
		this.position.y = newPos.y;
		this.position.z = newPos.z;
	}
	
	
	public TexturedModel getModel() {
		return model;
	}
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
		this.polar = Maths.convertToPolar(position);
		updateRotation();
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
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}


	public Vector3f getPolar() {
		return polar;
	}


	public void setPolar(Vector3f polar) {
		this.polar = polar;
		this.position = Maths.convertBackToCart(polar);
		updateRotation();
	}


	public int getColourID() {
		return colourID;
	}


	public void setColourID(int colourID) {
		this.colourID = colourID;
	}


	public boolean isHighlighted() {
		return highlighted;
	}


	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}


	public boolean isEnableShaderAnimation() {
		return enableShaderAnimation;
	}


	public void setEnableShaderAnimation(boolean enableShaderAnimation) {
		this.enableShaderAnimation = enableShaderAnimation;
	}
	
	
	public void setupScaleAnimation(float amplitude, float speed){
		this.scaleAnimation = true;
		this.scaleAnimationAmplitude = amplitude;
		this.scaleAnimationSpeed = speed;
	}


	public float getRotYOffset() {
		return rotYOffset;
	}


	public void setRotYOffset(float rotYOffset) {
		this.rotYOffset = rotYOffset;
	}
	
	
	
	
	
	
	
	
	
}
