package ch.supsi.fsci.client.model;

public interface UserPreferencesHandler {

    void init(String propertiesFilePath, Class<?> userNodeForPackage);

    String getPreference(String preference);

    void setPreference(String preference, String value);

    void resetPreference(String preference);

    void resetAllPreferences();
}
