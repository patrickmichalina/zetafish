package ZetaFish.NetworkObjects;

import java.io.Serializable;

import ZetaFish.*;

public class ZFPlayBook implements Serializable  
{
	private Card[] book;
	
	public ZFPlayBook(Card[] book)
	{
		this.book = book;
	}
	
	public Card[] getBook()
	{
		return this.book;
	}

}
