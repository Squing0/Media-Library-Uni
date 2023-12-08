package FileManagement;

import GUI.FileObserver;
import MediaManagement.MediaLibrary;

import java.awt.*;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.io.IOException;
import static java.nio.file.StandardWatchEventKinds.*;

public class FileManager implements Runnable{
    private String folderLocation = "";
    private String libraryPath = "";
    private FileObserver observer;

    public FileManager(FileObserver o){
        observer = o;
    }

    public FileManager(){
    }
    public synchronized void watchFolder() throws IOException { //Made synchronized just to make sure

        try {
            Path myDir = Paths.get(folderLocation);
            WatchService watcher = myDir.getFileSystem().newWatchService();

            myDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
            WatchKey key = watcher.take();

            while (key != null){
                if(Thread.interrupted()){
                    break;
                }

                List<WatchEvent<?>> events = key.pollEvents();

                    for (WatchEvent event : events) {
                        if (event.kind() == ENTRY_CREATE) {
                            Path createdFilePath = myDir.resolve((Path) event.context());

                            Search search = Search.getInstance();
                            search.typeVerify(createdFilePath.toString().replace("\\", "/"), libraryPath);

                            observer.onFileChanged(createdFilePath);
                        }

                        if(event.kind() == ENTRY_DELETE){
                            Path deletedFilePath = myDir.resolve((Path) event.context());

                            String fullPath = deletedFilePath.toString();
                            String[] slashes = fullPath.split("\\\\");  //used as backslashes were present
                            String nameAndFormat = slashes[slashes.length - 1];

                            // split using dot and use last instance in case name also has dots in it
                            String[] dots = nameAndFormat.split("\\.");
                            String name = dots[0];
                            String format = dots[dots.length - 1];

                            MediaLibrary library = new MediaLibrary();
                            library.deleteMediaItem(libraryPath, name, format);

                            observer.onFileChanged(deletedFilePath);
                        }
                }

                boolean verified = key.reset();
                if(!verified){  // If key isn't valid then directory in accessible so break loop
                    break;
                }
            }
        }
        catch (InterruptedException e){
            System.out.println("File watcher has been interrupted");
        }
    }

    public void openMediaItem(String fl){
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();

            File file = new File(fl);

            try{
                desktop.open(file);
            }
            catch (IOException e){
                System.out.println("There was an error handling the file!");
            }
        }
    }
    public void createLibraryFile(String fl, String name){
        MediaLibrary library = new MediaLibrary(fl, name);

        library.writeLibraryToJson(library, fl);
    }
    public void deleteFile(String folderLocation, String fileName){
        File dir = new File(folderLocation);
        File[] files = dir.listFiles();
        String path;

        for(File file: files){
            path = file.getAbsolutePath().replace("\\", "/");
            if (path.endsWith(fileName)){
                file.delete();
            }
        }
    }

    /**
     * Creates a simple 'media file' for information
     * to be retrieved from.
     * @param fl the file location of the media file.
     */
    public void createMediaFileBasic(String fl)  {  // Should I make parameter names more consistent
        RandomAccessFile file;

        try {
            file = new RandomAccessFile(fl, "rw");
            file.close();
        } catch (FileNotFoundException e) {
            String s1 = String.format("File at (%s) does not exist!", fl);
            System.out.println(s1);
        }
        catch (IOException e) {
            String s1 = String.format("File at (%s) was not able to be closed.", fl);
            System.out.println(s1);
        }
    }
    public void setFolderLocation(String folderLocation) {this.folderLocation = folderLocation;}
    public void setLibraryPath(String libraryPath){this.libraryPath = libraryPath;}

    @Override
    public void run() {
        try {
            watchFolder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
