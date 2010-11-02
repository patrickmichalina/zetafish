package ZetaFish.GUI;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Patrick
 */
public class Card extends JLabel {
    private ImageIcon img;
    private ImageIcon imgBack;
    private JLabel    container;
    private boolean   shown = false;

    Card() {
        //this.container.setPreferredSize(new Dimension(img.getIconWidth(),img.getIconHeight()));
    }

    Card(ImageIcon image) {
       super();
       InternalConstruct(image, false);
    }
    
    Card(ImageIcon image, boolean show)
    {
    	  super();
    	  InternalConstruct(image, show);
    }
    
    private void InternalConstruct(ImageIcon image, boolean show)
    {
    	this.img     = image;
        //this.imgBack = new ImageIcon(getClass().getResource("/Resources/CardImages/back-blue-150-1.png"    ));
        this.imgBack = new ImageIcon(getClass().getResource("/Resources/CardImages/back-blue.png"    ));
        
        this.shown = show;
        
        this.setIcon(this.getImage());
    }

    public ImageIcon getImage() {
        if(shown) {
            return img;
        }
        else
            return imgBack;
    }

    public void setShown(boolean val) {
        shown = val;
        this.setIcon(this.getImage());
    }
}