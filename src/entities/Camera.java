package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
//import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;

public class Camera {

	private static final float MIN_DISTANCE_TO_PLAYER = 10;
	private static final float MAX_DISTANCE_TO_PLAYER = 500;

	private float distanceFromPlayer = 100;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(100, 30, 0);
	private float pitch;
	private float yaw;
	private float roll;

	// private Vector3f polar = new Vector3f(0, 0, 0);
	private float yawOffset = 0;
	private float pitchOffset = -40;
	private float rollOffset = 0;

	private Player player;

	public Camera(Player player) {
		this.player = player;
		// this.polar = Maths.convertToPolar(position);
	}

	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		// float horizontalDistance = calculateHorizontalDistance();
		// float verticalDistance = calculateVerticalDistance();
		// calculateCameraPosition(horizontalDistance, verticalDistance);
		// calculateCameraPosition(player);
		calculateCameraPositionLookAt(player);
		// calculateCameraPositionAdvanced(player);
		// calculateCameraPositionWithOffsets(player);
	}

	public void invertPitch() {
		this.pitch = -pitch;
	}

	public void invertRoll() {
		this.roll = -roll;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calculateCameraPosition(Player player) {
		// Vector3f polar = new Vector3f(player.getPolar().x +
		// distanceFromPlayer,(float) (-player.getRotX() * Math.PI / 180),
		// (float) (-player.getRotZ() * Math.PI / 180));
		// Vector3f polar = new Vector3f(distanceFromPlayer +
		// player.getPolar().x, player.getPolar().y - (float)Math.PI/8,
		// player.getPolar().z);
		Vector3f polar = new Vector3f(distanceFromPlayer + player.getPolar().x, player.getPolar().y,
				player.getPolar().z);
		position = Maths.convertBackToCart(polar);
		// this.yaw = -player.getRotZ();
		// this.pitch = -player.getRotX();
		// this.yaw = -90;
		// this.pitch = 90;
	}

	private void calculateCameraPositionAdvanced(Player player) {
		// Vector3f polar = new Vector3f(distanceFromPlayer +
		// player.getPolar().x, player.getPolar().y, player.getPolar().z);
		// Vector3f startPosition = Maths.convertBackToCart(polar);
		// calculatePitch();

		this.position.y = (float) (player.getPosition().y
				+ (Math.sin(player.getPolar().y - Math.toRadians(-pitchOffset)) * distanceFromPlayer));
		float dOnXZ = (float) (Math.cos(player.getPolar().y - Math.toRadians(-pitchOffset)) * distanceFromPlayer);
		// System.out.print(this.position.y + "\n");
		// System.out.print(pitchOffset + "\n");
		float totalOnXZ = dOnXZ + (float) (player.getPolar().x * Math.cos(player.getPolar().y));
		// this.position.y = (float) (player.getPosition().y);
		this.position.x = (float) (Math.cos(player.getPolar().z) * totalOnXZ);
		this.position.z = (float) (Math.sin(player.getPolar().z) * totalOnXZ);

	}

	private void calculateCameraPositionWithOffsets(Player player) {
		float rOnRing = (float) (distanceFromPlayer * Math.sin(Math.toRadians(-pitchOffset)));
		// this.position.y = (float) (this.position.y + (rOnRing - rOnRing *
		// Math.cos(Math.toRadians(angleAroundPlayer)))* Math.sin(Math.PI / 2 -
		// player.getPolar().y));
		this.position.y = (float) (this.position.y
				+ (rOnRing - rOnRing * Math.cos(Math.toRadians(angleAroundPlayer))) * Math.cos(player.getPolar().y));
		// this.position.x = (float) (this.position.x - rOnRing *
		// Math.sin(Math.toRadians(angleAroundPlayer)) *
		// Math.sin(player.getPolar().z));
		// this.position.z = (float) (this.position.z - rOnRing *
		// Math.sin(Math.toRadians(angleAroundPlayer)) *
		// Math.cos(player.getPolar().z));
		float middleX = (float) (this.position.x
				- (Math.cos(player.getPolar().y - Math.toRadians(-pitchOffset)) - Math.cos(player.getPolar().y))
						* distanceFromPlayer * Math.cos(player.getPolar().z));
		float middleZ = (float) (this.position.z
				- (Math.cos(player.getPolar().y - Math.toRadians(-pitchOffset)) - Math.cos(player.getPolar().y))
						* distanceFromPlayer * Math.sin(player.getPolar().z));
		float offsetComp = (float) (Math.cos(Math.toRadians(angleAroundPlayer))
				* (Math.cos(player.getPolar().y - Math.toRadians(-pitchOffset)) - Math.cos(player.getPolar().y))
				* distanceFromPlayer);
		float offsetCompOrtho = (float) (rOnRing * Math.sin(Math.toRadians(angleAroundPlayer)));
		float offsetCompX = (float) (offsetComp * Math.cos(player.getPolar().z));
		float offsetCompZ = (float) (offsetComp * Math.sin(player.getPolar().z));
		float offsetCompOrthoX = (float) (offsetCompOrtho * Math.sin(player.getPolar().z));
		float offsetCompOrthoZ = (float) (offsetCompOrtho * Math.cos(player.getPolar().z));

		this.position.x = middleX + offsetCompX - offsetCompOrthoX;
		this.position.z = middleZ + offsetCompZ + offsetCompOrthoZ;

		float orthoDistance = (float) (distanceFromPlayer * Math.cos(Math.toRadians(-pitchOffset)));
		float pitchSecondOffset = (float) Math
				.asin(rOnRing * Math.cos(Math.toRadians(angleAroundPlayer)) / orthoDistance); // in
																								// radians
		pitchSecondOffset = (float) Math.toDegrees(pitchSecondOffset);
		// float yawSecondOffset = angleAroundPlayer;
		// float yawSecondOffset = (float) Math.acos(rOnRing *
		// Math.cos(Math.abs(offsetComp) / rOnRing));
		// this.yaw = this.yaw + yawSecondOffset;
		// this.roll = this.roll + yawSecondOffset;
		this.roll = angleAroundPlayer;
		// this.pitch = -player.getPitchForCamera() + pitchSecondOffset;

	}

	private void calculateCameraPositionLookAt(Player player) {
		float radius = distanceFromPlayer;
		float theta1 = (float) Math.toRadians(90 + pitchOffset);
		float theta2 = (float) Math.toRadians(180 + angleAroundPlayer);
		// theta1 = 0;
		// theta2 = 0;
		//System.out.print("theta 1 is: " + theta1 + "theta 2 is: " + theta2 + "\n");
		Vector3f playerToCamera = Maths.convertBackToCart(new Vector3f(radius, theta1, theta2));
		Vector3f cameraPosModelSpace = new Vector3f(0, 0, 0);
		cameraPosModelSpace.x = playerToCamera.x + 0;
		cameraPosModelSpace.y = playerToCamera.y;// + player.getPlayerHeight();
		cameraPosModelSpace.z = playerToCamera.z + player.getPlayerHeight();
		
		cameraPosModelSpace.x =  playerToCamera.z;
		cameraPosModelSpace.y =  playerToCamera.x;
		cameraPosModelSpace.z =  playerToCamera.y + player.getPlayerHeight();

		//System.out.print("vector x is: " + playerToCamera.x + "vector y is: " + playerToCamera.y + "vector z is: " + playerToCamera.z + "\n");
		Vector3f cameraPosModelSpacePolar = Maths.convertToPolar(cameraPosModelSpace);
		Vector3f playerPolar = player.getPolar();

		float theta1Offset = playerPolar.y;
		float theta2Offset = (float) (playerPolar.z - Math.PI / 2);

		Vector3f cameraWorldSpacePolar = new Vector3f(cameraPosModelSpacePolar.x,
				cameraPosModelSpacePolar.y + theta1Offset, cameraPosModelSpacePolar.z + theta2Offset);
		
		this.position = Maths.convertBackToCart(cameraWorldSpacePolar);
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.03f;
		distanceFromPlayer -= zoomLevel;
		if (distanceFromPlayer < MIN_DISTANCE_TO_PLAYER) {
			distanceFromPlayer = MIN_DISTANCE_TO_PLAYER;
		}
		if (distanceFromPlayer > MAX_DISTANCE_TO_PLAYER) {
			distanceFromPlayer = MAX_DISTANCE_TO_PLAYER;
		}

	}

	private void calculatePitch() {
		if (Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitchOffset -= pitchChange;
			// pitch -= pitchChange;
			if (pitchOffset>0) {
				pitchOffset = 0;
			}
			if (pitchOffset < -80) {
				pitchOffset = -80;
			}
		}
		// pitch = -player.getRotX() + pitchOffset;
		// pitch = -player.getPitchForCamera() + pitchOffset;
		// pitch = 90;
	}

	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.1f;
			angleAroundPlayer -= angleChange;
			// roll -= angleChange;

		}
		// yaw = -player.getRotZ();// + yawOffset;
		yaw = player.getYawForCamera() + yawOffset;
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			yawOffset -= 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			yawOffset += 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			rollOffset -= 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			rollOffset += 1;
		}

		roll = rollOffset;
	}

	public Player getPlayer() {
		return player;
	}

	public float getPitchOffset() {
		return pitchOffset;
	}

	public float getDistanceFromPlayer() {
		return distanceFromPlayer;
	}
	
	
	
	

}
