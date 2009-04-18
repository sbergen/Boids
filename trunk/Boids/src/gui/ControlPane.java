package gui;

import processing.core.*;
import engine.Engine;
import engine.SimulationRules;

final class ControlPane extends Widget {
	
	private static final int ITEMS = 15;
	private static final int SPACING = 5;
	
	private float labelHeight;
	private float controlHeight;
	
	private Engine engine;
	private SimulationRules rules;
	private Rectangle mainRect;
	private PFont font;
	
	// Toggler and fileUI
	
	Toggler toggler;
	FileUI fileUI;
	
	// Scrollers
	
	private PropertyScroller perceptionRangeScroll;
	private PropertyScroller cohesionScroll;
	private PropertyScroller separationScroll;
	private PropertyScroller alignmentScroll;
	private PropertyScroller amountScroll;
	private PropertyScroller maxTurnScroll;
	private PropertyScroller minForceScroll;
	private PropertyScroller maxForceScroll;
	private PropertyScroller minSpeedScroll;
	private PropertyScroller maxSpeedScroll;
	
	public ControlPane (Engine physEngine,
			            Rectangle mainRectangle, Rectangle togglerRectangle) {
		engine = physEngine;
		rules = engine.getRules();
		mainRect = mainRectangle;
		toggler = new Toggler(togglerRectangle);
		
		font = parent.loadFont("CourierNew36.vlw");
		
		labelHeight = (mainRect.height() - ITEMS * 3 * SPACING) / (ITEMS * 2);
		controlHeight = labelHeight;
		
		// SCrollers
		
		int barWidth = mainRect.width() / 10;
		
		perceptionRangeScroll = new PropertyScroller(getControlRect(0),
				Scroller.Direction.Horizontal, barWidth, rules.perceptionRange);
		cohesionScroll = new PropertyScroller(getControlRect(1),
				Scroller.Direction.Horizontal, barWidth, rules.cohesionFactor);
		separationScroll = new PropertyScroller(getControlRect(2),
				Scroller.Direction.Horizontal, barWidth, rules.separationFactor);
		alignmentScroll = new PropertyScroller(getControlRect(3),
				Scroller.Direction.Horizontal, barWidth, rules.alignmentFactor);
		amountScroll = new PropertyScroller(getControlRect(4),
				Scroller.Direction.Horizontal, barWidth, engine.boidCount);
		maxTurnScroll = new PropertyScroller(getControlRect(5),
				Scroller.Direction.Horizontal, barWidth, rules.maxTurn);
		minForceScroll = new PropertyScroller(getControlRect(6),
				Scroller.Direction.Horizontal, barWidth, rules.minForce);
		maxForceScroll = new PropertyScroller(getControlRect(7),
				Scroller.Direction.Horizontal, barWidth, rules.maxForce);
		minSpeedScroll = new PropertyScroller(getControlRect(8),
				Scroller.Direction.Horizontal, barWidth, rules.minSpeed);
		maxSpeedScroll = new PropertyScroller(getControlRect(9),
				Scroller.Direction.Horizontal, barWidth, rules.maxSpeed);
		
		// Save/Load
		
		Rectangle rect = getControlRect(10);
		Rectangle fileRect = new Rectangle(rect.left(), rect.top(),
				                           rect.width(), mainRect.bottom() - rect.top());
		fileUI = new FileUI(fileRect, rules);
	}
	
	@Override
	public void draw() {
		toggler.draw();
		if (toggler.selected()) {	
			drawControls();
		}
	}
	
	/* Private stuff */
	
	private void drawControls() {
		
		// "Container"
		
		parent.fill(0,0);
		parent.stroke(0, 200, 0, 100);
		parent.rect(mainRect.left(), mainRect.top(), mainRect.width(), mainRect.height());
		
		/* Direct Controls */
		
		parent.rectMode(PGraphics.CORNER);
		parent.textFont(font, labelHeight);
		
		drawLabel(0, "Perception Range");
		perceptionRangeScroll.draw();
		
		drawLabel(1, "Cohesion");
		cohesionScroll.draw();
		
		drawLabel(2, "Separation");
		separationScroll.draw();
		
		drawLabel(3, "Alignment");
		alignmentScroll.draw();
		
		drawLabel(4, "Number of Boids");
		amountScroll.draw();
		
		drawLabel(5, "Maximum Turn");
		maxTurnScroll.draw();
		
		drawLabel(6, "Minimum Force");
		minForceScroll.draw();
		
		drawLabel(7, "Maximum Force");
		maxForceScroll.draw();
		
		drawLabel(8, "Minimum Speed");
		minSpeedScroll.draw();
		
		drawLabel(9, "Maximum Speed");
		maxSpeedScroll.draw();
		
		/* Save/Load */
		
		drawLabel(10, "Save/Load settings");
		fileUI.draw();
		if (fileUI.hasLoaded()) {
			updateValues();
		}
		
		commitValues();
	}
	
	private void drawLabel (int number, final String text) {
		Rectangle rect = getLabelRect(number);
		parent.textAlign(PGraphics.LEFT, PGraphics.BOTTOM);
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
	
	private void commitValues() {
		perceptionRangeScroll.commitValue();
		cohesionScroll.commitValue();
		separationScroll.commitValue();
		alignmentScroll.commitValue();
		amountScroll.commitValue();
		maxTurnScroll.commitValue();
		minForceScroll.commitValue();
		maxForceScroll.commitValue();
		minSpeedScroll.commitValue();
		maxSpeedScroll.commitValue();
	}

	private void updateValues() {
		perceptionRangeScroll.updateValue();
		cohesionScroll.updateValue();
		separationScroll.updateValue();
		alignmentScroll.updateValue();
		amountScroll.updateValue();
		maxTurnScroll.updateValue();
		minForceScroll.updateValue();
		maxForceScroll.updateValue();
		minSpeedScroll.updateValue();
		maxSpeedScroll.updateValue();
	}
}
