package MediaManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

    /** Arraylist of Media Item objects */
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();    // Should I use generics in other places?

    /**
     * Constructor assigns playlist information.
     * @param n playlist name.
     * @param t playlist type.
     * @param ID playlist ID.
     */
    public Playlist(String n, String t){
        // Need to have either different constructors or if statements to differentiate type here
        this.playlistName = n;
        this.playlistType = t;
    }

    /**
     * Empty constructor so that methods can be accessed
     * without creating a playlist.
     */
    public Playlist(){  //KEEP EMPTY CONSTRUCTOR
    }
    // DONT DELETE
    public String getPlaylistName() {
        return playlistName;
    }
    public String getPlaylistType() {
        return playlistType;
    }
    public List<MediaItem> getMediaItems(){
        return mediaItems;
    }
}