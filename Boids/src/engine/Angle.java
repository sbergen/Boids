package engine;

import java.lang.Math;

class Angle extends GenericVector {
	
	/* Constructors */
	
	public Angle() {
		super();
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
	
	public double steradians() {
		return Math.sqrt(Math.pow(azimuth(), 2) + Math.pow(zenith(), 2));
	}
	
	public Angle difference(Angle other) {
		return new Angle (other.azimuth() - azimuth(), other.zenith() - zenith());
	}
	
	public Angle turn(Angle other) {
		Angle tmp = new Angle (azimuth() + other.azimuth(), zenith() + other.zenith());
		copyFrom(tmp);
		return this;
	}
	
	public Angle limitTo(double steradians) {
		assert (steradians <= (4 * Math.PI));
		
		if (steradians() > steradians) {
			double factor = steradians / steradians();
			Angle tmp = new Angle(factor * azimuth(), factor * zenith());
			copyFrom(tmp);
		}
		
		return this;
	}
	
	/* Private helpers */
	
	private void normalize () {
		if (absolute() != 0) {
			scale (1.0 / absolute());
		}
	}
	
	private double azimuth() {
		return Math.atan2(y, x);
	}
	
	private double zenith() {
		return Math.atan2(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)), z);
	}
}
