package engine;

public final class PhysLimits {
	
	public PhysLimits() {
		mass = 1.0;
		maxForce = 1.0;
		minSpeed = 0.1;
		maxSpeed = 5.0;
		perceptionRange = 4.0;
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
