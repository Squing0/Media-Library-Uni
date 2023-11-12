public class Main {
    public static void main(String[] args) {
        MediaItem item1 = new MediaItem("Sunset", "Image", "PNG", 1, 50, "lol" );
        MediaItem item2 = new MediaItem("Winter", "Image", "JPG", 2, 60, "lol" );
        MediaItem item3 = new MediaItem("Autumn", "Image", "PNG", 3, 70, "lol" );

        Playlist playlist1 = new Playlist("Random", "Image", 1);

        playlist1.addMedia(1,item1);
        playlist1.addMedia(2,item2);
        playlist1.addMedia(3,item3);

        playlist1.print();

        playlist1.deleteMedia(1,item1);

        playlist1.print();

        MediaLibrary library = new MediaLibrary("Library", 1, "lol");

        library.addMediaItem(1,item1);
        library.addMediaItem(2,item2);
        library.addMediaItem(3,item3);

        library.print();

        library.addPlaylist(1,playlist1);

        library.print();
    }
}