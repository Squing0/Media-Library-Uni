package GUI;

import java.nio.file.Path;

public interface FileObserver {
    /**
     *
     * @param fl File location of file from folder being watched.
     */
    void onFileChanged(Path fl);
}
