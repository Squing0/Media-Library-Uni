package GUI;

import MediaManagement.*;
import FileManageAndSearch.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryPage extends JFrame{
    private JLabel label = new JLabel("Hello!");
    private JButton backToMain;
    private String libraryPath;
    private JButton manualMediaItem;
    private JButton importFolder;
    private JButton folderWatch;
    private JButton deleteMediaItem;
    private JButton searchMediaItem;    // Make actual methods have more specific names
    private JButton createPlaylist;
    private JButton deletePlaylist;
    private JButton addPlaylistItem;
    private JButton removePlaylistItem;
    private JButton openMediaItem;
    private JScrollPane mediaItems;
    private JList mediaItemsList;
    private DefaultListModel<String> mediaItemsModel;
//    private JComboBox playlists;
//    private JList playlistList;
//    private DefaultListModel<String> playlistModel;
    private JComboBox<String> playlists;
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

    public LibraryPage(String lp){
        this.libraryPath = lp;

//        label.setBounds(0,0,100,50);
//        label.setFont(new Font(null, Font.PLAIN, 25));
//        this.add(label);


//
//        JLabel label = new JLabel(libraryPath);
//        label.setBounds(0,0,200,50);
//        this.add(label);

        JLabel title = new JLabel("Library: " + libraryPath);

        // Defining buttons:
        manualMediaItem = new JButton("Manually create media item");    //remove later
        importFolder = new JButton("Import media files from folder");
        folderWatch = new JButton("Select folder to be watched");
        deleteMediaItem = new JButton("Delete media item");
        searchMediaItem = new JButton("Search for media item");
        openMediaItem = new JButton("Open media item");

        createPlaylist = new JButton("Create Playlist");
        deletePlaylist = new JButton("Delete Playlist");
        addPlaylistItem = new JButton("Add item to playlist");
        removePlaylistItem = new JButton("Remove item from playlist");

        //Adding action listeners for buttons (sort in same order as above)
        openMediaItem.addActionListener(e -> openItem());
        importFolder.addActionListener(e -> openFolder());
        deleteMediaItem.addActionListener(e -> deleteitemList());

        // Tabbed pane
        createitemPane = new JTabbedPane();

        // first pane
        JPanel nameTypePanel = new JPanel();
        nameTypePanel.setBackground(Color.BLUE);
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

        // Second pane
        JPanel itemMainDetails = new JPanel();
        itemMainDetails.setBackground(Color.GREEN);
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

        // Add third pane
        JPanel submitPanel = new JPanel();
        subItem = new JButton("Submit");
        subItem.addActionListener(e -> submitItem());

        submitPanel.setName("Submit item");
        submitPanel.setBackground(Color.PINK);
        submitPanel.add(subItem);

        // Add tabbed panels
        createitemPane.add(nameTypePanel);
        createitemPane.add(itemMainDetails);
        createitemPane.add(submitPanel);

        backToMain = new JButton("Go back!");
        backToMain.addActionListener(e -> goBack());

        // Defining ScrollPanels
        mediaItemsModel = new DefaultListModel<>();
        mediaItemsList = new JList<>(mediaItemsModel);


        //Double Click event
        mediaItemsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    JList<String> list = (JList<String>) e.getSource();

                    int selectedIndex = list.getSelectedIndex();
                    String selectedItem = list.getModel().getElementAt(selectedIndex);

                    MediaItem item = getMediaItemFromList(selectedItem);
                    String details = item.printAllMediaLibrary();

                    JOptionPane.showMessageDialog(null, details, "Success!", JOptionPane.INFORMATION_MESSAGE);

                    System.out.println(item.getMediaName());

                }
            }
        });


        mediaItems = new JScrollPane(mediaItemsList);
        JLabel itemsHeading = new JLabel("Media items:");
        mediaItems.setColumnHeaderView(itemsHeading);

        playlistsModel = new DefaultComboBoxModel<>();
        playlists = new JComboBox<>(playlistsModel);
        playlists.addActionListener(e -> loadplaylistItems());




        //asd


//        playlistModel = new DefaultComboBoxModel<>();
//        playlistList = new JList<>(playlistModel);
//
//        playlists = new JComboBox<>(playlistModel);

        JLabel playlistHeading = new JLabel("Playlists:");
