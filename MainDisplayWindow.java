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



////TODO Close all filestreams!!!!!!!!


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
    	
    	remove(audio_display_container);
    	revalidate();
    	repaint();
    	int new_index;
    	
    	if (getTracksList().indexOf(audio_display_container.getTrack()) - 1 >= 0) {
    		new_index = (getTracksList().indexOf(audio_display_container.getTrack()) - 1);
    	}
    	else {
    		
    		new_index = 0;
    	}
    	
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
    	getProgramFrame().setCurrentTrack(getTracksList().indexOf(selected_track));
    	getProgramFrame().our_current_track_display.revalidate();
    	getProgramFrame().our_current_track_display.repaint();
    	getProgramFrame().revalidate();
    	getProgramFrame().repaint();
    }
    
    
    void resample(int resample_rate) {
    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File("C:\\Users\\dopda\\Desktop\\DAW WAV Files\\" + "_resampled_" + getTracksList().get(getCurrentTrack()).getName());
    	
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			int i=0;
			while(i < 11 ) {
					
					byte b1 = (byte) (in.read() & 0xff);
					byte b2 = (byte) (in.read() & 0xff);
					byte b3 = (byte) (in.read() & 0xff);
					byte b4 = (byte) (in.read() & 0xff);
					i++;
					int val = b4 << 24 | b3 << 16 & 0xff0000 | b2 << 8 & 0xff00 | ((int) b1) & 0xff;
					
					if (i == 7) {
						
						
						
						byte leastSignificantByte = (byte) (val & 0xff);
					    byte nextToLeastSignificantByte = (byte) (val >> 8 & 0xff);
					    byte nextToMostSignificantByte = (byte) (val >> 16 & 0xff);
					    byte mostSignificantByte = (byte) (val >> 32 & 0xff);
					    
					    out.write(leastSignificantByte);
				        out.write(nextToLeastSignificantByte);
				        out.write(nextToMostSignificantByte);
				        out.write(mostSignificantByte);
						
						System.out.println("VAL: " + val);
					}
					if (i == 8) {
						
						val = val *2;
						
						byte leastSignificantByte = (byte) (val & 0xff);
					    byte nextToLeastSignificantByte = (byte) (val >> 8 & 0xff);
					    byte nextToMostSignificantByte = (byte) (val >> 16 & 0xff);
					    byte mostSignificantByte = (byte) (val >> 32 & 0xff);
					    
					    out.write(leastSignificantByte);
				        out.write(nextToLeastSignificantByte);
				        out.write(nextToMostSignificantByte);
				        out.write(mostSignificantByte);
					}
					if (i != 7 && i != 8) {
						out.write(b1);
				        out.write(b2);
				        out.write(b3);
				        out.write(b4);
					}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    void adjustAmplitudeClip(double scaling_ratio) {
    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File("C:\\Users\\Noah\\Desktop\\" + "amplitude_clipped " + getTracksList().get(getCurrentTrack()).getName());
        
    	
        
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		try {
			for (int i = 0; i<(current_file.length()/2); i++) {
				
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
			
			short s = ((short) (b2 << 8 | (((int) b1) & 0xff) & 0xffff));
			
				s = (short) (s * scaling_ratio);
				byte b3 = ((byte) (s & 0xff));
				byte b4 = ((byte) (s >> 8 & 0xff));
				if (b1 * scaling_ratio >= 127 || b1 * scaling_ratio <= -128) {
					if (b1 > 0) {
						b3 = 127;
					}
					if (b1 < 0) {
						b3 = -128;
					}
				}
				if (b2 * scaling_ratio >= 127 || b2 * scaling_ratio <= -128) {
					if (b2 > 0) {
						b4 = 127;
					}
					if (b2 < 0) {
						b4 = -128;
					}
				}
			
				out.write(b3);
				out.write(b4);
			
			}
		getTracksList().add(write_file);
                this.addAudioFile(getTracksList().indexOf(write_file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    void adjustAmplitudeNormalize(double scaling_ratio) {
    	
    	
    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File("C:\\Users\\Noah\\Desktop\\" + getTracksList().get(getCurrentTrack()).getName());
        
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//If scaling_ratio <= 1, don't have to do this
		
		double adjusted_scaling_ratio;
		byte max_byte = 0;
		
		
		
		try {
			
			for (int i = 0; i < current_file.length(); i++) {
				
				if (i > 44) {
				
					
					byte current_byte = (byte) (in.read() & 0xff);
					
					if (current_byte > max_byte) {
						
						max_byte = current_byte;
					
					}
					if (current_byte == 127) {
						System.out.println("CUR BYT: " + current_byte + "    " + i);
					}
					
				}
				
			}
			System.out.println("MAX B : " + max_byte);
		}
		catch (Exception could_not_read_from_FileInputStream) {}
		
		
		
		if (scaling_ratio > 1) {
			adjusted_scaling_ratio = ((double) 126 / (double) max_byte);
		}
		else {
			adjusted_scaling_ratio = scaling_ratio;
		}
		
		
		
		try {
			in.close();
			in = new FileInputStream(current_file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		try {
			for (int i = 0; i < current_file.length()/2; i++) {
				
				while (i < 44) {
					byte b1 = (byte) (in.read() & 0xff);
					byte b2 = (byte) (in.read() & 0xff);
					out.write(b1);
					out.write(b2);
					i++;
				}
				
				
			byte b1 = (byte) (in.read() & 0xff);
			byte b2 = (byte) (in.read() & 0xff);
			
			short s = ((short) (b2 << 8 | (((int) b1) & 0xff) & 0xffff));
			
				s = (short) (s * adjusted_scaling_ratio);
				byte b3 = ((byte) (s & 0xff));
				byte b4 = ((byte) (s >> 8 & 0xff));
			
				
				out.write(b3);
				out.write(b4);
			
			}
                getTracksList().add(write_file);
                this.addAudioFile(getTracksList().indexOf(write_file));
			
		} catch (IOException e) {
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
        catch (Exception excep) {
            
        }
     }
    
}

//byte[] byte_array = new byte[(int) current_file.length()];

