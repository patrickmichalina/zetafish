package ZetaFish.NetworkObjects;

import java.io.Serializable;

public class ZFStatus implements Serializable
{
	public enum StatusType
	{
		NONE,
		GAME_STARTED,
		GAME_OVER,
		TURN_CHANGE,	
		
		ASSIGN_PLAYER_NUMBER
	}
	
	private StatusType status = StatusType.NONE;
	private int currentPlayer = 0;	
	private ZFPlayer[] players = null;
	private boolean isGameRunning;
	private boolean isGameOver;
	
	public ZFStatus(StatusType sts, int currentPlayer, ZFPlayer[] players, boolean isGameRunning, boolean isGameOver)
	{
		this.status = sts;
		this.currentPlayer = currentPlayer;
		this.players = players;
		this.isGameRunning = isGameRunning;
		this.isGameOver = isGameOver;
	}
	
	public StatusType getStatus()
	{
		return this.status;
	}
	
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public ZFPlayer[] getPlayers()
	{
		return players;
	}
		
	public boolean getIsGameRunning()
	{
		return this.isGameRunning;
	}
	
	public boolean getIsGameOver()
	{
		return this.isGameOver;
	}
}
