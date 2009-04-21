package boid;

import vector.Vector;
import vector.Angle;
import engine.SimulationRules;
import java.util.concurrent.TimeUnit;


/**
 * BoidModel takes care of translating optimal headings for a boid 
 * to moves that are possible obeying the given rules. (i.e. physics modeling)
 * There is one boid model per simulation rules in use.
 */
final class BoidModel {
	
	/* Data members */
	
	SimulationRules rules;
	private static long currentTimeDelta; // nanoseconds since last simulation cycle
	private static double simulationSpeed; // a scaling factor for the simulation speed (no unit)
	private static final double SECONDS_IN_NANOSECOND = 1.0 / TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS);
	private static final double DISTANCE_FACTOR = 1.0; // How many units in the model equals one meter
	
	/* Public interface */
	
	BoidModel (SimulationRules rules_) {
		rules = rules_;
	}
	
	/** Tries to apply force to oldState and writes new state to newState */
	void attemptMove (PhysState oldState, PhysState newState, Vector force) {
		
		newState.reset();
		
		Vector accel = forceToAccel(force);
		limitAngle(oldState, accel);
		applyAccel(oldState, newState, accel);
		updatePosition(oldState, newState);
		updateBase(oldState, newState, accel);
	}
	
	/** Set nanoseconds since last simulation cycle */
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
	
	
	/** Limits angle to maximum turning angle as specified in the rules 
	 * @param state current state
	 * @param accel the angle of this vector will be limited
	 */
	private void limitAngle(PhysState state, Vector accel) {
		double origLength = accel.length();
		state.base.localize(accel);

		accel.limitXYLength(rules.maxTurn.value());

		state.base.globalize(accel);
	}
	
	/** Applies acceleration to speed in oldState (newState is updated) */
	private void applyAccel (PhysState oldState, PhysState newState, Vector accel) {
		
		// v = v0 + a * t
		newState.speed.add(oldState.speed);
		scaleToTime(accel); 
		newState.speed.add(accel);
		
		// Limit speed
		newState.speed.clamp(rules.minSpeed.value(), rules.maxSpeed.value());
	}
	
	/** The speed in newState is applied to the position in newState to move the boid */
	private void updatePosition (PhysState oldState, PhysState newState) {
		// New position
		// p = p + v * t
		Vector posDelta = new Vector (newState.speed);
		scaleToTime(posDelta);
		posDelta.scale(DISTANCE_FACTOR);
		
		newState.position.copyFrom(oldState.position);
		newState.position.add(posDelta);
	}
	
	/** Updates the vector base according to the forward speed and acceleration.
	 *  This Forward base vector is the only one that matters for the simulation,
	 *  so the up and left vectors just make the simulation look nice :)
	 */
	private void updateBase (PhysState oldState, PhysState newState, Vector accel) {
		Vector newUp = new Vector(oldState.base.getUp());
		Vector gravity = new Vector (0.0, 0.0, 1.0);
		
		// Centrifugal force is equal to acceleration
		Vector centrifugal = new Vector(accel);
		
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
