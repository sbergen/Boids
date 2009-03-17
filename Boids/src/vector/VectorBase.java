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
		
		left.crossProduct(up, fwd);		
		up.crossProduct(fwd, left);
		
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
	public void globalize(Vector vec) {
		double newX = vec.x * left.x + vec.y * up.x + vec.z * fwd.x;
		double newY = vec.x * left.y + vec.y * up.y + vec.z * fwd.y;
		double newZ = vec.x * left.z + vec.y * up.z + vec.z * fwd.z;
		
		vec.x = newX;
		vec.y = newY;
		vec.z = newZ;
	}
	
	/**
	 * Translate local vector to global base.
	 * x = left, y = up, z = forward
	 * @param vec vector to translate
	 * @return vec in local coordinates
	 */
	public void localize(Vector vec) {
		double newX = vec.dotProduct(left);
		double newY = vec.dotProduct(up);
		double newZ = vec.dotProduct(fwd);
		
		vec.x = newX;
		vec.y = newY;
		vec.z = newZ;
	}
}
