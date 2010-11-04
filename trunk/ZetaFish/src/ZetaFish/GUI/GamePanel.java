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
public class GamePanel extends JPanel implements IStatusListener, ITurnListener, ICardRequestResponseListener, Runnable, IChatListener, ActionListener{
	private DeckOfCards deck 					= new DeckOfCards();

    private OpponentHandPanes   panelOpponent   = new OpponentHandPanes(deck);
    private BookPanes panelBook         		= new BookPanes(deck);    
    private PlayerPane panelPool         		= new PlayerPane();   
    private PlayerHandPane panelPlayer      	= new PlayerHandPane(deck);	
     
    //private JPanel       panelInfo         = new JPanel();
    private JPanel       panelCardButtons  = new JPanel();
    private JPanel       panelChat         = new JPanel();

    private JPanel       panelChatOut = new JPanel();

    private JPanel       panelButtons      = new JPanel();
    private JButton      btn1              = new JButton("A");
    private JButton      btn2              = new JButton("2");
    private JButton      btn3              = new JButton("3");
    private JButton      btn4              = new JButton("4");
    private JButton      btn5              = new JButton("5");
    private JButton      btn6              = new JButton("6");
    private JButton      btn7              = new JButton("7");
    private JButton      btn8              = new JButton("8");
    private JButton      btn9              = new JButton("9");
    private JButton      btn10             = new JButton("10");
    private JButton      btnjack           = new JButton("J");
    private JButton      btnqueen          = new JButton("Q");
    private JButton      btnking           = new JButton("K");
    private JButton      btnSend           = new JButton("Send");
    private JButton      btnEndTurn        = new JButton("End Turn");
    private JButton      btnPlayBook       = new JButton("Play Books");
    private JButton      btnStartGame      = new JButton("Start Game");
    private JLabel       lblPool           = new JLabel("Pool: ");
    private JLabel       lblBook           = new JLabel("Book: ");
    private JLabel       lblWins           = new JLabel("Wins: ");
    private JLabel       lblLoss           = new JLabel("Losses: ");
    private JLabel       lblStatus         = new JLabel("Network Status: ");
    private JLabel       lblMyTurn         = new JLabel("My Turn!");
    private JLabel       lblCardCount      = new JLabel("Count: ");
    private JTextArea    txtOutput         = new JTextArea(5,0);
    private JTextField   txtInput          = new JTextField(50);
    private Dimension    dimButtonSize     = new Dimension(50,35);

    private final String SEND_ACTION = "send";
    private final String START_GAME_ACTION = "start_game";
    private final String END_TURN_ACTION = "end_turn";
    private final String PLAY_BOOK_ACTION = "play_book";
    private final String REQ_1_ACTION = "request_1_from_player";
    private final String REQ_2_ACTION = "request_2_from_player";
    private final String REQ_3_ACTION = "request_3_from_player";
    private final String REQ_4_ACTION = "request_4_from_player";
    private final String REQ_5_ACTION = "request_5_from_player";
    private final String REQ_6_ACTION = "request_6_from_player";
    private final String REQ_7_ACTION = "request_7_from_player";
    private final String REQ_8_ACTION = "request_8_from_player";
    private final String REQ_9_ACTION = "request_9_from_player";
    private final String REQ_10_ACTION = "request_10_from_player";
    private final String REQ_JACK_ACTION = "request_jack_from_player";
    private final String REQ_QUEEN_ACTION = "request_queen_from_player";
    private final String REQ_KING_ACTION = "request_king_from_player";
    
    private String serverName = "";
    private String playerName = "";

    private INetworkManager networkManager = null;

    private boolean CanStartGame = true;
    private boolean gameJustStarted = true;

    private ZFCard[] CurrentHand = null;

    public GamePanel(INetworkManager networkManager, String serverName, String playerName, boolean CanStartGame) throws Exception {
        super();
        this.setLayout(new FlowLayout(1,0,0));
        this.networkManager = networkManager;

        setButtonListeners();
        setLayouts();
        setComponents();
        setComponentDimensions();
        setBorders();
        setSeeThrough();
        setInitVisLayers();
        setTextColors();        
        setNetworkListeners();
        setTextRules();
        
        //testing!!!!
        //sub1BackDrop.setIcon(new ImageIcon(getClass().getResource("/Resources/fishtest.png")));
        //sub1BackDrop.setBounds(50, 50, 50, 50);
        //panelOpponentSub1.add(sub1BackDrop, 10);
        
        //sub1BackDrop.repaint();

        this.CanStartGame = CanStartGame;
        this.serverName = serverName;
        this.playerName = playerName;
        this.networkManager.openConnection(this.serverName, this.playerName, "My Password");

        DrawOcean();
    }

