package gui;

import processing.core.PGraphics;

public class Toggler extends Widget {

	private Rectangle rect;
	private boolean selected = false;
	private TogglerGroup group;
	private double size = 0.8;
	
	public Toggler(Rectangle rectangle) {
		rect = rectangle;
	}
	
	public Toggler(Rectangle rectangle, TogglerGroup group) {
		this(rectangle);
		this.group = group;
		group.addToggler(this);
	}
	
	public void setSize(double newSize) {
		size = newSize;
	}
	
	@Override
	public void draw() {
		float radius = (float) (Math.min(rect.width(), rect.height()) * size);
		
		parent.stroke(0, 200, 0);
		parent.fill(0, selected ? 200 : 0, 0, 100);
		
		parent.ellipseMode(PGraphics.CENTER);
		parent.ellipse(rect.centerX(), rect.centerY(), radius, radius);
	}
	
	public boolean selected() {
		return selected;
	}
	
	void deselect() {
		selected = false;
	}
	
	@Override
	public void mousePressed() {
		if (rect.isInside(parent.mouseX, parent.mouseY)) {
			if (group != null) {
				if (selected) {
					return;
				} else {
					group.deselectAll();
					selected = true;
				}
			} else {
				selected = !selected;
			}
		}
    }
	
}
