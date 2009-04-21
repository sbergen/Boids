package engine;

import fileio.PropertyFile;
import java.util.LinkedList;

/** Simulation rules for boids to follow */
public final class SimulationRules {
	
	private LinkedList<Property> properties;
	
	private static final double defaultMinSpeed = 10.0;
	private static final double defaultMaxSpeed = 40.0;
	private static final double defaultMinForce = 1.0;
	private static final double defaultMaxForce = 60.0;
	private static final double defaultMaxTurn = 2.0;
	private static final double defaultMass = 5.0;
	private static final double defaultSeparationFactor = 20.0;
	private static final double defaultCohesionFactor = 0.2;
	private static final double defaultAlignmentFactor = 0.1;
	private static final double defaultPerceptionRange = 150.0;
	
	// Maximum and minimum speed (m/s)
	public Property minSpeed;
	public Property maxSpeed;
	
	// Maximum and minimum force (N)
	public Property minForce;
	public Property maxForce;
	
	// Maximum turn force (N, excluding forward/back force)
	public Property maxTurn;
	
	// Mass (kg)
	public Property mass;
	
	// Simulation parameters
	public Property separationFactor;
	public Property cohesionFactor;
	public Property alignmentFactor;
	
	public Property perceptionRange;
	
	public SimulationRules() {
		properties = new LinkedList<Property>();
		properties.add(minSpeed = new Property("minSpeed", defaultMinSpeed));
		properties.add(maxSpeed = new Property("maxSpeed", defaultMaxSpeed));
		properties.add(minForce = new Property("minForce", defaultMinForce));
		properties.add(maxForce = new Property("maxForce", defaultMaxForce));
		properties.add(maxTurn = new Property("maxTurn", defaultMaxTurn));
		properties.add(mass = new Property("mass", defaultMass));
		properties.add(separationFactor = new Property("separationFactor", defaultSeparationFactor));
		properties.add(cohesionFactor = new Property("cohesionFactor", defaultCohesionFactor));
		properties.add(alignmentFactor = new Property("alignmentFactor", defaultAlignmentFactor));
		properties.add(perceptionRange = new Property("perceptionRange", defaultPerceptionRange));
	}
	
	public boolean saveToFile(String filename) {
		PropertyFile file = new PropertyFile();
		
		for (Property p : properties) {
			try {
				file.addData(p.identifier(), Double.toString(p.value()));
			} catch (PropertyFile.InvalidDataException e) {
				throw new Error("Programming error: Invalid identifier in SimulationRules property");
			}
		}
		
		try {
			file.save(filename);
		} catch (java.io.IOException e) {
			return false;
		}
		
		return true;
	}
	
	public boolean loadFromFile(String filename) {
		PropertyFile file = new PropertyFile();
		try {
			file.load(filename);
		} catch (java.io.IOException e) {
			return false;
		}
		
		for (Property p : properties) {
			try {
				p.setValue(Double.parseDouble(file.getData(p.identifier())));
			} catch (PropertyFile.NotFoundException e) {
				p.setValue(p.defaultValue());
			}
		}
		
		return true;
	}
}
