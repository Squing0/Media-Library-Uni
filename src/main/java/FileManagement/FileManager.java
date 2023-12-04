package FileManagement;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.io.IOException;

public class FileManager implements Runnable{
//    private String folderLocation = "C:/Users/lylep/Downloads/testtt";
    private String folderLocation = "";
    private String libraryPath = "";
    private FileObserver observer;

    public FileManager(FileObserver o){
        observer = o;
    }

    public FileManager(){
    }
    // NEEDED? ^^^
    public synchronized void watchFolder() throws IOException { //Made synchronized just to make sure

        try {
            Path dir = Paths.get(folderLocation);
            WatchService ws = FileSystems.getDefault().newWatchService();
            dir.register(ws, StandardWatchEventKinds.ENTRY_CREATE);

            while (true){
                if(Thread.interrupted()){
                    break;
                }
                WatchKey key;
                try{
                    key = ws.take();
                }
                catch (InterruptedException e){
                    break;
                }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            Path createdFilePath = dir.resolve((Path) event.context());
                            SwingUtilities.invokeLater(() -> System.out.println("File processed"));

                            Search search = new Search();
                            search.typeVerify(createdFilePath.toString().replace("\\", "/"), libraryPath);

                            observer.onFileCreated(createdFilePath);
                        }

                }

                boolean valid = key.reset();
                if(!valid){
                    break;
                }

            }

        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
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

    public void deleteFile(String folderLocation, String fileName){  // put in different class
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

    // FILES DON'T GET DELTED WHEN LIBRARY IS DELTEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD

    /**
     * Creates a simple 'media file' for information
     * to be retrieved from.
     * @param fl the file location of the media file.
     */
    public void createMediaFileBasic(String fl)  {
        RandomAccessFile file = null;

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
