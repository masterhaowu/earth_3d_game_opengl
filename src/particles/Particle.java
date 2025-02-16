package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.CameraCenter;
import renderEngine.DisplayManager;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	
	private float elapsedTime = 0;
	private float distance;
	
	public boolean marked = false;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	
	private ParticleTexture particleTexture;

	public Particle(ParticleTexture particleTexture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		this.particleTexture = particleTexture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}
	
	
	
	

	public float getDistance() {
		return distance;
	}





	public Vector2f getTexOffset1() {
		return texOffset1;
	}





	public Vector2f getTexOffset2() {
		return texOffset2;
	}





	public float getBlend() {
		return blend;
	}





	public ParticleTexture getParticleTexture() {
		return particleTexture;
	}



	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}
	
	
	protected boolean update(Camera camera){
		//velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		updateTextureCoordInfo();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
		
	}
	
	private void updateTextureCoordInfo(){
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = particleTexture.getNumberOfRows() * particleTexture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		/*
		if (marked) {
			System.out.println(blend);
		}
		*/
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset, int index){
		int column = index % particleTexture.getNumberOfRows();
		int row = index / particleTexture.getNumberOfRows();
		offset.x = (float) column / particleTexture.getNumberOfRows();
		offset.y = (float) row / particleTexture.getNumberOfRows();
	}
	

}
