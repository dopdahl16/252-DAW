package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.*;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
	public File track;
	public int track_index;
	public int current_track;
	private AudioInputStream our_audio_stream;
    //private Clip our_clip;
    //private AudioFormat format;
    //private Long currentPosition;
    //private String status;
	//	track_index is for creating the audio files in AudioDisplayContainer,AudioFileInfo, and AudioFileVisualDisplay only
	//	current_track is for playback purposes ONLY
	
    public MainDisplayWindow(ProgramFrame program_frame, ArrayList<File> tracks_list, int current_track) {
        
    	setTracksList(tracks_list);
    	setProgramFrame(program_frame);
    	setCurrentTrack(current_track);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        
    }
    
    void addAudioFile(int track_index) {
    	
    	AudioDisplayContainer new_container = new AudioDisplayContainer(this, getTracksList().get(track_index));
    	new_container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
    	add(new_container);
    	revalidate();
    	repaint();
    	
    	
    	
    	
		
		
		
		
    }
    
   
    void selectTrack(File selected_track) {
    	setCurrentTrack(getTracksList().indexOf(selected_track));
    	System.out.println("Track: " + selected_track.getName() + " selected");
    }
    
    void adjustAmplitude(double scaling_ratio) {
    	File current_file = getTracksList().get(getCurrentTrack());
    	//setAudioInputStream(getTracksList().get(getCurrentTrack()));
    	//AudioInputStream stream = our_audio_stream;
    	scaling_ratio = 0.5;
    	
    	try {
			AudioInputStream in_stream = AudioSystem.getAudioInputStream(current_file);
			Short sample = 0;
			
			int max_length = 5000000;
			int length = in_stream.available();
			if (in_stream.available()>max_length) {
				length = max_length;
			}
			
			
			//read bytes as shorts - in book 48
			byte [] byte_stream = new byte [length];
			in_stream.read(byte_stream, 0, length);
			int i;
			for (i=0; i<byte_stream.length; i = i+2) {
				byte b1 = byte_stream[i];
				byte b2 = byte_stream[i+1];
				
				System.out.println("BYTES: " + b1 + " " + b2);
				System.out.println("BYTES binary: " + Integer.toBinaryString(b1) + " " + Integer.toBinaryString(b2));
				short res = (short) Integer.parseInt(Integer.toBinaryString(b1), 2);
				System.out.println("BYTES TRIAL: " + res);
				
				ByteBuffer bb = ByteBuffer.allocate(2);
				bb.order(ByteOrder.LITTLE_ENDIAN);
				bb.put(b1);
				bb.put(b2);
				sample = bb.getShort(0);
				
				
				/*
				
				
				sample = (((short)(((((int)b2)|0xff)<<8)|(((int)b1)|0xff))));
				
				
				*/
				
				System.out.println("SHORT PRE_EDIT: " + sample);
				
				
				int jjj = (int) sample;
				
				System.out.println("SHORT TO INT: " + jjj);
				
				jjj = (int) (jjj * scaling_ratio);
				
				System.out.println("Culprit?: " + jjj);
				
				sample = (short) jjj;
				
				System.out.println("SHORT post_EDIT: " + sample);
				
				b1 = (byte) (sample & 0xff);
				b2 = (byte) ((sample >> 8) & 0xff);
				
				System.out.println("BYTES 2         : " + b1 + " " + b2);
				System.out.println(" ");
				
				byte_stream[i] = b1;
				byte_stream[i+1] = b2;
			}
		
			ByteArrayInputStream b_in = new ByteArrayInputStream(byte_stream);
			/*
			//AudioInputStream my_stream = new AudioInputStream(my_byte_stream, in_stream.getFormat(), byte_stream.length);
			FileOutputStream output = new FileOutputStream(new File("C:\\Users\\dopda\\Downloads\\WWW\\aaa.wav"));
			output.write(byte_stream);
			output.close();
			*/
			//DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:\\\\Users\\\\dopda\\\\Downloads\\\\WWW\\\\aaa.wav"));
	        //dos.write(byte_stream);
	        AudioFormat format = in_stream.getFormat();
	        AudioInputStream stream = new AudioInputStream(b_in, format, byte_stream.length);
	        File file = new File("C:\\\\Users\\\\dopda\\\\Downloads\\\\WWW\\\\bbb.wav");
	        AudioSystem.write(stream, Type.WAVE, file);
	        System.out.println("Amp Adjusted");
	        
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
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

    int getCurrentTrack(){
        return current_track;
    }
    
    void setCurrentTrack(int other){
        current_track = other;
    }
    private AudioInputStream getAudioInputStream(){
        return our_audio_stream;

    }
    
    private void setAudioInputStream(File other){
        try{
         our_audio_stream = AudioSystem.getAudioInputStream(other);
        }
        catch(Exception excep){
            
        }
     }
    
}
