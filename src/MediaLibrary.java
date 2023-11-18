import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MediaLibrary {
    private String libraryName = "";
    private int libraryID = 0;
    private String location = "";
    private File mediaLibraryFile;  // Not sure if right
    private ArrayList<MediaItem> items = new ArrayList<MediaItem>();
    private ArrayList<Playlist> playlists = new ArrayList<Playlist>();
    // Instantiate media items and playlists here???

    public MediaLibrary(String n, int ID, String l){
        this.libraryName = n;
        this.libraryID = ID;
        this.location = l;
        this.mediaLibraryFile = new File("Media-Libraries/" + n + ".txt");
    }

    public void addMediaItem(int ID, MediaItem item){   // Unsure about all 4 of these
        // ID here is for media library itself
        // Can user update media Item? Check email
        items.add(item);

        FileWriter fr = null;
        BufferedWriter br = null;
        try{
            fr = new FileWriter(mediaLibraryFile, true);
            br = new BufferedWriter(fr);
            br.append(item.printAllMediaLibrary());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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



    // Remove which ones aren't used
    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getLibraryID() {
        return libraryID;
    }

    public void setLibraryID(int libraryID) {
        this.libraryID = libraryID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
