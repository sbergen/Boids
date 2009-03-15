package boid;

import vector.Vector;
import vector.Angle;
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
		if (accel.length() < limits.maxForce / 100) {
			//accel.randomize(limits.maxForce / (limits.mass * 10));
		}
		
		// New speed
		newState.speed.add(oldState.speed).add(accel);
		
		// Limit turn
		Vector newSpeed = new Vector(newState.speed);
		newSpeed.subtract(oldState.speed);
		Angle turn = new Angle (newSpeed);
		turn.turn(new Angle(oldState.speed));
		
		// Limit speed
		newState.speed.limitLength(limits.maxSpeed);
		if (newState.speed.length() < limits.minSpeed) {
			newState.speed.scale(limits.minSpeed / newState.speed.length());
		}
		if (newState.speed.length() == 0.0) {
			newState.speed.randomize(0.5);
		}
		
		// New position
		newState.position.add(oldState.position).add(accel);
		
		// TODO: top angle
	}
}
