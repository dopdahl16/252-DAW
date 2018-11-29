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

public class AudioFileInfo extends JPanel implements ActionListener{
    
	public ArrayList<File> tracks_list;
	
    AudioFileInfo(ArrayList<File> tracks_list) {
        
    	setTracksList(tracks_list);
        setBorder(BorderFactory.createLineBorder(Color.red));
        setPreferredSize(new Dimension(200,50));
        JLabel file_name = new JLabel();
        JLabel file_size = new JLabel();
        JLabel file_duration = new JLabel();
        file_name.setText(getTracksList().get(0).getName());
        file_size.setText("Size: " + Long.toString(getTracksList().get(0).length()) + " bytes");
        AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(getTracksList().get(0));
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
        add(file_name);
        add(file_size);
        add(file_duration);
        
        
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
