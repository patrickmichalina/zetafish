package ZetaFish.GUI;

/**
 *  GUI Pane containing all the played books. 
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
