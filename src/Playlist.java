import java.util.ArrayList;

public class Playlist {
    private String playlistName = "";
    private String playlistType = "";
    private int playlistID = 0;
    private ArrayList<MediaItem> items = new ArrayList<MediaItem>();
    private int mediaTotal = 0; // Even needed??

    public Playlist(String n, String t, int ID){
        // Need to have either different constructors or if statements to differentiate type here
        this.playlistName = n;
        this.playlistType = t;
        this.playlistID = ID;
    }

    public void deletePlaylist(int ID, Playlist playlist){  // Not sure if the ID is needed for any of these items
        playlist.items.clear();
        playlist = null;
    }

    public void addMedia(int ID, MediaItem item){
        items.add(item);
    }
    public void deleteMedia(int ID, MediaItem item){
        items.remove(item);
    }

    public void print(){
        System.out.println(items);
    }
}
