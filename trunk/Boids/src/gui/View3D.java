package gui;

import java.util.TreeSet;
import java.awt.event.KeyEvent;

import processing.opengl.*;
import processing.core.*;

import engine.Engine;
import engine.SimulationRules;
import vector.Vector;
import vector.VectorBase;
import vector.Angle;
import boid.*;

@SuppressWarnings("unused") // processing.opengl is not used, but needed...
public abstract class View3D extends PApplet implements BoidList.BoidReader {

	private static final float ZOOM_STEP = (float) 5.0;
	private static final double SPEED_STEP = 0.2;
	
	protected int height;
	protected int width;
	
	private Engine engine;
	private TreeSet<Integer> keysDown;
	
	// Camera
	
	private boolean followBoid;
	private float cameraDistance;
	private double azimuth;
	private double zenith;
	
	// Scrollers & controls
	
	private Scroller hScroller;
	private Scroller vScroller;
	private ControlPane controls;
	
	/* Constructor */
	
	View3D (int _width, int _height) {
		
		engine = new Engine();
		
		width = _width;
		height = _height;
		
		azimuth = PI;
		zenith = HALF_PI;
		
		// Scrollers
		
		final int scrollerWidth = 50;
		final int scrollBarWidth = 100;
		
		Rectangle hScrollRect = new Rectangle(1, height - scrollerWidth, width - scrollerWidth - 2, scrollerWidth);
		hScroller = new Scroller(this, hScrollRect, Scroller.Direction.Horizontal, scrollBarWidth);
		
		Rectangle vScrollRect = new Rectangle(width - scrollerWidth, 1, scrollerWidth, height - scrollerWidth - 2);
		vScroller = new Scroller(this, vScrollRect, Scroller.Direction.Vertical, scrollBarWidth);
		
		// Contorls
		
		final int controlsWidth = 300;
		
		Rectangle controlMainRect = new Rectangle(
				width - scrollerWidth - controlsWidth - 4, // x
				1, // y
				controlsWidth, // width
				height - scrollerWidth - 4 // height
				);
		Rectangle controlToggleRect = new Rectangle(width - scrollerWidth, height - scrollerWidth, scrollerWidth, scrollerWidth);
		controls = new ControlPane(this, engine, controlMainRect, controlToggleRect);
	}
	
	/* BoidReader implementation */
	
	@Override
	public void readBoid(ThreadSafeBoidState boid) {
		if (followBoid) {
    		setCameraFollowBoid(boid);
    		followBoid = false;
    	} else {
	    	pushMatrix();
	    	translate(boid.getPosition(), boid.getBase());
	    	drawBoid ();
	    	popMatrix();
    	}
	}
	
	/**
     * Draw boid facing right
     */
    protected abstract void drawBoid(); 
	
	/* setup and draw */
	
    @Override
	public void setup() {
		
		size(width, height, OPENGL);
        //size(width, height, P3D);
		
		keysDown = new TreeSet<Integer>();
		cameraDistance = 800;
		
		// Engine 
        engine.start();
    }

    public void draw() {
    	
    	//System.out.println("draw");
    	
    	handleKeys();
    	
    	background(0);
    	lights();
    	
    	// uncomment for following mode
    	//followBoid = true;
    	
    	//drawIndicator();
    	
    	// uncomment for regular mode
    	setCamera();
    	
    	/* Draw box */
    	
    	noFill();
    	stroke(255);
    	box(500);
    	
    	/* Draw boids */
    	
    	//fill(255);
    	noStroke();
        engine.readBoids(this);
		
        drawControls();
    }

    /* Helpers */

    private void drawControls() {
    	
    	hint(DISABLE_DEPTH_TEST);
    	camera();
    	perspective();
    	
    	hScroller.draw();
    	vScroller.draw();
    	controls.draw();
    	
    	hint(ENABLE_DEPTH_TEST);
    }
    
    private void translate(Vector position, VectorBase base) {    	
    	translate(
    			(float) position.getX(),
    			(float) position.getY(),
    			(float) position.getZ());
    	
    	Angle fwdAngle = new Angle(base.getFwd());
    	
    	rotateZ((float) fwdAngle.azimuth());
    	rotateY((float) (fwdAngle.zenith() - HALF_PI));
    	rotateX((float) calculateRoll(base));
	}
    
    private void setCamera() {
    	
    	azimuth = TWO_PI * (1.0 - hScroller.getPosition());
    	
    	double zPos = vScroller.getPosition();
    	zPos = Math.min(zPos, 0.9999999);
    	zPos = Math.max(zPos, 0.0000001);
    	zenith = PI * zPos;
    	
    	Vector eye = new Vector(cameraDistance, new Angle(azimuth, zenith));
    	
    	camera(
    			(float)eye.getX(), (float)eye.getY(), (float)eye.getZ(),
    			(float)0.0, (float)0.0, (float)0.0, 
    			(float)0.0, (float)0.0, (float)1.0);
    }
    
    private void setCameraFollowBoid(ThreadSafeBoidState state) {
    	
    	Vector position = state.getPosition();
    	Vector up = state.getBase().getUp();
    	Vector fwd = new Vector (state.getBase().getFwd());
    	fwd.scale(2.0);
    	fwd.add(position);
    	
    	camera(
    			(float)position.getX(), (float)position.getY(), (float)position.getZ(),
    			(float)fwd.getX(), (float)fwd.getY(), (float)fwd.getZ(), 
    			(float)up.getX(), (float)up.getY(), (float)up.getZ());
    			//(float)0.0, (float)0.0, (float)1.0);
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
    
    @Override
    public void keyPressed() {
    	keysDown.add(new Integer(keyCode));
    }
    
    @Override
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
    		  case KeyEvent.VK_LEFT:
    			engine.changeSpeed(-SPEED_STEP);
    			break;
    		  case KeyEvent.VK_RIGHT:
      			engine.changeSpeed(SPEED_STEP);
      			break;
    		}
    	}
    }
    
    /* Mouse */
    
    @Override
    public void mousePressed() {
    	controls.mousePressed();
    }
    
    /* Private helpers */
    
    private double calculateRoll (VectorBase base) {
    	
    	Vector up;
    	Vector left;
    	double roll;
    	
    	/* calculating roll needs some wizardry... */
    	
    	/* Localize 3d up vector */
    	up = new Vector(0.0, 1.0, 0.0);
    	base.localize(up);
    	
    	/* Cross product of up and forward is the "straight" left */
    	left = new Vector();
    	left.crossProduct(up, base.getFwd());
    	
    	/* project up to the "straight" left to get roll */
    	roll = left.dotProduct(base.getUp()) * HALF_PI;
    	
    	/* Translate values to the range [0, 2Pi] */
    	if (roll < 0.0) {
    		roll = TWO_PI - roll;
    	}
    	
    	return roll;
    }
    
    // Suppress warnings...
	private static final long serialVersionUID = 1880066128256612928L;
}
