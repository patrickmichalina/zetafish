package ZetaFish.NetworkObjects;

import java.io.Serializable;

public class ZFRemovePlayer implements Serializable   
{
	private static final long serialVersionUID = 1L;
	
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
