package ch.supsi.fsci.client.model;

import java.util.ArrayList;

public interface CmdDescriptionsHandler {
    void init(String propertiesFilePath);

    ArrayList<String> getAllDescriptionKeys();
}
