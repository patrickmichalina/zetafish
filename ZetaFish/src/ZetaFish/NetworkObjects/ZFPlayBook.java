package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 * The ZFPlayBook object is used to allow a given player to play a book 
 * during his/her turn.  It contains the cards being played in the book.
 * 
 * Design 4.6.7 v1.5
 */
public class ZFPlayBook implements Serializable  
{
	private static final long serialVersionUID = 1L;
	
	private ZFCard[] book;
	
	/**
	 * Constructor
	 * @param book Cards to play as a book.
	 */
	public ZFPlayBook(ZFCard[] book)
	{
		this.book = book;
	}
	
	/**
	 * Get the cards being played as a book.
	 * @return ZFCard[]
	 */
	public ZFCard[] getBook()
	{
		return this.book;
	}

}
