package ZetaFish.GUI;

import java.awt.Color;
import javax.swing.BorderFactory;

/**
 *
 * @author Patrick
 */
public class GamePanel extends Panel {

    /**
     * 
     */
    public GamePanel() {
        super();
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        DeckOfCards deck = new DeckOfCards();

        this.setBorder(BorderFactory.createTitledBorder("Game Area"));
        this.add(deck.deck.get(0));
    }
}
