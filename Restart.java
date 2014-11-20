// Program: BabyZap
// Author: Eli Wyman
// Date: Nov 2014
//
// Purpose: This class implements a Jbutton extension called Restart
//          The cell is clickable and will call logic's restart function, effectively restarting the game.
//
// Notes:
// Based on code found in : http:://faculty.csci.viu.ca/~juhl/teaching/331

public class Restart extends javax.swing.JButton implements java.awt.event.ActionListener
/* OVERVIEW: Implements a button that, when clicked calls logic.restart(), 
resetting instance variables and allowing the game to played again.
*/
{
    private final Logic logic;

    Restart(Logic logic) {
    //REQUIRES: An int r, indicating row index in the grid.
    //REQUIRES: An int c, indicating column index in the grid.
    //REQUIRES: An instance of the Logic class.
    //EFFECTS: Initializes each cell with it's own value for column and row location in the grid.
    //Connects it to the logic class to handle user interaction.

	   super();
	   this.logic = logic;
	   addActionListener(this);
    }

    public void actionPerformed(java.awt.event.ActionEvent e) { 
    //REQUIRES: ActionEvent 
    //EFFECTS: Calls the logic classes restart function
	   logic.restart();
    } 
}
