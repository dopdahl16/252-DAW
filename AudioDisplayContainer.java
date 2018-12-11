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

public class AudioDisplayContainer extends JPanel implements ActionListener {
    
    //FIELDS//
    
	public File track;
	public MainDisplayWindow main_display_window;
        
    //CONSTRUCTOR//
	
    public AudioDisplayContainer(MainDisplayWindow main_display_window, File track) {
        
    	setTrack(track);
    	setMainDisplayWindow(main_display_window);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.green));
        setMinimumSize(new Dimension(500,10));
        
        AudioFileVisualDisplay audio_file_display = new AudioFileVisualDisplay();
        AudioFileInfo audio_file_info = new AudioFileInfo(this, getMainDisplayWindow(), getTrack());
        
        add(audio_file_info, BorderLayout.LINE_START);
        add(audio_file_display, BorderLayout.LINE_END);

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //ACCESSORS//
    
    File getTrack(){
        return track;
    }
    
    MainDisplayWindow getMainDisplayWindow(){
        return main_display_window;
    }
    
    //MUTATORS//
    
    void setTrack(File other){
        track = other;
    }
    
    
    void setMainDisplayWindow(MainDisplayWindow other){
        main_display_window = other;
    }
}
