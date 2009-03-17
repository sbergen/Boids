package gui;

import java.util.TreeSet;
import java.awt.event.KeyEvent;

import processing.core.*;
import processing.opengl.*;

import engine.Engine;
import vector.Vector;
import vector.Angle;
import boid.*;

public abstract class View3D extends PApplet implements BoidList.BoidReader {

	private static final float ZOOM_STEP = (float) 5.0;
	
	private Engine engine;
	private TreeSet<Integer> keysDown;
	private float cameraDistance;
	
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
		
		keysDown = new TreeSet<Integer>();
		cameraDistance = 800;
		
		engine = new Engine();
        engine.start();
    }

    public void draw() {
    	
    	//System.out.println("draw");
    	
    	handleKeys();
    	
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
    	
    	Vector eye = new Vector(cameraDistance, new Angle(azimuth, zenith));
    	
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
    
    /* Key listening and repeating */
    
    public void keyPressed() {
    	keysDown.add(new Integer(keyCode));
    }
    
    public void keyReleased() {
    	keysDown.remove(new Integer(keyCode));
    }
    
    private void handleKeys() {
    	for (Integer keyCode : keysDown) {
    		switch(keyCode) {
    		  case KeyEvent.VK_UP:
    			cameraDistance -= ZOOM_STEP;
    			break;
    		  case KeyEvent.VK_DOWN:
    			cameraDistance += ZOOM_STEP;
    			break;
    		}
    	}
    }
    
    // Suppress warnings...
	private static final long serialVersionUID = 1880066128256612928L;
	
}
