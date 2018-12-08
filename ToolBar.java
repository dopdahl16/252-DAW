package daw;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

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

public class ToolBar extends JToolBar implements ActionListener {
    
    private AudioInputStream our_audio_stream;
    private Clip our_clip;
    private AudioFormat format;
    public Long currentPosition;
    private String status;
    private FileExplorerWindow file_explorer_window;
    public File soundFile;
    public ArrayList<File> tracks_list;
    public MainDisplayWindow main_display_window;
    public SaveState save_state;
    
    public ToolBar(MainDisplayWindow main_display_window, FileExplorerWindow file_explorer_window, ArrayList<File> tracks_list, Long initial_position) {
        
        setFloatable(false);
        setFileExplorerWindow(file_explorer_window);
        setTracksList(tracks_list);
        setMainDisplayWindow(main_display_window);
        currentPosition = initial_position;
        if (currentPosition > 0) {
        	status = "paused";
        }
        System.out.println(currentPosition);
        
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
    	
        if (e.getActionCommand().equals("play")) {
        	if (getTracksList().isEmpty()) {
        		JOptionPane loaded_notification = new JOptionPane();
                loaded_notification.showMessageDialog(this, "No Tracks to Play!");
                status = "";
        	}
        	try{
        		if(status == "paused") {
        			if (our_clip == null) {
        				
        				setFile(getTracksList().get(getMainDisplayWindow().getCurrentTrack()));
    	        		setAudioInputStream(getFile());
    	        		setClip(AudioSystem.getClip());
    	        		our_clip.open(our_audio_stream);
    	        		our_clip.setMicrosecondPosition(currentPosition);
    	        		our_clip.start();
    	        		status = "playing";
        				
        				
        				
        			}
        			else {
        				System.out.println(getFile());
        				System.out.println(getTracksList().get(getMainDisplayWindow().getCurrentTrack()));
        				if (getFile() == getTracksList().get(getMainDisplayWindow().getCurrentTrack())) {
        					System.out.println("SAME");
        					our_clip.setMicrosecondPosition(currentPosition);
    	        			//getSaveState().setCurrentPosition(our_clip.getMicrosecondPosition());
    	        			our_clip.start();
    	        			status = "playing";
        				}
        				else {
        					System.out.println("DIFFERENT");
        					setFile(getTracksList().get(getMainDisplayWindow().getCurrentTrack()));
        	        		setAudioInputStream(getFile());
        	        		setClip(AudioSystem.getClip());
        	        		our_clip.open(our_audio_stream);
        	        		our_clip.start();
        	        		status = "playing";
        				}
        			}
        		}
        		else {
	        		setFile(getTracksList().get(getMainDisplayWindow().getCurrentTrack()));
	        		setAudioInputStream(getFile());
	        		if (status == "playing") {
	        			our_clip.stop();
	        			TimeUnit.MILLISECONDS.sleep(10);
	        		}
	        		setClip(AudioSystem.getClip());
	        		our_clip.open(our_audio_stream);
	        		//our_clip.setMicrosecondPosition(currentPosition);
	        		our_clip.start();
	        		status = "playing";
        		}
        		
            }
        	
        	catch(Exception excep) {
        		
        	}
            
        }

        //THESE NEED TRY BLOCKS!!!!!!!!!!!!1
        
        if (e.getActionCommand().equals("pause")) {
        	if (getTracksList().isEmpty()) {
        		JOptionPane loaded_notification = new JOptionPane();
                loaded_notification.showMessageDialog(this, "No Tracks to Pause!");
                status = "";
        	}
            our_clip.stop();
            currentPosition = our_clip.getMicrosecondPosition();
            status = "paused";
          
        }
        
        if (e.getActionCommand().equals("stop")) {
        	if (getTracksList().isEmpty()) {
        		JOptionPane loaded_notification = new JOptionPane();
                loaded_notification.showMessageDialog(this, "No Tracks to Stop!");
                status = "";
        	}
             our_clip.stop();
             our_clip.setMicrosecondPosition(0);
             status = "";
         }

        if (e.getActionCommand().equals("show")) {
            if (getFileExplorerWindow().isVisible()) {
                getFileExplorerWindow().setVisible(false);
            }
            else{
                getFileExplorerWindow().setVisible(true);
            }
           
        }
      
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
    
    private File getFile() {
        return soundFile;
    }
    
    private AudioInputStream getAudioInputStream() {
        return our_audio_stream;

    }
    
    private void setAudioInputStream(File other) {
        try{
         our_audio_stream = AudioSystem.getAudioInputStream(other);
        }
        catch(Exception excep) {
            
        }
     }
    
    public Clip getClip() {
        return our_clip;
    }
    
    private AudioFormat getFormat() {
        return format;
    }
    
    //Mutators
    
    private void setFile(File other) {
        soundFile = other;
    }
    
    
    
    private void setClip(Clip other) {
        try {
            our_clip = other;
        } catch (Exception excep) {
            
        }
    }
    
    FileExplorerWindow getFileExplorerWindow() {
        return file_explorer_window;
    }
    
    void setFileExplorerWindow(FileExplorerWindow other) {
        file_explorer_window = other;
    }
    
    ArrayList<File> getTracksList() {
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other) {
        tracks_list = other;
    }
            
    MainDisplayWindow getMainDisplayWindow() {
        return main_display_window;
    }
    
    void setMainDisplayWindow(MainDisplayWindow other) {
        main_display_window = other;
    }
    
    SaveState getSaveState() {
		return save_state;
    }
    
    void setSaveState(SaveState other) {
    	save_state = other;
    }
}
