package engine;

import java.util.ArrayList;
import vector.Vector;

public final class BoidList {
	
	/* Data members */
	
	private ArrayList<BoidState> list;
	
	/* Package methods (default visibility) */
	
	ArrayList<ConstBoidState> getBoidsWithinRange(Vector position, double range) {
		ArrayList<ConstBoidState> neighbours = new ArrayList<ConstBoidState>();
		
		for (ConstBoidState boid : list) {
			if (Vector.distance(position, boid.getPosition()) <= range) {
				neighbours.add(boid);
			}
		}
		
		return neighbours;
	}
	
	/* Public methods */
	
	public interface BoidReader {
		public void readBoid(ConstBoidState boid);
	}
	
	public void operateList (BoidReader operator) {
		synchronized (list) {
			for (ConstBoidState boid : list) {
				operator.readBoid(boid);
			}
		}
	}
	
	public void updateBoidStates() {
		for (ConstBoidState boid : list) {
			boid.calculateNextMove();
		}
		
		synchronized (list) {
			for (BoidState boid : list) {
				boid.commitNextMove();
			}
		}
	}
}
