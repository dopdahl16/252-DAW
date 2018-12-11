package daw;

import java.awt.*;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author opdada01 Daniel Opdahl
 * @author mantno01 Noah Manternach
 * @author nteste01 Teboho Nteso
 * 
 */

//The DAW class holds the main function. This class only creates and holds the ProgramFrame, which
//could be described as our "true" main class.

public class DAW {
	
	public ProgramFrame frame;
	
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(
            new Runnable() {

                public void run() {

                	ProgramFrame frame = new ProgramFrame();

                	//Here we set the closing operation and the default and minimum sizes
                	//for our ProgramFrame
                	
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setMinimumSize(new Dimension(800,500));
                    frame.setVisible(true);
                    
                    }

                }
            );
        
    }
    
}
/*
GENERAL TO DO BEFORE FINAL TURN IN:
Indentation and perfect white space
check public/private field declarations
Go over System.out 's 
*/
