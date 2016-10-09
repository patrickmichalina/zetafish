package ZetaFish.Interfaces;

import ZetaFish.NetworkObjects.*;

/**
 * Interface to be implemented by those interested in CardRequestResponse
 * objects sent from the server to the client and published by the 
 * implementation of INetworkManager. 
 */
public interface ICardRequestResponseListener 
{
	/** 
	 *  Gets messages from other players. 
	 */
	public void OnCardRequestResponse(ZFCardRequestResponse response);	
}
