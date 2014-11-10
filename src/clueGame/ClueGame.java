package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import clueGame.Card.CardType;

// Trying to draw players into the board based on cell location...

public class ClueGame extends JFrame {
	private static final long serialVersionUID = 1L;
	protected static final int tileDim = 30;
	private Map<Character, String> rooms;
	private Board board;
	private String mapFile;
	private String legendFile;
	private String playerFile;
	private String deckFile;
	public ArrayList<Player> players;
	private List<Card> deck;
	private Player currentPlayer;
	public Solution solution;
	public int turn;
	private int counter;
	private Random rand;
	private Integer roll;
	private Set<BoardCell> adj;
	private ClueGUI control;
	private DetectiveNotes detectiveNotes;
	private List<Card> humanCards = new ArrayList<Card>();
	private CardPanel cPanel;
	private int target;
	private ArrayList<BoardCell> targets;
	private String room;
	private String person;
	private String weapon;
	private String guess;
	private MakeAGuess suggestion;
	private MakeAccusation accusation;
	private Boolean humanTurn;
	private Boolean selectTarget = false;

	public ClueGame(String map, String legend, String deck, String pieces) {
		
		this.board = new Board();
		this.rooms = new HashMap<Character, String>();
		this.players = new ArrayList<Player>();
		this.mapFile = map;
		this.legendFile = legend;
		this.playerFile = pieces;
		this.deckFile = deck;
		this.deck = new ArrayList<Card>();
		this.solution = new Solution();
		this.turn = 0;
		loadConfigFiles();
		this.currentPlayer = players.get(0);
		this.counter = 0;
		setHumanTurn(true);
		
		// JFrame 
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setResizable(false);
		setSize(800,850);
		setJMenuBar(createMenuBar());	
		deal();
		detectiveNotes = new DetectiveNotes();
		control = new ClueGUI(this);
		humanCards = players.get(0).getCards();
		System.out.println(humanCards);
		cPanel = new CardPanel(humanCards);
		add(control, BorderLayout.SOUTH);
		add(board, BorderLayout.CENTER);
		add(cPanel, BorderLayout.EAST);
		JOptionPane.showMessageDialog(null, "You are Professor Plum. Press Next Player to Begin.");
		setCurrentPlayer(players.get(counter));
		board.setHumanTurn(true);
		
	}
	
	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("Menu");
		menuBar.add(menu);

