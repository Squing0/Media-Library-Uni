import java.io.*;
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
        this.location = "Media-Libraries/" + n + ".txt";
        this.mediaLibraryFile = new File(location);
        setUpLibraryFile();
    }

    public MediaLibrary(){  // Empty constructor so can access methods without making new medialibrary file

    }

    public void setUpLibraryFile(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.location, true));
            writer.write("Media Items:\n");
            writer.write("Playlists:\n");
            writer.close();

            System.out.println("Text Added!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getItemFromLibrary(){

    }

    public void addMediaItem(int ID, MediaItem item){   // Unsure about all 4 of these
        // ID here is for media library itself
        // Can user update media Item? Check email
        items.add(item);

        FileWriter fr = null;
        BufferedWriter br = null;   //Change this a bit, can be simpler
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

    public void addItemSpecificPoint(String fl, String name, String text){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fl));
            StringBuilder sb = new StringBuilder();
            String line;

//            boolean nameExists = false;

            while((line = reader.readLine()) != null){
                if(line.contains(name)){
                    sb.append(line).append("\n");
                    sb.append(text).append("\n");
//                    nameExists = true;
                }
                else{
                    sb.append(line).append("\n");
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fl));
            writer.write(sb.toString());
            writer.close();

            System.out.println("Text added!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean nameAlreadyPresent(String name, String libraryLocation){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
            String line;

            // There will always be a line between media items and playlists and this checks for that
            while((line = reader.readLine()) != null & !line.isEmpty()){
                if(line.contains(name)){
                    reader.close();
                    return true;
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPlaylistName(String playlistName, String playlistType, String libraryLocation){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
            String line;

            while((line = reader.readLine()) != null){
                if(line.contains("Playlist, " + playlistName + ", " + playlistType)){
                    return true;
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkPlaylistItem(String playlistName, String mediaName, String libraryLocation){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
            String line;
            boolean afterPlaylist = false;

            while((line = reader.readLine()) != null){
                if(line.contains("Playlist, " + playlistName)){
                    afterPlaylist = true;
                }

                if(afterPlaylist){  // This works fine but will go through entire playlist so not efficient
                    if(line.contains(mediaName)){
                        reader.close();
                        return true;
                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteSpecificLine(){   // Make more unique, taken from chatgpt for now
        // Seems to work well, they say that new file needs to be created but original file is updated here
        String filePath = "Media-Libraries/Seasons2.txt";
        String lineToRemove = "Lolsers2"; // Replace with the actual content of the line you want to delete

        try {
            File inputFile = new File(filePath);
            File tempFile = new File("Media-Libraries/Seasons3.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // If the line does not contain the content to be deleted, write it to the temporary file
                if (!currentLine.contains(lineToRemove)) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }

            writer.close();
            reader.close();

            // Delete the original file
            if (inputFile.delete()) {
                // Rename the temporary file to the original file name
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Could not rename the temporary file");
                }
            } else {
                System.out.println("Could not delete the original file");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Not sure if this needs to be done, can simply copy the string attached to the media item in the GUI
    // This still might be needed tho so keeping for later
//    public MediaItem getMediaItemByName(String name, String libraryLocation){
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(libraryLocation));
//            String line;
//            String mediaInfo;
//
//            while((line = reader.readLine()) != null){
//                if(line.contains(name)){
//                    mediaInfo = line;
//                    reader.close();
//                }
//            }
//            reader.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
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
