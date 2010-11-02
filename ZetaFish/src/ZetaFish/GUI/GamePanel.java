package ZetaFish.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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
public class GamePanel extends JPanel implements IStatusListener, ITurnListener, ICardRequestResponseListener, Runnable, IChatListener, ActionListener, MouseListener {
    private JLayeredPane panelPlayer       = new JLayeredPane();
    private JLayeredPane panelPool         = new JLayeredPane();
    private JLayeredPane panelBook         = new JLayeredPane();
    
    private JLayeredPane panelOpponent     = new JLayeredPane();
//    private JLayeredPane panelOpponentSub1 = new JLayeredPane();
//    private JLayeredPane panelOpponentSub2 = new JLayeredPane();
//    private JLayeredPane panelOpponentSub3 = new JLayeredPane();
//    private JLayeredPane panelOpponentSub4 = new JLayeredPane();
//    private JLayeredPane panelOpponentSub5 = new JLayeredPane();
    private PlayerPane panelOpponentSub1 = new PlayerPane();
    private PlayerPane panelOpponentSub2 = new PlayerPane();
    private PlayerPane panelOpponentSub3 = new PlayerPane();
    private PlayerPane panelOpponentSub4 = new PlayerPane();
    private PlayerPane panelOpponentSub5 = new PlayerPane();
   
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
    private JPanel       panelInfo         = new JPanel();
    private JPanel       panelPlayerButtons= new JPanel();
    private JPanel       panelCardButtons  = new JPanel();
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
    private JButton      btnSend           = new JButton("Send");
    private JButton      btnEndTurn        = new JButton("End Turn");    
    private JButton      btnPlayBook       = new JButton("Play Book");    
    private JButton      btnStartGame      = new JButton("Start Game");
    private ButtonGroup  reqPlayerGroup    = new ButtonGroup();
    private PlayerButton btnReqPlayer1     = new PlayerButton("Player1", true);
    private PlayerButton btnReqPlayer2     = new PlayerButton("Player2");
    private PlayerButton btnReqPlayer3     = new PlayerButton("Player3");
    private PlayerButton btnReqPlayer4     = new PlayerButton("Player4");
    private PlayerButton btnReqPlayer5     = new PlayerButton("Player5");
    private JLabel       lblPool           = new JLabel("Pool: ");
    private JLabel       lblBook           = new JLabel("Book: ");
    private JLabel       lblWins           = new JLabel("Wins: ");
    private JLabel       lblLoss           = new JLabel("Losses: ");
    private JLabel       lblStatus         = new JLabel("Network Status: ");
    private JLabel       lblMyTurn         = new JLabel("My Turn!");
    private JLabel       lblCardCount      = new JLabel("Count: ");
    private JTextArea    txtOutput         = new JTextArea(5,0);
    private JTextField   txtInput          = new JTextField();
    private Dimension    dimButtonSize     = new Dimension(50,35);

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

    private String serverName = "";
    private String playerName = "";

    private INetworkManager networkManager = null;

    private DeckOfCards deck = null;

    private boolean CanStartGame = true;
    
    private ZFCard[] CurrentHand = null;

