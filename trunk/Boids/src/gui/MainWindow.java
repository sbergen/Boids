/**
 * Main GUI Window.
 */
package gui;

import javax.swing.JFrame;


/** Makes a window to contain the applet and starts it (also provides main()) */
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
        frame.setUndecorated(true);
		
	    SimpleView3D view = new SimpleView3D(width, height);
	    frame.getContentPane().add(view);
	    frame.pack();
	    frame.setSize(width, height);
	    frame.setResizable(false);
	    frame.setVisible(true);
	    
	    view.init();
	    view.start();
	}

}
