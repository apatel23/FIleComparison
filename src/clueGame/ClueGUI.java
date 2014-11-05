package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class ClueGUI extends JFrame {
	
	private JTextField whoseTurn;
	private JTextField rollField;
	private Integer roll;
	private JTextField guess;
	private JTextField response;
	private ClueGame game;
	private Player player;
	private String name;
	private Random rand;
	
	
	public ClueGUI () {
		game = new ClueGame("ClueLayoutStudents.csv", "roomConfig.txt", "Cards.txt", "PlayerCards.txt");
		//game.loadConfigFiles();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(800,200);
		
		// Contents of North Layout
		JPanel nPanel = new JPanel();
		nPanel = northPanel();
		add(nPanel, BorderLayout.NORTH);
		
		// Contents of West Layout
		JPanel wPanel = new JPanel();
		wPanel = rollPanel();
		add(wPanel, BorderLayout.WEST);
		
		// Contents of Center Layout
		JPanel cPanel = new JPanel();
		cPanel = guessPanel();
		add(cPanel, BorderLayout.CENTER);
		
		// Contents of East Layout
		JPanel ePanel = new JPanel();
		ePanel = guessResultPanel();
		add(ePanel, BorderLayout.EAST);
	}

	// Contents of northPanel: Whose turn label and text-box, Next Player
	// and Make Accusation buttons
	private JPanel northPanel() {
		JPanel panel = new JPanel();
		JLabel whoseTurnLabel = new JLabel("Whose Turn?");
		JButton nextPlayer = new JButton("Next Player");
		nextPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.nextPlayer();
				setCurrentPlayerName(game.getCurrentPlayer().getName());
				updateDisplay();
			}
			
		});
		JButton makeAccusation = new JButton("Make Accusation");
		whoseTurn = new JTextField(15);
		rollField = new JTextField(5);
		panel.add(whoseTurnLabel);
		whoseTurn.setText(game.getCurrentPlayer().getName());
		updateDisplay();
		panel.add(whoseTurn);
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;
	}
	
	// Contents of rollPanel: Roll label and text-box, and Die border
	private JPanel rollPanel() {
		JPanel panel = new JPanel();
		JLabel rollLabel = new JLabel("Roll");
		rollField = new JTextField(5);
		rollField.setText("");
		game.displayTargets();
		panel.add(rollLabel);
		panel.add(rollField);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return panel;
	}
	
	// Contents of guessPanel: Guess label and text-box, and Guess border
	private JPanel guessPanel() {
		JPanel panel = new JPanel();
		JLabel guessLabel = new JLabel("Guess");
		guess = new JTextField(20);
		panel.add(guessLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}
	
	// Contents of guessResultPanel: Response label and text-box, 
	// and Guess Result border
	private JPanel guessResultPanel() {
		JPanel panel = new JPanel();
		JLabel responseLabel = new JLabel("Response");
		response = new JTextField(20);
		panel.add(responseLabel);
		panel.add(response);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	public void setCurrentPlayerName(String playerName) {
		this.name = playerName;
		updateDisplay();
	}
	
	public void setCurrentRoll(Integer moves) {
		this.roll = moves;
		updateDisplay();
	}

	public void updateDisplay() {
		whoseTurn.setText(name);
		Integer roll = game.rollDie();
		rollField.setText(roll.toString());
	}
}