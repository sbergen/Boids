package vector;


abstract class GenericVector {
	
	protected double x;
	protected double y;
	protected double z;
	
	/* Constructors */
	
	protected GenericVector() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}
	
	protected GenericVector(double _x, double _y, double _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	
	protected GenericVector(GenericVector other) {
		copyFrom(other);
	}

	/* Public getters */
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	/* Public copyer */
	
	public void copyFrom (GenericVector other) {
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	/* Protected operations */
	
	protected abstract void reset();
	
	protected double absolute() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	protected void scale(double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}
	
	protected void plus(GenericVector other) {
		x += other.x;
		y += other.y;
		z += other.z;
	}
	
	protected void minus(GenericVector other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
	}
	
	protected void negate() {
		x = -x;
		y = -y;
		z = -z;
	}
	
	protected void normalize () {
		if (absolute() != 0.0) {
			scale (1.0 / absolute());
		} else {
			reset();
		}
	}
}
