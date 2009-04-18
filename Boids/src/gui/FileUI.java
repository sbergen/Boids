package gui;

import java.util.LinkedList;

final class FileUI extends Widget {
	
	private Rectangle rect;
	private TogglerGroup togglerGroup;
	private LinkedList<Toggler> togglers;
	
	public FileUI(Rectangle rectangle) {
		rect = rectangle;
		
		togglerGroup = new TogglerGroup();
		togglers = new LinkedList<Toggler>();
		Rectangle togglerRect;
		
		final int numTogglers = 5;
		final int togglerWidth = rect.width() / numTogglers;
		for(int i = 0; i < numTogglers; i++) {
			togglerRect = new Rectangle(rect.left() + i * togglerWidth, rect.top(),
					                    togglerWidth, togglerWidth);
			Toggler t = new Toggler(togglerRect, togglerGroup);
			t.setSize(0.5);
			togglers.add(t);
		}
	}
	
	public void draw() {
		for(Toggler t : togglers) {
			t.draw();
		}
	}
	
}
