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
import java.util.ArrayList;
import java.util.List;

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
public class GamePanel extends JPanel implements IStatusListener, ITurnListener, ICardRequestResponseListener, Runnable, IChatListener, ActionListener {
    private JLayeredPane playerPanel       = new JLayeredPane();
    private JLayeredPane poolPanel         = new JLayeredPane();
    private JLayeredPane bookPanel         = new JLayeredPane();
    private JLayeredPane opponentPanel     = new JLayeredPane();
    private JLayeredPane opponentSubPanel1 = new JLayeredPane();
    private JLayeredPane opponentSubPanel2 = new JLayeredPane();
    private JLayeredPane opponentSubPanel3 = new JLayeredPane();
    private JLayeredPane opponentSubPanel4 = new JLayeredPane();
    private JLayeredPane opponentSubPanel5 = new JLayeredPane();
    private JLayeredPane panelBookAce      = new JLayeredPane();
    private JLayeredPane panelBookKing     = new JLayeredPane();
    private JLayeredPane panelBookQueen    = new JLayeredPane();
    private JLayeredPane panelBookJack     = new JLayeredPane();
    private JLayeredPane panelBook10       = new JLayeredPane();
    private JLayeredPane panelBook9        = new JLayeredPane();
    private JLayeredPane panelBook8        = new JLayeredPane();
    private JLayeredPane panelBook7        = new JLayeredPane();
    private JLayeredPane panelBook6        = new JLayeredPane();
    private JLayeredPane panelBook5        = new JLayeredPane();
    private JLayeredPane panelBook4        = new JLayeredPane();
    private JLayeredPane panelBook3        = new JLayeredPane();
    private JLayeredPane panelBook2        = new JLayeredPane();
    private JPanel       infoPanel         = new JPanel();
    private JPanel       goFishPlayerButtons = new JPanel();
    private JPanel       goFishCardButtons = new JPanel();
    private JPanel       panelChat         = new JPanel();
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
    
    private ButtonGroup  reqPlayerGroup    = new ButtonGroup();
    private PlayerButton btnReqPlayer1     = new PlayerButton("Player1", true);
    private PlayerButton btnReqPlayer2     = new PlayerButton("Player2");
    private PlayerButton btnReqPlayer3     = new PlayerButton("Player3");
    private PlayerButton btnReqPlayer4     = new PlayerButton("Player4");
    private PlayerButton btnReqPlayer5     = new PlayerButton("Player5");
    
    private JButton      btnSend           = new JButton("Send");
    
    private JButton      btnEndTurn        = new JButton("End Turn");    
    private JButton      btnPlayBook       = new JButton("Play Book");    
    private JButton      btnStartGame      = new JButton("Start Game");    
    
    private JLabel       poolTxt           = new JLabel("Pool: ");
    private JLabel       bookTxt           = new JLabel("Book: ");
    private JLabel       winsTxt           = new JLabel("Wins: ");
    private JLabel       lossTxt           = new JLabel("Losses: ");
    private JLabel       statusTxt         = new JLabel("Network Status: ");
    private JLabel       myTrnTxt          = new JLabel("My Turn!");
    private JLabel       lblCardCount      = new JLabel("Count: ");
    private JTextArea    txtOutput         = new JTextArea(5,0);
    private JTextField   txtInput          = new JTextField();
    private Dimension    btnSize           = new Dimension(50,35);

    private final String SEND_ACTION = "send";
    private final String START_GAME_ACTION = "start_game";
    private final String END_TURN_ACTION = "end_turn";
    
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

    private INetworkManager networkManager = null;

    private DeckOfCards deck = null;

    private String serverName = "";
    private String playerName = "";
    
    private boolean CanStartGame = true;
    
    private ZFCard[] CurrentHand = null;

    public GamePanel(INetworkManager networkManager, String serverName, String playerName, boolean CanStartGame) throws Exception{
        super();
        this.setLayout(new FlowLayout(1,0,0));
        this.networkManager = networkManager;

        this.deck = new DeckOfCards();

        setButtonListeners();
        setLayouts();
        setComponents();
        setComponentDimensions();
        setBorders();
        setSeeThrough();
        setInitVisLayers();
        setTextColors();
        
        setNetworkListeners();

        this.CanStartGame = CanStartGame;
        this.serverName = serverName;
        this.playerName = playerName;
        this.networkManager.openConnection(this.serverName, this.playerName, "My Password");
        
        //testDrawCards();
        
        DrawOcean();
    }

