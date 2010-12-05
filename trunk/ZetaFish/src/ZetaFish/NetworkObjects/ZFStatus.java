package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 * The ZFStatus object will be passed to all clients whenever the game state changes.  
 * 
 * Design 4.6.10 v1.5
 */
public class ZFStatus implements Serializable
{
	private static final long serialVersionUID = 1L;
		
	/**
	 * Status types
	 */
	public enum StatusType
	{
		NONE,
		GAME_STARTED,
		GAME_OVER,
		TURN_CHANGE,
		CARDS_CHANGE,
		BOOK_PLAY,
		
		PLAYER_DELETE,
		
		ASSIGN_PLAYER_NUMBER
	}
	
	private StatusType status = StatusType.NONE;
	private int currentPlayer = 0;	
	private ZFPlayer[] players = null;
	private boolean isGameRunning;
	private boolean isGameOver;
	
	/**
	 * Constructor
	 * @param sts StatusType
	 * @param currentPlayer Current player number. (turn)
	 * @param players Array of ZFPlayers in the game.
	 * @param isGameRunning Is the game running?  true if it is, false otherwise.
	 * @param isGameOver Is the game over?  true if it is, false otherwise.
	 */
	public ZFStatus(StatusType sts, int currentPlayer, ZFPlayer[] players, boolean isGameRunning, boolean isGameOver)
	{
		this.status = sts;
		this.currentPlayer = currentPlayer;
		this.players = players;
		this.isGameRunning = isGameRunning;
		this.isGameOver = isGameOver;
	}
	
	/**
	 * StatusType
	 * @return StatusType
	 */
	public StatusType getStatus()
	{
		return this.status;
	}
	
	/**
	 * Current player number. (turn)
	 * @return int
	 */
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	/**
	 * Array of ZFPlayers in the game.
	 * @return ZFPlayer[]
	 */
	public ZFPlayer[] getPlayers()
	{
		return players;
	}
	
	/**
	 * Is the game running?  
	 * @return true if it is, false otherwise.
	 */
	public boolean getIsGameRunning()
	{
		return this.isGameRunning;
	}
	
	/**
	 * Is the game over?  
	 * @return true if it is, false otherwise.
	 */
	public boolean getIsGameOver()
	{
		return this.isGameOver;
	}
}
