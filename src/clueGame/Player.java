package clueGame;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {
	private String name;
	private List<Card> myCards;
	private String color;
	private int xCoord;
	private int yCoord;

	public Player(String name, String color, int yCoord, int xCoord) {
		this.name = name;
		this.color = color;
		this.yCoord = yCoord;
		this.xCoord = xCoord;
		myCards = new ArrayList<>();
	}

	public void loadPlayers(String filename) throws NumberFormatException,
	IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		ArrayList<Player> playerList = new ArrayList<Player>();
		ComputerPlayer compPlayer;
		HumanPlayer humPlayer;

		String s;
		String[] arr;
		String line;
		int linenum = 0;

		while ((line = reader.readLine()) != null) {
			s = reader.readLine();
			arr = s.split(","); // [0] = name, [1] = color, [2] = y-coord, [3] =
			// x-coord
			int yCoord = Integer.parseInt(arr[2]);
			int xCoord = Integer.parseInt(arr[3]);

			if (linenum == 0) { // The first player in the file will be the
				// human player.
				humPlayer = new HumanPlayer(arr[0], arr[1], yCoord, xCoord);
				playerList.add(humPlayer);
			} else {
				compPlayer = new ComputerPlayer(arr[0], arr[1], yCoord, xCoord);
				playerList.add(compPlayer);
			}
			linenum++;
		}
		reader.close();
	}

	public Card disproveSuggestion(String person, String room, String weapon) {
		return null;
	}

	public List<Card> getCards() {
		return myCards;
	}

	public void setCards(Collection<Card> cards) {
		myCards = new ArrayList<>(cards);
	}

	public String getName() {
		return name;
	}

	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(
					strColor.trim());
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined }
		}
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + xCoord;
		result = prime * result + yCoord;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (xCoord != other.xCoord) {
			return false;
		}
		if (yCoord != other.yCoord) {
			return false;
		}
		return true;
	}

}
