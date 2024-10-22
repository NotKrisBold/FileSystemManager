package ch.supsi.fsci.client.model;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizationModel implements LocalizationHandler {

    private static LocalizationModel instance;

    private boolean initialized;

    private String bundleName;

    private Locale locale;

    private ResourceBundle translations;


    // protected constructor
    protected LocalizationModel() {
        this.initialized = false;
    }

    // singleton construction method
    public static LocalizationModel getInstance() {
        if (instance == null) {
            instance = new LocalizationModel();
        }

        return instance;
    }


    @Override
    public void init(String bundleName, Locale locale) {
        if (bundleName == null || bundleName.isEmpty() || locale == null) {
            throw new IllegalArgumentException();
        }

        this.bundleName = bundleName;
        this.locale = locale;
        this.translations = ResourceBundle.getBundle(bundleName, locale);
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public String getBundleName() {
        if (!isInitialized()) {
            throw new IllegalStateException("LocalizationModel is not properly initialized.");
        }
        return this.bundleName;
    }

    @Override
    public Locale getLocale() {
        if (!isInitialized()) {
            throw new IllegalStateException("LocalizationModel is not properly initialized.");
        }
        return this.locale;
    }

    @Override
    public ResourceBundle getResourceBundle() {
        if (!isInitialized()) {
            throw new IllegalStateException("LocalizationModel is not properly initialized.");
        }
        return this.translations;
    }

    public ResourceBundle getTranslations() {
        if (!isInitialized()) {
            throw new IllegalStateException("LocalizationModel is not properly initialized.");
        }
        return translations;
    }

    @Override
    public String localize(String key) {
        String translation;

        if (key == null || key.isEmpty()) {
            return "";
        }

        try {
            translation = translations.getString(key);

        } catch (MissingResourceException e) {
            translation = key;
        }

        return translation;
    }
}
