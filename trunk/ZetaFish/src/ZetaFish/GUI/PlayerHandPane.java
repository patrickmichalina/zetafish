package ZetaFish.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ZetaFish.NetworkObjects.ZFCard;

public class PlayerHandPane extends PlayerPane
{		
	public PlayerHandPane(DeckOfCards deck)
	{
		super(deck);
		
		super.cardSpacing = 20;
		super.cardOffsetX = 20;
		super.cardOffsetY = 18;
	}		
	
	@Override
	public void addCards(ZFCard[] cards)
	{
		// sort cards low to high
		ArrayList<ZFCard> list = new ArrayList<ZFCard>(Arrays.asList(cards));
		Arrays.sort(cards);
		super.addCards(cards);
	}
}
