public class Game extends javax.swing.JPanel	
/* Purpose: Draws a grid of buttons that can be used to implement a grid game.
*/
{
	public static final int HEIGHT=9;	// default height and width of the play area
	public static final int WIDTH=9;
	public static final long ENERGY = 100;
	private static final long MAX = 1000;
	private static final int DMG = 30;
	private int height;
	private int width;
	
	Cell[][] grid;
	
	Game() {
		super();
		height = HEIGHT+1;	// an extra header row and column
		width = WIDTH+1;
		setLayout(new java.awt.GridLayout(height,width));
		
		//Create the Logic Class, the Controller for the Game
		Logic control = new Logic(HEIGHT, WIDTH, ENERGY, MAX, DMG);
		
		// Generate the grid
		grid = new Cell[height][width];
		grid[0][0] = new Cell(0, 0, control);
		
		for (int i = height-1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = new Cell(i, j, control);
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
		//Initialize the logic's board set-up
		control.initBoard(grid);
	}
}