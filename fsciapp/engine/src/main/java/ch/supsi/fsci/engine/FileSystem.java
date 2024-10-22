package ch.supsi.fsci.engine;


import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSystem {
    private static FileSystem instance;
    private final DirectoryElement root;
    private DirectoryElement current;

    protected FileSystem() {
        this.root = new DirectoryElement("/", null);
        this.current = root;
    }

    // singleton construction method
    public static FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public DirectoryElement getRoot() {
        return root;
    }

    public DirectoryElement getCurrent() {
        return current;
    }

    public void setCurrent(DirectoryElement dir) {
        this.current = dir;
    }

    public String ls() {
        StringBuilder stringBuilder = new StringBuilder();
        if (current.getChildren() != null) {
            for (FileSystemElement element : current.getChildren()) {
                stringBuilder.append(element.getName()).append(" ");
            }
        }
        return stringBuilder.toString().strip();
    }

    public String pwd() {
        return getCurrent().getPath();
    }

    public DirectoryElement cd(String path) {
        String[] splitPath = path.split("/");
        String firstElement = splitPath.length > 0 ? splitPath[0] : "/";

        if (path.startsWith(firstElement)) {
            DirectoryElement dir = findDirectory(path);
            if (dir != null)
                setCurrent(dir);
            return dir;
        } else {
            for (FileSystemElement element : current.getChildren()) {
                if (element.getName().equals(firstElement)) {
                    DirectoryElement dir = findDirectory(path);
                    if (dir != null)
                        setCurrent(dir);
                    return dir;
                }
            }
        }
        return null;
    }

    public boolean rm(String path) {
        DirectoryElement dir = findDirectory(path);
        if (dir != null && dir != root && dir != current) {
            dir.getParent().remove(dir);
            return true;
        } else return false;
    }

    public boolean mv(String origin, String destination) {
        DirectoryElement sourceDir = findDirectory(origin);
        DirectoryElement targetDir = findDirectory(destination);
        if (sourceDir == null || targetDir == null)
            return false;
        if (sourceDir.getName().equals(targetDir.getName()))
            return false;
        for (FileSystemElement dir : targetDir.getChildren()) {
            if (dir.getName().equals(sourceDir.getName()))
                return false;
        }
        if (sourceDir != root && sourceDir != current) {
            sourceDir.getParent().remove(sourceDir);
            sourceDir.setParent(targetDir);
            targetDir.add(sourceDir);
            return true;
        }
        return false;
    }

    private DirectoryElement findDirectory(String path) {
        FindDirectoryVisitor visitor = new FindDirectoryVisitor(path);
        if (path.startsWith(root.getName())) {
            root.accept(visitor);
        } else {
            current.accept(visitor);
        }
        return visitor.getFoundDirectoryElement();
    }

    public DirectoryElement mkdir(String path) {
        // Non permette di creare directory con nome /
        if (path.equals(root.getName())) return null;
        // Non permette di creare directory con lo stesso nome nella stessa directory
        DirectoryElement dir = findDirectory(path);
        if(dir != null)
            return null;
        //non permetto la creazione di una stessa cartella sotto current directory
        if (current.getChildren() != null) {
            for (FileSystemElement elem : current.getChildren()) {
                if (elem.getName().equals(path))
                    return null;
            }
        }
        StringTokenizer st = new StringTokenizer(path, "/");
        ArrayList<String> pathSplit = new ArrayList<>();
        while (st.hasMoreTokens()) {
            pathSplit.add(st.nextToken());
        }
        String name = pathSplit.get(pathSplit.size() - 1);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < pathSplit.size(); i++) {
            stringBuilder.append("/");
            stringBuilder.append(pathSplit.get(i - 1));
        }

        DirectoryElement currentToComeBackTo = getCurrent(); // Directory a cui ritornare
        DirectoryElement dircd = cd(stringBuilder.toString());
        if (dircd != null || pathSplit.size() == 1) {
            DirectoryElement newDir = new DirectoryElement(name, current);
            current.add(newDir);
            setCurrent(currentToComeBackTo); // Ritorniamo nella directory iniziale
            return newDir;
        }
        return null;

    }
}
