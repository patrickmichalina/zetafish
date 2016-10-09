package ZetaFish.Interfaces;

/**
 * Interface to be implemented by those interested in the button push
 * events published by the CardSelectorPane. 
 */
public interface ICardButtonPush {
	 /** 
	   *  Notifies of a card button push 
	   */
	  public void OnCardButtonSelect(int cardValue);
}
