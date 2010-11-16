package ZetaFish.GUI;

import java.util.Arrays;
import ZetaFish.NetworkObjects.ZFCard;

public class PlayerHandPane extends PlayerPane
{	
	private static final long serialVersionUID = 1L;
	
	public PlayerHandPane(DeckOfCards deck)
	{
		super(deck);
		
		super.cardSpacing = 20;
		super.cardOffsetX = 20;
		super.cardOffsetY = 18;
	}		
		
	public void addCards(ZFCard[] cards, int score)
	{
		this.setTitle("Your Hand - " + score);
		// sort cards low to high
		//ArrayList<ZFCard> list = new ArrayList<ZFCard>(Arrays.asList(cards));
		Arrays.sort(cards);
		super.addCards(cards);
	}
	
	public void reset()
	{
		this.removeAll();
	}
}
