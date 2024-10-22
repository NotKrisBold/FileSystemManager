package ch.supsi.fsci.engine;

public class FindDirectoryVisitor implements FileSystemVisitor {
    private String searchPath;
    private DirectoryElement foundDirectoryElement;

    public FindDirectoryVisitor(String searchPath) {
        this.searchPath = searchPath.startsWith("/") ? searchPath :
                FileSystem.getInstance().getCurrent().getPath().equals("/") ? FileSystem.getInstance().getCurrent().getPath() +searchPath
                    : FileSystem.getInstance().getCurrent().getPath() + "/" +searchPath;
    }

    public DirectoryElement getFoundDirectoryElement() {
        return foundDirectoryElement;
    }

    @Override
    public void visit(DirectoryElement dir) {
        if (dir.getPath().equals(searchPath)) {
            foundDirectoryElement = dir;
        }
    }
}

