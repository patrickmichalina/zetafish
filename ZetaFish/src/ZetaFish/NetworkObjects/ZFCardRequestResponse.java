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
	private ZFCard[] cards;
	private String msg;
	
	public ZFCardRequestResponse(CardRequestResult result, ZFCard[] cards, String msg)
	{
		this.result = result;
		this.cards = cards;
		this.msg = msg;
	}
	
	public CardRequestResult getResult()
	{
		return this.result;		
	}
	
	public ZFCard[] getCards()
	{
		return this.cards;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

}
