package vector;

import static org.junit.Assert.*;

import org.junit.Test;


public class VectorBaseTest {
	@Test
	public void testSimpleTranslations() {
		
		VectorBase b = new VectorBase();
		Vector v = new Vector (5, 3, 2);
		Vector vt = b.localize(v);
		
		assertEquals("no change in vector with default base", 5.0, vt.x);
		assertEquals("localize + globalize = original", 3.0, vt.y);
		assertEquals("localize + globalize = original", 2.0, vt.z);
		
		b.set(new Vector(1,0,0), new Vector(0, 0, 1));
		vt = b.localize(v);
		assertEquals("90 degree translation", 3.0, vt.x);
		assertEquals("90 degree translation", 2.0, vt.y);
		assertEquals("90 degree translation", 5.0, vt.z);
		
		vt = b.globalize(vt);
		assertEquals("localize + globalize = original", 5.0, vt.x);
		assertEquals("localize + globalize = original", 3.0, vt.y);
		assertEquals("localize + globalize = original", 2.0, vt.z);
	}
	
	@Test
	public void testComplexTranslations() {
		VectorBase b = new VectorBase(new Vector(3, 5, -2), new Vector(3, -5, 12));
		Vector v = new Vector (5, -14, 45);
		
		Vector vt = b.localize(v);
		vt = b.globalize(vt);
		
		assertTrue("localize + globalize = original", dEquals(5.0, vt.x));
		assertTrue("localize + globalize = original", dEquals(-14.0, vt.y));
		assertTrue("localize + globalize = original", dEquals(45.0, vt.z));
		
		vt = b.localize(v);
		vt.scale(0.5);
		vt = b.globalize(vt);
		vt.scale(2.0);
		assertTrue("localize + globalize + double scaling = original", dEquals(5.0, vt.x));
		assertTrue("localize + globalize + double scaling = original", dEquals(-14.0, vt.y));
		assertTrue("localize + globalize + double scaling = original", dEquals(45.0, vt.z));
	}
	
	private boolean dEquals(double a, double b) {
		return (Math.abs(a - b) < 0.00001);
	}
}
