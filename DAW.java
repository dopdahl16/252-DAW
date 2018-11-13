package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
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

public class DAW {

    public static void main(String[] args) {
        
        EventQueue.invokeLater(
            new Runnable()
            {

                public void run()
                {

                    
                    ProgramFrame frame = new ProgramFrame();

                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setMinimumSize(new Dimension(800,500));
                    frame.setVisible(true);
                    
                    }

                }
            );
        
    }
    
}
