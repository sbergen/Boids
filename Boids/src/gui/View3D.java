package gui;

import processing.core.*;
import processing.opengl.*;

import engine.Engine;
import vector.Vector;
import vector.Angle;
import boid.*;

public abstract class View3D extends PApplet implements BoidList.BoidReader {

	private Engine engine;
	
	public void setup() {
		
		engine = new Engine();
		
        // original setup code here ...
        size(1200, 800, OPENGL);
        //size(1200, 800, P3D);
        noStroke();
        sphereDetail(1);
        
        engine.start();
    }

    public void draw() {
    	background(0);
    	lights();
    	
        engine.readBoids(this);
    }

    @Override
	public void readBoid(ThreadSafeBoidState boid) {
    	drawBoid (boid.getPosition(), boid.getAngle());
	}

    protected abstract void drawBoid(Vector position, Angle angle); 
    
    // Suppress warnings...
	private static final long serialVersionUID = 1880066128256612928L;
	
}
