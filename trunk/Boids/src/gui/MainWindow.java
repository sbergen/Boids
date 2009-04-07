/**
 * Main GUI Window.
 */
package gui;

import javax.swing.*;
import java.awt.*;


/**
 * @author sbergen
 *
 */
public class MainWindow {
	
	/**
	 * @param args standard argument parameter
	 */
	public static void main(String[] args) {
		
		int width = 1200;
		int height = 800;
		
		if (args.length >= 2) {
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				// Do nothing
			}
		}
		
		JFrame frame = new JFrame("Boids");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Sizes
		
		int scrollerBarSize = 100;
		int scrollerWidth = 50;   
		int viewWidth = width - scrollerWidth;
		int viewHeight = height - scrollerWidth;
		
		HScroller scroller = new HScroller(viewWidth, scrollerWidth, scrollerBarSize);
	    SimpleView3D view = new SimpleView3D(viewWidth, viewHeight);
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.ipadx = 0;
	    c.ipady = 0;
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    frame.getContentPane().add(view, c);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    frame.getContentPane().add(scroller, c);

	    /*try {
	    	Thread.sleep(2000);
	    } catch(InterruptedException e) {
	    	// Do nothing
	    }*/
	    
	    frame.pack();
	    frame.setSize(width, height);
	    frame.setVisible(true);
	    
	    view.init();
	    scroller.init();
	    
	    scroller.start();
	    view.start();
	}

}
