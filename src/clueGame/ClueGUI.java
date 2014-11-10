package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class ClueGUI extends JPanel {
	
	private JTextField whoseTurn = new JTextField("Professor Plum");
	private JTextField rollField;
	private Integer roll;
	private String guess;
	private JTextField guessField;
	private JTextField response;
	private ClueGame game;
	private Player player;
	private String name;
	private Random rand;
	private JButton nextPlayer;
	
	
	public ClueGUI (ClueGame game) {
		this.game = game;
		// Contents of North Layout
		setLayout(new GridLayout(2,4));
		JPanel nPanel = new JPanel();
		nPanel = northPanel();
		add(nPanel);
		
		// Contents of West Layout
		JPanel wPanel = new JPanel();
		wPanel = rollPanel();
		add(wPanel);
		
		// Contents of Center Layout
		JPanel cPanel = new JPanel();
		cPanel = guessPanel();
		add(cPanel);
		
		// Contents of East Layout
		JPanel ePanel = new JPanel();
		ePanel = guessResultPanel();
		add(ePanel);
	}

	// Contents of northPanel: Whose turn label and text-box, Next Player
	// and Make Accusation buttons
	private JPanel northPanel() {
		JPanel panel = new JPanel();
		JLabel whoseTurnLabel = new JLabel("Whose Turn?");
		nextPlayer = new JButton("Next Player");
		JButton makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(game.getHumanTurn()) {
					setCurrentGuess(game.getGuess());
					game.makeAccustion();
					updateDisplay();
				}
				else {
					JOptionPane.showMessageDialog(null, "Wait until it's your turn!");
				}
				
			}
			
		});
		whoseTurn = new JTextField(15);
		rollField = new JTextField(5);
		panel.add(whoseTurnLabel);
		whoseTurn.setText(game.getCurrentPlayer().getName());
		//updateDisplay();
		nextPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.nextPlayer();
				setCurrentPlayerName(game.getCurrentPlayer().getName());
				updateDisplay();
			}
			
		});
		
		panel.add(whoseTurn);
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextPlayer) {
			System.out.println("click");
			if(!game.getTargetSelected() && player == game.players.get(0)) {
				
				JOptionPane.showMessageDialog(null, "You must finish your turn!");
			}
		}
	}
	
	// Contents of rollPanel: Roll label and text-box, and Die border
	private JPanel rollPanel() {
		JPanel panel = new JPanel();
		JLabel rollLabel = new JLabel("Roll");
		rollField = new JTextField(5);
		rollField.setText("");
		panel.add(rollLabel);
		panel.add(rollField);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return panel;
	}
	
	// Contents of guessPanel: Guess label and text-box, and Guess border
	private JPanel guessPanel() {
		JPanel panel = new JPanel();
		JLabel guessLabel = new JLabel("Guess");
		guessField = new JTextField(20);
		guessField.setText("");
		panel.add(guessLabel);
		panel.add(guessField);
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
	
	public void setCurrentGuess(String guessText) {
		this.guess = guessText;
	}
	
	public Integer getRoll() {
		return roll;
	}

	public void updateDisplay() {
		guessField.setText(guess);
		whoseTurn.setText(name);
		Integer roll = game.getBoard().getRoll();
		rollField.setText(roll.toString());
		
	}
}