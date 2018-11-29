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

public class MainDisplayWindow extends JPanel {
    
	public ProgramFrame program_frame;
	public ArrayList<File> tracks_list;
	
    public MainDisplayWindow(ProgramFrame program_frame, ArrayList<File> tracks_list) {
        
    	setTracksList(tracks_list);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        //addAudioFile();
        
        
        
        /*
        //When New... is clicked on
        AudioDisplayContainer new_audio_display_container = new AudioDisplayContainer();
        AudioDisplayContainer new_audio_display_container2 = new AudioDisplayContainer();
        
        //new_audio_display_container.setMinimumSize(new Dimension(Integer.MIN_VALUE, 250));
        new_audio_display_container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        new_audio_display_container2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        add(new_audio_display_container);
        add(new_audio_display_container2);
        */
       
    }
    
    void addAudioFile(ArrayList<File> tracks_list) {
    	
    	AudioDisplayContainer new_container = new AudioDisplayContainer(getTracksList());
    	new_container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
    	add(new_container);
    	revalidate();
    	repaint();
    }
    
    void setProgramFrame(ProgramFrame other) {
    	program_frame = other;
    }
    
    ProgramFrame getProgramFrame() {
    	return program_frame;
    }
    ArrayList<File> getTracksList(){
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other){
        tracks_list = other;
    }

}
