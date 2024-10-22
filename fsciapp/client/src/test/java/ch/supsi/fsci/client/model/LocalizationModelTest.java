package ch.supsi.fsci.client.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationModelTest {
    private static LocalizationModel localizationModel;
    private static String bundleNameTest;
    private static String languageTagTest;
    private static Locale localeTest;
    private static ResourceBundle resourceBundleTest;


    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Reset singleton instance before each test
        Field field = LocalizationModel.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
        localizationModel = LocalizationModel.getInstance();
        bundleNameTest = "i18n.translations";
        languageTagTest = "en-UK";
        localeTest = Locale.forLanguageTag(languageTagTest);
        resourceBundleTest = ResourceBundle.getBundle(bundleNameTest, localeTest);
    }

    @AfterAll
    public static void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field field = LocalizationModel.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    void isInitialized() {
        assertFalse(localizationModel.isInitialized());

        localizationModel.init(bundleNameTest, localeTest);
        assertTrue(localizationModel.isInitialized());
    }

    @Test
    void getInstance() {
        assertEquals(localizationModel, LocalizationModel.getInstance());
    }

    @Test
    void init0() {
        assertThrows(IllegalArgumentException.class, () -> localizationModel.init(null, localeTest));
    }

    @Test
    void init1() {
        assertThrows(IllegalArgumentException.class, () -> localizationModel.init("", localeTest));
    }


    @Test
    void init2() {
        assertThrows(IllegalArgumentException.class, () -> localizationModel.init(bundleNameTest, null));
    }

    @Test
    void getBundleName() {
        assertThrows(IllegalStateException.class, () -> localizationModel.getBundleName());

        localizationModel.init(bundleNameTest, localeTest);
        assertEquals(bundleNameTest, localizationModel.getBundleName());
    }

    @Test
    void getLocale() {
        assertThrows(IllegalStateException.class, () -> localizationModel.getLocale());

        localizationModel.init(bundleNameTest, localeTest);
        assertEquals(localeTest, localizationModel.getLocale());
    }

    @Test
    void getResourceBundle() {
        assertThrows(IllegalStateException.class, () -> localizationModel.getResourceBundle());

        localizationModel.init(bundleNameTest, localeTest);
        assertEquals(resourceBundleTest, localizationModel.getResourceBundle());
    }

    @Test
    void localize() {
        localizationModel.init(bundleNameTest, localeTest);
        String testKey = "test.key";
        assertEquals(resourceBundleTest.getString(testKey), localizationModel.localize(testKey));
    }

}