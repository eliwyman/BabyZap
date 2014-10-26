public class SpriteDriver {
//OVERVIEW: Test driver for the Sprite class    

	private static final int testComp = 2;

	SpriteDriver(){
	}
	
    public static void main(String[] args) {
	
    	int testCount = 0;
    	int errorCount = 0;
    	int numBuckets = 13;
    	int numBoolBuckets = 4;

    	//2-d array of integer test buckets, 
    	//[i][0] represents the ith actual value
		//[i][1] represents the ith expected value
    	int buckets[][] = new int[numBuckets][testComp];
    	Boolean boolBuckets[][] = new Boolean [numBoolBuckets][testComp];
		Sprite s = new Sprite(100,1000,30);

    	System.out.println("Begin Test Bench");



    	for(int i = 0; i < numBuckets; i++) {

    		testCount++;

    		if(buckets[i][0] != buckets[i][1]) {
    			System.out.println("Error in Test Case: "+(i+1));
    			System.out.println("Actual Value: "+buckets[i][0]);
    			System.out.println("Expected Value: "+buckets[i][1]);
    			errorCount++;
    		}
    	}

    	for(int i = 0; i < numBoolBuckets; i++) {

    		testCount++;

    		if(boolBuckets[i][0] != boolBuckets[i][1]) {
    			System.out.println("Error in Test Case: "+(i+1));
    			System.out.println("Actual Value: "+boolBuckets[i][0]);
    			System.out.println("Expected Value: "+boolBuckets[i][1]);
    			errorCount++;
    		}
    	}

    	System.out.println("End Test Bench\n");
    	System.out.println("Number of Test Cases: "+ testCount);
    	System.out.println("Number of Test Cases in Error: "+ errorCount+"\n");

	}
}