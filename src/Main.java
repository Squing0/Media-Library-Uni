import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String name = "Eh";
        String format = "mp3";
        String mediaType = "Audio";
        double fileSize = 1d;
        String resolution = "1920x1080";
        double trackLength = 30;


        String fileLocation = "Created-Files/" + name + "." + format;

        String fileLocation2 = "Created-Files/" + name;

        //Creating image

//        MediaItem item1 = new MediaItem(name, "Image", format, 1, 50, fileLocation , resolution);
//        item1.CreateMediaFileBasic(item1.getFileLocation(), item1.getMediaType());

        //Creating Video
//        MediaItem item2 = new MediaItem(name, mediaType, format, 2, 30, fileLocation, trackLength, resolution);
//        item2.CreateMediaFileBasic(item2.getFileLocation(), item2.getMediaType());

        // Creating audio
//        MediaItem item3 = new MediaItem(name, mediaType, format, 2, fileSize, fileLocation, trackLength);
//        item3.CreateMediaFileBasic(item3.getFileLocation(), item3.getMediaType());

        //Accessing info from existing files
//        Search search = new Search();

        // Accessing an image
//        String fl = "C:/Users/lylep/Downloads/C4AvqGR2zXrN.gif";
//        search.AccessImageInfo(fl);

        //Accessing audio
//        String fl2 = "Created-Files/file_example_MP4_480_1_5MG.mp4";
//        String resolutionCheck = " -i " + fl2 + " -show_entries stream=width,height -v quiet -of csv=p=0";
//        String durationCheck = " -i " + fl2 + " -show_entries format=duration -v quiet -of csv=p=0";
//
//
//        String fd = "C:/Users/lylep/Downloads/Tester";
//        search.searchDirectory(fd);


//        search.typeVerify(fl2);
//        System.out.println("Width: " + width + " Height: " + height);
//        System.out.println("Absolute path of input:" + new File(fileLocation2).getAbsolutePath());
//        System.out.println("Absolute path of output" + new File("Created-Files/OHNO.mp3").getAbsolutePath());


        MediaItem item2 = new MediaItem("Winter", "Image", "jpg", 2, 60, "lol" ,"120x120");
        MediaItem item3 = new MediaItem("Autumn", "Image", "png", 3, 70, "lol" ,"400x600");
        MediaItem item4 = new MediaItem("Autumn", "Audio", "mp3", 3, 70, "lol" ,30);
        MediaItem item5 = new MediaItem("Spring", "Video", "mov", 3, 70, "lol" ,40,"400x600");
//
//        Playlist playlist1 = new Playlist("Random", "Image", 1);
//
//        playlist1.addMedia(2,item2);
//        playlist1.addMedia(3,item3);
//
//        playlist1.print();

        MediaLibrary library = new MediaLibrary("Library7", 1, "lol");

//        library.addMediaItem(2,item2);
        library.addMediaItem(3,item3);
        library.addMediaItem(4,item4);
        library.addMediaItem(5,item5);

//        library.addMediaItem(3,item3);
//
//        library.print();
//
//        library.addPlaylist(1,playlist1);
//
//        library.print();


    }
}