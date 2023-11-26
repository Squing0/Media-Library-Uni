package GUI;

import MediaManagement.MediaLibrary;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

public class MainPage extends JFrame {
private JButton button;
    private JButton but = new JButton("Submit");
    private JTextField text = new JTextField();
    private JComboBox mediaTypes;
    private JScrollPane libraries;
    private JList mediaLibraryItems;
    private DefaultListModel<String> mediaLibraryModel;
    private JButton createLib;
    private JButton deleteLib;
    private JButton openLib;
    private JButton exitPage;

    private String librariesLocation = "Media-Libraries";

int count = 0;
    public MainPage(){
        this.setTitle("Media Library Organiser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setPreferredSize(new Dimension(1000,600));
        this.setLayout(new BorderLayout());


        mediaLibraryModel = new DefaultListModel<>();
        mediaLibraryItems = new JList<>(mediaLibraryModel);


        // Title
        JLabel title = new JLabel("Media Library Organiser");
        title.setFont(new Font("Helvitica", Font.BOLD, 25));
        title.setForeground(Color.BLACK);

        JLabel columnHeader = new JLabel("Media Libraries");

        JTextArea desc = new JTextArea(10,10);
        // JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        libraries = new JScrollPane(mediaLibraryItems);
        libraries.setColumnHeaderView(columnHeader);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());


        centerPanel.add(libraries);
        this.add(centerPanel);

        //Items for bottom panel
        createLib = new JButton("Create Library");
        deleteLib = new JButton("Delete Library");
        exitPage = new JButton("Exit App");
        openLib = new JButton("Open Library");

        exitPage.addActionListener(e -> closeWindow());
        deleteLib.addActionListener(e -> deleteLibrary());
        createLib.addActionListener(e -> addLibraryToList());
        openLib.addActionListener(e -> openSpecificLibrary());

        // File chooser for files and directories:
//        but.addActionListener(e -> openFile());
//        this.add(but);

        // Combo box items
//        String[] types = {"Image", "Audio", "Video"};
//        mediaTypes = new JComboBox(types);
//
//        mediaTypes.addActionListener(e -> printCombo());
//        mediaTypes.setEditable(false);
//        mediaTypes.getItemCount();
//        mediaTypes.addItem("lol");
//        mediaTypes.insertItemAt("lol", 1);
//        mediaTypes.setSelectedIndex(0);
//        mediaTypes.removeItem("Video");
//        mediaTypes.removeItemAt(2);
//        mediaTypes.removeAllItems();

//        this.add(mediaTypes);


        // Text fields:
//        text = new JTextField();
//        text.setPreferredSize(new Dimension(250,40));
//        text.setFont(new Font("Consolas", Font.PLAIN, 20));
//        text.setForeground(Color.GREEN);
//        text.setBackground(Color.BLACK);
//        text.setCaretColor(Color.WHITE);
//        text.setText("Media Item:");
//        text.setEditable(false);
//
//        but.addActionListener(e -> submitText());
//        this.add(but);
//        this.add(text);

        //Message options
//        JOptionPane.showMessageDialog(null, "Useless info", "Title", JOptionPane.PLAIN_MESSAGE);
//        JOptionPane.showMessageDialog(null, "Useless info", "Title", JOptionPane.INFORMATION_MESSAGE);
//        JOptionPane.showMessageDialog(null, "Useless info", "Title", JOptionPane.QUESTION_MESSAGE);
//        JOptionPane.showMessageDialog(null, "Useless info", "Title", JOptionPane.WARNING_MESSAGE);
//        JOptionPane.showMessageDialog(null, "Useless info", "Title", JOptionPane.ERROR_MESSAGE);


        // User options
        // 0,1,2 and -1 for different options depending on what user choose
//        int answer = JOptionPane.showConfirmDialog(null, "Lol bRO>>?", "TITLE", JOptionPane.YES_NO_CANCEL_OPTION);

        // Inputs
//        String name = JOptionPane.showInputDialog("What is your name?: ");
//        System.out.println("Hello: " + name);

        // Combined
//        String[] responses = {"lol no whha", ";{", "NO!"};
//        JOptionPane.showOptionDialog(null,
//                "You da coolest",
//                "Secret message",
//                JOptionPane.YES_NO_CANCEL_OPTION,
//                JOptionPane.INFORMATION_MESSAGE,
//                null,
//                responses,
//                0);


//        this.setLayout(new GridLayout(3,3,10,10));
//        this.setLayout(new BorderLayout(10,10));

        // Use for navigation between pages
        //        but = new JButton("New Window");
//        this.add(but);
//        but.addActionListener(e -> openWindow());


        // Flow layout:
//        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
//        this.add(new JButton("2"));
//        this.add(new JButton("3"));
//        this.add(new JButton("4"));
//        this.add(new JButton("5"));
//        this.add(new JButton("6"));
//        this.add(new JButton("7"));
//        this.add(new JButton("8"));
//        this.add(new JButton("9"));
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
//        panel.setPreferredSize(new Dimension(250,250));
//        panel.setBackground(Color.LIGHT_GRAY);
//        panel.add(new JButton("1"));
//        panel.add(new JButton("2"));
//        panel.add(new JButton("3"));
//        panel.add(new JButton("4"));
//        panel.add(new JButton("5"));
//        panel.add(new JButton("6"));
//        panel.add(new JButton("7"));
//        panel.add(new JButton("8"));
//        panel.add(new JButton("9"));
//
//        this.add(panel);
        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);


