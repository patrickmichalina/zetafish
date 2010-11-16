package ZetaFish.NetworkObjects;

import java.io.Serializable;

public class ZFPlayBook implements Serializable  
{
	private static final long serialVersionUID = 1L;
	
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