    private void testDrawCards()
    {    
    	// this is just for testing at the moment!
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
        			poolPanel.add(card, new Integer(i));
        			card.setIcon(card.getImage());
        			card.setBounds((i++ * 18) + 30, 45,  60, 60);
        		}
      		}   
      	}
        lblCardCount.setText("Count: " + i);    	
    }

    private void setComponents() {
        //All these components are added in a flowlayout. The order DOES matter.
        this.add(infoPanel);
        this.add(opponentPanel);
        this.add(bookPanel);
        this.add(poolPanel);
        this.add(playerPanel);
        this.add(goFishPlayerButtons);
        this.add(goFishCardButtons);
        this.add(panelChat);
        this.add(panelButtons);

        opponentPanel.add(opponentSubPanel1);
        opponentPanel.add(opponentSubPanel2);
        opponentPanel.add(opponentSubPanel3);
        opponentPanel.add(opponentSubPanel4);
        opponentPanel.add(opponentSubPanel5);
        
        opponentSubPanel1.setVisible(false);
        opponentSubPanel2.setVisible(false);
        opponentSubPanel3.setVisible(false);
        opponentSubPanel4.setVisible(false);
        opponentSubPanel5.setVisible(false);
        
        reqPlayerGroup.add(btnReqPlayer1);
        reqPlayerGroup.add(btnReqPlayer2);
        reqPlayerGroup.add(btnReqPlayer3);
        reqPlayerGroup.add(btnReqPlayer4);
        reqPlayerGroup.add(btnReqPlayer5);
        
        goFishPlayerButtons.add(btnReqPlayer1);
        goFishPlayerButtons.add(btnReqPlayer2);
        goFishPlayerButtons.add(btnReqPlayer3);
        goFishPlayerButtons.add(btnReqPlayer4);
        goFishPlayerButtons.add(btnReqPlayer5);
        
        btnReqPlayer1.setVisible(false);
        btnReqPlayer2.setVisible(false);
        btnReqPlayer3.setVisible(false);
        btnReqPlayer4.setVisible(false);
        btnReqPlayer5.setVisible(false);
        
        goFishCardButtons.add(myTrnTxt);        
        
        goFishCardButtons.add(btn1);
        goFishCardButtons.add(btn2);
        goFishCardButtons.add(btn3);
        goFishCardButtons.add(btn4);
        goFishCardButtons.add(btn5);
        goFishCardButtons.add(btn6);
        goFishCardButtons.add(btn7);
        goFishCardButtons.add(btn8);
        goFishCardButtons.add(btn9);
        goFishCardButtons.add(btn10);
        goFishCardButtons.add(btnjack);
        goFishCardButtons.add(btnqueen);
        goFishCardButtons.add(btnking);
        
        infoPanel.add(poolTxt);
        infoPanel.add(bookTxt);
        infoPanel.add(winsTxt);
        infoPanel.add(lossTxt);
        infoPanel.add(statusTxt);

        bookPanel.add(panelBookAce);
        bookPanel.add(panelBookKing);
        bookPanel.add(panelBookQueen);
        bookPanel.add(panelBookJack);
        bookPanel.add(panelBook10);
        bookPanel.add(panelBook9);
        bookPanel.add(panelBook8);
        bookPanel.add(panelBook7);
        bookPanel.add(panelBook6);
        bookPanel.add(panelBook5);
        bookPanel.add(panelBook4);
        bookPanel.add(panelBook3);
        bookPanel.add(panelBook2);

        poolPanel.add(lblCardCount);

        panelChat.add(new JScrollPane(txtOutput), BorderLayout.PAGE_START);
        panelChat.add(txtInput, BorderLayout.CENTER);
        panelChat.add(btnSend, BorderLayout.LINE_END);

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
    	
    	for(Component comp : opponentPanel.getComponents())
    	{
    		JLayeredPane pane = (JLayeredPane)comp;
    		border = (TitledBorder)pane.getBorder();
        	border.setTitleColor(DefaultForeColor);    	
    	}
    	
    	border = (TitledBorder)this.bookPanel.getBorder();
    	border.setTitleColor(DefaultForeColor);   
    	
    	border = (TitledBorder)this.poolPanel.getBorder();
    	border.setTitleColor(DefaultForeColor);
    	
    	border = (TitledBorder)this.playerPanel.getBorder();
    	border.setTitleColor(DefaultForeColor);
    	
    	border = (TitledBorder)this.goFishPlayerButtons.getBorder();
    	border.setTitleColor(DefaultForeColor);
    	
    	border = (TitledBorder)this.goFishCardButtons.getBorder();
    	border.setTitleColor(DefaultForeColor);
    	
    	this.lblCardCount.setForeground(DefaultForeColor);    	
    	this.myTrnTxt.setForeground(DefaultForeColor);
    }
    

    private void setBorders() {
        opponentSubPanel1.setBorder(BorderFactory.createTitledBorder("Player 2" ));
        opponentSubPanel2.setBorder(BorderFactory.createTitledBorder("Player 3"));
        opponentSubPanel3.setBorder(BorderFactory.createTitledBorder("Player 4"));
        opponentSubPanel4.setBorder(BorderFactory.createTitledBorder("Player 5"));
        opponentSubPanel5.setBorder(BorderFactory.createTitledBorder("Player 6"));
        poolPanel.setBorder(        BorderFactory.createTitledBorder("Ocean"));
        bookPanel.setBorder(        BorderFactory.createTitledBorder("Books"));
        playerPanel.setBorder(      BorderFactory.createTitledBorder("Your Hand"));
        
        goFishPlayerButtons.setBorder(	BorderFactory.createTitledBorder("Request from Player"));
        goFishCardButtons.setBorder(	BorderFactory.createTitledBorder("Request Cards"));
        
        infoPanel.setBorder(        BorderFactory.createLineBorder(Color.black, 2));
        
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
        txtOutput.setBorder(        BorderFactory.createLineBorder(Color.black, 1));
        txtInput.setBorder(         BorderFactory.createLineBorder(Color.black, 1));
        panelChat.setBorder(        BorderFactory.createLineBorder(Color.black, 2));
    }

    private void setComponentDimensions() {
        infoPanel.setPreferredSize(        new Dimension(1024, 30));
        panelChat.setPreferredSize(        new Dimension( 890,110));
        panelButtons.setPreferredSize(     new Dimension( 124,130));
        
        goFishPlayerButtons.setPreferredSize(  new Dimension(1024,75));
        goFishCardButtons.setPreferredSize(	   new Dimension(1024,75));

        playerPanel.setPreferredSize(      new Dimension(1024,100));
        poolPanel.setPreferredSize(        new Dimension(1024,150));
        bookPanel.setPreferredSize(        new Dimension(1024,200));

        opponentPanel.setPreferredSize(    new Dimension(1024,100));
        opponentSubPanel1.setPreferredSize(new Dimension( 205,100));
        opponentSubPanel2.setPreferredSize(new Dimension( 205,100));
        opponentSubPanel3.setPreferredSize(new Dimension( 205,100));
        opponentSubPanel4.setPreferredSize(new Dimension( 205,100));
        opponentSubPanel5.setPreferredSize(new Dimension( 204,100));

        btn1.setPreferredSize(    btnSize);
        btn2.setPreferredSize(    btnSize);
        btn3.setPreferredSize(    btnSize);
        btn4.setPreferredSize(    btnSize);
        btn5.setPreferredSize(    btnSize);
        btn6.setPreferredSize(    btnSize);
        btn7.setPreferredSize(    btnSize);
        btn8.setPreferredSize(    btnSize);
        btn9.setPreferredSize(    btnSize);
        btn10.setPreferredSize(   btnSize);
        btnjack.setPreferredSize( btnSize);
        btnqueen.setPreferredSize(btnSize);
        btnking.setPreferredSize( btnSize);
        
        setBookPaneBounds();
    }

    private void setBookPaneBounds() {
        panelBookAce.setBounds(   30,  70, 99, 65);
        panelBookKing.setBounds( 130,  40, 99, 65);
        panelBookQueen.setBounds(130, 100, 99, 65);
        panelBookJack.setBounds( 230,  70, 99, 65);
        panelBook10.setBounds(   330,  40, 99, 65);
        panelBook9.setBounds(    330, 100, 99, 65);
        panelBook8.setBounds(    430,  70, 99, 65);
        panelBook7.setBounds(    530,  40, 99, 65);
        panelBook6.setBounds(    530, 100, 99, 65);
        panelBook5.setBounds(    630,  70, 99, 65);
        panelBook4.setBounds(    730,  40, 99, 65);
        panelBook3.setBounds(    730, 100, 99, 65);
        panelBook2.setBounds(    830,  70, 99, 65);

        lblCardCount.setBounds(10, 10, 100, 20);
    }

    private void setSeeThrough() {
        this.setOpaque(         false);
        infoPanel.setOpaque(    false);
        playerPanel.setOpaque(  false);
        poolPanel.setOpaque(    false);
        bookPanel.setOpaque(    false);
        opponentPanel.setOpaque(false);
        goFishPlayerButtons.setOpaque(false);
        goFishCardButtons.setOpaque(false);
        panelButtons.setOpaque( false);
    }

    private void setLayouts() {
        opponentPanel.setLayout(new FlowLayout(0,0,0));
        panelChat.setLayout(new BorderLayout(3,3));
    }

    private void setInitVisLayers() {
        myTrnTxt.setVisible(false);
    }

    private void setButtonListeners() {
        this.btnSend.setActionCommand(SEND_ACTION);
        this.btnSend.addActionListener(this);

        this.btnStartGame.setActionCommand(START_GAME_ACTION);
        this.btnStartGame.addActionListener(this);
        
        this.btnEndTurn.setActionCommand(END_TURN_ACTION);
        this.btnEndTurn.addActionListener(this);

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

    /**
     * Below are the access methods that can be used to add or remove cards
     * between each area. So when a player draws a card from the pool, it should
     * be updated by removing a card from the pool and adding it into the players
     * hand. Likewise, when a player gets 4 of a kind, those 4 should be removed
     * and the score should be adjust in the title bar.
     *
     */
    public JLayeredPane getPoolPanel() {
        return poolPanel;
    }

    public JLayeredPane getPlayerPanel() {
        return playerPanel;
    }

    public JLayeredPane getOpponentPanel() {
        return opponentPanel;
    }


    /**
     * Adds one or more cards coming in from a request to a pane
     * @param pane
     * @param cards
     */
    private void addCardsToPane(JLayeredPane pane, ZFCard[] cards, boolean isHeld, boolean isShown)
    {
    	pane.removeAll();
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

    		card.setShown(isShown);

            if(isHeld) {
                card.setBounds((i * 60) + 20, 18,  45, 65);
            }
            else {
                card.setBounds((i * 8) + 15, 20,  45, 65);
            }

            pane.add(card, i);
    		pane.setComponentZOrder(card, i);
    		i++;
                this.repaint();
                pane.repaint();
                this.validate();
    	}
    	pane.repaint();
    }
   
	@Override
	public void OnGameStausChange(ZFStatus status)
	{
		ShowGameStatus(status);

		// Enable controls used during game play
		if(status.getIsGameRunning())
		{
			HandleTurnChange(false);
			
			this.btnStartGame.setEnabled(false);
        	this.btnPlayBook.setEnabled(CanPlayBook());
        	this.btnEndTurn.setEnabled(true);
		}
		else
		{
			this.btnStartGame.setEnabled(this.CanStartGame);
        	this.btnPlayBook.setEnabled(false);
        	this.btnEndTurn.setEnabled(false);
		}

		ZFPlayer[] players = status.getPlayers();
		if(players != null)
        {
			int i = 0;
			for(ZFPlayer player: players)
            {
				// Update my hand
				if(player.getPlayerNumber() == this.networkManager.getMyPlayerNumber())
				{
					this.CurrentHand = player.getHand();
					this.playerPanel.removeAll();
					addCardsToPane(playerPanel, this.CurrentHand, true, true);
				}
				else // Update other players
				{
					String playerName = player.getPlayerName();
					JLayeredPane playerPane = (JLayeredPane)this.opponentPanel.getComponent(i);
					playerPane.setVisible(true);
					TitledBorder border = (TitledBorder)playerPane.getBorder();
			    	border.setTitle(playerName);   
					
			    	PlayerButton playerButton = (PlayerButton)this.goFishPlayerButtons.getComponent(i);
					playerButton.setPlayerNumber(player.getPlayerNumber());
			    	playerButton.setText(playerName);
					playerButton.setVisible(true);

					playerPane.removeAll();
					addCardsToPane(playerPane, player.getHand(), false, false);
					i++;
				}
            }
			lblCardCount.setText("Count: " + poolPanel.getComponentCount());
        }

	}

	private void ShowGameStatus(ZFStatus status)
	{
        System.out.println("STATUS CHANGE!");
        System.out.println("Status:" + status.getStatus());
        System.out.println("Current Player:" + status.getCurrentPlayer());
        System.out.println("Is Game Over?:" + status.getIsGameOver());
        System.out.println("Is Game Running?:" + status.getIsGameRunning());
        ZFPlayer[] players = status.getPlayers();
        System.out.println("Number of players:" + ((players == null) ? "null" : status.getPlayers().length));

        if(players != null)
        {
            for(ZFPlayer player: players)
            {
                System.out.println("\tName:" + player.getPlayerName());
                System.out.println("\tScore:" + player.getScore());
                System.out.println("\tCards in hand:" + player.getCardsInHand());
            }
        }
	}
	
	/**
	 * Determines if there are 4 or more of a kind in players hand
	 * @return
	 */
	private boolean CanPlayBook()
	{
		// TODO: Check hand for books
		return false;
	}
	
	private void HandleTurnChange(boolean IsTurn)
    {
    	if(IsTurn)
			System.out.println("My Turn!");
    	
    	btnReqPlayer1.setEnabled(IsTurn);
    	btnReqPlayer2.setEnabled(IsTurn);
    	btnReqPlayer3.setEnabled(IsTurn);
    	btnReqPlayer4.setEnabled(IsTurn);
    	btnReqPlayer5.setEnabled(IsTurn);
    	
    	if(IsTurn)
    	{
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
    	
    	myTrnTxt.setVisible(IsTurn);    	    
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
		ShowCardRequestResponse(response);

//		// TODO: This is ugly, refactor this reponse back to a status reponse?
//		List<ZFCard> hand = new ArrayList<ZFCard>();
//		
//		for(ZFCard card : this.CurrentHand)
//		{
//			hand.add(card);
//		}
//		
//		for(ZFCard card : response.getCards())
//		{
//			hand.add(card);			
//		}
//		
//		this.CurrentHand = hand.toArray(this.CurrentHand);
//		addCardsToPane(playerPanel, this.CurrentHand, true, true);		
	}
	
	private void ShowCardRequestResponse(ZFCardRequestResponse response)
	{
		System.out.println("CARD REQUEST RESPONSE");
		System.out.println("Result:" + response.getResult());
		System.out.println("Message:" + response.getMessage());
		System.out.println("Cards:");
		ZFCard[] cards = response.getCards();
		for(ZFCard card: cards)
		{
			System.out.println(card.toString());
		}
	}

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void OnNewMessage(String from, String msg) {
        txtOutput.setText(txtOutput.getText() + from + ": " + msg + "\n");
    }

    public void actionPerformed(ActionEvent ae)
    {
    	String action = ae.getActionCommand();
    	try {
	        if(action == SEND_ACTION)
	        {
	        	this.networkManager.sendMessage(txtInput.getText());
	            txtInput.setText("");
	        }
	        else if(action == START_GAME_ACTION)
	        {
	        	this.networkManager.startGame();

	        }
	        else if(action == END_TURN_ACTION)
	        {
	        	myTrnTxt.setVisible(false);
	        	this.networkManager.DoneWithTurn();

	        }	        
	        else if(action == REQ_1_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 1);
	        }
	        else if(action == REQ_2_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 2);
	        }
	        else if(action == REQ_3_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 3);
	        }
	        else if(action == REQ_4_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 4);
	        }
	        else if(action == REQ_5_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 5);
	        }
	        else if(action == REQ_6_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 6);
	        }
	        else if(action == REQ_7_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 7);
	        }
	        else if(action == REQ_8_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 8);	
	        }
	        else if(action == REQ_9_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 9);
	        }
	        else if(action == REQ_10_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 10);
	        }
	        else if(action == REQ_JACK_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 11);
	        }
	        else if(action == REQ_QUEEN_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 12);	
	        }
	        else if(action == REQ_KING_ACTION)
	        {
	        	this.networkManager.RequestCards(GetRequestPlayer(), 13);
	        }
    	}
        catch(Exception err) {
            HandleException(err);
        }
    }
    
    /**
     * Pulls the selected playernumber from the radio button group
     * @return
     */
    private int GetRequestPlayer()
    {    
    	int retval = -1;
    	for(Component cmp : goFishPlayerButtons.getComponents())
    	{
    		if(cmp.getClass() == PlayerButton.class)
    		{
    			PlayerButton btn = (PlayerButton)cmp;
    			if(btn.isSelected())
    			{
    				retval = btn.getPlayerNumber();
    			}    			    			
    		}    		
    	}
    	    	    	
    	return retval;
    }
        
    /**
     * Single spot to determine how to handle exceptions
     * @param err
     */
    private void HandleException(Exception err) {
    	err.printStackTrace();
    }
}

class PlayerButton extends JRadioButton
{
	private int playerNumber = -1;
	
	public PlayerButton(String text)
	{
		super(text);
	}
	
	public PlayerButton(String text, boolean selected)
	{
		super(text, selected);
	}
	
	public int getPlayerNumber()
	{
		return this.playerNumber;		
	}
	
	public void setPlayerNumber(int playerNumber)
	{
		this.playerNumber = playerNumber;
	}	
}
