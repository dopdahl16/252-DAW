package daw;

import java.awt.*;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

//The MainDisplayWindow class is where our audio display swing components and is where our audio
//information is held (e.g., tracks_list). MainDisplayWindow acts as the hub for all file editing,
//as well as the container for our audio display objects.

public class MainDisplayWindow extends JPanel {
    
	/* FIELDS */
	
	public ProgramFrame program_frame;
	public ArrayList<File> tracks_list;
	public File track;
	//track_index and current_track differ in function: track_index is for creating the audio files 
	//in AudioDisplayContainer,AudioFileInfo, and AudioFileVisualDisplay. current_track is for 
	//playback purposes ONLY.
	public int track_index;
	public int current_track;
	private AudioInputStream our_audio_stream;
	
	/* CONSTRUCTOR */
	
    public MainDisplayWindow(ProgramFrame program_frame, ArrayList<File> tracks_list, int current_track) {
        
    	//Here we set relevant playback variables and set our layout.
    	setTracksList(tracks_list);
    	setProgramFrame(program_frame);
    	setCurrentTrack(current_track);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        
    }
    
    //addAudioFile adds an audio file to the MainDisplayWindow using track_index, as any audio file
    //a user wishes to add will already be in tracks_list when this method is called. We create an
    //AudioDisplayContainer for the track, add it to MainDisplayWindow and then repaint. We do not
    //reset current_track when we add and audio file.
    void addAudioFile(int track_index) {
    	
    	AudioDisplayContainer new_container = new AudioDisplayContainer(this, getTracksList().get(track_index));
    	new_container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
    	add(new_container);
    	//audio_display_container_list.add(new_container);
    	revalidate();
    	repaint();
		
    }
    
    //removeAudioFile first removes the AudioDisplayContainer object from our MainDisplayWindow. Next,
    //it repaints. Then, it checks to see if the track the was inside of the removed AudioDisplayContainer 
    //object was the track at index 0 in tracks_list. If it was, it resets new_idex to zero so that
    //new_idex can set our_current_track_display to a valid track. If it wasn't, it drops new_index to
    //the next track so that our_current_track_display can be set to that track. Finally, we remove the 
    //track from tracks_list, set the current track in ProgramFrame, repaint both the ProgramFrame and
    //our_current_track_display and then set current_track.
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
   
    //selectTrack sets current_track to a different track in tracks_list in both here and ProgramFrame,
    //then it repaints both the ProgramFrame and our_current_track_display.
    void selectTrack(File selected_track) {
    	setCurrentTrack(getTracksList().indexOf(selected_track));
    	getProgramFrame().setCurrentTrack(getTracksList().indexOf(selected_track));
    	getProgramFrame().our_current_track_display.revalidate();
    	getProgramFrame().our_current_track_display.repaint();
    	getProgramFrame().revalidate();
    	getProgramFrame().repaint();
    }
/*
    //resample creates a write_file to write the resampled track to. It also creates a FileInputStream 
    //out of current_file and a FileOutputStream out of write_file. Then, 
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
*/
    
    //adjustAmplitudeClip creates a write_file to write the edited track to. It also creates a FileInputStream 
    //out of current_file and a FileOutputStream out of write_file. Then, it copies the file header of the .wav
    //file from the FileOutputStream to the FileInputStream. Then, it reads in a pair of bytes, smashes the two
    //bytes into a short, multiplies the short by scaling_ratio, then extracts the two bytes from the short. If
    //either of the two bytes are greater than their max value of 127, they are reset to 127. Finally the two 
    //modified bytes are written out to the FileOutputStream. Finally we automatically add write_file to 
    //tracks_list and this using addAudioFile.
    void adjustAmplitudeClip(double scaling_ratio) {
    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File("C:\\Users\\Noah\\Desktop\\" + "amplitude_clipped " + getTracksList().get(getCurrentTrack()).getName());
        
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} 
		catch (Exception file_not_found) {}
		
