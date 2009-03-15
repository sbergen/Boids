package vector;

public final class VectorBase {
	private Vector left; // x
	private Vector up;    // y
	private Vector fwd;   // z
	
	public VectorBase() {
		left = new Vector(1, 0, 0);
		up = new Vector(0, 1, 0);
		fwd = new Vector(0, 0, 1);
	}
	
	public VectorBase (Vector newUp, Vector newFwd) {
		left = new Vector();
		up = new Vector();
		fwd = new Vector();
		
		set(newUp, newFwd);
	}
	
	public void set (Vector newFwd, Vector newUp) {
		fwd.copyFrom(newFwd);
		up.copyFrom(newUp);
		
		// Cross-product
		left.x = fwd.y * up.z - fwd.z * up.y;
		left.y = fwd.x * up.z - fwd.z * up.x;
		left.z = fwd.x * up.y - fwd.y * up.x;
		
		fwd.normalize();
		up.normalize();
		left.normalize();
	}
	
	/**
	 * Translate global vector to local base.
	 * x = left, y = up, z = forward
	 * @param vec vector to translate
	 * @return vec in local coordinates
	 */
	public Vector globalize(Vector vec) {
		Vector ret = new Vector();
		
		ret.x = vec.x * left.x + vec.y * up.x + vec.z * fwd.x;
		ret.y = vec.x * left.y + vec.y * up.y + vec.z * fwd.y;
		ret.z = vec.x * left.z + vec.y * up.z + vec.z * fwd.z;
		
		return ret;
	}
	
	/**
	 * Translate local vector to global base.
	 * x = left, y = up, z = forward
	 * @param vec vector to translate
	 * @return vec in local coordinates
	 */
	public Vector localize(Vector vec) {
		Vector ret = new Vector();
		
		ret.x = Vector.dotProduct(vec, left);
		ret.y = Vector.dotProduct(vec, up);
		ret.z = Vector.dotProduct(vec, fwd);
		
		return ret;
	}

	
}
