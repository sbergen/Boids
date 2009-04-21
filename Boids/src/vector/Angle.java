package vector;

/** A class representing an angle in 3D space (polar coordinates) */
public final class Angle extends GenericVector {
	
	/* Constructors */
	
	public Angle() {
		x = 0.0;
		y = 0.0;
		z = 1.0;
	}
	
	public Angle(GenericVector other) {
		super (other);
		normalize();
	}
	
	public Angle(double azimuth, double zenith) {
		x = Math.sin(zenith) * Math.cos(azimuth);
		y = Math.sin(zenith) * Math.sin(azimuth);
		z = Math.cos(zenith);
	}
	
	/* Public methods */
	
	public double azimuth() {
		return atan2(y, x);
	}
	
	public double zenith() {
		return atan2(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)), z);
	}
	
	public void turn(Angle other) {
		Angle tmp = new Angle (azimuth() + other.azimuth(), zenith() + other.zenith());
		copyFrom(tmp);
	}
	
	/**
	 * Limits zenith of angle
	 * @param radians limit zenith to this 
	 * @return true if angle was limited
	 */
	public boolean limitZenith(double radians) {
		assert (radians <= Math.PI);
		
		if (zenith() > radians) {
			if (x == 0.0 && y == 0.0) { // Special case...
				z = 1.0;
				return true;
			}
			double factor = radians / zenith();
			Angle tmp = new Angle(azimuth(), factor * zenith());
			copyFrom(tmp);
			
			return true;
		}
		
		return false;
	}
	
	public void reset() {
		x = 0.0;
		y = 0.0;
		z = 1.0;
	}
	
	/* Private helpers */
	
	/**
	 * atan, that returns in the range [0, 2Pi]
	 */
	double atan2(double y, double x) {
		double angle = Math.atan2(y, x);
		if (angle < 0) {
			angle += 2.0 * Math.PI;
		}
		return angle;
	}
}
