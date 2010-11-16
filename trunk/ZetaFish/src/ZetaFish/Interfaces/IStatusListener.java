package ZetaFish.Interfaces;

import ZetaFish.NetworkObjects.*;

public interface IStatusListener 
{
	 /** 
	   *  Notifies player in change of game status. 
	   */
	  public void OnGameStausChange(ZFStatus status);	
}
