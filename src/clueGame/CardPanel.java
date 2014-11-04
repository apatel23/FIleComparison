package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class CardPanel extends JPanel{
	private JLabel myCards;
	private List<JLabel> people, weapons, rooms;



	public CardPanel(List<Card> humanCards) {
		setLayout(new GridLayout(4, 1));
		myCards = new JLabel ("My cards");
		myCards.setLocation(800, 100);
		add(myCards);
		sortHumanCards(humanCards);
		add(addPeopleLabels());
		add(addRoomLabels());
		add(addWeaponLabels());
	}

	public void sortHumanCards(List<Card> humanCards) {
		people = new ArrayList<JLabel>();
		weapons = new ArrayList<JLabel>();
		rooms = new ArrayList<JLabel>();
		
		for (Card c : humanCards) {
			JLabel tempLabel = new JLabel(c.name);
			if (c.type == CardType.ROOM) {
				rooms.add(tempLabel);
			} else if (c.type == CardType.WEAPON) {
				weapons.add(tempLabel);
			} else {
				people.add(tempLabel);
			}
		}
	}


	public JPanel addRoomLabels() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(rooms.size(), 1));
		for (JLabel r : rooms) {
			add(r);
			panel.add(r);
		}
		return panel;
	}

	public JPanel addWeaponLabels() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(weapons.size(), 1));
		for (JLabel w : weapons) {
			add(w);
			panel.add(w);
		}
		return panel;
	}

	public JPanel addPeopleLabels() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(people.size(), 1));
		for (JLabel p : people) {
			add(p);
			panel.add(p);			
		}
		return panel;
	}

}
