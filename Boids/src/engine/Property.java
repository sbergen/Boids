package engine;

/**
 * Class for storing properties.
 * Takes care of synchronization,
 * helps in saving and loading to files,
 * has a default value,
 * makes it easy to use with scrollers...
 */
public class Property {
	
	private final String identifier;
	private final double defaultValue;
	private double value;
	
	public Property(String identifier, double defaultValue) {
		this.defaultValue = defaultValue;
		this.identifier = identifier;
		value = defaultValue;
	}
	
	public synchronized double value() {
		return value;
	}
	
	public synchronized void setValue(double value) {
		this.value = value;
	}
	
	public String identifier() {
		return identifier;
	}
	
	public double defaultValue() {
		return defaultValue;
	}
	
}