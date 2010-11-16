package ZetaFish.GUI;

import javax.swing.ImageIcon;

/**
 *  Summary: Class BackgroundPanel represents a basic panel in the ZetaFish game.
 *           It is a simple way to instantiate panes with the background image
 *           declared as a formal parameter.
 *  Imports: ImageIcone
 *  Exports: BackgroundPanel Object.
 *  {author= Zeta Dragons, version=1.0, since=2010}
 *
 *  @author Patrick Michalina
 */
public class BackgroundPanel extends Panel
{    
	private static final long serialVersionUID = 1L;

	BackgroundPanel(ImageIcon image)
    {
        super(image);
    }
}