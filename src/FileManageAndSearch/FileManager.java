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


    // Remove which ones aren't used
    public String getFolderLocation() {
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }
}
