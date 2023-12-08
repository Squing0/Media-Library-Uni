package GUI;

import java.nio.file.Path;

/**
 * File Observer interface
 * @author Lyle Patterson
 */
public interface FileObserver {
    /**
     *
     * @param fl File location of file from folder being watched.
     */
    void onFileChanged(Path fl);
}
