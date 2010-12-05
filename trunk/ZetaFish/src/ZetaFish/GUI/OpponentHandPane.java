package ZetaFish.GUI;

/**
 * Represents a single (face down) hand of cards held by an opponent. 
 * Design 5.2 v1.6
 */
public class OpponentHandPane extends PlayerPane
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param deck Deck of playing cards
	 * 
	 * Design 7.1.10 v1.5
	 */
	public OpponentHandPane(DeckOfCards deck)
	{
		super(deck);
		
		super.cardSpacing = 6;
		super.cardOffsetX = 15;
		super.cardOffsetY = 20;
		
		super.cardsVisible = false;
	}

}