		try {
			for (int i = 0; i<(current_file.length()/2); i++) {
				
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
		addAudioFile(getTracksList().indexOf(write_file));
		
		} 
		catch (Exception could_not_read_write_to_iostreams) {}
    	
    }
    
    //adjustAmplitudeNormalize creates a write_file to write the edited track to. It also creates a FileInputStream 
    //out of current_file and a FileOutputStream out of write_file. Then, it copies the file header of the .wav
    //file from the FileOutputStream to the FileInputStream. Then, it reads through all of the audio data and finds
    //the largest byte, stored as max_byte. It then modifies scaling_ratio so that no byte multiplied by scaling_ratio 
    //is greater than 127. Then, we close the FileInputStream and reopen it to read through it again. Finally, we go 
    //through FileInputStream, reading in a pair of bytes, smashing the two bytes into a short, multiplying the short 
    //by adjusted_scaling_ratio, then extracting the two bytes from the short. Finally the two modified bytes are 
    //written out to the FileOutputStream. Finally we automatically add write_file to tracks_list and this using 
    //addAudioFile.
    void adjustAmplitudeNormalize(double scaling_ratio) {
    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File("C:\\Users\\Noah\\Desktop\\" + getTracksList().get(getCurrentTrack()).getName());
        
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} catch (Exception file_not_found) {}
		
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
		catch (Exception could_not_read_write_to_iostreams) {}
		
		
		
		if (scaling_ratio > 1) {
			adjusted_scaling_ratio = ((double) 126 / (double) max_byte);
		}
		else {
			adjusted_scaling_ratio = scaling_ratio;
		}
		
