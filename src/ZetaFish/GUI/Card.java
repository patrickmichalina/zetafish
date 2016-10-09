package ZetaFish.GUI;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * An individual GUI card.
 * @author Patrick
 */
public class Card extends JLabel
{
	private static final long serialVersionUID = 1L;
	
    private ImageIcon img;
    private ImageIcon imgBack;    
    private boolean   shown = false;

    /**
     * Constructor
     * @param image Image to use on the face.
     */
    Card(ImageIcon image) {
       super();
       InternalConstruct(image, false);
    }
    
    /**
     * Constructor
     * @param image Image to use on the face.
     * @param show Show the card face up?
     */
    Card(ImageIcon image, boolean show)
    {
    	  super();
    	  InternalConstruct(image, show);
    }
    
    /**
     * Internal constructor
     * @param image Image to use on the face.
     * @param show Show the card face up?
     */
    private void InternalConstruct(ImageIcon image, boolean show)
    {
    	this.img     = image;
        this.imgBack = new ImageIcon(getClass().getResource("/Resources/CardImages/back-blue.png"));
        
        this.shown = show;
        
        this.setIcon(this.getImage());
    }

    /**
     * Current image to display for the card.  (face up or down)
     * @return ImageIcon
     */
    public ImageIcon getImage() {
        if(shown) {
            return img;
        }
        else
            return imgBack;
    }

    /**
     * Set the card to face up or down.
     * @param val True if face up, false otherwise.
     */
    public void setShown(boolean val) {
        shown = val;
        this.setIcon(this.getImage());
    }
}