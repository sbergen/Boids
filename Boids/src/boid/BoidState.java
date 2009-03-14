package boid;

import java.util.ArrayList;

import vector.Angle;
import vector.Vector;

final class BoidState implements ThreadSafeBoidState {
	
	private BoidModel boid;
	private PhysState state;
	private PhysState nextState;
	private static BoidList list;
	
	static void setList(BoidList newList) {
		list = newList;
	}
	
	BoidState (BoidModel newBoid, PhysState newState) {
		boid = newBoid;
		state = newState;
		nextState = new PhysState();
	}

	public Vector getPosition() {
		return state.position;
	}

	public Angle getAngle() {
		return new Angle(state.speed);
	}

	public Vector getSpeed() {
		return state.speed;
	}
	
	public boolean hasLimits(engine.PhysLimits limits) {
		return (boid.limits == limits);
	}

	public void calculateNextMove() {
		Vector force = new Vector();
		Vector separation = new Vector();
		Vector cohesion = new Vector();
		Vector alignment = new Vector();
		
		ArrayList<ThreadSafeBoidState> neighbours = 
			list.getBoidsWithinRange(state.position, boid.limits.perceptionRange);
		
		for (ThreadSafeBoidState neighbour : neighbours) {
			
			/* Separation */
			
			Vector diff = new Vector(neighbour.getPosition());
			diff.subtract(state.position);
			diff.scale(1.0 / diff.length());
			separation.add(diff);
			
			/* Cohesion and Alignment */
			
			cohesion.add(neighbour.getPosition());
			alignment.add(neighbour.getSpeed());
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
	
}
