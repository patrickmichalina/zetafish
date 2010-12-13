package ZetaFish.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import ZetaFish.NetworkObjects.ZFCard;
import ZetaFish.NetworkObjects.ZFPlayer;

/**
 * Visual collection of OpponentHandPane.
 * Design 5.2 v1.6
 */
public class OpponentHandPanes extends JLayeredPane implements MouseListener
{
	private static final long serialVersionUID = 1L;
	
    private final Color SELECTED_COLOR = Color.GREEN;
    private final Color HOVER_COLOR = Color.WHITE;
	
	private OpponentHandPane   panelOpponentSub1;
    private OpponentHandPane   panelOpponentSub2;
    private OpponentHandPane   panelOpponentSub3;
    private OpponentHandPane   panelOpponentSub4;
    private OpponentHandPane   panelOpponentSub5;        
    
    private JLabel sub1BackDrop = new JLabel();
    
    int selectedOpponentNumber = -1;
    int hoveredlayerPaneNumber = -1;
    int oldAlpha = 0xFF000000;
    
    @SuppressWarnings("unused")
	private DeckOfCards deck = null;
	
    /**
     * Constructor
     * @param deck Deck of playing cards.
     */
	public OpponentHandPanes(DeckOfCards deck)
	{
		super();
		
		this.deck = deck;
		
		panelOpponentSub1 = new OpponentHandPane(deck);
		panelOpponentSub2 = new OpponentHandPane(deck);
		panelOpponentSub3 = new OpponentHandPane(deck);
		panelOpponentSub4 = new OpponentHandPane(deck);
		panelOpponentSub5 = new OpponentHandPane(deck);
		
		sub1BackDrop.setBounds(120,30,65,50);
		
		this.add(panelOpponentSub1);
		this.add(panelOpponentSub2);
		this.add(panelOpponentSub3);
		this.add(panelOpponentSub4);
		this.add(panelOpponentSub5);

        panelOpponentSub1.setVisible(false);
        panelOpponentSub2.setVisible(false);
        panelOpponentSub3.setVisible(false);
        panelOpponentSub4.setVisible(false);
        panelOpponentSub5.setVisible(false);
        
        panelOpponentSub1.setBorder(BorderFactory.createTitledBorder("Player 2" ));
        panelOpponentSub1.paint(null);
        panelOpponentSub2.setBorder(BorderFactory.createTitledBorder("Player 3"));
        panelOpponentSub3.setBorder(BorderFactory.createTitledBorder("Player 4"));
        panelOpponentSub4.setBorder(BorderFactory.createTitledBorder("Player 5"));
        panelOpponentSub5.setBorder(BorderFactory.createTitledBorder("Player 6"));
        
        panelOpponentSub1.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub2.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub3.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub4.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub5.setPreferredSize(new Dimension( 204,100));

		
		setMouseListener();		
	}
		
	/**
	 * Attach the mouse listener
	 */
	private void setMouseListener() {
        panelOpponentSub1.addMouseListener(this);
        panelOpponentSub2.addMouseListener(this);
        panelOpponentSub3.addMouseListener(this);
        panelOpponentSub4.addMouseListener(this);
        panelOpponentSub5.addMouseListener(this);
    }
	
	/**
	 * Pick one of the opponents as the default opponent to ask for cards from.
	 */
	public void SetDefaultOpponent()
	{		
		this.SelectOpponentPane((OpponentHandPane)this.getComponents()[0]);
	}
	
	/**
	 * Clear all the opponent hands.
	 */
	public void reset()
	{
		for(Component cmp : this.getComponents())
    	{ 
			OpponentHandPane pane = (OpponentHandPane)cmp;
			pane.removeAll();
    	}
	}
	
	/**
	 * Show a opponent pane as being selected.
	 * @param selectedPane Target pane
	 */
	public void SelectOpponentPane(OpponentHandPane selectedPane)
    {
		for(Component cmp : this.getComponents())
    	{    		
			OpponentHandPane pane = (OpponentHandPane)cmp;
    		if(pane == selectedPane)
    		{
    			pane.setBackground(SELECTED_COLOR);
    			pane.setOpaque(true);
    			pane.setSelected(true);  
    			selectedOpponentNumber = pane.getPlayerNumber();
    		}
    		else
    		{
    			pane.setBackground(HOVER_COLOR);
    			pane.setOpaque(false);
    			pane.setSelected(false);
    		}
    	}

        this.validate();
    }
	
	/**
     * Enable an indicator to show current player
     * @param CurrentPlayer Player number
     */
    public void SetTurnIndicator(int CurrentPlayer)
    {
    	for(Component cmp : this.getComponents())
    	{    		
			OpponentHandPane pane = (OpponentHandPane)cmp;  		
    		if(CurrentPlayer == pane.getPlayerNumber()) 
    		{
    			//turn indicator fish image
                sub1BackDrop.setIcon(new ImageIcon(getClass().getResource("/Resources/fishtest.png")));
                pane.add(sub1BackDrop, pane.getComponentCount() + 1);
                pane.setLayer(sub1BackDrop, Integer.MAX_VALUE);
            }
    	}
    }
    
