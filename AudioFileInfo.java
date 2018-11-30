package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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


///ADD A DELETE BUTTON WITH A POP-UP MENU THAT ASKS IF YOU REALLY DO WANT TO REMOVE THE TRACK
///ADD A SAVE BUTTON
///ADD BACKGROUND COLOR CHANGER TO SIGNIFY WHICH TRACK IS CURRENTLY SELECTED



public class AudioFileInfo extends JPanel implements ActionListener{
    
	public File track;
	public MainDisplayWindow main_display_window;
	
    AudioFileInfo(MainDisplayWindow main_display_window, File track) {
        
    	setTrack(track);
    	setMainDisplayWindow(main_display_window);
        setBorder(BorderFactory.createLineBorder(Color.red));
        setPreferredSize(new Dimension(200,50));
        JLabel file_name = new JLabel();
        JLabel file_size = new JLabel();
        JLabel file_duration = new JLabel();
        JButton select_button = new JButton();
        file_name.setText(getTrack().getName());
        file_size.setText("Size: " + Long.toString(getTrack().length()) + " bytes");
        AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(getTrack());
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double duration = (frames+0.0) / format.getFrameRate();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        file_duration.setText("Duration: " + numberFormat.format(duration) + " seconds");
        select_button.setActionCommand("SELECT");
        select_button.setText("SELECT");
        select_button.addActionListener(this);
        add(file_name);
        add(file_size);
        add(file_duration);
        add(select_button);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("SELECT")){
    		getMainDisplayWindow().selectTrack(getTrack());
    	}
    }
    File getTrack(){
        return track;
    }
    
    void setTrack(File other){
        track = other;
    }
    MainDisplayWindow getMainDisplayWindow(){
        return main_display_window;
    }
    
    void setMainDisplayWindow(MainDisplayWindow other){
        main_display_window = other;
    }
}
