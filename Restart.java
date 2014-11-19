public class Restart extends javax.swing.JButton implements java.awt.event.ActionListener
/* OVERVIEW: Implements a button that knows its location in a grid of
     buttons.  When the button is clicked, it calls userMove on the
     instance of BabyGame passed in on creation and passes its row and
     column.
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
    //EFFECTS: Calls the logic class with parameters self.row and self.col
	   logic.restart();
    } 
}
