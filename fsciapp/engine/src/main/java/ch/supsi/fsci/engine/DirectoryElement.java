package ch.supsi.fsci.engine;

import java.util.ArrayList;
import java.util.List;

public class DirectoryElement implements FileSystemElement {
    private String name;
    private List<FileSystemElement> children;
    private DirectoryElement parent;

    public DirectoryElement(String name, DirectoryElement parent) {
        this.name = name;
        children = new ArrayList<>();
        this.parent = parent;
    }

    @Override
    public void setParent(DirectoryElement parent) {
        this.parent = parent;
    }

    @Override
    public DirectoryElement getParent() {
        return parent;
    }

    public void add(FileSystemElement element) {
        if (element != null) {
            element.setParent(this);
            children.add(element);
        }
    }

    public void remove(FileSystemElement element) {
        if (element != null)
            children.remove(element);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
        for (FileSystemElement element : children) {
            element.accept(visitor);
        }
    }


    public List<FileSystemElement> getChildren() {
        return children;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getPath() {
        if (parent == null) {
            return name;
        } else {
            String parentPath = parent.getPath();
            return parentPath.endsWith("/") ? parentPath + name : parentPath + "/" + name;
        }
    }


}

