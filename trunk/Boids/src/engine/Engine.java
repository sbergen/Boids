package engine;

import java.util.Timer;
import java.util.Date;
import java.util.TimerTask;

import boid.BoidList;

public final class Engine {
	
	/* Private stuff */
	
	private PhysLimits limits;
	private BoidList   boidList;
	private SimulationRunner runner;
	private Timer timer;
	private boolean running;
	private long lastExecTime;
	private double speed;
	
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
	
	public Engine () {
		limits = new PhysLimits();
		boidList = new BoidList();
		runner = new SimulationRunner();
		timer = new Timer();
		
		for (int i = 0; i < 80; i++) {
			boidList.addBoid(limits);
		}
	}
	
	public void start() {
		lastExecTime = System.nanoTime();
		timer.scheduleAtFixedRate(runner, 0, 20);
		running = true;
		speed = 2.5;
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
}
