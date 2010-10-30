package ZetaFish.NetworkObjects;

import java.io.Serializable;

import ZetaFish.*;

public class ZFPlayBook implements Serializable  
{
	private ZFCard[] book;
	
	public ZFPlayBook(ZFCard[] book)
	{
		this.book = book;
	}
	
	public ZFCard[] getBook()
	{
		return this.book;
	}

}
