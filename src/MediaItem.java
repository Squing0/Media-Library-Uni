import java.io.*;

import it.sauronsoftware.jave.AudioAttributes;

public class MediaItem {
    private String mediaName = "";
    private String mediaType = "";  // Know mot really need to add values but lecturers might like it???
    private String format = "";
    private int itemID = 0;
    private double size = 0;
    private String fileLocation = "";
    private double trackLength = 0;
    private String resolution = "1920x1080";    // Make separate constructor for videos to make unique

    // Should call save method in constructors? Opinion seems to be that bad idea
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

    public void CreateMediaFileBasic(String fl, String mediaType) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fl, "rw");
        // Add try catch or have throws?
        file.close();

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

    public void AccessFileInfo(String fileLocation){
//        String filePath = fileLocation;
//        long sizeBytes = 1024;
//
////        File file = new File(filePath);
//
//        try{
////            boolean creationSuccess = file.createNewFile();
//
//            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
//            file.setLength(sizeBytes);
//
////            if(creationSuccess){
////                System.out.println("Yes");
////            }
////            else{
////                System.out.println("No");
////            }
//        }
//        catch (IOException e){
//            System.out.println("Oopsies");
//        }

        int width = 1920;
        int height = 1080;
        int duration = 30;
        int trackLength = 50;

        String ffmpegPath = "ffmpeg-2023-11-13-git-67a2571a55-full_build/bin/ffmpeg.exe";

        try{
            String[] command = {
                 ffmpegPath,
                 "ffmpeg",
                 "-f", "lavfi",
                 "-i", "anullscr=channel_layout=stereo:sample_rate=44100",
                 "-t", String.valueOf(duration),
                 "-vf", "color=c=black:s=" + width + "x" + height,
                 "-c:v", "libx264",
                 "-b:v", trackLength + "M",
                 "-pix_fmt", "yuv420p",
                 fileLocation
            };

            String[] command2 = {
                ffmpegPath,
                "ffmpeg",
                "-i", fileLocation,
                "-t", "30",
                "-c:a", "copy",
                "C:\\Users\\lylep\\IdeaProjects\\Media-Library-CW\\Created-Files\\OHNO.mp3"
            };

            String[] command3 = {
              ffmpegPath,
              "-i", fileLocation,
              "-hide_banner"    // Check if can get file size here
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command2);
            Process process = processBuilder.start();

//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String line;
//            while((line = reader.readLine()) != null){
//                System.out.println(line);
//            }



            process.waitFor();

            System.out.println("Video created!");

        }
        catch(IOException | InterruptedException e){
            e.printStackTrace();
        }


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
