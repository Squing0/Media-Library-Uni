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
    /** Do I really need to comment each one of these? */
    private String libraryName;
    /** Used to identify specific libraries (NEEDED?) */
    private int libraryID;
    /** Specific location of media library. */
    private String location;
    /** Text file associated with media library. */
    private Vector<MediaItem> mediaItems = new Vector<>();
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
     */
    public MediaLibrary(){
    }
// KEEP

    public void createMediaLibrary(String fl, String name){
        MediaLibrary mediaLibrary = new MediaLibrary(fl, name);

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
    public MediaItem searchForItem(MediaLibrary library, String name, String type){
        for(MediaItem mediaItem: library.getMediaItems()){
        // LOOK INTO STREAMS OR BINARY SEARCH
            if (mediaItem.getMediaName().equals(name) && mediaItem.getMediaType().equals(type)){    // Just copied
                return mediaItem;
            }
        }

        return null;
    }

    public MediaItem binarySearchTrial(MediaLibrary library, String name, String type){   //change name
//        Vector<String> itemNames = new Vector<>();
//        Vector<String> itemTypes = new Vector<>();
//
//        for(MediaItem item : library.getMediaItems()){
//            itemNames.add(item.getMediaName());
//            itemTypes.add(item.getMediaType());
//        }
//
//        Collections.sort(itemNames);
//        Collections.sort(itemTypes);

        MediaItem item = new MediaItem(name, type, null, 0, 0, null, 0, null, false);

        Comparator<MediaItem> comparator = Comparator.
                comparing(MediaItem::getMediaName).
                thenComparing(MediaItem::getMediaType);

        int index = Collections.binarySearch(library.getMediaItems(), item, comparator);

        if (index >= 0){
            return library.getMediaItems().get(index);
        }
        else{
            return null;
        }
    }

// DDONT DELTE GETTERS FOR JACKJSONNNNNNNNNNNN
    public List<MediaItem> getMediaItems(){ return mediaItems;}
    public List <Playlist> getPlaylists(){return playlists;}

    public String getLibraryName() {return libraryName;}
    public String getLocation() {return location;}

}
