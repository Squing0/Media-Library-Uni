public class Playlist {
    private String playlistName = "";
    private String playlistType = "";
    private int playlistID = 0;
    private int mediaTotal = 0; // Even needed??

    public Playlist(String n, String t, int ID){
        this.playlistName = n;
        this.playlistType = t;
        this.playlistID = ID;
    }

    public void deletePlaylist(int ID){

    }

    public void addMedia(int ID, MediaItem item){

    }
    public void deleteMedia(int ID, MediaItem item){

    }
}
