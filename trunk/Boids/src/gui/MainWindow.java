/**
 * Main GUI Window.
 */
package gui;

import javax.swing.JFrame;


/**
 * @author sbergen
 *
 */
public class MainWindow {
	
	/**
	 * @param args standard argument parameter
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Boids");
	    SimpleView3D view = new SimpleView3D();
	    frame.getContentPane().add(view);
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setSize(1200, 800);
	    frame.setVisible(true);
	    
	    view.init();
	    view.start();
	}

}
