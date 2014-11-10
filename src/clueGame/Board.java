package clueGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	public static int MAX_ROWS = 50;
	public static int MAX_COLS = 50;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Set<BoardCell> visited;
	private static Map<Character, String> rooms;
	private Set<BoardCell> targetList;
	private ArrayList<BoardCell> targets;
	private Map<BoardCell, LinkedList<BoardCell>> adjList;
	private ArrayList<Player> players;
	private Set<BoardCell> adj;
	protected static final int tileDim = 30;
	private Boolean needToFinish = false;
	private Random rand;
	private int roll;
	private int row;
	private int col;
	private int counter;
	private Boolean hasRolled = false;

	public Board() {
		targetList = new HashSet<BoardCell>();
		targets = new ArrayList<BoardCell>();
		this.counter = 0;
	}



	public void displayTargets() {

		roll = rollDie();

		int row = 0;
		int col = 0;

		col = players.get(counter).getCol();
		row = players.get(counter).getRow();
		calcAdjacencies();
		calcTargets(row, col, roll);
		adj = getTargets();
		// computer's turn
		BoardCell b = selectTarget(adj);
		players.get(counter).row = b.getRow();
		players.get(counter).col = b.getCol();
		repaint();
	}

	public BoardCell selectTarget(Set<BoardCell> targets) {
		Random rand = new Random();
		ArrayList<BoardCell> list = new ArrayList(targets);
		if (targets.size() == 1) {
			return list.get(0);
		}
		int target = rand.nextInt(targets.size());
		int count = 0;
		for(BoardCell b : targets) {
			if(target == count) {
				return b;
			}
			count++;	
		}
		return null;
	}

	public void humanPlay() {
		addMouseListener(this);
		needToFinish = true;
		if (!hasRolled) {
			roll = rollDie();
			hasRolled = true;
		}
		col = players.get(0).getCol();
		row = players.get(0).getRow();
		calcAdjacencies();
		calcTargets(row, col, roll);
		targets = new ArrayList<BoardCell>(targetList);
		repaint();
	}

		public void mouseClicked(MouseEvent e) {
			BoardCell cell = null;

			for (int i = 0; i < getTargets().size(); i++) {
				if (targets.get(i).containsClick(e.getX(), e.getY())) {
					cell = targets.get(i);
					break;
				}
			}
			if (cell != null) { // valid target
				// Update the location of the human player
				row = cell.getRow();
				col = cell.getCol();
				players.get(0).row = row;
				players.get(0).col = col;
				targetList.clear(); // clear the target list
				needToFinish = false;
				hasRolled = false;
				counter++;
				repaint();
			} else { // invalid target
				System.out.println("invalid target!");
				JOptionPane.showMessageDialog(null, "Invalid Target!");
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}


	public int rollDie() {
		rand = new Random();
		roll = rand.nextInt(6) + 1;
		return roll;
	}

	public void drawNames(Graphics g) {
		g.drawString("Dungeon", 30, 30);
		g.drawString("Bedroom", 30, 10 * 30);
		g.drawString("Library", 2 * 30, 18 * 30);
		g.drawString("Bathroom", 8 * 30 - 20, 10 * 30);
		g.drawString("Theatre", 8 * 30, 18 * 30);
		g.drawString("Study", 21 * 30, 13 * 30);
		g.drawString("Kitchen", 8 * 30, 1 * 30);
		g.drawString("Workshop", 18 * 30, 1 * 30);
		g.drawString("Dining Room", 12 * 30, 5 * 30);
		g.drawString("Closet", 13 * 30, 9 * 30);
	}

	// In that class, override paintComponent(Graphics g) method.
	@Override
	public void paintComponent(Graphics g) {
		// Inside the method, use draw commands as needed to create your image.
		// paintComponent will be called automatically when the screen needs to
		// be redrawn (first displayed, minimized,
		// maximized).
		// Inside paintComponent, call super.paintComponent for required
		// housekeeping.
		super.paintComponent(g);
		// If you need to update the drawing, do not call paintComponent
		// directly. Instead call repaint().

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (getNeedToFinish() && targetList.contains(board[i][j])) {
					board[i][j].draw(g, this, true);
				} else
					board[i][j].draw(g, this, false);
			}
		}
		for (Player p : players) {
			p.draw(g);
		}
		drawNames(g);
	}

	public void loadBoardConfig(String mapFile) throws FileNotFoundException,
	BadConfigFormatException {
		board = new BoardCell[MAX_ROWS][MAX_COLS];
		FileReader reader = new FileReader(mapFile);
		Scanner fileIn = new Scanner(reader);
		// Set numCols and numRows initially to 0
		numRows = 0;
		numColumns = 0;

		// Read board configuration file line by line

		while (fileIn.hasNextLine()) {
			// Read the first line
			String newLine = fileIn.nextLine();
			// Split the row into string "cells"
			String[] parts = newLine.split(",");
			// Set the numCols initially, or check to make sure it's the same
			if (numColumns == 0)
				numColumns = parts.length;
			else if (parts.length != numColumns) {
				fileIn.close();
				throw new BadConfigFormatException("Column length mismatch!");
			}
			// Loop through the parts array to initialize cells
			for (int i = 0; i < parts.length; i++) {
				// Check first to make sure the first letter is an initial
				if (!rooms.containsKey(parts[i].charAt(0))) {
					fileIn.close();
					throw new BadConfigFormatException("Bad room initial");
				}
				if (parts[i].equals("W")) {
					board[numRows][i] = new WalkwayCell(numRows, i);
				} else
					board[numRows][i] = new RoomCell(numRows, i, parts[i]);
			}
			// Update rows
			numRows++;
		}
		fileIn.close();
	}

	// Adjacency and Target Finding
	public void calcAdjacencies() {
		adjList = new HashMap<BoardCell, LinkedList<BoardCell>>();
		// Calculate a list for each cell on the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				BoardCell currentCell = board[i][j];
				// Simple case, if cell is a doorway then only adj is direction
				// of doorway
				if (currentCell.isDoorway()) {
					// Convert BoardCell to RoomCell
					RoomCell currentRoomCell = (RoomCell) currentCell;
					// Create the LinkedList and add the appropriate direction
					LinkedList<BoardCell> currentAdjList = new LinkedList<BoardCell>();
					if (currentRoomCell.getDoorDirection() == DoorDirection.DOWN)
						currentAdjList.add(board[i + 1][j]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.RIGHT)
						currentAdjList.add(board[i][j + 1]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.LEFT)
						currentAdjList.add(board[i][j - 1]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.RIGHT)
						currentAdjList.add(board[i - 1][j]);
					// Add the pair to the adjList map
					adjList.put(currentCell, currentAdjList);
				} else if (currentCell.isRoom()) {
					// Add an empty list
					adjList.put(currentCell, new LinkedList<BoardCell>());
				} else if (currentCell.isWalkway()) {
					// Create the linked list and add all directions if they are
					// allowed moves
					LinkedList<BoardCell> currentAdjList = new LinkedList<BoardCell>();
					if (isViableMove(i + 1, j, DoorDirection.UP))
						currentAdjList.add(board[i + 1][j]);
					if (isViableMove(i - 1, j, DoorDirection.DOWN))
						currentAdjList.add(board[i - 1][j]);
					if (isViableMove(i, j + 1, DoorDirection.LEFT))
						currentAdjList.add(board[i][j + 1]);
					if (isViableMove(i, j - 1, DoorDirection.RIGHT))
						currentAdjList.add(board[i][j - 1]);
					// Add the linked list to the map
					adjList.put(board[i][j], currentAdjList);
				}
			}
		}
	}

	public boolean isViableMove(int row, int col,
			DoorDirection allowedDoorDirection) {
		// If the cell is out of bounds return false
		if (row < 0 || row >= numRows)
			return false;
		if (col < 0 || col >= numColumns)
			return false;
		// If cell is a room cell, check allowed direction
		if (getCellAt(row, col).isRoom()) {
			if (getCellAt(row, col).isDoorway()) {
				RoomCell aRoomCell = (RoomCell) getCellAt(row, col);
				if (allowedDoorDirection == aRoomCell.getDoorDirection())
					return true;
			}
			// Not a doorway, return false
			return false;
		}
		// Must be a walkway
		return true;
	}

	public void calcTargets(int row, int col, int moves) {
		targetList = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(getCellAt(row, col));
		findAllTargets(getCellAt(row, col), moves);
	}

	public void findAllTargets(BoardCell cell, int moves) {
		LinkedList<BoardCell> adjCells = nonVisitedAdjCells(cell);
		for (BoardCell c : adjCells) {
			visited.add(c);
			if (moves == 1 || c.isDoorway())
				targetList.add(c);
			else
				findAllTargets(c, moves - 1);
			visited.remove(c);
		}
	}

	public LinkedList<BoardCell> nonVisitedAdjCells(BoardCell cell) {
		LinkedList<BoardCell> nonVisitedCells = new LinkedList<BoardCell>();
		LinkedList<BoardCell> adjLinkedList = adjList.get(cell);
		for (BoardCell c : adjLinkedList) {
			if (!visited.contains(c)) {
				nonVisitedCells.add(c);
			}
		}
		return nonVisitedCells;
	}

	// Getters
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public static Map<Character, String> getRooms() {
		return rooms;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public RoomCell getRoomCellAt(int row, int col) {
		return (RoomCell) board[row][col];
	}

	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjList.get(board[row][col]);
	}

	public Set<BoardCell> getTargets() {
		return targetList;
	}

	// Setters
	public void setRooms(Map<Character, String> rooms) {
		Board.rooms = rooms;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;

	}

	public Boolean getNeedToFinish() {
		return needToFinish;
	}

	public void setNeedToFinsish(Boolean needToFinish) {
		this.needToFinish = needToFinish;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Boolean getHasRolled() {
		return hasRolled;
	}

	public void setHasRolled(Boolean hasRolled) {
		this.hasRolled = hasRolled;
	}

	public void setNeedToFinish(boolean b) {
		this.needToFinish = b;
	}

}
