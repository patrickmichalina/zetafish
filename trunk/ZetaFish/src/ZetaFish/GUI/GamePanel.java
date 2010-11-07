package ZetaFish.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import ZetaFish.Interfaces.*;
import ZetaFish.NetworkObjects.ZFCard;
import ZetaFish.NetworkObjects.ZFCardRequestResponse;
import ZetaFish.NetworkObjects.ZFPlayer;
import ZetaFish.NetworkObjects.ZFStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *  Summary: Class GamePanel represents the playing area that houses 3 sections.
 *           It holds the player, opponent, and pool panels. This is where the user
 *           can see what is happening during the game. Mainly, it can give an
 *           intuitive feeling of how many cards are left in the deck and what is
 *           in the player's hand.
 *  Imports: java.awt.BorderLayout, java.awt.Color, javax.swing.BorderFactory
 *  Exports: GamePanel Object.
 *  {author= Zeta Dragons, version=1.0, since=2010}
 *
 *  @author Patrick Michalina
 *  @author Chad Albrecht
 *  @author Melanie
 */
public class GamePanel extends JPanel implements IStatusListener, ITurnListener, ICardRequestResponseListener, Runnable, IChatListener, ICardButtonPush, ActionListener{
	private DeckOfCards deck 					= new DeckOfCards();

    private OpponentHandPanes   panelOpponent   = new OpponentHandPanes(deck);
    private BookPanes panelBook         		= new BookPanes(deck);    
    private PlayerPane panelPool         		= new PlayerPane();   
    private PlayerHandPane panelPlayer      	= new PlayerHandPane(deck);	
         
    private JPanel       panelChat         = new JPanel();

    private JPanel       panelChatOut = new JPanel();

    private JPanel       panelButtons      = new JPanel();
    
    private CardSelectorPane       panelCardButtons  = new CardSelectorPane();
    
    private JButton      btnSend           = new JButton("Send");
    private JButton      btnEndTurn        = new JButton("End Turn");
    private JButton      btnPlayBook       = new JButton("Play Books");
    private JButton      btnStartGame      = new JButton("Start Game");
    private JLabel       lblPool           = new JLabel("Pool: ");
    private JLabel       lblBook           = new JLabel("Book: ");
    private JLabel       lblWins           = new JLabel("Wins: ");
    private JLabel       lblLoss           = new JLabel("Losses: ");
    private JLabel       lblStatus         = new JLabel("Network Status: ");
    private JLabel       lblCardCount      = new JLabel("Count: ");
    private JTextArea    txtOutput         = new JTextArea(5,0);
    private JTextField   txtInput          = new JTextField(50);

    private final String SEND_ACTION = "send";
    private final String START_GAME_ACTION = "start_game";
    private final String END_TURN_ACTION = "end_turn";
    private final String PLAY_BOOK_ACTION = "play_book";
    
    private String serverName = "";
    private String playerName = "";

    private INetworkManager networkManager = null;

    private boolean CanStartGame = true;
    private boolean gameJustStarted = true;

    private ZFCard[] CurrentHand = null;

    /**
     * Constructor
     * @param networkManager
     * @param serverName
     * @param playerName
     * @param CanStartGame
     * @throws Exception
     */
    public GamePanel(INetworkManager networkManager, String serverName, String playerName, boolean CanStartGame) throws Exception {
        super();
        this.setLayout(new FlowLayout(1,0,0));
        this.networkManager = networkManager;
        this.CanStartGame = CanStartGame;
        this.serverName = serverName;
        this.playerName = playerName;

        setButtonListeners();
        setLayouts();
        setComponents();
        setComponentDimensions();
        setBorders();
        setSeeThrough();
        setTextColors();        
        setNetworkListeners();
        setTextRules();
        
        panelCardButtons.addCardButtonPushListener(this);
        
        //testing!!!!
        //sub1BackDrop.setIcon(new ImageIcon(getClass().getResource("/Resources/fishtest.png")));
        //sub1BackDrop.setBounds(50, 50, 50, 50);
        //panelOpponentSub1.add(sub1BackDrop, 10);
        
        //sub1BackDrop.repaint();
        
        this.networkManager.openConnection(this.serverName, this.playerName, "My Password");     
        
        this.invalidate();
    }

    /**
     * DrawOcean - Draws Ocean cards
     */
    private void DrawOcean()
    {
    	int i = 0;
    	panelPool.removeAll();
    	panelPool.add(lblCardCount);
      	for(DeckOfCards.Suits suit : DeckOfCards.Suits.values())
      	{
      		if(suit != DeckOfCards.Suits.JOKER)
      		{
        		for(int val = 1; val <= 13; val++)
        		{
        			Card card= deck.getCard(val, suit);
        			panelPool.add(card, new Integer(i));
        			card.setIcon(card.getImage());
        			card.setBounds((i++ * 18) + 30, 30,  60, 60);
        		}
      		}
      	}
        lblCardCount.setText("Count: " + i);
        this.invalidate();
    }

