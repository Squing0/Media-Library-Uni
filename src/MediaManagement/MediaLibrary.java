package MediaManagement;

import MediaManagement.MediaItem;
import MediaManagement.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Media Library class handles all actions relating to media library.
 * @author Lyle Patterson.
 */
public class MediaLibrary {
    /** Do I really need to comment each one of these? */
    private String libraryName = "";
    /** Used to identify specific libraries (NEEDED?) */
    private int libraryID = 0;
    /** Specific location of media library. */
    private String location = "";
    /** Text file associated with media library. */
    private File mediaLibraryFile;
    /** Might not need these two. */
    private ArrayList<MediaItem> items = new ArrayList<MediaItem>();
    private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    // Instantiate media items and playlists here???

    /**
     * Constructor assigns media library information and
     * creates new media library at entered location.
     * @param n the name of the media library file.
     * @param ID the specific ID of the media library file.
     * @param l the location of the media library file.
     */
    public MediaLibrary(String n, int ID, String l){
        this.libraryName = n;
        this.libraryID = ID;
        this.location = "Media-Libraries/" + n + ".txt";
        this.mediaLibraryFile = new File(location);
        setUpLibraryFile();
    }

    /**
     * Empty constructor so that methods can be accessed
     * without creating a new media library file.
     */
    public MediaLibrary(){

    }

    /**
     * Used so that there are specific points for
     * media items and playlists to be added.
     */
    public void setUpLibraryFile(){ // How is the location here gotten?
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.location, true));
            writer.write("Media Items:\n");
            writer.write("Playlists:\n");
            writer.close();

            System.out.println("Text Added!");
        }
        catch(FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch (IOException e){
            System.out.println("There was an error handling the file!");
        }
    }

    public void getItemFromLibrary(){

    }

    /**
     * Adds a media item to the media library file
     * at a specific point.
     * @param ID the specific ID of the media item.
     * @param item the media item object allows information to be retrieved from it.
     */
    public void addMediaItem(int ID, MediaItem item){
        // ID here is for media library itself
        // Can user update media Item? Check email
        items.add(item);

        FileWriter fr = null;
        BufferedWriter br = null;   //Change this a bit, can be simpler
        try{
            fr = new FileWriter(mediaLibraryFile, true);
            br = new BufferedWriter(fr);
            br.append(item.printAllMediaLibrary());
            br.close();
            fr.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch(IOException e){
            System.out.println("There was an error handling the file!");
        }

        // Should there be finallys in the code?

    }

    /**
     * Adds text to a file if a name entered to the method is found
     * within a line.
     * @param fl the file location of the media library file.
     * @param name the name that is checked against each line in the file.
     * @param text the text that is added if the name is found in a line of the file.
     */
    public void addItemSpecificPoint(String fl, String name, String text){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fl));
            StringBuilder sb = new StringBuilder();
            String line;

