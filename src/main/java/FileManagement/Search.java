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

public class Search {
    private static final Search instance = new Search();
    private Search(){}
    public static Search getInstance(){
        return instance;
    }
    public void typeVerify2(String fl){

    }

    public void importFile(){

    }
    public void typeVerify(String itemFl, String libraryFl) throws InterruptedException, IOException {
        // Use this method to check if image, audio or video and if file type correct before extracting info
        String[] slashes = itemFl.split("/");
        String nameAndFormat = slashes[slashes.length - 1];

        // split using dot and use last instance in case name also has dots in it
        String[] dots = nameAndFormat.split("\\.");
        String name = dots[0];
        String format = dots[dots.length - 1];    // Make method???

        String formatCorrect = findFormat(format);

        String type;
        double fileSize = getFileSize(itemFl);
        String resolution;

        String resolutionCheck = " -i \"" + itemFl + "\" -show_entries stream=width,height -v quiet -of csv=p=0";
        String durationCheck = " -i \"" + itemFl + "\" -show_entries format=duration -v quiet -of csv=p=0";

        double duration = Double.parseDouble(accessMediaSpecific(durationCheck));

        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryFl);

        if(library.mediaItemAlreadyPresent(library, name, format)) { //Make simpler, how is the library location gotten?
            switch (formatCorrect) {
                case "Image" -> {
                    type = "Image";
                    resolution = getImageResolution(itemFl);

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
                    resolution = accessMediaSpecific(resolutionCheck);

                    MediaItem item = new MediaItem(name, type, format, fileSize, itemFl, duration, resolution.replace(",", "x"), true);
                    library.addMedia(libraryFl, item);
                }
                default ->
                        System.out.println(nameAndFormat + "File is either not a media file or has unsupported file type!");
            }
        }
        else{
            System.out.println("File with that name and format already exists!");
        }
    }

    public void searchDirectory(String fd, String libraryFl) {
        Search search = Search.getInstance();

        File dir = new File(fd);
        File[] files = dir.listFiles();     // Assumes that there are no subdirectories

        for (File file : files){
            try {
                search.typeVerify(file.getAbsolutePath().replace("\\", "/"), libraryFl);
            }
            catch (IOException e) {
                System.out.println("There was an error handling the directory!");   // FIX THIS LATER
            }
            catch (InterruptedException e) {
                System.out.println("Error handling file");
            }
        }
    }

    public MediaItem searchForItem(MediaLibrary library, String name, String type){
        for(MediaItem mediaItem: library.getMediaItems()){
            // LOOK INTO STREAMS OR BINARY SEARCH
            if (mediaItem.getMediaName().equals(name) && mediaItem.getMediaType().equals(type)){    // Just copied
                return mediaItem;
            }
        }

        return null;
    }

    public MediaItem binarySearchTrial(MediaLibrary library, String name, String type){   //change name
//        Vector<String> itemNames = new Vector<>();
//        Vector<String> itemTypes = new Vector<>();
//
//        for(MediaItem item : library.getMediaItems()){
//            itemNames.add(item.getMediaName());
//            itemTypes.add(item.getMediaType());
//        }
//
//        Collections.sort(itemNames);
//        Collections.sort(itemTypes);
//
//        boolean nameCheck = false;
//        boolean typeCheck = false;
//
//        for(String name2 : itemNames){
//            if(name2.equals(name)){
//                nameCheck = true;
//            }
//        }
//
//        for(String type2 : itemTypes){
//            typeCheck = true;
//        }
//
//        if(nameCheck && typeCheck){
//
//        }
//
//        return null;

        MediaItem item = new MediaItem(name, type, null, 0, null, 0, null, false);

        Comparator<MediaItem> comparator = Comparator.
                comparing(MediaItem::getMediaName).
                thenComparing(MediaItem::getMediaType);

        int index = Collections.binarySearch(library.getMediaItems(), item, comparator);

        if (index >= 0){
            return library.getMediaItems().get(index);
        }
        else{
            return null;
        }
    }
    public String findFormat(String format){
        List<String> imageFormats = Arrays.asList("jpeg","png","gif");
        List<String> audioFormats = Arrays.asList("mp3", "aac", "wav");
        List<String> videoFormats = Arrays.asList("mp4", "mkv", "mov");

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
    public double getFileSize(String fl) {
        Path path = Paths.get(fl);
        long bytes = 0;

        try {
            bytes = Files.size(path);
        }
        catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }

        double kilobytes = (double) bytes / 1024;

        return kilobytes / 1024;
    }
    public String getImageResolution(String fl)  {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(fl));
        } catch (IOException e) {
            System.out.println("There was an error handling the file!");
        }

        int height = image.getHeight();
        int width = image.getWidth();

        return height + "x" + width;
    }

    public String accessMediaSpecific(String action) {
        String ffprobePath = "ffmpeg-2023-11-13-git-67a2571a55-full_build/bin/ffprobe.exe";

        String command = ffprobePath + action;

        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+")); //Removes whitespace charactes
        Process process;
        String ffprobeOutput = "";
        String line;

        try {
            process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            line = reader.readLine();
            output.append(line);

            ffprobeOutput = output.toString();
            reader.close();
        } catch (IOException e) {
            System.out.println("Command couldn't execute!");
        }

       return ffprobeOutput;
    }
}
