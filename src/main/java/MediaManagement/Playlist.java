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
    /** Arraylist of Media Item objects */
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();

    /**
     * Constructor assigns playlist information.
     * @param n playlist name.
     * @param t playlist type.
     */
    public Playlist(String n, String t){
        this.playlistName = n;
        this.playlistType = t;
    }

    /**
     * Empty constructor used here for jackson
     * library to model items off of.
     */
    public Playlist(){
    }

    /**
     * Getter
     * @return playlist name.
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * Getter
     * @return playlist type.
     */
    public String getPlaylistType() {
        return playlistType;
    }

    /**
     * Getter
     * @return list of media items.
     */
    public List<MediaItem> getMediaItems(){
        return mediaItems;
    }
}