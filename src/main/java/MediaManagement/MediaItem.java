package MediaManagement;

import java.io.*;

/**
 * Media item class handles creation of media items.
 * @author Lyle Patterson
 */
public class MediaItem {
    /** Name of media item. */
    private String mediaName;
    /** Type of media item. Either image, audio or video. */
    private String mediaType;  // Know mot really need to add values but lecturers might like it???
    /** Format of media item (e.g., mp3). */
    private String format;
    /** ID to uniquely identify media item. */
    private int itemID;
    /** Size of media item in megabytes. */
    private double size;
    /** Specific file location of media item. */
    private String fileLocation;
    /** Track length of audio and video in seconds. */
    private double trackLength;
    /** Resolution of images and videos. */
    private String resolution;    // Make separate constructor for videos to make unique
    /** Describes if file is usable.
     * Manually created files are not. */
    private boolean usability;

    /**
     * Media item constructor.
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

    // NEED EMPTY CONSTRUCTOR FOR JACKSON
    public MediaItem(){
    }

    /**
     * Creates specifically formatted line of media item information
     * for the media library file.
     * @return formatted media item string.
     */
    public String printAllItemDetails(){
        String s1 = "";
        String use = "";
        if(!usability){
            use = "NOT USABLE";
        }
        else{
            use = "USABLE";
        }

        s1 = String.format("%s\n%s\n%s\n%o\n%.2f(MB)\n%.2f seconds\n%s\n%s\n%s",
                this.mediaName, this.mediaType, this.format, this.itemID,
                this.size, this.trackLength, this.resolution, this.fileLocation, use);

        return s1;
    }
    // DONT DELTE ANU
    public String getMediaName() {return mediaName;}
    public String getMediaType() {return mediaType;}
    public String getFormat() {return format;}
    public double getSize() {return size;}
    public String getFileLocation() {return fileLocation;}
    public double getTrackLength(){return trackLength;}
    public String getResolution(){return resolution;}
    public boolean getUsability(){return usability;}
}

