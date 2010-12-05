package ZetaFish.Interfaces;

/**
 * Interface to be implemented by those interested in being notified
 * of the local player's turn and published by implementations
 * of INetworkManager. 
 */
public interface ITurnListener {

	 /** 
	   *  Notifies player of turn 
	   */
	  public void OnGameTurn();
}
