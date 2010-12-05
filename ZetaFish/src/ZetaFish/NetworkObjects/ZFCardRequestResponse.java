package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 * The ZFCardRequestResponse is the “partner” to the ZFCardRequest.  
 * This object returns the results of the card requests.
 *   
 * Design 4.6.3 v1.5
 */
public class ZFCardRequestResponse implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Determines if cards came from the player or were drawn from the ocean.
	 */
	public enum CardRequestResult
	{
		FROM_PLAYER,
		FROM_OCEAN,
		
		ERROR
	}
	
	private CardRequestResult result;
	private ZFCard[] cards;
	private String msg;
	
	/**
	 * Constructor
	 * @param result CardRequestResult.
	 * @param cards Cards returned from request.
	 * @param msg Associated message.
	 */
	public ZFCardRequestResponse(CardRequestResult result, ZFCard[] cards, String msg)
	{
		this.result = result;
		this.cards = cards;
		this.msg = msg;
	}
	
	/**
	 * From player or ocean?
	 * @return CardRequestResult
	 */
	public CardRequestResult getResult()
	{
		return this.result;		
	}
	
	/**
	 * Cards returned
	 * @return ZFCard[]
	 */
	public ZFCard[] getCards()
	{
		return this.cards;
	}
	
	/**
	 * Associated message. (if any)
	 * @return String
	 */
	public String getMessage()
	{
		return this.msg;
	}

}
