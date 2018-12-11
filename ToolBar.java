package daw;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

////TODO NEED TO SET EXCEPTIONS

//The TooBar class provides the user with the ability to control track playback and to show or hide 
//the FileExplorerWindow

public class ToolBar extends JToolBar implements ActionListener {
    
	/* FIELDS */
	
    private AudioInputStream our_audio_stream;
    private Clip our_clip;
    private AudioFormat format;
    public Long current_position;
    private String status;
    private FileExplorerWindow file_explorer_window;
    public File sound_file;
    public ArrayList<File> tracks_list;
    public MainDisplayWindow main_display_window;
    
    /* CONSTRUCTOR */
    
    public ToolBar(MainDisplayWindow main_display_window, FileExplorerWindow file_explorer_window, ArrayList<File> tracks_list, Long initial_position) {
        
    	//Here we set the main_display_window, tracks_list, and file_explorer_window as well as locally 
    	//declare and set positions for track playback purposes
        setFloatable(false); 
        setFileExplorerWindow(file_explorer_window);
        setTracksList(tracks_list);
        setMainDisplayWindow(main_display_window);
        current_position = initial_position;
        
        //If the position that was passed when the constructor is called in is greater than 0, that means 
        //that there was a loaded state when ProgramFrame was constructed. The status is then set to 
        //"paused" so that when the play button is pressed, the track will resume at the same point it was
        //saved at. If there was no saved state when ProgramFrame was constructed, then current_position is
        //set to 0 by the new ToolBar call in ProgramFrame.
        if (current_position > 0) {
        	
        	status = "paused";
        }
        
        //Declare, construct and add buttons and FileExplorer
        
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

    //All actions utilize a "checker" to ensure that the tracks_list is not empty, and therefore 
    //the user has a track to interact with. This is the first thing done with every action except
    //for the "show" command
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	//Command "play" allows the user to begin playback of a track. If tracks_list is empty, a
    	//pop-up is displayed, and nothing is done. If there are tracks in tracks_list, then we check
    	//if the status is paused. If it is, we check to see if our_clip is null, or if there is a
    	//clip already loaded. If there is no clip (i.e., (our_clip == null) is true), then we get the
    	//current track, construct a clip out of it, set it to the position it was paused at 
    	//(current_position), and begin playback. If our_clip has a non-null value, however, we determine
    	//what track to play by a process detailed below. Rewinding a bit, if the status was not "paused",
    	//then we set our_clip to the current track (pausing the current clip, if it was playing), begin
    	//playback of the new clip, and set the status to "playing"
        if (e.getActionCommand().equals("play")) {
        	
        	if (getTracksList().isEmpty()) {
        		
        		JOptionPane no_tracks_to_play_notification = new JOptionPane();
        		no_tracks_to_play_notification.showMessageDialog(this, "No Tracks to Play!");
                status = "";
                
        	}
        	
        	try{
        		
        		if(status == "paused") {
        			
        			if (our_clip == null) {
        				
        				setFile(getTracksList().get(getMainDisplayWindow().getCurrentTrack()));
    	        		setAudioInputStream(getFile());
    	        		setClip(AudioSystem.getClip());
    	        		our_clip.open(our_audio_stream);
    	        		our_clip.setMicrosecondPosition(current_position);
    	        		our_clip.start();
    	        		status = "playing";
        				
        			}
        			//If our_clip has a non-null value, and the current track is the same as the track  
        			//that our_clip was created from, we simply resume the same track by resetting the 
        			//clip at the recorded position using current_position, the playing the clip, and 
        			//finally setting the status to "playing". However if the current track is not the same 
        			//as the track that our_clip was created from, we reassign our_clip to the new 
        			//current track and start playing it.
        			else {
        				
        				if (getFile() == getTracksList().get(getMainDisplayWindow().getCurrentTrack())) {
        					
        					our_clip.setMicrosecondPosition(current_position);
    	        			our_clip.start();
    	        			status = "playing";
    	        			
        				}
        				else {
        					
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
	        			
	        			//When we stop our_clip, we sleep for a bit to allow for a break in between the
	        			//two clips. This is purely stylistic.
	        			our_clip.stop();
	        			TimeUnit.MILLISECONDS.sleep(10);
	        			
	        		}
	        		
	        		setClip(AudioSystem.getClip());
	        		our_clip.open(our_audio_stream);
	        		//our_clip.setMicrosecondPosition(current_position);
	        		our_clip.start();
	        		status = "playing";
	        		
        		}
            }
        	//This catch clause handles IO exceptions thrown when we are unable to open an audiostream or
        	//get a clip from AudioSystem
        	catch (Exception could_not_open_clip_or_audiostream) {}
        }
        
        //Command "pause" allows the user to pause playback of a track, and save its position to restart
        //it when the "play" button is pressed. If tracks_list is not empty, we stop our_clip and then 
        //set current_position to the current position of our_clip. This allows us to resume our_clip at
        //a later time by a clause in the "play" command that sets the clip to current_position before 
        //beginning playback. Finally, we set status to "paused".
        if (e.getActionCommand().equals("pause")) {
        	
        	if (getTracksList().isEmpty()) {
        		
        		JOptionPane no_tracks_to_pause_notification = new JOptionPane();
        		no_tracks_to_pause_notification.showMessageDialog(this, "No Tracks to Pause!");
                status = "";
                
        	}
        	else {
        		
        		try {
	        		our_clip.stop();
		            current_position = our_clip.getMicrosecondPosition();
		            status = "paused";
        		}
        		catch (Exception no_clip_to_stop){}
        	}
        }
        
        if (e.getActionCommand().equals("stop")) {
        	
        	JOptionPane no_tracks_to_stop_notification = new JOptionPane();
        	
        	if (getTracksList().isEmpty()) {
        		
        		no_tracks_to_stop_notification.showMessageDialog(this, "No Tracks to Stop!");
        	
        	}
        	else {
        		
        		try {
        			our_clip.stop();
		            our_clip.setMicrosecondPosition(0);
        		}
        		catch (Exception no_clip_to_stop) {}
        	}
        	
        	status = "";
         }
        
        //Command "show" allows the user to toggle hide and show of the FileExplorerWindow. When the 
        //command is activated, we check to see if the FileExplorerWindow is visible. If it is, we set
        //it to be hidden, if it isn't, we set it to be shown.
        if (e.getActionCommand().equals("show")) {
        	
            if (getFileExplorerWindow().isVisible()) {
            	
                getFileExplorerWindow().setVisible(false);
                
            }
            else{
            	
                getFileExplorerWindow().setVisible(true);
                
            }
        }
    }

    //makeButton is a function that easily creates JButtons to be added into ToolBar. The function first
    //creates a generic JButton, then it creates an ImageIcon by getting the image file from the imageName 
    //parameter. If the image exists, an ImageIcon is created using the file path, it is scaled down, we
    //add our actionCommand and ActionListener to the button, and set our icon. If the image does not exist,
    //we set our actionCommand and ActionListener to the button, and then set altText for the button instead
    //of an image. Finally, we return the button we have made.
    private JButton makeButton(String imageName, String actionCommand, String altText) {
        
    	JButton button = new JButton();
        File img_file = new File("src/data/images/" + imageName);
        
        if (img_file.exists()) {
            
	        String img_string = "src/data/images/" + imageName;
	        ImageIcon img_ico = new ImageIcon(img_string, altText);
	        Image image = img_ico.getImage();
	        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
	        img_ico = new ImageIcon(newimg);
	        button.setActionCommand(actionCommand);
	        button.addActionListener(this);
	        button.setIcon(img_ico);
	        
        }
        else {
        	
            button.setActionCommand(actionCommand);
            button.addActionListener(this);
            button.setText(altText);
            
        }
 
        return button;
        
    }
    
    
    /* ACCESSORS */
    
    private File getFile() {
        return sound_file;
    }
    
    FileExplorerWindow getFileExplorerWindow() {
        return file_explorer_window;
    }
    
    public Clip getClip() {
        return our_clip;
    }
    
    MainDisplayWindow getMainDisplayWindow() {
        return main_display_window;
    }
    
    ArrayList<File> getTracksList() {
        return tracks_list;
    }
    
    
    /* MUTATORS */
    
    private void setFile(File other) {
        sound_file = other;
    }
    
    private void setAudioInputStream(File other) {

    	try {
			our_audio_stream = AudioSystem.getAudioInputStream(other);
		} 
    	//This catch clause catches any Exception that may be thrown while calling getAudioInputStream from
    	//AudioSystem, e.g., UnsupportedAudioFile, IO Error, etc. 
    	catch (Exception could_not_set_aduio_input_stream) {
    		
    		JOptionPane no_tracks_notification = new JOptionPane();
            no_tracks_notification.showMessageDialog(this, "Could not play track. Please only play valid tracks.");
            
			} 
     }
    
    private void setClip(Clip other) {
        our_clip = other;
    }
    
    void setFileExplorerWindow(FileExplorerWindow other) {
        file_explorer_window = other;
    }
    
    void setTracksList(ArrayList<File> other) {
        tracks_list = other;
    }
            
    void setMainDisplayWindow(MainDisplayWindow other) {
        main_display_window = other;
    }
    
}
