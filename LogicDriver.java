//test class for the Logic class

public class LogicDriver {
    
	//private int testCount;
	//private int errorCount;
	//private int numBuckets;
	private static final int testComp = 2;


	LogicDriver(){

	//	testCount = 0;
	//	errorCount = 0;

	}
	

    public static void main(String[] args) {
	
    	int testCount = 0;
    	int errorCount = 0;
    	int numBuckets = 7;
    	int numBoolBuckets = 4;

    	//2-d array of integer test buckets, 
    	//[i][0] represents the ith actual value
		//[i][1] represents the ith expected value
    	double buckets[][] = new double[numBuckets][testComp];
    	Boolean boolBuckets[][] = new Boolean [numBoolBuckets][testComp];
    	LogicDriver ld = new LogicDriver();
		Logic l = new Logic(9,9);

    	System.out.println("Begin Test Bench\n");

    	//Test function: double distance(int row, int col)
    	l.setCoords(0,0);

    	buckets[0][0] = l.distance(1,2);
    	buckets[0][1] = 2.23606797749979;
    	buckets[1][0] = l.distance(-1,2);
    	buckets[1][1] = 2.23606797749979;
    	buckets[2][0] = l.distance(-1,-2);
    	buckets[2][1] = 2.23606797749979;
    	buckets[3][0] = l.distance(1,-2);
    	buckets[3][1] = 2.23606797749979;
    	buckets[4][0] = l.distance(0,0);
    	buckets[4][1] = 0;
    	buckets[5][0] = l.distance(2,4);
    	buckets[5][1] = 4.47213595499958;
    	buckets[6][0] = l.distance(2,2);
    	buckets[6][1] = 2.8284271247461903;
    	//End test of function: int distance(int row, int col)

    	//Test function: boolean starGates(int x, int y)
    	boolBuckets[0][0] = l.starGates(0,0);
    	boolBuckets[0][1] = false;
    	boolBuckets[1][0] = l.starGates(8,9);
    	boolBuckets[1][1] = true;
    	boolBuckets[2][0] = l.starGates(9,8);
    	boolBuckets[2][1] = true;
    	boolBuckets[3][0] = l.starGates(8,8);
    	boolBuckets[3][1] = true;
    	//End test of function: boolean starGates(int x, int y)

    	for(int i = 0; i < numBuckets; i++) {

    		testCount++;

    		if(buckets[i][0] != buckets[i][1]) {
    			System.out.println("Error in Test Case: "+(i+1));
    			System.out.println("Actual Value: "+buckets[i][0]);
    			System.out.println("Expected Value: "+buckets[i][1]+"\n");
    			errorCount++;
    		}
    	}

    	for(int i = 0; i < numBoolBuckets; i++) {

    		testCount++;

    		if(boolBuckets[i][0] != boolBuckets[i][1]) {
    			System.out.println("Error in Boolean Test Case: "+(i+1));
    			System.out.println("Actual Value: "+boolBuckets[i][0]);
    			System.out.println("Expected Value: "+boolBuckets[i][1]+"\n");
    			errorCount++;
    		}
    	}

    	System.out.println("End Test Bench\n");
    	System.out.println("Number of Test Cases: "+ testCount);
    	System.out.println("Number of Test Cases in Error: "+ errorCount+"\n");

	}
}