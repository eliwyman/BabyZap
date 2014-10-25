import javax.swing.JTextArea;

public class Logic
{

	private final int HEIGHT;
	private final int WIDTH;
	private final int MINES = 3;

	private Sprite s;
	private Cell[][] grid;
	private JTextArea field;
	//ship co-ordinates
	private int shipR, shipC = 1;
	//ship destination
	private int newR, newC;
	//user turns
	private int turns = 0;
	//turn display
	private String display;

	Logic(int h, int w, long e, long max, int dmg){
		
		HEIGHT = h;
		WIDTH = w;
		s = new Sprite(e, max, dmg);
		display = "";

	}

	public void initBoard(Cell[][] g, JTextArea f) {
		grid = g;
		field = f;
		//Launch the shuttle
		grid[1][1].setText(s.getShip());
		shipR = shipC = 1;
		//Generate the star gates
		String star = s.getGate();
		grid[HEIGHT-1][WIDTH].setText(star);
		grid[HEIGHT-1][WIDTH-1].setText(star);
		grid[HEIGHT][WIDTH-1].setText(star);
		//Generate the wormhole
		grid[HEIGHT][WIDTH].setText(s.getPort());
	}

	public void userMove(int row, int col) {
		newC = col;
		newR = row;
		int dist = distance(newR, newC);
		if (!s.enoughFuel(dist) || starGates(newC,newR)) {
			turnDisplay("Invalid Move"); 
			turnDisplay("Current Energy:"+s.getEnergy());
			printTurn();
		} else if (turns < 100 && surviveTurn()) {
			moveShip(dist);
			turns++;
			turnDisplay("Current Energy:"+s.getEnergy());
			printTurn();
		} else {
			turnDisplay("Current Energy:"+s.getEnergy());
			turnDisplay("MAYDAY, MAYDAY, We're going down!");
			printTurn();
			endGame(false);
		}
		return;
    }

    public int distance(int row, int col) {
    	return (Math.abs(row - shipR) + Math.abs(col - shipC));
    }

    private boolean surviveTurn() {

    	int hits = 0;
    	//pick 1 to 'mines' number of mines
    	int num = (int) Math.floor(1.0 + Math.random() * MINES);
    	//gen 'num' mines
    	for (int i = 0; i < num; i ++){
    		int x = (int) Math.floor(1.0 + Math.random() * HEIGHT);
    		int y = (int) Math.floor(1.0 + Math.random() * HEIGHT);
    		hits += handleMine(x,y);
    		if (hits > 0) turnDisplay("Ship's been hit!");
    	}
    	return(!s.shipDead(hits));
    }

    private void moveShip(int dist) {
		if(newR == shipR && newC == shipC){
			s.shipHeal();
		} else if (newR == 9 && newC == 9){
			s.useFuel(dist);
			endGame(true);
		} else {
			grid[shipR][shipC].setText("");
    		shipR = newR;
			shipC = newC;
			grid[shipR][shipC].setText(s.getShip());
			s.useFuel(dist);	
		}
    	
    }

    public int handleMine(int x, int y) {
    	if (!(x == newR && y == newC)) {
			if(!(starGates(x,y)) && !(x == 9 && y == 9)) {
				grid[x][y].setText(s.getMine());
    			return 0;
    		}
    	}
    	return 1;
    }

    public boolean starGates(int x, int y) {
    	return ((x == 8 && y == 8) || (x == 8 && y == 9) || (x == 9 && y == 8));
    }

    private void endGame(boolean won){
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
    	display = display + s + "\n";
    }

    private void printTurn() {
    	field.setText(display);
    	display = "";
    }

    public void setCoords(int row, int col){
    	shipR = row;
    	shipC = col;
    }

    public void setNewCoords(int row, int col){
    	newR = row;
    	newC = col;
    }
}