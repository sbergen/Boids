package boid;

import vector.Vector;
import engine.PhysLimits;

final class BoidModel {
	
	/* Data members (default visibility for package) */
	
	PhysLimits limits;
	
	/* Public interface */
	
	BoidModel (PhysLimits limits_) {
		limits = limits_;
	}
	
	void attemptMove (PhysState oldState, PhysState newState, Vector force) {
		
		Vector accel = new Vector(force);
		newState.reset();
		
		// Acceleration
		accel.limitLength(limits.maxForce);
		accel.scale(1.0 / limits.mass);
		if (accel.length() < limits.maxForce / 10) {
			accel.randomize(limits.maxForce / limits.mass);
		}
		
		
		// New speed
		newState.speed.add(oldState.speed).add(accel);
		newState.speed.limitLength(limits.maxSpeed);
		if (newState.speed.length() < limits.minSpeed) {
			newState.speed.scale(limits.minSpeed / newState.speed.length());
		}
		
		// New position
		newState.position.add(oldState.position).add(accel);
		
		// TODO: top angle
	}
}
