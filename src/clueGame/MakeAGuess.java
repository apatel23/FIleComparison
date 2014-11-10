package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MakeAGuess extends JDialog {
	private String roomText;
	private JTextField roomField;
	private String personSelected;
	private String weaponSelected;
	private String person;
	private String weapon;
	
	
	public MakeAGuess(String rm) {
		setSize(300,250);
		//setModal(true);
		//setResizable(false);
		setVisible(true);
		setTitle("Make a Guess");
		//setVisible(true);
		roomText = rm;
		getContentPane().setLayout(null);
		setContent();
		
	}
	
	public void setContent() {
		// Rooms
		JLabel room = new JLabel();
		room.setText("Your room");
		room.setLocation(20, 10);
		room.setSize(150,25);
		getContentPane().add(room);
		roomField = new JTextField();
		roomField.setText(roomText);
		roomField.setLocation(150, 10);
		roomField.setSize(100,25);
		getContentPane().add(roomField);
		
		// Person
		JLabel person = new JLabel();
		person.setText("Person");
		person.setLocation(20, 60);
		person.setSize(150,25);
		getContentPane().add(person);
		JComboBox<String> personBox = new JComboBox<String>();
		personBox.addItem("Professor Plum");
		personBox.addItem("Mr. Green");
		personBox.addItem("Mrs. White");
		personBox.addItem("Mrs. Peacock");
		personBox.addItem("Miss Scarlet");
		personBox.addItem("ColonelMustard");
		personBox.setLocation(150, 60);
		personBox.setSize(100,25);
		getContentPane().add(personBox);
		personBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox combo = (JComboBox)e.getSource();
                personSelected = (String)combo.getSelectedItem();
			}
		});

		// Weapon
		JLabel weapon = new JLabel();
		weapon.setText("Weapon");
		weapon.setLocation(20, 110);
		weapon.setSize(150,25);
		getContentPane().add(weapon);
		JComboBox<String> wpnBox = new JComboBox<String>();
		wpnBox.addItem("Lead Pipe");
		wpnBox.addItem("Revolver");
		wpnBox.addItem("Knife");
		wpnBox.addItem("Candlestick");
		wpnBox.addItem("Wrench");
		wpnBox.addItem("Rope");
		wpnBox.setLocation(150, 110);
		wpnBox.setSize(100,25);
		getContentPane().add(wpnBox);
		wpnBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox combo = (JComboBox)e.getSource();
                weaponSelected = (String)combo.getSelectedItem();
			}
		});
		
		JButton submit = new JButton();
		submit.setText("Submit");
		submit.setSize(100, 25);
		submit.setLocation(20, 160);
		getContentPane().add(submit);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setPerson(personSelected);
				setWeapon(weaponSelected);
				dispose();
			}		
		});
		
		JButton cancel = new JButton();
		cancel.setText("Cancel");
		cancel.setSize(100, 25);
		cancel.setLocation(150, 160);
		getContentPane().add(cancel);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	
	}
	
	public void solution(String person, String weapon) {
		person = getPerson();
		weapon = getWeapon();
	}
	
	public String getPerson() {
		return personSelected;
	}
	
	public String getWeapon() {
		return weaponSelected;
	}
	
	public void setPerson(String person) {
		this.person = person; 
	}
	
	public void setWeapon(String weapon) {
		this.weapon = weapon; 
	}

}
