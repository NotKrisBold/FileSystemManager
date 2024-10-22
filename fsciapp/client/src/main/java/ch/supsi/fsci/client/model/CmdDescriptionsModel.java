package ch.supsi.fsci.client.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class CmdDescriptionsModel implements CmdDescriptionsHandler{
    private final ArrayList<String> keys = new ArrayList<>();

    @Override
    public void init(String propertiesFilePath){
        if(propertiesFilePath == null || propertiesFilePath.isEmpty()){
            throw new IllegalArgumentException();
        }

        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(propertiesFilePath)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (String key : properties.stringPropertyNames())
                keys.add("help." + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getAllDescriptionKeys() {
        return keys;
    }
}
