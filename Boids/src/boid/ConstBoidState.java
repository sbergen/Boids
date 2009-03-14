package boid;

import java.util.ArrayList;

import vector.Angle;
import vector.Vector;

public class ConstBoidState {

	private BoidModel boid;
	protected PhysState state;
	protected PhysState nextState;
	protected static BoidList list;

	public ConstBoidState() {
		super();
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

	protected void calculateNextMove() {
		Vector force = new Vector();
		Vector separation = new Vector();
		Vector cohesion = new Vector();
		Vector alignment = new Vector();
		
		ArrayList<ConstBoidState> neighbours = 
			list.getBoidsWithinRange(state.position, boid.limits.perceptionRange);
		
		for (ConstBoidState neighbour : neighbours) {
			
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

}