package engine;

public final class PhysLimits {
	
	public PhysLimits() {
		mass = 2.0;
		minForce = 0.5;
		maxForce = 100.0;
		maxTurn = 0.0;
		minSpeed = 10;
		maxSpeed = 20;
		perceptionRange = 150.0;
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
