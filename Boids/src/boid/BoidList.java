package boid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

import vector.Vector;
import engine.SimulationRules;

public final class BoidList implements ThreadSafeBoidList {
	
	/* Data members */
	
	private ArrayList<BoidState> list;
	private HashMap<SimulationRules, BoidModel> models;
	private HashMap<SimulationRules, Integer> boidCounts;
	private HashMap<SimulationRules, Integer> requestedBoidCounts; 
	
	/* Package methods (default visibility) */
	
	public ArrayList<ThreadSafeBoidState> getBoidsWithinRange(Vector position, double range) {
		ArrayList<ThreadSafeBoidState> neighbours = new ArrayList<ThreadSafeBoidState>();
		
		for (ThreadSafeBoidState boid : list) {
			if (position.distance(boid.getPosition()) <= range) {
				neighbours.add(boid);
			}
		}
		
		return neighbours;
	}
	
	/* Public methods */
	
	public BoidList() {
		list = new ArrayList<BoidState>();
		models = new HashMap<SimulationRules, BoidModel>();
		boidCounts = new HashMap<SimulationRules, Integer>();
		requestedBoidCounts = new HashMap<SimulationRules, Integer>();		
		BoidState.setList(this);
	}
	
	/** 
	 * The BoidReader interface is a thread safe
	 * interface to be used with the visitor pattern
	 * to read Boids in the list.
	 */
	
	public interface BoidReader {
		public void readBoid(ThreadSafeBoidState boid);
	}
	
	public void readBoids (BoidReader operator) {
		synchronized (list) {
			for (ThreadSafeBoidState boid : list) {
				operator.readBoid(boid);
			}
		}
	}
	
	/**
	 * Updates Boid states in two stages.
	 * 1. new states are calculates (this does not need to be synchronized)
	 * 2. new states are committed (this is synchronized to the list)
	 */
	public void updateBoidStates(long timeDelta, double simulationSpeed) {
		
		BoidModel.setSimulationSpeed(simulationSpeed);
		BoidModel.setTimeDelta (timeDelta);
		
		for (ThreadSafeBoidState boid : list) {
			boid.calculateNextMove();
		}
		
		synchronized (list) {
			for (BoidState boid : list) {
				boid.commitNextMove();
			}
			
			updateBoidCounts();
		}
	}
	
	public void setBoidCount(SimulationRules rules, int count) {
		synchronized (requestedBoidCounts) {
			if (!requestedBoidCounts.containsKey(rules)) {
				requestedBoidCounts.put(rules, count);
			} else {
				requestedBoidCounts.remove(rules);
				requestedBoidCounts.put(rules, count);
			}
		}
	}
	
	/* Private stuff */
	
	private void updateBoidCounts() {
		synchronized (requestedBoidCounts) {
			Set<SimulationRules> keys = requestedBoidCounts.keySet();
			for(SimulationRules key : keys) {
				
				// Add key if it doesn't exist
				if(!boidCounts.containsKey(key)) {
					boidCounts.put(key, 0);
				}
				
				// Add/remove boids to simulation
				int requested = requestedBoidCounts.get(key);
				int actual = boidCounts.get(key);
				
				while (requested > actual) {
					addBoid(key);
					actual++;
				}
				
				while (requested < actual) {
					removeBoid(key);
					actual--;
				}
				
				boidCounts.remove(key);
				boidCounts.put(key, actual);
			}
		}
	}
	
	private void addBoid(SimulationRules rules) {
		BoidModel model;
		if (models.containsKey(rules)) {
			model = models.get(rules);
		} else {
			model = new BoidModel(rules);
			models.put(rules, model);
		}
		synchronized (list) {
			list.add(new BoidState (model, PhysState.random(), rules));
		}
	}
	
	private void removeBoid(SimulationRules rules) {
		synchronized(list) {
			Iterator<BoidState> it = list.iterator();
			while (it.hasNext()) {
				if (it.next().hasRules(rules)) {
					it.remove();
					return;
				}
			}
		}
	}
}
