package daw;

import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

//The AudioDisplayContainer class serves as a container component for AudioFileInfo objects and 
//The AudioFileInfo class serves as a display of a single track's information and as a way for
//the user to interact with the track in certain application-wide ways (e.g., selecting it for
//playback, deleting it from the tracks_list, etc.).
public class AudioFileInfo extends JPanel implements ActionListener {
    
	/* FIELDS */
	
	public File track;
	public MainDisplayWindow main_display_window;
	public AudioDisplayContainer audio_display_container;
	
	
	/* CONSTRUCTOR */
	
    public AudioFileInfo(AudioDisplayContainer audio_display_container, MainDisplayWindow main_display_window, File track) {
        
    	setTrack(track);
    	setMainDisplayWindow(main_display_window);
      setBorder(BorderFactory.createLineBorder(Color.red));
      setPreferredSize(new Dimension(200,50));
      setAudioDisplayContainer(audio_display_container);
      JLabel file_name = new JLabel();
      JLabel file_size = new JLabel();
      JLabel file_duration = new JLabel();
      JButton select_button = new JButton()
      JButton delete_button = new JButton();
      JButton save_button = new JButton();
      file_name.setText(getTrack().getName());
      file_size.setText("Size: " + Long.toString(getTrack().length()) + " bytes")
      AudioInputStream audioInputStream = null;
        
		try {
			audioInputStream = AudioSystem.getAudioInputStream(getTrack());
    } 
		catch (Exception could_not_open_clip_or_audiostream) {}
      AudioFormat format = audioInputStream.getFormat();
      long frames = audioInputStream.getFrameLength();
      double duration = (frames+0.0) / format.getFrameRate();
      DecimalFormat numberFormat = new DecimalFormat("#.00")
      file_duration.setText("Duration: " + numberFormat.format(duration) + " seconds");
      select_button.setActionCommand("SELECT");
      select_button.setText("SELECT");
      select_button.addActionListener(this)
      delete_button.setActionCommand("DELETE");
      delete_button.setText("DELETE");
      delete_button.addActionListener(this);
      save_button.setActionCommand("SAVE");
      save_button.setText("SAVE");
      save_button.addActionListener(this);
      add(file_name);
      add(file_size);
      add(file_duration);
      add(select_button);
      add(delete_button);
      add(save_button);   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("SELECT")) {
    	  getMainDisplayWindow().selectTrack(getTrack());
    	}
    	
    	if (e.getActionCommand().equals("DELETE")) {
    		getMainDisplayWindow().removeAudioFile(getAudioDisplayContainer());
    		
    	} 
    	
    	if (e.getActionCommand().equals("SAVE")) {
    		File current_file = getMainDisplayWindow().getTracksList().get(getMainDisplayWindow().getCurrentTrack());
    		String write_file_name = JOptionPane.showInputDialog("What would you like to save this .wav File as?");
        File write_file = new File(current_file.getPath() + write_file_name + ".wav");
        	
        FileOutputStream out = null;
        FileInputStream in = null;
        	
    		try {
    			in = new FileInputStream(current_file);
    			out = new FileOutputStream(write_file);
    		} 
        catch (Exception file_not_found) {}
    		
    		try {
    			for (int i = 0; i<current_file.length(); i++) {
    				
	    			byte b1 = (byte) (in.read() & 0xff);
	    			out.write(b1);
					
    			}
    		} 
        catch (Exception could_not_read_write_to_iostreams) {}
        }
    } 
    	
    //ACCESSORS//
    
    File getTrack() {
        return track;
    }
    
    MainDisplayWindow getMainDisplayWindow() {
        return main_display_window;
    }
    
    AudioDisplayContainer getAudioDisplayContainer() {
    	return audio_display_container;
    }
    
    //MUTATORS//
    
    void setTrack(File other) {
        track = other;
    }
    
    
    void setMainDisplayWindow(MainDisplayWindow other) {
        main_display_window = other;
    }
    void setAudioDisplayContainer(AudioDisplayContainer other) {
    	audio_display_container = other;
    }
   
}
