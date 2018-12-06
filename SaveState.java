package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.*;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
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

public class SaveState implements Serializable{
   
	private static final long serialVersionUID = 1L;
	public static Long currentPosition;
	public static ArrayList<File> tracks_list;
	public static int current_track;
    /*
	public SaveState() {
		currentPosition = 0L;
		tracks_list = null;
		current_track = 0;
	}
	*/
    ArrayList<File> getTracksList() {
        return tracks_list;
    }
    
    void setTracksList(ArrayList<File> other) {
        tracks_list = other;
    }
    
    void setCurrentPosition(Long other) {
    	currentPosition = other;
    }
    
    Long getCurrentPosition() {
    	return currentPosition;
    }
    
    int getCurrentTrack() {
        return current_track;
    }
    
    void setCurrentTrack(int other) {
        current_track = other;
    }
}