        // Labels
        JLabel label = new JLabel("library7.json");
//        label.setHorizontalAlignment(JLabel.CENTER);
//        label.setVerticalAlignment(JLabel.TOP);
        label.setForeground(Color.BLACK);
//        label.setBackground(Color.DARK_GRAY);   // Expands to whole screen
//        label.setOpaque(true);
        label.setBorder(border);
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));


        // Panels
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        panel1.setPreferredSize(new Dimension(100,100));
        panel1.add(label);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.setPreferredSize(new Dimension(100,100));
        bottomPanel.setLayout(new GridLayout(0,4,10,0));
        bottomPanel.add(openLib);
        bottomPanel.add(createLib);
        bottomPanel.add(deleteLib);
        bottomPanel.add(exitPage);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.GREEN);
        rightPanel.setPreferredSize(new Dimension(100,100));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.MAGENTA);
        leftPanel.setPreferredSize(new Dimension(100,100));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.PINK);
        topPanel.setPreferredSize(new Dimension(100,100));
        topPanel.add(title);

        // Sub panels for border layout
//        JPanel panel6 = new JPanel();
//        panel6.setBackground(Color.BLACK);
//        panel6.setPreferredSize(new Dimension(50,50));
//
//        JPanel panel7 = new JPanel();
//        panel7.setBackground(Color.DARK_GRAY);
//        panel7.setPreferredSize(new Dimension(50,50));
//
//        JPanel panel8 = new JPanel();
//        panel8.setBackground(Color.GRAY);
//        panel8.setPreferredSize(new Dimension(50,50));
//
//        JPanel panel9 = new JPanel();
//        panel9.setBackground(Color.LIGHT_GRAY);
//        panel9.setPreferredSize(new Dimension(50,50));
//
//        JPanel panel10 = new JPanel();
//        panel10.setBackground(Color.WHITE);
//        panel10.setPreferredSize(new Dimension(50,50));
//
//        panel5.setLayout(new BorderLayout());
//
//        panel5.add(panel6, BorderLayout.NORTH);
//        panel5.add(panel7, BorderLayout.SOUTH);
//        panel5.add(panel8, BorderLayout.EAST);
//        panel5.add(panel9, BorderLayout.WEST);
//        panel5.add(panel10, BorderLayout.CENTER);
        //Buttons