		try {
			in.close();
			in = new FileInputStream(current_file);
		} 
		catch (Exception file_not_found) {}
		
		
		
		
		
		
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
			
		} catch (Exception could_not_read_write_to_iostreams) {}
    	
    
    	
    }
    
    void erase() {

    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File("C:\\Users\\dopda\\Desktop\\DAW WAV Files\\" + "1" + getTracksList().get(getCurrentTrack()).getName());
    	
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} 
		catch (Exception file_not_found) {}
		
		
		
		
		
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
			
				s = (short) 0;
				byte b3 = ((byte) (s & 0xff));
				byte b4 = ((byte) (s >> 8 & 0xff));
				out.write(b3);
				out.write(b4);
			
			}
			
		} catch (Exception could_not_read_write_to_iostreams) {}
    	
    
    	
    }
    
    
    
    
    
    
    void merge(File merge_selected_file) {
    	File write_file = new File("C:\\Users\\dopda\\Desktop\\DAW WAV Files\\" + merge_selected_file.getName() + "_merge_" + getTracksList().get(getCurrentTrack()).getName());
    	
    	short[] short_array1 = new short[(int) (merge_selected_file.length() - 44)/2];
    	short[] short_array2 = new short[(int) (getTracksList().get(getCurrentTrack()).length() - 44)/2];
    	
    	FileOutputStream out = null;
    	FileInputStream in1 = null;
    	FileInputStream in2 = null;
    	
    	try {
			in1 = new FileInputStream(merge_selected_file);
			in2 = new FileInputStream(getTracksList().get(getCurrentTrack()));
			out = new FileOutputStream(write_file);
		} catch (Exception file_not_found) {}
    	
    	try {
			for (int i1 = 0; i1<merge_selected_file.length()/2; i1++) {
				
				while (i1 < 44) {
					byte b1 = (byte) (in1.read() & 0xff);
					byte b2 = (byte) (in1.read() & 0xff);
					i1++;
				}
				
				
			byte b1 = (byte) (in1.read() & 0xff);
    		byte b2 = (byte) (in1.read() & 0xff);
    			
    		short s = ((short) (b2 << 8 | (((int) b1) & 0xff) & 0xffff));
    			
    		short_array1[i1-44] = s;
			
    		System.out.println("Reading into array1");
    		
			}
			
		} catch (Exception could_not_read_write_to_iostreams) {}
    	
    	
    	
    	
    	
    	try {
			for (int i1 = 0; i1<getTracksList().get(getCurrentTrack()).length()/2; i1++) {
				
				while (i1 < 44) {
					byte b1 = (byte) (in2.read() & 0xff);
					byte b2 = (byte) (in2.read() & 0xff);
					out.write(b1);
					out.write(b2);
					i1++;
				}
				
				
			byte b1 = (byte) (in2.read() & 0xff);
    		byte b2 = (byte) (in2.read() & 0xff);
    			
    		short s = ((short) (b2 << 8 | (((int) b1) & 0xff) & 0xffff));
    			
    		short_array2[i1-44] = s;
			
    		System.out.println("Reading into array2");
    		
			}
			
		} catch (Exception could_not_read_write_to_iostreams) {}
    	
    	
    	
    	if (short_array2.length > short_array1.length) {
    		for (int i1 = 0; i1 < short_array2.length; i1++) {
    			while (i1 < short_array1.length) {
    				short read_short = (short) (short_array1[i1] + short_array2[i1]);
    				byte b3 = ((byte) (read_short & 0xff));
    				byte b4 = ((byte) (read_short >> 8 & 0xff));
    				try {
						out.write(b3);
						out.write(b4);
					} catch (Exception could_not_read_write_to_iostreams) {}
    				i1++;
    				System.out.println("2 > 1: " + i1);
    			}
			}
    		
    	}
    	
    	if (short_array1.length > short_array2.length) {
    		for (int i1 = 0; i1 < short_array1.length; i1++) {
        		while (i1 < short_array2.length) {
        			short read_short = (short) (short_array1[i1] + short_array2[i1]);
    				byte b3 = ((byte) (read_short & 0xff));
    				byte b4 = ((byte) (read_short >> 8 & 0xff));
    				try {
						out.write(b3);
						out.write(b4);
					} catch (Exception could_not_read_write_to_iostreams) {}
    				i1++;
    				System.out.println("1 > 2: " + i1 + " len: " + short_array2.length);
				}
        		short read_short = (short) (short_array1[i1]);
        		byte b3 = ((byte) (read_short & 0xff));
				byte b4 = ((byte) (read_short >> 8 & 0xff));
				try {
					out.write(b3);
					out.write(b4);
				} catch (Exception could_not_read_write_to_iostreams) {}
				System.out.println("more");
    		}
    	}
    		
    	
		
	
    }
    
    
    
    
    
    
    
    
    void reverse() {

    	
    	File current_file = getTracksList().get(getCurrentTrack());
    	File write_file = new File(current_file.getPath() + "REVERSE");
    	
    	short[] short_array = new short[(int) (getTracksList().get(getCurrentTrack()).length() - 44)/2];
    	
    	FileOutputStream out = null;
    	FileInputStream in = null;
    	
    	
		try {
			in = new FileInputStream(current_file);
			out = new FileOutputStream(write_file);
		} catch (Exception file_not_found) {}
		
		
		
		
		
		try {
			for (int i = 0; i<current_file.length()/2; i++) {
				
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
    			
    		short_array[i-44] = s;
			
			}
			
			for(int i = 0; i < short_array.length / 2; i++) {
			    short temp = short_array[i];
			    short_array[i] = short_array[short_array.length - i - 1];
			    short_array[short_array.length - i - 1] = temp;
			}
			
			for(int i = 0; i < short_array.length; i++) {
				short read_short = short_array[i];
				byte b3 = ((byte) (read_short & 0xff));
				byte b4 = ((byte) (read_short >> 8 & 0xff));
				out.write(b3);
				out.write(b4);
			}
                        getTracksList().add(write_file);
                        addAudioFile(getTracksList().indexOf(write_file));
			
		} catch (Exception could_not_read_write_to_iostreams) {}
    	
    
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
}
