package ch.supsi.fsci.client.model;
import java.util.Locale;
import java.util.ResourceBundle;

public interface LocalizationHandler {

    void init(String bundleName, Locale locale);

    String getBundleName();

    Locale getLocale();

    ResourceBundle getResourceBundle();

    String localize(String key);

    public boolean isInitialized();

}
