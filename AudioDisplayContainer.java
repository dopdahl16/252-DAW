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
import javax.sound.sampled.*;

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

public class AudioDisplayContainer extends JPanel implements ActionListener{
    
	public ArrayList<File> tracks_list;
	public int track_number = 0;
	
    public AudioDisplayContainer (ArrayList<File> tracks_list){
        
    	setTracksList(tracks_list);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.green));
        setMinimumSize(new Dimension(500,10));
        
        AudioFileVisualDisplay audio_file_display = new AudioFileVisualDisplay();
        AudioFileInfo audio_file_info = new AudioFileInfo(getTracksList());
        
        add(audio_file_info, BorderLayout.LINE_START);
        add(audio_file_display, BorderLayout.LINE_END);
        
        //use track numbers to keep track of which is which?
        track_number = track_number + 1;

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ArrayList<File> getTracksList(){
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other){
        tracks_list = other;
    }
}
