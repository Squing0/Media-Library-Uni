import java.util.ArrayList;

public class MediaLibrary {
    private String libraryName = "";
    private int libraryID = 0;
    private String location = "";
    private ArrayList<MediaItem> items = new ArrayList<MediaItem>();
    private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    // Instantiate media items and playlists here???

    public MediaLibrary(String n, int ID, String l){
        this.libraryName = n;
        this.libraryID = ID;
        this.location = l;
    }

    public void addMediaItem(int ID, MediaItem item){   // Unsure about all 4 of these
        // ID here is for media library itself
        // Can user update media Item? Check email
        items.add(item);
    }

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
}
