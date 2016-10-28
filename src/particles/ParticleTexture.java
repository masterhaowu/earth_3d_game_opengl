package particles;

import org.lwjgl.util.vector.Vector3f;

public class ParticleTexture {
	
	private int textureID;
	private int numberOfRows;
	private boolean additive;
	private boolean singleTexture;
	private boolean useColourFilter;
	private Vector3f colour;
	
	
	public ParticleTexture(int textureID, int numberOfRows, boolean additive) {
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
		this.additive = additive;
		this.singleTexture = false;
		if (numberOfRows == 1) {
			this.singleTexture = true;
		}
		this.useColourFilter = false;
		
		
	}
	
	public void useColour(Vector3f colour){
		this.useColourFilter = true;
		this.colour = colour;
	}

	public boolean useAdditiveBlending(){
		return additive;
	}

	public int getTextureID() {
		return textureID;
	}


	public int getNumberOfRows() {
		return numberOfRows;
	}

	public boolean isUseColourFilter() {
		return useColourFilter;
	}

	public Vector3f getColour() {
		return colour;
	}
	
	
	
	

}
