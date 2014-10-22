public class Logic
{

	private final int HEIGHT;
	private final int WIDTH;
	private final int MINES = 3;

	private Sprite s;
	private Cell[][] grid;
	//ship co-ordinates
	private int shipR, shipC = 1;
	//ship destination
	private int newR, newC;
	

	Logic(int h, int w, long e, long max, int dmg){
		
		HEIGHT = h;
		WIDTH = w;
		s = new Sprite(e, max, dmg);

	}

	public void initBoard(Cell[][] g) {
		grid = g;
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
		System.out.println("You clicked on ("+row+","+col+")");
		newC = col;
		newR = row;
		int dist = distance(newR, newC);
		if (!s.enoughFuel(dist)) return;
		if (surviveTurn()) {
			moveShip(dist);
		} else {
			endGame(false);
		}
		System.out.println("Current Energy:"+s.getEnergy());
    }

    private int distance(int row, int col) {
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
    	}
    	return(!s.shipDead(hits));
    }

    private void moveShip(int dist) {
		if(newR == shipR && newC == shipC){
			s.shipHeal();
		} else if (newR == 9 && newC == 9){
			endGame(true);
		} else {
			grid[shipR][shipC].setText("");
    		shipR = newR;
			shipC = newC;
			grid[shipR][shipC].setText(s.getShip());
			s.useFuel(dist);	
		}
    	
    }

    private int handleMine(int x, int y) {
    	if (!(x == newR && y == newC)) {
    		grid[x][y].setText(s.getMine());
    		return 0;
    	}
    	return 1;
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
}