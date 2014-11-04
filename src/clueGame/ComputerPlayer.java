package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ComputerPlayer extends Player {
	public char lastRoomVisited;
	public static List<Card> unseen = new ArrayList<>();
	public char currentRoom;

	public ComputerPlayer(String name, Color color, int col, int row) {
		super(name, color, col, row);
	}

	// Selects random location for the computer player in the case that there are only walkway cells to choose from.
	// Selects random location for the computer player in the case that there are only walkway cells and a doorway that
	// is from a room that was last entered to choose from.
	// Selects a doorway if it was not visited last and is in a list of possible locations.
	public void pickLocation(Set<BoardCell> targets) {
		for (BoardCell b : targets) {
			if (b.isDoorway()) {
				// Check to see if b is a room cell.
				if (b instanceof RoomCell) {
					// If b is confirmed to be a room cell, check its initial and compare it to the last room visited's
					// initial.
					if (((RoomCell) b).getInitial() == lastRoomVisited) {
						// If they are the same, continue going through the loop.
						continue;
					}
					row = b.getRow();
					col = b.getCol();
					currentRoom = ((RoomCell) b).getInitial();
					lastRoomVisited = currentRoom;
					// TODO createSuggestion()?
					return;
				}
			}
		}
		// If a doorway has not been found, or has already been visited, shuffle the possible locations and return a
		// walkway.
		List<BoardCell> targetList = new ArrayList<>(targets);
		BoardCell b = targetList.get(0);
		// If a doorway has already been visited, and is still in the target list, make sure to return only a walkway.
		while (b.isDoorway()) {
			Collections.shuffle(targetList);
		}
		row = b.getRow();
		col = b.getCol();
		currentRoom = 'W';
	}

	public List<Card> createSuggestion() {
		List<Card> suggestion = new ArrayList<>();

		// Add current room to the suggestion.
		String roomName = Board.getRooms().get(currentRoom);
		suggestion.add(new Card(roomName, Card.CardType.ROOM));
		// Pick person and weapon.
		Collections.shuffle(unseen);
		for (Card c : unseen) {
			if (c.type.equals(Card.CardType.PERSON)) {
				suggestion.add(c);
				break;
			}
		}
		for (Card c : unseen) {
			if (c.type.equals(Card.CardType.WEAPON)) {
				suggestion.add(c);
				break;
			}
		}
		return suggestion;
	}
}