    public void setOpponents(ZFPlayer[] players, int MyPlayerNumber)
    {	    	
    	/* hide all opponent panes */
    	for(Component cmp : this.getComponents())
    	{
    		OpponentHandPane op = (OpponentHandPane)cmp;
    		op.setVisible(false);
    		op.setPlayerNumber(-1);
    	}
    	
    	int i=0;
    	/* show the opponent panes */
    	for(ZFPlayer player : players)
    	{
    		if(player.getPlayerNumber() != MyPlayerNumber)
    		{
    			OpponentHandPane op = (OpponentHandPane)this.getComponent(i++);
    			op.setVisible(true);
    			op.setPlayerNumber(player.getPlayerNumber());
    			op.setTitle(player.getPlayerName() + " - " + player.getScore()); 
    		}    		
    	}    	
    }
        
    /**
     * Add playing cards to an opponent's hand and set information.
     * @param paneNumber Pane number index in collection.
     * @param cards Cards to add.
     * @param PlayerNumber Opponent's player number.
     * @param name Opponent's player name.
     * @param score Opponent's score.
     */
    public void addCardsToOpponent(int paneNumber, ZFCard[] cards, int PlayerNumber, String name, int score)
    {    
    	OpponentHandPane op = null;
    	for(Component cmp : this.getComponents())
    	{
    		op = (OpponentHandPane)cmp;
    		if(op.getPlayerNumber() == PlayerNumber)
    		{
    			op.setVisible(true);
    			op.setPlayerNumber(PlayerNumber);
    			op.setTitle(name + " - " + score);                   		
    			op.addCards(cards);
    		}    	
    		else if(op.getPlayerNumber() == -1)
    			op.setVisible(false);
    	}
    }
    
    /**
     * Remove player from collection.
     * @param playerNumber Player to remove.
     */
    public void removePlayer(int playerNumber)
    {
    	for(Component cmp : this.getComponents())
    	{    		
			OpponentHandPane pane = (OpponentHandPane)cmp;  		
    		if(playerNumber == pane.getPlayerNumber()) 
    		{
    			pane.setVisible(false);
    			pane.setPlayerNumber(-1);
    		}
    	}    	
    }
    
    /**
     * Get the cards in an opponent's hand as an array of Components.
     * @param playerNumber Opponent's player number
     * @return Component[]
     */
    public Component[] getPlayerCards(int playerNumber)
    {
    	Component[] retval = null;
    	
    	for(Component cmp : this.getComponents())
    	{    		
			OpponentHandPane pane = (OpponentHandPane)cmp;  		
    		if(playerNumber == pane.getPlayerNumber()) 
    		{
    			retval = pane.getComponents();
    			break;    			    			
    		}
    	}
    	return retval;
    }
	
	 /**
     * Pulls the selected playernumber from the radio button group
     * @return int
     */
    public int GetRequestPlayer() {
    	return selectedOpponentNumber;
    }

    /**
     *  Handle the selection of a new target opponent.  
     *  
     *  Additionally, see description in MouseListener
     */
    @Override
    public void mouseClicked(MouseEvent me) {
    	GUIUtilities.playSound("select.wav", this.getClass());
    	OpponentHandPane clickedPane = (OpponentHandPane) me.getSource();
    	
    	SelectOpponentPane(clickedPane);
    } 

    /**
     * See description in MouseListener
     */
    @Override
    public void mousePressed(MouseEvent me) { }

    /**
     * See description in MouseListener
     */
    @Override
    public void mouseReleased(MouseEvent me) {   }
    
    /**
     * Perform a "hover" effect.
     * 
     * Additionally, see description in MouseListener
     */
    @Override
    public void mouseEntered(MouseEvent me) {
        PlayerPane clickedPane = (PlayerPane) me.getSource();
        GUIUtilities.playSound("hover.wav", this.getClass());
        
        // Set the pane to translucent
        Color backColor = clickedPane.getBackground();
        if(!clickedPane.isSelected())
        {
        	backColor = HOVER_COLOR;
        	clickedPane.setOpaque(true);
        }
        oldAlpha = backColor.getAlpha();
        backColor = new Color(backColor.getRed(), backColor.getGreen(), backColor.getBlue(), 0x7F);
        
        clickedPane.setBackground(backColor);
              
        this.repaint();
    }

    /**
     * Clear the "hover" effect.
     * 
     * Additionally, see description in MouseListener
     */
    @Override
    public void mouseExited(MouseEvent me) {
        PlayerPane clickedPane = (PlayerPane) me.getSource();
        
        Color backColor = clickedPane.getBackground(); 
        if(!clickedPane.isSelected())
        {
        	backColor = HOVER_COLOR;
        	clickedPane.setOpaque(false);
        }
        backColor = new Color(backColor.getRed(), backColor.getGreen(), backColor.getBlue(), oldAlpha);
        
        clickedPane.setBackground(backColor);

        this.repaint();
    }
	
}
