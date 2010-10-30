package ZetaFish.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ZetaFish.Interfaces.*;
import ZetaFish.NetworkObjects.ZFCard;
import ZetaFish.NetworkObjects.ZFCardRequestResponse;
import ZetaFish.NetworkObjects.ZFPlayer;
import ZetaFish.NetworkObjects.ZFStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

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
    private JPanel       goFishButtons     = new JPanel();
    private JPanel       panelChat         = new JPanel();
    private JPanel       panelButtons      = new JPanel();
    private JButton      btn1              = new JButton("1");
    private JButton      btn2              = new JButton("2");
    private JButton      btn3              = new JButton("3");
    private JButton      btn4              = new JButton("4");
    private JButton      btn5              = new JButton("5");
    private JButton      btn6              = new JButton("6");
    private JButton      btn7              = new JButton("7");
    private JButton      btn8              = new JButton("8");
    private JButton      btn9              = new JButton("9");
    private JButton      btn10             = new JButton("10");
    private JButton      btnjack           = new JButton("Jack");
    private JButton      btnqueen          = new JButton("Queen");
    private JButton      btnking           = new JButton("King");
    private JButton      btnace            = new JButton("Ace");
    private JButton      btnSend           = new JButton("Send");
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

    private INetworkManager networkManager = null;

    private DeckOfCards deck = null;

    private String serverName = "";
    private String playerName = "";

    public GamePanel(INetworkManager networkManager, String serverName, String playerName) throws Exception{
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
        setNetworkListeners();

        this.serverName = serverName;
        this.playerName = playerName;
        this.networkManager.openConnection(this.serverName, this.playerName, "My Password");

        testDrawCards();
    }

    private void testDrawCards()
    {
        //draw cards in opponents hands

        //draw booked sets

        //draw draw ocean

        //draw hand



      /*********************************************************************/
      /*                         Ocean of Cards                            */
      /*********************************************************************/

          // this is just for testing at the moment!
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
                      lblCardCount.setText("Count: " + i);
          }



    }

    private void setComponents() {
        //All these components are added in a flowlayout. The order DOES matter.
        this.add(infoPanel);
        this.add(opponentPanel);
        this.add(bookPanel);
        this.add(poolPanel);
        this.add(playerPanel);
        this.add(goFishButtons);
        this.add(panelChat);
        this.add(panelButtons);

        opponentPanel.add(opponentSubPanel1);
        opponentPanel.add(opponentSubPanel2);
        opponentPanel.add(opponentSubPanel3);
        opponentPanel.add(opponentSubPanel4);
        opponentPanel.add(opponentSubPanel5);

        goFishButtons.add(myTrnTxt);
        goFishButtons.add(btn1);
        goFishButtons.add(btn2);
        goFishButtons.add(btn3);
        goFishButtons.add(btn4);
        goFishButtons.add(btn5);
        goFishButtons.add(btn6);
        goFishButtons.add(btn7);
        goFishButtons.add(btn8);
        goFishButtons.add(btn9);
        goFishButtons.add(btn10);
        goFishButtons.add(btnjack);
        goFishButtons.add(btnqueen);
        goFishButtons.add(btnking);
        goFishButtons.add(btnace);

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

        panelButtons.add(btnPlayBook);
        panelButtons.add(btnStartGame);

        btnPlayBook.setEnabled(false);

        txtOutput.setLineWrap(true);
    }

    private void setBorders() {
        opponentSubPanel1.setBorder(BorderFactory.createTitledBorder("Player 2"));
        opponentSubPanel2.setBorder(BorderFactory.createTitledBorder("Player 3"));
        opponentSubPanel3.setBorder(BorderFactory.createTitledBorder("Player 4"));
        opponentSubPanel4.setBorder(BorderFactory.createTitledBorder("Player 5"));
        opponentSubPanel5.setBorder(BorderFactory.createTitledBorder("Player 6"));
        poolPanel.setBorder(        BorderFactory.createTitledBorder("Ocean"));
        bookPanel.setBorder(        BorderFactory.createTitledBorder("Books"));
        playerPanel.setBorder(      BorderFactory.createTitledBorder("Your Hand"));
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
        btnace.setPreferredSize(  btnSize);


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
        goFishButtons.setOpaque(false);
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
    private void addCardsToPane(JLayeredPane pane, ZFCard[] cards, boolean isHeld)
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

    		card.setShown(isHeld);

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
			this.btnStartGame.setEnabled(false);
        	this.btnPlayBook.setEnabled(true);
		}
		else
		{
			this.btnStartGame.setEnabled(true);
        	this.btnPlayBook.setEnabled(false);
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
					this.playerPanel.removeAll();
					addCardsToPane(playerPanel, player.getHand(), true);
				}
				else // Update other players
				{
					JLayeredPane playerPane = (JLayeredPane)this.opponentPanel.getComponent(i);
					playerPane.removeAll();
					addCardsToPane(playerPane, player.getHand(), false);
					i++;
				}

            }
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

	@Override
	public void OnGameTurn()
	{
                myTrnTxt.setVisible(true);
               System.out.println("My Turn!");
	}

	@Override
	public void OnCardRequestResponse(ZFCardRequestResponse response)
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