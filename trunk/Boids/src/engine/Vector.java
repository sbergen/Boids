package engine;

class Vector extends GenericVector {
	
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
		return (Vector) super.scale(factor);
	}
}
