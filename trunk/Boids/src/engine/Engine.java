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
	private double speed;
	
	private boolean running;
	
	private class SimulationRunner extends TimerTask {
		public void run() { 
			
			//System.out.println("update");
			
			long currentTime = System.nanoTime();
			long delta = currentTime - lastExecTime;
			lastExecTime = currentTime;
			
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
		
		boidList.setBoidCount(rules, defaultBoidCount);
	}
	
	public void start() {
		lastExecTime = System.nanoTime();
		timer.scheduleAtFixedRate(runner, 0, 30);
		running = true;
		speed = 4.5;
	}
	
	public void stop() {
		timer.cancel();
		running = false;
	}
	
	public void readBoids (BoidList.BoidReader reader) {
		boidList.readBoids(reader);
	}
	
	public void changeSpeed (double speedDelta) {
		speed += speedDelta;
	}
	
	public void setBoidCount(int count) {
		boidList.setBoidCount(rules, count);
	}
	
	public SimulationRules getRules() {
		return rules;
	}
}
