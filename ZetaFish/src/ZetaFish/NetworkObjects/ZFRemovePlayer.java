package ZetaFish.NetworkObjects;

import java.io.Serializable;

public class ZFRemovePlayer implements Serializable   
{
	private ZFPlayer player = null;
	
	public ZFRemovePlayer(ZFPlayer player)
	{
		this.player = player;
	}
	
	public ZFPlayer getPlayer()
	{
		return this.player;
	}	
}
