package terrainsSphere;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class TerrainObject {
	private int objectType;
	private String objectName;
	private float objectAmount;
	private boolean affectTerrainColour;
	private Vector3f colour;
	private Vector2f entityAmountRange;
	
	
	
	public TerrainObject() {
		
	}
	
	public TerrainObject(int objectType, float objectAmount) {
		this.objectType = objectType;
		this.objectAmount = objectAmount;
		this.affectTerrainColour = false;
	}
	
	public TerrainObject(int objectType, float objectAmount, Vector3f colour) {
		this.objectType = objectType;
		this.objectAmount = objectAmount;
		this.affectTerrainColour = true;
		this.colour = colour;
	}

	public float getObjectAmount() {
		return objectAmount;
	}

	public void setObjectAmount(float objectAmount) {
		this.objectAmount = objectAmount;
	}

	public int getObjectType() {
		return objectType;
	}

	public String getObjectName() {
		return objectName;
	}

	public boolean isAffectTerrainColour() {
		return affectTerrainColour;
	}

	public Vector3f getColour() {
		return colour;
	}

	public Vector2f getEntityAmountRange() {
		return entityAmountRange;
	}
	
	
	
	
}
