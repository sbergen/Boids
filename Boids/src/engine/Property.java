package engine;

/**
 * Class for storing properties.
 * Takes care of synchronization,
 * helps in saving and loading to files,
 * has a default value,
 * makes it easy to use with scrollers...
 */
public class Property<T> {
	
	private final String identifier;
	private final T defaultValue;
	private T value;
	
	public Property(String identifier, T defaultValue) {
		this.defaultValue = defaultValue;
		this.identifier = identifier;
		value = defaultValue;
	}
	
	public synchronized T value() {
		return value;
	}
	
	public synchronized void setValue(T value) {
		this.value = value;
	}
	
	public String identifier() {
		return identifier;
	}
	
	public T defaultValue() {
		return defaultValue;
	}
	
}