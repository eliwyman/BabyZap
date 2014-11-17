import javax.swing.JTextArea;

public class Logic
/*OVERVIEW: This class handles all game logic,
when a cell is clicked it's location is passed to this function
to handle how the game will react to said mouse action.
*/
{
	private final int HEIGHT;
	private final int WIDTH;

	private Sprite sprite;
	private Cell[][] grid;
	private JTextArea field;
	//origin location
	private final int ORIGIN = 1;
	//ship co-ordinates
	private int shipR, shipC = 1;
	//ship destination
	private int newR, newC;
	//user turns
	private int turns = 0;
	private final int TURN_LIMIT = 99;
	//mine location array
	private int[][] mines;
	//turn display
	private String display;


	Logic(int h, int w){
	//REQUIRES: int h, indicating board height (less the row used for index).
	//REQURES: int w, indicating board width (less the column used for index).
	//REQUIRES: long e, indicating initial ship energy
	//REQUIRES: long max, indicating max ship energy		
	//REQUIRES: int dmg, indicating percentage of damage induced by each mine hit.
	//MODIFIES: set's the HEIGHT, WIDTH, s, turnHits, and display instance variables.

		HEIGHT = h;
		WIDTH = w;
		sprite = new Sprite();
		display = "";
		mines = new int[HEIGHT][WIDTH];

		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				mines[i][j] = 0;
			}			
		}

	}

	public void initBoard(Cell[][] g, JTextArea f) {
	//REQUIRES: An instance of the grid
	//REQUIRES: The JTextArea for the game, used for printing to the user.
	//MODIFIES: set's the grid and field instance variables.
	//EFFECTS: Displays the initial board graphics.

		grid = g;
		field = f;
		//Launch the shuttle
		grid[1][1].setText(sprite.getShip());
		shipR = shipC = 1;
		//Generate the star gates
		String star = sprite.getGate();
		grid[HEIGHT-1][WIDTH].setText(star);
		grid[HEIGHT-1][WIDTH-1].setText(star);
		grid[HEIGHT][WIDTH-1].setText(star);
		//Generate the star port
		grid[HEIGHT][WIDTH].setText(sprite.getPort());
	}

	public void userMove(int row, int col) {
	//REQUIRES: int row, indicating where the user clicked.
	//REQUIRES: int col, indicating where the user clicked.
	//MODIFIES: newC and newR are set to the new destination of the user.
	//EFFECTS: Will tell the user they have picked an invalid destination and display their current energy.
	//EFFECTS: Or, will tell the user they have used up all their turns, and end the game.
	//EFFECTS: Or, will tell the user they have died and display their current energy, also ending the game.
	//EFFECTS: Or, will move the user ship to their destination, either where they clicked or the origin if they were hit.
	//Displaying their energy.

		newC = col;
		newR = row;
		double dist = distance(newR, newC);
		//User clicked an invalid cell
		if (!sprite.enoughFuel(dist) || starGates(newC,newR)) {
			turnDisplay("Invalid Move"); 
			turnDisplay("Current Energy:"+sprite.getEnergy());
			printTurn();
		//User ran out of turns
		} else if (turns > TURN_LIMIT) {
			turnDisplay("You have run out of time!");
			printTurn();
			endGame(false);
		//Ship got hit by a mine
		} else if (shipHit()) {
			//Ship ran out of energy
			if (sprite.shipHit()) {
				turnDisplay("Current Energy:"+sprite.getEnergy());
				turnDisplay("MAYDAY, MAYDAY, We're going down!");
				printTurn();
				endGame(false);
				return;
			//Ship survived the turn
			} else {
				//Reroute the ship to the origin
				newR = newC = ORIGIN;
				moveShip(0);
				turns++;
				turnDisplay("Current Energy:"+sprite.getEnergy());
				printTurn();
			}
		//Ship dodged the mines
		} else {
			moveShip(dist);
			turns++;
			turnDisplay("Current Energy:"+sprite.getEnergy());
			printTurn();
		}
    }

    public double distance(int row, int col) {
	//REQUIRES: int row, indicating where the user clicked, value from 1-9.
	//REQUIRES: int col, indicating where the user clicked, value from 1-9.
	//EFFECTS: Returns the distance between current ship co-ordinates and the destination co-ordinates
    //The algorithm being that a move is equal to the horizontal distance + vertical distance.

    	double x1 = row;
    	double x2 = shipR;
    	double y1 = col;
    	double y2 = shipC;

    	return (Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }

    private boolean shipHit() {
	//EFFECTS: The computer randomly generates a mine and place it on the board.
	//EFFECTS: Returns true or false indicating whether the ship was hit by a mine this turn.
		int x = (int) Math.floor(1.0 + Math.random() * HEIGHT);
		int y = (int) Math.floor(1.0 + Math.random() * HEIGHT);

    	if (handleMine(x,y)) {
    		turnDisplay("Ship's been hit!");
    		return true;
    	}


    	return false;
    }

    private void moveShip(double dist) {
    //REQUIRES: an int dist, specifying the distance of the user's move, between 1-16
    //EFFECTS: will fuel the ship if it's stays in place,
    //EFFECTS: or, will end the game if the user moved to the star-port,
    //EFFECTS: or, will move the ship accordingly to a new cell on the board
    //Ending the game if the user used all his fuel irresponsibly (energy dropped below 20 units).

    	//Uses this comparator over (dist == 0), so we are not charged fuel to
    	//return to the origin when hit by a mine.
		if(newR == shipR && newC == shipC){
			sprite.shipHeal();
		} else if (newR == HEIGHT && newC == WIDTH){
			sprite.useFuel(dist);
			endGame(true);
		} else {
			grid[shipR][shipC].setText("");
    		shipR = newR;
			shipC = newC;
			grid[shipR][shipC].setText(sprite.getShip());
			mines[shipR][shipC] = 0;
			sprite.useFuel(dist);
			if (sprite.shipDead()) endGame(false);
		}
    }

    public Boolean handleMine(int x, int y) {
    //REQUIRES: an int x, indicating x-co-ordinates for the mine, between 1-9.
    //REQUIRES: an int y, indicating y-co-ordinates for the mine, between 1-9.
    //EFFECTS: If the mine was not placed on either: the ship, the star-port, or the stargates,
    //The mine will be drawn on the board, and this function will return 0.
    //EFFECTS: Otherwise, this function will return true.

    	if (!(x == newR && y == newC)) {
			if(!(starGates(x,y)) && !(x == WIDTH && y == HEIGHT)) {
				if (mines[x][y] == 0) {
					grid[x][y].setText(sprite.getKMine());
					mines[x][y] = 1;
				} else {
					grid[x][y].setText(sprite.getLMine());
					mines[x][y] = 2;
				}
    			return false;
    		}
    	}
    	return true;
    }

    public boolean starGates(int x, int y) {
    //REQUIRES: int x, and int y, indicating co-ordinates.
    //EFFECTS: will return true if the co-ordinates match starGate locations.
    //EFFECTS: Otherwise, will return false.

    	return ((x == WIDTH-1 && y == HEIGHT) || (x == WIDTH && y == HEIGHT-1) || (x == WIDTH-1 && y == HEIGHT-1));
    }

    private void endGame(boolean won){
    //REQUIRES: boolean variable indicating if the user won the game.
    //MODIFIES: All board-cells become disabled.
    //EFFECTS: The game ends with a display message based on whether or not the user won the game.

    	//declare all cells false
		for (int i = 1; i <= HEIGHT; i++) {
			for (int j = 1; j <= HEIGHT; j++) {
			    grid[i][j].setText("");
			    grid[i][j].setEnabled(false);
			    grid[j][j].setText("");
			    grid[i][j].setEnabled(false);
			}
		}

		//print "YOU on the cells
		grid[6][4].setText("Y");
		grid[6][5].setText("O");
		grid[6][6].setText("U");

		if(won){
			grid[4][4].setText("W");
			grid[4][5].setText("O");
			grid[4][6].setText("N");
		} else {
			grid[4][3].setText("L");
			grid[4][4].setText("O");
			grid[4][5].setText("S");
			grid[4][6].setText("T");
			grid[4][7].setText("!");
    	}
    }

    private void turnDisplay(String s) {
    //REQUIRES: text String with input to be written to the user at end of turn.
    //MODIFIES: the display String is concatenated with 's'

    	display = display + s + "\n";
    }

    private void printTurn() {
    //MODIFIES: The display string is reset to the empty string at the end of this function.
    //EFFECTS: Prints the contents of the display string to the screen.

    	field.setText(display);
    	display = "";
    }

    public void setCoords(int row, int col){
    //REQUIRES: an int row, indicating the ship's x-co-ordinates, between 1 and 9.
    //REQUIRES: an int col, indicating the ship's y-co-ordinates, between 1 and 9.
    //MODIFIES: updates the private instance variables shipR and shipC.
    
    	shipR = row;
    	shipC = col;
    }

    public void setNewCoords(int row, int col){
    //REQUIRES: an int row, indicating the ship's x-co-ordinates, between 1 and 9.
    //REQUIRES: an int col, indicating the ship's y-co-ordinates, between 1 and 9.
    //MODIFIES: updates the private instance variables newR and newC.
    
    	newR = row;
    	newC = col;
    }
}