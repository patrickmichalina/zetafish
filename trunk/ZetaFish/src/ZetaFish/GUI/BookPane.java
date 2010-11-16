package ZetaFish.GUI;

public class BookPane extends PlayerPane
{
	private static final long serialVersionUID = 1L;
	
	public BookPane(DeckOfCards deck)
	{
		super(deck);
		
		super.cardSpacing = 19;
		super.cardOffsetX = 0;
		super.cardOffsetY = 0;
	}		
}
