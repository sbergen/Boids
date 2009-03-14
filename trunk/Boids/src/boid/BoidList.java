package boid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

import vector.Vector;
import engine.PhysLimits;;

final class BoidList implements ThreadSafeBoidList {
	
	/* Data members */
	
	private ArrayList<BoidState> list;
	private HashMap<PhysLimits, BoidModel> models;
	
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
	
	public void addBoid(PhysLimits limits) {
		BoidModel model;
		if (models.containsKey(limits)) {
			model = models.get(limits);
		} else {
			model = new BoidModel(limits);
			models.put(limits, model);
		}
		list.add(new BoidState (model, PhysState.random()));
	}
	
	public void removeBoid(PhysLimits limits) {
		Iterator<BoidState> it = list.iterator();
		while (it.hasNext()) {
			if (it.next().hasLimits(limits)) {
				it.remove();
				return;
			}
		}
	}

}
