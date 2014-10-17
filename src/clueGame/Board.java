package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.RoomCell.DoorDirection;


public class Board {
	public static int MAX_ROWS = 50;
	public static int MAX_COLS = 50;
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Set<BoardCell> visited;
	private Map<Character, String> rooms;
	private Set<BoardCell> targetList;
	private Map<BoardCell, LinkedList<BoardCell>> adjList;

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
	
	public void calcTargets(int row, int col, int moves){
		targetList = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(getCellAt(row, col));
		findAllTargets(getCellAt(row, col), moves);
	}
	
	public void findAllTargets(BoardCell cell, int moves){
		LinkedList<BoardCell> adjCells = nonVisitedAdjCells(cell);
		for(BoardCell c : adjCells){
			visited.add(c);
			if(moves == 1 || c.isDoorway())
				targetList.add(c);
			else
				findAllTargets(c, moves-1);
			visited.remove(c);
		}
		
	}
	
	public LinkedList<BoardCell> nonVisitedAdjCells(BoardCell cell){
		LinkedList<BoardCell> nonVisitedCells = new LinkedList<BoardCell>();
		LinkedList<BoardCell> adjLinkedList = adjList.get(cell);
		for(BoardCell c : adjLinkedList){
			if(!visited.contains(c)){
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

	public Map<Character, String> getRooms() {
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
		this.rooms = rooms;
	}

}
