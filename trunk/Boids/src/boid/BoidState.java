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
		
		if (neighbours.size() <= 1) {
			System.out.println("no neighbours");
			boid.attemptMove(state, nextState, force);
			return;
		}
		
		for (ThreadSafeBoidState neighbour : neighbours) {
			
			if (neighbour == this) {
				continue;
			}
			
			/* Separation */
			
			Vector diff = new Vector(state.position);
			diff.subtract(neighbour.getPosition());
			diff.scale(1.0 / diff.length());
			separation.add(diff);
			
			/* Cohesion and Alignment */
			
			cohesion.add(neighbour.getPosition());
			alignment.add(neighbour.getSpeed());
		}
		
		/* Cohesion finalization */
		
		cohesion.scale(1.0 / (neighbours.size() - 1));
		cohesion.subtract(state.position);
		
		/* Alignment finalization */
		
		alignment.scale(1.0 / (neighbours.size() - 1));
		alignment.subtract(state.speed);
		
		/* Scale */
		
		separation.scale(7.0);
		cohesion.scale(3.5);
		alignment.scale (1.0);
		
		/* Force vector */
		
		force.add(separation);
		force.add(cohesion);
		force.add(alignment);
		
		force.scale(0.4);
		
		boid.attemptMove(state, nextState, force);
	}
	
	void commitNextMove() {
		PhysState tmp = state;
		state = nextState;
		nextState = tmp;
	}
	
}