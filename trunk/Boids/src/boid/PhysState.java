package boid;

import vector.Angle;
import vector.Vector;

final class PhysState {
	
	private static final double RAND_MAX_POS = 100.0;
	private static final double RAND_MAX_SPEED = 1.0;
	
	/// Position in world
	Vector position;
	
	/// Speed as vector, also forward heading
	Vector speed;
	
	/// Angle that points "up" from the POV of the object
	Angle topAngle;
	
	PhysState() {
		position = new Vector();
		speed = new Vector();
		// TODO topAngle not in use yet...
	}
	
	void reset() {
		position.reset();
		speed.reset();
		//topAngle.reset();
	}
	
	static PhysState random() {
		PhysState state = new PhysState();
		
		state.position = new Vector(
				RAND_MAX_POS * Math.random(),
				RAND_MAX_POS * Math.random(),
				RAND_MAX_POS * Math.random());
		
		state.speed = new Vector(
				RAND_MAX_SPEED * Math.random(),
				RAND_MAX_SPEED * Math.random(),
				RAND_MAX_SPEED * Math.random());
		
		return state;
	}
	
}
