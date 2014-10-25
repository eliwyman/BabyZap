//test class for the Logic class

public class LogicDriver {
    
	//private int testCount;
	//private int errorCount;
	//private int numBuckets;


	LogicDriver(){

	//	testCount = 0;
	//	errorCount = 0;

	}
	

    public static void main(String[] args) {
	
    	int testCount = 0;
    	int errorCount = 0;
    	int numBuckets = 13;

    	//2-d array of integer test buckets, 
    	//[i][0] represents the ith actual value
		//[i][1] represents the ith expected value
    	int buckets[][] = new int[numBuckets][2];

    	LogicDriver ld = new LogicDriver();
		Logic l = new Logic(9,9,100,1000,30);

    	System.out.println("Begin Test Bench");

    	//Test function: int distance(int row, int col)
    	l.setCoords(0,0);
System.out.println("check");
    	buckets[0][0] = l.distance(0,0);
    	buckets[0][1] = 0;
    	buckets[1][0] = l.distance(-5,0);
    	buckets[1][1] = 5;
    	buckets[2][0] = l.distance(-5,-1);
    	buckets[2][1] = 6;
    	buckets[3][0] = l.distance(-3,2);
    	buckets[3][1] = 5;
    	buckets[4][0] = l.distance(2,0);
    	buckets[4][1] = 2;
    	buckets[5][0] = l.distance(3,-5);
    	buckets[5][1] = 8;
    	buckets[6][0] = l.distance(1,4);
    	buckets[6][1] = 5;
    	System.out.println("check");
    	//End test of function: int distance(int row, int col)

    	//Test function: int handleMine(int x, int y)

    	System.out.println("check4");
    	l.setNewCoords(0,0);
    	System.out.println("check5");
    	buckets[7][0] = l.handleMine(0,0);
    	System.out.println("check6");
    	buckets[7][1] = 1;
    	buckets[8][0] = l.handleMine(9,9);
    	buckets[8][1] = 1;
    	buckets[9][0] = l.handleMine(8,9);
    	buckets[9][1] = 1;
    	buckets[10][0] = l.handleMine(9,8);
    	buckets[10][1] = 1;
    	buckets[11][0] = l.handleMine(8,8);
    	buckets[11][1] = 1;
    	buckets[12][0] = 4; //l.handleMine(5,5);
    	buckets[12][1] = 0;
    	System.out.println("check8");
    	//End test of function: int handleMine(int x, int y)

/*
	int handleMine(int x, int y) {
    	if (!(x == newR && y == newC)) {
    		if(!(starGates(x,y)) && !(x == 9 && y == 9)) grid[x][y].setText(s.getMine());
    		return 0;
    	} 
    		return 1;
    	
    }
*/
    	//Test function: boolean starGates(int x, int y)

    	//End test of function: boolean starGates(int x, int y)

/*
	private boolean starGates(int x, int y) {
	    	return ((x == 8 && y == 8) || (x == 8 && y == 9) || (x == 9 && y == 8));

*/
    	for(int i = 0; i < numBuckets; i++) {

    		testCount++;

    		if(buckets[i][0] != buckets[i][1]) {
    			System.out.println("Error in Test Case: "+(i+1));
    			System.out.println("Actual Value: "+buckets[i][0]);
    			System.out.println("Expected Value: "+buckets[i][1]);
    			errorCount++;
    		}
    	}

    	System.out.println("End Test Bench\n");
    	System.out.println("Number of Test Cases: "+ testCount);
    	System.out.println("Number of Test Cases in Error: "+ errorCount+"\n");

	}
}