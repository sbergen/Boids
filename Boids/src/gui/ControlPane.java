package gui;

import processing.core.*;
import engine.Engine;
import engine.SimulationRules;

final class ControlPane {
	
	private static final int ITEMS = 15;
	private static final int SPACING = 5;
	
	private float labelHeight;
	private float controlHeight;
	
	private PApplet parent;
	private Engine engine;
	private SimulationRules rules;
	private Rectangle togglerRect;
	private Rectangle mainRect;
	private PFont font;
	
	private boolean hidden;
	
	// Scrollers
	
	private Scroller perceptionRangeScroll;
	private Scroller cohesionScroll;
	private Scroller separationScroll;
	private Scroller alignmentScroll;
	
	public ControlPane (PApplet parentApplet,  Engine physEngine,
			            Rectangle mainRectangle, Rectangle togglerRectangle) {
		parent = parentApplet;
		engine = physEngine;
		rules = engine.getRules();
		mainRect = mainRectangle;
		togglerRect = togglerRectangle;
		
		hidden = true;
		font = parent.loadFont("CourierNew36.vlw");
		
		labelHeight = (mainRect.height() - ITEMS * 3 * SPACING) / (ITEMS * 2);
		controlHeight = labelHeight;
		
		// SCrollers
		
		int barWidth = mainRect.width() / 10;
		
		perceptionRangeScroll = new Scroller(parent, getControlRect(0), Scroller.Direction.Horizontal, barWidth);
		cohesionScroll = new Scroller(parent, getControlRect(1), Scroller.Direction.Horizontal, barWidth);
		separationScroll = new Scroller(parent, getControlRect(2), Scroller.Direction.Horizontal, barWidth);
		alignmentScroll = new Scroller(parent, getControlRect(3), Scroller.Direction.Horizontal, barWidth);
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
		
		/* Controls */
		
		parent.rectMode(PGraphics.CORNER);
		parent.textFont(font, labelHeight);
		
		// Perception range
		drawLabel(0, "Perception Range");
		perceptionRangeScroll.draw();
		rules.setPerceptionRange(perceptionRangeScroll.getPosition() * 2 * SimulationRules.defaultPerceptionRange);
		
		// cohesion
		drawLabel(1, "Cohesion");
		cohesionScroll.draw();
		rules.setCohesionFactor(cohesionScroll.getPosition() * 2 * SimulationRules.defaultCohesionFactor);
		
		// separation
		drawLabel(2, "Separation");
		separationScroll.draw();
		rules.setSeparationFactor(separationScroll.getPosition() * 2 * SimulationRules.defaultSeparationFactor);
		
		// alignment
		drawLabel(3, "Alignment");
		alignmentScroll.draw();
		rules.setAlignmentFactor(alignmentScroll.getPosition() * 2 * SimulationRules.defaultAlignmentFactor);
		
	}
	
	private void drawLabel (int number, final String text) {
		Rectangle rect = getLabelRect(number);
		parent.fill(255, 200);
		parent.text(text, rect.left(), rect.bottom());
	}
	
	private Rectangle getLabelRect(int n) {
		int offset = (int) (SPACING + n * (3 * SPACING + labelHeight + controlHeight));
		return new Rectangle(mainRect.left() + SPACING, mainRect.top() + offset, mainRect.width() - 2 * SPACING, (int) controlHeight);
	}
	
	private Rectangle getControlRect(int n) {
		int offset = (int) (2 * SPACING + labelHeight + n * (3 * SPACING + labelHeight + controlHeight));
		return new Rectangle(mainRect.left() + SPACING, mainRect.top() + offset, mainRect.width() - 2 * SPACING, (int) controlHeight);
	}

}
