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
     * (indent output is used so that json isn't returned on singular line)
     * @param library Media library object.
     * @param fl File location of media library.
     */
    public void writeLibraryToJson(MediaLibrary library, String fl){
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try{
            objectMapper.writeValue(new File(fl), library);
        }
        catch (IOException e){
            System.out.println("Couldn't write to json file!");
        }
    }
    /**
     * Gets contents of json file and adds it to library object
     * (indent output is used so that json isn't returned on singular line)
     * @param fl File location of media library.
     * @return Media library object with contents of json file.
     */
    public MediaLibrary getLibraryFromJson(String fl){
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try{
            return objectMapper.readValue(new File(fl), MediaLibrary.class);
        }
        catch (IOException e){
            System.out.println("Couldn't read from json file!");
        }
        return null;
    }

    /**
     * Adds media item to json file
     * by reading file into library object and then
     * writing said object to json file.
     * (checks if media item is already present in file before adding)
     * @param fl File location of media library.
     * @param item Media item object.
     */
    public void addMedia(String fl, MediaItem item){
        MediaLibrary library = getLibraryFromJson(fl);

        if (mediaItemAlreadyPresent(library, item.getMediaName(), item.getFormat())){
            library.getMediaItems().add(item);

            writeLibraryToJson(library, fl);

            System.out.println("Success!");
        }
        else{
            System.out.println("Item already present!");
        }
    }

    /**
     * Adds playlist to json file
     * by reading file into object and then
     * writing said object to json file.
     * (checks if playlist is already present in file before adding)
     * @param fl File location of media library.
     * @param playlist Playlist object.
     */
    public void addPlaylist(String fl, Playlist playlist){
        MediaLibrary library = getLibraryFromJson(fl);

        if(!playlistAlreadyPresent(library, playlist.getPlaylistName(), playlist.getPlaylistType())){
            library.getPlaylists().add(playlist);
            writeLibraryToJson(library, fl);

            System.out.println("Success");
        }
        else{
            System.out.println("Playlist already present!");
        }
    }

    /**
     * Deletes playlist from json file by reading file into library object,
     * finding a playlist with its name and type, removing this from the library
     * and then writing this to the json file.
     * @param fl File location of media library.
     * @param playlistName Name of playlist.
     * @param playlistType Type of playlist.
     */
    public void deletePlaylist(String fl, String playlistName, String playlistType){
        MediaLibrary library = getLibraryFromJson(fl);

        Playlist specific = findPlaylist(library, playlistName, playlistType);

        library.getPlaylists().remove(specific);

        writeLibraryToJson(library, fl);

        System.out.println("Success!");
    }

    public void deleteMediaItem(String fl, String itemName, String itemFormat){
        MediaLibrary library = getLibraryFromJson(fl);
        MediaItem specific = checkNameFormat(library, itemName, itemFormat);

        library.getMediaItems().remove(specific);

        for(Playlist playlist : library.getPlaylists()){
            // EXPLAIN THIS
            playlist.getMediaItems().removeIf(item -> item.getMediaName().equals(itemName) && item.getFormat().equals(itemFormat));
        }

        writeLibraryToJson(library, fl);
    }

    public void deleteItemPlaylist(String fl, String playlistName, String playlistType, String mediaName, String mediaFormat){
        MediaLibrary library = getLibraryFromJson(fl);
        Playlist specific = findPlaylist(library, playlistName, playlistType);

        specific.getMediaItems().removeIf(item -> item.getMediaName().equals(mediaName) && item.getFormat().equals(mediaFormat));

        writeLibraryToJson(library, fl);
    }

    public void addItemPlaylist(String fl, String playlistName, String playlistType, MediaItem item) throws IOException {
        MediaLibrary library = getLibraryFromJson(fl);

        boolean checkType = isItemCorrectType(library, item.getMediaType(), playlistName, playlistType);
        boolean checkPresence = itemAlreadyInPlaylist(library, item.getMediaName(), item.getFormat(), playlistName, playlistType);

        if(checkType && !checkPresence){
            Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);

            specificPlaylist.getMediaItems().add(item);
            writeLibraryToJson(library, fl);

            System.out.println("Success!");
        }
        else{
            System.out.println("Item either isn't correct type or is already present!");
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

        for(MediaItem mediaItem : library.getMediaItems()){ // Use iterator here?
            if (mediaItem.getMediaName().equals(name) && mediaItem.getFormat().equals(format)){
                return false;
            }
        }
        return true;
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

        for(MediaItem item : specificPlaylist.getMediaItems()){
            if(item.getMediaName().equals(itemName) && item.getFormat().equals(format)){
                return true;
            }
        }

        return false;
    }

    public boolean isItemCorrectType(MediaLibrary library, String type, String playlistName, String playlistType){
        Playlist specificPlaylist = findPlaylist(library, playlistName, playlistType);

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
