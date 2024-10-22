package ch.supsi.fsci.client.command;

import ch.supsi.fsci.client.model.FileSystemHandler;

import java.lang.reflect.Method;

abstract class AbstractNoArgsCommandTest<T extends Command & ActsOnFileSystem> extends AbstractCommandTest<T> {
    @Override
    protected Method fileSystemHandlerMethod() throws NoSuchMethodException {
        return FileSystemHandler.class.getMethod(getCommandKey());
    }

    abstract String getCommandKey();
}
