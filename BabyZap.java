// Program: BabyZap
// Author: Jim Uhl/Eli Wyman
// Date: Oct 2014
//
// Purpose: This class implements a JFrame extension for the BabyZap game.
//          Using containers to add a writable text field for game output.
//
// Notes:
// Based on code found in : http:://faculty.csci.viu.ca/~juhl/teaching/331

//swing
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;

//awt
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;


public class BabyZap extends javax.swing.JFrame
/* OVERVIEW: Implements a Container that contains a window to contain the BabyZap game and a window to contain output for the game.*/
{

    private final int PREFERRED_HEIGHT = 100;
    private final int PREFERRED_WIDTH = 200;
    private final int SIZE = 1000;

    BabyZap() {
	initUI();
    }

    private void initUI() {
    //EFFECTS: Creates a Container to hold a JFrame and JTextArea window to house game contents,
    //renders it to the center of the screen. 

        setTitle("Baby Zap");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        //Set up the BorderLayout
        Container pane = getContentPane();

        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        //Add dashboard
        JTextArea field = new JTextArea(); 
        field.setEnabled(false);
        field.setText("BabyZap Game, Click a cell!");
		field.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));        
        pane.add(field, BorderLayout.PAGE_END);
        pane.add(new Game(field, pane), BorderLayout.CENTER);
    	
		setSize(SIZE, SIZE);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
    //EFFECTS: Creates an instance of BabyZap and sets it as visible.
 
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {

		    BabyZap bz = new BabyZap();
		    bz.setVisible(true);
		}
	    });
    }
}