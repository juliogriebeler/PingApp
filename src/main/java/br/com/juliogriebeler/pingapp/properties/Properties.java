package br.com.juliogriebeler.pingapp.properties;

import br.com.juliogriebeler.pingapp.enumeration.Constants;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Properties {

    private static final Logger LOGGER = LogManager.getLogger(Properties.class);
    private static Properties instance = null;
    private java.util.Properties properties;

    protected Properties() {
        properties = new java.util.Properties();
        loadResourceProperties();
    }

    protected void loadResourceProperties() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(Constants.APP_CONFIG_FILE)) {
            properties.load(is);
        } catch (IOException ex) {
            LOGGER.error("Error loading resource properties: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Properties getInstance() {
        if (null == instance) {
            instance = new Properties();
        }
        return instance;
    }

    public String getValue(String key) throws PropertyNotFoundException {
        return Optional.of(properties.getProperty(key))
                .orElseThrow(() ->
                        new PropertyNotFoundException("Property not found on properties file")
                );
    }

}
