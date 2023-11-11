public class MediaItem {
    private String mediaName = "";
    private String mediaType = "";  // Know mot really need to add values but lecturers might like it???
    private String format = "";
    private int itemID = 0;
    private double size = 0d;
    private String fileLocation = "";
    private double trackLength = 0d;
    private String resolution = "1920x1080";    // Make separate constructor for videos to make unique

    // Don't forget getters and setters
    public MediaItem(String n, String t, String f, int ID, double s, String fl, double tl){
    // Use for videos and audio. Should have specific attribute for videos?
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

    public MediaItem(String n, String t, String f, int ID, double s, String fl){
    // Use for images
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.itemID = ID;
        this.size = s;
        this.fileLocation = fl;
    }
    public void delete(int ID){}
}
