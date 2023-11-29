package MediaManagement;

import FileManageAndSearch.Search;
import MediaManagement.MediaItem;
import MediaManagement.Playlist;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
    private ArrayList<MediaItem> mediaitems = new ArrayList<MediaItem>();
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
        mediaitems.add(item);

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
        mediaitems.remove(item);
    }

    public void addPlaylist(int ID, Playlist playlist){
        playlists.add(playlist);
    }
    public void removePlaylist(int ID, Playlist playlist){
        playlists.remove(playlist);
    }

    public void print(){
        System.out.println(mediaitems);
        System.out.println(playlists);
    }


    public void JSONcreateMediaFile(){
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            MediaItem item = new MediaItem("lol", "image", "jpg", 1, 20, "Created-Files/Sample_abc.jpg", "1920x1080", false);
            objectMapper.writeValue(new File("Media-Libraries/library.json"), item);
            System.out.println("File created!");
        }
        catch (Exception e){
            System.out.println("Error!");
        }
    }

    public void createMediaItemsJson(){
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        List<MediaItem> mediaItems = new ArrayList<MediaItem>();
        mediaItems.add(new MediaItem("Sample_abc", "Image", "jpg", 1, 20, "Created-Files/Sample_abc.jpg", "1920x1080", false));
        mediaItems.add(new MediaItem("file_example_MP4_480_1_5MG", "Video", "mp3", 2, 20, "Created-Files/file_example_MP4_480_1_5MG.mp4", 20, "1920x1080", false));

        try{
            String jsonString = objectMapper.writeValueAsString(mediaItems);

            try (FileWriter fileWriter = new FileWriter("Media-Libraries/library5.json")){
                fileWriter.write(jsonString);
            }
            System.out.println("It worked!");
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getJSONFile(){
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("Media-Libraries/library.json");
        try {
            MediaItem item = objectMapper.readValue(file, MediaItem.class);
            System.out.println("It worked!");
            System.out.println("Usability: " + item.getUsability());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void createMediaLibrary(String fl){
        MediaLibrary mediaLibrary = new MediaLibrary();

//        MediaItem item = new MediaItem("Sample_abc", "Image", "jpg", 1, 20, "Created-Files/Sample_abc.jpg", "1920x1080", false);
//        mediaLibrary.getMediaItems().add(item);

        writeLibraryToFile(mediaLibrary, fl);

        System.out.println("ya did it!");

    }

    public void writeLibraryToFile(MediaLibrary mediaLibrary, String filePath){
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try{
            objectMapper.writeValue(new File(filePath), mediaLibrary);
        }
        catch (IOException e){
            System.out.println("Error!");
        }
    }

    public void addMedia(String fl, MediaItem item){
        MediaLibrary library = getLibraryFromJson(fl);

        if (!mediaItemAlreadyPresent(library, item.getMediaName(), item.getFormat())){
            library.getMediaItems().add(item);

            writeLibraryToFile(library, fl);

            System.out.println("Success!");
        }
        else{
            System.out.println("Item already present!");
        }
    }

    public void addPlaylist(String fl, Playlist playlist){
        MediaLibrary library = getLibraryFromJson(fl);

        if(!playlistAlreadyPresent(library, playlist.getPlaylistName(), playlist.getPlaylistType())){
            library.getPlaylists().add(playlist);
            writeLibraryToFile(library, fl);

            System.out.println("Success");
        }
        else{
            System.out.println("Playlist already present!");
        }
    }

    public void deletePlaylist(String fl, String playlistName, String playlistType){
        MediaLibrary library = getLibraryFromJson(fl);

        Playlist specific = findPlaylist(library, playlistName, playlistType);

        library.getPlaylists().remove(specific);

        writeLibraryToFile(library, fl);

        System.out.println("Success!");
    }

    public void deleteMediaItem(String fl, String itemName, String itemFormat){
        MediaLibrary library = getLibraryFromJson(fl);

        MediaItem specific = checkNameFormat(library, itemName, itemFormat);

        if(specific != null){   // Have two separate checks? One for item and one for item in playlist
            library.getMediaItems().remove(specific);

            for(Playlist playlist : library.getPlaylists()){
                Iterator<MediaItem> iterator = playlist.getMediaItems().listIterator();

                // playlist.getMediaItems().removeIf(item -> item.getMediaName().equals(itemName) && item.getFormat().equals(itemFormat));
                while(iterator.hasNext()){
                    MediaItem item = iterator.next();
                    if(item.getMediaName().equals(itemName) && item.getFormat().equals(itemFormat)){
                        iterator.remove();
                    }
                }
            }

            writeLibraryToFile(library, fl);
            System.out.println("Success!");
        }
        else{
            System.out.println("Media item not found!");
        }
    }

    public void deleteItemPlaylist(String fl, String playlistName, String playlistType, String mediaName, String mediaFormat){
        MediaLibrary library = getLibraryFromJson(fl);  // MAKE DIFFERENTTTTTT

        Playlist specific = findPlaylist(library, playlistName, playlistType);
        if (specific != null){
            Iterator<MediaItem> iterator = specific.getMediaItems().listIterator();

            while (iterator.hasNext()){
                MediaItem item = iterator.next();

                if(item.getMediaName().equals(mediaName) && item.getFormat().equals(mediaFormat)){
                    iterator.remove();
                    System.out.println("Success!");
                    writeLibraryToFile(library, fl);
                    return;
                }
            }
            System.out.println("Item not found in playlist!");

        }
        else{
            System.out.println("Playlist empty!");
        }
    }

    public MediaLibrary getLibraryFromJson(String fl){
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try{
            return objectMapper.readValue(new File(fl), MediaLibrary.class);
        }
        catch (IOException e){
            System.out.println("Error!");
        }
        return null;
    }

    public void getSpecificMediaItem(){
        ObjectMapper objectMapper = new ObjectMapper(); // Change this to be more like other methods

        try{
            File file = new File("Media-Libraries/library4.json");
            String jsonContent = Files.readString(file.toPath());
            String name = "Sample_abc";
            String format = "jpg";

            List<MediaItem> MediaItemsde = objectMapper.readValue(jsonContent, new TypeReference<List<MediaItem>>() {
            });

//            for (MediaItem mediaItem: MediaItemsde){
//                System.out.println("Here ya go! " + mediaItem.printAllMediaLibrary());
//            }
//            MediaItem specificItem = checkNameFormat(MediaItemsde, name, format);
//
//            if(specificItem == null){
//                System.out.println("No media item with Name: " + name + " and " + format);
//            }
//            else{
//                System.out.println(specificItem.getMediaName() + "Ya did it!");
//            }
        }
        catch (IOException e){
            System.out.println("Error!");
        }
    }

    public void addItemPlaylist(String fl, String playlistName, String playlistType, MediaItem item) throws IOException {
        MediaLibrary library = getLibraryFromJson(fl);

        boolean checkType = isitemCorrectType(library, item.getMediaType(), playlistName, playlistType);
        boolean checkPresence = itemAlreadyInPlaylist(library, item.getMediaName(), item.getFormat(), playlistName, playlistType);

        if(checkType && !checkPresence){
            Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);
            if(specificPlaylist == null){
                System.out.println("Playlist not found");
            }
            else{
                specificPlaylist.getMediaItems().add(item);
            }

            writeLibraryToFile(library, fl);

            System.out.println("Success!");
        }
        else{
            System.out.println("Item either isn't correct type or is already present!");    // Should I make 2 seperate checks?
        }
    }
    public Playlist findPlaylist(MediaLibrary library, String name, String type){
        for (Playlist playlist : library.getPlaylists()){
            if(playlist.getPlaylistName().equals(name) && playlist.getPlaylistType().equals(type)){
                return playlist;
            }
        }
        return null;
    }

    public MediaItem checkNameFormat(MediaLibrary library, String name, String format){   // Make this same as above?
        for(MediaItem mediaItem: library.getMediaItems()){

            if (mediaItem.getMediaName().equals(name) && mediaItem.getFormat().equals(format)){
                return mediaItem;
            }
        }

        return null;
    }

    public boolean mediaItemAlreadyPresent(MediaLibrary library, String name, String format){

        for(MediaItem mediaItem : library.getMediaItems()){
            if (mediaItem.getMediaName().equals(name) && mediaItem.getFormat().equals(format)){
                return true;
            }
        }
        return false;
    }

    public boolean playlistAlreadyPresent(MediaLibrary library, String name, String type){

        for(Playlist playlist : library.getPlaylists()){
            if(playlist.getPlaylistName().equals(name) && playlist.getPlaylistType().equals(type)){
                return true;
            }
        }
        return false;
    }

    public boolean itemAlreadyInPlaylist(MediaLibrary library, String itemName, String format, String playlistName, String playlistType){
        Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);
        if(specificPlaylist != null){
            for(MediaItem item : specificPlaylist.getMediaItems()){
                if(item.getMediaName().equals(itemName) && item.getFormat().equals(format)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isitemCorrectType(MediaLibrary library, String type, String playlistName, String playlistType){
        Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);
        if(specificPlaylist != null){
            if (specificPlaylist.getPlaylistType().equals(type)){
                return true;
            }
        }
        return false;
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

    public List<MediaItem> getMediaItems(){ return mediaitems;}
    public List <Playlist> getPlaylists(){return playlists;}
}
