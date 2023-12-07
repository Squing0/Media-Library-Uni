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

public class LibraryPage extends JFrame implements FileObserver {
    private MediaLibrary library;
    private FileManager fm;
    private Thread folderThread;
    private File unchangedFile;
    private File clonedFile;
    private JButton backToMain;
    private String libraryPath;
    private JButton importFolder;
    private JButton folderWatch;
    private JButton deleteMediaItem;
    private JButton searchMediaItem;    // Make actual methods have more specific names
    private JButton createPlaylist;
    private JButton deletePlaylist;
    private JButton addPlaylistItem;
    private JButton removePlaylistItem;
    private JButton openMediaItem;
    private JButton openPlaylistItem;
    private JScrollPane mediaItems;
    private JList mediaItemsList;
    private DefaultListModel<String> mediaItemsModel;
//    private JComboBox playlists;
//    private JList playlistList;
//    private DefaultListModel<String> playlistModel;
    private JComboBox<String> playlists;
    private JList playlistsList; // Trying to keep consistent, if not then explain
    private DefaultComboBoxModel<String> playlistsModel;
    private JScrollPane playlistItems;
    private JList playlistItemsList;
    private DefaultListModel<String> playlistItemsModel;
    private JTabbedPane createitemPane;
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

    public LibraryPage(String lp){
        this.libraryPath = lp;

        // Top panel label
        title = new JLabel("Library: " + libraryPath);

        // Defining buttons and adding listeners:
        defineButtons();
        addButtonListeners();

        // Tabbed pane
        createitemPane = new JTabbedPane();

        createitemPane.add(defineGetNameTab());
        createitemPane.add(defineGetDetailsTab());
        createitemPane.add(defineGetSubmitTab());

        //Defining exit button
        defineExitButton();

        // Defining scroll panes
        defineMediaItemPane();
        definePlaylistPane();
        definePlaylistItemsPane();

        // Adding all main panels to frame and setting layout
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
        fm = new FileManager(this::onFileChanged);
        folderThread = new Thread(fm);
        folderThread.start();

        // Loading library data to UI.
        loadData();

        // Creating temporary library file in case user doesn't save.
        try {
            setUpFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchForItem() {
        String name = JOptionPane.showInputDialog("Enter name to search");
        String type = findType();

        library = library.getLibraryFromJson(libraryPath);
        Search search = Search.getInstance();
        MediaItem item = search.searchForItem(library, name, type);

        if(item != null){
            JOptionPane.showMessageDialog(this, item.printAllItemDetails());
            int choice = JOptionPane.showConfirmDialog(this,
                    "Open media item?",
                    "Open?",
                    JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){
                if(!item.getUsability()){
                    JOptionPane.showMessageDialog(null,
                            "File isn't usable as was manually created!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String path = item.getFileLocation();
                fm.openMediaItem(path);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "File name with that type does not exist!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void mouseDetails(MouseEvent e){
        JList<String> list = (JList<String>) e.getSource();

        int selectedIndex = list.getSelectedIndex();
        String selectedItem = list.getModel().getElementAt(selectedIndex);

        MediaItem item = getMediaItemFromList(selectedItem);
        String details = item.printAllItemDetails();

        JOptionPane.showMessageDialog(null,
                details,
                "Success!",
                JOptionPane.INFORMATION_MESSAGE);

        System.out.println(item.getMediaName());
    }
    public void setFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showOpenDialog(null);
        String path;

        fm = new FileManager(this::onFileChanged);

        if(response == JFileChooser.APPROVE_OPTION){
           path = fileChooser.getSelectedFile().getAbsolutePath().replace("\\", "/");

           fm.setFolderLocation(path);
           fm.setLibraryPath(libraryPath);

           if(folderThread != null){    // Ensures thread is active
               folderThread.interrupt();  // Signals to filemanager

               try{
                   folderThread.join(); // Waits for thread to fully finish
               }
               catch (InterruptedException e){
                  System.out.println("Interruption in thread!");
               }
           }

//            try {
//                folderThread.join();
//            }
//            catch (InterruptedException e){
//                System.out.println("Interrruption in thread!");
//            }

            folderThread = new Thread(fm);
            folderThread.start();
        }
    }

    public void setUpFiles() throws IOException {
        String[] fullPath = libraryPath.split("/");
        String[] nameAndFormat = fullPath[1].split("\\.");
        String name = nameAndFormat[0];
        String format = nameAndFormat[1];

        unchangedFile = new File(libraryPath);

      String libraryName = name + "json";
        clonedFile = new File(libraryName);

        String finalName = name + "C." + format; // Can't add dot alone for some reason
        clonedFile = new File("Media-Libraries/", finalName);

        try {
            Files.copy(unchangedFile.toPath(), clonedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            System.out.println("Error!");
        }
    }

    public void saveChoice(){
        clonedFile.delete();
    }

    public void notSaveChoice(){
        unchangedFile.delete();
    }
    public void checkItemType() {
       String type = (String) typeEnter.getSelectedItem();

        switch (type) {
            case "Image" -> {
                durationEnter.setEditable(false);
                resolutionEnter.setEnabled(true);
                formatEnter.removeAllItems();

                for (String iType : imageFormats) {
                    formatEnter.addItem(iType);
                }
            }
            case "Audio" -> {
                durationEnter.setEditable(true);
                resolutionEnter.setEnabled(false);
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

    public void submitItem(){
        if(checkMediaItemEntered()){
            System.out.println("items entered");

            String name = nameEnter.getText();
            String type = (String) typeEnter.getSelectedItem();
            String format = (String) formatEnter.getSelectedItem();
            double size = Double.parseDouble(sizeEnter.getText());
            boolean use = false;
            String fl = "Created-Files/" + name + "." + format;

            String resolution = "No resolution";
            double trackLength = 0;

            MediaItem item = new MediaItem();   // Not sure if will work as also initialised below

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
            library.addMedia(libraryPath, item);

            fm.createMediaFileBasic(fl);

            loadData();

            JOptionPane.showMessageDialog(null,
                    "Item added!",
                    "Success!",
                    JOptionPane.INFORMATION_MESSAGE);

        }
        else{
            JOptionPane.showMessageDialog(null,
                    "Enter all details in correct format!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteitemList() {
        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        int selectedIndex = mediaItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = mediaItemsModel.getElementAt(selectedIndex);
            MediaItem item = getMediaItemFromList(itemInfo);

            library.deleteMediaItem(libraryPath, item.getMediaName(), item.getFormat());    // Look for more efficient way

            if(!item.getUsability()){
                fm = new FileManager();
                fm.deleteFile("Created-Files",
                        item.getMediaName() + "." + item.getFormat());
            }
            JOptionPane.showMessageDialog(null,
                    item.getMediaName() + " deleted!",
                    "Success!",
                    JOptionPane.INFORMATION_MESSAGE);

            loadData();
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No media item selected",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createPlaylist(){   // name too similar to component
        String name = JOptionPane.showInputDialog("What is your playlist name?: ");
        String type = findType();

        Playlist playlist = new Playlist(name, type);

        library = new MediaLibrary();
        library.addPlaylist(libraryPath, playlist);

        loadData();
    }

    public String findType(){

        String[] responses = {"Image", "Audio", "Video"};
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

    public void deletePlaylist(){
        String specificPlaylist = (String) playlists.getSelectedItem();
        String[] nameAndType = specificPlaylist.split(",");
        String name = nameAndType[0];
        String type = nameAndType[1].trim();

        library = new MediaLibrary();
        library.deletePlaylist(libraryPath, name, type);

        JOptionPane.showMessageDialog(null,
                name + " deleted!",
                "Success!",
                JOptionPane.INFORMATION_MESSAGE);

        loadData();
    }

    public void addItemPlaylistList(){
        String specificPlaylist = (String) playlists.getSelectedItem();
        String[] nameAndType = specificPlaylist.split(",");
        String name = nameAndType[0];
        String type = nameAndType[1].trim();

        int selectedIndex = mediaItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = mediaItemsModel.getElementAt(selectedIndex);
            MediaItem item = getMediaItemFromList(itemInfo);

            library = new MediaLibrary();

            try {
                library.addItemPlaylist(libraryPath, name, type, item);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            loadData();
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No playlist item selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removePlaylistItemList() {
        int selectedIndex = playlistItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = playlistItemsModel.getElementAt(selectedIndex);
            String nameAndFormat[] = itemInfo.split(",");
            String itemName = nameAndFormat[0];
            String itemFormat = nameAndFormat[2].trim();


            String specificPlaylist = (String) playlists.getSelectedItem();
            String nameAndType[] = specificPlaylist.split(",");
            String playlistName = nameAndType[0];
            String playlistType = nameAndType[1].trim();


            library = new MediaLibrary();
            library.deleteItemPlaylist(libraryPath, playlistName, playlistType, itemName, itemFormat);

            loadData();
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No playlist item selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean checkMediaItemEntered(){
        boolean nameNotEntered = nameEnter.getText().isEmpty();
        boolean sizeNotEntered = sizeEnter.getText().isEmpty();

        String type = (String) typeEnter.getSelectedItem(); //Needed to check that 3 text areas were entered

        for(char c : sizeEnter.getText().toCharArray()){
            if(Character.isAlphabetic(c)){
                return false;
            }
        }

        if(type.equals("Image")){
            return !nameNotEntered && !sizeNotEntered;
        }
        else{
        boolean durationNotEntered = durationEnter.getText().isEmpty();

            for(char c :  durationEnter.getText().toCharArray()){
                if(Character.isAlphabetic(c)){
                    return false;
                }
            }
            return !nameNotEntered && !sizeNotEntered && !durationNotEntered;
        }

//        boolean nameNotEntered = nameEnter.getText().isEmpty();
//        boolean sizeNotEntered = sizeEnter.getText().isEmpty();
//
//        String type = (String) typeEnter.getSelectedItem();
//
//        if ("Image".equals(type)) {
//            return !(nameNotEntered || sizeNotEntered);
//        } else {
//            boolean durationNotEntered = durationEnter.getText().isEmpty();
//            return !(nameNotEntered || sizeNotEntered || durationNotEntered);
//        }

    }

    public void openFolder() {
        Search search = Search.getInstance();

        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        String dirPath = "";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            dirPath = file.getAbsolutePath().replace("\\", "/");
        }

        search.searchDirectory(dirPath, libraryPath);
        loadData();
    }

    public MediaItem getMediaItemFromList(String info){
        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        String[] nameAndFormat = info.split(",");
        String name = nameAndFormat[0];
        String format = nameAndFormat[2].trim();
        return library.checkNameFormat(library, name, format);
    }

    public void openItem() {
        fm = new FileManager();

        int selectedIndex = mediaItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = mediaItemsModel.getElementAt(selectedIndex);

            MediaItem item = getMediaItemFromList(itemInfo);

            if(!item.getUsability()){
                JOptionPane.showMessageDialog(null,
                        "File isn't usable as was manually created!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String path = item.getFileLocation();
            fm.openMediaItem(path);
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No media item selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openPlaylistItem() {
        fm = new FileManager();

        int selectedIndex = playlistItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = playlistItemsModel.getElementAt(selectedIndex);

            MediaItem item = getMediaItemFromList(itemInfo);

            if(!item.getUsability()){
                JOptionPane.showMessageDialog(null,
                        "File isn't usable as was manually created!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String path = item.getFileLocation();
            fm.openMediaItem(path);
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No playlist item selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    public void loadPlaylistItems() {
        String specificPlaylist = (String) playlists.getSelectedItem();

        if(specificPlaylist != null){
            String[] nameAndType = specificPlaylist.split(",");
            String name = nameAndType[0];
            String type = nameAndType[1].trim();

            updatePlaylistItems(getPlaylistItems(name, type));
        }
    }

    public void loadData(){
        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        if(library != null){    // Deleting doesn't work if only one item present for items and playlists
            if(!library.getMediaItems().isEmpty()){
                updateMediaItems(getMediaItems());
            }

            playlistItemsModel.clear();

            if(!library.getPlaylists().isEmpty()){
                updatePlaylists(getPlaylists());
            }
        }
    }

    public void onFileChanged(Path fl){
        SwingUtilities.invokeLater(() -> {
            loadData();
        });
    }

    public void loadMedia(){
        library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        if(library.getMediaItems() != null){
            updateMediaItems(getMediaItems());
        }
    }

    public void updateMediaItems(List<MediaItem> items){
        mediaItemsModel.clear();
        for(MediaItem item : items){
            mediaItemsModel.addElement(item.getMediaName()
                    + ", " + item.getMediaType()
                    + ", " + item.getFormat());
        }

    }

    public List<MediaItem> getMediaItems(){
        List<MediaItem> items;

        items = library.getMediaItems();
        return items;
    }

    public void updatePlaylists(List<Playlist> playlists){
//        playlistComboBoxModel.clear();
        playlistsModel.removeAllElements();
            for(Playlist playlist : playlists){
                playlistsModel.addElement(playlist.getPlaylistName()
                        + ", " + playlist.getPlaylistType());
            }

    }

    public List<Playlist> getPlaylists(){
        List<Playlist> playlists;

        playlists = library.getPlaylists();
        return playlists;
    }


    public void updatePlaylistItems(List<MediaItem> playlistItems){
        playlistItemsModel.clear();

        if(playlistItems != null){
            for(MediaItem item : playlistItems){
                playlistItemsModel.addElement(item.getMediaName() +
                        ", " + item.getMediaType() +
                        ", " + item.getFormat());
            }
        }
    }

    public List<MediaItem> getPlaylistItems(String name, String type){
        List<MediaItem> playlistItems;

        Playlist specific = library.findPlaylist(library, name, type);
        playlistItems = specific.getMediaItems();

        return playlistItems;
    }
    public void goBack(){
        int choice = JOptionPane.showConfirmDialog(this,
                "Save changes?",
                        "Options:",
                JOptionPane.YES_NO_OPTION);

        if(choice == JOptionPane.YES_OPTION){
            saveChoice();
        }
        else {
            notSaveChoice();
        }

        // Look for right place to use invoke later
        SwingUtilities.invokeLater(MainPage::new);
        dispose();
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
        openMediaItem.addActionListener(e -> openItem());
        deleteMediaItem.addActionListener(e -> deleteitemList());
        searchMediaItem.addActionListener(e -> searchForItem());
        importFolder.addActionListener(e -> openFolder());
        folderWatch.addActionListener(e -> setFolder());

        createPlaylist.addActionListener(e -> createPlaylist());
        deletePlaylist.addActionListener(e -> deletePlaylist());
        addPlaylistItem.addActionListener(e -> addItemPlaylistList());
        removePlaylistItem.addActionListener(e -> removePlaylistItemList());
        openPlaylistItem.addActionListener(e -> openPlaylistItem());
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
        topPanel.add(createitemPane);
        topPanel.add(title);

        return topPanel;
    }
}
