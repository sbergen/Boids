package engine;

import java.util.Timer;
import java.util.TimerTask;

import boid.BoidList;

public final class Engine {
	
	/* Private stuff */
	
	private PhysLimits limits;
	private BoidList   boidList;
	private SimulationRunner runner;
	private Timer timer;
	private boolean running;
	
	private class SimulationRunner extends TimerTask {
		public void run() {
			boidList.updateBoidStates();
		}
	}
	
	/* Public stuff */
	
	public Engine () {
		limits = new PhysLimits();
		boidList = new BoidList();
		runner = new SimulationRunner();
		timer = new Timer();
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
	}
	
	public void start() {
		timer.scheduleAtFixedRate(runner, 1000, 100);
		running = true;
	}
	
	public void stop() {
		timer.cancel();
		running = false;
	}
	
	public void readBoids (BoidList.BoidReader reader) {
		boidList.readBoids(reader);
	}
}
