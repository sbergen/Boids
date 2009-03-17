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
	
	private class SimulationRunner extends TimerTask {
		public void run() { 
			
			//System.out.println("update");
			
			long currentTime = System.nanoTime();
			long delta = currentTime - lastExecTime;
			lastExecTime = currentTime;
			
			boidList.updateBoidStates(delta);
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
		boidList.addBoid(limits);
		
		// 10
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		
		// 20
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		
		// 10
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		boidList.addBoid(limits);
		
		boidList.addBoid(limits);
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
		lastExecTime = System.nanoTime();
		timer.scheduleAtFixedRate(runner, 0, 20);
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
