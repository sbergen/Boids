package gui;

/** Provides a boid drawing function to View3D */
final class SimpleView3D extends View3D {

	public SimpleView3D (int width, int height) {
		super (width, height);
	}
	
	@Override
	protected void drawBoid() {
		beginShape(TRIANGLES);
    	vertex(-10, 5);
    	vertex(10, 0);
    	vertex(-10, -5);
    	endShape();
    	
    	pushMatrix();
    	translate(0,0,1);
    	fill(0,255,0);
    	beginShape(TRIANGLES);
    	vertex(-10, 5);
    	vertex(10, 0);
    	vertex(-10, -5);
    	endShape();
    	fill(255);
    	popMatrix();
	}
	
	// Suppress warnings...
	private static final long serialVersionUID = 2428240699286020508L;
}