    /**
     * setComponents
     */
    private void setComponents() {
        // All these components are added in a flowlayout. 
    	// The order DOES matter.
        this.add(panelOpponent);
        this.add(panelBook);
        this.add(panelPool);
        this.add(panelPlayer);
        this.add(panelCardButtons);
        this.add(panelChat);
        this.add(panelButtons);

        panelPool.add(lblCardCount);

        panelChat.add(new JScrollPane(txtOutput), BorderLayout.CENTER);
        panelChat.add(panelChatOut, BorderLayout.PAGE_END);

        panelChatOut.add(txtInput);
        panelChatOut.add(btnSend);

        panelButtons.add(btnEndTurn);
        panelButtons.add(btnPlayBook);
        panelButtons.add(btnStartGame);

        this.btnPlayBook.setEnabled(false);
        this.btnEndTurn.setEnabled(false);
        this.btnStartGame.setEnabled(this.CanStartGame);
        this.btnStartGame.setVisible(this.CanStartGame);
        
        this.txtOutput.setLineWrap(true);
    }

    /**
     * setTextColors
     */
    private void setTextColors()
    {
    	Color DefaultForeColor = Color.WHITE;
    	TitledBorder border = null;

    	for(Component comp : panelOpponent.getComponents())
    	{
    		JLayeredPane pane = (JLayeredPane)comp;
    		border = (TitledBorder)pane.getBorder();
        	border.setTitleColor(DefaultForeColor);
    	}

    	border = (TitledBorder)this.panelBook.getBorder();
    	border.setTitleColor(DefaultForeColor);

    	border = (TitledBorder)this.panelPool.getBorder();
    	border.setTitleColor(DefaultForeColor);

    	border = (TitledBorder)this.panelPlayer.getBorder();
    	border.setTitleColor(DefaultForeColor);


    	border = (TitledBorder)this.panelCardButtons.getBorder();
    	border.setTitleColor(DefaultForeColor);

    	this.lblCardCount.setForeground(DefaultForeColor);
    }

    /**
     * setBorders
     */
    private void setBorders() {
    	panelPool.setBorder(        BorderFactory.createTitledBorder("Ocean"));
    	panelBook.setBorder(        BorderFactory.createTitledBorder("Books"));
    	panelPlayer.setBorder(      BorderFactory.createTitledBorder("Your Hand"));

    	panelCardButtons.setBorder(	BorderFactory.createTitledBorder("Request Cards"));

    	txtOutput.setBorder(        BorderFactory.createLineBorder(Color.black, 1));
    	txtInput.setBorder(         BorderFactory.createLineBorder(Color.black, 1));
    	panelChat.setBorder(        BorderFactory.createLineBorder(Color.black, 2));
    }

    /**
     * setComponentDimensions
     */
    private void setComponentDimensions() {
        panelBook.setPreferredSize(        new Dimension(1024,200));
        panelPool.setPreferredSize(        new Dimension(1024,110));
        panelPlayer.setPreferredSize(      new Dimension(1024,110));

        panelCardButtons.setPreferredSize( new Dimension(1024,70));

        panelChat.setPreferredSize(        new Dimension( 880,130));
        //panelChatOut.setPreferredSize(     new Dimension( 870,100));
        //txtOutput.setPreferredSize(        new Dimension( 870,20));

        panelButtons.setPreferredSize(     new Dimension( 124,130));

        panelOpponent.setPreferredSize(    new Dimension(1024,100));
       
        lblCardCount.setBounds(10, 10, 100, 20); 
    }   

    /**
     * setSeeThrough
     */
    private void setSeeThrough() {
        this.setOpaque(         false);
        panelPlayer.setOpaque(  false);
        panelPool.setOpaque(    false);
        panelBook.setOpaque(    false);
        panelOpponent.setOpaque(false);
        panelCardButtons.setOpaque(false);
        panelButtons.setOpaque( false);
    }

    /**
     * setLayouts
     */
    private void setLayouts() {
        panelOpponent.setLayout(new FlowLayout(0,0,0));
        panelChat.setLayout(new BorderLayout(0,0));
    }

