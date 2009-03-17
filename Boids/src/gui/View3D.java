package gui;

import processing.core.*;
import processing.opengl.*;

import engine.Engine;
import vector.Vector;
import vector.Angle;
import boid.*;

public abstract class View3D extends PApplet implements BoidList.BoidReader {

	private Engine engine;
	
	/* BoidReader implementation */
	
	@Override
	public void readBoid(ThreadSafeBoidState boid) {
    	pushMatrix();
    	translate(boid.getPosition(), boid.getAngle());
    	drawBoid ();
    	popMatrix();
	}
	
	/**
     * Draw boid facing right
     */
    protected abstract void drawBoid(); 
	
	/* setup and draw */
	
	public void setup() {
		
		size(1200, 800, OPENGL);
        //size(1200, 800, P3D);
		
		engine = new Engine();
        engine.start();
    }

    public void draw() {
    	background(0);
    	lights();
    	setCamera();
    	drawIndicator();
    	
    	/* Draw box */
    	
    	noFill();
    	stroke(255);
    	box(500);
    	
    	/* Draw boids */
    	
    	fill(255);
    	noStroke();
        engine.readBoids(this);
    }

    /* Helpers */

    private void translate(Vector position, Angle angle) {    	
    	translate(
    			(float) position.getX(),
    			(float) position.getY(),
    			(float) position.getZ());
    	
    	rotateZ((float) angle.azimuth());
    	//rotateX((float) (HALF_PI));
    	rotateY((float) (angle.zenith() - HALF_PI));
	}
    
    private void setCamera() {
    	
    	double azimuth = TWO_PI * (1.0 - ((float)mouseX / width));
    	double zenith = PI * ((float)mouseY / height);  
    	
    	Vector eye = new Vector(800.0, new Angle(azimuth, zenith));
    	
    	camera(
    			(float)eye.getX(), (float)eye.getY(), (float)eye.getZ(),
    			(float)0.0, (float)0.0, (float)0.0, 
    			(float)0.0, (float)0.0, (float)1.0);
    }
    
    private void drawIndicator() {
    	fill(255, 0, 0);
    	pushMatrix();
    	rotateX(-HALF_PI);
    	beginShape(TRIANGLES);
    	vertex(-20, 0);
    	vertex(0, 250);
    	vertex(20, 0);
    	endShape();
    	popMatrix();
    	
    	fill(0, 255, 0);
    	pushMatrix();
    	rotateY(HALF_PI);
    	beginShape(TRIANGLES);
    	vertex(0, -20);
    	vertex(250, 0);
    	vertex(0, 20);
    	endShape();
    	popMatrix();
    }
    
    // Suppress warnings...
	private static final long serialVersionUID = 1880066128256612928L;
	
}
