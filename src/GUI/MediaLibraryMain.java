package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import FileManageAndSearch.*;

public class MediaLibraryMain {

    private JButton mediaLibraryButton;
    private JPanel panelMain;
    private int[] numbers= {1,2,3,4,5};
    private JComboBox comboBox1;
    private JLabel Label1;

    public MediaLibraryMain() {

    mediaLibraryButton.addActionListener(new ActionListener() {
        int count;
        String s1;

        @Override
        public void actionPerformed(ActionEvent e) {
//            count++;
//            s1 = String.format("Counts:%d", count);
//            System.out.println(s1);
//
//            if (count >= 10){
//                mediaLibraryButton.setText("Ya did it! " + count);
//                Label1.setText(":)");
//            }

            FileManager fm = new FileManager();
            fm.openMediaItem("Created-Files/file_example_MP4_480_1_5MG.mp4");
        }
    });


        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               int state = e.getStateChange();
               if(state == ItemEvent.SELECTED){
                   if (comboBox1.getSelectedItem().toString() == "5"){
                       Label1.setText("NO! :(");
                   }
               }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Media-Library");
        frame.setContentPane(new MediaLibraryMain().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
