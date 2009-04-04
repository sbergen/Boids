package boid;

import java.util.ArrayList;

import vector.Angle;
import vector.Vector;
import vector.VectorBase;

final class BoidState implements ThreadSafeBoidState {
	
	static final double WALL_LIMITS = 250;
	static final double WALL_DISTANCE = 40;
	
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

	public VectorBase getBase() {
		return state.base;
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
			addWallForce(force);
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
		alignment.scale (0.5);
		
		/* Force vector */
		
		force.add(separation);
		force.add(cohesion);
		force.add(alignment);
		
		force.scale(1.0);
		addWallForce (force);
		
		boid.attemptMove(state, nextState, force);
	}
	
	void commitNextMove() {
		PhysState tmp = state;
		state = nextState;
		nextState = tmp;
	}
	
	/* private helpers */
	
	private void addWallForce (Vector force) {
		
		double xForce = calculateWallForceComponent(state.position.getX());
		double yForce = calculateWallForceComponent(state.position.getY());
		double zForce = calculateWallForceComponent(state.position.getZ());
		
		force.add(new Vector(xForce, yForce, zForce));
	}
	
	private double calculateWallForceComponent (double coord) {
		
		boolean negative = (coord < 0.0);
		double distance = Math.abs(coord);
		double force;
		
		if (distance < (WALL_LIMITS - WALL_DISTANCE)) {
			force = 0.0;
		} else if (distance >= WALL_LIMITS ) {
			force = 1000.0;
		} else {
			force = 50.0 / (Math.abs(distance - WALL_LIMITS) / WALL_DISTANCE);
		}
		
		return (negative ? 1.0 : -1.0) * force;
	}
	
}
