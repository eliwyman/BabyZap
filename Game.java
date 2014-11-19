//Program
//Author: Jim Uhl

//awt
import java.awt.Container;

//swing
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class Game extends javax.swing.JPanel
/* EFFECTS: Draws a grid of buttons that can be used to implement a grid game.
*/
{
	public static final int HEIGHT=11;	// default height and width of the play area
	public static final int WIDTH=15;
	private final Logic controller;
	private int height;
	private int width;
	private final Restart restart;
	
	Cell[][] grid;
	
	Game(JTextArea field, Container container) {
	//EFFECTS: Initializes a HEIGHTxWIDTH board with an index,
	//The '+1' in each dimension used for the index.

		super();
		height = HEIGHT+1;	// an extra header row and column
		width = WIDTH+1;
		setLayout(new java.awt.GridLayout(height,width));
		
		//Create the Logic Class, the Controller for the Game
		controller = new Logic(HEIGHT, WIDTH);
		
		// Generate the grid
		grid = new Cell[height][width];
		grid[0][0] = new Cell(0, 0, controller);
		
		for (int i = height-1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = new Cell(i, j, controller);
				//Render the grid, natural graph format
				add(grid[i][j]);
			}
		}

		grid[0][0].setEnabled(false);
		for (int i = 1; i < height; i++) {
			grid[i][0].setText("" + i);
			grid[i][0].setEnabled(false);
			grid[0][i].setText("" + i);
			grid[0][i].setEnabled(false);
		}

		//Add restart button to JTextArea
		restart = new Restart(controller);
		restart.setText("Restart");
		restart.setEnabled(true);
		container.add(restart, BorderLayout.PAGE_START);

		//Initialize the logic's board set-up
		controller.initBoard(grid, field);
	}
}