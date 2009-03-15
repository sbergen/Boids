package engine;

public final class PhysLimits {
	
	public PhysLimits() {
		mass = 5;
		minForce = 0.0;
		maxForce = 20.0;
		maxTurn = 1.5;
		minSpeed = 3;
		maxSpeed = 10;
		perceptionRange = 100.0;
	}
	
	// Maximum and minimum speed (m/s)
	public double minSpeed;
	public double maxSpeed;
	
	// Maximum and mimimum force (N)
	public double minForce;
	public double maxForce;
	
	// Maximum turn (radians)
	public double maxTurn;
	
	// Mass (kg)
	public double mass;
	
	// PerceptionRange (m)
	public double perceptionRange;
}