    public GamePanel(INetworkManager networkManager, String serverName, String playerName, boolean CanStartGame) throws Exception {
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
        setMouseListerner();

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
        			card.setBounds((i++ * 18) + 30, 20,  60, 60);
        		}
      		}   
      	}
        lblCardCount.setText("Count: " + i);    	
    }

    private void setComponents() {
        //All these components are added in a flowlayout. The order DOES matter.
        this.add(panelInfo);
        this.add(panelOpponent);
        this.add(panelBook);
        this.add(panelPool);
        this.add(panelPlayer);
        this.add(panelPlayerButtons);
        this.add(panelCardButtons);
        this.add(panelChat);
        this.add(panelButtons);

        panelOpponent.add(panelOpponentSub1);
        panelOpponent.add(panelOpponentSub2);
        panelOpponent.add(panelOpponentSub3);
        panelOpponent.add(panelOpponentSub4);
        panelOpponent.add(panelOpponentSub5);
        
        panelOpponentSub1.setVisible(false);
        panelOpponentSub2.setVisible(false);
        panelOpponentSub3.setVisible(false);
        panelOpponentSub4.setVisible(false);
        panelOpponentSub5.setVisible(false);
        
        reqPlayerGroup.add(btnReqPlayer1);
        reqPlayerGroup.add(btnReqPlayer2);
        reqPlayerGroup.add(btnReqPlayer3);
        reqPlayerGroup.add(btnReqPlayer4);
        reqPlayerGroup.add(btnReqPlayer5);
        
        panelPlayerButtons.add(btnReqPlayer1);
        panelPlayerButtons.add(btnReqPlayer2);
        panelPlayerButtons.add(btnReqPlayer3);
        panelPlayerButtons.add(btnReqPlayer4);
        panelPlayerButtons.add(btnReqPlayer5);
        
        btnReqPlayer1.setVisible(false);
        btnReqPlayer2.setVisible(false);
        btnReqPlayer3.setVisible(false);
        btnReqPlayer4.setVisible(false);
        btnReqPlayer5.setVisible(false);
        
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
        
        panelInfo.add(lblPool);
        panelInfo.add(lblBook);
        panelInfo.add(lblWins);
        panelInfo.add(lblLoss);
        panelInfo.add(lblStatus);

        panelBook.add(panelBookAce);
        panelBook.add(panelBookKing);
        panelBook.add(panelBookQueen);
        panelBook.add(panelBookJack);
        panelBook.add(panelBook10);
        panelBook.add(panelBook9);
        panelBook.add(panelBook8);
        panelBook.add(panelBook7);
        panelBook.add(panelBook6);
        panelBook.add(panelBook5);
        panelBook.add(panelBook4);
        panelBook.add(panelBook3);
        panelBook.add(panelBook2);

        panelPool.add(lblCardCount);

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
    	
    	border = (TitledBorder)this.panelPlayerButtons.getBorder();
    	border.setTitleColor(DefaultForeColor);
    	
    	border = (TitledBorder)this.panelCardButtons.getBorder();
    	border.setTitleColor(DefaultForeColor);
    	
    	this.lblCardCount.setForeground(DefaultForeColor);    	
    	this.lblMyTurn.setForeground(DefaultForeColor);
    }
    

    private void setBorders() {
        panelOpponentSub1.setBorder(BorderFactory.createTitledBorder("Player 2" ));
        panelOpponentSub2.setBorder(BorderFactory.createTitledBorder("Player 3"));
        panelOpponentSub3.setBorder(BorderFactory.createTitledBorder("Player 4"));
        panelOpponentSub4.setBorder(BorderFactory.createTitledBorder("Player 5"));
        panelOpponentSub5.setBorder(BorderFactory.createTitledBorder("Player 6"));
        panelPool.setBorder(        BorderFactory.createTitledBorder("Ocean"));
        panelBook.setBorder(        BorderFactory.createTitledBorder("Books"));
        panelPlayer.setBorder(      BorderFactory.createTitledBorder("Your Hand"));
        
        panelPlayerButtons.setBorder(	BorderFactory.createTitledBorder("Request from Player"));
        panelCardButtons.setBorder(	BorderFactory.createTitledBorder("Request Cards"));
        
        panelInfo.setBorder(        BorderFactory.createLineBorder(Color.black, 2));
        
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
        panelInfo.setPreferredSize(          new Dimension(1024, 30));
        panelBook.setPreferredSize(        new Dimension(1024,200));
        panelPool.setPreferredSize(        new Dimension(1024,100));
        panelPlayer.setPreferredSize(      new Dimension(1024,100));

        panelPlayerButtons.setPreferredSize(new Dimension(1024,60));
        panelCardButtons.setPreferredSize(  new Dimension(1024,60));
        
        panelChat.setPreferredSize(          new Dimension( 890,110));
        panelButtons.setPreferredSize(       new Dimension( 124,130));

        panelOpponent.setPreferredSize(    new Dimension(1024,100));
        panelOpponentSub1.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub2.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub3.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub4.setPreferredSize(new Dimension( 205,100));
        panelOpponentSub5.setPreferredSize(new Dimension( 204,100));

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
        panelInfo.setOpaque(    false);
        panelPlayer.setOpaque(  false);
        panelPool.setOpaque(    false);
        panelBook.setOpaque(    false);
        panelOpponent.setOpaque(false);
        panelPlayerButtons.setOpaque(false);
        panelCardButtons.setOpaque(false);
        panelButtons.setOpaque( false);
    }

    private void setLayouts() {
        panelOpponent.setLayout(new FlowLayout(0,0,0));
        panelChat.setLayout(new BorderLayout(3,3));
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

    private void setMouseListerner() {
        panelOpponentSub1.addMouseListener(this);
        panelOpponentSub2.addMouseListener(this);
        panelOpponentSub3.addMouseListener(this);
        panelOpponentSub4.addMouseListener(this);
        panelOpponentSub5.addMouseListener(this);
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
        return panelPool;
    }

    public JLayeredPane getPlayerPanel() {
        return panelPlayer;
    }

    public JLayeredPane getOpponentPanel() {
        return panelOpponent;
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
    	int CardCount = cards.length;
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
                card.setBounds((i * 20) + 20, 18,  45, 65);
            }
            else {
                card.setBounds((i * 8) + 15, 20,  45, 65);
            }

            pane.add(card, i);
            pane.setLayer(card, i);
    		i++;               
    	}    	    	    
