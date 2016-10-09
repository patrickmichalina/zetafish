package ZetaFish.GUI;

/**
 *  GUI Pane containing all the played books. 
 *  
 *  Design 5.2 v1.6
 */
public class BookPane extends PlayerPane
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param deck Instance of DeckOfCards
	 */
	public BookPane(DeckOfCards deck)
	{
		super(deck);
		
		super.cardSpacing = 19;
		super.cardOffsetX = 0;
		super.cardOffsetY = 0;
	}		
}
