package MediaManagement;

import java.io.*;

/**
 * Media item class handles creation of media items.
 * @author Lyle Patterson
 */
public class MediaItem {
    /** Name of media item. */
    private String mediaName = "";
    /** Type of media item. Either image, audio or video. */
    private String mediaType = "";  // Know mot really need to add values but lecturers might like it???
    /** Format of media item (e.g., mp3). */
    private String format = "";
    /** ID to uniquely identify media item. */
    private int itemID = 0;
    /** Size of media item in megabytes. */
    private double size = 0;
    /** Specific file location of media item. */
    private String fileLocation = "";
    /** Track length of audio and video in seconds. */
    private double trackLength = 0;
    /** Resolution of images and videos. */
    private String resolution = "1920x1080";    // Make separate constructor for videos to make unique
    /** Describes if file is usable.
     * Manually created files are not. */
    private boolean usability = false;

    // Should call save method in constructors? Opinion seems to be that bad idea
    // Use for audio

    /**
     * Media item constructor for audio specifically.
     * @param n item name.
     * @param t item type.
     * @param f item format.
     * @param ID item ID.
     * @param s item size.
     * @param fl item file location.
     * @param tl item track length.
     * @param u item usability.
     */
    public MediaItem(String n, String t, String f, int ID, double s, String fl, double tl, boolean u){
    // Still unsure what creating media items means
    // Considering that we have to manually create them, I think we enter in details
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.itemID = ID;
        this.size = s;
        this.fileLocation = fl;
        this.trackLength = tl;
        this.usability = u;
    }

    /**
     * Media item constructor for video specifically.
     * @param n item name.
     * @param t item type.
     * @param f item format.
     * @param ID item ID.
     * @param s item size.
     * @param fl item file location.
     * @param tl item track length.
     * @param r item resolution.
     * @param u item usability.
     */
    public MediaItem(String n, String t, String f, int ID, double s, String fl, double tl, String r, boolean u){
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.itemID = ID;
        this.size = s;
        this.fileLocation = fl;
        this.trackLength = tl;
        this.resolution = r;
        this.usability = u;
    }

    /**
     * Media item constructor for images specifically.
     * @param n item name.
     * @param t item type.
     * @param f item format.
     * @param ID item ID.
     * @param s item size.
     * @param fl item file location.
     * @param r item resolution.
     * @param u item usability.
     */
    public MediaItem(String n, String t, String f, int ID, double s, String fl, String r, boolean u){
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.itemID = ID;
        this.size = s;
        this.fileLocation = fl;
        this.resolution = r;
        this.usability = u;
    }

    // NEED EMPTY CONSTRUCTOR FOR JACKSON
    public MediaItem(){

    }

    /**
     * Creates a simple 'media file' for information
     * to be retrieved from.
     * @param fl the file location of the media file.
     * @param mediaType the type of the media file.
     *                  DON't GET HOW THROWS WORK HERE
     */
    public void CreateMediaFileBasic(String fl, String mediaType)  {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(fl, "rw");
            file.close();
        } catch (FileNotFoundException e) {
            String s1 = String.format("File at (%s) does not exist!", fl);
            System.out.println(s1);
        }
        catch (IOException e) {
            String s1 = String.format("File at (%s) was not able to be closed.", fl);
            System.out.println(s1);
        }

        switch (mediaType){
            case "Image":
                System.out.println("Image Created!");
                break;
            case "Audio":
                System.out.println("Audio Created!");
                break;
            case "Video":
                System.out.println("Video Created!");
        }
        // REMEMBER TO LET USER KNOW THAT THESE CANNOT BE OPENED
    }

    public void printAll(){
        // Purely to test accessing info
        String s1 = "";

        if(this.mediaType == "Image"){
            s1 = String.format("Name: %s\n Type: %s\nFormat: %s\nID: %o\nSize: %.2f(MB)\nResolution: %s",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.resolution);
            System.out.println(s1);
        }
        else if (mediaType == "Audio"){
            s1 = String.format("Name: %s\n Type: %s\nFormat: %s\nID: %o\nSize: %.2f(MB)\nDuration: %.2f Seconds",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.trackLength);
            System.out.println(s1);
        }
        else if (mediaType == "Video"){
            s1 = String.format("Name: %s\n Type: %s\nFormat: %s\nID: %o\nSize: %.2f(MB)\nDuration: %.2f Seconds\nResolution: %s",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.trackLength, this.resolution);
            System.out.println(s1);
        }
    }

    /**
     * Creates specifically formatted line of media item information
     * for the media library file.
     * @return formatted media item string.
     */
    public String printAllMediaLibrary(){
        String s1 = "";

        if(mediaType == "Image"){
            if (usability){
                s1 = String.format("%s,%s,%s,%o,%.2f(MB),%s,%s,USABLE",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.resolution, this.fileLocation);
                return s1;
            }
            else{
                s1 = String.format("%s,%s,%s,%o,%.2f(MB),%s,%s,NOT USABLE",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.resolution, this.fileLocation);
                return s1;
            }
        }
        else if (mediaType == "Audio"){
            if(usability){
                s1 = String.format("%s,%s,%s,%o,%.2f(MB),%.2f,%s,USABLE",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.trackLength, this.fileLocation);
                return s1;
            }
            else{
                s1 = String.format("%s,%s,%s,%o,%.2f(MB),%.2f,%s,NOT USABLE",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.trackLength, this.fileLocation);
                return s1;
            }
        }
        else if (mediaType == "Video"){
            if(usability){
                s1 = String.format("%s,%s,%s,%o,%.2f(MB),%.2f,%s,%s,USABLE",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.trackLength, this.resolution, this.fileLocation);
                return s1;
            }
            else{
                s1 = String.format("%s,%s,%s,%o,%.2f(MB),%.2f,%s,%s,NOT USABLE",this.mediaName, this.mediaType, this.format, this.itemID, this.size, this.trackLength, this.resolution, this.fileLocation);
                return s1;
            }
        }
        return null;
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
    public boolean getUsability(){return usability;};

}

