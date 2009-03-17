package vector;

import static org.junit.Assert.*;

import org.junit.Test;


public class VectorBaseTest {
	@Test
	public void testSimpleTranslations() {
		
		VectorBase b = new VectorBase();
		Vector v = new Vector (5, 3, 2);
		Vector vt = new Vector(v);
		b.localize(vt);
		
		assertEquals("no change in vector with default base", 5.0, vt.x);
		assertEquals("no change in vector with default base", 3.0, vt.y);
		assertEquals("no change in vector with default base", 2.0, vt.z);
		
		b.set(new Vector(1,0,0), new Vector(0, 0, 1));
		vt.copyFrom(v);
		b.localize(vt);
		assertEquals("90 degree translation", 3.0, vt.x);
		assertEquals("90 degree translation", 2.0, vt.y);
		assertEquals("90 degree translation", 5.0, vt.z);
		
		b.globalize(vt);
		assertEquals("localize + globalize = original", 5.0, vt.x);
		assertEquals("localize + globalize = original", 3.0, vt.y);
		assertEquals("localize + globalize = original", 2.0, vt.z);
	}
	
	@Test
	public void testComplexTranslations() {
		VectorBase b = new VectorBase(new Vector(3, 5, -2), new Vector(3, -5, 12));
		Vector v = new Vector (5, -14, 45);
		
		Vector vt = new Vector(v);
		b.localize(vt);
		b.globalize(vt);
		
		assertTrue("localize + globalize = original", dEquals(5.0, vt.x));
		assertTrue("localize + globalize = original", dEquals(-14.0, vt.y));
		assertTrue("localize + globalize = original", dEquals(45.0, vt.z));
		
		vt.copyFrom(v);
		b.localize(vt);
		vt.scale(0.5);
		b.globalize(vt);
		vt.scale(2.0);
		assertTrue("localize + globalize + double scaling = original", dEquals(5.0, vt.x));
		assertTrue("localize + globalize + double scaling = original", dEquals(-14.0, vt.y));
		assertTrue("localize + globalize + double scaling = original", dEquals(45.0, vt.z));
	}
	
	private boolean dEquals(double a, double b) {
		return (Math.abs(a - b) < 0.00001);
	}
}
