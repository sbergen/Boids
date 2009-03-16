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
		
		newState.reset();
		Vector accel = forceToAccel(force);
		limitAngle(oldState, accel);
		newState.speed.add(oldState.speed);
		newState.speed.add(accel);
		
		// Limit speed
		newState.speed.clamp(limits.minSpeed, limits.maxSpeed);
		
		// New position
		newState.position.add(oldState.position);
		newState.position.add(newState.speed);
		newState.base.set(newState.speed, new Vector (0, 0, 1));
		// TODO: top angle
	}
	
	/* private helpers */
	
	private Vector forceToAccel (Vector force) {
		force.clamp(limits.minForce, limits.maxForce);
		force.scale(1.0 / limits.mass);
		return force;
	}
	
	private void limitAngle(PhysState state, Vector accel) {
		Vector local = state.base.localize(accel);
		Angle turnAngle = new Angle(local);
		
		double magnitude = accel.length();
		if (turnAngle.limitZenith(limits.maxTurn)) {
			// Zenith was limited. Project vector onto the cone forming the zenith limit
			magnitude = Vector.dotProduct(local, 
					// Unit vector with same azimuth, and maximum zenith
					new Vector(1.0, new Angle(turnAngle.azimuth(), limits.maxTurn)));
			magnitude = Math.abs(magnitude);
		}
		
		Vector localAccel = new Vector(magnitude, turnAngle);
		//localAccel.print();
		Angle locA = new Angle(localAccel);
		if (locA.zenith() > limits.maxTurn + 0.001) {
			System.out.println("Angle zenith: " + locA.zenith());
			System.out.println("Vector length: " + localAccel.length());
			System.out.println();
		}
		
		accel.copyFrom(state.base.globalize(localAccel));
		
	}
}
