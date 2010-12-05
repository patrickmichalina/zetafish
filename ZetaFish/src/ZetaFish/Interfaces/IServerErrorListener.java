package ZetaFish.Interfaces;

/**
 * Interface to be implemented by those interested in error messages
 * generated at the server and published by the implementations
 * of INetworkManager. 
 */
public interface IServerErrorListener {

	/** 
	 *  Notifies of a server error/exit and other such errors. 
	 */
	public void OnServerError(String msg);
}