    /**
     * Draws Ocean cards
     */
    private void DrawOcean()
    {
    	int i = 0;
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
    }

    private void setComponents() {
        //All these components are added in a flowlayout. The order DOES matter.
       //this.add(panelInfo);
        this.add(panelOpponent);
        this.add(panelBook);
        this.add(panelPool);
        this.add(panelPlayer);
        this.add(panelCardButtons);
        this.add(panelChat);
        this.add(panelButtons);

        panelCardButtons.add(lblMyTurn);

        panelCardButtons.add(btn1);
        panelCardButtons.add(btn2);
        panelCardButtons.add(btn3);
        panelCardButtons.add(btn4);
        panelCardButtons.add(btn5);
        panelCardButtons.add(btn6);
        panelCardButtons.add(btn7);
        panelCardButtons.add(btn8);
        panelCardButtons.add(btn9);
        panelCardButtons.add(btn10);
        panelCardButtons.add(btnjack);
        panelCardButtons.add(btnqueen);
        panelCardButtons.add(btnking);             

        panelPool.add(lblCardCount);

        panelChat.add(new JScrollPane(txtOutput), BorderLayout.CENTER);
        panelChat.add(panelChatOut, BorderLayout.PAGE_END);


        //panelChatOut.setLayout(new FlowLayout());
        panelChatOut.add(txtInput);
        panelChatOut.add(btnSend);

        panelButtons.add(btnEndTurn);
        panelButtons.add(btnPlayBook);
        panelButtons.add(btnStartGame);

        this.btnPlayBook.setEnabled(false);
        this.btnEndTurn.setEnabled(false);
        this.btnStartGame.setEnabled(this.CanStartGame);

        this.txtOutput.setLineWrap(true);
    }

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
    	this.lblMyTurn.setForeground(DefaultForeColor);
    }


    private void setBorders() {
        panelPool.setBorder(        BorderFactory.createTitledBorder("Ocean"));
        panelBook.setBorder(        BorderFactory.createTitledBorder("Books"));
        panelPlayer.setBorder(      BorderFactory.createTitledBorder("Your Hand"));

        panelCardButtons.setBorder(	BorderFactory.createTitledBorder("Request Cards"));

        txtOutput.setBorder(        BorderFactory.createLineBorder(Color.black, 1));
        txtInput.setBorder(         BorderFactory.createLineBorder(Color.black, 1));
        panelChat.setBorder(        BorderFactory.createLineBorder(Color.black, 2));
    }

    private void setComponentDimensions() {
//        panelInfo.setPreferredSize(        new Dimension(1024, 30));
        panelBook.setPreferredSize(        new Dimension(1024,200));
        panelPool.setPreferredSize(        new Dimension(1024,110));
        panelPlayer.setPreferredSize(      new Dimension(1024,110));

        panelCardButtons.setPreferredSize( new Dimension(1024,70));

        panelChat.setPreferredSize(        new Dimension( 880,130));
        //panelChatOut.setPreferredSize(     new Dimension( 870,100));
        //txtOutput.setPreferredSize(        new Dimension( 870,20));

        panelButtons.setPreferredSize(     new Dimension( 124,130));

        panelOpponent.setPreferredSize(    new Dimension(1024,100));

        btn1.setPreferredSize(    dimButtonSize);
        btn2.setPreferredSize(    dimButtonSize);
        btn3.setPreferredSize(    dimButtonSize);
        btn4.setPreferredSize(    dimButtonSize);
        btn5.setPreferredSize(    dimButtonSize);
        btn6.setPreferredSize(    dimButtonSize);
        btn7.setPreferredSize(    dimButtonSize);
        btn8.setPreferredSize(    dimButtonSize);
        btn9.setPreferredSize(    dimButtonSize);
        btn10.setPreferredSize(   dimButtonSize);
        btnjack.setPreferredSize( dimButtonSize);
        btnqueen.setPreferredSize(dimButtonSize);
        btnking.setPreferredSize( dimButtonSize);

//        setBookPaneBounds();
        
        lblCardCount.setBounds(10, 10, 100, 20); 
    }   

    private void setSeeThrough() {
        this.setOpaque(         false);
//        panelInfo.setOpaque(    false);
        panelPlayer.setOpaque(  false);
        panelPool.setOpaque(    false);
        panelBook.setOpaque(    false);
        panelOpponent.setOpaque(false);
        panelCardButtons.setOpaque(false);
        panelButtons.setOpaque( false);
    }

    private void setLayouts() {
        panelOpponent.setLayout(new FlowLayout(0,0,0));
        panelChat.setLayout(new BorderLayout(0,0));
    }

    private void setInitVisLayers() {
        lblMyTurn.setVisible(false);
    }

    private void setButtonListeners() {
        this.btnSend.setActionCommand(SEND_ACTION);
        this.btnSend.addActionListener(this);

        this.btnStartGame.setActionCommand(START_GAME_ACTION);
        this.btnStartGame.addActionListener(this);

        this.btnEndTurn.setActionCommand(END_TURN_ACTION);
        this.btnEndTurn.addActionListener(this);
                
        this.btnPlayBook.setActionCommand(PLAY_BOOK_ACTION);
        this.btnPlayBook.addActionListener(this);
        
        this.btn1.setActionCommand(REQ_1_ACTION);
        this.btn1.addActionListener(this);

        this.btn2.setActionCommand(REQ_2_ACTION);
        this.btn2.addActionListener(this);

        this.btn3.setActionCommand(REQ_3_ACTION);
        this.btn3.addActionListener(this);

        this.btn4.setActionCommand(REQ_4_ACTION);
        this.btn4.addActionListener(this);

        this.btn5.setActionCommand(REQ_5_ACTION);
        this.btn5.addActionListener(this);

        this.btn6.setActionCommand(REQ_6_ACTION);
        this.btn6.addActionListener(this);

        this.btn7.setActionCommand(REQ_7_ACTION);
        this.btn7.addActionListener(this);

        this.btn8.setActionCommand(REQ_8_ACTION);
        this.btn8.addActionListener(this);

        this.btn9.setActionCommand(REQ_9_ACTION);
        this.btn9.addActionListener(this);

        this.btn10.setActionCommand(REQ_10_ACTION);
        this.btn10.addActionListener(this);

        this.btnjack.setActionCommand(REQ_JACK_ACTION);
        this.btnjack.addActionListener(this);

        this.btnqueen.setActionCommand(REQ_QUEEN_ACTION);
        this.btnqueen.addActionListener(this);

        this.btnking.setActionCommand(REQ_KING_ACTION);
        this.btnking.addActionListener(this);
    }

    private void setNetworkListeners() {
    	this.networkManager.addChatListener(this);
    	this.networkManager.addStatusListener(this);
    	this.networkManager.addTurnListener(this);
    	this.networkManager.addCardRequestResponseListener(this);
    }

    private void setTextRules() {
        txtOutput.setEditable(false);
    }
    
    private String BuildWinMessage(ZFStatus status)
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
      
	@Override
	public void OnGameStausChange(ZFStatus status)
	{
		GUIUtilities.ShowGameStatus(status);

		// Enable controls used during game play
		if(status.getIsGameRunning())
		{
			if(!status.getIsGameOver())
			{
				HandleTurnChange(false);
	
				this.btnStartGame.setEnabled(false);
	        	this.btnPlayBook.setEnabled(CanPlayBook());
	        	
	            if(gameJustStarted) {
	                GUIUtilities.playSound("shuffling.wav", this.getClass());             
	            }
			}
			else
			{
				
				String msg = BuildWinMessage(status);
				JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.OK_OPTION);
			}
		}
		else
		{					
			this.btnStartGame.setEnabled(this.CanStartGame);
        	this.btnPlayBook.setEnabled(false);
        	this.btnEndTurn.setEnabled(false);
        	
        	gameJustStarted = true;
		}

		ZFPlayer[] players = status.getPlayers();
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
					panelPlayer.addCards(this.CurrentHand);
					
				}
				else // Update other players
				{
					this.panelOpponent.addCardsToOpponent(i, player.getHand(), player.getPlayerNumber(), player.getPlayerName());
					i++;
				}
            }
			lblCardCount.setText("Count: " + panelPool.getComponentCount());
			this.panelOpponent.SetTurnIndicator(status.getCurrentPlayer());
        }
	
		if(status.getIsGameRunning())
		{
			if(gameJustStarted) {             
                SetDefaultOpponent();
            }
			gameJustStarted = false;
		}
	}

	private void SetDefaultOpponent()
	{
		System.out.println("SetDefaultOpponent");
		this.panelOpponent.SetDefaultOpponent();
	}
	
	/**
	 * Determines if there are 4 or more of a kind in players hand
	 * @return True if there are, false otherwise
	 */
	private boolean CanPlayBook()
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
	
	private void PlayBooks() throws Exception
	{
		List<ZFCard> possible_book = new ArrayList<ZFCard>();
		for(ZFCard card : this.CurrentHand)
    	{
			possible_book.clear();			
			for(ZFCard othercard : this.CurrentHand)
	    	{
				if(othercard.getValue() == card.getValue())
					possible_book.add(othercard);
	    	}
			if(possible_book.size() >= 4)
			{
				ZFCard[] book = new ZFCard[possible_book.size()];
				possible_book.toArray(book);
				this.networkManager.PlayBook(book);
			}    	
    	}
	}
	
	/**
	 * Ends player turn
	 * @throws Exception
	 */
	private void EndTurn() throws Exception
	{
		this.btnEndTurn.setEnabled(false);
		lblMyTurn.setVisible(false);
    	this.networkManager.DoneWithTurn();
	}

	private void HandleTurnChange(boolean IsTurn)
    {
    	if(IsTurn)
    	{   
    		System.out.println("My Turn!");
    		EnableCardRequestChoiceByHand();
    	}
    	else
    	{
    		btn1.setEnabled(false);
    		btn2.setEnabled(false);
    		btn3.setEnabled(false);
    		btn4.setEnabled(false);
    		btn5.setEnabled(false);
    		btn6.setEnabled(false);
    		btn7.setEnabled(false);
    		btn8.setEnabled(false);
    		btn9.setEnabled(false);
    		btn10.setEnabled(false);
    		btnjack.setEnabled(false);
    		btnqueen.setEnabled(false);
    		btnking.setEnabled(false);
    	}

    	this.btnEndTurn.setEnabled(IsTurn);
    	
    	lblMyTurn.setVisible(IsTurn);
    }

    private void EnableCardRequestChoiceByHand()
    {
    	List<Integer> handvals = new ArrayList<Integer>();
    	for(ZFCard card : this.CurrentHand)
    	{
    		handvals.add(card.getValue());
    	}

    	btn1.setEnabled(handvals.contains(1));
		btn2.setEnabled(handvals.contains(2));
		btn3.setEnabled(handvals.contains(3));
		btn4.setEnabled(handvals.contains(4));
		btn5.setEnabled(handvals.contains(5));
		btn6.setEnabled(handvals.contains(6));
		btn7.setEnabled(handvals.contains(7));
		btn8.setEnabled(handvals.contains(8));
		btn9.setEnabled(handvals.contains(9));
		btn10.setEnabled(handvals.contains(10));
		btnjack.setEnabled(handvals.contains(11));
		btnqueen.setEnabled(handvals.contains(12));
		btnking.setEnabled(handvals.contains(13));
    }


	@Override
	public void OnGameTurn()
	{
		HandleTurnChange(true);
	}

	@Override
	public void OnCardRequestResponse(ZFCardRequestResponse response)
	{
		GUIUtilities.ShowCardRequestResponse(response);
		
		boolean canPlayBook = CanPlayBook();
		
		this.btnPlayBook.setEnabled(canPlayBook);
		
		// Auto end turn
		if(!canPlayBook)
		{
			try
			{
				EndTurn();
			}
			catch(Exception err)
			{
				HandleException(err);
			}
		}		
	}

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void OnNewMessage(String from, String msg) {
        txtOutput.setText(txtOutput.getText() + from + ": " + msg + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
    	String action = ae.getActionCommand();
    	int selectedOpponent = this.panelOpponent.GetRequestPlayer();
    	try {
	        if(action == null ? SEND_ACTION == null : action.equals(SEND_ACTION))
	        {
	        	this.networkManager.sendMessage(txtInput.getText());
	            txtInput.setText("");
	        }
	        else if(action.equals(START_GAME_ACTION))
	        {
	        	this.networkManager.startGame();
	        }
	        else if(action.equals(END_TURN_ACTION))
	        {
	        	EndTurn();
	        }
	        else if(action.equals(PLAY_BOOK_ACTION))
	        {
	        	PlayBooks();
	        	EndTurn();
	        }
	        else if(action.equals(REQ_1_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 1);
	        }
	        else if(action.equals(REQ_2_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 2);
	        }
	        else if(action.equals(REQ_3_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 3);
	        }
	        else if(action.equals(REQ_4_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 4);
	        }
	        else if(action.equals(REQ_5_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 5);
	        }
	        else if(action.equals(REQ_6_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 6);
	        }
	        else if(action.equalsIgnoreCase(REQ_7_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 7);
	        }
	        else if(action.equals(REQ_8_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 8);
	        }
	        else if(action.equals(REQ_9_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 9);
	        }
	        else if(action.equals(REQ_10_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 10);
	        }
	        else if(action.equals(REQ_JACK_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 11);
	        }
	        else if(action.equals(REQ_QUEEN_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 12);
	        }
	        else if(action.equals(REQ_KING_ACTION))
	        {
	        	this.networkManager.RequestCards(selectedOpponent, 13);
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
