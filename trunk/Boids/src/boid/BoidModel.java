package boid;

import vector.Vector;
import vector.Angle;
import engine.PhysLimits;

final class BoidModel {
	
	/* Data members */
	
	PhysLimits limits;
	private static long currentTimeDelta;
	
	/* Public interface */
	
	BoidModel (PhysLimits limits_) {
		limits = limits_;
	}
	
	void attemptMove (PhysState oldState, PhysState newState, Vector force) {
		
		newState.reset();
		
		Vector accel = forceToAccel(force);
		limitAngle(oldState, accel);
		applyAccel(oldState, newState, accel);
		updatePosition(oldState, newState);
		
		// TODO: top angle
	}
	
	static void setTimeDelta (long delta) {
		currentTimeDelta = delta;
	}
	
	/* private helpers */
	
	private Vector forceToAccel (Vector force) {
		force.clamp(limits.minForce, limits.maxForce);
		force.scale(1.0 / limits.mass);
		return force;
	}
	
	private void limitAngle(PhysState state, Vector accel) {
		state.base.localize(accel);
		Angle turnAngle = new Angle(accel);
		
		double magnitude = accel.length();
		if (turnAngle.limitZenith(limits.maxTurn)) {
			// Zenith was limited. Project vector onto the cone forming the zenith limit
			magnitude = Vector.dotProduct(accel, 
					// Unit vector with same azimuth, and maximum zenith
					new Vector(1.0, new Angle(turnAngle.azimuth(), limits.maxTurn)));
			magnitude = Math.abs(magnitude);
		}
		
		accel.fromSphericCoords(magnitude, turnAngle);
		state.base.globalize(accel);
	}
	
	private void applyAccel (PhysState oldState, PhysState newState, Vector accel) {
		
		// v = v0 + a * t
		newState.speed.add(oldState.speed);
		scaleToTime(accel); 
		newState.speed.add(accel);
		
		// Limit speed
		newState.speed.clamp(limits.minSpeed, limits.maxSpeed);
	}
	
	private void updatePosition (PhysState oldState, PhysState newState) {
		// New position
		Vector posDelta = new Vector (newState.speed);
		scaleToTime(posDelta);
		
		newState.position.add(oldState.position);
		newState.position.add(posDelta);
		newState.base.set(newState.speed, new Vector (0, 0, 1));
	}
	
	/* This is the only method aware of time */
	private void scaleToTime (Vector v) {
		v.scale((double) currentTimeDelta / 100000000);
	}
}
