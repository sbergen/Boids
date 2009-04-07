package gui;

final class Rectangle {
	
	// Top-left
	private int tlx;
	private int tly;
	
	// Bottom-right
	private int brx;
	private int bry;
	
	public Rectangle(int x, int y, int width, int height) {
		tlx = x;
		tly = y;
		brx = x + width;
		bry = y + height;
	}
	
	public boolean isInside(int x, int y) {
		return (x >= tlx && x < brx &&
				y >= tly && y < bry);
	}
	
	public int width() {
		return (brx - tlx);
	}
	
	public int height() {
		return (bry - tly);
	}
	
	public int top() {
		return tly;
	}
	
	public int bottom() {
		return bry;
	}

	public int left() {
		return tlx;
	}

	public int right() {
		return brx;
	}
	
	public int centerX() {
		return (tlx + brx) / 2;
	}
	
	public int centerY() {
		return (tly + bry) / 2;
	}
}