		//Creates the Detective notes for the menu
		JMenuItem notesAction = new JMenuItem("Detective Notes");
		notesAction.addMouseListener(new MouseListener(){
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {}
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e){
				detectiveNotes = new DetectiveNotes();
				detectiveNotes.setVisible(true);
			}
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e){}
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {}
		});
		
		//Creates the Exit button
		JMenuItem exitAction = new JMenuItem("Exit");
		exitAction.addMouseListener(new MouseListener(){
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {}
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e){
				System.exit(0);
			}
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e){}
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e){}
		});

		//Adds the objects to the menu
		menu.add(notesAction);
		menu.addSeparator();
		menu.add(exitAction);

		return menuBar;
	}
	
	public void makeAccustion() {
		accusation = new MakeAccusation();
		accusation.solution(room, person, weapon);
		guess =  accusation.getPerson() + " in the " + accusation.getRoom() + " with the " + accusation.getWeapon();
		setGuess(guess);
		// Handle Suggestion
		Card card = players.get(0).disproveSuggestion(accusation.getRoom(), accusation.getPerson(),
				accusation.getWeapon());
		if(card != null) System.out.println(card.name);
		else System.out.println("null");
	}
	
	public void humanPlay() {
		roll = rollDie();
		
		System.out.println("roll: " + getRoll());
		int row = 0;
		int col = 0;
		col = players.get(counter).getCol();
		row = players.get(counter).getRow();
		board = getBoard();
		board.calcAdjacencies();
		board.calcTargets(row, col, roll);
		adj = board.getTargets();
		targets = new ArrayList<BoardCell>(adj);
		
		for(int i = 0; i < targets.size(); i++) {
			System.out.println("Target: " + targets.get(i).getCol() + " " + targets.get(i).getRow());
		}
		
		addMouseListener(new MouseListener() {
			@SuppressWarnings("unused")
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("coordinates of click: " + x + ", " + y);
				
				BoardCell cell = null;
				for (int i = 0; i < targets.size(); i++) {
					if (targets.get(i).containsClick(e.getX(), e.getY())) {
						cell = targets.get(i);
						System.out.println("cell: " + cell.getCol() + ", " + cell.getRow());
						break;
					}
				}
				if (cell != null) { // valid target
					// Update the location of the human player
					int row = cell.getRow();
					int col = cell.getCol();
					players.get(0).row = row;
					players.get(0).col = col;
					targets.clear(); // clear the target list
					repaint();
					System.out.println("Move to cell: "
							+ players.get(0).col + ", "
							+ players.get(0).row);
					// TODO Move player to the new cell, nextPlayer's turn
					if(cell.isRoom()) {
						char room = ((RoomCell) cell).getInitial();
						String r = board.initialName(room);
						suggestion = new MakeAGuess(r);
						suggestion.solution(person, weapon);
						System.out.println("Human guess: " + person + " " + weapon);
						
					}
					board.setHumanTurn(false);
					setTargetSelected(true);
					repaint();
				} else { // invalid target
					System.out.println("invalid target! ");
					JOptionPane.showMessageDialog(null, "Invalid Target!");
				}
				
				humanTurn = false;

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
		});
	
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int rollDie() {
		rand = new Random();
		roll = rand.nextInt(5) + 1;
		return roll;
	}
	
	public void displayTargets() {
		
		System.out.println("Current Player: " + currentPlayer.getName());
		System.out.println("Position: " + currentPlayer.getCol() + ", " + currentPlayer.getRow());
		roll = board.rollDie();
		board.setRoll(roll);
		System.out.println("roll: " + getRoll());
		
		int row = 0;
		int col = 0;
		
		col = players.get(counter).getCol();
		row = players.get(counter).getRow();
		board = getBoard();
		board.calcAdjacencies();
		board.calcTargets(row, col, board.getRoll());
		adj = board.getTargets();
		System.out.println("targets size: " + adj.size());
		if(board.getHumanTurn()) {
			board.setHumanTurn(true);
		}
		else { // computer's turn
			System.out.println("Computer: ");
			BoardCell b = selectTarget(adj);
			players.get(counter).row = b.getRow();
			players.get(counter).col = b.getCol();
			repaint();
		}
	}
	
	// return the chosen target for the computer
	public BoardCell selectTarget(Set<BoardCell> targets) {
		Random rand = new Random();
		target = rand.nextInt(targets.size());
		int count = 0;
		for(BoardCell b : targets) {
			if(target == count || targets.size() == 1 ) {
				System.out.println("c move to: " + b.getCol() + ", " + b.getRow());
				System.out.println("--------------------------------");
				return b;
			}
			count++;	
		}
		return null;
	}

	public void nextPlayer() {
		if(counter == players.size()) counter = 0;
		setCurrentPlayer(players.get(counter));

		// Human turn
		if(counter == 0) {
			humanTurn = true;
			setHumanTurn(true);
			humanPlay();
		}
		// Computer Turn
		else {
			displayTargets();
		}
		// Next Player pressed before player selects target
		if(counter == 0 && !board.getSelectTarget()) { 
			JOptionPane.showMessageDialog(null, "You must finish your turn!");
			
		}
		counter++;
	}
	

	public void loadConfigFiles() {
		try {
			loadRoomConfig();
			loadPlayersConfig();
			loadCardConfig();
			board.loadBoardConfig(mapFile);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void loadPlayersConfig() throws NumberFormatException, IOException {
		Scanner reader = new Scanner(new File(playerFile));
		// ArrayList<Player> playerList = new ArrayList<Player>();
		ComputerPlayer compPlayer;
		HumanPlayer humPlayer;

		String line;
		String[] lineArray;
		int linenum = 0;

		while (reader.hasNextLine()) {
			// Reads in the line from the file and then splits it into an array where lineArray[0] = name, [1] = color,
			// [2] = col, [3] = row
			line = reader.nextLine();
			lineArray = line.split(",");

			String name = lineArray[0];
			Color color = convertColor(lineArray[1]);
			int col = Integer.parseInt(lineArray[2]);
			int row = Integer.parseInt(lineArray[3]);

			if (linenum == 0) {
				// The first player in the file will be the human player.
				humPlayer = new HumanPlayer(name, color, col, row);
				players.add(humPlayer);
			} else {
				// All other players will be computer players
				compPlayer = new ComputerPlayer(name, color, col, row);
				players.add(compPlayer);
			}
			linenum++;
		}
		reader.close();
		board.setPlayers(this.players);
	}

	// This method generates the deck of cards from a text file
	public void loadCardConfig() throws FileNotFoundException {
		FileReader reader = new FileReader(deckFile);
		Scanner fileIn = new Scanner(reader);

		CardType newType;
		int count = 0;
		String tempName;
		Card tempCard;

		while (fileIn.hasNextLine()) {
			// The file is formatted such that the first six lines are people,
			// the next six are weapons, and the last 11 are rooms. This
			// information is used to find the newType and tempName for the card
			// to be added to the deck.
			if (count < 6) {
				newType = Card.CardType.PERSON;
				tempName = fileIn.nextLine();
				tempCard = new Card(tempName, newType);
				deck.add(tempCard);
			} else if (count < 12 && count >= 6) {
				newType = Card.CardType.WEAPON;
				tempName = fileIn.nextLine();
				tempCard = new Card(tempName, newType);
				deck.add(tempCard);
			} else if (count >= 12) {
				newType = Card.CardType.ROOM;
				tempName = fileIn.nextLine();
				tempCard = new Card(tempName, newType);
				deck.add(tempCard);
			}
			count++;
		}
		fileIn.close();
	}

	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(legendFile);
		Scanner fileIn = new Scanner(reader);
		// Parse Line by line
		while (fileIn.hasNextLine()) {
			String newLine = fileIn.nextLine();
			String[] parts = newLine.split(",");

			// Should have 2 parts per line
			if (parts.length != 2) {
				fileIn.close();
				throw new BadConfigFormatException("Room config has too many arguments on one line!");
			}

			// Make sure first part is a char
			if (parts[0].length() != 1) {
				fileIn.close();
				throw new BadConfigFormatException("Room initial is not 1 letter!");
			}

			// Remove spaces from parts[1]
			parts[1] = parts[1].trim();
			this.rooms.put(parts[0].charAt(0), parts[1]);
		}
		board.setRooms(rooms);
		fileIn.close();
	}

	// This method deals all of the cards to the players in the game
	public void deal() {
		// int random;
		int count = 0;
		List<Card> cardList;

		Collections.shuffle(deck);

		// While there are still cards in the deck...
		while (deck.size() > 0) {
			// Reset the counter if it is greater than the number of players, as
			// it represents the player's position in the player list.
			if (count == players.size()) {
				count = 0;
			}

			// Creates a list of the players current cards
			cardList = players.get(count).getCards();

			// Generates a random integer using our random int method, selects a
			// card at random, and removes the card from the deck
			cardList.add(deck.get(0));
			deck.remove(0);

			// Sets the player's cards to the current card list that includes
			// the randomly selected card.
			players.get(count).setCards(cardList);

			count++;
		}
	}

	public List<Card> getDeck() {
		return deck;
	}

	// Method that queries players in order that they appear in the player list and checks if they have the suggested
	// cards.
	public Card handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		// Create a null suggestion to be filled in for loop or returned if no card is found.
		Card suggestion = null;
		// Iterates through the player list in order.
		for (Player p : players) {
			// Makes sure the accusing person does not have their own cards returned.
			if (p.equals(accusingPerson)) {
				continue;
			} else {
				// Updates suggestion with a random card or null if there is no match.
				suggestion = p.disproveSuggestion(person, room, weapon);
			}
			if (suggestion != null) {
				// If suggestion contains a random card, return it.
				return suggestion;
			}
		}
		// If no cards are found, return a null suggestion.
		return suggestion;
	}

	// Checks to see if the solution's person, weapon, and room names are the same as the accused
	// person, weapon, and room names.
	public boolean checkAccusation(Solution solution) {
		if (this.solution.person != solution.person) {
			return false;
		}
		if (this.solution.room != solution.room) {
			return false;
		}
		if (this.solution.weapon != solution.weapon) {
			return false;
		}
		return true;
	}

	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}
	
	public Integer getRoll() {
		return roll;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Boolean getHumanTurn() {
		return humanTurn;
	}
	
	public void setHumanTurn(Boolean humanTurn) {
		this.humanTurn = humanTurn;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void setTargetSelected(Boolean selectTarget) {
		this.selectTarget = selectTarget;
	}
	
	public Boolean getTargetSelected() {
		return selectTarget;
	}
	
	public String getGuess() {
		return guess;
	}
	
	public void setGuess(String guess) {
		this.guess = guess;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt", "PlayerCards.txt");
		game.setVisible(true);

	}	

}