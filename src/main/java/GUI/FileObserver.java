package GUI;

import java.nio.file.Path;

public interface FileObserver {
    void onFileChanged(Path fl);
}
