package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigRead {
	public static Properties readConfigProperties(String filename) throws IOException {
		filename = filename.trim();
		Properties configProperties = new Properties();
		InputStream inStream = new FileInputStream(filename);
		configProperties.load(inStream);
		return configProperties;
	}

}
