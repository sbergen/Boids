package boid;

import java.util.ArrayList;

import vector.Vector;

final class BoidList implements ThreadSafeBoidList {
	
	/* Data members */
	
	private ArrayList<BoidState> list;
	
	
	/* Package methods (default visibility) */
	
	public ArrayList<ThreadSafeBoidState> getBoidsWithinRange(Vector position, double range) {
		ArrayList<ThreadSafeBoidState> neighbours = new ArrayList<ThreadSafeBoidState>();
		
		for (ThreadSafeBoidState boid : list) {
			if (Vector.distance(position, boid.getPosition()) <= range) {
				neighbours.add(boid);
			}
		}
		
		return neighbours;
	}
	
	/* Public methods */
	
	public interface BoidReader {
		public void readBoid(ThreadSafeBoidState boid);
	}
	
	public void readList (BoidReader operator) {
		synchronized (list) {
			for (ThreadSafeBoidState boid : list) {
				operator.readBoid(boid);
			}
		}
	}
	
	public void updateBoidStates() {
		for (ThreadSafeBoidState boid : list) {
			boid.calculateNextMove();
		}
		
		synchronized (list) {
			for (BoidState boid : list) {
				boid.commitNextMove();
			}
		}
	}
}
