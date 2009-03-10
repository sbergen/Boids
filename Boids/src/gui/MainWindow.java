/**
 * Main GUI Window.
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author sbergen
 *
 */
public class MainWindow {

	/**
	 * @param args standard argument paramter
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Boids");
	    final JLabel label = new JLabel("Hello World");
	    frame.getContentPane().add(label);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setSize(300, 200);
	    frame.setVisible(true);
	}

}
