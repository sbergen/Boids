package vector;

import static org.junit.Assert.*;

import org.junit.Test;

import vector.Vector;


public class VectorTest {

	@Test
	public void testAdd() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(3, 4, 5);
		
		v1.add(v2);
		
		assertEquals("Addition x coordinate", 4, v1.getX());
		assertEquals("Addition y coordinate", 6, v1.getY());
		assertEquals("Addition z coordinate", 8, v1.getZ());
	}

	@Test
	public void testSubtract() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(3, 4, 5);
		
		v1.subtract(v2);
		
		assertEquals("Subtraction x coordinate", -2, v1.getX());
		assertEquals("Subtraction y coordinate", -2, v1.getY());
		assertEquals("Subtraction z coordinate", -2, v1.getZ());
	}

	@Test
	public void testLength() {
		Vector v1 = new Vector(1, 1, 1);
		Vector v2 = new Vector(v1);
		v2.scale(3.0);
		
		assertEquals("Vector length (1)", Math.sqrt(3.0), v1.length());
		assertEquals("Vector length (2)", 3.0 * v1.length(), v2.length());
	}

	@Test
	public void testAngle() {
		Vector v1 = new Vector();
		Vector v2 = new Vector(1, 0, 0);
		Vector v3 = new Vector(-1, 1, 1);
		
		assertEquals("Vector zenith (1)", 0.0, v1.angle().zenith());
		assertEquals("Vector azimuth (1)", 0.0, v1.angle().azimuth());
		
		assertEquals("Vector zenith (2)", Math.PI / 2.0, v2.angle().zenith());
		assertEquals("Vector azimuth (2)", 0.0, v2.angle().azimuth());
		
		assertEquals("Vector zenith (3)", Math.PI / 4.0, v3.angle().zenith());
		assertEquals("Vector azimuth (3)", 3.0 * Math.PI / 4.0, v3.angle().azimuth());
	}
}
