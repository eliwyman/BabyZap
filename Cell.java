
// Program: BabyZap
// Author: Jim Uhl
// Date: Oct 2014
//
// Purpose: This class implements a Jbutton extension called Cell.
//			The cell is clickable and will pass it's current row and column to the logic class for handling.
//
// Notes:
// Based on code found in : http:://faculty.csci.viu.ca/~juhl/teaching/331

public class Cell extends javax.swing.JButton implements java.awt.event.ActionListener
/* OVERVIEW: Implements a button that knows its location in a grid of
     buttons.  When the button is clicked, it calls userMove on the
     instance of BabyGame passed in on creation and passes its row and
     column.
*/
{
    private final int row;
    private final int col;
    private final Logic logic;

    Cell(int row, int col, Logic logic) {
    //REQUIRES: An int r, indicating row index in the grid.
    //REQUIRES: An int c, indicating column index in the grid.
    //REQUIRES: An instance of the Logic class.
    //EFFECTS: Initializes each cell with it's own value for column and row location in the grid.
    //Connects it to the logic class to handle user interaction.

	   super();
	   this.row = row;
	   this.col = col;
	   this.logic = logic;
	   addActionListener(this);
    }
    public int getRow() {
    //EFFECTS: Returns this.row to the caller.
	   return row;
    }
    public int getCol() {
    //EFFECTS: Returns this.col to the caller.
	   return col;
    }
    public void actionPerformed(java.awt.event.ActionEvent e) { 
    //REQUIRES: ActionEvent 
    //EFFECTS: Calls the logic class with parameters self.row and self.col
	   logic.userMove(row, col);
    } 
}
