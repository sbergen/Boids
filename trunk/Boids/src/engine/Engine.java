package engine;

import java.util.Timer;
import java.util.TimerTask;

import boid.BoidList;


/** Engine takes care of running the simulation.
 * This is also the only class used bu GUI parts
 */
public final class Engine {
	
	/* Private stuff */
	
	private SimulationRules rules; // Currently only one set of rules is supported
	private BoidList boidList;
	private SimulationRunner runner;
	private Timer timer;
	private long lastExecTime;
	private double speed = 8.0;
	
	private static final int CYCLE_LENGTH = 20; // simulation cycle length in milliseconds
	
	/* Timer task that runs the simulation at a maximum rate */
	private class SimulationRunner extends TimerTask {
		public void run() { 
			
			long currentTime = System.nanoTime();
			long delta = currentTime - lastExecTime;
			lastExecTime = currentTime;
			
			boidList.setBoidCount(rules, (int)boidCount.value());
			boidList.updateBoidStates(delta, speed);
		}
	}
	
	/* Public stuff */
	
	public static final int defaultBoidCount = 100;
	
	public Engine () {
		rules = new SimulationRules();
		boidList = new BoidList();
		runner = new SimulationRunner();
		timer = new Timer();
		
		boidCount = new Property("BoidCount", defaultBoidCount);
		boidList.setBoidCount(rules, (int) boidCount.defaultValue());
	}
	
	public void start() {
		lastExecTime = System.nanoTime();
		timer.scheduleAtFixedRate(runner, 0, CYCLE_LENGTH);
	}
	
	public void stop() {
		timer.cancel();
	}
	
	/** this exposes the boid reader interface of the list */
	public void readBoids (BoidList.BoidReader reader) {
		boidList.readBoids(reader);
	}
	
	public void changeSpeed (double speedDelta) {
		speed += speedDelta;
		speed = Math.max(speed, 0.0);
	}
	
	public Property boidCount;
	
	public SimulationRules getRules() {
		return rules;
	}
}
