package gui;

import vector.Angle;
import vector.Vector;

public final class SimpleView3D extends View3D {

	// Suppress warnings...
	private static final long serialVersionUID = 2428240699286020508L;

	@Override
	protected void drawBoid(Vector position, Angle angle) {
		pushMatrix();
		translate(position, angle);
		drawObject();
    	popMatrix();
	}
	
	private void translate(Vector position, Angle angle) {
		final double scale = 2.0;
    	translate(
    			(float) (position.getX() * scale),
    			(float) (position.getY() * scale),
    			(float) (position.getZ() * -scale));
    	
    	double rotZ = angle.azimuth();
    	if (rotZ < 0) {
    		rotZ += 2.0 * Math.PI;
    	}
    	rotZ += Math.PI / 2.0;
    	
    	rotateZ((float) rotZ);
    	rotateY((float) (angle.zenith() - Math.PI / 2.0));
	}
	
	private void drawObject() {
		beginShape(TRIANGLES);
    	vertex(0, 55);
    	vertex(10, 0);
    	vertex(20, 55);
    	endShape();
	}

}
