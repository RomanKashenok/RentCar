package com.kashenok.rentcar.resource;

import java.util.ResourceBundle;

/**
 * The class ConfigurationManager.
 */
public class ConfigurationManager {

    private final static String PROP_FILE = "resources.configuration";
    private ResourceBundle BUNDLE;
    private static ConfigurationManager instance = new ConfigurationManager();

    /**
     * Instantiates a new ConfigurationManager.
     */
    private ConfigurationManager() {
        BUNDLE = ResourceBundle.getBundle(PROP_FILE);
    }

    /**
     * Gets the single instance of ConfigurationManager.
     *
     * @return single instance of ConfigurationManager
     */
    public static ConfigurationManager getInstance() {
        return instance;
    }

    /**
     * Gets the property.
     *
     * @param key is the key
     * @return the property
     */
    public String getProperty(String key) {
        return BUNDLE.getString(key);
    }

}
