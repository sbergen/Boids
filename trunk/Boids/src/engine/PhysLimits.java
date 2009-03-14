package engine;

public final class PhysLimits {
	
	public PhysLimits() {
		mass = 2.0;
		maxForce = 3.0;
		minSpeed = 1.0;
		maxSpeed = 50.0;
		perceptionRange = 10.0;
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
