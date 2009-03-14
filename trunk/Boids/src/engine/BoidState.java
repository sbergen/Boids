package engine;

import vector.Vector;
import vector.Angle;

import java.util.ArrayList;

public final class BoidState {
	
	/* Data memebers */
	
	private Boid      boid;
	private PhysState state;
	private PhysState nextState;
	private static BoidList list;
	
	/* Package methods (default visibility) */
	
	static void setList(BoidList newList) {
		list = newList;
	}
	
	BoidState (Boid boid, PhysState state) {
		
	}
	
	/* Public interface */

	public Vector getPosition() {
		return state.position;
	}
	
	public Angle getAngle() {
		return new Angle(state.speed);
	}
	
	public Vector getSpeed() {
		return state.speed;
	}
	
	/* Package methods (default visibility) */
	
	void calculateNextMove() {
		Vector force = new Vector();
		Vector separation = new Vector();
		Vector cohesion = new Vector();
		Vector alignment = new Vector();
		
		ArrayList<BoidState> neighbours = 
			list.getBoidsWithinRange(state.position, boid.limits.perceptionRange);
		
		for (BoidState neighbour : neighbours) {
			
			/* Separation */
			
			Vector diff = new Vector(neighbour.state.position);
			diff.subtract(state.position);
			diff.scale(1.0 / diff.length());
			separation.add(diff);
			
			/* Cohesion and Alignment */
			
			cohesion.add(neighbour.state.position);
			alignment.add(neighbour.state.speed);
		}
		
		/* Cohesion finalization */
		
		cohesion.scale(1.0 / neighbours.size());
		cohesion.subtract(state.position);
		
		/* Alignment finalization */
		
		alignment.scale(1.0 / neighbours.size());
		alignment.subtract(state.speed);
		
		/* Force vector */
		
		force.add(separation.scale(0.2));
		force.add(cohesion.scale(0.2));
		force.add(alignment.scale(0.2));
		
		boid.attemptMove(state, nextState, force);
	}
	
	void commitNextMove() {
		PhysState tmp = state;
		state = nextState;
		nextState = tmp;
	}
	
	/* Private helpers */
}
