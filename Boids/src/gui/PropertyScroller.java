package gui;

import engine.Property;
import processing.core.PApplet;

public final class PropertyScroller extends Scroller {

	private Property property;
	
	public PropertyScroller(PApplet parentApplet,
			Rectangle rectangle, Direction direction, int barWidth,
			Property property) {
		super(parentApplet, rectangle, direction, barWidth);
		this.property = property;
		
	}
	
	/// Commits value to property
	public void commitValue() {
		property.setValue(position * 2 * property.defaultValue());
	}
	
	public void updateValue() {
		position = property.value() / (2 * property.defaultValue());
	}
	
}
