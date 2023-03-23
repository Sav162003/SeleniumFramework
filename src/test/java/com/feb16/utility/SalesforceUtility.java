package com.feb16.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SalesforceUtility {
	
	public static String readPropertyData(String key) throws IOException {
		String curdir = System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(new File(curdir+"/src/test/resources/testData.properties "));
		Properties ob = new Properties();
		ob.load(fis);
		String value = ob.getProperty(key);
		return value;
	}

}