    /**
     * setButtonListeners
     */
    private void setButtonListeners() {
        this.btnSend.setActionCommand(SEND_ACTION);
        this.btnSend.addActionListener(this);

        if(this.CanStartGame)
        {
        	this.btnStartGame.setActionCommand(START_GAME_ACTION);
        	this.btnStartGame.addActionListener(this);
        }

        this.btnEndTurn.setActionCommand(END_TURN_ACTION);
        this.btnEndTurn.addActionListener(this);
                
        this.btnPlayBook.setActionCommand(PLAY_BOOK_ACTION);
        this.btnPlayBook.addActionListener(this);
       
    }

    /**
     * setNetworkListeners
     */
    private void setNetworkListeners() {
    	this.networkManager.addChatListener(this);
    	this.networkManager.addStatusListener(this);
    	this.networkManager.addTurnListener(this);
    	this.networkManager.addCardRequestResponseListener(this);
    }

    /**
     * setTextRules
     */
    private void setTextRules() {
        txtOutput.setEditable(false);
    }
    
    /**
     * BuildWinMessage
     * @param status
     * @return
     */
    private String buildWinMessage(ZFStatus status)
    {
    	int max_score = 0;
    	ZFPlayer max_player = null;
    	for(ZFPlayer player: status.getPlayers())
        {
    		if(player.getScore() > max_score)
    		{
    			max_player = player;
    			max_score = player.getScore();
    		}    		
        }
    	
    	String name = "Player";
    	if(max_player != null)
    		name = max_player.getPlayerName();
    	
    	return name + " wins!";
    }
    
    private void updatePlayerHands(ZFPlayer[] players)
    {
    	if(players != null)
        {
			int i = 0;
                        
			for(ZFPlayer player: players)
            {
				// Update player books
				panelBook.AddBooks(player.getBooks());
				
				// Update my hand
				if(player.getPlayerNumber() == this.networkManager.getMyPlayerNumber())
				{
					this.CurrentHand = player.getHand();
					panelPlayer.addCards(this.CurrentHand, player.getScore());
					
				}
				else // Update other players
				{					
					this.panelOpponent.addCardsToOpponent(i, player.getHand(), 
															 player.getPlayerNumber(), 
															 player.getPlayerName(),
															 player.getScore());
					i++;
				}
            }
			lblCardCount.setText("Count: " + panelPool.getComponentCount());			
        }
    }
      
