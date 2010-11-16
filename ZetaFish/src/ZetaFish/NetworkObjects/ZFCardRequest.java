package ZetaFish.NetworkObjects;

import java.io.Serializable;


public class ZFCardRequest implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private int Player;
	private int Opponent;
	private int CardValue;
	
	public ZFCardRequest(int PlayerNum, int OpponentNum, int CardValue)
	{
		this.Player = PlayerNum;
		this.Opponent = OpponentNum;
		this.CardValue = CardValue;
	}
	
	public int getPlayerNumber()
	{
		return this.Player;
	}
	
	public int getOpponentNumber()
	{
		return this.Opponent;
	}
	
	public int getCardValue()
	{
		return this.CardValue;
	}

}
