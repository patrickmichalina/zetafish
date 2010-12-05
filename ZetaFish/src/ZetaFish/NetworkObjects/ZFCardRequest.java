package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 *  Represents a message to the server to ask an opponent for a card. 
 *
 *	Design 4.6.2 v1.5
 */
public class ZFCardRequest implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private int Player;
	private int Opponent;
	private int CardValue;
	
	/**
	 * Constructor
	 * @param PlayerNum Player number asking
	 * @param OpponentNum Opponent to ask
	 * @param CardValue Card value to ask for
	 */
	public ZFCardRequest(int PlayerNum, int OpponentNum, int CardValue)
	{
		this.Player = PlayerNum;
		this.Opponent = OpponentNum;
		this.CardValue = CardValue;
	}
	
	/**
	 * Player number asking
	 * @return int
	 */
	public int getPlayerNumber()
	{
		return this.Player;
	}
	
	/**
	 * Opponent being asked
	 * @return int
	 */
	public int getOpponentNumber()
	{
		return this.Opponent;
	}
	
	/**
	 * Card value being asked for
	 * @return
	 */
	public int getCardValue()
	{
		return this.CardValue;
	}
}
