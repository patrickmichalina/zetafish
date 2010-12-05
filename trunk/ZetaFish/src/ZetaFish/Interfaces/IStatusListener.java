package ZetaFish.Interfaces;

import ZetaFish.NetworkObjects.*;

/**
 * Interface to be implemented by those interested in ZFStatus
 * objects sent from the server to the client and published by the 
 * implementation of INetworkManager. 
 */
public interface IStatusListener 
{
	 /** 
	   *  Notifies player in change of game status. 
	   */
	  public void OnGameStausChange(ZFStatus status);	
}
