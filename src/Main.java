import java.io.File;

public class Main {
    public static void main(String[] args) {

        String name = "file_example_MP3_1MG.mp3";
        String format = "mp3";

        String fileLocation = "Created-Files/" + name + "." + format;

        String fileLocation2 = "Created-Files/" + name;

        MediaItem item1 = new MediaItem(name, "Image", format, 1, 50, fileLocation2 , "");

        item1.CreateMediafile(item1.getFileLocation());

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