	@Override
	public void OnGameStausChange(ZFStatus status)
	{
		GUIUtilities.ShowGameStatus(status);

		// Enable controls used during game play
		if(status.getIsGameRunning())
		{
			if(!status.getIsGameOver())
			{
				boolean isTurn = (status.getCurrentPlayer() == this.networkManager.getMyPlayerNumber());
				ZFPlayer[] players = status.getPlayers();
				switch(status.getStatus())
				{
					case GAME_STARTED:
						gameJustStarted = true;
						deck.reset();
						this.panelOpponent.reset();
						this.panelBook.reset();
						this.panelPlayer.reset();
						DrawOcean();
						GUIUtilities.playSound("shuffling.wav", this.getClass());				    
			            updatePlayerHands(players);			           
						handleTurnChange(isTurn);
						setDefaultOpponent();
						this.panelOpponent.SetTurnIndicator(status.getCurrentPlayer());
						this.btnStartGame.setText("New Game");
						gameJustStarted = false;
			            break;
						
					case TURN_CHANGE:						
						handleTurnChange(isTurn);
						this.panelOpponent.SetTurnIndicator(status.getCurrentPlayer());
						break;

					case BOOK_PLAY:
					case CARDS_CHANGE:						
			            updatePlayerHands(players);			           
						handleTurnChange(isTurn);
						this.btnPlayBook.setEnabled(canPlayBook());
						break;
						
					case PLAYER_DELETE:
						break;
						
					case GAME_OVER:					
						break;
				}
				this.repaint();
			}
			else if(status.getStatus() == ZFStatus.StatusType.GAME_OVER)  //Game end?
			{

	        	this.btnPlayBook.setEnabled(false);
	        	this.btnEndTurn.setEnabled(false);
	        	
				String msg = buildWinMessage(status);
				JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.OK_OPTION);
			}	
		}
		else
		{					
        	this.btnPlayBook.setEnabled(false);
        	this.btnEndTurn.setEnabled(false);
        	
        	gameJustStarted = true;
		}
	}

	/**
	 * SetDefaultOpponent
	 */
	private void setDefaultOpponent()
	{
		System.out.println("SetDefaultOpponent");
		this.panelOpponent.SetDefaultOpponent();
	}
	
	/**
	 * Determines if there are 4 or more of a kind in players hand
	 * @return True if there are, false otherwise
	 */
	private boolean canPlayBook()
	{	
		boolean retval = false;
		if(this.CurrentHand != null)
		{
			
			for(ZFCard card : this.CurrentHand)
	    	{
				int cardcount = 1;
				for(ZFCard othercard : this.CurrentHand)
		    	{
					if(othercard.getValue() == card.getValue())
						cardcount++;
		    	}				
				if(cardcount > 4)
				{
					retval = true;
					break;
				}    	
	    	}
		}
		return retval;
	}
	
	/**
	 * PlayBooks
	 * @throws Exception
	 */
	private void playBooks() throws Exception
	{
		List<ZFCard> hand = new ArrayList<ZFCard>(Arrays.asList(this.CurrentHand));
		ZFCard[] book = getBook(hand); 
		while(book != null)
		{
			this.networkManager.PlayBook(book);
			book = getBook(hand);
		}
	}
	
	/**
	 * getBook - Returns all the books in a players hand
	 * @param hand
	 * @return
	 */
	private ZFCard[] getBook(List<ZFCard> hand)
	{
		ZFCard[] book = null;
		List<ZFCard> possible_book = new ArrayList<ZFCard>();
		for(ZFCard card : hand)
    	{
			possible_book.clear();			
			for(ZFCard othercard : hand)
	    	{
				if(othercard.getValue() == card.getValue())
					possible_book.add(othercard);
	    	}
			if(possible_book.size() >= 4)
			{
				book = new ZFCard[possible_book.size()];
				possible_book.toArray(book);	
				
			}    	
    	}
		if(book != null)
		{
			for(ZFCard bookcard : book)
			{
				hand.remove(bookcard);
			}
		}
		return book;
	}
	
	/**
	 * Ends player turn
	 * @throws Exception
	 */
	private void endTurn() throws Exception
	{
		this.btnEndTurn.setEnabled(false);
		this.btnPlayBook.setEnabled(false);
		this.panelCardButtons.ShowMyTurn(false);
    	this.networkManager.DoneWithTurn();
	}

	/**
	 * HandleTurnChange
	 * @param IsTurn
	 */
	private void handleTurnChange(boolean IsTurn)
    {
    	if(IsTurn)
    	{   
    		System.out.println("My Turn!");
    		this.panelCardButtons.EnableCardRequestChoiceByHand(this.CurrentHand);
    	}
    	else
    	{
    		this.panelCardButtons.SetAllEnabledState(false);
    	}

    	this.btnEndTurn.setEnabled(IsTurn);    	
    	this.panelCardButtons.ShowMyTurn(IsTurn);
    }
	
	/**
	 * startGame
	 */
	private void startGame() throws Exception
	{		
		
    	this.networkManager.startGame();
    	
	}
	
	@Override
	public void OnGameTurn()
	{
		// Currently not used
	}

	@Override
	public void OnCardRequestResponse(ZFCardRequestResponse response)
	{
		GUIUtilities.ShowCardRequestResponse(response);
		
		boolean canPlayBook = canPlayBook();
		
		this.btnPlayBook.setEnabled(canPlayBook);
		this.panelCardButtons.SetAllEnabledState(false);
		
		// Auto end turn
		if(!canPlayBook)
		{
			try
			{
				endTurn();
			}
			catch(Exception err)
			{
				HandleException(err);
			}
		}		
	}

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void OnNewMessage(String from, String msg) {
        txtOutput.setText(txtOutput.getText() + from + ": " + msg + "\n");
    }
    
    @Override
	public void OnCardButtonSelect(int cardValue) {
    	int selectedOpponent = this.panelOpponent.GetRequestPlayer();
    	try {
			this.networkManager.RequestCards(selectedOpponent, cardValue);
		} catch (Exception e) {
			HandleException(e);
		}		
	}     

    @Override
    public void actionPerformed(ActionEvent ae)
    {
    	String action = ae.getActionCommand();
    	try {
	        if(action == null ? SEND_ACTION == null : action.equals(SEND_ACTION))
	        {
	        	this.networkManager.sendMessage(txtInput.getText());
	            txtInput.setText("");
	        }
	        else if(action.equals(START_GAME_ACTION))
	        {
	        	startGame();
	        }
	        else if(action.equals(END_TURN_ACTION))
	        {
	        	endTurn();
	        }
	        else if(action.equals(PLAY_BOOK_ACTION))
	        {
	        	playBooks();
	        	endTurn();
	        }
    	}
        catch(Exception err) {
            HandleException(err);
        }
    }

    /**
     * Single spot to determine how to handle exceptions
     * @param err
     */
    private void HandleException(Exception err) {
    	err.printStackTrace();
    }	 
}
