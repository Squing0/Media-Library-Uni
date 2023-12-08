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

/**
 * Handles all actions related to files.
 * @author Lyle Patterson
 */
public class FileManager implements Runnable{
    /** File location of folder */
    private String folderLocation = "";
    /** File path of specific library */
    private String libraryPath = "";
    /** Observer interface used to interact with library page.*/
    private FileObserver observer;

    /**
     * Interface is defined when folder is being watched,
     * @param o File Observer interface.
     */
    public FileManager(FileObserver o){
        observer = o;
    }

    /**
     * Empty constructor for methods unrelated to watching folder.
     */
    public FileManager(){
    }

    /**
     * Watches a folder to see if file is added or deleted
     * and updates library UI accordingly.
     * @throws IOException if there is a problem handling the folder.
     */
    public synchronized void watchFolder() throws IOException { //Made synchronized as is concurrent method

        try {
            Path myDir = Paths.get(folderLocation); // Path used as required to create watcher
            WatchService watcher = myDir.getFileSystem().newWatchService(); // Watches specific directory.

            myDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);    // Specifically checks if file is created or deleted.
            WatchKey key = watcher.take();

            while (key != null){    //Runs as long as object is watched
                if(Thread.interrupted()){   // If thread is interrupted (folder is changed from UI) then loop is broken
                    break;
                }

                List<WatchEvent<?>> events = key.pollEvents();  //list of 4 possible poll events

                    for (WatchEvent event : events) {   //Loops through all 4 and checks for create and delete
                        if (event.kind() == ENTRY_CREATE) {
                            Path createdFilePath = myDir.resolve((Path) event.context());   // gets path from event context

                            // Imports created file to library
                            Search search = Search.getInstance();
                            search.typeVerify(createdFilePath.toString().replace("\\", "/"), libraryPath);

                            observer.onFileChanged(createdFilePath);    //Updates library page UI
                        }

                        if(event.kind() == ENTRY_DELETE){
                            Path deletedFilePath = myDir.resolve((Path) event.context()); // gets path from event context

                            //Gets name and format
                            String fullPath = deletedFilePath.toString();
                            String[] slashes = fullPath.split("\\\\");  //used as backslashes were present
                            String nameAndFormat = slashes[slashes.length - 1];

                            // split using dot and use last instance in case name also has dots in it
                            String[] dots = nameAndFormat.split("\\.");
                            String name = dots[0];
                            String format = dots[dots.length - 1];

                            //Deletes media item from list
                            MediaLibrary library = new MediaLibrary();
                            library.deleteMediaItem(libraryPath, name, format);

                            observer.onFileChanged(deletedFilePath); //Updates library page UI
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

    /**
     * Opens media item in file explorer.
     * @param fl File location of media item.
     */
    public void openMediaItem(String fl){
        if(Desktop.isDesktopSupported()){   //Tests if desktop class is supported on platform used
            Desktop desktop = Desktop.getDesktop();

            File file = new File(fl);

            try{
                desktop.open(file); // Desktop class to open file
            }
            catch (IOException e){
                System.out.println("There was an error handling the file!");
            }
        }
    }

    /**
     * Creates media library file
     * @param fl File location of media library.
     * @param name name of media library.
     */
    public void createLibraryFile(String fl, String name){
        MediaLibrary library = new MediaLibrary(fl, name);

        library.writeLibraryToJson(library, fl);
    }

    /**
     * Deletes either media library or manually created file.
     * @param folderLocation folder to be checked.
     * @param fileName Name of file.
     */
    public void deleteFile(String folderLocation, String fileName){
        File dir = new File(folderLocation);
        File[] files = dir.listFiles(); // Creates array of files in directory.
        String path;

        for(File file: files){
            path = file.getAbsolutePath().replace("\\", "/");
            if (path.endsWith(fileName)){   //Checks if filename is at very end of path so that not all files are deleted.
                file.delete();
            }
        }
    }

    /**
     * Creates a simple 'media file' for information
     * to be retrieved from.
     * @param fl the file location of the media file.
     */
    public void createMediaFile(String fl)  {  // Should I make parameter names more consistent
        RandomAccessFile file;

        try {
            file = new RandomAccessFile(fl, "rw");  //Random access file used to write to file
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

    /**
     * Setter
     * @param folderLocation location of folder to be watched.
     */
    public void setFolderLocation(String folderLocation) {this.folderLocation = folderLocation;}

    /**
     * Setter
     * @param libraryPath Location of library to be watched.
     */
    public void setLibraryPath(String libraryPath){this.libraryPath = libraryPath;}

    /**
     * Run method of runnable interface overridden to call synchronised
     * watch folder method anytime a thread is started using the file manager class.
     */
    @Override
    public void run() {
        try {
            watchFolder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
