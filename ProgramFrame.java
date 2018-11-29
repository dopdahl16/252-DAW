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

class ProgramFrame extends JFrame
{
    
    private static final String OUR_TITLE = "DAW";
    public ArrayList<File> tracks_list = new ArrayList<File>();

    public ProgramFrame()
    {
        setTitle(OUR_TITLE);
        setLayout(new GridBagLayout());
        
        MainDisplayWindow our_main_display_window = new MainDisplayWindow(this, tracks_list);
        FileExplorerWindow our_file_explorer_window = new FileExplorerWindow(our_main_display_window, tracks_list);
        MenuBar our_menu_bar = new MenuBar(our_main_display_window);
        ToolBar our_tool_bar = new ToolBar(our_file_explorer_window, tracks_list);
        
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
        
        }
    
    }
