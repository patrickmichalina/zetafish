package ZetaFish.NetworkObjects;

import java.io.Serializable;

public class ZFPlayer implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String PlayerName = "";
	private int PlayerNumber = -1;
	private int score = 0;
	private int cardsInHand = 0;
	private ZFCard[] hand = null;
	private ZFCard[][] books = null;
	
	public ZFPlayer(String PlayerName, int PlayerNumber, int score, int cardsInHand, ZFCard[] hand, ZFCard[][] books)
	{
		this.PlayerName = PlayerName;
		this.PlayerNumber = PlayerNumber;
		this.score = score;
		this.cardsInHand = cardsInHand;
		this.hand = hand;
		this.books = books;
	}
	
	public String getPlayerName()
	{
		return this.PlayerName;
	}
	
	public int getPlayerNumber()
	{
		return this.PlayerNumber;
	}
	
	public int getScore()
	{
		return this.score;		
	}
	
	public int getCardsInHand()
	{
		return this.cardsInHand;
	}
	
	public ZFCard[] getHand()
	{
		return hand;
	}
	
	public ZFCard[][] getBooks()
	{
		return books;
	}
}