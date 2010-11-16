package ZetaFish.GUI;

public class OpponentHandPane extends PlayerPane
{
	private static final long serialVersionUID = 1L;
	
	public OpponentHandPane(DeckOfCards deck)
	{
		super(deck);
		
		super.cardSpacing = 6;
		super.cardOffsetX = 15;
		super.cardOffsetY = 20;
		
		super.cardsVisible = false;
	}

}
