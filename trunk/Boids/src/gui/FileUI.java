package gui;

import java.util.LinkedList;
import processing.core.PGraphics;

import engine.SimulationRules;

/** GUI component for saving and loading settings from a file */
final class FileUI extends Widget {
	
	private SimulationRules rules;
	private Rectangle saveRect;
	private Rectangle loadRect;
	private TogglerGroup togglerGroup;
	private LinkedList<Toggler> togglers;
	private boolean hasLoaded;
	
	public FileUI(Rectangle rectangle, SimulationRules rules) {
		this.rules = rules;
		togglerGroup = new TogglerGroup();
		togglers = new LinkedList<Toggler>();
		Rectangle togglerRect;
		
		final int numTogglers = 5;
		final int togglerWidth = rectangle.width() / numTogglers;
		for(int i = 0; i < numTogglers; i++) {
			togglerRect = new Rectangle(rectangle.left() + i * togglerWidth, rectangle.top(),
					                    togglerWidth, togglerWidth);
			Toggler t = new Toggler(togglerRect, togglerGroup);
			t.setSize(0.5);
			togglers.add(t);
		}
		
		final int padding = 5;
		final int buttonTop = rectangle.top() + togglerWidth + padding;
		final int buttonWidth = (rectangle.width() - 3 * padding) / 2;
		final int buttonHeight = togglerWidth;
		saveRect = new Rectangle(rectangle.left() + padding, buttonTop,
				                 buttonWidth, buttonHeight);
		loadRect = new Rectangle(rectangle.left() + 2 * padding + buttonWidth, buttonTop,
                                 buttonWidth, buttonHeight);
	}
	
	public void draw() {
		for(Toggler t : togglers) {
			t.draw();
		}
		
		parent.noFill();
		parent.stroke(255, 200);
		parent.rect(saveRect.left(), saveRect.top(), saveRect.width(), saveRect.height());
		parent.rect(loadRect.left(), loadRect.top(), loadRect.width(), loadRect.height());
		
		parent.fill(255, 200);
		parent.textAlign(PGraphics.CENTER, PGraphics.CENTER);
		parent.text("Save", saveRect.left() + saveRect.width() / 2, saveRect.top() + saveRect.height() / 2);
		parent.text("Load", loadRect.left() + loadRect.width() / 2, loadRect.top() + loadRect.height() / 2);
	}
	
	/// Returns whether or not a load has happened since this function was last called
	public boolean hasLoaded() {
		boolean ret = hasLoaded;
		hasLoaded = false;
		return ret;
	}
	
	@Override
	public void mousePressed() {
		if(saveRect.isInside(parent.mouseX, parent.mouseY)) {
			rules.saveToFile(getFilename());
		} else if(loadRect.isInside(parent.mouseX, parent.mouseY)) {
			rules.loadFromFile(getFilename());
			hasLoaded = true;
		}
	}
	
	private String getFilename() {
		return "setting" + togglerGroup.getActive() + ".rules";
	}
	
}
