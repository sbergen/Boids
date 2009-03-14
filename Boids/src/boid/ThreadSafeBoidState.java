package boid;

import vector.Angle;
import vector.Vector;

/**
 * Thread safe interface for BoidState
 * @author sbergen
 *
 */

public interface ThreadSafeBoidState {
	public Vector getPosition();
	public Angle getAngle();
	public Vector getSpeed();
	public boolean hasLimits(engine.PhysLimits limits);
	void calculateNextMove();
}
