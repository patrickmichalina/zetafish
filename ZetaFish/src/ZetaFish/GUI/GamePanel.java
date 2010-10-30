package ZetaFish.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ZetaFish.Interfaces.*;
import ZetaFish.NetworkObjects.ZFCard;
import ZetaFish.NetworkObjects.ZFCardRequestResponse;
import ZetaFish.NetworkObjects.ZFPlayer;
import ZetaFish.NetworkObjects.ZFStatus;


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
public class GamePanel extends Panel implements IStatusListener, ITurnListener, ICardRequestResponseListener {
    private JLayeredPane playerPanel       = new JLayeredPane();
    private JLayeredPane poolPanel         = new JLayeredPane();
    private JLayeredPane bookPanel         = new JLayeredPane();
    private JLayeredPane opponentPanel     = new JLayeredPane();
    private JLayeredPane opponentSubPanel1 = new JLayeredPane();
    private JLayeredPane opponentSubPanel2 = new JLayeredPane();
    private JLayeredPane opponentSubPanel3 = new JLayeredPane();
    private JLayeredPane opponentSubPanel4 = new JLayeredPane();
    private JLayeredPane opponentSubPanel5 = new JLayeredPane();
    private JPanel       goFishButtons     = new JPanel();
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
    private Dimension    btnSize           = new Dimension(50,35);
    
    private INetworkManager networkManager = null;

    public GamePanel(INetworkManager networkManager) {
        super();
        this.setLayout(new FlowLayout(1,0,0));

        this.networkManager = networkManager;
        
        this.networkManager.addStatusListener(this);
        this.networkManager.addTurnListener(this);
        this.networkManager.addCardRequestResponseListener(this);
                
                
        //just for show at the moment!
        DeckOfCards deck = new DeckOfCards();        
        
        setComponents();
        setLayouts();
        setBorders();
        setComponentDimensions();
        setSeeThrough();

        /*********************************************************************/
        /*                         Ocean of Cards                            */
        /*********************************************************************/
        
            // this is just for testing at the moment!
            for(int i = 0; i < 52; i++) {
                
                poolPanel.add(deck.deck.get(i), new Integer(i));
                deck.deck.get(i).setIcon(deck.deck.get(i).getImage());
                deck.deck.get(i).setBounds((i * 18) + 30, 45,  60, 60);
            }
    }

    private void setComponents() {
        this.add(opponentPanel);
        this.add(bookPanel);
        this.add(poolPanel);
        this.add(playerPanel);
        this.add(goFishButtons);

        opponentPanel.add(opponentSubPanel1);
        opponentPanel.add(opponentSubPanel2);
        opponentPanel.add(opponentSubPanel3);
        opponentPanel.add(opponentSubPanel4);
        opponentPanel.add(opponentSubPanel5);

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
    }

    private void setComponentDimensions() {
        playerPanel.setPreferredSize(      new Dimension(1024,100));
        poolPanel.setPreferredSize(        new Dimension(1024,150));
        bookPanel.setPreferredSize(        new Dimension(1024,200));
        opponentPanel.setPreferredSize(    new Dimension(1024,100));
        opponentSubPanel1.setPreferredSize(new Dimension(205,100) );
        opponentSubPanel2.setPreferredSize(new Dimension(205,100) );
        opponentSubPanel3.setPreferredSize(new Dimension(205,100) );
        opponentSubPanel4.setPreferredSize(new Dimension(205,100) );
        opponentSubPanel5.setPreferredSize(new Dimension(204,100) );

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
    }

    private void setSeeThrough() {
        playerPanel.setOpaque(  false);
        poolPanel.setOpaque(    false);
        bookPanel.setOpaque(    false);
        opponentPanel.setOpaque(false);
        goFishButtons.setOpaque(false);
    }

    private void setLayouts() {
        opponentPanel.setLayout(new FlowLayout(0,0,0));
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

	@Override
	public void OnGameStausChange(ZFStatus status) 
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
}