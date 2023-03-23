package com.feb16.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtility {

	private FileInputStream stream = null;
	private Properties propertyFile = null;

	public Properties loadFile(String filename) {
		Properties propertyFile = new Properties();
		String PropertyFilePath = null;
		switch(filename) {
		case"applicationData.Properties":
			PropertyFilePath = Constants.APPLICATION_PROPERTIES;
			try {
				stream = new FileInputStream(PropertyFilePath);
				propertyFile.load(stream);
			} catch (IOException e) {
				e.printStackTrace();
					}
		}
		this.propertyFile = propertyFile;
		return propertyFile;
	}

	public String getPropertyValue(String key) {
		String value = propertyFile.getProperty(key);
		System.out.println("Property we get from the file is : " + value);
		if(stream!=null) {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}
}
