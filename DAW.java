package daw;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.Serializable;
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

public class DAW implements Serializable{

	//We are required to implement this. This keeps track of the "version number" 
	//so that if we update how an object is read or written, we can keep track of 
	//what files are what version
	
	private static final long serialVersionID = 0L;
	
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
        
        //When closed:
        //write out this as an object to a file! using object input/output streams
        //Implement method writeObject
        //Implement method readObject
        //Implement method redObjectNoData
        
    }
    
}
/*
GENERAL TO DO BEFORE FINAL TURN IN:
Remove unnecessary imports
Make sure all try-catch blocks are in order
Commentation
Indentation and perfect white space
*/
