package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 * ZFPlayer is used by other Network Object to represent a given player.  
 * 
 * Design 4.6.8 v1.5
 */
public class ZFPlayer implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String PlayerName = "";
	private int PlayerNumber = -1;
	private int score = 0;
	private int cardsInHand = 0;
	private ZFCard[] hand = null;
	private ZFCard[][] books = null;
	
	/**
	 * Constructor
	 * @param PlayerName Name to use for this player.
	 * @param PlayerNumber Player number.
	 * @param score Current score.
	 * @param cardsInHand Number of cards in the player's hand.
	 * @param hand Cards in the player's hand.
	 * @param books Owned books.
	 */
	public ZFPlayer(String PlayerName, int PlayerNumber, int score, int cardsInHand, ZFCard[] hand, ZFCard[][] books)
	{
		this.PlayerName = PlayerName;
		this.PlayerNumber = PlayerNumber;
		this.score = score;
		this.cardsInHand = cardsInHand;
		this.hand = hand;
		this.books = books;
	}
	
	/**
	 * Player's name.
	 * @return String
	 */
	public String getPlayerName()
	{
		return this.PlayerName;
	}
	
	/**
	 * Player's number.
	 * @return int
	 */
	public int getPlayerNumber()
	{
		return this.PlayerNumber;
	}
	
	/**
	 * Player's score.
	 * @return int
	 */
	public int getScore()
	{
		return this.score;		
	}
	
	/**
	 * Number of cards in the player's hand.
	 * @return int
	 */
	public int getCardsInHand()
	{
		return this.cardsInHand;
	}
	
	/**
	 * Player's hand.
	 * @return ZFCard[]
	 */
	public ZFCard[] getHand()
	{
		return hand;
	}
	
	/**
	 * Player's books
	 * @return ZFCard[][]
	 */
	public ZFCard[][] getBooks()
	{
		return books;
	}
}