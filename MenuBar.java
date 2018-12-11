package daw;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

////TODO Actions documentation

//The MenuBar class provides the user with various track options via a drop-down menu, and allows
//the application to handle user selections. 

public class MenuBar extends JMenuBar implements ActionListener {

	/* FIELDS */
	
	public MainDisplayWindow main_display_window;
	public ArrayList<File> tracks_list;
	

    /* CONSTRUCTOR */
    
    public MenuBar(MainDisplayWindow main_display_window, ArrayList<File> tracks_list) {
        
    	//Here we set the main_display_window and tracks_list as well as locally declare all the 
    	//menu drop-downs (e.g., edit_menu) and all the menu buttons (e.g., resample_item).
    	setMainDisplayWindow(main_display_window);
    	setTracksList(tracks_list);
        JMenu edit_menu, track_menu, effect_menu;
        JMenuItem cut_item, paste_item, erase_item;
        JMenuItem merge_item, resample_item;
        JMenuItem amplitude_item, normalize_item, clip_item, reverse_item;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        //Here we create the drop-downs and the menu buttons, add the menu buttons to the
        //drop-downs, and add ActionListeners to each menu button.
        edit_menu = new JMenu("Edit");
            cut_item = new JMenuItem("Cut");
            paste_item = new JMenuItem("Paste");
            erase_item = new JMenuItem("Erase");
        track_menu = new JMenu("Track");
            merge_item = new JMenuItem("Merge");
            resample_item = new JMenuItem("Resample");
        effect_menu = new JMenu("Effect");
            amplitude_item = new JMenuItem("Adjust Amplitude");
            normalize_item = new JMenuItem("Normalize");
            clip_item = new JMenuItem("Clip Amplitude");
            reverse_item = new JMenuItem("Reverse");
            
        add(edit_menu);
            edit_menu.add(cut_item);
            edit_menu.add(paste_item);
            edit_menu.add(erase_item);
        add(track_menu);
            track_menu.add(merge_item);
            track_menu.add(resample_item);
        add(effect_menu);
            effect_menu.add(amplitude_item);
            effect_menu.add(normalize_item);
            effect_menu.add(clip_item);
            effect_menu.add(reverse_item);
        
        cut_item.addActionListener(this);
        paste_item.addActionListener(this);
        erase_item.addActionListener(this);
        merge_item.addActionListener(this);
        resample_item.addActionListener(this);
        amplitude_item.addActionListener(this);
        normalize_item.addActionListener(this);
        clip_item.addActionListener(this);
        reverse_item.addActionListener(this);
        
    }    

