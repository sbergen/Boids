package fileio;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.io.*;

/** Class for storing key-value-pairs in a CSV format to a file */
public final class PropertyFile {
	
	// File stuff
	
	private String basePath;
	private File file;
	
	BufferedReader iBuf;
	BufferedWriter oBuf;
	
	// Data
	
	private static final String DELIMITER = ";";
	HashMap<String, String> dataMap;
	
	/* Public stuff */
	
	public PropertyFile() {
		dataMap = new HashMap<String, String>();
		
		basePath = System.getProperty("user.home");
		basePath += File.separator;
		basePath += ".boids";
		
		File dir = new File(basePath);
		if(!dir.exists()) {
			if (!dir.mkdir()) {
				throw new Error("foo");
			}
		}
		
		basePath += File.separator;
	}
	
	@Override
	public void finalize() {
		close();
	}
	
	/** Gets thrown if the data is invalid (contains newline or delimiter) */
	@SuppressWarnings("serial")
	static public class InvalidDataException extends Exception { /* Nothing required */ }
	
	/** Add key-value-pair to data */
	public void addData(String key, String value) throws InvalidDataException {
		
		if (key.contains(DELIMITER) || value.contains(DELIMITER) ||
				key.contains("\n") || value.contains("\n")) {
			throw new InvalidDataException();
		}
		
		dataMap.put(key, value);
	}
	
	/** Get's thrown if the data is not found */
	@SuppressWarnings("serial")
	static public class NotFoundException extends Exception {  /* Nothing required */ }
	
	/** gets a data value based on key */
	public String getData(String key) throws NotFoundException {
		if (!dataMap.containsKey(key)) {
			throw new NotFoundException();
		}
		return dataMap.get(key);
	}
	
	/** Saves stored data to file */
	public void save(String filename) throws IOException {
		if(!open(Mode.Output, filename)) {
			close();
			throw new IOException();
		}
		
		Set<String> keys = dataMap.keySet();
		for(String key : keys) {
			String data = key + DELIMITER + dataMap.get(key) + '\n';
			oBuf.write(data);
		}
		close();
	}
	
	/** loads data in file */
	public void load(String filename) throws IOException {
		dataMap.clear();
		
		if(!open(Mode.Input, filename)) {
			close();
			throw new IOException();
		}
		
		String line;
		while((line = iBuf.readLine()) != null) {
			String data[] = line.split(DELIMITER);
			dataMap.put(data[0], data[1]);
		}
		close();
	}
	
	/* Private stuff */
	
	private enum Mode {
		Input,
		Output
	}
	
	private boolean open(Mode mode, String filename) {
		if(!close()) {
			return false;
		}
		
		file = new File(basePath + filename);
		switch(mode) {
			case Input:
				if(!file.canRead()) { return false; }
				try {
					iBuf = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e) {
					return false;
				}
				break;
				
			case Output:
				if(!file.exists()) {
					try {
						file.createNewFile();
					} catch(IOException e) {
						return false;
					}
				}
				
				if(!file.canWrite()) { return false; }
				try {
					oBuf = new BufferedWriter(new FileWriter(file));
				} catch(IOException e) {
					return false;
				}
				break;
		}
		
		return true;
	}
	
	private boolean close() {
		boolean ret = true;
		
		if (oBuf != null) {
			try {
				oBuf.close();
				oBuf = null;
			} catch(IOException e) {
				ret = false;
			}
		}
		
		if (iBuf != null) {
			try {
				iBuf.close();
				iBuf = null;
			} catch(IOException e) {
				ret = false;
			}
		}
		
		return ret;
	}
	
}
