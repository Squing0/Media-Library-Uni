public class Search {
    public MediaItem searchLocation(String location){

        // Have 2 if statements, one for videos and audio and one for images
        String name = "Sunset";
        String format = "JPG";
        String type = "Image";
        double size = 50d;  //In Mb
        int itemId = 1;


        // pretend as though this one is for images

        return new MediaItem(name, type, format, itemId, size, location);
    }

    public void importItems(int libraryID){ //Make plural as in items?
        MediaItem newItem = searchLocation("lol");
        // Want to add to media library but also don't want to have to instantiate one
    }

    public void searchLibrary(int libraryID){
        // need to have if statements for name, format, type, size, ID
    }
}
