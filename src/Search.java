import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class Search {
    public MediaItem searchLocation(String location){

        // Have 2 if statements, one for videos and audio and one for images
        String name = "Sunset";
        String format = "JPG";
        String type = "Image";
        long size = 50l;  //In Mb
        int itemId = 1;


        // pretend as though this one is for images

        return new MediaItem(name, type, format, itemId, size, location,"");
    }

    public void importItems(int libraryID){ //Make plural as in items?
        MediaItem newItem = searchLocation("lol");
        // Want to add to media library but also don't want to have to instantiate one
    }

    public void typeVerify(String fl) throws IOException, InterruptedException {
        // Use this method to check if image, audio or video and if file type correct before extracting info
        String[] slashes = fl.split("/");
        String nameAndFormat = slashes[slashes.length - 1];

        // split using dot and use last instance in case name also has dots in it
        String[] dots = nameAndFormat.split("\\.");
        String name = dots[0];
        String format = dots[dots.length - 1];    // Make method???

        // Checking file format (also finds type)
        List<String> imageFormats = Arrays.asList("jpg","jpeg","png","gif");
        List<String> audioFormats = Arrays.asList("mp3", "aac", "wav");
        List<String> videoFormats = Arrays.asList("mp4", "mkv", "mov");

        String type = ""; // Mame better later
        int ID = 1;
        double fileSize = 1;
        String resolution;
        double duration;

        String resolutionCheck = " -i " + fl + " -show_entries stream=width,height -v quiet -of csv=p=0";
        String durationCheck = " -i " + fl + " -show_entries format=duration -v quiet -of csv=p=0";

        if(imageFormats.contains(format.toLowerCase())){
            type = "Image";
            ID = 1;
            fileSize = getFileSize(fl);
            resolution = getImageResolution(fl);
            MediaItem item = new MediaItem(name, type, format, ID, fileSize, fl, resolution);
            item.printAll();
        }
        else if(audioFormats.contains(format.toLowerCase())){
            type = "Audio";
            ID = 1;
            fileSize = getFileSize(fl);
            duration = Double.parseDouble(accessMediaSpecific(durationCheck));  // Need to parse back to double as string is returned
            MediaItem item = new MediaItem(name, type, format, ID, fileSize, fl,duration);
            item.printAll();
        }
        else if(videoFormats.contains(format.toLowerCase())){
            type = "Video";
            ID = 1;
            fileSize = getFileSize(fl);
            duration = Double.parseDouble(accessMediaSpecific(durationCheck));
            resolution = accessMediaSpecific(resolutionCheck);
            MediaItem item = new MediaItem(name, type, format, ID, fileSize, fl, duration, resolution.replace(",","x"));
            item.printAll();
        }
        else{
            System.out.println("File is either not a media file or has unsupported file type!");
        }
    }

    public void searchDirectory(String fd) throws IOException, InterruptedException {
        Search search = new Search();

        File dir = new File(fd);
        File[] files = dir.listFiles();     // Assumes that there are no sub directories
        for (File file : files){
            System.out.println();
            search.typeVerify(file.getAbsolutePath().replace("\\", "/"));
        }
    }

    public double getFileSize(String fl) throws IOException {
        Path path = Paths.get(fl);
        long bytes = Files.size(path);
        double kilobytes = bytes / 1024;
        double megabytes = kilobytes / 1024;

        return megabytes;
    }
    public String getImageResolution(String fl) throws IOException {  // Use try and catch and finally clause?
        //getting resolution
        BufferedImage image = ImageIO.read(new File(fl));
        int width = image.getWidth();
        int height = image.getHeight();
        String resolution = width + "x" + height;

        return resolution;
        // Creating final file
//        MediaItem item = new MediaItem(name, type, format, ID, megabytes, fl, resolution);
//        item.printAll();
    }

    public String accessMediaSpecific(String fl) throws IOException, InterruptedException { // awful name lol
        String ffprobePath = "ffmpeg-2023-11-13-git-67a2571a55-full_build/bin/ffprobe.exe";

        String command = ffprobePath + fl;

        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();

        while((line = reader.readLine()) != null){
            output.append(line);
        }

        String ffprobeOutput = output.toString();
        return ffprobeOutput;
    }
    public void searchLibrary(int libraryID){
        // need to have if statements for name, format, type, size, ID
    }
}
