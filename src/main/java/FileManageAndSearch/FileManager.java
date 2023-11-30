package FileManageAndSearch;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import GUI.*;

public class FileManager implements Runnable{
    private String folderLocation = "C:/Users/lylep/Downloads/testtt";
    private FileObserver observer;

    public FileManager(FileObserver o){
        this.observer = o;
    }

    public FileManager(){

    }
    public void watchFolder() throws IOException {

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
                            SwingUtilities.invokeLater(() -> {
                                System.out.println("File processed");
                            });

                            Search search = new Search();
                            search.typeVerify(createdFilePath.toString().replace("\\", "/"), "Media-Libraries/library7.json");

                            observer.onFileCreated(createdFilePath);
                        }

                }

                boolean valid = key.reset();
                if(!valid){
                    break;
                }

            }

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
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

    // Remove which ones aren't used
    public String getFolderLocation() {
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    @Override
    public void run() {
        try {
            watchFolder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
