package MediaManagement;

/**
 * Media item class handles creation of media items.
 * @author Lyle Patterson
 */
public class MediaItem {
    /** Name of media item. */
    private String mediaName;
    /** Type of media item. Either image, audio or video. */
    private String mediaType;
    /** Format of media item (e.g., mp3). */
    private String format;
    /** Size of media item in megabytes. */
    private double size;
    /** Specific file location of media item. */
    private String fileLocation;
    /** Track length of audio and video in seconds.
     * (initialised to 0 for image)*/
    private double trackLength = 0;
    /** Resolution of images and videos. */
    private String resolution;
    /** Describes if file is usable.
     * Manually created files are not. */
    private boolean usability;

    /**
     * Video item constructor.
     * @param n item name.
     * @param t item type.
     * @param f item format.
     * @param s item size.
     * @param fl item file location.
     * @param tl item track length.
     * @param r item resolution.
     * @param u item usability.
     */
    public MediaItem(String n, String t, String f,double s, String fl, double tl, String r, boolean u){
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.size = s;
        this.fileLocation = fl;
        this.trackLength = tl;
        this.resolution = r;
        this.usability = u;
    }

    /**
     * Audio item constructor.
     * @param n item name.
     * @param t item type.
     * @param f item format.
     * @param s item size.
     * @param fl item file location.
     * @param tl item track length.
     * @param u item usability.
     */
    public MediaItem(String n, String t, String f, double s, String fl, double tl, boolean u){
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.size = s;
        this.fileLocation = fl;
        this.trackLength = tl;
        this.usability = u;
    }
    /**
     * Image item constructor.
     * @param n item name.
     * @param t item type.
     * @param f item format.
     * @param s item size.
     * @param fl item file location.
     * @param r item resolution.
     * @param u item usability.
     */
    public MediaItem(String n, String t, String f, double s, String fl, String r, boolean u){
        this.mediaName = n;
        this.mediaType = t;
        this.format = f;
        this.size = s;
        this.fileLocation = fl;
        this.resolution = r;
        this.usability = u;
    }


    /**
     * Empty constructor used here for jackson
     * library to model items off of.
     */
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

        if(mediaType.equals("Audio")){
            resolution = "No resolution";   // Shows null otherwise
        }

        s1 = String.format("%s\n%s\n%s\n%.2f(MB)\n%.2f seconds\n%s\n%s\n%s",
                this.mediaName, this.mediaType, this.format,
                this.size, this.trackLength, this.resolution, this.fileLocation, use);

        return s1;
    }

    /**
     * Getter
     * @return media item name
     */
    public String getMediaName() {return mediaName;}
    /**
     * Getter
     * @return media item type
     */
    public String getMediaType() {return mediaType;}
    /**
     * Getter
     * @return media item format
     */
    public String getFormat() {return format;}
    /**Getter. (unused getter needed for jackson library)
     * @return media item size.
     */
    public double getSize() {return size;}
    /**
     * Getter
     * @return media item location.
     */
    public String getFileLocation() {return fileLocation;}
    /**Getter. (unused getter needed for jackson library)
     * @return media item track length (if applicable).
     */
    public double getTrackLength(){return trackLength;}
    /**Getter. (unused getter needed for jackson library)
     * @return media item resolution (if applicable).
     */
    public String getResolution(){return resolution;}
    /**
     * Getter
     * @return media item usability.
     */
    public boolean getUsability(){return usability;}
}

