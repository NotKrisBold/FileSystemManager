package ch.supsi.fsci.client.command;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

abstract class AbstractWithArgsCommandTest<T extends Command & ActsOnFileSystem> extends AbstractCommandTest<T> {
    @Test
    // Test isInitialized with invalid argumentList
    abstract void isInitialized2();

    @Test
    // Test the constructor of the class
    abstract void constructor0();

    @Test
    // Test constructor with invalid fields
    abstract void constructor1();
}
