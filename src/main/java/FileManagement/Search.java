package FileManagement;

import MediaManagement.MediaItem;
import MediaManagement.MediaLibrary;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Search {
    public void typeVerify2(String fl){

    }
    public void typeVerify(String itemFl, String libraryFl) throws IOException, InterruptedException {
        // Use this method to check if image, audio or video and if file type correct before extracting info
        String[] slashes = itemFl.split("/");
        String nameAndFormat = slashes[slashes.length - 1];

        // split using dot and use last instance in case name also has dots in it
        String[] dots = nameAndFormat.split("\\.");
        String name = dots[0];
        String format = dots[dots.length - 1];    // Make method???

        // Checking file format (also finds type)
        List<String> imageFormats = Arrays.asList("jpeg","png","gif");
        List<String> audioFormats = Arrays.asList("mp3", "aac", "wav");
        List<String> videoFormats = Arrays.asList("mp4", "mkv", "mov");

        String type = null; // Mame better later
        int ID = 1;
        double fileSize = getFileSize(itemFl);
        String resolution = null;
        double duration = 0;

        String resolutionCheck = " -i \"" + itemFl + "\" -show_entries stream=width,height -v quiet -of csv=p=0";
        String durationCheck = " -i \"" + itemFl + "\" -show_entries format=duration -v quiet -of csv=p=0";
        // " -i \"" + itemFl + "\" -show_entries format=duration -v quiet -of csv=p=0"; (try this if having problems)

        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryFl);

        if(!library.mediaItemAlreadyPresent(library, name, format)) { //Make simpler, how is the library location gotten?
            if(imageFormats.contains(format.toLowerCase())){
                type = "Image";
                resolution = getImageResolution(itemFl);
                MediaItem item = new MediaItem(name, type, format, ID, fileSize, itemFl, duration, resolution, true);
                library.addMedia(libraryFl, item);
            }
            else if(audioFormats.contains(format.toLowerCase())){
                type = "Audio";
                duration = Double.parseDouble(accessMediaSpecific(durationCheck));  // Need to parse back to double as string is returned
                MediaItem item = new MediaItem(name, type, format, ID, fileSize, itemFl,duration,"No resolution", true);
                library.addMedia(libraryFl, item);
            }
            else if(videoFormats.contains(format.toLowerCase())){
                type = "Video";
                duration = Double.parseDouble(accessMediaSpecific(durationCheck));  // Need to parse back to double as string is returned
                resolution = accessMediaSpecific(resolutionCheck);
                MediaItem item = new MediaItem(name, type, format, ID, fileSize, itemFl, duration, resolution.replace(",","x"), true);
                library.addMedia(libraryFl, item);
            }

            else{
                    System.out.println(nameAndFormat + "File is either not a media file or has unsupported file type!");
            }
        }
        else{
                System.out.println("File with that name and format already exists!");
        }
    }

    public void searchDirectory(String fd, String libraryFl) {
        Search search = new Search();

        File dir = new File(fd);
        File[] files = dir.listFiles();     // Assumes that there are no subdirectories
        for (File file : files){
            System.out.println();
            try {
                search.typeVerify(file.getAbsolutePath().replace("\\", "/"), libraryFl);
            }
            catch (IOException e) {
                System.out.println("There was an error handling the directory!");   // FIX THIS LATER
            }
            catch (InterruptedException e) {
                System.out.println("IDK");
            }
        }

        // Need to have error handling in case file, isn't media file
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

        double kilobytes = bytes / 1024;
        double megabytes = kilobytes / 1024;

        return megabytes;
    }
    public String getImageResolution(String fl)  {  // Use try and catch and finally clause?
        //getting resolution
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

    public String accessMediaSpecific(String action) { // awful name lol
        String ffprobePath = "ffmpeg-2023-11-13-git-67a2571a55-full_build/bin/ffprobe.exe";

        String command = ffprobePath + action;

        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
        Process process;
        String ffprobeOutput = "";

        try {
            process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while((line = reader.readLine()) != null){
                output.append(line);
            }

            ffprobeOutput = output.toString();
            reader.close();
        } catch (IOException e) {
            System.out.println("Command failed!");
        }


       return ffprobeOutput;
    }
}
