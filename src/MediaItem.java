import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MediaItem {
    private String mediaName = "";
    private String mediaType = "";  // Know mot really need to add values but lecturers might like it???
    private String format = "";
    private int itemID = 0;
    private double size = 0d;
    private String fileLocation = "";
    private double trackLength = 0d;
    private String resolution = "1920x1080";    // Make separate constructor for videos to make unique

    // Use for audio
    public MediaItem(String n, String t, String f, int ID, double s, String fl, double tl){
    // Still unsure what creating media items means
    // Considering that we have to manually create them, I think we enter in details
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.itemID = ID;
        this.size = s;
        this.fileLocation = fl;
        this.trackLength = tl;
    }
    // Use for videos
    public MediaItem(String n, String t, String f, int ID, double s, String fl, double tl, String r){
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.itemID = ID;
        this.size = s;
        this.fileLocation = fl;
        this.trackLength = tl;
        this.resolution = r;
    }
    // Use for images
    public MediaItem(String n, String t, String f, int ID, double s, String fl, String r){
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.itemID = ID;
        this.size = s;
        this.fileLocation = fl;
        this.resolution = r;
    }

    public void CreateMediafile(String fileLocation){
        String filePath = fileLocation;
        long sizeBytes = 1024;

//        File file = new File(filePath);

        try{
//            boolean creationSuccess = file.createNewFile();

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.setLength(sizeBytes);

//            if(creationSuccess){
//                System.out.println("Yes");
//            }
//            else{
//                System.out.println("No");
//            }
        }
        catch (IOException e){
            System.out.println("Oopsies");
        }
    }

    public void delete(int ID){
        // Use for either deleting media item or deleting media item and file if item was manually made?
    }

    // Remove which ones aren't used
    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public double getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(double trackLength) {
        this.trackLength = trackLength;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    // Don't forget getters and setters
}
