package appium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
    private static String getPropertiesPath() {
        String evn = System.getProperty("env");
        String propertiesFileName = "config-%s.properties";
        if (evn == null) {
            return String.format(propertiesFileName, "dev");
        } else {
            return String.format(propertiesFileName, evn.toLowerCase());
        }
    }

    public static String getPropertiesValue(String key) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = Utils.class.getClassLoader().getResourceAsStream(getPropertiesPath());
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (inputStream != null)
                {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
