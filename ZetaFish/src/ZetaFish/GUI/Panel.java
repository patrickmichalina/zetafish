package ZetaFish.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Patrick
 */

public class Panel extends JPanel {
    private ImageIcon background = null;

    /**
     * 
     */
    public Panel() {
        super();
        this.isDoubleBuffered();
        this.isLightweight();
    }
    
    /**
     *
     * @param image
     */
    public Panel(ImageIcon image) {
        super();
        background = image;
        setPreferredSize(new Dimension(background.getIconWidth() + getInsets().left + getInsets().right, background.getIconHeight() + getInsets().top + getInsets().bottom));
        this.setOpaque(false);
    }

    /**
     *
     * @param length
     * @param height
     */
    public Panel(int length, int height) {
        super();
        Dimension dim = new Dimension(length, height);
        setPreferredSize(dim);
    }

    @Override
    protected void paintComponent(Graphics g) {

        //  Scale image to size of component
        if(background != null) {
            Dimension dim = getSize();
            g.drawImage(background.getImage(), 0, 0, dim.width, dim.height, null);
            super.paintComponent(g);
        }
    }
}