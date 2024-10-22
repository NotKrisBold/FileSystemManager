package ch.supsi.fsci.client.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class UserPreferencesModelTest {
    private static UserPreferencesModel userPreferencesModel;
    private static String propertiesPath;
    private static Properties expectedDefaultPreferences;
    private static final List<String> expectedPreferencesKeys = new ArrayList<>();


    @BeforeAll
    public static void setUp() {
        propertiesPath = "default_preferences_test.properties";

        // Define expected variables
        expectedDefaultPreferences = new Properties();
        expectedDefaultPreferences.put("key1", "defValue1");
        expectedDefaultPreferences.put("key2", "defValue2");
        expectedDefaultPreferences.put("key3", "defValue3");

        expectedPreferencesKeys.addAll(List.of("key1", "key2", "key3"));
    }

    @BeforeEach
    public void resetSingletonBefore() throws NoSuchFieldException, IllegalAccessException {
        // Reset singleton and get new instance
        Field field = UserPreferencesModel.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
        userPreferencesModel = UserPreferencesModel.getInstance();
    }

    @AfterAll
    public static void resetSingletonAfter() throws NoSuchFieldException, IllegalAccessException {
        Field field = UserPreferencesModel.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    void getInstance() {
        assertEquals(userPreferencesModel, UserPreferencesModel.getInstance());
    }

    @Test
    void isInitialized() throws NoSuchFieldException, IllegalAccessException {
        assertFalse(userPreferencesModel.isInitialized());
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);
        assertTrue(userPreferencesModel.isInitialized());
    }

    @Test
    void init0() {
        // Test invalid arguments
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.init("", UserPreferencesModelTest.class));
    }

    @Test
    void init1() {
        // Test invalid arguments
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.init(null, UserPreferencesModelTest.class));
    }

    @Test
    void init4() {
        // Test invalid arguments
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.init(propertiesPath,null));
    }

    @Test
    void init2() {
        // Test functionality
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);
        assertEquals(expectedDefaultPreferences, userPreferencesModel.getDefault_preferences());
    }

    @Test
    void init3() {
        // Test functionality
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);
        assertEquals(expectedPreferencesKeys, userPreferencesModel.getPreferencesKeys());
    }

    @Test
    void getPreference0() {
        // Test not initialized
        assertThrows(IllegalStateException.class, () -> userPreferencesModel.getPreference("key1"));
    }

    @Test
    void getPreference1() {
        // Test invalid arguments
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);
        userPreferencesModel.resetAllPreferences(); // Reset all preferences before test
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.getPreference(""));
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.getPreference("invalidKey"));
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.getPreference(null));
    }

    @Test
    void getPreference2() {
        // Test functionality
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);
        userPreferencesModel.resetAllPreferences();
        String key = "key1";
        assertEquals(expectedDefaultPreferences.getProperty(key), userPreferencesModel.getPreference(key));
        String newValue = "newValue";
        userPreferencesModel.setPreference(key, newValue);
        assertEquals(newValue, userPreferencesModel.getPreference(key));
    }

    @Test
    void setPreference0() {
        // Test invalid arguments
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.getPreference(""));
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.getPreference("invalidKey"));
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.getPreference(null));
    }

    @Test
    void setPreference1() {
        // Test not initialized
        assertThrows(IllegalStateException.class, () -> userPreferencesModel.setPreference("key1", "value"));
    }

    @Test
    void setPreference2() {
        // Test functionality
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);

        userPreferencesModel.setPreference("key1", "newValue1");
        userPreferencesModel.setPreference("key2", "newValue2");
        userPreferencesModel.setPreference("key3", "newValue3");

        assertEquals("newValue1", userPreferencesModel.getPreference("key1"));
        assertEquals("newValue2", userPreferencesModel.getPreference("key2"));
        assertEquals("newValue3", userPreferencesModel.getPreference("key3"));
    }

    @Test
    void resetPreference0() {
        // Test invalid arguments
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.resetPreference(""));
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.resetPreference("invalidKey"));
        assertThrows(IllegalArgumentException.class, () -> userPreferencesModel.resetPreference(null));
    }

    @Test
    void resetPreference1() {
        // Test not initialized
        assertThrows(IllegalStateException.class, () -> userPreferencesModel.resetPreference("key1"));
    }

    @Test
    void resetPreference2() {
        // Test functionalities
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);

        userPreferencesModel.setPreference("key1", "newValue1");
        userPreferencesModel.setPreference("key2", "newValue2");
        userPreferencesModel.setPreference("key3", "newValue3");

        userPreferencesModel.resetPreference("key1");
        userPreferencesModel.resetPreference("key2");
        userPreferencesModel.resetPreference("key3");

        assertEquals("defValue1", userPreferencesModel.getPreference("key1"));
        assertEquals("defValue2", userPreferencesModel.getPreference("key2"));
        assertEquals("defValue3", userPreferencesModel.getPreference("key3"));
    }

    @Test
    void resetAllPreferences() {
        userPreferencesModel.init(propertiesPath, UserPreferencesModelTest.class);

        userPreferencesModel.setPreference("key1", "newValue1");
        userPreferencesModel.setPreference("key2", "newValue2");
        userPreferencesModel.setPreference("key3", "newValue3");

        userPreferencesModel.resetAllPreferences();

        assertEquals("defValue1", userPreferencesModel.getPreference("key1"));
        assertEquals("defValue2", userPreferencesModel.getPreference("key2"));
        assertEquals("defValue3", userPreferencesModel.getPreference("key3"));
    }
}