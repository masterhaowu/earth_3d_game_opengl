package entityObjects;

public class PredationUnit {
	
	
	//private ObjectData predator;
	//private ObjectData prey;
	
	
	private float baseCaptureRate = 0.1f;
	private float baseKillRate = 0.3f;
	
	
	public PredationUnit(ObjectData predator, ObjectData prey){
		//this.predator = predator;
		//this.prey = prey;
		connectObjectDatas(predator, prey);
	}
	
	public PredationUnit(ObjectData predator, ObjectData prey, float baseCaptureRate, float baseKillRate){
		//this.predator = predator;
		//this.prey = prey;
		this.baseCaptureRate = baseCaptureRate;
		this.baseKillRate = baseKillRate;
		connectObjectDatas(predator, prey);
	}
	
	public void connectObjectDatas(ObjectData predator, ObjectData prey){
		//this.predator.addPrey(prey.getObjectType());
		//this.prey.addPredator(predator.getObjectType());
		//this.predator.getPredationUnits().add(this);
		predator.addPrey(prey.getObjectType(), this);
		prey.addPredator(predator.getObjectType());
	}
	/*
	public ObjectData getPredator() {
		return predator;
	}

	public void setPredator(ObjectData predator) {
		this.predator = predator;
	}

	public ObjectData getPrey() {
		return prey;
	}

	public void setPrey(ObjectData prey) {
		this.prey = prey;
	}
	*/
	public float getBaseCaptureRate() {
		return baseCaptureRate;
	}

	public void setBaseCaptureRate(float baseCaptureRate) {
		this.baseCaptureRate = baseCaptureRate;
	}

	public float getBaseKillRate() {
		return baseKillRate;
	}

	public void setBaseKillRate(float baseKillRate) {
		this.baseKillRate = baseKillRate;
	}
	
	
	

}
