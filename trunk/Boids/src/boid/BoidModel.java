package boid;

import engine.PhysLimits;
import vector.Vector;

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
		
		// New speed
		newState.speed.add(oldState.speed).add(accel);
		newState.speed.limitLength(limits.maxSpeed);
		
		// New position
		newState.position.add(oldState.position).add(accel);
		
		// TODO: top angle
	}
}
