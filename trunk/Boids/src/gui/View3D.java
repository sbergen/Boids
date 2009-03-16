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
    	pushMatrix();
    	translate(boid.getPosition(), boid.getAngle());
    	drawBoid ();
    	popMatrix();
	}

    private void translate(Vector position, Angle angle) {
		final double scale = 2.0;
    	translate(
    			(float) (position.getX() * scale),
    			(float) (position.getY() * scale),
    			(float) (position.getZ() * -scale));
    	
    	double rotZ = angle.azimuth();
    	if (rotZ < 0) {
    		rotZ += 2.0 * Math.PI;
    	}
    	rotZ += Math.PI / 2.0;
    	
    	rotateZ((float) rotZ);
    	rotateX((float) (angle.zenith() - Math.PI / 2.0));
	}
    
    protected abstract void drawBoid(); 
    
    // Suppress warnings...
	private static final long serialVersionUID = 1880066128256612928L;
	
}
