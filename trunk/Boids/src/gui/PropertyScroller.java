package gui;

import engine.Property;

/** A Scroller for properties, can set and load to/from a property instance */
public final class PropertyScroller extends Scroller {

	private Property property;
	
	public PropertyScroller( Rectangle rectangle, Direction direction, int barWidth, Property property) {
		super(rectangle, direction, barWidth);
		this.property = property;
		
	}
	
	/** Commits value to property */
	public void commitValue() {
		property.setValue(position * 2 * property.defaultValue());
	}
	
	/** Loads value from property */
	public void updateValue() {
		position = property.value() / (2 * property.defaultValue());
	}
	
}
