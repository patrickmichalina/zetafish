package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JList;

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
 */
public class GamePanel extends Panel {
    private Panel playerPanel   = new Panel(1024,100);
    private Panel opponentPanel = new Panel(1024,70);
    private Panel poolPanel     = new Panel(600,330);

    private Panel turnPanel     = new Panel(200,400);
    private Panel bookPanel     = new Panel(200,400);

    private JList playerList    = new JList();
    private JList bookList      = new JList();

    public GamePanel() {
        super();
        this.setLayout(new BorderLayout());

        //just for show at the moment!
        DeckOfCards deck = new DeckOfCards();

        /*********************************************************************/
        /*                      Opponent Card Area                           */
        /*********************************************************************/
        this.add(opponentPanel, BorderLayout.PAGE_START);

        /*********************************************************************/
        /*                           Turn Area                               */
        /*********************************************************************/
        this.add(turnPanel, BorderLayout.LINE_START);
        turnPanel.setBorder(BorderFactory.createTitledBorder("Current Turn"));

        /*********************************************************************/
        /*                         Ocean of Cards                            */
        /*********************************************************************/
        this.add(poolPanel, BorderLayout.CENTER);
        poolPanel.setBorder(BorderFactory.createTitledBorder("Ocean of Cards"));

        // this is just for show at the moment!
        for(int i = 0; i < 52; i++) {
           poolPanel.add(deck.deck.get(i));
        }

        /*********************************************************************/
        /*                             Books                                 */
        /*********************************************************************/
        this.add(bookPanel, BorderLayout.LINE_END);
        bookPanel.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        bookPanel.setBorder(BorderFactory.createTitledBorder("Books"));

        /*********************************************************************/
        /*                      Player's Hand                                */
        /*********************************************************************/
        this.add(playerPanel, BorderLayout.PAGE_END);
        playerPanel.setBorder(BorderFactory.createTitledBorder("Your Hand"));
        playerPanel.add(deck.deck.get(1));
    }

    /**
     * Below are the access methods that can be used to add or remove cards
     * between each area. So when a player draws a card from the pool, it should
     * be updated by removing a card from the pool and adding it into the players
     * hand. Likewise, when a player gets 4 of a kind, those 4 should be removed
     * and the score should be adjust in the title bar.
     *
     */
    public Panel getPoolPanel()
    {
        return poolPanel;
    }

    public Panel getPlayerPanel()
    {
        return playerPanel;
    }

    public Panel getOpponentPanel()
    {
        return opponentPanel;
    }
}