package GUI;

import MediaManagement.*;
import FileManagement.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Library page for specific media library.
 * @author Lyle Patterson
 */
public class LibraryPage extends JFrame implements FileObserver {
    /** Library object */
    private MediaLibrary library;
    /** File manager object */
    private FileManager fm;
    /** Thread for folder to be watched */
    private Thread folderThread;
    /** Unchanged json file */
    private File unchangedFile;
    /** Cloned json file */
    private File clonedFile;
    /** Specific path to library. */
    private String libraryPath;
    private JButton backToMain;
    private JButton importFolder;
    private JButton folderWatch;
    private JButton deleteMediaItem;
    private JButton searchMediaItem;
    private JButton createPlaylist;
    private JButton deletePlaylist;
    private JButton addPlaylistItem;
    private JButton removePlaylistItem;
    private JButton openMediaItem;
    private JButton openPlaylistItem;
    private JScrollPane mediaItems;
    private JList mediaItemsList;
    private DefaultListModel<String> mediaItemsModel;
    private JComboBox<String> playlists;
    private DefaultComboBoxModel<String> playlistsModel;
    private JScrollPane playlistItems;
    private JList playlistItemsList;
    private DefaultListModel<String> playlistItemsModel;
    private JTabbedPane createItemPane;
    private JComboBox typeEnter;
    private JTextField durationEnter;
    private JComboBox resolutionEnter;
    private JButton subItem;
    private JTextField nameEnter;
    private String[] imageFormats;
    private String[] audioFormats;
    private String[] videoFormats;
    private JComboBox formatEnter;
    private JTextField sizeEnter;
    JLabel title;

    /**
     * Library page constructor called when frame is initialised.
     * @param lp Location of library file.
     */
    public LibraryPage(String lp){
        this.libraryPath = lp;  // location set

        // Top panel label
        title = new JLabel("Library: " + libraryPath);

        // Defining buttons and adding listeners:
        defineButtons();
        addButtonListeners();

        // Tabbed pane
        createItemPane = new JTabbedPane();

        createItemPane.add(defineGetNameTab());
        createItemPane.add(defineGetDetailsTab());
        createItemPane.add(defineGetSubmitTab());

        //Defining exit button
        defineExitButton();

        // Defining scroll panes
        defineMediaItemPane();
        definePlaylistPane();
        definePlaylistItemsPane();

        // Adding all main panels to frame and setting border layout
        this.setLayout(new BorderLayout());

        this.add(defineGetTopPanel(), BorderLayout.NORTH);
        this.add(defineGetBottomPanel(), BorderLayout.SOUTH);
        this.add(defineGetRightPanel(), BorderLayout.EAST);
        this.add(defineGetLeftPanel(), BorderLayout.WEST);
        this.add(defineGetCenterPanel(), BorderLayout.CENTER);

        // Main frame details.
        this.setSize(1000,600);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Finalising frame.
        this.setVisible(true);

        // Setting up threading.
        fm = new FileManager(this::onFileChanged);  //File observer interface used to interact with file manager
        folderThread = new Thread(fm);
        folderThread.start();   // Thread started when frame initialised.

        // Loading library data to UI.
        loadData();

        // Creating temporary library file in case user doesn't save.
        try {
            setUpFiles();
        } catch (IOException e) {
            System.out.println("There was an error handling the file");
        }
    }

