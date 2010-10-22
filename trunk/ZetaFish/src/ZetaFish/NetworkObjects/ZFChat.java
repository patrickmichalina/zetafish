package ZetaFish.NetworkObjects;

import java.io.*;

import ZetaFish.*;

public class ZFChat implements Serializable
{	
	public String from;
	public String msg;
	
	public ZFChat(String from, String msg)
	{
		this.from = from;
		this.msg = msg;
	}
}
