package particles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Entity;
import renderEngine.Loader;

public class ParticleMaster {
	
	//private static List<Particle> particles = new ArrayList<Particle>();
	private static Map<ParticleTexture, List<Particle>> particles = new HashMap<ParticleTexture, List<Particle>>();
	private static ParticleRenderer renderer;
	
	public static void init(Loader loader, Matrix4f projectionMatrix){
		renderer = new ParticleRenderer(loader, projectionMatrix);
		
	}
	
	public static void update(Camera camera){
		
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while (mapIterator.hasNext()){
			Entry<ParticleTexture, List<Particle>> entry = mapIterator.next();
			List<Particle> list = entry.getValue();
			Iterator<Particle> iterator = list.iterator();
			while (iterator.hasNext()){
				Particle p = iterator.next();
				boolean stillAlive = p.update(camera);
				if (!stillAlive) {
					iterator.remove();
					if (list.isEmpty()) {
						mapIterator.remove();
					}
				}
			}
			if (!entry.getKey().useAdditiveBlending()) {
				InsertionSort.sortHighToLow(list);
			}
			
		}
		
	}
	
	public static void renderParticles(Camera camera){
		renderer.render(particles, camera);
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle){
		List<Particle> list = particles.get(particle.getParticleTexture());
		if (list == null) {
			list = new ArrayList<Particle>();
			particles.put(particle.getParticleTexture(), list);
		}
		list.add(particle);
	}

}
