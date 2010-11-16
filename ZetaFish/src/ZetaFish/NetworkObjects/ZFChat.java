package ZetaFish.NetworkObjects;

import java.io.*;

public class ZFChat implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	public String from;
	public String msg;
	
	public ZFChat(String from, String msg)
	{
		this.from = from;
		this.msg = msg;
	}
}
