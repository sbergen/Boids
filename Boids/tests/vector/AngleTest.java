package vector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AngleTest {

	@Test
	public void testContructor() {
		Angle a1 = new Angle (3 * Math.PI, 1.0);
		assertEquals("Azimuth wrapping in constructor", Math.PI, a1.azimuth());
		
		Angle a2 = new Angle (1.0, 3 * Math.PI / 2.0);
		assertEquals("Zenith wrapping in constructor", Math.PI / 2.0, a2.zenith());
		
		Angle a3 = new Angle (3 * Math.PI / 2.0, 3 * Math.PI / 2.0);
		assertEquals("Zenith and azimuth wrapping in constructor (zenith)", Math.PI / 2.0, a3.zenith());
		assertEquals("Zenith and azimuth wrapping in constructor (azimuth)", Math.PI / 2.0, a3.azimuth());
	}
	
	@Test
	public void testTurn() {
		Angle a1 = new Angle(1, 2);
		Angle a2 = new Angle(2, 1);
		
		a1.turn(a2);
		assertEquals("turn azimuth (1)", 3.0, a1.azimuth());
		assertEquals("turn zenith (1)", 3.0, a1.zenith());
		
		a1.turn(a2);
		assertEquals("turn azimuth (2)", 5.0 - Math.PI, a1.azimuth());
		assertEquals("turn zenith (2)", 2.0 * Math.PI - 4.0, a1.zenith());
	}

	@Test
	public void testLimitZenith() {
		Angle a1 = new Angle(1.0, 2.0);
		
		a1.limitZenith(2.0);
		
		assertEquals("zenith limiting should not effect azimuth (1)", 1.0, a1.azimuth());
		assertEquals("zenith limiting should not effect zenith", 2.0, a1.zenith());
		
		a1.limitZenith(1.0);
		
		assertEquals("zenith limiting should not effect azimuth (2)", 1.0, a1.azimuth());
		assertEquals("zenith limiting should effect zenith", 1.0, a1.zenith());
		
		Angle a2 = new Angle(new Vector(0,0,-1));
		assertEquals("zenith for -k should be Pi", Math.PI, a2.zenith());
		
		a2.limitZenith(Math.PI / 2.0);
		assertEquals("zenith for -k limited to Pi/2 should be 0", 0, a2.zenith());
	}
	
	@Test
	public void testZenithValues() {
		Angle a1 = new Angle(new Vector(-1.4, 1.7, -4.6));
		System.out.println(a1.zenith());
	}
}
