package boid;

import java.util.ArrayList;

import vector.Vector;
import boid.BoidList.BoidReader;

public interface ThreadSafeBoidList {
	public ArrayList<ThreadSafeBoidState> getBoidsWithinRange(Vector position, double range);
	public void readList (BoidReader operator);
	public void updateBoidStates();
}
