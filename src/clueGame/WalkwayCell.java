package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	public WalkwayCell(int row, int col) {
		super(row, col);
	}

	@Override
	public boolean isWalkway() {
		return true;
	}

	@Override
	void draw(Graphics g, Board b) {
		int row = getPixelRow();
		int col = getPixelCol();
		g.setColor(Color.YELLOW);
		g.fillRect(row, col, tileDim, tileDim);
		g.setColor(Color.BLACK);
		g.drawRect(row, col, tileDim, tileDim);
	}
}
