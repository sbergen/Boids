/**
 * Main GUI Window.
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import engine.Engine; 

/**
 * @author sbergen
 *
 */
public class MainWindow {

	static private Engine engine;
	
	/**
	 * @param args standard argument parameter
	 */
	public static void main(String[] args) {
		engine = new Engine();
		
		JFrame frame = new JFrame("Boids");
	    final JLabel label = new JLabel("Hello World");
	    frame.getContentPane().add(label);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setSize(300, 200);
	    frame.setVisible(true);
	}

}
