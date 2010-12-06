package ZetaFish.GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import ZetaFish.NetworkObjects.ZFCard;
import ZetaFish.NetworkObjects.ZFCardRequestResponse;
import ZetaFish.NetworkObjects.ZFPlayer;
import ZetaFish.NetworkObjects.ZFStatus;

/**
 * A set of utilities for displaying status, playing sounds, etc.
 */
public class GUIUtilities 
{
	/**
	 * Show the contents of the ZFCardRequestResponse network object to System.out.
	 * @param response ZFCardRequestResponse object
	 */
	public synchronized static void ShowCardRequestResponse(ZFCardRequestResponse response)
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
	
	/**
	 * Show the contents of the ZFStatus network object to System.out.
	 * @param status ZFStatus object
	 */
	public synchronized static void ShowGameStatus(ZFStatus status)
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
            	if(player != null)
            	{
	                System.out.println("\tName:" + player.getPlayerName());
	                System.out.println("\tScore:" + player.getScore());
	                System.out.println("\tCards in hand:" + player.getCardsInHand());
            	}
            }
        }
	}
	
	/**
	 * Plays a sound from contained in the embedded resources
	 * @param <T> Calling type.
	 * @param link Resource name.
	 * @param cls Instance of T.
	 */
	public synchronized static <T> void playSound(final String link, Class<T> cls) 
	{
		try {
			javax.sound.sampled.Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(cls.getResourceAsStream("/Resources/Sounds/" + link));
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}	
}
