public class Cell extends javax.swing.JButton implements java.awt.event.ActionListener
/* Purpose: Implements a button that knows its location in a grid of
     buttons.  When the button is clicked, it calls userMove on the
     instance of BabyGame passed in on creation and passes its row and
     column.
*/
{
    private final int row;
    private final int col;
    private final Logic logic;

    Cell(int r, int c, Logic l) {
	   super();
	   row = r;
	   col = c;
	   logic = l;
	   addActionListener(this);
    }
    public int getRow() {
	   return row;
    }
    public int getCol() {
	   return col;
    }
    public void actionPerformed(java.awt.event.ActionEvent e) { 
	   logic.userMove(row, col);
    } 
}
