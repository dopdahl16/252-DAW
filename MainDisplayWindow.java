package daw;

import java.awt.*;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
	//public ArrayList<AudioDisplayContainer> audio_display_container_list;
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
    	//audio_display_container_list.add(new_container);
    	revalidate();
    	repaint();
		
    }
    
    void removeAudioFile(AudioDisplayContainer audio_display_container) {
    	//audio_display_container_list.remove(audio_display_container);
    	
    	System.out.println("Track: " + audio_display_container.getTrack().getName() + " removed from program");
    	System.out.println("BOOP: " + getTracksList().indexOf(audio_display_container.getTrack()));
    	System.out.println("BOOP1: " + getTracksList());
    	System.out.println("BOOP2: " + getTracksList().indexOf(audio_display_container));
    	System.out.println("BOOP3: " + getTracksList().indexOf(audio_display_container.getTrack()));
    	
    	
    	remove(audio_display_container);
    	revalidate();
    	repaint();
    	int new_index;
    	System.out.println("BOOP BOOP: " + getTracksList().indexOf(audio_display_container.getTrack()));
    	if (getTracksList().indexOf(audio_display_container.getTrack()) - 1 >= 0) {
    		new_index = (getTracksList().indexOf(audio_display_container.getTrack()) - 1);
    	}
    	else {
    		System.out.println("NEW INDEX ASSIGNED 0");
    		new_index = 0;
    	}
    	System.out.println("NEW INDEX: " + new_index);
    	getTracksList().remove(audio_display_container.getTrack());
    	getProgramFrame().setCurrentTrack(new_index);
    	getProgramFrame().our_current_track_display.revalidate();
    	getProgramFrame().our_current_track_display.repaint();
    	getProgramFrame().revalidate();
    	getProgramFrame().repaint();
    	setCurrentTrack(new_index);
    	
    }
   
    void selectTrack(File selected_track) {
    	setCurrentTrack(getTracksList().indexOf(selected_track));
    	System.out.println("Track: " + selected_track.getName() + " selected");
    	getProgramFrame().setCurrentTrack(getTracksList().indexOf(selected_track));
    	getProgramFrame().our_current_track_display.revalidate();
    	getProgramFrame().our_current_track_display.repaint();
    	getProgramFrame().revalidate();
    	getProgramFrame().repaint();
    }
    
    void adjustAmplitude(double scaling_ratio) {
    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	//AudioFormat fmt = new AudioFormat(44100f, 16, 1, true, false);
    	//AudioFormat audioFormat = new AudioFormat(Encoding.ULAW, 8000, 8, 1, frameSize, 50, true);
    	File write_file = new File("C:\\Users\\dopda\\Downloads\\WWW\\aaa.wav");
    	//setAudioInputStream(getTracksList().get(getCurrentTrack()));
    	//AudioInputStream stream = our_audio_stream;
    	scaling_ratio = 0.5;
    	
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
    	AudioInputStream my_stream = null;
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			my_stream = AudioSystem.getAudioInputStream(current_file);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		try {
			// i<999999999
			for (int i = 0; i<999999999; i++) {
				
				
				while (i < 44) {
					byte b1 = (byte) (in.read() & 0xff);
					byte b2 = (byte) (in.read() & 0xff);
					System.out.println(b1 + " " + b2 + " ");
					out.write(b1);
					out.write(b2);
					System.out.println(b1 + " " + b2 + " ");
					System.out.println(i);
					i++;
				}
				
				
			byte b1 = (byte) (in.read() & 0xff);
			byte b2 = (byte) (in.read() & 0xff);
			if (b1 == -1) {
				System.out.println("WHAM");
				break;
			}
			short s = ((short) (b2 << 8 | (((int) b1) & 0xff) & 0xffff));
			
			
			
			
				System.out.println(b1 + " " + b2 + " " + s);
				s = (short) (s * 0.5);
				byte b3 = ((byte) (s & 0xff));
				byte b4 = ((byte) (s >> 8 & 0xff));
				System.out.println(b3 + " " + b4 + " " + s);
				System.out.println(" " + i);
			
		
				out.write(b3);
				out.write(b4);
			
				
			
				
			}
			
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	//try {
    		
    	
    		
    		
    		
    		
    		
    		
    		
    		
    		/*
			AudioInputStream in_stream = AudioSystem.getAudioInputStream(current_file);
			Short sample;
			
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
				
				System.out.println(b1 + " " + b2);
				
				ByteBuffer bb = ByteBuffer.allocate(2);
				bb.order(ByteOrder.LITTLE_ENDIAN);
				bb.put(b1);
				bb.put(b2);
				sample = bb.getShort(0);
				sample = (short) (sample * scaling_ratio);
				
				b1 = ((byte) (sample & 0xff));
				b2 = ((byte) (sample >> 8 & 0xff));
				
				System.out.println(sample);
				System.out.println("EDITED" + b1 + " " + b2);
				System.out.println(" ");
				
				
				
				
				
				
				
				*/
				/*
				
				System.out.println("BYTES: " + b1 + " " + b2);
				
				ByteBuffer bb = ByteBuffer.allocate(2);
				bb.order(ByteOrder.LITTLE_ENDIAN);
				bb.put(b1);
				bb.put(b2);
				sample = bb.getShort(0);
				
				
				
				
				
				sample = (((short)(((((int)b2)|0xff)<<8)|(((int)b1)|0xff))));
				
				
				
				
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
			*/
    		/*
			ByteArrayInputStream b_in = new ByteArrayInputStream(byte_stream);
			
			//AudioInputStream my_stream = new AudioInputStream(my_byte_stream, in_stream.getFormat(), byte_stream.length);
			FileOutputStream output = new FileOutputStream(new File("C:\\Users\\dopda\\Downloads\\WWW\\aaa.wav"));
			output.write(byte_stream);
			output.close();
			
			//DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:\\\\Users\\\\dopda\\\\Downloads\\\\WWW\\\\aaa.wav"));
	        //dos.write(byte_stream);
	        AudioFormat format = in_stream.getFormat();
	        AudioInputStream stream = new AudioInputStream(b_in, format, byte_stream.length);
	        File file = new File("C:\\\\Users\\\\dopda\\\\Downloads\\\\WWW\\\\bbb.wav");
	        AudioSystem.write(stream, Type.WAVE, file);
	        System.out.println("Amp Adjusted");
	        */
	        /*
		} 
    	
    	catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    	
    }
    
    
    
    void setProgramFrame(ProgramFrame other) {
    	program_frame = other;
    }
    
    ProgramFrame getProgramFrame() {
    	return program_frame;
    }
    ArrayList<File> getTracksList() {
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other) {
        tracks_list = other;
    }

    int getCurrentTrack() {
        return current_track;
    }
    
    void setCurrentTrack(int other) {
        current_track = other;
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
    
}
