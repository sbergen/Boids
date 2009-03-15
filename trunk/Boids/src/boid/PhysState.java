package boid;

import vector.Angle;
import vector.Vector;
import vector.VectorBase;

final class PhysState {
	
	private static final double RAND_MAX_POS = 100.0;
	private static final double RAND_AVG = 250.0;
	private static final double RAND_MAX_SPEED = 1.0;
	
	/// Position in world
	Vector position;
	
	/// Speed
	Vector speed;
	
	/// Vector base
	VectorBase base;
	
	PhysState() {
		position = new Vector();
		speed = new Vector();
		base = new VectorBase();
	}
	
	void reset() {
		position.reset();
		speed.reset();
	}
	
	static PhysState random() {
		PhysState state = new PhysState();
		
		state.position = new Vector(
				RAND_MAX_POS * Math.random() + RAND_AVG,
				RAND_MAX_POS * Math.random() + RAND_AVG,
				RAND_MAX_POS * Math.random() + RAND_AVG);
		
		state.speed = new Vector(
				((Math.random() > 0.5) ? -1.0 : 1.0) * RAND_MAX_SPEED * Math.random(),
				((Math.random() > 0.5) ? -1.0 : 1.0) * RAND_MAX_SPEED * Math.random(),
				((Math.random() > 0.5) ? -1.0 : 1.0) * RAND_MAX_SPEED * Math.random());
		
		state.base = new VectorBase(state.speed, new Vector(0, 0, 1));
		
		return state;
	}
	
}
