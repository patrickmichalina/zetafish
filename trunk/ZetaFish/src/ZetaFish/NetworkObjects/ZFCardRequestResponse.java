package ZetaFish.NetworkObjects;

import java.io.Serializable;

import ZetaFish.*;


public class ZFCardRequestResponse implements Serializable 
{
	public enum CardRequestResult
	{
		FROM_PLAYER,
		FROM_OCEAN,
		
		ERROR
	}
	
	private CardRequestResult result;
	private Card[] cards;
	private String msg;
	
	public ZFCardRequestResponse(CardRequestResult result, Card[] cards, String msg)
	{
		this.result = result;
		this.cards = cards;
		this.msg = msg;
	}
	
	public CardRequestResult getResult()
	{
		return this.result;		
	}
	
	public Card[] getCards()
	{
		return this.cards;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

}
