package boid;

import java.util.ArrayList;

import vector.Vector;
import boid.BoidList.BoidReader;

/** Thread safe interface for BoidList */
public interface ThreadSafeBoidList {
	public ArrayList<ThreadSafeBoidState> getBoidsWithinRange(Vector position, double range);
	public void readBoids (BoidReader operator);
	public void updateBoidStates(long timeDelta, double simulationSpeed);
}
