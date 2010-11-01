package ZetaFish.GUI;

import java.util.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Patrick
 */
public class DeckOfCards {
    public enum Suits {SPADES, HEARTS, DIAMONDS, CLUBS, JOKER};
	
    /**
     * 
     */
    private Map<Suits, ArrayList <Card>> deck = new HashMap<Suits, ArrayList <Card>>();
    
    // declare the memory space for each card
    private Card aceOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-ace.png"    )));
    private Card aceOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-ace.png"     )));
    private Card aceOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-ace.png"    )));
    private Card aceOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-ace.png"  )));
    
    private Card kingOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-king.png"   )));
    private Card kingOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-king.png"    )));
    private Card kingOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-king.png"   )));
    private Card kingOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-king.png" )));

    private Card queenOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-queen.png"  )));
    private Card queenOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-queen.png"   )));
    private Card queenOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-queen.png"  )));
    private Card queenOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-queen.png")));

    private Card jackOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-jack.png"   )));
    private Card jackOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-jack.png"    )));
    private Card jackOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-jack.png"   )));
    private Card jackOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-jack.png" )));

    private Card tenOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-10.png"     )));
    private Card tenOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-10.png"      )));
    private Card tenOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-10.png"     )));
    private Card tenOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-10.png"   )));

    private Card nineOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-9.png"      )));
    private Card nineOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-9.png"       )));
    private Card nineOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-9.png"      )));
    private Card nineOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-9.png"    )));

    private Card eightOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-8.png"      )));
    private Card eightOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-8.png"       )));
    private Card eightOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-8.png"      )));
    private Card eightOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-8.png"    )));

    private Card sevenOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-7.png"      )));
    private Card sevenOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-7.png"       )));
    private Card sevenOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-7.png"      )));
    private Card sevenOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-7.png"    )));

    private Card sixOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-6.png"      )));
    private Card sixOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-6.png"       )));
    private Card sixOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-6.png"      )));
    private Card sixOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-6.png"    )));

    private Card fiveOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-5.png"      )));
    private Card fiveOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-5.png"       )));
    private Card fiveOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-5.png"      )));
    private Card fiveOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-5.png"    )));

    private Card fourOfSpades     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-4.png"      )));
    private Card fourOfClubs      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-4.png"       )));
    private Card fourOfHearts     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-4.png"      )));
    private Card fourOfDiamonds   = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-4.png"    )));

    private Card threeOfSpades    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-3.png"      )));
    private Card threeOfClubs     = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-3.png"       )));
    private Card threeOfHearts    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-3.png"      )));
    private Card threeOfDiamonds  = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-3.png"    )));

    private Card twoOfSpades      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/spades-2.png"      )));
    private Card twoOfClubs       = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/clubs-2.png"       )));
    private Card twoOfHearts      = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/hearts-2.png"      )));
    private Card twoOfDiamonds    = new Card(new ImageIcon(getClass().getResource("/Resources/CardImages/diamonds-2.png"    )));

    /**
     *
     */
    public DeckOfCards() 
    {
    	// add a list for all the suits
    	for(Suits suit: Suits.values()) {
            deck.put(suit, new ArrayList <Card>());
    	}
    	
        // fill the array list with 52 defined cards
        deck.get(Suits.SPADES).add(aceOfSpades    );
        deck.get(Suits.CLUBS).add(aceOfClubs     );
        deck.get(Suits.HEARTS).add(aceOfHearts    );
        deck.get(Suits.DIAMONDS).add(aceOfDiamonds  );
        
        deck.get(Suits.SPADES).add(twoOfSpades    );
        deck.get(Suits.CLUBS).add(twoOfClubs     );
        deck.get(Suits.HEARTS).add(twoOfHearts    );
        deck.get(Suits.DIAMONDS).add(twoOfDiamonds  );
        
        deck.get(Suits.SPADES).add(threeOfSpades  );
        deck.get(Suits.CLUBS).add(threeOfClubs   );
        deck.get(Suits.HEARTS).add(threeOfHearts  );
        deck.get(Suits.DIAMONDS).add(threeOfDiamonds);

        deck.get(Suits.SPADES).add(fourOfSpades   );
        deck.get(Suits.CLUBS).add(fourOfClubs    );
        deck.get(Suits.HEARTS).add(fourOfHearts   );
        deck.get(Suits.DIAMONDS).add(fourOfDiamonds );
        
        deck.get(Suits.SPADES).add(fiveOfSpades   );
        deck.get(Suits.CLUBS).add(fiveOfClubs    );
        deck.get(Suits.HEARTS).add(fiveOfHearts   );
        deck.get(Suits.DIAMONDS).add(fiveOfDiamonds );

        deck.get(Suits.SPADES).add(sixOfSpades    );
        deck.get(Suits.CLUBS).add(sixOfClubs     );
        deck.get(Suits.HEARTS).add(sixOfHearts    );
        deck.get(Suits.DIAMONDS).add(sixOfDiamonds  );
        
        deck.get(Suits.SPADES).add(sevenOfSpades  );
        deck.get(Suits.CLUBS).add(sevenOfClubs   );
        deck.get(Suits.HEARTS).add(sevenOfHearts  );
        deck.get(Suits.DIAMONDS).add(sevenOfDiamonds);
        
        deck.get(Suits.SPADES).add(eightOfSpades  );
        deck.get(Suits.CLUBS).add(eightOfClubs   );
        deck.get(Suits.HEARTS).add(eightOfHearts  );
        deck.get(Suits.DIAMONDS).add(eightOfDiamonds);
        
        deck.get(Suits.SPADES).add(nineOfSpades   );
        deck.get(Suits.CLUBS).add(nineOfClubs    );
        deck.get(Suits.HEARTS).add(nineOfHearts   );
        deck.get(Suits.DIAMONDS).add(nineOfDiamonds );

        deck.get(Suits.SPADES).add(tenOfSpades    );
        deck.get(Suits.CLUBS).add(tenOfClubs     );
        deck.get(Suits.HEARTS).add(tenOfHearts    );
        deck.get(Suits.DIAMONDS).add(tenOfDiamonds  );

        deck.get(Suits.SPADES).add(jackOfSpades   );
        deck.get(Suits.CLUBS).add(jackOfClubs    );
        deck.get(Suits.HEARTS).add(jackOfHearts   );
        deck.get(Suits.DIAMONDS).add(jackOfDiamonds );
        
        deck.get(Suits.SPADES).add(queenOfSpades  );
        deck.get(Suits.CLUBS).add(queenOfClubs   );
        deck.get(Suits.HEARTS).add(queenOfHearts  );
        deck.get(Suits.DIAMONDS).add(queenOfDiamonds);
        
        deck.get(Suits.SPADES).add(kingOfSpades   );
        deck.get(Suits.CLUBS).add(kingOfClubs    );
        deck.get(Suits.HEARTS).add(kingOfHearts   );
        deck.get(Suits.DIAMONDS).add(kingOfDiamonds );       
    }
        
    public Card getCard(int value, Suits suit) {
    	Card card = null;
    	try {
            card = deck.get(suit).get(value - 1);
    	}
        catch(Exception err) {
    		err.printStackTrace();    	
    	}
    	return card;
    }
}