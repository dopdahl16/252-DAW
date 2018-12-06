package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.*;
import javax.swing.*;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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


////REPLACE DESKTOP SAVING OF STATE PATH WITH "src/data/state_restore/state.ser"


public class ProgramFrame extends JFrame {
	
	private static final String our_title = "DAW";
    public static ArrayList<File> tracks_list = new ArrayList<File>();
    public static int current_track;
    public static MainDisplayWindow our_main_display_window;
    public static FileExplorerWindow our_file_explorer_window;
    public static MenuBar our_menu_bar;
    public static ToolBar our_tool_bar;
    public SaveState save_state;

    public ProgramFrame() {
    	
    	File f = new File("C:\\\\Users\\\\dopda\\\\Desktop\\\\DAWSTORAGE\\\\state.ser");
    	
    	if(f.exists() && !f.isDirectory()) { 
    		
    		try {
    	        FileInputStream fileIn = new FileInputStream("C:\\\\Users\\\\dopda\\\\Desktop\\\\DAWSTORAGE\\\\state.ser");
    	        ObjectInputStream in = new ObjectInputStream(fileIn);
    	        setTracksList((ArrayList<File>) in.readObject());
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
    	
        setTitle(our_title);
        setLayout(new GridBagLayout());
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
				try {
					/*
					getSaveState().setTracksList(getMainDisplayWindow().getTracksList());
					System.out.println("SAVing track list: " + getSaveState().getTracksList());
					getSaveState().setCurrentTrack(getMainDisplayWindow().getCurrentTrack());
					*/
                	FileOutputStream fileOut = new FileOutputStream(new File("C:\\Users\\dopda\\Desktop\\DAWSTORAGE\\state.ser"));
                	ObjectOutputStream out = new ObjectOutputStream(fileOut);
                	out.writeObject(getTracksList());
                	out.close();
                	fileOut.close();
                }
                catch(IOException i) {
                	i.printStackTrace();
                }
				
            }
        });
        
        our_main_display_window = new MainDisplayWindow(this, tracks_list, current_track);
        our_file_explorer_window = new FileExplorerWindow(our_main_display_window, tracks_list, current_track);
        our_menu_bar = new MenuBar(our_main_display_window, tracks_list);
        our_tool_bar = new ToolBar(our_main_display_window, our_file_explorer_window, tracks_list, save_state);
        save_state = new SaveState();
        
        GridBagConstraints menuBarConstraints = new GridBagConstraints();
        GridBagConstraints toolBarConstraints = new GridBagConstraints();
        GridBagConstraints audioDisplayWindowConstraints = new GridBagConstraints();
        GridBagConstraints fileExplorerWindowConstraints = new GridBagConstraints();
        
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
        audioDisplayWindowConstraints.gridx = 0;
        audioDisplayWindowConstraints.gridy = 2;
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
        getContentPane().add(our_main_display_window, audioDisplayWindowConstraints);
        getContentPane().add(our_file_explorer_window, fileExplorerWindowConstraints);
        our_file_explorer_window.setVisible(true);
        pack();
        
        System.out.println(getTracksList());
        //adds possible tracks in the trackslist into the maindisplay
    	for(int i = 0; i<getTracksList().size(); i++) {
			getMainDisplayWindow().addAudioFile(i);
			System.out.println("AAA");
		}
    }
    
    ArrayList<File> getTracksList(){
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other){
        tracks_list = other;
    }
    
    MainDisplayWindow getMainDisplayWindow(){
        return our_main_display_window;
    }
    
    void setMainDisplayWindow(MainDisplayWindow other){
    	our_main_display_window = other;
    }
    
    SaveState getSaveState() {
		return save_state;
    }
    
    void setSaveState(SaveState other) {
    	save_state = other;
    }
}
