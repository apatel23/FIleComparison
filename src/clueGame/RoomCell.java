package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE
	};

	private DoorDirection doorDirection;
	private char initial;
	private boolean isDoorway;

	public RoomCell(int row, int col, String identifier) throws BadConfigFormatException {
		// Store row and col
		super(row, col);
		// Is not a doorway, until proven a doorway
		isDoorway = false;
		// If it's 1 char long, just store it's initial
		if (identifier.length() == 1) {
			initial = identifier.charAt(0);
			doorDirection = DoorDirection.NONE;
		} else if (identifier.length() == 2) {
			initial = identifier.charAt(0);
			// Assign direction
			if (identifier.charAt(1) == 'U') {
				doorDirection = DoorDirection.UP;
				isDoorway = true;
			} else if (identifier.charAt(1) == 'R') {
				doorDirection = DoorDirection.RIGHT;
				isDoorway = true;
			} else if (identifier.charAt(1) == 'L') {
				doorDirection = DoorDirection.LEFT;
				isDoorway = true;
			} else if (identifier.charAt(1) == 'D') {
				doorDirection = DoorDirection.DOWN;
				isDoorway = true;
			} else if (identifier.charAt(1) == 'N') {
				doorDirection = DoorDirection.NONE;
			} else {
				throw new BadConfigFormatException("Invalid door direction found");
			}
		} else {
			throw new BadConfigFormatException("RoomCell given bad parameters");
		}

	}

	@Override
	public boolean isRoom() {
		return true;
	}

	@Override
	public boolean isDoorway() {
		return isDoorway;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}

	@Override
	void draw(Graphics g, Board b) {
		int row = getPixelRow();
		int col = getPixelCol();
		if (isRoom()) {
			if (getDoorDirection() == DoorDirection.UP) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(row, col, tileDim, tileDim);
				g.setColor(Color.BLUE);
				// ... on the top
				g.fillRect(row, col, tileDim, 5);
			} else if (getDoorDirection() == DoorDirection.DOWN) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(row, col, tileDim, tileDim);
				g.setColor(Color.BLUE);
				// ... on the bottom
				g.fillRect(row, col + 25, tileDim, 5);
			} else if (getDoorDirection() == DoorDirection.LEFT) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(row, col, tileDim, tileDim);
				g.setColor(Color.BLUE);
				// ... on the left
				g.fillRect(row, col, 5, tileDim);
			} else if (getDoorDirection() == DoorDirection.RIGHT) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(row, col, tileDim, tileDim);
				g.setColor(Color.BLUE);
				// ... on the right
				g.fillRect(row + 25, col, 5, tileDim);
			} else {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(row, col, tileDim, tileDim);
			}
		}
	}
}
