package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.*;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

/*
DOCUMENTATION
*/

public class FileExplorerWindow extends JFileChooser implements ActionListener {
    
    public ArrayList<File> tracks_list;
    public AudioDisplayContainer hhh = new AudioDisplayContainer();
    
    public FileExplorerWindow(MainDisplayWindow main_display_window, ArrayList<File> tracks_list) {
        
        setBorder(BorderFactory.createLineBorder(Color.yellow));
        setTracksList(tracks_list);
        //setCurrentDirectory(new File ("/home/opdada01/Music"));
        String userDir = System.getProperty("user.home");
        //setCurrentDirectory(new File ("/home/opdada01/NetBeansProjects/DAW/sounds"));
        setCurrentDirectory(new File (userDir));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "wav");
        setFileFilter(filter);
        addActionListener(this);
        
        
        
               
    }
    
    ArrayList<File> getTracksList(){
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other){
        tracks_list = other;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "ApproveSelection"){
            if (!(getTracksList().contains(getSelectedFile()))){
                getTracksList().add(getSelectedFile());
                System.out.println(".wav file:" + getSelectedFile() + " loaded into program");
                JOptionPane loaded_notification = new JOptionPane();
                loaded_notification.showMessageDialog(this, ".wav file: \"" + getSelectedFile().getName() + "\" loaded into program");
            }
            else{
                JOptionPane loaded_notification = new JOptionPane();
                loaded_notification.showMessageDialog(this, ".wav file: \"" + getSelectedFile().getName() + "\" is already loaded into program");
            }
            
        }
       
    }
    
}

