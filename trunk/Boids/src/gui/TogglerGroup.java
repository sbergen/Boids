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
	
	// Get index of active toggler
	public int getActive() {
		int i = 0;
		for(Toggler t : togglers) {
			if(t.selected()) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
}
