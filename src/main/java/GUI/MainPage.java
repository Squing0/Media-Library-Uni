package GUI;

import FileManagement.FileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main page of GUI to manage media libraries.
 * @author Lyle Patterson
 */
public class MainPage extends JFrame {
    /** ref to file manager class. */
    private FileManager fm;
    private JScrollPane libraries;
    private JList mediaLibraryItems;
    private DefaultListModel<String> mediaLibraryModel;
    private JButton createLib;
    private JButton deleteLib;
    private JButton openLib;
    /** Defines media libraries folder location to be reused*/
    private String librariesLocation = "Media-Libraries";

    /**
     * Main page constructor called when page is initialised.
     */
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

        // Adding panels to sections of border layout
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

    /**
     * Main entry point of app.
     * @param args unused string array of command line arguments.
     */
    public static void main(String[] args) {
        //Invoke later used so that frame is opened on event dispatch thread.
        SwingUtilities.invokeLater(MainPage::new);
    }

    /**
     * Loads data into media library scroll pane..
     */
    public void loadData(){
        updateLibraryItems(getLibraries());
    }

    /**
     * Updates library items by clearing model and adding items one by one.
     * @param libraryItems List of media library items.
     */
    public void updateLibraryItems(List <String> libraryItems){
        mediaLibraryModel.clear();
        for(String library : libraryItems){
            mediaLibraryModel.addElement(library);
        }
    }

    /**
     * Gets media libraries from media library folder
     * @return list of library names.
     */
    public List<String> getLibraries(){
        List<String> libraryNames = new ArrayList<>();
        String filePath;

        File dir = new File(librariesLocation);
        File[] files = dir.listFiles();

        for (File file : files){    // Loops through files in media libraries folder.
            filePath = file.getAbsolutePath().replace("\\", "/");
            String[] slashes = filePath.split("/");
            String nameAndFormat = slashes[slashes.length - 1];

            libraryNames.add(nameAndFormat);    //Adds library name and format individually

        }
        return libraryNames;
    }

    /**
     * Adds media library to library list.
     */
    public void addLibraryToList(){
        String name = JOptionPane.showInputDialog(null, "Enter new media library name:");

        if(name == null){   // Purely used to handle if the user exits input dialog.
        }
        else if(!name.isEmpty()){   // User isn't allowed to enter blank text
            if(!checkLibraryPresent(name)){ //Same library can't be entered
                String path = "Media-Libraries/" + name + ".json";

                fm = new FileManager();
                fm.createLibraryFile(path, name);   //Literal file is created

                mediaLibraryModel.addElement(name + ".json");   // library item added to list.

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

    /**
     * Checks if media library is already present in list.
     * @param name Name of media library to check.
     * @return true if library is present and false if not.
     */
    public boolean checkLibraryPresent(String name){
        String fullName = name + ".json";

        File dir = new File(librariesLocation);
        File[] files = dir.listFiles();

        for(File file: files){  //Loops through library folder against name entered.
            if (file.getAbsolutePath().replace("\\", "/").endsWith(fullName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Opens library page of library selected.
     */
    private void openSpecificLibrary() {
        int selectedIndex = mediaLibraryItems.getSelectedIndex();   //Index is first gotten so element can be gotten

        if(selectedIndex != -1){    //Index is checked to see if user has not selected a library.
            String specificLibrary = mediaLibraryModel.getElementAt(selectedIndex); // Element gotten
            String path = "Media-Libraries/" + specificLibrary;

            SwingUtilities.invokeLater(() -> new LibraryPage(path));    // Invoke later used so new frame opened on EDT.

            JOptionPane.showMessageDialog(null,
                    "Opened: " + specificLibrary,
                    "Item opened",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose(); // Main page not shown when library page is opened.
        }
        else{
            JOptionPane.showMessageDialog(null,
                    "No Library Selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes library chosen.
     */
    public void deleteLibrary() {
        int selectedIndex = mediaLibraryItems.getSelectedIndex();

        if(selectedIndex != -1){ //Index is checked to see if user has not selected a library.
            String specificLibrary = mediaLibraryModel.getElementAt(selectedIndex);
            mediaLibraryModel.remove(selectedIndex);    // Library removed from list in UI.

            fm = new FileManager();
            fm.deleteFile(librariesLocation, specificLibrary);  // Literal file deleted.

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

    /**
     * Defines model for scroll pane.
     */
    public void defineLibraryModel(){
        mediaLibraryModel = new DefaultListModel<>();
        mediaLibraryItems = new JList<>(mediaLibraryModel);
    }

    /**
     * Defines title of frame
     * @return title.
     */
    public JLabel defineGetTitle(){
        JLabel title = new JLabel("Media Library Organiser");
        title.setFont(new Font("Helvetica", Font.BOLD, 40));
        title.setForeground(Color.BLACK);
        return title;
    }

    /**
     * Defines scroll pane using library model.
     */
    public void defineLibraryPane(){
        JLabel columnHeader = new JLabel("Media Libraries");

        libraries = new JScrollPane(mediaLibraryItems);
        libraries.setColumnHeaderView(columnHeader);
        libraries.setPreferredSize(new Dimension(400, 300));
    }

    /**
     * Defines and retrieves center panel.
     * @return center panel.
     */
    public JPanel defineGetCenterPane(){
        JPanel centerPanel = new JPanel();
        centerPanel.add(libraries);

        return centerPanel;
    }

    /**
     * Defines buttons in bottom panel and adds action listeners for them.
     */
    public void defineButtons(){
        createLib = new JButton("Create Library");
        deleteLib = new JButton("Delete Library");
        openLib = new JButton("Open Library");

        deleteLib.addActionListener(e -> deleteLibrary());
        createLib.addActionListener(e -> addLibraryToList());
        openLib.addActionListener(e -> openSpecificLibrary());
    }

    /**
     * Defines and retrieves bottom panel
     * @return bottom panel.
     */
    public JPanel defineGetBottomPanel(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(24, 240, 151));
        bottomPanel.setPreferredSize(new Dimension(80,80));
        bottomPanel.setLayout(new GridLayout(0,3,10,0));   //3 columns for each button
        bottomPanel.add(openLib);
        bottomPanel.add(createLib);
        bottomPanel.add(deleteLib);

        return bottomPanel;
    }

    /**
     * Defines and retrieves top panel.
     * @return top panel.
     */
    public JPanel defineGetTopPanel(){
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(24, 240, 151));
        topPanel.add(defineGetTitle());

        return topPanel;
    }
}