package ZetaFish.Interfaces;

import ZetaFish.NetworkObjects.*;

/**
 * Interface to be implemented by those interested in ZFRemovePlayer
 * objects sent from the server to the client and published by the 
 * implementation of INetworkManager. 
 */
public interface IRemovePlayerListener {
	/** 
	 *  Remove a player from the game. 
	 */
	public void OnRemovePlayer(ZFRemovePlayer remove);
}
