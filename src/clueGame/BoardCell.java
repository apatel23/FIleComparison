package clueGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class BoardCell {
	private int row;
	private int pixelRow;
	private int col;
	private int pixelCol;
	protected static final int tileDim = 30;
	
	abstract void draw(Graphics g, Board b, Boolean humanTurn);

	public BoardCell(int row, int col) {
		this.row = row;
		this.pixelRow = col * 30;
		this.col = col;
		this.pixelCol = row * 30;
	}
	
	// check if a BoardCell is clicked
	public boolean containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(pixelRow, pixelCol, tileDim, tileDim);
		if (rect.contains(new Point(mouseX, mouseY))) {
			return true;
		}
		return false;
	}

	// Getters
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public int getPixelRow() {
		return pixelRow;
	}
	
	public int getPixelCol() {
		return pixelCol;
	}

	public boolean isWalkway() {
		return false;
	}

	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway() {
		return false;
	}
}
