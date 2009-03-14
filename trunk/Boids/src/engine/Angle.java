package engine;

import java.lang.Math;

class Angle extends GenericVector {
	
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
		return Math.atan2(y, x);
	}
	
	public double zenith() {
		return Math.atan2(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)), z);
	}
	
	public Angle turn(Angle other) {
		Angle tmp = new Angle (azimuth() + other.azimuth(), zenith() + other.zenith());
		copyFrom(tmp);
		return this;
	}
	
	public Angle limitZenith(double radians) {
		assert (radians <= Math.PI);
		
		if (zenith() > radians) {
			double factor = radians / zenith();
			Angle tmp = new Angle(azimuth(), factor * zenith());
			copyFrom(tmp);
		}
		
		return this;
	}
	
	/* Private helpers */
	
	private void normalize () {
		if (absolute() != 0.0) {
			scale (1.0 / absolute());
		} else {
			x = 0.0;
			y = 0.0;
			z = 1.0;
		}
	}
}
