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

/** Basic view which takes care of most of the top-level GUI stuff + boid positioning */
@SuppressWarnings("unused") // processing.opengl is not used, but needed...
abstract class View3D extends PApplet implements BoidList.BoidReader {
	
	private static final boolean USE_OPENGL = false;
	
	private static final float ZOOM_STEP = 5;
	private static final double SPEED_STEP = 0.2;
	
	protected int height;
	protected int width;
	
	private Engine engine;
	private TreeSet<Integer> keysDown;
	
	// Camera
	
	private float cameraDistance = (float) 1.5 * Engine.BOX_SIZE;
	private double azimuth = PI;
	private double zenith = HALF_PI;
	
	// Scrollers & controls
	
	private Scroller hScroller;
	private Scroller vScroller;
	private ControlPane controls;
	
	/* Constructor */
	
	View3D (int _width, int _height) {
		
		Widget.setApplet(this);
		
		engine = new Engine();
		
		width = _width;
		height = _height;
		
		// Scrollers
		
		final int scrollerWidth = (int)(0.03 * width);
		final int scrollBarWidth = scrollerWidth * 4;
		
		Rectangle hScrollRect = new Rectangle(1, height - scrollerWidth, 
				width - scrollerWidth - 2, scrollerWidth);
		hScroller = new Scroller(hScrollRect, Scroller.Direction.Horizontal, scrollBarWidth);
		
		Rectangle vScrollRect = new Rectangle(width - scrollerWidth, 1, 
				scrollerWidth, height - scrollerWidth - 2);
		vScroller = new Scroller(vScrollRect, Scroller.Direction.Vertical, scrollBarWidth);
		
		// Contorls
		
		final int controlsWidth = 300;
		
		Rectangle controlMainRect = new Rectangle(
				width - scrollerWidth - controlsWidth - 4, // x
				1, // y
				controlsWidth, // width
				height - scrollerWidth - 4 // height
				);
		Rectangle controlToggleRect = new Rectangle(width - scrollerWidth,
				height - scrollerWidth, scrollerWidth, scrollerWidth);
		controls = new ControlPane(engine, controlMainRect, controlToggleRect);
	}
	
	/* BoidReader implementation */
	
	@Override
	public void readBoid(ThreadSafeBoidState boid) {
	    	pushMatrix();
	    	translate(boid.getPosition(), boid.getBase());
	    	drawBoid ();
	    	popMatrix();
	}
	
	/**
     * Draw boid facing right on the x-y-plane
     */
    protected abstract void drawBoid(); 
	
	/* setup and draw */
	
    @Override
	public void setup() {
		
    	if(USE_OPENGL) {
    		size(width, height, OPENGL);
    	} else {
    		size(width, height, P3D);
    	}
		
		keysDown = new TreeSet<Integer>();
		
		// Engine 
        engine.start();
    }

    @Override
    public void draw() {
    	
    	handleKeys();
    	background(0);
    	lights();
    	setCamera();
    	
    	/* Draw box */
    	
    	noFill();
    	stroke(255);
    	box(Engine.BOX_SIZE);
    	
    	/* Draw boids */
    	
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
    
    /** Translates view to a position and vector base.
     *  After this the boid can be drawn facing right on the x-y-plane.
     */
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
    	
    	// Limit maximum and minimum angles to avoid rendering problems
    	double zPos = vScroller.getPosition();
    	zPos = Math.min(zPos, 0.9999999);
    	zPos = Math.max(zPos, 0.0000001);
    	zenith = PI * zPos;
    	
    	cameraDistance = Math.max(cameraDistance, 0);
    	Vector eye = new Vector(cameraDistance, new Angle(azimuth, zenith));
    	
    	camera(
    			(float)eye.getX(), (float)eye.getY(), (float)eye.getZ(),
    			(float)0.0, (float)0.0, (float)0.0, 
    			(float)0.0, (float)0.0, (float)1.0);
    }
    
    /** Not used, but can be used to follow a boid with the camera.
     * Probably doesn't work correctly anymore though...
     */
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
    	Widget.globalMousePressed();
    }
    
    /* Private helpers */
    
    /** calculates the roll (rotation around forward axis)
     * of a boid (in radians), given its vector base
     */
    private double calculateRoll (VectorBase base) {
    	
    	Vector up;
    	Vector left;
    	double roll;
    	
    	/* calculating roll needs some wizardry...
    	 * Nothing magical here though ;)
    	 */
    	
    	/* Localize global up vector */
    	up = new Vector(0.0, 1.0, 0.0);
    	base.localize(up);
    	
    	/* Cross product of the global up and local forward is the vertical left */
    	left = new Vector();
    	left.crossProduct(up, base.getFwd());
    	
    	/* project base up to the vertical left to get roll */
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
