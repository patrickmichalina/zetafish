package ZetaFish.Tests.NetworkObjects;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ZetaFish.NetworkObjects.ZFCard;
import ZetaFish.NetworkObjects.ZFDeck;

public class ZFDeckTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShuffle() {
		ZFDeck deck = new ZFDeck();
		deck.shuffle();
		
		assertTrue(deck.cardsLeft() == 52);					
	}
	

	@Test
	public void testDealCard() {
		ZFDeck deck = new ZFDeck();
		deck.shuffle();
		
		assertTrue(deck.cardsLeft() == 52);
		assertNotNull(deck.dealCard());		
	}

	@Test
	public void testReplaceCards() {
		fail("Not yet implemented");				
	}

	@Test
	public void testReplaceCard() {
		ZFDeck deck = new ZFDeck();
		deck.shuffle();
		
		assertTrue(deck.cardsLeft() == 52);
		
		ZFCard card = deck.dealCard();
		assertNotNull(card);
		
		assertTrue(deck.cardsLeft() == 51);
		
		deck.replaceCard(card);
		assertTrue(deck.cardsLeft() == 52);
	}

}
