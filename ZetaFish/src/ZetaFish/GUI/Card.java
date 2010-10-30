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
        this.container.setPreferredSize(new Dimension(img.getIconWidth(),img.getIconHeight()));
    }

    Card(ImageIcon image) {
       super();
       this.img     = image;
       this.imgBack = new ImageIcon(getClass().getResource("/Resources/CardImages/back-blue-150-1.png"    ));
       //this.imgBack = new ImageIcon(getClass().getResource("/Resources/CardImages/back-blue.png"    ));
    }

    public ImageIcon getImage() {
        if(shown) {
            return img;
        }
        else
            return imgBack;
    }
}