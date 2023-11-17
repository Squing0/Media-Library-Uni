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

//        item1.AccessFileInfo(item1.getFileLocation());

        //Creating Video
//        MediaItem item2 = new MediaItem(name, mediaType, format, 2, 30, fileLocation, trackLength, resolution);
//        item2.CreateMediaFileBasic(item2.getFileLocation(), item2.getMediaType());

        // Creating audio
//        MediaItem item3 = new MediaItem(name, mediaType, format, 2, fileSize, fileLocation, trackLength);
//        item3.CreateMediaFileBasic(item3.getFileLocation(), item3.getMediaType());

//        BufferedImage bimg = ImageIO.read(new File("Created-Files/Sample_abc.jpg"));
//        int width = bimg.getWidth();
//        int height = bimg.getHeight();

        //Accessing info from existing files
        Search search = new Search();

        // Accessing an image
//        String fl = "C:/Users/lylep/Downloads/C4AvqGR2zXrN.gif";
//        search.AccessImageInfo(fl);

        //Accessing audio
        String fl2 = "Created-Files/file_example_MP3_1MG.mp3";
        String resolutionCheck = " -i " + fl2 + " -show_entries stream=width,height -v quiet -of csv=p=0";
        String durationCheck = " -i " + fl2 + " -show_entries format=duration -v quiet -of csv=p=0";

//        String lol =  search.AccessAudioInfo(durationCheck);
//        System.out.println(lol);

        search.typeVerify(fl2);
//        System.out.println("Width: " + width + " Height: " + height);
//        System.out.println("Absolute path of input:" + new File(fileLocation2).getAbsolutePath());
//        System.out.println("Absolute path of output" + new File("Created-Files/OHNO.mp3").getAbsolutePath());


//        MediaItem item2 = new MediaItem("Winter", "Image", "JPG", 2, 60, "lol" ,"");
//        MediaItem item3 = new MediaItem("Autumn", "Image", "PNG", 3, 70, "lol" ,"");
//
//        Playlist playlist1 = new Playlist("Random", "Image", 1);
//
//        playlist1.addMedia(1,item1);
//        playlist1.addMedia(2,item2);
//        playlist1.addMedia(3,item3);
//
//        playlist1.print();
//
//        playlist1.deleteMedia(1,item1);
//
//        playlist1.print();
//
//        MediaLibrary library = new MediaLibrary("Library", 1, "lol");
//
//        library.addMediaItem(1,item1);
//        library.addMediaItem(2,item2);
//        library.addMediaItem(3,item3);
//
//        library.print();
//
//        library.addPlaylist(1,playlist1);
//
//        library.print();


    }
}