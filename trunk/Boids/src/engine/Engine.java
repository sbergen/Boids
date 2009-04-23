package engine;

import java.util.Timer;
import java.util.TimerTask;

import boid.BoidList;


/** Engine takes care of running the simulation.
 * This is also the only class used bu GUI parts
 */
public final class Engine {
	
	/* Private stuff */
	
	protected SimulationRules rules; // Currently only one set of rules is supported
	protected BoidList boidList;
	private SimulationRunner runner;
	private Timer timer;
	protected long lastExecTime;
	protected double speed = 8.0;
	
	// simulation cycle minimum length in milliseconds
	private static final int CYCLE_LENGTH = 15;
	
	/* Timer task that runs the simulation at a maximum rate */
	private class SimulationRunner extends TimerTask {
		
		public SimulationRunner() { /* no implementation required */ }
		
		@Override
		public void run() { 
			
			long currentTime = System.nanoTime();
			long delta = currentTime - lastExecTime;
			lastExecTime = currentTime;
			
			boidList.setBoidCount(rules, boidCount.value().intValue());
			boidList.updateBoidStates(delta, speed);
		}
	}
	
	/* Public stuff */
	
	public static final int BOX_SIZE = 650;
	public static final int defaultBoidCount = 100;
	public Property<Double> boidCount;
	
	public Engine () {
		rules = new SimulationRules();
		boidList = new BoidList();
		runner = new SimulationRunner();
		timer = new Timer();
		
		boidCount = new Property<Double>("BoidCount", Double.valueOf(defaultBoidCount));
		boidList.setBoidCount(rules, boidCount.defaultValue().intValue());
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
	
	public SimulationRules getRules() {
		return rules;
	}
}
