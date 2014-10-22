public class BabyQ extends javax.swing.JFrame
/* Purpose: Implements a top level window to contain the BabyQ game.*/
{
    BabyQ() {
	initUI();
    }

    private void initUI() {
        
        setTitle("Baby Q");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        add(new Game());

		setSize(1000,1000);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {

		    BabyQ bq = new BabyQ();
		    bq.setVisible(true);
		}
	    });
    }
}