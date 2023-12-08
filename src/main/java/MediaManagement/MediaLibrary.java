package MediaManagement;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Media Library class handles all actions relating to media library.
 * @author Lyle Patterson.
 */
public class MediaLibrary {
    /** Name of media library.*/
    private String libraryName;
    /** Location of JSON associated with media library. */
    private String location;
    /** List of media item objects in media library.
     * Vector specifically used for media items as thread-safe.*/
    private Vector<MediaItem> mediaItems = new Vector<>();
    /** List of playlist objects in media library.*/
    private ArrayList<Playlist> playlists = new ArrayList<>();

    /**
     * Constructor assigns media library information and
     * creates new media library at entered location.
     * @param n the name of the media library file.
     * @param l the location of the media library file.
     */
    public MediaLibrary(String l, String n){
        this.libraryName = n;
        this.location = l;
    }

    /**
     * Empty constructor so that methods can be accessed
     * without creating a new media library file.
     * Also for use with jackson library.
     */
    public MediaLibrary(){
    }

    /**
     * Sets up json file by writing object to file.
     * @param library Media library object.
     * @param fl File location of media library.
     */
    public void writeLibraryToJson(MediaLibrary library, String fl){
        // indent output is used so that json isn't returned on singular line.
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try{
            objectMapper.writeValue(new File(fl), library); //Writes library object as model to specific json file.
        }
        catch (IOException e){
            System.out.println("Couldn't write to json file!");
        }
    }
    /**
     * Gets contents of json file and adds it to library object.
     * @param fl File location of media library.
     * @return Media library object with contents of json file.
     */
    public MediaLibrary getLibraryFromJson(String fl){
        // indent output is used so that json isn't returned on singular line.
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try{
            return objectMapper.readValue(new File(fl), MediaLibrary.class);    //Reads json file to object by specifying class
        }
        catch (IOException e){
            System.out.println("Couldn't read from json file!");
        }
        return null;
    }

    /**
     * Adds media item to json file.
     * @param fl File location of media library.
     * @param item Media item object.
     */
    public void addMedia(String fl, MediaItem item){
        MediaLibrary library = getLibraryFromJson(fl);


        if (!mediaItemAlreadyPresent(library, item.getMediaName(), item.getFormat())){
            library.getMediaItems().add(item);  // Adds to media item list in library.

            writeLibraryToJson(library, fl);
        }
        else{
            System.out.println("Item already present!");
        }
    }

    /**
     * Adds playlist to json file.
     * @param fl File location of media library.
     * @param playlist Playlist object.
     */
    public void addPlaylist(String fl, Playlist playlist){
        MediaLibrary library = getLibraryFromJson(fl);

        if(!playlistAlreadyPresent(library, playlist.getPlaylistName(), playlist.getPlaylistType())){
            library.getPlaylists().add(playlist); // Adds to playlist list in library.

            writeLibraryToJson(library, fl);
        }
        else{
            System.out.println("Playlist already present!");
        }
    }

    /**
     * Deletes playlist from json file.
     * @param fl File location of media library.
     * @param playlistName Name of playlist.
     * @param playlistType Type of playlist.
     */
    public void deletePlaylist(String fl, String playlistName, String playlistType){
        MediaLibrary library = getLibraryFromJson(fl);

        Playlist specific = findPlaylist(library, playlistName, playlistType);  // Finds specific playlist with name and type.

        library.getPlaylists().remove(specific);    // Removes playlist from playlist list.

        writeLibraryToJson(library, fl);
    }

    /**
     * Deletes media item from json file.
     * @param fl File location of media library.
     * @param itemName Name of media item.
     * @param itemFormat Format of media item.
     */
    public void deleteMediaItem(String fl, String itemName, String itemFormat){
        MediaLibrary library = getLibraryFromJson(fl);  //
        MediaItem specific = findItem(library, itemName, itemFormat);

        library.getMediaItems().remove(specific);   // Gets and removes specific media item.

        //Loops through all playlists and removes
        // media item if found with same name and format
        for(Playlist playlist : library.getPlaylists()){
            playlist.getMediaItems().removeIf(item ->
                    item.getMediaName().equals(itemName)
                            && item.getFormat().equals(itemFormat));
        }

        writeLibraryToJson(library, fl);
    }

    /**
     * Deletes media item from playlist.
     * @param fl File location of media library.
     * @param playlistName Name of playlist.
     * @param playlistType Type of playlist.
     * @param mediaName Name of media item.
     * @param mediaFormat Format of media item.
     */
    public void deleteItemPlaylist(String fl, String playlistName, String playlistType, String mediaName, String mediaFormat){
        MediaLibrary library = getLibraryFromJson(fl);
        Playlist specific = findPlaylist(library, playlistName, playlistType);

        // Loops through playlist and deletes item if name and type
        // are same as name and type given to method.
        specific.getMediaItems().removeIf(item ->
                item.getMediaName().equals(mediaName)
                        && item.getFormat().equals(mediaFormat));

        writeLibraryToJson(library, fl);
    }

