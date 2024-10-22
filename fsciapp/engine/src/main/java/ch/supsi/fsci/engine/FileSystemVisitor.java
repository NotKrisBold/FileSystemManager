package ch.supsi.fsci.engine;

public interface FileSystemVisitor {
    void visit(DirectoryElement dir);
}