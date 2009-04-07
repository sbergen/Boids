package gui;

import processing.core.*;

class Scroller {
	
	public enum Direction {
		Vertical,
		Horizontal
	};
	
	/* Data */
	
	private PApplet parent;
	private Rectangle rect;
	private Direction dir;
	
	private double position;
	private int min;
	private int max;
	private int bWidth;
	private boolean active;
	
	/* Constructor */
	
	public Scroller(PApplet parentApplet, Rectangle rectangle, Direction direction, int barWidth) {
		parent = parentApplet;
		rect = rectangle;
		dir = direction;
		bWidth = barWidth;
		active = false;
		position = 0.5;
		
		switch (dir) {
		  case Horizontal:
			min = rect.left() + barWidth / 2;
			max = rect.right() - barWidth / 2;
			break;
		  case Vertical:
			min = rect.top() + barWidth / 2;
			max = rect.bottom() - barWidth / 2;
			break;
		}
	}
	
	/* Public stuff */
	
	public void draw() {
		if (rect.isInside(parent.mouseX, parent.mouseY) && parent.mousePressed) {
			active = true;
		} else if (active && parent.mousePressed) {
			active = true;
		} else {
			active = false;
		}
		
		if (active) {
			setPosition();
		}
		
		parent.noFill();
		parent.stroke(255, 100);
		parent.rect(rect.left(), rect.top(), rect.width() - 1, rect.height() - 1);
		
		parent.fill(100, 100);
		switch (dir) {
		  case Horizontal:
			parent.rect(getBarStart(), rect.top() + 1, bWidth - 2, rect.height() - 3);
			break;
		  case Vertical:
			  parent.rect(rect.left(), getBarStart(), rect.width() - 2, bWidth - 3);
			break;
		}
	}
	
	public double getPosition() {
		return position;
	}
	
	/* Private stuff */
	
	private int getBarStart() {
		return (int) ((min + position * (max - min)) - bWidth / 2);
	}
	
	private void setPosition() {
		int coordinate = 0;
		
		switch (dir) {
		  case Horizontal:
			coordinate = parent.mouseX;
			break;
		  case Vertical:
			coordinate = parent.mouseY;
			break;
		}
		
		position = ((double)(coordinate - min) / (max - min));
		position = Math.max(position, 0.0);
		position = Math.min(position, 1.0);
	}
	
	// Suppress warnings...
	private static final long serialVersionUID = 274719132536765332L;
}
