package clueTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class GameActionTest {

	private static ClueGame game;

	private static Card profPlum;
	private static Card rope;
	private static Card dungeon;
	private static Card missScarlet;

	private static ComputerPlayer ai;
	private static ComputerPlayer frank;

	@BeforeClass
	public static void setUp() {
		profPlum = new Card("Professor Plum", CardType.PERSON);
		missScarlet = new Card("Miss Scarlet", CardType.PERSON);
		rope = new Card("Rope", CardType.WEAPON);
		dungeon = new Card("Dungeon", CardType.ROOM);
	}

	@Before
	public void setUpBefore() {
		game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt", "PlayerCards.txt");
		game.loadConfigFiles();
		game.getBoard().calcAdjacencies();
		ai = new ComputerPlayer("Mrs. White", game.convertColor("white"), 0, 11);
	}

	@Test
	public void testAccusation() {
		// Setting up the game's solution
		game.solution = new Solution("Professor Plum", "Rope", "Hall");

		// Set up the asserted correct solutions and then check to see if the accusation is correct
		Solution correctSolution = new Solution("Professor Plum", "Rope", "Hall");
		Assert.assertTrue(game.checkAccusation(correctSolution));

		Solution wrongPerson = new Solution("Not the Professor Plum", "Rope", "Hall");
		Assert.assertFalse(game.checkAccusation(wrongPerson));

		Solution wrongWeapon = new Solution("Professor Plum", "Not the Rope", "Hall");
		Assert.assertFalse(game.checkAccusation(wrongWeapon));

		Solution wrongRoom = new Solution("Professor Plum", "Rope", "Not the Hall");
		Assert.assertFalse(game.checkAccusation(wrongRoom));
	}

	@Test
	public void testDisproveSuggestion() {
		// Setting up the game's solution
		game.solution = new Solution("Mr. Green", "Rope", "Hall");

		frank = new ComputerPlayer("Professor Plum", game.convertColor("purple"), 0, 0);

		Card knife = new Card("Knife", CardType.WEAPON);
		Card wrench = new Card("Wrench", CardType.WEAPON);
		Card profPlum = new Card("Professor Plum", CardType.PERSON);
		Card mrsWhite = new Card("Mrs. White", CardType.PERSON);
		Card conservatory = new Card("Conservatory", CardType.ROOM);
		Card kitchen = new Card("Kitchen", CardType.ROOM);

		List<Card> cardSet;
		List<Card> emptySet = new ArrayList<>();

		cardSet = frank.getCards();
		cardSet.add(knife);
		cardSet.add(wrench);
		cardSet.add(profPlum);
		cardSet.add(mrsWhite);
		cardSet.add(conservatory);
		cardSet.add(kitchen);

		// Test for when 1 card in human or computer player's hand disproves the suggestion
		// Set up human player with cards first:
		game.players.get(0).setCards(cardSet);
		Assert.assertEquals(profPlum, game.players.get(0).disproveSuggestion("Professor Plum", "Billiard Room", "Rope"));
		game.players.get(0).setCards(emptySet);

		// Computer Player
		frank.setCards(cardSet);
		Assert.assertEquals(profPlum, frank.disproveSuggestion("Professor Plum", "Billiard Room", "Rope"));
		frank.setCards(emptySet);

		// Test for when 2 cards in computer player's hand disproves the suggestion
		frank.setCards(cardSet);
		boolean seenPlum = false;
		boolean seenConservatory = false;
		for (int i = 0; i < 1000; i++) {
			Card disprove = frank.disproveSuggestion("Professor Plum", "Conservatory", "Rope");
			Assert.assertNotNull(disprove);
			if (disprove.equals(profPlum)) {
				seenPlum = true;
			} else if (disprove.equals(conservatory)) {
				seenConservatory = true;
			} else {
				Assert.fail("Expected to see either Professor Plum or the Conservatory, not neither.");
			}
			if (seenPlum == true && seenConservatory == true) {
				break;
			}
		}
		Assert.assertTrue(seenPlum);
		Assert.assertTrue(seenConservatory);
		frank.setCards(emptySet);

		// Test for when 2 cards in human player's hand disproves the suggestion
		game.players.get(0).setCards(cardSet);
		seenPlum = false;
		seenConservatory = false;
		for (int i = 0; i < 1000; i++) {
			Card disprove = game.players.get(0).disproveSuggestion("Professor Plum", "Conservatory", "Rope");
			Assert.assertNotNull(disprove);
			if (disprove.equals(profPlum)) {
				seenPlum = true;
			} else if (disprove.equals(conservatory)) {
				seenConservatory = true;
			} else {
				Assert.fail("Expected to see either Professor Plum or the Conservatory, not neither.");
			}
			if (seenPlum == true && seenConservatory == true) {
				break;
			}
		}
		Assert.assertTrue(seenPlum);
		Assert.assertTrue(seenConservatory);
		game.players.get(0).setCards(emptySet);

		// Test that all players are queried in order. -- use handleSuggestion

		// Made a suggestion which no players could disprove, and ensured that null was returned.
		Assert.assertNull(game.handleSuggestion(profPlum.name, conservatory.name, knife.name, game.players.get(0)));

		// Made a suggestion that only the human could disprove, and ensured that the correct card was returned.
		game.players.get(0).setCards(cardSet);
		Assert.assertEquals(profPlum, game.handleSuggestion(profPlum.name, "test", "test", game.players.get(1)));
		game.players.get(0).setCards(emptySet);

		// I ensured that if the person who made the suggestion was the only one who could disprove it, null was
		// returned. This may require either a setter for whose turn it is or passing the current player into the
		// Board's handleSuggestion method. I tested this for both the human and a computer player as the current
		// player.
		game.players.get(0).setCards(cardSet);
		Assert.assertNull(game.handleSuggestion(profPlum.name, conservatory.name, knife.name, game.players.get(0)));
		game.players.get(0).setCards(emptySet);

		// To test the order that players are queried, I set up a suggestion that two players could disprove. I ensure
		// that the first person does the disproving (where "first" depends on the order in the players list).
		List<Card> cardSetKnife = new ArrayList<>();
		cardSetKnife.add(knife);
		List<Card> cardSetPlum = new ArrayList<>();
		cardSetPlum.add(profPlum);

		game.players.get(1).setCards(cardSetKnife);
		game.players.get(2).setCards(cardSetPlum);
		Assert.assertEquals(knife, game.handleSuggestion(profPlum.name, knife.name, "test", game.players.get(0)));
		game.players.get(0).setCards(emptySet);
		game.players.get(1).setCards(emptySet);
		game.players.get(2).setCards(emptySet);

		// I also set up a test where the furthest person from the accuser is the one who can disprove, to ensure that
		// all players are queried.
		game.players.get(5).setCards(cardSetPlum);
		Assert.assertEquals(profPlum, game.handleSuggestion(profPlum.name, "test", "test", game.players.get(0)));
	}

	@Test
	public void selectTarget() {
		Set<BoardCell> targetList;

		// Create a list of targets that include a room.
		game.getBoard().calcTargets(21, 5, 1);
		targetList = game.getBoard().getTargets();
		ai.pickLocation(targetList);
		Assert.assertEquals('L', ai.currentRoom);

		// Create a list of targets that do not include a room.
		game.getBoard().calcTargets(6, 5, 1);
		targetList = game.getBoard().getTargets();
		ai.pickLocation(targetList);
		Assert.assertEquals('W', ai.currentRoom);

		// Set the last visited room to 'L'.
		ai.lastRoomVisited = 'L';

		// Create a list of targets that include the last visited room.
		game.getBoard().calcTargets(21, 5, 1);
		targetList = game.getBoard().getTargets();
		ai.pickLocation(targetList);
		Assert.assertEquals('W', ai.currentRoom);
	}

	@Test
	public void makeSuggestion() {
		List<Card> unseenCards = new ArrayList<Card>();
		unseenCards.add(profPlum);
		unseenCards.add(rope);
		ComputerPlayer.unseen = unseenCards;
		ai.currentRoom = 'D';
		List<Card> cardList = ai.createSuggestion();

		// Make sure that the returned suggestion is three cards: a player, weapon, and room.
		Assert.assertEquals(3, cardList.size());
		Assert.assertTrue(cardList.contains(profPlum));
		Assert.assertTrue(cardList.contains(rope));
		Assert.assertTrue(cardList.contains(dungeon));

		// Create a new set of unseen cards.
		unseenCards.clear();
		unseenCards.add(missScarlet);
		unseenCards.add(profPlum);
		unseenCards.add(rope);

		// Iterate through until both plum and scarlet have been seen, as there is a 50/50 chance that either will be
		// returned. This guarantees that both will be returned after a certain number of tests.
		boolean seenPlum = false;
		boolean seenScarlet = false;
		for (int i = 0; i < 1000; i++) {
			cardList = ai.createSuggestion();

			Assert.assertEquals(3, cardList.size());
			Assert.assertTrue(cardList.contains(rope));
			Assert.assertTrue(cardList.contains(dungeon));

			if (cardList.contains(profPlum)) {
				seenPlum = true;
			} else if (cardList.contains(missScarlet)) {
				seenScarlet = true;
			} else {
				// If neither card is returned, the test must fail.
				Assert.fail("Expected to see either Professor Plum or Miss Scarlet, not neither.");
			}
			if (seenPlum == true && seenScarlet == true) {
				break;
			}
		}
		// By the end of this loop plum and scarlet should have been seen at least once each.
		Assert.assertTrue(seenPlum);
		Assert.assertTrue(seenScarlet);
	}

}
