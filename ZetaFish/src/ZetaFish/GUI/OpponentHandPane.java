package ZetaFish.GUI;

public class OpponentHandPane extends PlayerPane
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param deck
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
