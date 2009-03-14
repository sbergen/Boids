package gui;

import processing.core.*;

import engine.Engine;
import vector.Vector;
import boid.*;

public final class View3D extends PApplet implements BoidList.BoidReader {

	private Engine engine;
	
	public void setup() {
		
		engine = new Engine();
		
        // original setup code here ...
        size(1400, 800, P3D);
        noStroke();
        
        engine.start();
    }

    public void draw() {
    	background(0);
    	lights();
    	
        engine.readBoids(this);
    }

    @Override
	public void readBoid(ThreadSafeBoidState boid) {
    	pushMatrix();
    	
    	
    	Vector pos = new Vector (boid.getPosition());
    	pos.scale(5);
    	//System.out.println("X: " + pos.getX() + ", Y: " + pos.getY() + ", Z: " + pos.getZ());
    	//translate(58, 48, 0);
    	translate((float)pos.getX(), (float)pos.getY(), -(float)pos.getZ());
    	
    	sphere(10);
    	
    	popMatrix();
	}

	private static final long serialVersionUID = 1880066128256612928L;
	
}
