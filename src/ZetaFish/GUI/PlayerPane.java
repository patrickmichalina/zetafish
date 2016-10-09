package ZetaFish.GUI;

import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;

import ZetaFish.NetworkObjects.ZFCard;

/**
 * Base class for many of the Panes described in:
 * Design 5.2 v1.6
 */
public class PlayerPane extends JLayeredPane  
{
	private static final long serialVersionUID = 1L;
	
    private int playerNumber = -1;
    private boolean isSelected = false;
    
    protected DeckOfCards deck = null;
    
    protected int cardSpacing = 20;
    protected int cardOffsetX = 0;
    protected int cardOffsetY = 0;
    
    protected int cardWidth = 45;
    protected int cardHeight = 65;
    
    protected boolean cardsVisible = true;

    /**
     * Default Constructor
     */
	public PlayerPane() {
            super();
	}
	
	/**
	 * Constructor
	 * @param deck Deck of playing cards.
	 */
	public PlayerPane(DeckOfCards deck)
	{
		super();
		this.deck = deck;		
	}

	/**
	 * Gets the player number represented by this pane.
	 * @return int player number
	 */
	public int getPlayerNumber() {
            return this.playerNumber;
	}

	/**
	 * Sets the player number represented by this pane.
	 * @param playerNumber in player number.
	 */
	public void setPlayerNumber(int playerNumber) {
            this.playerNumber = playerNumber;
	}

	/**
	 * Is this pane in the "selected" state?
	 * @return true if selected, false otherwise.
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Set this pane to the "selected" state.
	 * @param isSelected true if selected, false otherwise.
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	/**
	 * Set the title of the pane. (TitledBorder)
	 * @param title String to use for the title.
	 */
	protected void setTitle(String title){
		TitledBorder border = (TitledBorder)this.getBorder();
        border.setTitle(title);
	}
	
	
		
	public void addCards(ZFCard[] cards)
	{
		
		if((cards != null) && (deck != null))
		{
			this.removeAll();
	    	int i = 0;
	    	
	    	// TODO: This is ugly.  Refactor to follow DRY
	    	for(ZFCard zfCard : cards)
	    	{
	    		DeckOfCards.Suits suit = DeckOfCards.Suits.JOKER;
	    		switch(zfCard.getSuit())
	    		{
	    			case CLUBS:
	    				suit = DeckOfCards.Suits.CLUBS;
	    				break;
	    			case SPADES:
	    				suit = DeckOfCards.Suits.SPADES;
	    				break;
	    			case HEARTS:
	    				suit = DeckOfCards.Suits.HEARTS;
	    				break;
	    			case DIAMONDS:
	    				suit = DeckOfCards.Suits.DIAMONDS;
	    				break;
	    		}
	    		Card card = deck.getCard(zfCard.getValue(), suit);	
	            card.setShown(cardsVisible);	            
	            card.setBounds((i * cardSpacing) + cardOffsetX, cardOffsetY,  cardWidth, cardHeight);
	            
	            this.add(card, i);
                this.setLayer(card, i);                
                i++;
	    	}
	    	this.repaint();
		}
	}
}