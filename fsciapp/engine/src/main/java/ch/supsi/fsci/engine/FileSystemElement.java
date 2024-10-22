package ch.supsi.fsci.engine;

public interface FileSystemElement {
    String getName();
    String getPath();

    void accept(FileSystemVisitor visitor);

    void setParent(DirectoryElement dir);
    DirectoryElement getParent();

}