package particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import terrainsSphere.TerrainFace;
import terrainsSphere.TerrainSphere;
import toolbox.Maths;

public class SnowSystem {

	public static final float SNOW_EFFECT_HEIGHT = 15;
	public static final float SNOW_GRAVITY = 0f;
	public static final float SNOW_LENGTH = 4;
	public static final float SNOW_SCALE = 1;
	public static final float SNOW_CENTER_MAX_ERROR = 1;
	public static final float SNOW_SPEED = 1.5f;
	public static final float SNOW_SPEED_MAX_ERROR = 0.5f;

	private List<TerrainFace> faces;
	private ParticleTexture particleTexture;
	private TerrainSphere terrainSphere;
	private float speedError = 0;
	private float lifeError = 2f;
	private float scaleError = 1f;
	private boolean randomRotation = false;

	private Random random = new Random();

	public SnowSystem(ParticleTexture particleTexture, TerrainSphere terrainSphere) {
		this.particleTexture = particleTexture;
		this.terrainSphere = terrainSphere;
		this.faces = new ArrayList<TerrainFace>();
	}

	public void updateSnowSystem() {
		//System.out.println(faces.size());
		for(int i=0; i<faces.size(); i++){
			generateParticles(faces.get(i));
		}
	}

	public void generateParticles(TerrainFace face) {
		Vector3f centerPolar = face.getAveragePolarWithHeight();
		
		Vector3f snowCenter = new Vector3f(centerPolar.x * terrainSphere.getScale() + SNOW_EFFECT_HEIGHT, centerPolar.y, centerPolar.z);
		//System.out.println(centerPolar.x * terrainSphere.getScale() + SNOW_EFFECT_HEIGHT);
		//centerPolar.x += SNOW_EFFECT_HEIGHT;
		
		Vector3f systemCenter = Maths.convertBackToCart(snowCenter);
		systemCenter.x = generateValue(systemCenter.x, SNOW_CENTER_MAX_ERROR);
		systemCenter.y = generateValue(systemCenter.y, SNOW_CENTER_MAX_ERROR);
		systemCenter.z = generateValue(systemCenter.z, SNOW_CENTER_MAX_ERROR);
		float delta = DisplayManager.getFrameTimeSeconds();
		float snowAmount = face.getSnowAmount();
		snowAmount = generateValue(snowAmount, 0.1f);
		float particlesToCreate = snowAmount * delta;
		int count = (int) Math.floor(particlesToCreate);
		float partialParticle = particlesToCreate % 1;
		
		Vector3f velocity = new Vector3f(-systemCenter.x, -systemCenter.y, -systemCenter.z);
		velocity.normalise();
		float speed = generateValue(SNOW_SPEED, SNOW_SPEED_MAX_ERROR);
		velocity.scale(speed);
		
		float height = SNOW_EFFECT_HEIGHT;
		float lifeLength = height / speed;
		
		
		for (int i = 0; i < count; i++) {
			emitParticle(systemCenter, lifeLength, velocity);
		}
		if (Math.random() < partialParticle) {
			emitParticle(systemCenter, lifeLength, velocity);
		}
	}

	private void emitParticle(Vector3f center, float lifeLength, Vector3f velocity) {
		
		float scale = generateValue(SNOW_SCALE, scaleError);
		//float lifeLength = generateValue(SNOW_LENGTH, lifeError);
		new Particle(particleTexture, new Vector3f(center), velocity, SNOW_GRAVITY, lifeLength, generateRotation(),
				scale);
	}

	private float generateValue(float average, float errorMargin) {
		float offset = (random.nextFloat() - 0.5f) * 2f * errorMargin;
		return average + offset;
	}

	private float generateRotation() {
		if (randomRotation) {
			return random.nextFloat() * 360f;
		} else {
			return 0;
		}
	}

	public void addFace(TerrainFace face) {
		//System.out.println("here");
		faces.add(face);
	}

	public void removeFace(TerrainFace face) {
		for (int i = 0; i < faces.size(); i++) {
			if (faces.get(i) == face) {
				faces.remove(i);
			}
		}
	}

}
