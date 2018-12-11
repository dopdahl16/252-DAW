package daw;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

//The AudioDisplayContainer class serves as a container component for AudioFileInfo objects and 
//AudioFileVisualDisplay objects.

public class AudioDisplayContainer extends JPanel {
    
	/* FIELDS */
	
	public File track;
	public MainDisplayWindow main_display_window;
	
  
	/* CONSTRUCTOR */
	
    public AudioDisplayContainer(MainDisplayWindow main_display_window, File track) {
        
    	//The AudioDisplayContainer constructor is given a main_display_window and a track. After
    	//setting the track, main_display_window for functional purposes and layout, border and
    	//minimumsize for display and astetic purposes, we create and add the AudioFileVisualDisplay
    	//and AudioFileInfo objects for our respective track.
    	setTrack(track);
    	setMainDisplayWindow(main_display_window);
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createLineBorder(Color.green));
      setMinimumSize(new Dimension(500,10));
        
      AudioFileVisualDisplay audio_file_display = new AudioFileVisualDisplay()
      AudioFileInfo audio_file_info = new AudioFileInfo(this, getMainDisplayWindow(), getTrack());
        
      add(audio_file_info, BorderLayout.LINE_START);
      add(audio_file_display, BorderLayout.LINE_END);

    }
  
  
    /* ACCESSORS */
    
    File getTrack() {
        return track;
    }
    
    MainDisplayWindow getMainDisplayWindow() {
        return main_display_window;
    }
    
    
    /* MUTATORS */
    
    void setTrack(File other) {
        track = other;
    }
    
    void setMainDisplayWindow(MainDisplayWindow other) {
        main_display_window = other;
    }
}
