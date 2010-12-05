package ZetaFish.GUI;

import java.util.Arrays;
import ZetaFish.NetworkObjects.ZFCard;

/**
 * Represents a single (face up) hand of cards held by local player. 
 * Design 5.2 v1.6
 */
public class PlayerHandPane extends PlayerPane
{	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param deck Deck of playing cards.
	 */
	public PlayerHandPane(DeckOfCards deck)
	{
		super(deck);
		
		super.cardSpacing = 20;
		super.cardOffsetX = 20;
		super.cardOffsetY = 18;
	}		
		
	/**
	 * Add cards to visible hand.
	 * @param cards Cards to add.
	 * @param score Player's score.
	 */
	public void addCards(ZFCard[] cards, int score)
	{
		this.setTitle("Your Hand - " + score);
		// sort cards low to high
		//ArrayList<ZFCard> list = new ArrayList<ZFCard>(Arrays.asList(cards));
		Arrays.sort(cards);
		super.addCards(cards);
	}
	
	/**
	 * Remove all the cards from the player's hand.
	 */
	public void reset()
	{
		this.removeAll();
	}
}
