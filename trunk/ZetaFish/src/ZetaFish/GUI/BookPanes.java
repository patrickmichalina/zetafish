package ZetaFish.GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import ZetaFish.NetworkObjects.ZFCard;

public class BookPanes extends JLayeredPane  
{
	private BookPane panelBookAce;
    private BookPane panelBookKing;
    private BookPane panelBookQueen;
    private BookPane panelBookJack;
    private BookPane panelBook10;
    private BookPane panelBook9;
    private BookPane panelBook8;
    private BookPane panelBook7;
    private BookPane panelBook6;
    private BookPane panelBook5;
    private BookPane panelBook4;
    private BookPane panelBook3;
    private BookPane panelBook2; 
	    
    private DeckOfCards deck = null; 
    
    public BookPanes(DeckOfCards deck)
    {
    	super();
    	this.deck = deck;
    	
    	panelBookAce      = new BookPane(deck);
        panelBookKing     = new BookPane(deck);
        panelBookQueen    = new BookPane(deck);
        panelBookJack     = new BookPane(deck);
        panelBook10       = new BookPane(deck);
        panelBook9        = new BookPane(deck);
        panelBook8        = new BookPane(deck);
        panelBook7        = new BookPane(deck);
        panelBook6        = new BookPane(deck);
        panelBook5        = new BookPane(deck);
        panelBook4        = new BookPane(deck);
        panelBook3        = new BookPane(deck);
        panelBook2        = new BookPane(deck); 
    	
    	this.add(panelBookAce);
    	this.add(panelBook2);
    	this.add(panelBook3);
    	this.add(panelBook4);
    	this.add(panelBook5);
    	this.add(panelBook6);
    	this.add(panelBook7);
    	this.add(panelBook8);
    	this.add(panelBook9);
    	this.add(panelBook10);
    	this.add(panelBookJack);
    	this.add(panelBookQueen);
    	this.add(panelBookKing);  
    	
    	panelBookAce.setBorder(     BorderFactory.createLineBorder(Color.black, 2));
        panelBookKing.setBorder(    BorderFactory.createLineBorder(Color.black, 2));
        panelBookQueen.setBorder(   BorderFactory.createLineBorder(Color.black, 2));
        panelBookJack.setBorder(    BorderFactory.createLineBorder(Color.black, 2));
        panelBook10.setBorder(      BorderFactory.createLineBorder(Color.black, 2));
        panelBook9.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
        panelBook8.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
        panelBook7.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
        panelBook6.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
        panelBook5.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
        panelBook4.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
        panelBook3.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
        panelBook2.setBorder(       BorderFactory.createLineBorder(Color.black, 2));
    	
        setBookPaneBounds();
    }
    
    private void setBookPaneBounds() {
        panelBookAce.setBounds(   30,  70, 101, 65);
        panelBookKing.setBounds( 134,  40, 101, 65);
        panelBookQueen.setBounds(134, 108, 101, 65);

        panelBookJack.setBounds( 238,  70, 101, 65);
        panelBook10.setBounds(   342,  40, 101, 65);
        panelBook9.setBounds(    342, 108, 101, 65);
        panelBook8.setBounds(    446,  70, 101, 65);
        panelBook7.setBounds(    550,  40, 101, 65);
        panelBook6.setBounds(    550, 108, 101, 65);
        panelBook5.setBounds(    654,  70, 101, 65);
        panelBook4.setBounds(    758,  40, 101, 65);
        panelBook3.setBounds(    758, 108, 101, 65);
        panelBook2.setBounds(    862,  70, 101, 65);

              
    } 
    
    public void AddBooks(ZFCard[][] books)
    {
		if(books != null)
		{
			for(ZFCard[] book : books)
			{
				int bookval = book[0].getValue();
				BookPane bp = (BookPane)this.getComponents()[bookval-1];
				bp.addCards(book);				
			}
		}
    }
    
    public void reset()
	{
		for(Component cmp : this.getComponents())
    	{ 
			BookPane pane = (BookPane)cmp;
			pane.removeAll();
    	}
	}
	
}