//            boolean nameExists = false;

            while((line = reader.readLine()) != null){
                if(line.contains(name)){
                    sb.append(line).append("\n");
                    sb.append(text).append("\n");
//                    nameExists = true;
                }
                else{
                    sb.append(line).append("\n");
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fl));
            writer.write(sb.toString());
            writer.close();

            System.out.println("Text added!");
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch (IOException e){
            System.out.println("There was an error handling the file!");
        }
    }

    /**
     * Checks file to see if name is already present.
     * @param name the name that is checked in the file.
     * @param libraryLocation the location of the media library file.
     * @return {@code true} if the name is present and {@code false} if it is not.
     */
    public boolean nameAlreadyPresent(String name, String libraryLocation){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
            String line;

            // There will always be a line between media items and playlists and this checks for that
            while((line = reader.readLine()) != null & !line.isEmpty()){
                if(line.contains(name)){
                    reader.close();
                    return true;
                }
            }
            reader.close();

        }
        catch(FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }
        return false;
    }

    /**
     * Checks file to see if playlist name and type
     * are already present.
     * @param playlistName the playlist name that is checked.
     * @param playlistType the playlist type that is also checked.
     * @param libraryLocation the location of the media library file.
     * @return {@code true} if the playlist name and time were already present
     * and {@code false} if they were not.
     */
    public boolean checkPlaylistInfo(String playlistName, String playlistType, String libraryLocation){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
            String line;

            while((line = reader.readLine()) != null){
                if(line.contains("MediaManagement.Playlist, " + playlistName + ", " + playlistType)){
                    return true;
                }
            }
            reader.close();

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");  // These are the exact same across all the methods
        }
        catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }

        return false;
    }

    /**
     * Checks if a media item is already present in a playlist
     * before a new media item is added.
     * @param playlistName the playlist that will be checked.
     * @param mediaName the media item within the playlist that will be checked.
     * @param libraryLocation the location of the media library file.
     * @return {@code true} if the media item was present
     * in the playlist and {@code false} if it was not.
     */
    public boolean checkPlaylistItem(String playlistName, String mediaName, String libraryLocation){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
            String line;
            boolean afterPlaylist = false;

            while((line = reader.readLine()) != null){
                if(line.contains("MediaManagement.Playlist, " + playlistName)){
                    afterPlaylist = true;
                }

                if(afterPlaylist){  // This works fine but will go through entire playlist so not efficient
                    if(line.contains(mediaName)){
                        reader.close();
                        return true;
                    }
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }

        return false;
    }

    /**
     * Deletes a specific line from the media library file.
     * CHANGE THIS LATERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
     */
    public void deleteSpecificLine(String fl, String name){   // Make more unique, taken from chatgpt for now
        // Seems to work well, they say that new file needs to be created but original file is updated here

        try {
            File inputFile = new File(fl);
            File tempFile = new File("Media-Libraries/Seasons3.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String playlistName = "June";
            String playlistType = "Audio";

            String currentLine;
            boolean inPlaylist = false;

            // MAKE MORE UNIQUEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE

            // Deleting media item and item in playlist
//            while ((currentLine = reader.readLine()) != null) {
//                // If the line does not contain the content to be deleted, write it to the temporary file
//                if (!currentLine.contains(name)) {
//                    writer.write(currentLine);
//                    writer.newLine();
//                }
//            }

            // Deleting specific item from playlist
//            while ((currentLine = reader.readLine()) != null){
//
//                if (currentLine.startsWith("Playlist,")){
//                    inPlaylist = currentLine.contains(playlistName + ", " +  playlistType);
//                }
//
//                if (inPlaylist && currentLine.contains(name)){
//                    continue;
//                }
//
//                writer.write(currentLine);
//                writer.newLine();
//            }

            // Deleting entire playlist
            while((currentLine = reader.readLine()) != null){
                if (currentLine.startsWith("Playlist,")){
                    inPlaylist = currentLine.contains(playlistName + ", " +  playlistType);
                }

                if(inPlaylist){
                    continue;
                }

                writer.write(currentLine);
                if(reader.ready()){
                    writer.newLine();
                }
            }

            writer.close();
            reader.close();

            // Delete the original file
            if (inputFile.delete()) {
                // Rename the temporary file to the original file name
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Could not rename the temporary file");
                }
            } else {
                System.out.println("Could not delete the original file");
            }

            String s1 = String.format("Line with '%s' in it was successfully removed", name);
            System.out.println(s1);

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }
    }


    // Not sure if this needs to be done, can simply copy the string attached to the media item in the GUI
    // This still might be needed tho so keeping for later
//    public MediaManagement.MediaItem getMediaItemByName(String name, String libraryLocation){
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
//            String line;
//            String mediaInfo;
//
//            while((line = reader.readLine()) != null){
//                if(line.contains(name)){
//                    mediaInfo = line;
//                    reader.close();
//                }
//            }
//            reader.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
    public void removeMediaItem(int ID, MediaItem item){
        items.remove(item);
    }

    public void addPlaylist(int ID, Playlist playlist){
        playlists.add(playlist);
    }
    public void removePlaylist(int ID, Playlist playlist){
        playlists.remove(playlist);
    }

    public void print(){
        System.out.println(items);
        System.out.println(playlists);
    }



    // Remove which ones aren't used
    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(int libraryID) {
        this.libraryID = libraryID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
