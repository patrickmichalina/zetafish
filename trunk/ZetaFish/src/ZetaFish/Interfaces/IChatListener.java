package ZetaFish.Interfaces;

/**
 * Interface to be implemented by those interested in chat messages
 * sent from the server to the client and published by the 
 * implementation of INetworkManager. 
 */
public interface IChatListener
{
	/** 
	   *  Gets messages from other players. 
	   */
	  public void OnNewMessage(String from, String msg);	

}