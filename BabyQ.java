import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

public class BabyQ extends javax.swing.JFrame
/* OVERVIEW: Implements a Container that contains a window to contain the BabyQ game and a window to contain output for the game.*/
{

    private final int PREFERRED_HEIGHT = 100;
    private final int PREFERRED_WIDTH = 200;
    private final int SIZE = 1000;

    BabyQ() {
	initUI();
    }

    private void initUI() {
    //EFFECTS: Creates a Container to hold a JFrame and JTextArea window to house game contents,
    //renders it to the center of the screen. 

        setTitle("Baby Q");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        //Set up the BorderLayout
        Container pane = getContentPane();

        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        //Add dashboard
        JTextArea field = new JTextArea(); 
        field.setEnabled(false);
        field.setText("BabyQ Game, Click a cell!");
		field.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));        
        pane.add(field, BorderLayout.PAGE_START);
        pane.add(new Game(field), BorderLayout.CENTER);
        //add(new Game());

    	
		setSize(SIZE, SIZE);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
    //EFFECTS: Creates an instance of BabyQ and sets it as visible.
 
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {

		    BabyQ bq = new BabyQ();
		    bq.setVisible(true);
		}
	    });
    }
}