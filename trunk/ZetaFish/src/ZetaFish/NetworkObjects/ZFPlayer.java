package ZetaFish.NetworkObjects;

import java.io.Serializable;
import java.util.*;


import ZetaFish.*;

public class ZFPlayer implements Serializable
{
	private String PlayerName = "";
	private int score = 0;
	private int cardsInHand = 0;
	private Card[] hand = null;
	private List<Card[]> books = null;
	
	public ZFPlayer(String PlayerName, int score, int cardsInHand, Card[] hand, List<Card[]> books)
	{
		this.PlayerName = PlayerName;
		this.score = score;
		this.cardsInHand = cardsInHand;
		this.hand = hand;
		this.books = books;
	}
	
	public String getPlayerName()
	{
		return this.PlayerName;
	}
	
	public int getScore()
	{
		return this.score;		
	}
	
	public int getCardsInHand()
	{
		return this.cardsInHand;
	}
	
	public Card[] getHand()
	{
		return hand;
	}
	
	public List<Card[]> getBooks()
	{
		return books;
	}

}