//        button = new JButton("library7.json");
//        button.setFocusable(false);
//        button.setBounds(500,0,200,100);
//        button.setHorizontalTextPosition(JButton.CENTER);
//        button.setVerticalTextPosition(JButton.TOP);
//        button.setFont(new Font("Comic Sans", Font.BOLD, 25));
//        button.setForeground(Color.PINK);
//        button.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
//        button.setEnabled(true);
//        button.addActionListener(e -> print());


//        this.add(label);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
//        this.add(button);

//        label.setBounds(0,0,250,250);



//        this.getContentPane().setBackground(new Color(173, 216, 230));

        this.pack();
        this.setVisible(true);
        loadData();
    }

    private void openSpecificLibrary() {
        int selectedIndex = mediaLibraryItems.getSelectedIndex();

        if(selectedIndex != -1){
            String specificLibrary = mediaLibraryModel.getElementAt(selectedIndex);
            String path = "Media-Libraries/" + specificLibrary;
            new LibraryPage(path);

            JOptionPane.showMessageDialog(null, "Opened: " + specificLibrary, "Item opened", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "No Library Selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteLibrary() {
        int selectedIndex = mediaLibraryItems.getSelectedIndex();

        if(selectedIndex != -1){
            String specificLibrary = mediaLibraryModel.getElementAt(selectedIndex);

            mediaLibraryModel.remove(selectedIndex);
            deleteLibraryFile(specificLibrary);

            JOptionPane.showMessageDialog(null, "Deleted: " + specificLibrary, "Item deleted", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "No Library Selected", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void deleteLibraryFile(String libraryFile){
        File dir = new File(librariesLocation);
        File[] files = dir.listFiles();
        String path;

        for(File file: files){
            path = file.getAbsolutePath().replace("\\", "/");
            if (path.endsWith(libraryFile)){
                file.delete();
            }
        }
    }
    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Use above line if want to directories, exact same code

        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showOpenDialog(null);
        // int response = fileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            System.out.println(file);
        }
    }

    public void loadData(){
//        String[] numbers = {"1", "2", "3", "4"};
//        List<String> libraryItems = Arrays.asList(numbers);

        updateLibraryItems(getLibraries());
    }

    public void addLibraryToList(){
        String name = JOptionPane.showInputDialog("Enter new media library name:");

        if(!checkLibraryPresent(name)){
            if(!name.isEmpty() && name != null){
                String specific = "Media-Libraries/" + name + ".json";

                MediaLibrary library = new MediaLibrary();
                library.createMediaLibrary(specific);

                mediaLibraryModel.addElement(name + ".json");

                JOptionPane.showMessageDialog(null, "Created: " + name, "Item created", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "File with name: " + name + " already exists!", "Item already present", JOptionPane.INFORMATION_MESSAGE);
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

    public List<String> getLibraries(){
        List<String> libraryNames = new ArrayList<>();
        String filePath;

        File dir = new File(librariesLocation);
        File[] files = dir.listFiles();

        for (File file : files){
            filePath = file.getAbsolutePath().replace("\\", "/");
            String[] slashes = filePath.split("/");
            String nameAndFormat = slashes[slashes.length - 1];

            // split using dot and use last instance in case name also has dots in it
//            String[] dots = nameAndFormat.split("\\.");
//            String name = dots[0];
            libraryNames.add(nameAndFormat);

            // Same method using in search class
        }
        return libraryNames;
    }

    public void updateLibraryItems(List <String> libraryItems){
        mediaLibraryModel.clear();
        for(String library : libraryItems){
            mediaLibraryModel.addElement(library);
        }
    }


    public void printCombo() {
        System.out.println("Selected: " + mediaTypes.getSelectedItem());
        System.out.println("Index: " + mediaTypes.getSelectedIndex());
    }

    public void submitText() {
        System.out.println("Welcome " + text.getText());
        text.setText("");
    }

    public static void main(String[] args) {
        MainPage page = new MainPage();
    }

    public void print(){
        System.out.println("YAY!");
    }

//    public void openWindow(){
//        this.dispose();
//        new LibraryPage();
//    }

    public void closeWindow(){
        this.dispose();
    }


}
