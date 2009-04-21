package boid;

import java.util.ArrayList;

import engine.SimulationRules;

import vector.Vector;
import vector.VectorBase;

/**
 * BoidState is responsible for the flock behavior logic.
 * It also owns the current and next state of a boid ("double buffering").
 * Each boid in the simulation has one state.
 * Implements its thread safe interface.
 */
final class BoidState implements ThreadSafeBoidState {
	
	// Behavior near walls
	static final double WALL_LIMITS = engine.Engine.BOX_SIZE / 2; // Coordinates of walls
	static final double WALL_DISTANCE = engine.Engine.BOX_SIZE / 10; // How near to the wall we start to turn
	
	private SimulationRules rules;
	private BoidModel boid;
	private PhysState state;
	private PhysState nextState;
	private static BoidList list;
	
	/** All boids are in the same list so this is static */
	static void setList(BoidList newList) {
		list = newList;
	}
	
	BoidState (BoidModel newBoid, PhysState newState,  SimulationRules newRules) {
		rules = newRules;
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
	
	/** Check to see if this boid follows a certain set of rules */
	public boolean hasRules(SimulationRules rulesToCheck) {
		return (boid.rules == rulesToCheck);
	}

	/** Calculates the next move, but does not apply it yet */
	public void calculateNextMove() {
		
		// The overall force
		Vector force = new Vector();
		
		// Each flocking rule has it's own vector
		Vector separation = new Vector();
		Vector cohesion = new Vector();
		Vector alignment = new Vector();
		
		ArrayList<ThreadSafeBoidState> neighbours = 
			list.getBoidsWithinRange(state.position, rules.perceptionRange.value());
		
		// If the boid has no neighbors (except itself, only apply wall force)
		if (neighbours.size() <= 1) {
			addWallForce(force);
			boid.attemptMove(state, nextState, force);
			return;
		}
		
		for (ThreadSafeBoidState neighbour : neighbours) {
			
			if (neighbour == this) {
				continue;
			}
			
			// Separation
			// (difference vector divided by its length squared) 
			
			Vector diff = new Vector(state.position);
			diff.subtract(neighbour.getPosition());
			diff.scale(1.0 / Math.pow(diff.length(), 2));
			separation.add(diff);
			
			// Cohesion and Alignment 
			// simple vector sums (scaled later)
			
			cohesion.add(neighbour.getPosition());
			alignment.add(neighbour.getSpeed());
		}
		
		// Cohesion finalization (scaling)
		
		cohesion.scale(1.0 / (neighbours.size() - 1));
		cohesion.subtract(state.position);
		
		// Alignment finalization (scaling)
		
		alignment.scale(1.0 / (neighbours.size() - 1));
		alignment.subtract(state.speed);
		
		// Scale components 
		separation.scale(rules.separationFactor.value());
		cohesion.scale(rules.cohesionFactor.value());
		alignment.scale (rules.alignmentFactor.value());
		
		// Final force vector (sum components)
		
		force.add(separation);
		force.add(cohesion);
		force.add(alignment);
		
		addWallForce (force);
		
		boid.attemptMove(state, nextState, force);
	}
	
	/** Simply swap current and next state ("double buffering") */
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
	
	/** Calculates the force to apply to avoid walls */
	private double calculateWallForceComponent (double coord) {
		
		boolean negative = (coord < 0.0);
		double distance = Math.abs(coord);
		double force;
		
		final double maxForce = 30.0; // large force
		final double baseForce = 10.0; // good force to scale for avoiding walls 
		
		if (distance < (WALL_LIMITS - WALL_DISTANCE)) { // well inside cube
			force = 0.0;
		} else if (distance >= WALL_LIMITS ) { // outside walls
			force = maxForce;
		} else { // close to walls
			// Apply force relative to 1/x,
			// where x is 1 ... 0 from "at WALL_DISTANCE from wall" to "at wall"
			force = baseForce / (Math.abs(distance - WALL_LIMITS) / WALL_DISTANCE);
		}
		
		return (negative ? 1.0 : -1.0) * force;
	}
	
}

