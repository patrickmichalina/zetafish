package ZetaFish.GUI;

import java.awt.Dimension;
import javax.swing.ImageIcon;

/**
 *
 * @author Patrick
 */
public class Card extends Panel
{
    private int pointValue;

    Card()
    {
        super();
        this.setPreferredSize(new Dimension(45,65));

    }

    Card(ImageIcon image, int value)
    {
        super(image);
        this.pointValue = value;
    }
}