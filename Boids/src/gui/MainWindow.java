/**
 * Main GUI Window.
 */
package gui;

import javax.swing.JFrame;


import engine.Engine; 

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
	    View3D view = new View3D();
	    frame.getContentPane().add(view);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setSize(300, 200);
	    frame.setVisible(true);
	}

}
