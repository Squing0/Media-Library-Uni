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
        if(item.getMediaType() == this.playlistType){
            items.add(item);
        }
        else{
            System.out.println(item.getMediaType() + " isn't the playlist type of " + this.playlistType);
        }

    }
    public void deleteMedia(int ID, MediaItem item){

        items.remove(item);
    }

    public void print(){
        for(int i =0; i < items.size(); i++){
            items.get(i).printAll();
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

    public int getMediaTotal() {
        return mediaTotal;
    }

    public void setMediaTotal(int mediaTotal) {
        this.mediaTotal = mediaTotal;
    }
}
