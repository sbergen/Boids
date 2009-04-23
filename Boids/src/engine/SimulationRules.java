package engine;

import fileio.PropertyFile;
import java.util.LinkedList;

/** Simulation rules for boids to follow */
public final class SimulationRules {
	
	private LinkedList<Property<Double>> properties;
	
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
	public Property<Double> minSpeed;
	public Property<Double> maxSpeed;
	
	// Maximum and minimum force (N)
	public Property<Double> minForce;
	public Property<Double> maxForce;
	
	// Maximum turn force (N, excluding forward/back force)
	public Property<Double> maxTurn;
	
	// Mass (kg)
	public Property<Double> mass;
	
	// Simulation parameters
	public Property<Double> separationFactor;
	public Property<Double> cohesionFactor;
	public Property<Double> alignmentFactor;
	
	public Property<Double> perceptionRange;
	
	public SimulationRules() {
		properties = new LinkedList<Property<Double>>();
		properties.add(minSpeed = new Property<Double>("minSpeed", defaultMinSpeed));
		properties.add(maxSpeed = new Property<Double>("maxSpeed", defaultMaxSpeed));
		properties.add(minForce = new Property<Double>("minForce", defaultMinForce));
		properties.add(maxForce = new Property<Double>("maxForce", defaultMaxForce));
		properties.add(maxTurn = new Property<Double>("maxTurn", defaultMaxTurn));
		properties.add(mass = new Property<Double>("mass", defaultMass));
		properties.add(separationFactor = new Property<Double>("separationFactor", defaultSeparationFactor));
		properties.add(cohesionFactor = new Property<Double>("cohesionFactor", defaultCohesionFactor));
		properties.add(alignmentFactor = new Property<Double>("alignmentFactor", defaultAlignmentFactor));
		properties.add(perceptionRange = new Property<Double>("perceptionRange", defaultPerceptionRange));
	}
	
	public boolean saveToFile(String filename) {
		PropertyFile file = new PropertyFile();
		
		for (Property<Double> p : properties) {
			try {
				file.addData(p.identifier(), p.value().toString());
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
		
		for (Property<Double> p : properties) {
			try {
				p.setValue(Double.parseDouble(file.getData(p.identifier())));
			} catch (PropertyFile.NotFoundException e) {
				p.setValue(p.defaultValue());
			}
		}
		
		return true;
	}
}
