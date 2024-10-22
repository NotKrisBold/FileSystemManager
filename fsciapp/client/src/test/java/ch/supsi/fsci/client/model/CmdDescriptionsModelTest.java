package ch.supsi.fsci.client.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CmdDescriptionsModelTest {
    private static final String key1 = "testKey1";
    public static final String key2 = "testKey2";
    private static final String key3 = "testKey3";

    private static final String help = "help.";

    private static final ArrayList<String> expected = new ArrayList<>();

    private static final String propertiesFilePath = "available_commands.properties";

    private CmdDescriptionsModel cmdDescriptionsModel;

    @BeforeAll
    static void start(){
        expected.add(help + key1);
        expected.add(help + key2);
        expected.add(help + key3);
    }

    @BeforeEach
    void setUp() {
        cmdDescriptionsModel = new CmdDescriptionsModel();
    }

    @Test
    void testInitWithValidFile() {
        cmdDescriptionsModel.init(propertiesFilePath);
        ArrayList<String> keys = cmdDescriptionsModel.getAllDescriptionKeys();

        for(String s : expected)
            assertTrue(keys.contains(s));
    }

    @Test
    void testInitWithNullFilePath() {
        assertThrows(IllegalArgumentException.class, () -> cmdDescriptionsModel.init(null));
    }

    @Test
    void testInitWithEmptyFilePath() {
        assertThrows(IllegalArgumentException.class, () -> cmdDescriptionsModel.init(""));
    }

    @Test
    void testInitWithInvalidFile() {
        assertThrows(NullPointerException.class, () -> cmdDescriptionsModel.init("invalid/path"));
    }

    @Test
    void testGetAllDescriptionKeysBeforeInit() {
        ArrayList<String> keys = cmdDescriptionsModel.getAllDescriptionKeys();
        assertEquals(0, keys.size());
    }

    @Test
    void testGetAllDescriptionKeysAfterInitWithInvalidFile() {
        assertThrows(NullPointerException.class, () -> cmdDescriptionsModel.init("invalid/path"));
        ArrayList<String> keys = cmdDescriptionsModel.getAllDescriptionKeys();
        assertEquals(new ArrayList<String>(), keys);
    }
}
