package GUI;

import MediaManagement.MediaItem;
import MediaManagement.MediaLibrary;
import MediaManagement.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryPage extends JFrame {
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
        manualMediaItem = new JButton("Manually create media item");
        importFolder = new JButton("Import media files from folder");
        folderWatch = new JButton("Select folder to be watched");
        deleteMediaItem = new JButton("Delete media item");
        searchMediaItem = new JButton("Search for media item");

        createPlaylist = new JButton("Create Playlist");
        deletePlaylist = new JButton("Delete Playlist");
        addPlaylistItem = new JButton("Add item to playlist");
        removePlaylistItem = new JButton("Remove item from playlist");

        backToMain = new JButton("Go back!");
        backToMain.addActionListener(e -> goBack());

        // Defining ScrollPanels
        mediaItemsModel = new DefaultListModel<>();
        mediaItemsList = new JList<>(mediaItemsModel);
        mediaItems = new JScrollPane(mediaItemsList);
        JLabel itemsHeading = new JLabel("Media items:");
        mediaItems.setColumnHeaderView(itemsHeading);

        playlistsModel = new DefaultComboBoxModel<>();
        playlists = new JComboBox<>(playlistsModel);
        playlists.addActionListener(e -> loadplaylistItems());


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
        leftPanel.setPreferredSize(new Dimension(200,100));

        leftPanel.setLayout(new GridLayout(5,0,0,10));

        leftPanel.add(manualMediaItem);
        leftPanel.add(importFolder);
        leftPanel.add(folderWatch);
        leftPanel.add(deleteMediaItem);
        leftPanel.add(searchMediaItem);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setPreferredSize(new Dimension(100,100));
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
            mediaItemsModel.addElement(item.getMediaName() + ", " + item.getMediaType());
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
            playlistItemsModel.addElement(item.getMediaName() + ", " + item.getMediaType());
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
