package boid;

import java.util.LinkedList;

import vector.Vector;
import boid.BoidList.BoidReader;

/** Thread safe interface for BoidList */
interface ThreadSafeBoidList {
	public LinkedList<ThreadSafeBoidState> getBoidsWithinRange(Vector position, double range);
	public void readBoids (BoidReader operator);
	public void updateBoidStates(long timeDelta, double simulationSpeed);
}
