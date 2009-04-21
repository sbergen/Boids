package vector;

/** A vector in 3D space */
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
	
	@Override
	public void scale(double factor) {
		super.scale(factor);
	}
	
	@Override
	public void negate() {
		super.negate();
	}
	
	@Override
	public void normalize() {
		super.normalize();
	}
	
	public void to2D () {
		z = 0.0;
	}
	
	public void clamp(double min, double max) {
		if (absolute() == 0.0) {
			copyFrom(random(min));
		} else if (absolute() < min) {
			scale(min / absolute());
		} else if (absolute() > max) {
			scale(max / absolute());
		}
	}
	
	/** Scales vector so that the length on the x-y-plane is limited */
	public boolean limitXYLength(double length) {
		double factor = length / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if(factor < 1.0) {
			scale(factor);
			return true;
		}
		return false;
	}
	
	@Override
	public void reset() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	public double distance (Vector other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2)); 
	}
	
	public double dotProduct (GenericVector other) {
		return (x * other.x + y * other.y + z * other.z);
	}
	
	public void crossProduct (GenericVector v1, GenericVector v2) {
		x = v1.y * v2.z - v1.z * v2.y;
		y = v1.z * v2.x - v1.x * v2.z;
		z = v1.x * v2.y - v1.y * v2.x;
	}
	
	/** Returns a random vector with given length */
	public static Vector random(double length) {
		Vector v = new Vector(Math.random(), Math.random(), Math.random());
		v.clamp(length, length);
		return v;
	}
	
	/** Prints out vector to System.out (for debugging) */
	public void print () {
		System.out.println("X: " + x + ", Y: " + y + ", Z: " + z);
	}
}
