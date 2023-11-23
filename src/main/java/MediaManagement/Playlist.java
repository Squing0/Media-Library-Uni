package MediaManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * MediaManagement.Playlist class handles creation of playlists.
 * @author Lyle Patterson
 */
public class Playlist {
    /** Name of playlist. */
    private String playlistName = "";
    /** Type of playlist. */
    private String playlistType = "";
    /** ID used to uniquely identify playlist. */
    private int playlistID = 0;
    /** Arraylist of Media Item objects */
    private ArrayList<MediaItem> mediaitems = new ArrayList<MediaItem>();    // Should I use generics in other places?
//    private int mediaTotal = 0; // Even needed??

    /**
     * Constructor assigns playlist information.
     * @param n playlist name.
     * @param t playlist type.
     * @param ID playlist ID.
     */
    public Playlist(String n, String t, int ID){
        // Need to have either different constructors or if statements to differentiate type here
        this.playlistName = n;
        this.playlistType = t;
        this.playlistID = ID;
    }

    /**
     * Empty constructor so that methods can be accessed
     * without creating a playlist.
     */
    public Playlist(){  //Not sure if this is good practice but just testing

    }

    /**
     * Deletes a playlist object and clears the arraylist
     * of media items.
     * @param ID playlist ID.
     * @param playlist playlist object.
     */
    public void deletePlaylist(int ID, Playlist playlist){  // Not sure if the ID is needed for any of these items
        playlist.mediaitems.clear();
        playlist = null;
    }

    /**
     * Adds a media item to a specific playlist within
     * a media library file.
     * @param ID playlist ID.
     * @param item MediaManagement.MediaItem object.
     * @param libraryLocation location of media library file.
     */
    public void addMedia(int ID, MediaItem item, String libraryLocation){
        MediaLibrary library = new MediaLibrary();

        if(!library.checkPlaylistItem(playlistName, item.getMediaName(), libraryLocation)){
            if(item.getMediaType() == playlistType){
                mediaitems.add(item);
                library.addItemSpecificPoint(libraryLocation, playlistName, item.printAllMediaLibrary());
            }
            else{
                System.out.println(item.getMediaType() + " isn't the playlist type of " + playlistType);
            }
        }
        else {
            System.out.println("Item already exists in playlist!");
        }

    }

    /**
     * Deletes media item from items arraylist.
     * @param ID MediaManagement.MediaItem ID
     * @param item MediaManagement.MediaItem object
     */
    public void deleteMedia(int ID, MediaItem item){
        mediaitems.remove(item);
    }

    public void print(){
        for(int i =0; i < mediaitems.size(); i++){
            mediaitems.get(i).printAll();
            System.out.println();
        }
    }



    // Remove which ones aren't used
    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistType() {
        return playlistType;
    }

    public void setPlaylistType(String playlistType) {
        this.playlistType = playlistType;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public List<MediaItem> getMediaItems(){
        return mediaitems;
    }
}
