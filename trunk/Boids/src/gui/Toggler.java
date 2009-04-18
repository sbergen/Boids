package gui;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Toggler extends Widget {

	private Rectangle rect;
	private boolean selected = false;
	
	public Toggler(Rectangle rectangle) {
		rect = rectangle;
	}
	
	public void draw() {
		float radius = (float) (Math.min(rect.width(), rect.height()) * 0.8);
		
		parent.stroke(0, 200, 0);
		parent.fill(0, selected ? 200 : 0, 0, 100);
		
		parent.ellipseMode(PGraphics.CENTER);
		parent.ellipse(rect.centerX(), rect.centerY(), radius, radius);
	}
	
	public boolean selected() {
		return selected;
	}
	
	public void mousePressed() {
		if (rect.isInside(parent.mouseX, parent.mouseY)) {
			selected = !selected;
		}
    }
	
}
