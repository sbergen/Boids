package gui;

/** A generic scroller. Can be horizontal or vertical */ 
class Scroller extends Widget {
	
	public enum Direction {
		Vertical,
		Horizontal
	}
	
	/* Data */
	
	private Rectangle rect;
	private Direction dir;
	
	static boolean globalActive = false;
	private boolean active = false;
	
	protected double position = 0.5;
	private int min;
	private int max;
	private int bWidth;
	
	/* Constructor */
	
	public Scroller(Rectangle rectangle, Direction direction, int barWidth) {
		rect = rectangle;
		dir = direction;
		bWidth = barWidth;
		
		switch (dir) {
		  case Horizontal:
			min = rect.left() + barWidth / 2;
			max = rect.right() - barWidth / 2;
			break;
		  case Vertical:
			min = rect.top() + barWidth / 2;
			max = rect.bottom() - barWidth / 2;
			break;
		}
	}
	
	/* Public stuff */
	
	@Override
	public void draw() {
		if (rect.isInside(parent.mouseX, parent.mouseY) && 
			parent.mousePressed && !globalActive) {
			active = globalActive = true;
		} else if (active && parent.mousePressed) {
			active = true;
		} else if (active) {
			active = globalActive = false;
		}
		
		if (active) {
			setPosition();
		}
		
		parent.noFill();
		parent.stroke(DEFAULT_WHITE, DEFAULT_OPACITY);
		parent.rect(rect.left(), rect.top(), rect.width() - 1, rect.height() - 1);
		
		parent.fill(DEFAULT_GRAY, DEFAULT_OPACITY);
		switch (dir) {
		  case Horizontal:
			parent.rect(getBarStart(), rect.top() + 1, bWidth - 2, rect.height() - 3);
			break;
		  case Vertical:
			  parent.rect(rect.left(), getBarStart(), rect.width() - 2, bWidth - 3);
			break;
		}
	}
	
	/** Gets position of scroller (in the range of 0.0 ... 1.0) */
	public double getPosition() {
		return position;
	}
	
	/* Private stuff */
	
	private int getBarStart() {
		return (int) ((min + position * (max - min)) - (double) bWidth / 2);
	}
	
	private void setPosition() {
		int coordinate = 0;
		
		switch (dir) {
		  case Horizontal:
			coordinate = parent.mouseX;
			break;
		  case Vertical:
			coordinate = parent.mouseY;
			break;
		}
		
		position = ((double)(coordinate - min) / (max - min));
		position = Math.max(position, 0.0);
		position = Math.min(position, 1.0);
	}
	
	// Suppress warnings...
	private static final long serialVersionUID = 274719132536765332L;
}