    /**
     * Adds media item to playlist.
     * @param fl File location of media library.
     * @param playlistName Name of playlist.
     * @param playlistType Type of playlist.
     * @param item Media item object.
     */
    public void addItemPlaylist(String fl, String playlistName, String playlistType, MediaItem item){
        MediaLibrary library = getLibraryFromJson(fl);

        // Type is checked as has to be same to add to playlist
        // and item is checked to see whether already in playlist.
        boolean checkType = isItemCorrectType(library, item.getMediaType(), playlistName, playlistType);
        boolean checkPresence = itemAlreadyInPlaylist(library, item.getMediaName(), item.getFormat(), playlistName, playlistType);

        if(checkType && !checkPresence){
            Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);  // Gets specific playlist.

            specificPlaylist.getMediaItems().add(item); // Adds to playlist
            writeLibraryToJson(library, fl);

        }
        else{
            System.out.println("Item either isn't correct type or is already present!");
        }
    }

    /**
     * Finds playlist in media library.
     * @param library Media library object.
     * @param name Name of playlist being checked.
     * @param type Type of playlist being checked.
     * @return Playlist object if found and null if not.
     */
    public Playlist findPlaylist(MediaLibrary library, String name, String type){
        // Loops through playlists until playlist with same name and type as entered is found.
        for (Playlist playlist : library.getPlaylists()){
            if(playlist.getPlaylistName().equals(name) && playlist.getPlaylistType().equals(type)){
                return playlist;
            }
        }
        // Returns null if playlist cannot be found.
        return null;
    }

    /**
     * Finds media item in media library.
     * @param library Media library object.
     * @param name Name of media item being checked.
     * @param format Format of media item being checked.
     * @return Media item object if found and null if not.
     */
    public MediaItem findItem(MediaLibrary library, String name, String format){
        // Loops through media items until media item list until item with same name and type as entered is found.
        for(MediaItem mediaItem: library.getMediaItems()){
            if (mediaItem.getMediaName().equals(name) && mediaItem.getFormat().equals(format)){
                return mediaItem;
            }
        }
        // Returns null if media item cannot be found.
        return null;
    }

    /**
     * Checks if media item is already present in library.
     * @param library Media library object.
     * @param name Name of media item.
     * @param format Format of media item.
     * @return true if media item is found and false if not.
     */
    public boolean mediaItemAlreadyPresent(MediaLibrary library, String name, String format){
        // Loops through media item to find item with same name and type.
        for(MediaItem mediaItem : library.getMediaItems()){
            if (mediaItem.getMediaName().equals(name) && mediaItem.getFormat().equals(format)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if playlist is already present in library.
     * @param library Media library object.
     * @param name Name of playlist being checked.
     * @param type Type of playlist being checked.
     * @return true if playlist is found and false if not.
     */
    public boolean playlistAlreadyPresent(MediaLibrary library, String name, String type){
        // Loops through playlist to find playlist with same name and type.
        for(Playlist playlist : library.getPlaylists()){
            if(playlist.getPlaylistName().equals(name) && playlist.getPlaylistType().equals(type)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if item is already present in playlist.
     * @param library Media library object.
     * @param itemName Name of media item.
     * @param itemFormat Format of media item.
     * @param playlistName Name of playlist.
     * @param playlistType Type of playlist.
     * @return true if item is found and false if not.
     */
    public boolean itemAlreadyInPlaylist(MediaLibrary library, String itemName, String itemFormat, String playlistName, String playlistType){
        Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);

        // Loops through media item list in playlist to find item with same name and format as entered.
        for(MediaItem item : specificPlaylist.getMediaItems()){
            if(item.getMediaName().equals(itemName) && item.getFormat().equals(itemFormat)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if media item to be added to playlist has same type as playlist.
     * @param library Media library object.
     * @param type Type of media item.
     * @param playlistName Name of playlist.
     * @param playlistType Type of playlist.
     * @return true if item type is correct and false if not.
     */
    public boolean isItemCorrectType(MediaLibrary library, String type, String playlistName, String playlistType){
        Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);

        // If the playlist is found, check type against media item.
        if(specificPlaylist != null){
            return specificPlaylist.getPlaylistType().equals(type);
        }
        return false;
    }

    /**
     * Getter
     * @return list of media items.
     */
    public List<MediaItem> getMediaItems(){ return mediaItems;}
    /**
     * Getter
     * @return media item usability.
     */
    public List <Playlist> getPlaylists(){return playlists;}
    /**Getter. (unused getter needed for jackson library)
     * @return name of media library.
     */
    public String getLibraryName() {return libraryName;}
    /**Getter. (unused getter needed for jackson library)
     * @return file location of media library.
     */
    public String getLocation() {return location;}
}
