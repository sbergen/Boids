package gui;

public final class SimpleView3D extends View3D {

	// Suppress warnings...
	private static final long serialVersionUID = 2428240699286020508L;

	@Override
	protected void drawBoid() {
		beginShape(TRIANGLES);
    	vertex(0, 55);
    	vertex(10, 0);
    	vertex(20, 55);
    	endShape();
	}
}
