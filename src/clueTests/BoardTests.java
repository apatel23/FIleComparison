package clueTests;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.ClueGame;
import clueGame.RoomCell.DoorDirection;

public class BoardTests {
	Board board;

	@Before
	public void setUp() {
		ClueGame game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt",
				"PlayerCards.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}

	@Test
	public void checkRooms() {
		Map<Character, String> rooms = board.getRooms();
		Assert.assertEquals(11, rooms.size());
		Assert.assertEquals("Dungeon", rooms.get('D'));
		Assert.assertEquals("Bedroom", rooms.get('B'));
		Assert.assertEquals("Workshop", rooms.get('R'));
		Assert.assertEquals("Library", rooms.get('L'));
		Assert.assertEquals("Walkway", rooms.get('W'));
		Assert.assertEquals("Bathroom", rooms.get('P'));
		Assert.assertEquals("Theater", rooms.get('T'));
		Assert.assertEquals("Study", rooms.get('S'));
		Assert.assertEquals("Kitchen", rooms.get('K'));
		Assert.assertEquals("Dining Room", rooms.get('E'));
		Assert.assertEquals("Closet", rooms.get('X'));
	}

	@Test
	public void checkDimensions() {
		Assert.assertEquals(board.getNumRows(), 22);
		Assert.assertEquals(board.getNumColumns(), 23);
	}

	@Test
	public void doorCheck() {
		Assert.assertTrue(board.getCellAt(2, 4).isDoorway());
		Assert.assertTrue(board.getCellAt(10, 4).isDoorway());
		Assert.assertTrue(board.getCellAt(17, 3).isDoorway());
		Assert.assertTrue(board.getCellAt(5, 9).isDoorway());
		Assert.assertFalse(board.getCellAt(6, 6).isDoorway());
		Assert.assertEquals(board.getRoomCellAt(2, 4).getDoorDirection(), DoorDirection.RIGHT);
		Assert.assertEquals(board.getRoomCellAt(10, 4).getDoorDirection(), DoorDirection.RIGHT);
		Assert.assertEquals(board.getRoomCellAt(17, 3).getDoorDirection(), DoorDirection.UP);
		Assert.assertEquals(board.getRoomCellAt(5, 9).getDoorDirection(), DoorDirection.RIGHT);
	}

	@Test
	public void numDoorCheck() {
		int numDoors = 0;
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumColumns(); j++) {
				if (board.getCellAt(i, j).isDoorway()) {
					numDoors++;
				}
			}
		}
		Assert.assertEquals(numDoors, 16);
	}

	@Test(expected = BadConfigFormatException.class)
	public void checkLayoutExceptions() throws BadConfigFormatException, FileNotFoundException {
		ClueGame badGame = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt",
				"PlayerCards.txt");
		badGame.loadRoomConfig();
		badGame.getBoard().loadBoardConfig("ClueLayoutBadColumns.csv");
	}

	@Test(expected = BadConfigFormatException.class)
	public void checkLegendExceptions() throws BadConfigFormatException, FileNotFoundException {
		ClueGame badGame = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt",
				"PlayerCards.txt");
		badGame.loadRoomConfig();
		badGame.getBoard().loadBoardConfig("ClueLayout.csv");
	}
}
