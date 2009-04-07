package gui;

import processing.core.*;
import engine.Engine;

final class ControlPane {
	
	private static final int ITEMS = 15;
	private static final int SPACING = 5;
	
	private float labelHeight;
	private float controlHeight;
	
	private PApplet parent;
	private Engine engine;
	private Rectangle togglerRect;
	private Rectangle mainRect;
	private PFont font;
	
	private boolean hidden;
	
	// Scrollers
	
	private Scroller perceptionRangeScroll;
	
	public ControlPane (PApplet parentApplet,  Engine physEngine,
			            Rectangle mainRectangle, Rectangle togglerRectangle) {
		parent = parentApplet;
		engine = physEngine;
		mainRect = mainRectangle;
		togglerRect = togglerRectangle;
		
		hidden = true;
		font = parent.loadFont("CourierNew36.vlw");
		
		labelHeight = (mainRect.height() - ITEMS * 3 * SPACING) / (ITEMS * 2);
		controlHeight = labelHeight;
		
		// SCrollers
		
		int barWidth = mainRect.width() / 10;
		
		perceptionRangeScroll = new Scroller(parent, getControlRect(0), Scroller.Direction.Horizontal, barWidth);
	}
	
	public void draw() {
		drawToggler();
		
		if (!hidden) {	
			drawControls();
		}
	}
	
	public void mousePressed() {
		if (togglerRect.isInside(parent.mouseX, parent.mouseY)) {
			hidden = !hidden;
		}
    }
	
	/* Private stuff */
	
	private void drawToggler() {
		float radius = (float) (Math.min(togglerRect.width(), togglerRect.height()) * 0.8);
		
		parent.stroke(0, 200, 0);
		parent.fill(0, hidden ? 0 : 200, 0, 100);
		
		parent.ellipseMode(PGraphics.CENTER);
		parent.ellipse(togglerRect.centerX(), togglerRect.centerY(), radius, radius);
	}
	
	private void drawControls() {
		
		// "Container"
		
		parent.fill(0,0);
		parent.stroke(0, 200, 0, 100);
		parent.rect(mainRect.left(), mainRect.top(), mainRect.width(), mainRect.height());
		
		// Controls
		parent.rectMode(PGraphics.CORNER);
		parent.fill(255, 200);
		parent.textFont(font, labelHeight);
		Rectangle rect;
		
		rect = getLabelRect(0);
		parent.text("Perception Range", rect.left(), rect.bottom());
		perceptionRangeScroll.draw();
		engine.setPerceptionRange(perceptionRangeScroll.getPosition());
		
	}
	
	Rectangle getLabelRect(int n) {
		int offset = (int) (SPACING + n * (3 * SPACING + labelHeight + controlHeight));
		return new Rectangle(mainRect.left() + SPACING, mainRect.top() + offset, mainRect.width() - 2 * SPACING, (int) controlHeight);
	}
	
	Rectangle getControlRect(int n) {
		int offset = (int) (2 * SPACING + labelHeight + n * (3 * SPACING + labelHeight + controlHeight));
		return new Rectangle(mainRect.left() + SPACING, mainRect.top() + offset, mainRect.width() - 2 * SPACING, (int) controlHeight);
	}

}
