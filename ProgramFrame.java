package daw;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

////TODO REPLACE DESKTOP SAVING OF STATE PATH WITH "src/data/state_restore/state.ser"

//The ProgramFrame class is where our swing components are added and we hold "global" objects e.g., 
//current_track. These objects are needed by most components are thus are stored at the highest
//level, i.e., here.

public class ProgramFrame extends JFrame {
	
	/* FIELDS */
	
	private static final String our_title = "DAW";
    public static ArrayList<File> tracks_list = new ArrayList<File>();
    public static int current_track;
    public static MainDisplayWindow our_main_display_window;
    public static FileExplorerWindow our_file_explorer_window;
    public static MenuBar our_menu_bar;
    public static ToolBar our_tool_bar;
    public static JLabel our_current_track_display;
    public long position = 0;

    
    /* CONSTRUCTOR */
    
    public ProgramFrame() {
    	
    	//First we check to see if there is a saved state of the application at the designated file
    	//path. If there is, we read in the saved objects (ArrayList<File>, long, int) and set them
    	//to be this ProgramFrame's tracks_list, position, and current_track, respectively.
    	File restore_state = new File("C:\\\\Users\\\\dopda\\\\Desktop\\\\DAWSTORAGE\\\\state.ser");
    	
    	if(restore_state.exists() && !restore_state.isDirectory()) { 
    		
    		try {
    	        FileInputStream fileIn = new FileInputStream("C:\\\\Users\\\\dopda\\\\Desktop\\\\DAWSTORAGE\\\\state.ser");
    	        ObjectInputStream in = new ObjectInputStream(fileIn);
    	        setTracksList((ArrayList<File>) in.readObject());
    	        position = (long) in.readObject();
    	        //System.out.println("position: " + position);
    	        current_track = (int) in.readObject();
    	        in.close();
    	        fileIn.close();
    	      } 
    		
    		catch (IOException i) {
    	         i.printStackTrace();
    	         return;
    	      } 
    		
    		catch (ClassNotFoundException c) {
    	         System.out.println("Class not found");
    	         c.printStackTrace();
    	         return;
    	      }
    		
    	}
    	
    	//Here we set the title and layout style of our ProgramFrame.
        setTitle(our_title);
        setLayout(new GridBagLayout());
        
        //This WindowListener is activated when the ProgramFrame is closed. First, we prompt the user
        //if they would like to save the state of the application or not. If they select yes, we save
        //the current state of the application (detailed below), if not, then we ask the user if they
        //would like to delete the previous saved state (if there is one). If they select yes, we remove
        //the previously saved state, if no, then we leave it.
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
				try {
					int save_reply = JOptionPane.showConfirmDialog(null, "Would you like to save the state of the application?", "Save?", JOptionPane.YES_NO_OPTION);
			        
					if (save_reply == JOptionPane.YES_OPTION) {
					
						//Here, we create a FileOutputStream to our destination file, then we create a 
						//ObjectOutputStream to our FileOutputStream. Next, we write the track_list. 
						//Then, we attempt to write the position of the current clip, so the user can
						//resume the same clip in the same place on restart. However, if that is not 
						//possible (e.g., there is no clip object in our_tool_bar), then we simply 
						//write a "0". Then, we write current_track and close our streams.
	                	FileOutputStream destination_file = new FileOutputStream(new File("C:\\Users\\dopda\\Desktop\\DAWSTORAGE\\state.ser"));
	                	ObjectOutputStream obj_output_stream = new ObjectOutputStream(destination_file);
	                	obj_output_stream.writeObject(getTracksList());
	                	
	                	try {
	                		obj_output_stream.writeObject(our_tool_bar.getClip().getMicrosecondPosition());
	                	}
	                	catch (Exception could_not_get_positon) {
	                		obj_output_stream.writeObject(0L);
	                	}
	                	
	                	obj_output_stream.writeObject(getMainDisplayWindow().getCurrentTrack());
	                	obj_output_stream.close();
	                	destination_file.close();
	                	
			        }
			        else {
			        	
			        	File previous_state = new File("C:\\\\Users\\\\dopda\\\\Desktop\\\\DAWSTORAGE\\\\state.ser");
			        	
			        	if(previous_state.exists() && !previous_state.isDirectory()) {
			        		
			        		//If the user elected not to save the state of the application, we prompt 
			        		//them if they would like to remove the previously saved state.
			        		int delete_response = JOptionPane.showConfirmDialog(null, "Delete previously saved state?", "Remove?", JOptionPane.YES_NO_OPTION);
					       
			        		if (delete_response == JOptionPane.YES_OPTION) {
			        			
			        			//If the user elects to remove the previous state, we delete the file.
					        	previous_state.delete();
					        	
					        }
			        	}
			        }
                }
				//This catch clause catches all IOExceptions that may be throw while the streams are
				//writing or opening.
                catch (IOException i) {
                	i.printStackTrace();
                }
            }
        });
        
        //Here we create the main components of our ProgramFrame.
        our_main_display_window = new MainDisplayWindow(this, tracks_list, current_track);
        our_file_explorer_window = new FileExplorerWindow(our_main_display_window, tracks_list, current_track);
        our_menu_bar = new MenuBar(our_main_display_window, tracks_list);
        our_tool_bar = new ToolBar(our_main_display_window, our_file_explorer_window, tracks_list, position);
        
        try {
        	our_current_track_display = new JLabel("Current Track: " + getTracksList().get(current_track).getName());
        }
        //This catch clause catch catches any Exception that may be thrown while creating 
        //our_current_track_display, e.g., empty tracks_list, current_track does not exist.
        catch (Exception our_current_track_display_generalExcpt) {
        	our_current_track_display = new JLabel("No Current Track");
        }
            
        
        //Here we create, specify, and add the GridBagConstraints for every component of our ProgramFrame. 
        GridBagConstraints menuBarConstraints = new GridBagConstraints();
        GridBagConstraints toolBarConstraints = new GridBagConstraints();
        GridBagConstraints audioDisplayWindowConstraints = new GridBagConstraints();
        GridBagConstraints fileExplorerWindowConstraints = new GridBagConstraints();
        GridBagConstraints currentTrackDisplayConstraints = new GridBagConstraints();
        
        menuBarConstraints.gridx = 0;
        menuBarConstraints.gridy = 0;
        menuBarConstraints.fill = GridBagConstraints.HORIZONTAL;
        menuBarConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        menuBarConstraints.weightx = 0;
        menuBarConstraints.weighty = 0;
        menuBarConstraints.gridwidth = 2;
        toolBarConstraints.gridx = 0;
        toolBarConstraints.gridy = 1;
        toolBarConstraints.fill = GridBagConstraints.HORIZONTAL;
        toolBarConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        toolBarConstraints.weightx = 0;
        toolBarConstraints.weighty = 0;
        toolBarConstraints.ipady = 40;
        currentTrackDisplayConstraints.gridx = 0;
        currentTrackDisplayConstraints.gridy = 2;
        currentTrackDisplayConstraints.fill = GridBagConstraints.HORIZONTAL;
        currentTrackDisplayConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        currentTrackDisplayConstraints.weightx = 0;
        currentTrackDisplayConstraints.weighty = 0;
        currentTrackDisplayConstraints.gridwidth = 2;
        audioDisplayWindowConstraints.gridx = 0;
        audioDisplayWindowConstraints.gridy = 3;
        audioDisplayWindowConstraints.fill = GridBagConstraints.BOTH;
        audioDisplayWindowConstraints.anchor = GridBagConstraints.CENTER;
        audioDisplayWindowConstraints.weightx = 1;
        audioDisplayWindowConstraints.weighty = 1;
        fileExplorerWindowConstraints.gridx = 1;
        fileExplorerWindowConstraints.gridy = 1;
        fileExplorerWindowConstraints.gridheight = 3;
        fileExplorerWindowConstraints.fill = GridBagConstraints.BOTH;
        fileExplorerWindowConstraints.anchor = GridBagConstraints.CENTER;
        fileExplorerWindowConstraints.weightx = 0;
        fileExplorerWindowConstraints.weighty = 0;
        
        getContentPane().add(our_menu_bar, menuBarConstraints);
        getContentPane().add(our_tool_bar, toolBarConstraints);
        getContentPane().add(our_current_track_display, currentTrackDisplayConstraints);
        getContentPane().add(our_main_display_window, audioDisplayWindowConstraints);
        getContentPane().add(our_file_explorer_window, fileExplorerWindowConstraints);
        our_file_explorer_window.setVisible(true);
        pack();
        
        //This for loop loads all tracks in tracks_list into our_main_display_window, if there to be
        //tracks already in tracks_list, i.e., tracks already loaded into tracks_list via a saved state.
    	for(int i = 0; i<getTracksList().size(); i++) {
			getMainDisplayWindow().addAudioFile(i);
		}
    }
    
    
    /* ACCESSORS */
    
    ArrayList<File> getTracksList() {
        return tracks_list;
    }
    
    MainDisplayWindow getMainDisplayWindow() {
        return our_main_display_window;
    }
   
    int getCurrentTrack() {
        return current_track;
    }
    
    
    /* MUTATORS */
    
    void setTracksList(ArrayList<File> other) {
        tracks_list = other;
    }
    
    void setMainDisplayWindow(MainDisplayWindow other) {
    	our_main_display_window = other;
    }
   
    void setCurrentTrack(int other) {
        current_track = other;
        
        try {
        	our_current_track_display.setText("Current Track: "+ getTracksList().get(current_track).getName());
        }
        //This catch clause catch catches any Exception that may be thrown while creating 
        //our_current_track_display, e.g., empty tracks_list, current_track does not exist.
        //If such an error occurs, it means there is no current track, so we set the text of
        //our_current_track_display accordingly.
        catch (Exception except) {
        	our_current_track_display.setText("No Current Track");
        }
        
        our_current_track_display.revalidate();
        our_current_track_display.repaint();
    }
}