    //All actions utilize a "checker" to ensure that the tracks_list is not empty, and therefore 
    //the user has a track to interact with. This is the first thing done with every action.
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	//Selection "Cut" allows the user to
        if (e.getActionCommand().equals("Cut")) {
        	
        	if (getTracksList().isEmpty()) {
        		
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
                
        	}
        }
        
        
        //Selection "Paste" allows the user to
        if (e.getActionCommand().equals("Paste")) {
        	
        	if (getTracksList().isEmpty()) {
 
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
        	
        	}
        	else {
        		
        	}
        }
        
        
        //Selection "Erase" allows the user to
        if (e.getActionCommand().equals("Erase")) {
        	
        	if (getTracksList().isEmpty()) {
        	
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
        	
        	}
        	else {
            	
            	File current_file = getTracksList().get(getMainDisplayWindow().getCurrentTrack());
            	File write_file = new File("C:\\Users\\dopda\\Desktop\\DAW WAV Files\\" + "1" + getTracksList().get(getMainDisplayWindow().getCurrentTrack()).getName());
            	
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
        			
        				s = (short) 0;
        				byte b3 = ((byte) (s & 0xff));
        				byte b4 = ((byte) (s >> 8 & 0xff));
        				out.write(b3);
        				out.write(b4);
        			
        			}
        			
        		} catch (IOException t) {
        			// TODO Auto-generated catch block
        			t.printStackTrace();
        		}
            	
            }
        }
        
        
        //Selection "Merge" allows the user to smash two file waveforms together. This is 
        //accomplished by...
        //First we check to see if tracks_list has two or more tracks. If there is not, we notify
        //the user with a pop-up window, do nothing, and return to the main display. Otherwise, we
        //prompt the user to select a track to merge the current track with. If the user cancels
        //or selects the current track, a pop-up window appears notifying them of their error, we 
        //do nothing, and return to the main display.
        if (e.getActionCommand().equals("Merge")) {
        	
        	if (getTracksList().isEmpty() || getTracksList().size() == 1) {
        		
        		JOptionPane need_two_or_more_tracks_notification = new JOptionPane();
        		need_two_or_more_tracks_notification.showMessageDialog(this, "Need two or more tracks in order to Merge!");
        	
        	}
        	else {
        	
        		//merge_possible_values works as the options for the drop-down menu from which the user can 
        		//select a track to merge with the current track
	        	Object[] merge_possible_values = new Object[getTracksList().size()];
	        	int i;
	        	
	        	//Here we go through tracks_list and add the name of each to possibleVales except for 
	        	//the name of the current track
	        	for (i=0; i<getTracksList().size(); i++) {
	        		
	        		if (getTracksList().indexOf(getTracksList().get(i)) != getMainDisplayWindow().getCurrentTrack()) {
	        			
	        			merge_possible_values[i] = getTracksList().get(i).getName();
	        			
	        		}
	        	}
	        	
	        	try {
		    		File merge_selected_value = (File) JOptionPane.showInputDialog(null, "Select a Track to Merge With", "Merge Tracks", JOptionPane.INFORMATION_MESSAGE, null, merge_possible_values, merge_possible_values[0]);
	        	}
	        	catch (Exception excpt) {
	        		JOptionPane can_not_merge_tracks_notification = new JOptionPane();
	        		can_not_merge_tracks_notification.showMessageDialog(this, "Select a valid Track to merge with");
	        	}
        	}
        }
        
        
        //Selection "Resample" allows the user to
        //TODO Make sure the user doesn't resample to the current sample rate. Use pop-up window and do nothing.
        if (e.getActionCommand().equals("Resample")) {
        	
        	if (getTracksList().isEmpty()) {
        		
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
        	}
        	else {
        		Object[] resample_possible_values = {"44100", "22050", "11025"};
        		String resample_selected_value = (String) JOptionPane.showInputDialog(null, "Choose a rate to Resample by:", "Resample Rate", JOptionPane.INFORMATION_MESSAGE, null, resample_possible_values, resample_possible_values[0]);
        		System.out.println("SELECT VALUE " + resample_selected_value);
        		
        		int resample_rate = Integer.parseInt(resample_selected_value);
        		

            	
            	File current_file = getTracksList().get(getMainDisplayWindow().getCurrentTrack());
            	
            	FileInputStream in = null;
            	
        		try {
        			in = new FileInputStream(current_file);
        		} catch (FileNotFoundException e1) {
        			e1.printStackTrace();
        		}
        		
        		int current_rate = 0;
        		
        		try {
        			for (int i = 0; i<32; i++) {
        				
        				while(i < 44 ) {
        					
        					byte b1 = (byte) (in.read() & 0xff);
        					byte b2 = (byte) (in.read() & 0xff);
        					byte b3 = (byte) (in.read() & 0xff);
        					byte b4 = (byte) (in.read() & 0xff);
        					i++;
        					if (i == 7) {
        						current_rate = b4 << 24 | b3 << 16 & 0xff0000 | b2 << 8 & 0xff00 | ((int) b1) & 0xff;
        					}
        				}
        				
        			}
        			
        		} catch (IOException t) {
        			// TODO Auto-generated catch block
        			t.printStackTrace();
        		}
        		
        		if (current_rate == resample_rate) {
        			JOptionPane invalid_input_rate_notification = new JOptionPane();
	        		invalid_input_rate_notification.showMessageDialog(this, "Cannot resample to current sample rate!");
	        	
        		}
        		else {
        			getMainDisplayWindow().resample(resample_rate);
        		}
        		
        	}
        	
        	
        	/*
    		Object[] resample_possible_values = {"44100 Hz", "22050 Hz", "11025 Hz"};
    		String resample_selected_value = (String) JOptionPane.showInputDialog(null, "Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null, resample_possible_values, resample_possible_values[0]);
    		System.out.println("SELECT VALUE " + resample_selected_value);
        	
    		//getMainDisplayWindow().getCurrentTrack();
    		 */
    		
        }
        
        
        //Selection "Adjust Amplitude" allows the user to adjust the amplitude of the current track by ..........
        //Here we prompt the user for input, specifically a percentage by which to scale the amplitude. If the user 
        //enters an in valid input (i.e., a number not between 0-1000, nothing at all, a character, etc.), a pop-up 
        //message is shown and nothing is done. The user can cancel the operation by pressing the cancel button.
        if (e.getActionCommand().equals("Adjust Amplitude")) {
        	
        	if (getTracksList().isEmpty()) {
        		
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
                
        	}
        	else {
        		
        		String percentage_input_string = JOptionPane.showInputDialog("Please input a percentage value to scale by:");
        		
        		try {
		        	if (percentage_input_string.equals("")) {
		        			
		        		JOptionPane invalid_input_val_notification = new JOptionPane();
		        		invalid_input_val_notification.showMessageDialog(this, "Percentage input necessary to adjust amplitude");
		        	}
		        	else {
		        		
		        		try {
		        			//We take the user input, convert it to a number, and divide by 100.0 to convert from 
		        			//percentage and get a double.
							double scaling_ratio = Double.parseDouble(percentage_input_string);
							scaling_ratio = scaling_ratio / 100.0;
							
							if (scaling_ratio > 10.0 || scaling_ratio < 0) {
								
								JOptionPane invalid_input_val_notification = new JOptionPane();
								invalid_input_val_notification.showMessageDialog(this, "\"" + percentage_input_string + "\"" + " is not a valid percentage. Please enter a percentage in the range 0% - 1000%");
							}
							else {
								
								Object[] amp_adj_possible_values = {"Clip", "Normalize"};

				        		Object amp_adj_selected_value = JOptionPane.showInputDialog(null, "How would you like to scale?\n(See manual for details on scaling methods)", "Scaling Method", JOptionPane.INFORMATION_MESSAGE, null, amp_adj_possible_values, amp_adj_possible_values[0]);
				        		
				        		System.out.println(amp_adj_selected_value);
				        		
				        		if(amp_adj_selected_value == "Clip") {
				        			System.out.println("beginning to Clip");
			        				getMainDisplayWindow().adjustAmplitudeClip(scaling_ratio);
			        			}
				        		
				        		if (amp_adj_selected_value == "Normalize") {
				        			getMainDisplayWindow().adjustAmplitudeNormalize(scaling_ratio);
				
				        		}
				        		if (amp_adj_selected_value != "Normalize" && amp_adj_selected_value != "Clip") {
				        			
				        			JOptionPane null_input_amp_adj_method_notification = new JOptionPane();
				        			null_input_amp_adj_method_notification.showMessageDialog(this, "Must select a method of scaling.");
				        			
				        		}
							}
						}
						catch (Exception invalid_percentage) {
							JOptionPane invalid_input_str_notification = new JOptionPane();
							invalid_input_str_notification.showMessageDialog(this, "\"" + percentage_input_string + "\"" + " is not a valid percentage");
						}
	        		}
        		}
        		//If the percentage_input_string is null, that means the user cancelled the operation. We do nothing in this case.
        		catch (Exception null_percentage_input_string) {}
        		 
        	}
        }
        
        
        //Selection "Reverse" allows the user to
        if (e.getActionCommand().equals("Reverse")) {
        	
        	if (getTracksList().isEmpty()) {
        		
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
                
        	}
        	else {
            	
            	File current_file = getTracksList().get(getMainDisplayWindow().getCurrentTrack());
            	File write_file = new File(current_file.getPath() + "REVERSE");
            	
            	short[] short_array = new short[(int) (getMainDisplayWindow().getTracksList().get(getMainDisplayWindow().getCurrentTrack()).length() - 44)/2];
            	
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
                                getMainDisplayWindow().addAudioFile(getTracksList().indexOf(write_file));
        			
        		} catch (IOException t) {
        			// TODO Auto-generated catch block
        			t.printStackTrace();
        		}
            	
            }
        }
    }
    
    
    /* ACCESSORS */
    
    MainDisplayWindow getMainDisplayWindow() {
        return main_display_window;
    }
    
    ArrayList<File> getTracksList() {
        return tracks_list;
    }
    
    
    /* MUTATORS */
    
    void setMainDisplayWindow(MainDisplayWindow other) {
    	main_display_window = other;
    }
    
    void setTracksList(ArrayList<File> other) {
        tracks_list = other;
    }
}
