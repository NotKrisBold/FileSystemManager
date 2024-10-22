package ch.supsi.fsci.client.model;

import ch.supsi.fsci.engine.DirectoryElement;

public interface FileSystemHandler {
    public DirectoryElement getRoot();

    public DirectoryElement getCurrent();

    public void setCurrent(DirectoryElement dir);

    public void ls();

    public void rm(String path,String inputCommand);

    public void mv(String source,String target,String inputCommand);
    public void pwd();
    public void cd(String path);

    public void mkdir(String name, String inputCommand);
}
