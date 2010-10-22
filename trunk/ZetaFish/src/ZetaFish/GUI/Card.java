package ZetaFish.GUI;

import javax.swing.ImageIcon;

/**
 *
 * @author Patrick
 */
public class Card extends Panel {
    private int pointValue;


    Card() {
        super();
    }

    Card(ImageIcon image, int value) {
        super(image);
        this.pointValue = value;
    }
}