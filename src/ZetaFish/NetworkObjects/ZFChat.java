package ZetaFish.NetworkObjects;

import java.io.*;

/**
 * The ZFChat object is used to pass as chat message from players to the server
 * which should broadcast it to all players.
 * 
 * Design 4.6.4 v1.5
 */
public class ZFChat implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	public String from;
	public String msg;
	
	/**
	 * Constructor
	 * @param from Who was this message from?
	 * @param msg The chat message.
	 */
	public ZFChat(String from, String msg)
	{
		this.from = from;
		this.msg = msg;
	}
}
