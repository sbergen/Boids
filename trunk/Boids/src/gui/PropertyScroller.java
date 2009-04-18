package gui;

import gui.Scroller.Direction;
import engine.SimulationRules;
import engine.SimulationRules.Property;
import processing.core.PApplet;

public final class PropertyScroller extends Scroller {

	private Property property;
	
	public PropertyScroller(PApplet parentApplet,
			Rectangle rectangle, Direction direction, int barWidth,
			Property property) {
		super(parentApplet, rectangle, direction, barWidth);
		this.property = property;
	}
	
	public void updateValue() {
		property.setValue(getPosition() * 2 * property.defaultValue());
	}
	
}
