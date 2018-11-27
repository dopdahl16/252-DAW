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
	
    public MainDisplayWindow(ProgramFrame program_frame) {
        
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
    
    void addAudioFile() {
    	
    	AudioDisplayContainer boop = new AudioDisplayContainer();
    	boop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
    	add(boop);
    	revalidate();
    	repaint();
    }
    
    void setProgramFrame(ProgramFrame other) {
    	program_frame = other;
    }
    
    ProgramFrame getProgramFrame() {
    	return program_frame;
    }

}
