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
		Angle tmp = new Angle(angle); // Take temporary copy
		tmp.scale(length);
		copyFrom(tmp);
	}
	
	/* Public operations */
	
	public Vector add(Vector other) {
		return (Vector) plus(other);
	}
	
	public Vector subtract(Vector other) {
		return (Vector) minus(other);
	}
	
	public double length() {
		return absolute();
	}
	
	public Angle angle() {
		return new Angle(this);
	}
	
	public Vector scale(double factor) {
		super.scale(factor);
		return this;
	}
	
	public Vector clamp(double min, double max) {
		if (absolute() == 0.0) {
			// Do nothing TODO
		} else if (absolute() < min) {
			scale(min / absolute());
		} else if (absolute() > max) {
			scale(max / absolute());
		}
		
		return this;
	}
	
	public void reset() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	public void print () {
		System.out.println("X: " + x + ", Y: " + y + ", Z: " + z);
	}
	
	public void randomize(double limit) {
		System.out.println ("doing random");
		
		x = ((Math.random() > 0.5) ? -1.0 : 1.0) * 2.0 * Math.random() * limit;
		y = ((Math.random() > 0.5) ? -1.0 : 1.0) * 2.0 * Math.random() * limit;
		z = ((Math.random() > 0.5) ? -1.0 : 1.0) * 2.0 * Math.random() * limit;
		clamp(limit, limit);
	}
	
	/* Static methods */
	
	public static double distance (Vector v1, Vector v2) {
		return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2) + Math.pow(v1.z - v2.z, 2)); 
	}
	
	public static double dotProduct (Vector v1, Vector v2) {
		return (v1.x * v2.x + v1.y * v2.y + v1.z * v2.z);
	}
	
	public static void crossProduct (Vector v1, Vector v2, Vector result) {
		result.x = v1.y * v2.z - v1.z * v2.y;
		result.y = v1.z * v2.x - v1.x * v2.z;
		result.z = v1.x * v2.y - v1.y * v2.x;
	}
}
