package ZetaFish.GUI;

import java.awt.Dimension;
import javax.swing.ImageIcon;

/**
 *
 * @author Patrick
 */
public class Card extends Panel {
    Card() {
        super();
        this.setPreferredSize(new Dimension(45,65));
    }

    Card(ImageIcon image) {
        super(image);
    }
}