    /**
     * Searches for media item in media item list.
     */
    public void searchForItem() {
        String name = JOptionPane.showInputDialog("Enter name to search");
        String type = findType();

        library = library.getLibraryFromJson(libraryPath);
        Search search = Search.getInstance();
        MediaItem item = search.searchForItem(library, name, type); //Searches using a name and type

        if(item != null){
            JOptionPane.showMessageDialog(this, item.printAllItemDetails());
            int choice = JOptionPane.showConfirmDialog(this,
                    "Open media item?",
                    "Open?",
                    JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){   //User can't open manually created file
                if(!item.getUsability()){
                    JOptionPane.showMessageDialog(null,
                            "File isn't usable as was manually created!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return; //ends method
                }

                String path = item.getFileLocation();
                fm.openMediaItem(path); //opens file if usable
            }
        }
        else{   //Checks if item is null
            JOptionPane.showMessageDialog(null,
                    "File name with that type does not exist!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows item details when item is double-clicked.
     * @param e Mouse event
     */
    public void mouseDetails(MouseEvent e){
        JList<String> list = (JList<String>) e.getSource();

        int selectedIndex = list.getSelectedIndex();    //index allows element to be gotten
        String selectedItem = list.getModel().getElementAt(selectedIndex);

        MediaItem item = getMediaItemFromList(selectedItem);    //Item is gotten and details are printed with item method
        String details = item.printAllItemDetails();

        JOptionPane.showMessageDialog(null,
                details,
                "Success!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Folder is set to be watched
     */
    public void setFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(".")); // Only directories can be selected. Chooser opens at media library directory.

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.CANCEL_OPTION){
            return; // For if user cancels
        }

        String path;
        fm = new FileManager(this::onFileChanged);  //File observer interface used to interact with file manager.

        if(response == JFileChooser.APPROVE_OPTION){
           path = fileChooser.getSelectedFile().getAbsolutePath().replace("\\", "/");

           fm.setFolderLocation(path); //Sets path of folder to be watched
           fm.setLibraryPath(libraryPath);

           if(folderThread != null){    // Ensures thread is active.
               folderThread.interrupt();  // Signals to file manager that folder has been changed.

               try{
                   folderThread.join(); // Waits for thread to fully finish.
               }
               catch (InterruptedException e){
                  System.out.println("Interruption in thread!");
               }
           }

            folderThread = new Thread(fm);  // New thread is started with new folder path.
            folderThread.start();
        }
    }

    /**
     * Creates clone file when class is initialised
     * that that holds state of page when created
     * @throws IOException if there is an error handling the file.
     */
    public void setUpFiles() throws IOException {
        String[] fullPath = libraryPath.split("/");
        String[] nameAndFormat = fullPath[1].split("\\.");
        String name = nameAndFormat[0];
        String format = nameAndFormat[1];

        unchangedFile = new File(libraryPath);  //Defines original file.

        String libraryName = name + "json";
        clonedFile = new File(libraryName); // Defines clone file.

        String finalName = name + "C." + format; // Adds a C for copy when the copy is used
        clonedFile = new File("Media-Libraries/", finalName);   // Copy is added to media libraries directory.

        try {
            Files.copy(unchangedFile.toPath(), clonedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);   //Cloned file copies contents of original
        }
        catch (IOException e){
            System.out.println("Error handling file.");
        }
    }

    /**
     * If user chooses to save, then clone is deleted.
     */
    public void saveChoice(){
        clonedFile.delete();
    }

    /**
     * If user chooses not to save, then original is repalced with clone.
     */
    public void notSaveChoice(){
        unchangedFile.delete();
    }

    /**
     * Asks user if they want to save or not while exiting frame.
     */
    public void goBack(){
        int choice = JOptionPane.showConfirmDialog(this,
                "Save changes?",
                "Options:",
                JOptionPane.YES_NO_OPTION);

        if(choice == JOptionPane.YES_OPTION){
            saveChoice();   // Saves changes
        }
        else {
            notSaveChoice();    // Doesn't save changes
        }

        SwingUtilities.invokeLater(MainPage::new);  //Invoke later used to instantiate frame on EDT.
        dispose();
    }

    /**
     * Checks item type selected in tabs and changes formats given accordingly.
     */
    public void checkItemType() {
       String type = (String) typeEnter.getSelectedItem();

        switch (type) {
            case "Image" -> {
                durationEnter.setEditable(false);
                resolutionEnter.setEnabled(true);   // Images can't select duration
                formatEnter.removeAllItems();

                for (String iType : imageFormats) { // Adds foramts depending on type
                    formatEnter.addItem(iType);
                }
            }
            case "Audio" -> {
                durationEnter.setEditable(true);
                resolutionEnter.setEnabled(false);  // Audio can't select resolution.
                formatEnter.removeAllItems();

                for (String aType : audioFormats) {
                    formatEnter.addItem(aType);
                }
            }
            case "Video" -> {
                durationEnter.setEditable(true);
                resolutionEnter.setEnabled(true);
                formatEnter.removeAllItems();

                for (String vType : videoFormats) {
                    formatEnter.addItem(vType);
                }
            }
        }
    }

    /**
     * Creates media item based on info entered in tabs.
     */
    public void submitItem(){
        if(checkMediaItemEntered()){    // Checks if item details have all been entered.

            //Gets each item variable
            String name = nameEnter.getText();
            String type = (String) typeEnter.getSelectedItem();
            String format = (String) formatEnter.getSelectedItem();
            double size = Double.parseDouble(sizeEnter.getText());
            boolean use = false;
            String fl = "Created-Files/" + name + "." + format;

            String resolution = "No resolution";
            double trackLength = 0;

            MediaItem item = new MediaItem();   // Not sure if it will work as also initialised below

            //Different constructor used depending on item type
            if(type.equals("Video")){
                resolution = (String) resolutionEnter.getSelectedItem();
                trackLength = Double.parseDouble(durationEnter.getText());
                item = new MediaItem(name, type, format, size, fl, trackLength, resolution, use);
            }
            if(type.equals("Image")){
                resolution = (String) resolutionEnter.getSelectedItem();
                item = new MediaItem(name, type, format, size, fl, resolution, use);
            }
            if(type.equals("Audio")){
                trackLength = Double.parseDouble(durationEnter.getText());
                item = new MediaItem(name, type, format, size, fl, trackLength,use);
            }

            library = new MediaLibrary();
            library.addMedia(libraryPath, item);    // Item added to library

            fm.createMediaFile(fl); //Literal file created.

            loadData(); // Main UI updated.

            JOptionPane.showMessageDialog(null,
                    "Process finished",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        }
        else{
            JOptionPane.showMessageDialog(null,
                    "Enter all details in correct format!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes item from media library list and library
     */
    public void deleteItemList() {
        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        int selectedIndex = mediaItemsList.getSelectedIndex();  // index used to get element.

        if(selectedIndex != -1){    // Have to be selecting item.
            String itemInfo = mediaItemsModel.getElementAt(selectedIndex);
            MediaItem item = getMediaItemFromList(itemInfo);

            library.deleteMediaItem(libraryPath, item.getMediaName(), item.getFormat());    //item deleted with name and format

            //If the file was manually created, delete the file associated with it.
            if(!item.getUsability()){
                fm = new FileManager();
                fm.deleteFile("Created-Files",
                        item.getMediaName() + "." + item.getFormat());
            }

            JOptionPane.showMessageDialog(null,
                    item.getMediaName() + " deleted!",
                    "Success!",
                    JOptionPane.INFORMATION_MESSAGE);

            loadData(); // Update main UI
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No media item selected",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Create playlist object and add to UI and library
     */
    public void createPlaylist(){
        String name = JOptionPane.showInputDialog("What is your playlist name?: ");

        if(name == null){   // If user wants to exit.
            return;
        }
        String type = findType();

        Playlist playlist = new Playlist(name, type);

        library = new MediaLibrary();
        library.addPlaylist(libraryPath, playlist);

        loadData(); // Update main ui
    }

    /**
     * Allows user to choose media type.
     * @return type chosen.
     */
    public String findType(){
        String[] responses = {"Image", "Audio", "Video"};   // Array of 3 created for yes, no, cancel dialog.
        String type = "";

        int index = JOptionPane.showOptionDialog(null,
                "Select item",
                "Item:",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                responses,
                0);

        if(index == 0){
            return type = "Image";
        }
        else if(index == 1){
            return type = "Audio";
        }
        else if(index == 2){
            return type = "Video";
        }
        return type;
    }

    /**
     * Deletes playlist from library and UI.
     */
    public void deletePlaylist(){
        String specificPlaylist = (String) playlists.getSelectedItem();
        String[] nameAndType = specificPlaylist.split(",");
        String name = nameAndType[0];
        String type = nameAndType[1].trim();    //Splits library with comma to get name and type

        library = new MediaLibrary();
        library.deletePlaylist(libraryPath, name, type);    // Deletes playlist from library

        JOptionPane.showMessageDialog(null,
                name + " deleted!",
                "Success!",
                JOptionPane.INFORMATION_MESSAGE);

        loadData(); // Update UI
    }

    /**
     * Adds item to playlist in library and UI
     */
    public void addItemPlaylistList(){
        String specificPlaylist = (String) playlists.getSelectedItem();
        String[] nameAndType = specificPlaylist.split(",");
        String name = nameAndType[0];
        String type = nameAndType[1].trim(); //Splits library with comma to get name and type

        int selectedIndex = mediaItemsList.getSelectedIndex();  // index allows element to be gotten

        if(selectedIndex != -1){    // User has to be selecting item
            String itemInfo = mediaItemsModel.getElementAt(selectedIndex);
            MediaItem item = getMediaItemFromList(itemInfo);

            library = new MediaLibrary();
            library.addItemPlaylist(libraryPath, name, type, item); // Adds item to playlist with name and type

            loadData(); // Update main UI
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No playlist item selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes playlist item from library and UI
     */
    public void removePlaylistItemList() {
        int selectedIndex = playlistItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = playlistItemsModel.getElementAt(selectedIndex);
            String nameAndFormat[] = itemInfo.split(",");
            String itemName = nameAndFormat[0];
            String itemFormat = nameAndFormat[2].trim();    // Gets playlist item name and format


            String specificPlaylist = (String) playlists.getSelectedItem();
            String nameAndType[] = specificPlaylist.split(",");
            String playlistName = nameAndType[0];
            String playlistType = nameAndType[1].trim();    // Gets playlist name and format


            library = new MediaLibrary();
            library.deleteItemPlaylist(libraryPath, playlistName, playlistType, itemName, itemFormat);  // Deletes item from library

            loadData(); // Update main UI
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No playlist item selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Checks if a media item has been fully entered
     * @return true if item details fully entered and false if not.
     */
    public boolean checkMediaItemEntered(){
        boolean nameNotEntered = nameEnter.getText().isEmpty(); // Makes sure user not entered null.
        boolean sizeNotEntered = sizeEnter.getText().isEmpty();

        String type = (String) typeEnter.getSelectedItem(); //Needed to check that 3 text areas were entered

        for(char c : sizeEnter.getText().toCharArray()){    //Make sure that size is numeric
            if(Character.isAlphabetic(c)){
                return false;
            }
        }

        if(type.equals("Image")){   // If image entered then duration doesn't need to be checked.
            return !nameNotEntered && !sizeNotEntered;
        }
        else{
        boolean durationNotEntered = durationEnter.getText().isEmpty();

            for(char c :  durationEnter.getText().toCharArray()){   // Making sure that duration isn't null and is numeric
                if(Character.isAlphabetic(c)){
                    return false;
                }
            }
            return !nameNotEntered && !sizeNotEntered && !durationNotEntered;
        }
    }

    /**
     * Opens and imports all files within folder
     */
    public void openFolder() {
        Search search = Search.getInstance();

        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        String dirPath = "";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(".")); //Allows user to choose folder, opens in media library app directory

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.CANCEL_OPTION){ // If user cancels then end
            return;
        }

        if(response == JFileChooser.APPROVE_OPTION){    // If user chooses folder then get path of directory.
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            dirPath = file.getAbsolutePath().replace("\\", "/");
        }

        search.searchDirectory(dirPath, libraryPath);   // Search through directory and import any media files.
        loadData(); // Update main UI.
    }

    /**
     * Gets specific media item from list.
     * @param info media item information.
     * @return media item found.
     */
    public MediaItem getMediaItemFromList(String info){
        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        String[] nameAndFormat = info.split(",");
        String name = nameAndFormat[0];
        String format = nameAndFormat[2].trim();    // Name and format found

        return library.findItem(library, name, format); // Item returned.
    }

    /**
     * Opens a media item.
     * @param list List of media items or playlist items
     * @param model model that uses either media items or playlist items.
     */
    public void openItem(JList list, DefaultListModel<String> model){
        fm = new FileManager();

        int selectedIndex = list.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = model.getElementAt(selectedIndex);

            MediaItem item = getMediaItemFromList(itemInfo);

            if(!item.getUsability()){   // If the item was manually created, then end method.
                JOptionPane.showMessageDialog(null,
                        "File isn't usable as was manually created!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String path = item.getFileLocation();
            fm.openMediaItem(path); //Opens item in file explorer with path from item.
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No item selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method from file observer interface
     * @param fl File location of file from folder being watched.
     */
    public void onFileChanged(Path fl){
        SwingUtilities.invokeLater(() -> {
            loadData(); // Invoke later used as involved in concurrent operation.
        });
    }

    /**
     * Updates UI for items, playlists and playlist items
     * when the frame is initialised and anytime a change is made.
     */
    public void loadData(){
        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        if(library != null){    // Ensures that library has items to get
            if(!library.getMediaItems().isEmpty()){ // Only updates if media items present
                updateMediaItems(getMediaItems());
            }

            playlistItemsModel.clear(); // Playlist model different as based off playlist combo box so simply cleared.

            if(!library.getPlaylists().isEmpty()){ // Only updates if playlists present
                updatePlaylists(getPlaylists());
            }
        }
    }
    /**
     * Updates media items in UI
     * @param items List of media items in library.
     */
    public void updateMediaItems(List<MediaItem> items){
        mediaItemsModel.clear();    //Clears item model
        for(MediaItem item : items){    //Loops through items and adds name, type and format with commas between.
            mediaItemsModel.addElement(item.getMediaName()
                    + ", " + item.getMediaType()
                    + ", " + item.getFormat());
        }
    }

    /**
     * Creates list of media items and gets from library.
     * @return list of media items.
     */
    public List<MediaItem> getMediaItems(){
        List<MediaItem> items;

        items = library.getMediaItems();
        return items;
    }

    /**
     * Updates playlists in UI.
     * @param playlists List of playlists in library.
     */
    public void updatePlaylists(List<Playlist> playlists){
        playlistsModel.removeAllElements(); //Combo box works differently than scroll pane so remove elements used here
            for(Playlist playlist : playlists){ // Loops through playlists and adds name and type with commas between.
                playlistsModel.addElement(playlist.getPlaylistName()
                        + ", " + playlist.getPlaylistType());
            }
    }

    /**
     * Creates list of playlists and gets from library.
     * @return list of playlists.
     */
    public List<Playlist> getPlaylists(){
        List<Playlist> playlists;

        playlists = library.getPlaylists();
        return playlists;
    }

    /**
     * Updates playlist items in UI
     * @param playlistItems List of playlist items in library.
     */
    public void updatePlaylistItems(List<MediaItem> playlistItems){
        playlistItemsModel.clear(); // Clears playlist items model.

        if(playlistItems != null){
            for(MediaItem item : playlistItems){    // Loops through media items in specific playlist
                playlistItemsModel.addElement(item.getMediaName() + //Adds name, type and format with commas between.
                        ", " + item.getMediaType() +
                        ", " + item.getFormat());
            }
        }
    }

    /**
     * Creates list of playlist items and gets from playlist in library.
     * @param name Name of playlist to be checked.
     * @param type Type of playlist to be checked.
     * @return List of playlist items.
     */
    public List<MediaItem> getPlaylistItems(String name, String type){
        List<MediaItem> playlistItems;

        Playlist specific = library.findPlaylist(library, name, type);  // Find specific playlist
        playlistItems = specific.getMediaItems();   // Retrieve items

        return playlistItems;   // Add to created list.
    }
    /**
     * Loads playlist items into playlist items list depending on playlist selected.
     */
    public void loadPlaylistItems() {
        String specificPlaylist = (String) playlists.getSelectedItem();

        if(specificPlaylist != null){
            String[] nameAndType = specificPlaylist.split(",");
            String name = nameAndType[0];
            String type = nameAndType[1].trim();    // Comma used to get info

            updatePlaylistItems(getPlaylistItems(name, type));  // Items updated with specific name and type of playlist.
        }
    }
    public void defineButtons(){
        openMediaItem = new JButton("Open media item");
        deleteMediaItem = new JButton("Delete media item");
        searchMediaItem = new JButton("Search for media item");
        importFolder = new JButton("Import media files from folder");
        folderWatch = new JButton("Select folder to be watched");

        createPlaylist = new JButton("Create Playlist");
        deletePlaylist = new JButton("Delete Playlist");
        addPlaylistItem = new JButton("Add item to playlist");
        removePlaylistItem = new JButton("Remove item from playlist");
        openPlaylistItem = new JButton("Open playlist item");
    }
    public void addButtonListeners(){
        openMediaItem.addActionListener(e -> openItem(mediaItemsList, mediaItemsModel));
        deleteMediaItem.addActionListener(e -> deleteItemList());
        searchMediaItem.addActionListener(e -> searchForItem());
        importFolder.addActionListener(e -> openFolder());
        folderWatch.addActionListener(e -> setFolder());

        createPlaylist.addActionListener(e -> createPlaylist());
        deletePlaylist.addActionListener(e -> deletePlaylist());
        addPlaylistItem.addActionListener(e -> addItemPlaylistList());
        removePlaylistItem.addActionListener(e -> removePlaylistItemList());
        openPlaylistItem.addActionListener(e -> openItem(playlistItemsList, playlistItemsModel));
    }
    public JPanel defineGetNameTab(){
        JPanel nameTypePanel = new JPanel();
        nameTypePanel.setBackground(Color.LIGHT_GRAY);
        nameTypePanel.setLayout(new GridLayout(4,0));
        nameTypePanel.setName("Item name and type");

        JLabel nameL = new JLabel("Name: ");
        JLabel typeL = new JLabel("Type: ");

        String[] types = {"Image", "Audio", "Video"};

        nameEnter = new JTextField();
        typeEnter = new JComboBox(types);
        typeEnter.addActionListener(e -> checkItemType());

        nameTypePanel.add(nameL);
        nameTypePanel.add(nameEnter);
        nameTypePanel.add(typeL);
        nameTypePanel.add(typeEnter);

        return nameTypePanel;
    }
    public JPanel defineGetDetailsTab(){
        JPanel itemMainDetails = new JPanel();
        itemMainDetails.setBackground(Color.PINK);
        itemMainDetails.setLayout(new GridLayout(4,2));
        itemMainDetails.setName("Main item details");

        JLabel formatL = new JLabel("Format: ");
        JLabel sizeL = new JLabel("Size: ");
        JLabel durationL = new JLabel("Duration: ");
        JLabel resolutionL = new JLabel("Resolution: ");

        imageFormats = new String[]{"jpeg", "png", "gif"};
        audioFormats = new String[]{"mp3", "aac", "wav"};
        videoFormats = new String[]{"mp4", "mkv", "mov"};

        String[] resolutions = {"320x240", "480x360", "640x480", "1280x720", "1920x1080"};

        formatEnter = new JComboBox(imageFormats);
        sizeEnter = new JTextField();
        durationEnter = new JTextField();
        resolutionEnter = new JComboBox(resolutions);

        itemMainDetails.add(formatL);
        itemMainDetails.add(formatEnter);
        itemMainDetails.add(sizeL);
        itemMainDetails.add(sizeEnter);
        itemMainDetails.add(durationL);
        itemMainDetails.add(durationEnter);
        itemMainDetails.add(resolutionL);
        itemMainDetails.add(resolutionEnter);

        durationEnter.setEditable(false);

        return itemMainDetails;
    }
    public JPanel defineGetSubmitTab(){
        JPanel submitPanel = new JPanel();
        subItem = new JButton("Submit");
        subItem.addActionListener(e -> submitItem());

        submitPanel.setName("Submit item");
        submitPanel.setBackground(new Color(24, 240, 151));
        submitPanel.add(subItem);

        return submitPanel;
    }
    public void defineExitButton(){
        backToMain = new JButton("Go back!");
        backToMain.addActionListener(e -> goBack());
    }
    public void defineMediaItemPane(){
        mediaItemsModel = new DefaultListModel<>();
        mediaItemsList = new JList<>(mediaItemsModel);

        mediaItemsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    mouseDetails(e);
                }
            }
        });

        mediaItems = new JScrollPane(mediaItemsList);
        JLabel itemsHeading = new JLabel("Media items:");
        mediaItems.setColumnHeaderView(itemsHeading);
    }
    public void definePlaylistPane(){
        playlistsModel = new DefaultComboBoxModel<>();
        playlists = new JComboBox<>(playlistsModel);    // does accept model
        playlists.addActionListener(e -> loadPlaylistItems());
    }
    public void definePlaylistItemsPane(){
        playlistItemsModel = new DefaultListModel<>();
        playlistItemsList = new JList<>(playlistItemsModel);
        playlistItems = new JScrollPane(playlistItemsList); // doesn't accept model

        playlistItemsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    mouseDetails(e);
                }
            }
        });

        JLabel playlistItemsHeading = new JLabel("Playlist Items:");
        playlistItems.setColumnHeaderView(playlistItemsHeading);
    }
    public JPanel defineGetCenterPanel(){
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0,3,10,0));

        centerPanel.add(mediaItems);
        centerPanel.add(playlists);
        centerPanel.add(playlistItems);

        return centerPanel;
    }
    public JPanel defineGetBottomPanel(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(24, 240, 151));
        bottomPanel.setPreferredSize(new Dimension(100,35));
        bottomPanel.setLayout(new GridLayout(0,3));

        JLabel clickDetails = new JLabel("(Double click to see media item details)");

        bottomPanel.add(clickDetails);
        bottomPanel.add(backToMain);
        bottomPanel.add(new JLabel());

        return bottomPanel;
    }
    public JPanel defineGetRightPanel(){
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(200,100));
        rightPanel.setLayout(new GridLayout(6,0,0,10));

        rightPanel.add(createPlaylist);
        rightPanel.add(deletePlaylist);
        rightPanel.add(new JLabel()); // Made so can add empty cell
        rightPanel.add(addPlaylistItem);
        rightPanel.add(removePlaylistItem);
        rightPanel.add(openPlaylistItem);

        return rightPanel;
    }
    public JPanel defineGetLeftPanel(){
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(220,100));
        leftPanel.setLayout(new GridLayout(5,0,0,10));

        leftPanel.add(openMediaItem);
        leftPanel.add(deleteMediaItem);
        leftPanel.add(searchMediaItem);
        leftPanel.add(importFolder);
        leftPanel.add(folderWatch);

        return leftPanel;
    }
    public JPanel defineGetTopPanel(){
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(24, 240, 151));
        topPanel.setPreferredSize(new Dimension(100,100));
        topPanel.setLayout(new GridLayout(0,2));
        topPanel.add(createItemPane);
        topPanel.add(title);

        return topPanel;
    }
}