package com.kashenok.rentcar.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class MessageManager.
 */
public class MessageManager {

    private final static String PROP_FILE = "resources.content";
    private ResourceBundle BUNDLE;
    private static MessageManager instance = new MessageManager();
    Locale current = Locale.getDefault();

    /**
     * Instantiates a new MessageManager.
     */
    public MessageManager() {
        BUNDLE = ResourceBundle.getBundle(PROP_FILE, current);
    }

    /**
     * Gets the single instance of ConfigurationManager.
     *
     * @return single instance of ConfigurationManager
     */
    public static MessageManager getInstance() {
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
