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
	private Date startedAt;
	
	private class SimulationRunner extends TimerTask {
		public void run() {
			Date start = new Date();
			boidList.updateBoidStates();
			Date end = new Date();
//			System.out.println("Simulation run took " + (end.getTime() - start.getTime()) + "ms");
//			if ((end.getTime() - start.getTime()) > 100) {
//				System.out.println("Calculation starting at " + (start.getTime() - startedAt.getTime()) + " took too long!");
//			}
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
		startedAt = new Date();
		timer.scheduleAtFixedRate(runner, 1000, 70);
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
