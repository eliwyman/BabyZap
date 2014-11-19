//awt
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//swing
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Logic
/*OVERVIEW: This class handles all game logic,
when a cell is clicked it's location is passed to this function
to handle how the game will react to said mouse action.
*/
{
	private final int HEIGHT;
	private final int WIDTH;


	private final int TURN_LIMIT = 99;
	private enum END_GAME { WIN, MINE_KILL, L_MINE_KILL, L_MINE_LAND, TURN_LIMIT, FUEL_USED}

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
	//mine variables
	private int[][] mines;
	private int numLMines;
	private final int SEVERITY_ONE = 1;
	private final int SEVERITY_TWO = 2;
	private final int NO_MINE = 0;
	private final int K_MINE = 1;
	private final int L_MINE = 2;
	//turn display
	private String display;
	//game variable
	private Boolean GAME_OVER;
	//timer variables
	private Timer timer;
	private Timer timer2;
	private final int REFRESH = 30000; //30 seconds
	private final int SPEED = 500;
	private final int PAUSE = 0;
	private Boolean toggle;
	//animation variables
	private final int ANIMATEMODW = 5;
	private final int ANIMATEMODH = 3;
	private int animateI;
    private int animateJ;
    private static final int ANIMATION_HEIGHT = 8;
    private static final String[][] FLAG =  {
    	{"+", "-", "~", "-","~","-","~","-","~","-","~","-","~","-","+"},
    	{"+", " ", " ", " "," "," "," "," "," "," "," "," "," "," ","+"},
    	{"+", " ", " ", " "," "," "," "," "," "," "," "," "," "," ","+"},
    	{"+", " ", " ", " ","V","I","C","T","O","R","Y"," "," "," ","+"},
    	{"+", " ", " ", " "," "," "," "," "," "," "," "," "," "," ","+"},
    	{"+", " ", " ", " "," "," "," "," "," "," "," "," "," "," ","+"},
    	{"+", " ", " ", " "," "," "," "," "," "," "," "," "," "," ","+"},
    	{"+", "-", "~", "-","~","-","~","-","~","-","~","-","~","-","+"}};

    private static final String[][] ALIEN =  {
    	{"+", " ", " ", " "," ","-","-","-","-","-"," "," "," "," ","+"},
    	{"+", " ", " ", " ","d"," "," "," "," "," ","b"," "," "," ","+"},
    	{"+", " ", " ", "|"," "," "," "," "," "," "," ","|"," "," ","+"},
    	{"+", " ", " ", " ","\\"," "," "," "," "," ","/"," "," "," ","+"},
    	{"+", " ", " ", " "," ","\\"," "," "," ","/"," "," "," "," ","+"},
    	{"+", " ", " ", " "," "," ","|"," ","|"," "," "," "," "," ","+"},
    	{"+", " ", " ", " "," "," ","|"," ","|"," "," "," "," "," ","+"},
    	{"+", "-", "~", "-","~","-","|","V","|","-","~","-","~","-","+"}};

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
		numLMines = 0;
		initMines();
		GAME_OVER = false;
		toggle = true;
		animateI = animateJ = 0;

	}

	private void initMines() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				mines[i][j] = NO_MINE;
			}			
		}
	}

	public void restart(){
		if (GAME_OVER) {
			timer.stop();
			timer2.stop();
		}
		sprite = new Sprite();
		mines = new int[HEIGHT][WIDTH];
		initMines();
		numLMines = 0;
		shipR = shipC = 1;
		turns = 0;
		display = "";

		for (int i = 1; i <= HEIGHT; i++) {
			for (int j = 1; j <= HEIGHT; j++) {
				grid[i][j].setText("");
				grid[i][j].setEnabled(true);
			}
		}

		initBoard(grid, field);
		turnDisplay("Game Restarted");
		turnDisplay("BabyZap Game, Click a cell!");
		printTurn();
		GAME_OVER = false;
		animateI = animateJ = 0;
	}

	public void initBoard(Cell[][] gameBoard, JTextArea field) {
	//REQUIRES: An instance of the grid
	//REQUIRES: The JTextArea for the game, used for printing to the user.
	//MODIFIES: set's the grid and field instance variables.
	//EFFECTS: Displays the initial board graphics.

		grid = gameBoard;
		this.field = field;
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
		//User ran out of turns
		} else if (turns > TURN_LIMIT) {
			turnDisplay("You have run out of turns!");
			printTurn();
			endGame(false);
		//Ship dodged the mines
		} else {
			moveShip(dist);
			turns++;
			turnDisplay("Ship moved to "+shipR+","+shipC);
			turnDisplay("Current turn: " +turns);
			turnDisplay("Current Energy:"+sprite.getEnergy());
		}
    	
    	if (!GAME_OVER) AIMove();
    }

    private void AIMove() {

    	//Fire the l-Mine weapons
    	if (fireLMine()) {
			turnDisplay("Current Energy:"+sprite.getEnergy());
			turnDisplay("MAYDAY, MAYDAY, We're going down!");
			printTurn();
			endGame(false);
    	}

    	//We have landed a k-mine on the ship!
		if (shipHit()) {
			//We have destroyed the ship!
			if (sprite.shipDead()) {
				turnDisplay("Current Energy:"+sprite.getEnergy());
				turnDisplay("MAYDAY, MAYDAY, We're going down!");
				printTurn();
				endGame(false);
				return;
			//Ship survived the turn
			} else {
				//Put the ship back in it's place! (the origin)
				newR = newC = ORIGIN;
				moveShip(0);
				turns++;
				turnDisplay("Current Energy:"+sprite.getEnergy());
				printTurn();
			}
		} else {
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

    private Boolean fireLMine() {
    	
    	//return false if num == 0
    	if (numLMines == 0) {
    		return false;
    	} else {
	    	//look at each LMine if x & y are within 2 spots, ship got hit, but continue!
	    	for (int i = 0; i < HEIGHT; i++) {
	    		for (int j = 0; j < WIDTH; j++) {
	    			if (mines[i][j] == L_MINE) {

	    				if (((Math.abs((i+1)-shipR) == 2) && ((j+1) == shipC)) || ((Math.abs((j+1)-shipC) == 2) && ((i+1) == shipR))) {
	    					if (LMineHit(i,j,SEVERITY_TWO)) return true;

	    				} else if (((Math.abs((i+1)-shipR) == 1) && ((j+1) == shipC)) || ((Math.abs((j+1)-shipC) == 1) && ((i+1) == shipR))) {
							if (LMineHit(i,j,SEVERITY_ONE)) return true;
	    				}
	    			}
	    		}

	    	}    		
    	}
    	return sprite.shipDead();
    }

    private Boolean LMineHit(int i, int j, int sev) {
		turnDisplay("Ship's been hit by an l-mine!");
		mines[i][j] = K_MINE;
		numLMines--;
		grid[i+1][j+1].setText(sprite.getKMine());
		return sprite.shipLMineHit(sev);
    }

    private boolean shipHit() {
	//EFFECTS: The computer randomly generates a mine and place it on the board.
	//EFFECTS: Returns true or false indicating whether the ship was hit by a mine this turn.
		int x = (int) Math.floor(1.0 + Math.random() * HEIGHT);
		int y = (int) Math.floor(1.0 + Math.random() * HEIGHT);

    	if (handleMine(x,y)) {
    		turnDisplay("Ship's been hit by a k-mine!");
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
			turnDisplay("You Won!\n");
			endGame(true);
		} else if (LMinePresent(newR, newC)){
			sprite.useFuel(dist);
			turnDisplay("We've run into an L_MINE, going down!");
			printTurn();
			endGame(false);
		} else {
			grid[shipR][shipC].setText("");
    		shipR = newR;
			shipC = newC;
			grid[shipR][shipC].setText(sprite.getShip());
			mines[shipR-1][shipC-1] = NO_MINE;
			sprite.useFuel(dist);
			if (sprite.shipDead()) endGame(false);
		}
    }

    private Boolean LMinePresent(int x, int y) {

    	return(mines[x-1][y-1] == L_MINE);
    }

    public Boolean handleMine(int x, int y) {
    //REQUIRES: an int x, indicating x-co-ordinates for the mine, between 1-9.
    //REQUIRES: an int y, indicating y-co-ordinates for the mine, between 1-9.
    //EFFECTS: If the mine was not placed on either: the ship, the star-port, or the stargates,
    //The mine will be drawn on the board, and this function will return 0.
    //EFFECTS: Otherwise, this function will return true.

    	if (!(x == newR && y == newC)) {
			if(!(starGates(x,y)) && !(x == WIDTH && y == HEIGHT)) {
				if (mines[x-1][y-1] == NO_MINE) {
					grid[x][y].setText(sprite.getKMine());
					mines[x-1][y-1] = K_MINE;
				} else {
					grid[x][y].setText(sprite.getLMine());
					mines[x-1][y-1] = L_MINE;
					numLMines++;
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

    private void endGame(Boolean won){
    //REQUIRES: boolean variable indicating if the user won the game.
    //MODIFIES: All board-cells become disabled.
    //EFFECTS: The game ends with a display message based on whether or not the user won the game.

    	GAME_OVER = true;

    	//declare all cells false
		for (int i = 1; i <= HEIGHT; i++) {
			for (int j = 1; j <= WIDTH; j++) {
			    grid[i][j].setText("");
			    grid[i][j].setEnabled(false);
			}
		}

		if(won){
			Timer timer = new Timer(SPEED/2, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					shipAnimate();    
       			}
			});
			timer.setRepeats(true);
			timer.setInitialDelay(SPEED);
			timer2 = new Timer(REFRESH, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			timer2.setRepeats(true);
			timer2.setInitialDelay(PAUSE);

			timer.start();
			timer2.start();

		} else {

			drawAlien();

			Timer timer = new Timer(SPEED, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					openAlien();	    
       			}
			});
			timer.setRepeats(true);
			timer2 = new Timer(SPEED, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				  	closeAlien();
				}
			});
			timer2.setRepeats(true);
			timer2.setInitialDelay(SPEED/2);

			timer.start();
			timer2.start();
    	}
    }

    public void shipAnimate() {
/*
	//let's say I is width, j is height 
   	//have an array representing the flag
	//animateI= 0;
	//animateJ = 0;
		for (int j = 0; j <= WIDTH; j++) {
	        if (WIDTH / j == animateJ) {
	        	//reset the unused text
	        	for (int i = 0; i < animateI; i++) grid[i][j].setText("");
	        	for (int i = HEIGHT-animateI; i < HEIGHT; i++) grid[i][j].setText("");

	        	//redraw the flag
	        	for (int i = 0; i < ANIMATION_HEIGHT; i++){
	        		//Reprint the map starting at height = animateJ, for column animate I
	       			grid[i+animateI][j].setText(FLAG[i][j]);	
	        	}

	        }
	   }
	   animateI++;
	   animateJ++;
	   if (animateJ == 5) animateJ = 0;
	   if (animateI == 3) animateI =  0;
*/
	}

	private void drawAlien() {
		for (int i = 1; i < HEIGHT-2; i++) {
			for (int j = 0; j < WIDTH; j++) {
				grid[i][j].setText(ALIEN[HEIGHT-2-i][j]);
			}
		}
	}

    private void openAlien() {

    	grid[1][8].setText("V");
    	grid[3][8].setText(" ");
    	grid[7][7].setText(" ");
    	grid[7][9].setText(" ");
    }

    private void closeAlien() {

    	grid[1][8].setText("_");
    	grid[3][8].setText("0");
    	grid[7][7].setText("*");
    	grid[7][9].setText("*");
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