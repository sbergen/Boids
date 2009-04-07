package gui;

import processing.core.*;
import java.util.ArrayList;

abstract class Scroller extends PApplet {

	/* Actions */
	
	public interface ScrollActionListener {
		void handleSrollAction(double newPosition);
	}
	
	private ArrayList<ScrollActionListener> listeners;
	
	/* Data */
	
	private double position;
	private int min;
	private int max;
	private int bWidth;
	private boolean active;
	
	/* Interface */
	
	public abstract void actualDraw();
	
	/* Constructor */
	
	public Scroller(int minCoordinate, int maxCoordinate, int barWidth) {
		bWidth = barWidth;
		min = minCoordinate + barWidth / 2;
		max = maxCoordinate - barWidth / 2;
		active = false;
		listeners = new ArrayList<ScrollActionListener>();
	}
	
	/* Protected stuff */
	
	protected int getBarStart() {
		return (int) (getCenter() - bWidth / 2);
	}
	
	protected int getBarEnd() {
		return (int) (getCenter() + bWidth / 2);
	}
	
	protected void setPosition(int coordinate) {
		
		if (!(active && mousePressed)) { return; }
		
		position = ((double)(coordinate - min) / max);
		position = Math.max(position, 0.0);
		position = Math.min(position, 100.0);
		
		if (active) {
			for (ScrollActionListener listener : listeners) {
				listener.handleSrollAction(position);
			}
		}
	}
	
	/* PApplet overrides */
	
	@Override
	public void draw() {
		if (mouseX >= 0 && mouseX < width && mouseY >= 0 && mouseY < height) {
			active = true;
		} else {
			active = false;
		}
		
		actualDraw();
	}
	
	/* Private stuff */
	
	private double getCenter() {
		return min + position * (max - min);
	}
	
	// Suppress warnings...
	private static final long serialVersionUID = 274719132536765332L;
}
