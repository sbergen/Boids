package gui;

final class HScroller extends Scroller {

	private int width;
	private int height;
	private int barWidth;
	
	public HScroller(int _width, int _height, int _barWidth) {
		super(0, _width, _barWidth);
		
		width = _width;
		height = _height;
		barWidth = _barWidth;
	}
	
	@Override
	public void setup() {
		size(width, height, OPENGL);
	}

	@Override
	public void actualDraw() {
		
		setPosition(mouseX);
		
		background(50);
		noFill();
		stroke(255);
		rect(0, 1, width - 1, height - 1);
		
		fill(200);
		rect(getBarStart(), 1, barWidth, height - 2);
	}
}
