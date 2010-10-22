package ZetaFish.GUI;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Patrick
 */
public class DeckOfCards {

    /**
     * 
     */
    public ArrayList <Card> deck = new ArrayList();

    // declare the memory space for each card
    private Card aceOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-ace.png"    )), 11);
    private Card aceOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-ace.png"     )), 11);
    private Card aceOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-ace.png"    )), 11);
    private Card aceOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-ace.png"  )), 11);
    
    private Card kingOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-king.png"   )), 10);
    private Card kingOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-king.png"    )), 10);
    private Card kingOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-king.png"   )), 10);
    private Card kingOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-king.png" )), 10);

    private Card queenOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-queen.png"  )), 10);
    private Card queenOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-queen.png"   )), 10);
    private Card queenOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-queen.png"  )), 10);
    private Card queenOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-queen.png")), 10);

    private Card jackOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-jack.png"   )), 10);
    private Card jackOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-jack.png"    )), 10);
    private Card jackOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-jack.png"   )), 10);
    private Card jackOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-jack.png" )), 10);

    private Card tenOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-10.png"     )), 10);
    private Card tenOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-10.png"      )), 10);
    private Card tenOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-10.png"     )), 10);
    private Card tenOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-10.png"   )), 10);

    private Card nineOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-9.png"      )),  9);
    private Card nineOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-9.png"       )),  9);
    private Card nineOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-9.png"      )),  9);
    private Card nineOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-9.png"    )),  9);

    private Card eightOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-8.png"      )),  8);
    private Card eightOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-8.png"       )),  8);
    private Card eightOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-8.png"      )),  8);
    private Card eightOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-8.png"    )),  8);

    private Card sevenOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-7.png"      )),  7);
    private Card sevenOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-7.png"       )),  7);
    private Card sevenOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-7.png"      )),  7);
    private Card sevenOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-7.png"    )),  7);

    private Card sixOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-6.png"      )),  6);
    private Card sixOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-6.png"       )),  6);
    private Card sixOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-6.png"      )),  6);
    private Card sixOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-6.png"    )),  6);

    private Card fiveOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-5.png"      )),  5);
    private Card fiveOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-5.png"       )),  5);
    private Card fiveOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-5.png"      )),  5);
    private Card fiveOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-5.png"    )),  5);

    private Card fourOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-4.png"      )),  4);
    private Card fourOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-4.png"       )),  4);
    private Card fourOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-4.png"      )),  4);
    private Card fourOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-4.png"    )),  4);

    private Card threeOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-3.png"      )),  3);
    private Card threeOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-3.png"       )),  3);
    private Card threeOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-3.png"      )),  3);
    private Card threeOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-3.png"    )),  3);

    private Card twoOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-2.png"      )),  2);
    private Card twoOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-2.png"       )),  2);
    private Card twoOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-2.png"      )),  2);
    private Card twoOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-2.png"    )),  2);

    /**
     *
     */
    public DeckOfCards() {

        // fill the array list with 52 defined cards
        deck.add(aceOfSpades    );
        deck.add(aceOfClubs     );
        deck.add(aceOfHearts    );
        deck.add(aceOfDiamonds  );

        deck.add(kingOfSpades   );
        deck.add(kingOfClubs    );
        deck.add(kingOfHearts   );
        deck.add(kingOfDiamonds );

        deck.add(queenOfSpades  );
        deck.add(queenOfClubs   );
        deck.add(queenOfHearts  );
        deck.add(queenOfDiamonds);

        deck.add(jackOfSpades   );
        deck.add(jackOfClubs    );
        deck.add(jackOfHearts   );
        deck.add(jackOfDiamonds );

        deck.add(tenOfSpades    );
        deck.add(tenOfClubs     );
        deck.add(tenOfHearts    );
        deck.add(tenOfDiamonds  );

        deck.add(nineOfSpades   );
        deck.add(nineOfClubs    );
        deck.add(nineOfHearts   );
        deck.add(nineOfDiamonds );

        deck.add(eightOfSpades  );
        deck.add(eightOfClubs   );
        deck.add(eightOfHearts  );
        deck.add(eightOfDiamonds);

        deck.add(sevenOfSpades  );
        deck.add(sevenOfClubs   );
        deck.add(sevenOfHearts  );
        deck.add(sevenOfDiamonds);

        deck.add(sixOfSpades    );
        deck.add(sixOfClubs     );
        deck.add(sixOfHearts    );
        deck.add(sixOfDiamonds  );

        deck.add(fiveOfSpades   );
        deck.add(fiveOfClubs    );
        deck.add(fiveOfHearts   );
        deck.add(fiveOfDiamonds );

        deck.add(fourOfSpades   );
        deck.add(fourOfClubs    );
        deck.add(fourOfHearts   );
        deck.add(fourOfDiamonds );

        deck.add(threeOfSpades  );
        deck.add(threeOfClubs   );
        deck.add(threeOfHearts  );
        deck.add(threeOfDiamonds);

        deck.add(twoOfSpades    );
        deck.add(twoOfClubs     );
        deck.add(twoOfHearts    );
        deck.add(twoOfDiamonds  );
    }
}