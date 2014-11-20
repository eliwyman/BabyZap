public class SpriteDriver {
//OVERVIEW: Test driver for the Sprite class    

	private static final int testComp = 2;

	SpriteDriver(){
	}
	
    public static void main(String[] args) {
	
    	int testCount = 0;
    	int errorCount = 0;
    	int numBoolBuckets = 3;
    	int numSBuckets = 10;

    	//2-d array of integer test buckets, 
    	//[i][0] represents the ith actual value
		//[i][1] represents the ith expected value
    	Boolean boolBuckets[][] = new Boolean [numBoolBuckets][testComp];
    	String sBuckets[][] = new String [numSBuckets][testComp];
		Sprite s = new Sprite();

    	System.out.println("Begin Test Bench\n");

        //Test Sprite character functions
        sBuckets[0][0] = s.getShip();
        sBuckets[0][1] = "/\\";
        sBuckets[1][0] = s.getShip();
        sBuckets[1][1] = "/^\\";
        sBuckets[2][0] = s.getKMine();
        sBuckets[2][1] = "+";
        sBuckets[3][0] = s.getKMine();
        sBuckets[3][1] = "-";
        sBuckets[4][0] = s.getGate();
        sBuckets[4][1] = "><";
        sBuckets[5][0] = s.getGate();
        sBuckets[5][1] = "<>";
        sBuckets[6][0] = s.getPort();
        sBuckets[6][1] = "0";
        sBuckets[7][0] = s.getPort();
        sBuckets[7][1] = "O";
        sBuckets[8][0] = s.getLMine();
        sBuckets[8][1] = "#";
        sBuckets[9][0] = s.getLMine();
        sBuckets[9][1] = "+";
        //End test of Sprite character functions

        //Test function: boolean enoughFuel(int dist)
        boolBuckets[0][0] = s.enoughFuel(0);
        boolBuckets[0][1] = true;
        boolBuckets[1][0] = s.enoughFuel(-5);
        boolBuckets[1][1] = true;
        //End test of function: boolean enoughFuel(int dist)

        //Test function: boolean s.shipDead()
        //Reset class instances
        s = new Sprite();
        Sprite s2 = new Sprite();
        Sprite s3 = new Sprite();

        boolBuckets[2][0] = s.shipDead();
        boolBuckets[2][1] = false;
        //End test of function: boolean s.shipDead()


    	for(int i = 0; i < numSBuckets; i+=2) {

    		testCount++;

    		if(sBuckets[i][0] != sBuckets[i][1]) {
    			System.out.println("Error in String Test Case: "+(i+1));
    			System.out.println("Actual Value: "+sBuckets[i][0]);
    			System.out.println("Expected Value: "+sBuckets[i][1]+"\n");
    			errorCount++;
    		}
    	}

        for(int i = 1; i < numSBuckets; i+=2) {

            testCount++;

            if(sBuckets[i][0] == sBuckets[i][1]) {
                System.out.println("Error in String Test Case: "+(i+1));
                System.out.println("Actual Value: "+sBuckets[i][0]);
                System.out.println("Not expected to match");
                System.out.println("Expected Value: "+sBuckets[i][1]+"\n");
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