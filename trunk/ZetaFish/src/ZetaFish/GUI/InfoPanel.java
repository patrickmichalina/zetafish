package ZetaFish.GUI;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author Patrick
 */
public class InfoPanel extends Panel {

    private JLabel poolTxt   = new JLabel("Pool: ");
    private JLabel bookTxt   = new JLabel("Book: ");
    private JLabel winsTxt   = new JLabel("Wins: ");
    private JLabel lossTxt   = new JLabel("Losses: ");
    private JLabel statusTxt = new JLabel("Network Status: ");



    /**
     * 
     */
    public InfoPanel() {
        super(600,30);

        this.add(poolTxt);
        this.add(bookTxt);
        this.add(winsTxt);
        this.add(lossTxt);
        this.add(statusTxt);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

    }
}