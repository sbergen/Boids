package engine;

public final class PhysLimits {
	
	public PhysLimits() {
		mass = 2.0;
		maxForce = 10.0;
		minSpeed = 0.2;
		maxSpeed = 1.0;
		perceptionRange = 150.0;
	}
	
	// Maximum and minimum speed (m/s)
	public double minSpeed;
	public double maxSpeed;
	
	// Maximum force (N)
	public double maxForce;
	
	// Mass (kg)
	public double mass;
	
	// PerceptionRange (m)
	public double perceptionRange;
}
