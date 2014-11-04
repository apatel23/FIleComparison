package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player {
	private String name;
	private List<Card> myCards;
	private Color color;
	protected int col;
	protected int pixelCol;
	protected int row;
	protected int pixelRow;
	protected static final int circleDim = 30;

	public Player(String name, Color color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.pixelRow = col * 30;
		this.col = col;
		this.pixelCol = row * 30;
		myCards = new ArrayList<>();

	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(getPixelRow(), getPixelCol(), circleDim, circleDim);
		g.setColor(Color.BLACK);
		g.drawOval(getPixelRow(), getPixelCol(), circleDim, circleDim);
	}

	public Card disproveSuggestion(String person, String room, String weapon) {
		// Shuffle the cards so that the returned card is random.
		Collections.shuffle(myCards);
		// Make sure that only one card is returned.
		for (Card c : myCards) {
			if (c.name == person) {
				return c;
			} else if (c.name == room) {
				return c;
			} else if (c.name == weapon) {
				return c;
			}
		}
		// If there are no matches, return null.
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

	public Color getColor() {
		return color;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getPixelCol() {
		return pixelCol;
	}

	public int getPixelRow() {
		return pixelRow;
	}

	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined }
		}
		return color;
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
		if (col != other.col) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

}
