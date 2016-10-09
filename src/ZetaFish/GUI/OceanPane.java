package ZetaFish.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JLabel;

/**
 * Displays the ocean of cards in the GUI.
 * 
 *  Design 5.2 v1.6
 */
public class OceanPane extends PlayerPane 
{
	private static final long serialVersionUID = 1L;
	
	private JLabel       lblCardCount      = new JLabel("Count: ");
	
	/**
	 * Constructor
	 * @param deck Playing card deck
	 */
	public OceanPane(DeckOfCards deck)
	{
		super(deck);
		
		this.add(lblCardCount);
		
		Color DefaultForeColor = Color.WHITE;
		
		this.lblCardCount.setForeground(DefaultForeColor);
		this.lblCardCount.setBounds(10, 10, 100, 20); 
	}
	
	/**
     * DrawOcean - Draws Ocean cards
     */
    public void DrawOcean()
    {
    	int i = 0;
    	this.removeAll();
    	this.add(lblCardCount);
      	for(DeckOfCards.Suits suit : DeckOfCards.Suits.values())
      	{
      		if(suit != DeckOfCards.Suits.JOKER)
      		{
        		for(int val = 1; val <= 13; val++)
        		{
        			Card card= super.deck.getCard(val, suit);
        			this.add(card, new Integer(i));
        			card.setIcon(card.getImage());
        			card.setBounds((i++ * 18) + 30, 30,  60, 60);
        		}
      		}
      	}
      	UpdateCardCount();
        this.invalidate();
    }
    
    /**
     * Updates the on screen ocean card count.
     */
    public void UpdateCardCount()
    {
    	lblCardCount.setText("Count: " + this.getComponentCount());
    }
    
    /**
     * Put cards back into ocean.  (Exiting player?)
     * @param cards  Cards to return.
     */
    public void returnCardsToOcean(Component cards[])    
    {    	    	
    	if(cards != null)
    	{
    		for(Component cmp : cards)
    		{
    			if(cmp.getClass() == Card.class)
    			{
    				Card card = (Card)cmp;
    				this.add(card);
    				// find an open slot
    				for(int i=0; i< this.deck.size(); i++)
    				{
    					int expectedX = ((i*18) + 30);
    					Card pool_card = (Card)this.getComponent(i);
    					if(pool_card != null)
    					{
    						Rectangle rect = pool_card.getBounds();    		    			
    						if(rect.x != expectedX)
    						{    				
    							// empty slot
    							card.setBounds(expectedX, 30,  60, 60);
    							break;
    						}
    					}
    					else
    					{
    						// right side of pool
    						card.setBounds(expectedX, 30,  60, 60);
    					}
    				}
    			}
    		}    
    	}
    	UpdateCardCount();
        this.invalidate();
    }

}
