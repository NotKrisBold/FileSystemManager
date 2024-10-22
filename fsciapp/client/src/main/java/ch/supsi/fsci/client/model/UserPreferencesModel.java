package ch.supsi.fsci.client.model;

import ch.supsi.fsci.client.MainFx;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.prefs.Preferences;

// Class that manages user preferences.
// Just add keys and default preferencs as lines (key=value) in the dedicated properties file, and they will be managed through this class.
public class UserPreferencesModel implements UserPreferencesHandler {
    private static UserPreferencesModel instance;

    private Preferences preferences;
    private final Properties default_preferences = new Properties();
    private final List<String> preferencesKeys = new ArrayList<>();

    private boolean initialized;

    // Singleton object construction
    private UserPreferencesModel() {
        this.initialized = false;
    }

    public static UserPreferencesModel getInstance() {
        if (instance == null) {
            instance = new UserPreferencesModel();
        }
        return instance;
    }

    // Load default preferences from properties file
    @Override
    public void init(String propertiesFilePath, Class<?> userNodeForPackage) {
        if (propertiesFilePath == null || propertiesFilePath.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (userNodeForPackage == null) {
            throw new IllegalArgumentException();
        }

        // Assign a class to be used as a node for storing preferences
        preferences = Preferences.userNodeForPackage(userNodeForPackage);

        // Load default preferences and keys
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(propertiesFilePath)) {
            default_preferences.load(inputStream); // Load default preferences from default_preferences.properties
            loadKeys(); // Save preferences keys
            initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public Properties getDefault_preferences() {
        return default_preferences;
    }

    public List<String> getPreferencesKeys() {
        return preferencesKeys;
    }

    // Get a preference
    @Override
    public String getPreference(String preference) {
        if (!isInitialized()) {
            throw new IllegalStateException("UserPreferencesModel is not properly initialized.");
        }

        if (preference == null || preference.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!preferencesKeys.contains(preference)) {
            throw new IllegalArgumentException("Preference key does not exist.");
        }

        String defaultValue = default_preferences.getProperty(preference);

        return preferences.get(preference, defaultValue);
    }

    // Set a preference
    @Override
    public void setPreference(String preference, String value) {
        if (!isInitialized()) {
            throw new IllegalStateException("UserPreferencesModel is not properly initialized.");
        }

        if (preference == null || preference.isEmpty()|| value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!preferencesKeys.contains(preference)) {
            throw new IllegalArgumentException("Preference key does not exist.");
        }

        preferences.put(preference, value);
    }

    // Reset a preference
    @Override
    public void resetPreference(String preference) {
        if (!isInitialized()) {
            throw new IllegalStateException("UserPreferencesModel is not properly initialized.");
        }

        if (preference == null || preference.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!preferencesKeys.contains(preference)) {
            throw new IllegalArgumentException("Preference key does not exist.");
        }

        preferences.remove(preference);
    }

    // Reset all preferences
    public void resetAllPreferences() {
        preferencesKeys.forEach(this::resetPreference);
    }

    // Load all preferences keys
    private void loadKeys() {
        Set<Object> keys = default_preferences.keySet();
        keys.forEach(o -> preferencesKeys.add((String) o));
    }
}
