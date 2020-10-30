package br.com.juliogriebeler.pingapp.util;

import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class AppProperties {

    private static final Logger LOGGER = LogManager.getLogger(AppProperties.class);
    private static AppProperties instance = null;
    private Properties properties;

    private AppProperties() throws IOException {
        properties = new Properties();
        loadResourceProperties();
    }

    protected void loadResourceProperties() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(AppConstants.APP_CONFIG_FILE)) {
            properties.load(is);
        } catch (IOException ex) {
            LOGGER.error("Error loading resource appProperties: {}", ex.getMessage());
            throw new IOException(ex);
        }
    }

    public static AppProperties getInstance() throws IOException {
        if (null == instance) {
            instance = new AppProperties();
        }
        return instance;
    }

    public String getValue(String key) throws PropertyNotFoundException {
        return Optional.of(properties.getProperty(key))
                .orElseThrow(() ->
                        new PropertyNotFoundException("Property not found on appProperties file")
                );
    }

}
