package daw;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

//The FileExplorerWindow is a window in which the user can find the .wav files that they would like to 
//import into the DAW. This can be done by searching for the file and double-clicking on it. 

public class FileExplorerWindow extends JFileChooser implements ActionListener {
    
    //FIELDS//
    
    public ArrayList<File> tracks_list;
    public MainDisplayWindow main_display_window;
    public int current_track;
    public transient FileNameExtensionFilter filter;
    
    //CONSTRUCTOR//
    
    public FileExplorerWindow(MainDisplayWindow main_display_window, ArrayList<File> tracks_list, int current_track) {
        
    	setMainDisplayWindow(main_display_window);
        setBorder(BorderFactory.createLineBorder(Color.yellow));
        setTracksList(tracks_list);
        setCurrentTrack(current_track);
        
        //Set the initial screen of the file explorer to the user's home directory
        String userDir = System.getProperty("user.home");
        setCurrentDirectory(new File (userDir));
        
        //Filter out files that won't work with the DAW, i.e., non-wav files
        FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "wav");
        setFileFilter(filter);
        addActionListener(this);
        
        
               
    }
    
    //ACCESSORS//
    
    ArrayList<File> getTracksList() {
        return tracks_list;
    }
    
    int getCurrentTrack() {
        return current_track;
    }
    
    MainDisplayWindow getMainDisplayWindow() {
    return main_display_window;
    }
    
    //MUTATORS//
    
    void setTracksList(ArrayList<File> other) {
        tracks_list = other;
    }
        
    void setMainDisplayWindow(MainDisplayWindow other) {
    	main_display_window = other;
    }
    
    void setCurrentTrack(int other) {
        current_track = other;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Add the selected file to the tracklist
        if (e.getActionCommand() == "ApproveSelection") {
            if (!(getTracksList().contains(getSelectedFile()))) {
                getTracksList().add(getSelectedFile());
                if (getTracksList().size() == 1) {
                	getMainDisplayWindow().getProgramFrame().setCurrentTrack(getTracksList().indexOf(getSelectedFile()));
                }
                getMainDisplayWindow().addAudioFile(getTracksList().indexOf(getSelectedFile()));
                //setCurrentTrack(getTracksList().indexOf(getSelectedFile()));
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
	
