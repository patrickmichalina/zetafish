/** 
 *  Summary: DeckDisplayPanel class satisfies the requirments listed in point 3.3.2 of the Requirements Specifictation document. Class provides a means to manage the card images shown on the GUI. Provides 7-9 card slots (as panels) in the screen depending on the number of players, and the protocol dictated by the gameengine numbered index 1 to 9 in an array of panels.  Implements runnable, runs on its own thread. 
 *  Imports: java.util, java.io., java.awt, CardLibrary.java
 *  Exports: All deck display mechanisms including, show(card#,slot), remove(card#, slot), resetCards().
 */
public class DeckDisplayPanel extends JPanel implements Runnable {
  /* {author=Zeta Dragons , version=1.0, see=					}*/


    
    public CardLibrary myCardLibrary;
    

}