package ZetaFish.Interfaces;

import ZetaFish.NetworkObjects.*;

public interface ICardRequestResponseListener 
{
	/** 
	   *  Gets messages from other players. 
	   */
	  public void OnCardRequestResponse(ZFCardRequestResponse response);	
}
