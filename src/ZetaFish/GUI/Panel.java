package ZetaFish.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Base class for GameWindow
 */
public class Panel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
    private ImageIcon background = null;

    /**
     * Default Constructor
     */
    public Panel() {
        super();
        this.isDoubleBuffered();
        this.isLightweight();
    }
    
    /**
     * Constructor
     * @param image Background image.
     */
    public Panel(ImageIcon image) {
        super();
        background = image;
        setPreferredSize(new Dimension(background.getIconWidth() + getInsets().left + getInsets().right, background.getIconHeight() + getInsets().top + getInsets().bottom));
        this.setOpaque(false);
    }

    /**
     * Constructor
     * @param length Length of panel
     * @param height Height of panel
     */
    public Panel(int length, int height) {
        super();
        Dimension dim = new Dimension(length, height);
        setPreferredSize(dim);
    }

    /**
     * Scale and paint the background.
     * 
     * Additionally, see description in JPanel
     */
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