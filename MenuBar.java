package daw;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

////TODO GET RID OF DELETE BUTTON????? MAYBE DON'T NEED IT? - IMPLEMENT IF TIME

//The MenuBar class provides the user with various options via a drop-down menu, and allows our
//application to handle user selections. 

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
        JMenuItem cut_item, paste_item, delete_item;
        JMenuItem merge_item, resample_item;
        JMenuItem amplitude_item, normalize_item, clip_item, reverse_item;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        //Here we create the drop-downs and the menu buttons, add the menu buttons to the
        //drop-downs, and add ActionListeners to each menu button.
        edit_menu = new JMenu("Edit");
            cut_item = new JMenuItem("Cut");
            paste_item = new JMenuItem("Paste");
            delete_item = new JMenuItem("Delete");
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
            edit_menu.add(delete_item);
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
        delete_item.addActionListener(this);
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
        }
        
        
        //Selection "Delete" allows the user to
        if (e.getActionCommand().equals("Delete")) {
        	if (getTracksList().isEmpty()) {
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
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
        	
        		//possibleValues works as the options for the drop-down menu from which the user can 
        		//select a track to merge with the current track
	        	Object[] possibleValues = new Object[getTracksList().size()];
	        	int i;
	        	
	        	//Here we go through tracks_list and add the name of each to possibleVales except for 
	        	//the name of the current track
	        	for (i=0; i<getTracksList().size(); i++) {
	        		
	        		if (getTracksList().indexOf(getTracksList().get(i)) != getMainDisplayWindow().getCurrentTrack()) {
	        			
	        			possibleValues[i] = getTracksList().get(i).getName();
	        			
	        		}
	        	}
	        	
	        	try {
		    		File selectedValue = (File) JOptionPane.showInputDialog(null, "Select a Track to Merge With", "Merge Tracks", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
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
        	
    		Object[] possibleValues = {"44100 Hz", "22050 Hz", "11025 Hz"};
    		String selectedValue = (String) JOptionPane.showInputDialog(null, "Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
    		System.out.println("SELECT VALUE " + selectedValue);
        	
    		//getMainDisplayWindow().getCurrentTrack();
    		
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
        		
        		String input_string = JOptionPane.showInputDialog("Please input a percentage value to scale by:");
        		
        		try {
		        	if (input_string.equals("")) {
		        			
		        		JOptionPane invalid_input_val_notification = new JOptionPane();
		        		invalid_input_val_notification.showMessageDialog(this, "Percentage input necessary to adjust amplitude");
		        	}
		        	else {
		        		
		        		try {
		        			//We take the user input, convert it to a number, and divide by 100.0 to convert from 
		        			//percentage and get a double.
							double inputValue = Double.parseDouble(input_string);
							inputValue = inputValue / 100.0;
							
							if (inputValue > 10.0 || inputValue < 0) {
								
								JOptionPane invalid_input_val_notification = new JOptionPane();
								invalid_input_val_notification.showMessageDialog(this, "\"" + input_string + "\"" + " is not a valid percentage. Please enter a percentage in the range 0% - 1000%");
							}
							else {
								
								getMainDisplayWindow().adjustAmplitude(inputValue);
							}
						}
						catch (Exception invalid_percentage) {
							JOptionPane invalid_input_str_notification = new JOptionPane();
							invalid_input_str_notification.showMessageDialog(this, "\"" + input_string + "\"" + " is not a valid percentage");
						}
	        		}
        		}
        		//If the input_string is null, that means the user cancelled the operation. We do nothing in this case.
        		catch(Exception null_input_string) {}
        	}
        }
        
        
        //Selection "Reverse" allows the user to
        if (e.getActionCommand().equals("Reverse")) {
        	
        	if (getTracksList().isEmpty()) {
        		
        		JOptionPane no_tracks_notification = new JOptionPane();
                no_tracks_notification.showMessageDialog(this, "No Tracks to Edit!");
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
