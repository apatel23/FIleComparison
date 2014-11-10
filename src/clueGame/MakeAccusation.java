package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MakeAccusation extends JDialog {
	private JTextField roomField;
	private String personSelected;
	private String weaponSelected;
	private String roomSelected;
	private String person;
	private String weapon;
	private String room;
	

	public MakeAccusation() {
		setSize(300,250);
		//setModal(true);
		//setResizable(false);
		setVisible(true);
		setTitle("Make a Guess");
		//setVisible(true);
		getContentPane().setLayout(null);
		setContent();
	}
	
	public void setContent() {
		// Rooms
				JLabel room = new JLabel();
				room.setText("Room");
				room.setLocation(20, 10);
				room.setSize(150,25);
				getContentPane().add(room);
				JComboBox<String> roomBox = new JComboBox<String>();
				roomBox.addItem("Conservatory");
				roomBox.addItem("Kitchen");
				roomBox.addItem("Ballroom");
				roomBox.addItem("Billiard Room");
				roomBox.addItem("Library");
				roomBox.addItem("Study");
				roomBox.addItem("Dining Room");
				roomBox.addItem("Lounge");
				roomBox.setLocation(150, 10);
				roomBox.setSize(150, 25);
				getContentPane().add(roomBox);
				roomBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						@SuppressWarnings("rawtypes")
						JComboBox combo = (JComboBox)e.getSource();
		                roomSelected = (String)combo.getSelectedItem();
					}
				});
				
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
						setRoom(roomSelected);
						setPerson(personSelected);
						setWeapon(weaponSelected);
						System.out.println(roomSelected + " " + personSelected + " " + weaponSelected);
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
	
	public void solution(String room, String person, String weapon) {
		room = getRoom();
		person = getPerson();
		weapon = getWeapon();
	}
	
	public String getRoom() {
		return roomSelected;
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
	
	public void setRoom(String room) {
		this.room = room;
	}
}
