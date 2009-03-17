package vector;


public final class Vector extends GenericVector {
	
	/* Constructors */
	
	public Vector() {
		super();
	}
	
	public Vector(GenericVector other) {
		super (other);
	}
	
	public Vector(double x, double y, double z) {
		super(x, y, z);
	}
	
	public Vector(double length, Angle angle) {
		fromSphericCoords(length, angle);
	}
	
	/* Public operations */
	
	public void fromSphericCoords(double length, Angle angle) {
		Vector tmp = new Vector(angle); // Take temporary copy
		tmp.scale(length);
		copyFrom(tmp);
	}
	
	public void add(Vector other) {
		plus(other);
	}
	
	public void subtract(Vector other) {
		minus(other);
	}
	
	public double length() {
		return absolute();
	}
	
	public Angle angle() {
		return new Angle(this);
	}
	
	public void scale(double factor) {
		super.scale(factor);
	}
	
	public void clamp(double min, double max) {
		if (absolute() == 0.0) {
			// Do nothing TODO
		} else if (absolute() < min) {
			scale(min / absolute());
		} else if (absolute() > max) {
			scale(max / absolute());
		}
	}
	
	public void reset() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	public void print () {
		System.out.println("X: " + x + ", Y: " + y + ", Z: " + z);
	}
	
	
	public double distance (Vector other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2)); 
	}
	
	public double dotProduct (Vector other) {
		return (x * other.x + y * other.y + z * other.z);
	}
	
	public void crossProduct (Vector v1, Vector v2) {
		x = v1.y * v2.z - v1.z * v2.y;
		y = v1.z * v2.x - v1.x * v2.z;
		z = v1.x * v2.y - v1.y * v2.x;
	}
}
