package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 * The ZFRemovePlayer is sent from the server to all clients to 
 * signal that a player has unexpectedly left the game.
 * 
 * Design 4.6.9 v1.5
 */
public class ZFRemovePlayer implements Serializable   
{
	private static final long serialVersionUID = 1L;
	
	private ZFPlayer player = null;
	
	/**
	 * Constructor
	 * @param player ZFPlayer that is leaving the game.
	 */
	public ZFRemovePlayer(ZFPlayer player)
	{
		this.player = player;
	}
	
	/**
	 * Player leaving the game.
	 * @return ZFPlayer
	 */
	public ZFPlayer getPlayer()
	{
		return this.player;
	}	
}
