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
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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

//NEED TO SET EXCEPTIONS, Need to implement resuming upon pausing 

public class ToolBar extends JToolBar implements ActionListener, KeyListener{
    
    private AudioInputStream our_audio_stream;
    private Clip our_clip;
    private AudioFormat format;
    private Long currentPosition;
    private String status;
    private FileExplorerWindow file_explorer_window;
    public File soundFile;
    public ArrayList<File> tracks_list;
    public MainDisplayWindow main_display_window;
    
    public ToolBar(MainDisplayWindow main_display_window, FileExplorerWindow file_explorer_window, ArrayList<File> tracks_list){
        
        setFloatable(false);
        setFileExplorerWindow(file_explorer_window);
        setTracksList(tracks_list);
        setMainDisplayWindow(main_display_window);
        
        JButton play, pause, stop, showFileExplorer;
        
        play = makeButton("play.png", "play", "playButton");
        pause = makeButton("pause.png", "pause", "pauseButton");
        stop = makeButton("stop.png", "stop", "stopButton");
        showFileExplorer = makeButton("show.png", "show", "showFileExplorer");
        
        add(play);
        add(pause);
        add(stop);
        add(showFileExplorer);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
        if (e.getActionCommand().equals("play")){
        	if (getTracksList().isEmpty()) {
        		JOptionPane loaded_notification = new JOptionPane();
                loaded_notification.showMessageDialog(this, "No Tracks to Play!");
        	}
        	try{
        		if(status == "paused") {
        			our_clip.setMicrosecondPosition(currentPosition);
        			our_clip.start();
        		}
        		else {
	        		setFile(getTracksList().get(getMainDisplayWindow().getCurrentTrack()));
	        		setAudioInputStream(getFile());
	        		setClip(AudioSystem.getClip());
	        		our_clip.open(our_audio_stream);
	        		our_clip.start();
        		}
        		
        		status = "";
        		
            }
        	
        	catch(Exception excep){
        		
        	}
            
        }

        if (e.getActionCommand().equals("pause")){
          our_clip.stop();
          currentPosition = our_clip.getMicrosecondPosition();
          status = "paused";
          
        }
        
        if (e.getActionCommand().equals("stop")){
           our_clip.stop();
           our_clip.setMicrosecondPosition(0);
           status = "";
         }

        if (e.getActionCommand().equals("show")){
            if (getFileExplorerWindow().isVisible()){
                getFileExplorerWindow().setVisible(false);
            }
            else{
                getFileExplorerWindow().setVisible(true);
            }
           
        }
      
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected JButton makeButton(String imageName, String actionCommand, String altText) {
        
        File img_file = new File("src/data/images/" + imageName);
        String img_string = "src/data/images/" + imageName;
        ImageIcon img_ico = new ImageIcon(img_string, altText);
        Image image = img_ico.getImage();
        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        img_ico = new ImageIcon(newimg); 
        
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
 
        if (img_file.exists()) {
            button.setIcon(img_ico);
        } else {
            button.setText(altText);
            System.err.println("Resource not found: " + img_string);
        }
 
        return button;
        
    }
    
    //Accessors
    
    private File getFile(){
        return soundFile;
    }
    
    private AudioInputStream getAudioInputStream(){
        return our_audio_stream;
    }
    
    private Clip getClip(){
        return our_clip;
    }
    
    private AudioFormat getFormat(){
        return format;
    }
    
    //Mutators
    
    private void setFile(File other){
        soundFile = other;
    }
    
    private void setAudioInputStream(File other){
       try{
        our_audio_stream = AudioSystem.getAudioInputStream(other);
       }
       catch(Exception excep){
           
       }
    }
    
    private void setClip(Clip other){
        try {
            our_clip = other;
        } catch (Exception excep) {
            
        }
    }
    
    FileExplorerWindow getFileExplorerWindow(){
        return file_explorer_window;
    }
    
    void setFileExplorerWindow(FileExplorerWindow other){
        file_explorer_window = other;
    }
    
    ArrayList<File> getTracksList(){
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other){
        tracks_list = other;
    }
            
    MainDisplayWindow getMainDisplayWindow(){
        return main_display_window;
    }
    
    void setMainDisplayWindow(MainDisplayWindow other){
        main_display_window = other;
    }
}