//    	this.repaint();
//        pane.repaint();
        this.validate();
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
					this.panelPlayer.removeAll();
					addCardsToPane(panelPlayer, this.CurrentHand, true, true);
				}
				else // Update other players
				{
					String playerName = player.getPlayerName();
					PlayerPane playerPane = (PlayerPane)this.panelOpponent.getComponent(i);
					playerPane.setVisible(true);
					playerPane.setPlayerNumber(player.getPlayerNumber());
					TitledBorder border = (TitledBorder)playerPane.getBorder();
			    	border.setTitle(playerName);   
					
			    	PlayerButton playerButton = (PlayerButton)this.panelPlayerButtons.getComponent(i);
					playerButton.setPlayerNumber(player.getPlayerNumber());
			    	playerButton.setText(playerName);
					playerButton.setVisible(true);

					playerPane.removeAll();
					addCardsToPane(playerPane, player.getHand(), false, false);
					i++;
				}
            }
			lblCardCount.setText("Count: " + panelPool.getComponentCount());
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
//		addCardsToPane(panelPlayer, this.CurrentHand, true, true);
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
	        	lblMyTurn.setVisible(false);
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
    	for(Component cmp : panelPlayerButtons.getComponents())
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
    	
    	//retval = whichComponentWasClicked();
    	
    	return retval;
    }

    public int whichComponentWasClicked() {
//        int x = 0;
//
//        String name = paneWasClicked.getContainerListeners().toString();
//
//        System.out.println(name);
//
//        if(name.equalsIgnoreCase("paneOpponentSub1")) {
//            x = 0;
//        }
//
//
//        return x;
    	return clickedPlayerPaneNumber;
    }
        
    /**
     * Single spot to determine how to handle exceptions
     * @param err
     */
    private void HandleException(Exception err) {
    	err.printStackTrace();
    }

    //Test code for mouse hover over opponent hands to choose who to fish from
    @Override
    public void mouseClicked(MouseEvent me) {
            System.out.println(me.toString());
    }

    //private JLayeredPane paneWasClicked;
    int clickedPlayerPaneNumber = -1;

    @Override
    public void mousePressed(MouseEvent me) {
    	PlayerPane clickedPane = (PlayerPane) me.getSource();
    	//paneWasClicked = clickedPane;
    	clickedPlayerPaneNumber = clickedPane.getPlayerNumber();
    	
    	for(Component cmp : this.panelOpponent.getComponents())
    	{
    		PlayerPane pane = (PlayerPane)cmp;
    		if(pane == clickedPane)
    		{
    			pane.setBackground(Color.GREEN);
    			pane.setOpaque(true);
    		}
    		else
    		{
    			pane.setBackground(Color.WHITE);
    			pane.setOpaque(false);
    		}
    	}
        
//        if (clickedPane == panelOpponentSub1) {
//            paneWasClicked = panelOpponentSub1;
//
//            panelOpponentSub2.setBackground(Color.WHITE);
//            panelOpponentSub3.setBackground(Color.WHITE);
//            panelOpponentSub4.setBackground(Color.WHITE);
//            panelOpponentSub5.setBackground(Color.WHITE);
//
//            panelOpponentSub2.setOpaque(false);
//        }
//        
//        if (clickedPane == panelOpponentSub2) {
//            paneWasClicked = panelOpponentSub2;
//
//            panelOpponentSub1.setBackground(Color.WHITE);
//            panelOpponentSub3.setBackground(Color.WHITE);
//            panelOpponentSub4.setBackground(Color.WHITE);
//            panelOpponentSub5.setBackground(Color.WHITE);
//
//            panelOpponentSub1.setOpaque(false);
//
//        }

        this.repaint();

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent me) {
//        JLayeredPane enteredPane = (JLayeredPane)me.getSource();
//
//        if (enteredPane == panelOpponentSub1) {
//            enteredPane.setBackground(Color.GREEN);
//            enteredPane.setOpaque(true);
//        }
//        
//        if (enteredPane == panelOpponentSub2) {
//            enteredPane.setBackground(Color.GREEN);
//            enteredPane.setOpaque(true);
//        }
//
//        this.repaint();
    }

    
    @Override
    public void mouseExited(MouseEvent me) {
//       JLayeredPane enteredPane = (JLayeredPane) me.getSource();
//
//        if (enteredPane == panelOpponentSub1 && enteredPane != paneWasClicked) {
//            //System.out.println(me.getSource().toString());
//            enteredPane.setBackground(Color.WHITE);
//            enteredPane.setOpaque(false);
//        }
//
//       if (enteredPane == panelOpponentSub2 && enteredPane != paneWasClicked) {
//            //System.out.println(me.getSource().toString());
//            enteredPane.setBackground(Color.WHITE);
//            enteredPane.setOpaque(false);
//        }
//
//       this.repaint();
    }

}


/**
 * Custom pane to hold player info
 *
 */
class PlayerPane extends JLayeredPane
{
	private int playerNumber = -1;
	
	public PlayerPane()
	{
		super();
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

/**
 *  Custom button to hold player info 
 *
 */
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
