package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.util.*;
import java.util.*;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

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

///Fix the current_track parameter according to git comment

public class MenuBar extends JMenuBar implements ActionListener{
    
	MainDisplayWindow main_display_window;
	ArrayList<File> tracks_list;
	public int current_track;
	
    public MenuBar(MainDisplayWindow main_display_window, ArrayList<File> tracks_list, int current_track) {
        
    	setMainDisplayWindow(main_display_window);
    	setTracksList(tracks_list);
    	setCurrentTrack(current_track);
        JMenu file_menu, edit_menu, view_menu, track_menu, effect_menu;
        JMenuItem new_item, open_item, save_item;
        JMenuItem cut_item, paste_item, delete_item;
        JMenuItem merge_item, resample_item;
        JMenuItem amplitude_item, normalize_item, clip_item, reverse_item;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        file_menu = new JMenu("File");
            new_item = new JMenuItem("New...");
            open_item = new JMenuItem("Open...");
            save_item = new JMenuItem("Save");
        edit_menu = new JMenu("Edit");
            cut_item = new JMenuItem("Cut");
            paste_item = new JMenuItem("Paste");
            delete_item = new JMenuItem("Delete");
        view_menu = new JMenu("View");
        track_menu = new JMenu("Track");
            merge_item = new JMenuItem("Merge");
            resample_item = new JMenuItem("Resample");
        effect_menu = new JMenu("Effect");
            amplitude_item = new JMenuItem("Adjust Amplitude");
            normalize_item = new JMenuItem("Normalize");
            clip_item = new JMenuItem("Clip Amplitude");
            reverse_item = new JMenuItem("Reverse");
        
        add(file_menu);
            file_menu.add(new_item);
            file_menu.add(open_item);
            file_menu.add(save_item);
        add(edit_menu);
            edit_menu.add(cut_item);
            edit_menu.add(paste_item);
            edit_menu.add(delete_item);
        add(view_menu);
        add(track_menu);
            track_menu.add(merge_item);
            track_menu.add(resample_item);
        add(effect_menu);
            effect_menu.add(amplitude_item);
            effect_menu.add(normalize_item);
            effect_menu.add(clip_item);
            effect_menu.add(reverse_item);
        
        new_item.addActionListener(this);
        open_item.addActionListener(this);
        save_item.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New...")){
        	//getMainDisplayWindow().addAudioFile();
        	//Create blank .wav file??? Or write empty argument constructor for addAudioFile()
        }
        if (e.getActionCommand().equals("Open...")){
            
        }
        if (e.getActionCommand().equals("Save")){
            
        }
        if (e.getActionCommand().equals("Cut")){
            
        }
        if (e.getActionCommand().equals("Paste")){
            
        }
        if (e.getActionCommand().equals("Delete")){
            
        }
        if (e.getActionCommand().equals("Merge")){
            
        }
        if (e.getActionCommand().equals("Resample")){
            
        }
        if (e.getActionCommand().equals("Adjust Amplitude")){
            
        }
        if (e.getActionCommand().equals("Normalize")){
            
        }
        if (e.getActionCommand().equals("Clip Amplitude")){
            
        }
        if (e.getActionCommand().equals("Reverse")){
            
        }
        
    }
    
    MainDisplayWindow getMainDisplayWindow(){
        return main_display_window;
    }
    
    void setMainDisplayWindow(MainDisplayWindow other) {
    	main_display_window = other;
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
}
