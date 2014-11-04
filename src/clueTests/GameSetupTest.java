package clueTests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTest {

	private static ClueGame game;
	private static Card profPlum;
	private static Card rope;
	private static Card hall;
	private static Player human;
	private static Player ai0;
	private static Player ai1;

	@BeforeClass
	public static void setUp() {
		profPlum = new Card("Professor Plum", CardType.PERSON);
		rope = new Card("Rope", CardType.WEAPON);
		hall = new Card("Hall", CardType.ROOM);
	}

	@Before
	public void setUpBefore() {
		game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt", "PlayerCards.txt");
		game.loadConfigFiles();
		game.getBoard().calcAdjacencies();

		human = new HumanPlayer("Professor Plum", game.convertColor("purple"), 0, 6);
		ai0 = new ComputerPlayer("Mrs. White", game.convertColor("white"), 0, 11);
		ai1 = new ComputerPlayer("Mr. Green", game.convertColor("green"), 0, 16);
	}

	@Test
	public void testLoadingPeopleFromFile() {
		// Check that the number of players is correct
		Assert.assertEquals(6, game.players.size());

		// Check that the human player and two computer players have the correct name, color, and
		// starting location.
		Assert.assertEquals(human, game.players.get(0));
		Assert.assertEquals(ai0, game.players.get(1));
		Assert.assertEquals(ai1, game.players.get(2));
	}

	@Test
	public void testLoadingCardsFromFile() {
		// We check that the deck size is correct
		List<Card> deck = game.getDeck();
		Assert.assertEquals(23, deck.size());

		// We then check to make sure the correct number of weapons,
		// people, and room cards are contained within the deck.
		int numWeps = 0;
		int numRooms = 0;
		int numChars = 0;
		for (Card c : deck) {
			switch (c.type) {
			case WEAPON:
				numWeps++;
				break;
			case PERSON:
				numChars++;
				break;
			case ROOM:
				numRooms++;
				break;
			}
		}

		Assert.assertEquals(6, numWeps);
		Assert.assertEquals(6, numChars);
		Assert.assertEquals(11, numRooms);

		// We then check one weapon, room, and person to make sure they are contained within the
		// deck.
		Assert.assertEquals(true, deck.contains(profPlum));
		Assert.assertEquals(true, deck.contains(rope));
		Assert.assertEquals(true, deck.contains(hall));
	}

	@Test
	public void testDeal() {
		int max = (int) Math.ceil(game.getDeck().size() / (game.players.size() - 1));
		game.deal();

		// We first check to make sure that the maximum number of cards is equal to the first
		// player's number of cards
		Player playerA = game.players.get(0);
		Assert.assertEquals(4, playerA.getCards().size());

		// We use this maximum number of cards to make sure that all players have roughly the same
		// number of cards.
		for (Player p : game.players) {
			if (p.getCards().size() > max && p.getCards().size() < max - 1) {
				Assert.fail("");
			}
		}

		// We then check to make sure that one card is not given to two different players
		for (int i = 0; i < game.players.size(); i++) {
			List<Card> cards = game.players.get(i).getCards();
			for (int j = 1; j < game.players.size(); j++) {
				// We do not need to check if the list of cards is equal to itself (when i = j).
				if (i == j) {
					continue;
				}

				// Retain all returns an array with elements that are shared between two arrays.
				// Therefore, if the size of cards.retainAll(...) is equal to zero, the two arrays
				// do not share similar values.
				cards.retainAll(game.players.get(j).getCards());
				if (cards.size() != 0) {
					Assert.fail("Player " + i + " and player " + j + " had the same card");
				}
			}
		}

	}
}
