package ZetaFish.Interfaces;

public interface IChatListener
{
	/** 
	   *  Gets messages from other players. 
	   */
	  public void OnNewMessage(String from, String msg);	

}