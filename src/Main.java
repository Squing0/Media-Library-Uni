import FileManageAndSearch.FileManager;
import MediaManagement.MediaItem;
import MediaManagement.MediaLibrary;
import MediaManagement.Playlist;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String name = "Lolsers2";
        String format = "mp3";
        String mediaType = "Audio";
        double fileSize = 3d;
        String resolution = "480x480";
        double trackLength = 40;


        String fileLocation = "Created-Files/" + name + "." + format;
        String fileLocation2 = "Created-Files/" + name;

       MediaItem item = new MediaItem(name, mediaType, format, 1, 20, fileLocation,trackLength, resolution, false);

        String fileandFormat = name + "." + format;

        MediaLibrary library = new MediaLibrary();


        Playlist playlist = new Playlist("Vibrations", "Audio", 1);

        String playlistNameDetails = "MediaManagement.Playlist, " + playlist.getPlaylistName() + ", " + playlist.getPlaylistType();

//        library.addItemSpecificPoint("Media-Libraries/Seasons2.txt", "Playlists:", playlistNameDetails);
        // Need to get item back from library and build new item for this to work
        // At same time, this info will be stored in GUI already so just go with this for now
        String libraryLocation = "Media-Libraries/Seasons2.txt";
//        playlist.addMedia(1, item, libraryLocation);

        // Checking before a playlist is created
        String playlistName = "Vibrations";
        String playlistType = "Audio";

//        if (library.checkPlaylistName(playlistName, playlistType, libraryLocation)){
//            System.out.println("MediaManagement.Playlist name " + playlistName + " of type " + playlistType + " already exists");
//        }
//        else{
//            System.out.println("MediaManagement.Playlist created!");
//        }

//        library.deleteSpecificLine("Media-Libraries/Seasons2.txt", "Summer.mp3");

        FileManager fm = new FileManager();
        fm.openMediaItem("Created-Files/Sample_abc.jpg");



//        if(library.nameAlreadyPresent(fileandFormat, "Media-Libraries/Library10.txt") == true){
//            System.out.println("File with same name and format already exists!");
//        }
//        else{
//            System.out.println("File Created!");
//        }

//        library.addItemSpecificPoint("Media-Libraries/Library9.txt", "Spring", item.printAllMediaLibrary());
//        FileManageAndSearch.Search search = new FileManageAndSearch.Search();
//        search.typeVerify("C:/Users/lylep/Downloads/file_example_MP4_480_1_5MG.mp4");

        //Creating image

//        MediaManagement.MediaItem item1 = new MediaManagement.MediaItem(name, "Image", format, 1, 50, fileLocation , resolution);
//        item1.CreateMediaFileBasic(item1.getFileLocation(), item1.getMediaType());

        //Creating Video
//        MediaManagement.MediaItem item2 = new MediaManagement.MediaItem(name, mediaType, format, 2, 30, fileLocation, trackLength, resolution);
//        item2.CreateMediaFileBasic(item2.getFileLocation(), item2.getMediaType());

        // Creating audio
//        MediaManagement.MediaItem item3 = new MediaManagement.MediaItem(name, mediaType, format, 2, fileSize, fileLocation, trackLength);
//        item3.CreateMediaFileBasic(item3.getFileLocation(), item3.getMediaType());

        //Accessing info from existing files
//        FileManageAndSearch.Search search = new FileManageAndSearch.Search();

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


//        MediaManagement.MediaItem item2 = new MediaManagement.MediaItem("Winter", "Image", "jpg", 2, 60, "Created-Files/Winter.jpg" ,"120x120", true);
//        MediaManagement.MediaItem item3 = new MediaManagement.MediaItem("Summer", "Image", "png", 3, 70, "Created-Files/Summer.png" ,"400x600", true);
//        MediaManagement.MediaItem item4 = new MediaManagement.MediaItem("Autumn", "Audio", "mp3", 3, 70, "Created-Files/Autumn.mp3" ,30, true);
//        MediaManagement.MediaItem item5 = new MediaManagement.MediaItem("Spring", "Video", "mov", 3, 70, "Created-Files/Spring.mov" ,40,"400x600", true);
//
//        MediaManagement.Playlist playlist1 = new MediaManagement.Playlist("Random", "Image", 1);
//
//        playlist1.addMedia(2,item2);
//        playlist1.addMedia(3,item3);
//
//        playlist1.print();

//        MediaManagement.MediaLibrary library = new MediaManagement.MediaLibrary("Library10", 1, "lol");
//
//        library.addMediaItem(2,item2);
//        library.addMediaItem(3,item3);
//        library.addMediaItem(4,item4);
//        library.addMediaItem(5,item5);

//        MediaManagement.MediaLibrary library = new MediaManagement.MediaLibrary();
//
//        library.addItemSpecificPoint("Media-Libraries/Library8.txt", "Autumn", "Text to insert");

//        library.addMediaItem(3,item3);
//
//        library.print();
//
//        library.addPlaylist(1,playlist1);
//
//        library.print();


    }
}