package gui;

import java.util.LinkedList;

import processing.core.PApplet;

/** Handles stuff common for all widgets. Currently only applet and mouse presses... */
abstract class Widget {
	
	/// global list of all widgets
	private static final LinkedList<Widget> widgets = new LinkedList<Widget>();
	
	/// Parent applet common for all widgets
	protected static PApplet parent;
	
	/** Common constructor registers widget */
	public Widget() {
		widgets.add(this);
	}
	
	/** Sets common applet for all widgets */
	public static void setApplet(PApplet parentApplet) {
		parent = parentApplet;
	}
	
	/** This must be called from applet! */
	static void globalMousePressed() {
		for(Widget w : widgets) {
			w.mousePressed();
		}
	}
	
	/** Override this to handle mouse presses */
	protected void mousePressed() {
		// empty default implementation
	}
	
	public abstract void draw();
}
