package engine;

public final class SimulationRules {
	
	public static final double defaultMinSpeed = 2.0;
	public static final double defaultMaxSpeed = 20.0;
	public static final double defaultMinForce = 0.2;
	public static final double defaultMaxForce = 35.0;
	public static final double defaultMaxTurn = 13.0;
	public static final double defaultMass = 5.0;
	public static final double defaultSeparationFactor = 7.0;
	public static final double defaultCohesionFactor = 3.5;
	public static final double defaultAlignmentFactor = 0.5;
	public static final double defaultPerceptionRange = 100.0;
	
	public synchronized double getMinSpeed() {
		return minSpeed;
	}
	
	public synchronized void setMinSpeed(double minSpeed) {
		this.minSpeed = minSpeed;
	}
	
	public synchronized double getMaxSpeed() {
		return maxSpeed;
	}
	
	public synchronized void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public synchronized double getMinForce() {
		return minForce;
	}
	
	public synchronized void setMinForce(double minForce) {
		this.minForce = minForce;
	}
	
	public synchronized double getMaxForce() {
		return maxForce;
	}
	
	public synchronized void setMaxForce(double maxForce) {
		this.maxForce = maxForce;
	}
	
	public synchronized double getMaxTurn() {
		return maxTurn;
	}
	
	public synchronized void setMaxTurn(double maxTurn) {
		this.maxTurn = maxTurn;
	}
	
	public synchronized double getMass() {
		return mass;
	}
	
	public synchronized void setMass(double mass) {
		this.mass = mass;
	}
	
	public synchronized double getSeparationFactor() {
		return separationFactor;
	}
	
	public synchronized void setSeparationFactor(double separationFactor) {
		this.separationFactor = separationFactor;
	}
	
	public synchronized double getCohesionFactor() {
		return cohesionFactor;
	}
	
	public synchronized void setCohesionFactor(double cohesionFactor) {
		this.cohesionFactor = cohesionFactor;
	}
	
	public synchronized double getAlignmentFactor() {
		return alignmentFactor;
	}
	
	public synchronized void setAlignmentFactor(double alignmentFactor) {
		this.alignmentFactor = alignmentFactor;
	}
	
	public synchronized double getPerceptionRange() {
		return perceptionRange;
	}
	
	public synchronized void setPerceptionRange(double perceptionRange) {
		this.perceptionRange = perceptionRange;
	}
	
	// Maximum and minimum speed (m/s)
	private double minSpeed = defaultMinSpeed;
	private double maxSpeed = defaultMaxSpeed;
	
	// Maximum and mimimum force (N)
	private double minForce = defaultMinForce;	
	private double maxForce = defaultMaxForce;
	
	// Maximum turn (radians/s)
	private double maxTurn = defaultMaxTurn;
	
	// Mass (kg)
	private double mass = defaultMass;
	
	// Simulation parameters
	private double separationFactor = defaultSeparationFactor;
	private double cohesionFactor = defaultCohesionFactor;
	private double alignmentFactor = defaultAlignmentFactor;
	
	private double perceptionRange = defaultPerceptionRange;
}
