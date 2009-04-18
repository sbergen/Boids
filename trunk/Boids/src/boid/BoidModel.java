package boid;

import vector.Vector;
import vector.Angle;
import engine.SimulationRules;
import java.util.concurrent.TimeUnit;

final class BoidModel {
	
	/* Data members */
	
	SimulationRules rules;
	private static long currentTimeDelta;
	private static double simulationSpeed;
	private static final double SECONDS_IN_NANOSECOND = 1.0 / TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS);
	private static final double DISTANCE_FACTOR = 1.0; // How many units in the model equals one meter
	
	/* Public interface */
	
	BoidModel (SimulationRules rules_) {
		rules = rules_;
	}
	
	void attemptMove (PhysState oldState, PhysState newState, Vector force) {
		
		newState.reset();
		
		Vector accel = forceToAccel(force);
		limitAngle(oldState, accel);
		applyAccel(oldState, newState, accel);
		updatePosition(oldState, newState);
		updateBase(oldState, newState, accel);
	}
	
	static void setTimeDelta (long delta) {
		currentTimeDelta = delta;
	}
	
	static void setSimulationSpeed (double speed) {
		simulationSpeed = speed;
	}
	
	/* private helpers */
	
	private Vector forceToAccel (Vector force) {
		force.clamp(rules.minForce.value(), rules.maxForce.value());
		force.scale(1.0 / rules.mass.value());
		return force;
	}
	
	private void limitAngle(PhysState state, Vector accel) {
		state.base.localize(accel);
		Angle turnAngle = new Angle(accel);

		double magnitude = accel.length();
		if (turnAngle.limitZenith(scaleToTime(rules.maxTurn.value()))) {
			// Zenith was limited. Project vector onto the cone forming the
			// zenith limit,
			// but adjust magnitude to be average of projected and and original.
			magnitude = (accel.dotProduct(new Vector(1.0, new Angle(turnAngle
					.azimuth(), rules.maxTurn.value()))) + 2.0 * magnitude) / 3.0;
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
		newState.speed.clamp(rules.minSpeed.value(), rules.maxSpeed.value());
	}
	
	private void updatePosition (PhysState oldState, PhysState newState) {
		// New position
		Vector posDelta = new Vector (newState.speed);
		scaleToTime(posDelta);
		posDelta.scale(DISTANCE_FACTOR);
		
		newState.position.add(oldState.position);
		newState.position.add(posDelta);
	}
	
	private void updateBase (PhysState oldState, PhysState newState, Vector accel) {
		Vector newUp = new Vector(oldState.base.getUp());
		Vector gravity = new Vector (0.0, 0.0, 1.0);
		
		// Centrifugal force is equal to acceleration
		Vector centrifugal = new Vector(newState.speed);
		centrifugal.subtract(oldState.speed);
		
		/// Scale forces to make it look nice :)
		newUp.scale(3.0);
		centrifugal.scale(5.0);
		gravity.scale(3.0);
		
		// Make final vector
		newUp.add(gravity);
		newUp.add(centrifugal);
		
		newState.base.set(newState.speed, newUp);
	}
	
	private void scaleToTime (Vector v) {
		v.scale(SECONDS_IN_NANOSECOND * currentTimeDelta * simulationSpeed);
	}
	
	private double scaleToTime (double d) {
		return d * (SECONDS_IN_NANOSECOND * currentTimeDelta * simulationSpeed);
	}
}
