import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

public class BabyQ extends javax.swing.JFrame
/* Purpose: Implements a top level window to contain the BabyQ game.*/
{
    BabyQ() {
	initUI();
    }

    private void initUI() {
        
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
		field.setPreferredSize(new Dimension(200, 100));        
        pane.add(field, BorderLayout.PAGE_START);
        pane.add(new Game(field), BorderLayout.CENTER);
        //add(new Game());

    	
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