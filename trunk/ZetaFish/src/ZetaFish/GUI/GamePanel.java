package ZetaFish.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;

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
public class GamePanel extends Panel
{
    private Panel playerPanel   = new Panel(500,70);
    private Panel opponentPanel = new Panel(500,70);
    private Panel poolPanel     = new Panel(500,310);

    public GamePanel()
    {
        super();
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.setBorder(BorderFactory.createTitledBorder("Game Area"));
        this.setLayout(new BorderLayout());

        DeckOfCards deck = new DeckOfCards();

        /****** OPPONENT CARD AREA ****/
        this.add(opponentPanel, BorderLayout.PAGE_START);
        opponentPanel.add(deck.deck.get(0));

        /****** OCEAN OF CARDS    ****/
        this.add(poolPanel, BorderLayout.CENTER);
        poolPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        for(int i = 0; i < 52; i++)
        {
           poolPanel.add(deck.deck.get(i));

        }
        
        /****** PLAYER CARD AREA ****/
        this.add(playerPanel, BorderLayout.PAGE_END);
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