package GUI;

import FileManagement.FileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends JFrame {
    private FileManager fm;
    private JScrollPane libraries;
    private JList mediaLibraryItems;
    private DefaultListModel<String> mediaLibraryModel;
    private JButton createLib;
    private JButton deleteLib;
    private JButton openLib;
    private String librariesLocation = "Media-Libraries";

    public MainPage(){
        // Defining JFrame
        this.setTitle("Media Library Organiser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(1000,600));
        this.setLayout(new BorderLayout());

        // Defining model
        defineLibraryModel();

        // Defining JScrollPanel
        defineLibraryPane();

        // Buttons for bottom panel
        defineButtons();

        // Defining bottom panel
        defineGetBottomPanel();

        // Defining right and left panel
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();

        // Adding panels
        this.add(defineGetTopPanel(), BorderLayout.NORTH);
        this.add(defineGetBottomPanel(), BorderLayout.SOUTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(defineGetCenterPane(), BorderLayout.CENTER);

        // Finalising JFrame
        this.pack();
        this.setVisible(true);

        // Loading media library items
        loadData();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPage::new);
    }
    public void loadData(){
        updateLibraryItems(getLibraries());
    }
    public void updateLibraryItems(List <String> libraryItems){
        mediaLibraryModel.clear();
        for(String library : libraryItems){
            mediaLibraryModel.addElement(library);
        }
    }
    public List<String> getLibraries(){
        List<String> libraryNames = new ArrayList<>();
        String filePath;

        File dir = new File(librariesLocation);
        File[] files = dir.listFiles();

        for (File file : files){
            filePath = file.getAbsolutePath().replace("\\", "/");
            String[] slashes = filePath.split("/");
            String nameAndFormat = slashes[slashes.length - 1];

            libraryNames.add(nameAndFormat);

        }
        return libraryNames;
    }
    public void addLibraryToList(){
        String name = JOptionPane.showInputDialog(null, "Enter new media library name:");

        if(name == null){   // Purely used to handle if the user exits input dialog.
        }
        else if(!name.isEmpty()){
            if(!checkLibraryPresent(name)){
                String path = "Media-Libraries/" + name + ".json";

                fm = new FileManager();
                fm.createLibraryFile(path, name);

                mediaLibraryModel.addElement(name + ".json");

                JOptionPane.showMessageDialog(null,
                        "Created: " + name,
                        "Item created",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null,
                        "File with name " + name + " already exists!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "Enter text",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean checkLibraryPresent(String name){
        String fullName = name + ".json";

        File dir = new File(librariesLocation);
        File[] files = dir.listFiles();

        for(File file: files){
            if (file.getAbsolutePath().replace("\\", "/").endsWith(fullName)){
                return true;
            }
        }
        return false;
    }
    private void openSpecificLibrary() {
        int selectedIndex = mediaLibraryItems.getSelectedIndex();

        if(selectedIndex != -1){
            String specificLibrary = mediaLibraryModel.getElementAt(selectedIndex);
            String path = "Media-Libraries/" + specificLibrary;

            SwingUtilities.invokeLater(() -> new LibraryPage(path));

            JOptionPane.showMessageDialog(null,
                    "Opened: " + specificLibrary,
                    "Item opened",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No Library Selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void deleteLibrary() {
        int selectedIndex = mediaLibraryItems.getSelectedIndex();

        if(selectedIndex != -1){
            String specificLibrary = mediaLibraryModel.getElementAt(selectedIndex);
            mediaLibraryModel.remove(selectedIndex);

            fm = new FileManager();
            fm.deleteFile(librariesLocation, specificLibrary);

            JOptionPane.showMessageDialog(null,
                    "Deleted: " + specificLibrary,
                    "Item deleted",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No Library Selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void defineLibraryModel(){
        mediaLibraryModel = new DefaultListModel<>();
        mediaLibraryItems = new JList<>(mediaLibraryModel);
    }

    public JLabel defineGetTitle(){
        JLabel title = new JLabel("Media Library Organiser");
        title.setFont(new Font("Helvetica", Font.BOLD, 40));
        title.setForeground(Color.BLACK);
        return title;
    }
    public void defineLibraryPane(){
        JLabel columnHeader = new JLabel("Media Libraries");

        libraries = new JScrollPane(mediaLibraryItems);
        libraries.setColumnHeaderView(columnHeader);
        libraries.setPreferredSize(new Dimension(400, 300));
    }
    public JPanel defineGetCenterPane(){
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());

        centerPanel.add(libraries);
        return centerPanel;
    }
    public void defineButtons(){
        createLib = new JButton("Create Library");
        deleteLib = new JButton("Delete Library");
        openLib = new JButton("Open Library");

        deleteLib.addActionListener(e -> deleteLibrary());
        createLib.addActionListener(e -> addLibraryToList());
        openLib.addActionListener(e -> openSpecificLibrary());
    }
    public JPanel defineGetBottomPanel(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(24, 240, 151));
        bottomPanel.setPreferredSize(new Dimension(80,80));
        bottomPanel.setLayout(new GridLayout(0,3,10,0));
        bottomPanel.add(openLib);
        bottomPanel.add(createLib);
        bottomPanel.add(deleteLib);

        return bottomPanel;
    }
    public JPanel defineGetTopPanel(){
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(24, 240, 151));
        topPanel.add(defineGetTitle());

        return topPanel;
    }
}