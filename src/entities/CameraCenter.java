package entities;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import terrainsSphere.TerrainSphere;
import toolbox.MousePicker;

public class CameraCenter extends Entity {

	private static final float RUNSPEED = 20;
	private static final float TURNSPEED = 160;
	
	private static final float MOVESPEEDTHETA1 = 2;
	private static final float MOVESPEEDTHETA2 = 4;
	
	public static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	private static final float MOUSE_THRESHOLD = 0.05f;
	
	private static final float THETA_SCALE = 800f;
	

	// private static final float TERRAIN_HEIGHT = 0;

	private float currentSpeed = 0;
	private float currentSpeedOrtho = 0;
	private float currentTurnSpeed = 0;
	private float upwardSpeed = 0;
	
	private float yawForCamera;
	private float pitchForCamera;
	
	private float currentSpeedTheta1 = 0;
	private float currentSpeedTheta2 = 0;

	private boolean isInAir = false;
	
	private float playerHeight;
	
	private Camera camera;

	// private float playerCenter = 0;

	public CameraCenter(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		playerHeight = position.z;
		
	}
	
	public void move(TerrainSphere terrainSphere){
		checkInputs();
		//super.setPolar(polar);
		//System.out.println(camera.getAngleAroundPlayer());
		float theta1Inc = (float) (currentSpeedTheta1/THETA_SCALE);
		float theta2Inc = (float) (currentSpeedTheta2/THETA_SCALE * Math.cos(camera.getAngleAroundPlayer()));
		super.increasePolar(0, theta1Inc, theta2Inc);
		//super.increasePosition(currentSpeedTheta1/800, 0, 0);
		//float rotX = (float)( - currentSpeedTheta1/THETA_SCALE * 180/Math.PI);
		//float rotY = (float)( - currentSpeedTheta2/THETA_SCALE * 180/Math.PI);
		//float rotZ = (float) (- currentSpeedTheta2/THETA_SCALE * 180/Math.PI);
		yawForCamera += (float) (currentSpeedTheta2/THETA_SCALE * 180/Math.PI);
		pitchForCamera -= (float) (currentSpeedTheta1/THETA_SCALE * 180/Math.PI);
		//super.increaseRotation(rotX, rotY, rotZ);
		
		//super.increaseRotation(0, rotY, 0);
		//super.increaseRotation(rotX, 0, 0);
		
		super.updateRotation();
		//super.increaseRotation(rotX, 0, rotZ);
		//super.increasePolar(0.0f, 0, 0);
		//float radius = 
		
	}

	public void move(List<Terrain> terrains) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float distanceOrtho = currentSpeedOrtho * DisplayManager.getFrameTimeSeconds();
		//float totalDistance = Math.abs((float) Math.pow(Math.pow(distance, 2) + Math.pow(distanceOrtho, 2), 0.5));
		//System.out.println(camera.getAngleAroundPlayer());
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		dx += (float) (distanceOrtho * Math.cos(Math.toRadians(super.getRotY())));
		dz += (float) (-distanceOrtho * Math.sin(Math.toRadians(super.getRotY())));
		float terrainHeight = 0;
		for (Terrain terrain : terrains) {
			if (checkOnTerrian(terrain)) {
				//System.out.print("here\n");
				terrainHeight += terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
			}
			//terrainHeight += terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		}
		
		super.increasePosition(dx, 0, dz);
		upwardSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if (super.getPosition().y < terrainHeight) {
			upwardSpeed = 0;
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
	}
	
	public void move(List<Terrain> terrains, MousePicker picker) {
		//checkInputs();
		//checkMousePos(picker);
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float distanceOrtho = currentSpeedOrtho * DisplayManager.getFrameTimeSeconds();
		//float totalDistance = Math.abs((float) Math.pow(Math.pow(distance, 2) + Math.pow(distanceOrtho, 2), 0.5));
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		dx += (float) (distanceOrtho * Math.cos(Math.toRadians(super.getRotY())));
		dz += (float) (-distanceOrtho * Math.sin(Math.toRadians(super.getRotY())));
		float terrainHeight = 0;
		for (Terrain terrain : terrains) {
			if (checkOnTerrian(terrain)) {
				//System.out.print("here\n");
				terrainHeight += terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
			}
			//terrainHeight += terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		}
		
		super.increasePosition(dx, 0, dz);
		upwardSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if (super.getPosition().y < terrainHeight) {
			upwardSpeed = 0;
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
	}

	private boolean checkOnTerrian(Terrain terrain) {
		boolean isOnTerrian = false;
		float terrianSize = terrain.getSize();
		float terrianX = terrain.getX();
		float terrianZ = terrain.getZ();
		//System.out.print("terrianX is " + terrain.getX() + "\n");
		if (super.getPosition().x >= terrianX && super.getPosition().x < terrianX + terrianSize
				&& super.getPosition().z >= terrianZ && super.getPosition().z < terrianZ + terrianSize) {
			isOnTerrian = true;
		}
		return isOnTerrian;
	}

	private void jump() {
		if (!isInAir) {
			this.upwardSpeed = JUMP_POWER;
			isInAir = true;
		}
	}
	
	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeedTheta1 = MOVESPEEDTHETA1;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeedTheta1 = -MOVESPEEDTHETA2;
		} else {
			this.currentSpeedTheta1 = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentSpeedTheta2 = MOVESPEEDTHETA2;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentSpeedTheta2 = -MOVESPEEDTHETA2;
		} else {
			this.currentSpeedTheta2 = 0;
		}
		
		currentSpeedTheta1 = currentSpeedTheta1 * camera.getDistanceFromPlayer() / 200;
		currentSpeedTheta2 = currentSpeedTheta2 * camera.getDistanceFromPlayer() / 300;

		

		

	}

	private void checkInputs2() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUNSPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUNSPEED;
		} else {
			this.currentSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentSpeedOrtho = RUNSPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentSpeedOrtho = -RUNSPEED;
		} else {
			this.currentSpeedOrtho = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			this.currentTurnSpeed = -TURNSPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			this.currentTurnSpeed = TURNSPEED;
		} else {
			this.currentTurnSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}

	}
	
	private void checkMousePos(MousePicker picker){
		Vector2f normalizedXY = picker.getNormalizedXY();
		//System.out.println(normalizedXZ.x);
		float normalizedX = normalizedXY.x;
		float normalizedY = normalizedXY.y;
		if (normalizedX > 1 - MOUSE_THRESHOLD){
			this.currentSpeedOrtho = -RUNSPEED;
		} 
		else if (normalizedX < MOUSE_THRESHOLD - 1){
			this.currentSpeedOrtho = RUNSPEED;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentSpeedOrtho = RUNSPEED;
		} 
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentSpeedOrtho = -RUNSPEED;
		}
		else {
			this.currentSpeedOrtho = 0;
		}
		
		if (normalizedY > 1 - MOUSE_THRESHOLD) {
			this.currentSpeed = RUNSPEED;
		} 
		else if (normalizedY < MOUSE_THRESHOLD - 1){
			this.currentSpeed = - RUNSPEED;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUNSPEED;
		} 
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUNSPEED;
		}
		else {
			this.currentSpeed = 0;
		}
	}

	public float getYawForCamera() {
		return yawForCamera;
	}

	public float getPitchForCamera() {
		return pitchForCamera;
	}

	public float getPlayerHeight() {
		return playerHeight;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	
	
	

}
