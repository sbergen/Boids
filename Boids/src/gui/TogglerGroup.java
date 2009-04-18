package gui;

import java.util.LinkedList;

public final class TogglerGroup {
	
	private LinkedList<Toggler> togglers;
	
	public TogglerGroup() {
		togglers = new LinkedList<Toggler>();
	}
	
	public void addToggler(Toggler toggler) {
		togglers.add(toggler);
	}
	
	public void deselectAll() {
		for(Toggler t : togglers) {
			t.deselect();
		}
	}
	
}
