package FileManagement;

import MediaManagement.MediaItem;
import MediaManagement.MediaLibrary;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Search class handles all functions related to searching.
 * @author Lyle Patterson
 */
public class Search {
    /** Instance of search class (using singleton here) */
    private static final Search instance = new Search();
    /** Empty constructor so instance can be gotten */
    private Search(){}

    /**
     * Getter
     * @return instance of search class.
     */
    public static Search getInstance(){
        return instance;
    }

    /**
     * Imports file to media library.
     * @param itemFl File location of media item.
     * @param libraryFl File location of media library.
     * @throws InterruptedException if method is interrupted.
     * @throws IOException if there is a problem handling the file.
     */
    public void typeVerify(String itemFl, String libraryFl) throws InterruptedException, IOException {
        // Split to find name.format of file
        String[] slashes = itemFl.split("/");
        String nameAndFormat = slashes[slashes.length - 1];

        // split using dot and use last instance in case name also has dots in it
        String[] dots = nameAndFormat.split("\\.");
        String name = dots[0];
        String format = dots[dots.length - 1];

        String formatCorrect = findFormat(format);  // Format is checked against specific formats.

        if(formatCorrect == null){
            System.out.println("File is either not a media file or has unsupported file type!\n");
            return; // Stops method if format isn't a media format.
        }

        // Defining media item parameters for switch statements.
        String type;
        double fileSize = getFileSize(itemFl);  // Gets size in megabytes
        String resolution;

        // Defines ffprobe commands and removes whitespaces
        String resolutionCheck = " -i \"" + itemFl + "\" -show_entries stream=width,height -v quiet -of csv=p=0";
        String durationCheck = " -i \"" + itemFl + "\" -show_entries format=duration -v quiet -of csv=p=0";

        // Double is gotten here as is same for audio and video
        double duration = Double.parseDouble(accessMediaSpecific(durationCheck));

        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryFl);

        if(!library.mediaItemAlreadyPresent(library, name, format)) {    //Checks if item is already present before adding.
            switch (formatCorrect) {    // Uses specific constructor depending on item and adds to library.
                case "Image" -> {
                    type = "Image";
                    resolution = getImageResolution(itemFl);    // If format is image, then specific method used

                    MediaItem item = new MediaItem(name, type, format, fileSize, itemFl, resolution, true);
                    library.addMedia(libraryFl, item);
                }
                case "Audio" -> {
                    type = "Audio";

                    MediaItem item = new MediaItem(name, type, format, fileSize, itemFl, duration, true);
                    library.addMedia(libraryFl, item);
                }
                case "Video" -> {
                    type = "Video";
                    resolution = accessMediaSpecific(resolutionCheck); // If format is resolution, then specific method used

                    // resolution formatted here
                    MediaItem item = new MediaItem(name, type, format, fileSize, itemFl, duration, resolution.replace(",", "x"), true);
                    library.addMedia(libraryFl, item);
                }
            }
        }
        else{
            System.out.println("File with that name and format already exists!");
        }
    }

    /**
     * Searches directory for any media files and imports files if found.
     * @param fd File location of file directory.
     * @param libraryFl File location of media library.
     */
    public void searchDirectory(String fd, String libraryFl) {
        Search search = Search.getInstance();

        File dir = new File(fd);    // Reference to directory made
        File[] files = dir.listFiles(); // Creates an array of files in directory

        for (File file : files){    // Loops through directory files individually
            try {
                // Replaces slashes and imports file.
                search.typeVerify(file.getAbsolutePath().replace("\\", "/"), libraryFl);
            }
            catch (IOException e) {
                System.out.println("There was an error handling the directory!");
            }
            catch (InterruptedException e) {
                System.out.println("There was an interruption.");
            }
        }
    }

    /**
     * Searches for media item in media item list.
     * @param library Media library object.
     * @param name Name of media item.
     * @param type Type of media item.
     * @return media item object if found and null if not.
     */
    public MediaItem searchForItem(MediaLibrary library, String name, String type){
        // Loops through each media item to check name and type.
        for(MediaItem mediaItem: library.getMediaItems()){

            if (mediaItem.getMediaName().equals(name) && mediaItem.getMediaType().equals(type)){
                return mediaItem;
            }
        }

        return null;
    }

    /**
     * Checks format against type arrays.
     * @param format media item format.
     * @return specific format if found and null if not.
     */
    public String findFormat(String format){
        // Specific formats defined for each media type
        List<String> imageFormats = Arrays.asList("jpeg","png","gif");
        List<String> audioFormats = Arrays.asList("mp3", "aac", "wav");
        List<String> videoFormats = Arrays.asList("mp4", "mkv", "mov");

        //Format made lowercase in case uppercase format is given.
        if(imageFormats.contains(format.toLowerCase())){
            return "Image";
        }
        else if(audioFormats.contains(format.toLowerCase())){
            return "Audio";
        }
        else if(videoFormats.contains(format.toLowerCase())){
            return "Video";
        }

        return null;
    }

    /**
     * Finds file size.
     * @param fl File location of file.
     * @return Size of file in megabytes.
     */
    public double getFileSize(String fl) {
        Path path = Paths.get(fl);
        long bytes = 0;

        try {
            bytes = Files.size(path);   // Files class used to find size
        }
        catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }

        double kilobytes = (double) bytes / 1024;   // Bytes to kilobytes

        return kilobytes / 1024;    // Kilobytes to megabytes
    }

    /**
     * Gets resolution of image in specific format.
     * @param fl File location of file.
     * @return resolution of image.
     */
    public String getImageResolution(String fl)  {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(fl)); // BufferedImage class used to get information from image
        } catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }

        int height = image.getHeight();
        int width = image.getWidth();

        return height + "x" + width;    // x added so that correct format used
    }

    /**
     * Uses ffprobe to find information about media file.
     * @param action Added with ffmpeg path to create ffprobe command.
     * @return resolution or duration depending on action entered.
     */
    public String accessMediaSpecific(String action) {
        // File path of ffmpeg made relevant so that user doesn't have to install on machine.
        String ffprobePath = "ffmpeg-2023-11-13-git-67a2571a55-full_build/bin/ffprobe.exe";

        String command = ffprobePath + action;  // Full command created

        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+")); //Removes whitespace characters
        Process process;
        String ffprobeOutput = "";
        String line;

        try {
            process = processBuilder.start();   // Process builder is used to execute command

            //Input stream reader converts bytes to characters.
            //Buffered reader reads said characters.
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            line = reader.readLine();
            output.append(line);    //String builder used to read characters and add to final output

            ffprobeOutput = output.toString();
            reader.close();     // reader closed when information is found.
        } catch (IOException e) {
            System.out.println("Command couldn't execute!");
        }

       return ffprobeOutput;
    }
}
