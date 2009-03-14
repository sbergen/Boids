package engine;

import java.util.ArrayList;
import vector.Vector;

public final class BoidList {
	
	/* Data members */
	
	ArrayList<BoidState> list;
	
	/* Package methods (default visibility) */
	
	ArrayList<BoidState> getBoidsWithinRange(Vector position, double range) {
		ArrayList<BoidState> neighbours = new ArrayList<BoidState>();
		
		for (BoidState boid : list) {
			if (Vector.distance(position, boid.getPosition()) <= range) {
				neighbours.add(boid);
			}
		}
		
		return neighbours;
	}
}
