package FileManageAndSearch;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileManager {
    private String folderLocation = "";

    public void watchFolder(int libraryID){

    }
    public void importToLibrary(int libraryID){

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
}
