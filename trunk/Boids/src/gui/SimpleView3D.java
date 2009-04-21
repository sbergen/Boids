package gui;

/** Provides a boid drawing function to View3D */
final class SimpleView3D extends View3D {

	public SimpleView3D (int width, int height) {
		super (width, height);
	}
	
	@Override
	protected void drawBoid() {
		
		final int xOffset = 10;
		final int yOffset = 5;
		final float zOffset =(float) 0.1;
		
		fill(255);
		beginShape(TRIANGLES);
    	vertex(-xOffset, yOffset);
    	vertex(xOffset, 0);
    	vertex(-xOffset, -yOffset);
    	endShape();
    	
    	pushMatrix();
    	translate(0, 0, zOffset);
    	fill(0, 255, 0);
    	beginShape(TRIANGLES);
    	vertex(-xOffset, yOffset);
    	vertex(xOffset, 0);
    	vertex(-xOffset, -yOffset);
    	endShape();
    	popMatrix();
	}
	
	// Suppress warnings...
	private static final long serialVersionUID = 2428240699286020508L;
}
