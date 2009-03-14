package engine;

import vector.Angle;
import vector.Vector;

final class PhysState {
	
	/// Position in world
	Vector position;
	
	/// Speed as vector, also forward heading
	Vector speed;
	
	/// Angle that points "up" from the POV of the object
	Angle topAngle;
	
	void reset() {
		position.reset();
		speed.reset();
		topAngle.reset();
	}
	
}