//        playlists.setColumnHeaderView(playlistHeading);

        playlistItemsModel = new DefaultListModel<>();
        playlistItemsList = new JList<>(playlistItemsModel);
        playlistItems = new JScrollPane(playlistItemsList);


        JLabel playlistItemsHeading = new JLabel("Playlist Items:");
        playlistItems.setColumnHeaderView(playlistItemsHeading);





        // Panels
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0,3,10,0));

        centerPanel.add(mediaItems);
        centerPanel.add(playlists);
        centerPanel.add(playlistItems);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        panel1.setPreferredSize(new Dimension(100,100));
        panel1.add(label);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.setPreferredSize(new Dimension(100,35));
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(backToMain);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.GREEN);
        rightPanel.setPreferredSize(new Dimension(200,100));

        rightPanel.setLayout(new GridLayout(5,0,0,10));

        rightPanel.add(createPlaylist);
        rightPanel.add(deletePlaylist);
        rightPanel.add(new JLabel("")); // Made so can add empty cell
        rightPanel.add(addPlaylistItem);
        rightPanel.add(removePlaylistItem);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.MAGENTA);
        leftPanel.setPreferredSize(new Dimension(220,100));

        leftPanel.setLayout(new GridLayout(6,0,0,10));

        leftPanel.add(manualMediaItem);
        leftPanel.add(importFolder);
        leftPanel.add(folderWatch);
        leftPanel.add(deleteMediaItem);
        leftPanel.add(searchMediaItem);
        leftPanel.add(openMediaItem);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setPreferredSize(new Dimension(100,100));
        topPanel.setLayout(new GridLayout(0,2));
        topPanel.add(createitemPane);
        topPanel.add(title);


        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,600);
        this.setVisible(true);

        loadData();
    }

    public void checkItemType() {
       String type = (String) typeEnter.getSelectedItem();

       if(type.equals("Image")){
           durationEnter.setEditable(false);
           resolutionEnter.setEnabled(true);

           formatEnter.removeAllItems();
           for(String iType: imageFormats){
               formatEnter.addItem(iType);
           }
       }
       else if(type.equals("Audio")){
           durationEnter.setEditable(true);
           resolutionEnter.setEnabled(false);

           formatEnter.removeAllItems();
           for(String aType: audioFormats){
               formatEnter.addItem(aType);
           }
       }
       else if(type.equals("Video")){
           durationEnter.setEditable(true);
           resolutionEnter.setEnabled(true);

           formatEnter.removeAllItems();
           for(String vType: videoFormats){
               formatEnter.addItem(vType);
           }
       }
    }

    public void submitItem(){
        if(checkItemEntered()){
            System.out.println("items entered");

            String name = nameEnter.getText();
            String type = (String) typeEnter.getSelectedItem();
            String format = (String) formatEnter.getSelectedItem();
            double size = Double.parseDouble(sizeEnter.getText());
            int ID = 1;
            boolean use = false;
            String fl = "Created-Files/" + name + "." + format;

            String resolution = "No resolution";
            double trackLength = 0;

            if(type.equals("Video")){
                resolution = (String) resolutionEnter.getSelectedItem();
                trackLength = Double.parseDouble(durationEnter.getText());
            }
            if(type.equals("Image")){
                resolution = (String) resolutionEnter.getSelectedItem();
            }
            if(type.equals("Audio")){
                trackLength = Double.parseDouble(durationEnter.getText());
            }

            MediaItem item = new MediaItem(name, type, format, ID, size, fl, trackLength, resolution, use);

            MediaLibrary library = new MediaLibrary();
            library.addMedia(libraryPath, item);
            item.createMediaFileBasic(fl, type);

            loadData();

            JOptionPane.showMessageDialog(null, "Item added!", "Success!", JOptionPane.INFORMATION_MESSAGE);

        }
        else{
            JOptionPane.showMessageDialog(null, "Enter all details in correct format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteitemList() {
        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        int selectedIndex = mediaItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = mediaItemsModel.getElementAt(selectedIndex);
            MediaItem item = getMediaItemFromList(itemInfo);

            library.deleteMediaItem(libraryPath, item.getMediaName(), item.getFormat());    // Look for more efficient way

            if(item.getUsability() == false){
                FileManager fm = new FileManager();
                fm.deleteFile("Created-Files", item.getMediaName() + "." + item.getFormat());
            }
            JOptionPane.showMessageDialog(null, item.getMediaName() + " deleted!", "Success!", JOptionPane.ERROR_MESSAGE);

            loadData();
        }
        else{
            JOptionPane.showMessageDialog(null, "No media item selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean checkItemEntered(){
        boolean nameNotEntered = nameEnter.getText().isEmpty();
        boolean sizeNotEntered = sizeEnter.getText().isEmpty();

        String type = (String) typeEnter.getSelectedItem();

        for(char c : sizeEnter.getText().toCharArray()){
            if(Character.isAlphabetic(c)){
                return false;
            }
        }

        if(type.equals("Image")){
            if (nameNotEntered || sizeNotEntered){
                return false;
            }
            else{
                return true;
            }
        }
        else{
        boolean durationNotEntered = durationEnter.getText().isEmpty();

            for(char c :  durationEnter.getText().toCharArray()){
                if(Character.isAlphabetic(c)){
                    return false;
                }
            }
            if(nameNotEntered || sizeNotEntered || durationNotEntered){
                return false;
            }
            else {
                return true;
            }
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
        Search search = new Search();
        MediaLibrary library = new MediaLibrary();
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
        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        String nameAndFormat[] = info.split(",");
        String name = nameAndFormat[0];
        String format = nameAndFormat[2].trim();
        MediaItem item = library.checkNameFormat(library, name, format);
        return item;
    }

    public void openItem() {
        FileManager fm = new FileManager();

        int selectedIndex = mediaItemsList.getSelectedIndex();

        if(selectedIndex != -1){
            String itemInfo = mediaItemsModel.getElementAt(selectedIndex);

            MediaItem item = getMediaItemFromList(itemInfo);

            if(item.getUsability() == false){
                JOptionPane.showMessageDialog(null, "File isn't usable as was manually created!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String path = item.getFileLocation();
            fm.openMediaItem(path);
        }
        else{
            JOptionPane.showMessageDialog(null, "No media item selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    public void loadplaylistItems() {
        String specificPlaylist = (String) playlists.getSelectedItem();

       String nameAndType[] = specificPlaylist.split(",");
       String name = nameAndType[0];
       String type = nameAndType[1].trim();

        if(specificPlaylist != null){
            updatePlaylistItems(getPlaylistItems(name, type));
        }
    }

    public void loadData(){
        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        if(library.getMediaItems() != null){
            updateMediaItems(getMediaItems());
        }
        if(library.getPlaylists() != null){
            updatePlaylists(getPlaylists());
        }
//       updatePlaylistItems(getPlaylistItems());

    }

    public void updateMediaItems(List<MediaItem> items){
        mediaItemsModel.clear();
        for(MediaItem item : items){
            mediaItemsModel.addElement(item.getMediaName() + ", " + item.getMediaType() + ", " + item.getFormat());
        }

    }

    public List<MediaItem> getMediaItems(){
        List<MediaItem> items = new ArrayList<>();

        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        items = library.getMediaItems();
        return items;
    }

    public void updatePlaylists(List<Playlist> playlists){
//        playlistComboBoxModel.clear();
        // Need to somehow clear model here
            for(Playlist playlist : playlists){
                playlistsModel.addElement(playlist.getPlaylistName() + ", " + playlist.getPlaylistType());
            }

    }

    public List<Playlist> getPlaylists(){
        List<Playlist> playlists = new ArrayList<>();

        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        playlists = library.getPlaylists();
        return playlists;
    }


    public void updatePlaylistItems(List<MediaItem> playlistItems){
        playlistItemsModel.clear();
        for(MediaItem item : playlistItems){
            playlistItemsModel.addElement(item.getMediaName() + ", " + item.getMediaType() + ", " + item.getFormat());
        }
    }

    public List<MediaItem> getPlaylistItems(String name, String type){
        List<MediaItem> playlistItems = new ArrayList<>();

        MediaLibrary library = new MediaLibrary();
        library = library.getLibraryFromJson(libraryPath);

        Playlist specific = library.findPlaylist(library, name, type);
        playlistItems = specific.getMediaItems();

        return playlistItems;
    }
    public void goBack(){
        this.dispose();
        new MainPage();
    }
}
