package engine;

import java.util.Timer;
import java.util.TimerTask;

import boid.BoidList;

public final class Engine {
	
	/* Private stuff */
	
	private SimulationRules rules;
	private BoidList   boidList;
	private SimulationRunner runner;
	private Timer timer;
	private long lastExecTime;
	private double speed = 8.0;
	
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
	
	public void save() {
		rules.saveToFile("rules");
	}
	
	public void start() {
		lastExecTime = System.nanoTime();
		timer.scheduleAtFixedRate(runner, 0, 30);
	}
	
	public void stop() {
		timer.cancel();
	}
	
	public void readBoids (BoidList.BoidReader reader) {
		boidList.readBoids(reader);
	}
	
	public void changeSpeed (double speedDelta) {
		speed += speedDelta;
	}
	
	public Property boidCount;
	
	public SimulationRules getRules() {
		return rules;
	}
}
