import java.util.ArrayList;

public class MediaLibrary {
    private String libraryName = "";
    private int libraryID = 0;
    private String location = "";
    private ArrayList<String> items = new ArrayList<String>();
    private ArrayList<String> playlists = new ArrayList<String>();
    // Instantiate media items and playlists here???

    public MediaLibrary(String n, int ID, String l){
        this.libraryName = n;
        this.libraryID = ID;
        this.location = l;
    }

    public void addMediaItem(int ID, MediaItem item){   // Unsure about all 4 of these
        // ID here is for media library itself
    }

    public void removeMediaItem(int ID, MediaItem item){

    }

    public void addPlaylist(int ID, Playlist playlist){

    }
    public void removePlaylist(int ID, Playlist playlist){

    }
}
