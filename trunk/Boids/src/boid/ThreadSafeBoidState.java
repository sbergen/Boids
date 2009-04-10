package boid;

import vector.Vector;
import vector.VectorBase;

/**
 * Thread safe interface for BoidState
 * @author sbergen
 *
 */

public interface ThreadSafeBoidState {
	public Vector getPosition();
	public VectorBase getBase();
	public Vector getSpeed();
	public boolean hasRules(engine.SimulationRules rules);
	void calculateNextMove();
}
