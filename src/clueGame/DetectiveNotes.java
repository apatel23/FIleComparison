package clueGame;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class DetectiveNotes extends JDialog {
	private static final long serialVersionUID = 1L;

	public DetectiveNotes() {
		setTitle("Detective Notes");
		setSize(600, 350);
		getContentPane().setLayout(null);
		fillPanel();
	}

	private void fillPanel() {
		// People and buttons
		JLabel ppl = new JLabel("People");
		ppl.setLocation(20, 0);
		ppl.setSize(150, 25);
		getContentPane().add(ppl);

		JCheckBoxMenuItem pPlum = new JCheckBoxMenuItem("Professor Plum");
		pPlum.setLocation(20, 20);
		pPlum.setSize(125, 25);
		getContentPane().add(pPlum);

		JCheckBoxMenuItem mGreen = new JCheckBoxMenuItem("Mr. Green");
		mGreen.setLocation(20, 40);
		mGreen.setSize(125, 25);
		getContentPane().add(mGreen);

		JCheckBoxMenuItem mWhite = new JCheckBoxMenuItem("Mrs. White");
		mWhite.setLocation(20, 60);
		mWhite.setSize(125, 25);
		getContentPane().add(mWhite);

		JCheckBoxMenuItem mPeacock = new JCheckBoxMenuItem("Mrs. Peacock");
		mPeacock.setLocation(150, 20);
		mPeacock.setSize(125, 25);
		getContentPane().add(mPeacock);

		JCheckBoxMenuItem mScarlet = new JCheckBoxMenuItem("Miss Scarlet");
		mScarlet.setLocation(150, 40);
		mScarlet.setSize(125, 25);
		getContentPane().add(mScarlet);

		JCheckBoxMenuItem cMustard = new JCheckBoxMenuItem("Colonel Mustard");
		cMustard.setLocation(150, 60);
		cMustard.setSize(125, 25);
		getContentPane().add(cMustard);

		JLabel choice = new JLabel("Person Choice");
		choice.setLocation(300, 40);
		choice.setSize(150, 25);
		getContentPane().add(choice);

		JComboBox<String> personBox = new JComboBox<String>();
		personBox.addItem("Professor Plum");
		personBox.addItem("Mr. Green");
		personBox.addItem("Mrs. White");
		personBox.addItem("Mrs. Peacock");
		personBox.addItem("Miss Scarlet");
		personBox.addItem("ColonelMustard");
		personBox.setLocation(400, 40);
		personBox.setSize(150, 25);
		getContentPane().add(personBox);

		// Weapons and Buttons
		JLabel wpn = new JLabel("Weapons");
		wpn.setLocation(20, 100);
		wpn.setSize(150, 25);
		getContentPane().add(wpn);

		JCheckBoxMenuItem lP = new JCheckBoxMenuItem("Lead Pipe");
		lP.setLocation(20, 120);
		lP.setSize(125, 25);
		getContentPane().add(lP);

		JCheckBoxMenuItem Revolver = new JCheckBoxMenuItem("Revolver");
		Revolver.setLocation(20, 140);
		Revolver.setSize(125, 25);
		getContentPane().add(Revolver);

		JCheckBoxMenuItem knife = new JCheckBoxMenuItem("Knife");
		knife.setLocation(20, 160);
		knife.setSize(125, 25);
		getContentPane().add(knife);

		JCheckBoxMenuItem cstick = new JCheckBoxMenuItem("Candlestick");
		cstick.setLocation(150, 120);
		cstick.setSize(125, 25);
		getContentPane().add(cstick);

		JCheckBoxMenuItem wrench = new JCheckBoxMenuItem("Wrench");
		wrench.setLocation(150, 140);
		wrench.setSize(125, 25);
		getContentPane().add(wrench);


		JCheckBoxMenuItem Rope = new JCheckBoxMenuItem("Rope");
		Rope.setLocation(150, 160);
		Rope.setSize(125, 25);
		getContentPane().add(Rope);


		JLabel wpnChoice = new JLabel("Weapon Choice");
		wpnChoice.setLocation(300, 140);
		wpnChoice.setSize(150, 25);
		getContentPane().add(wpnChoice);

		JComboBox<String> wpnBox = new JComboBox<String>();
		wpnBox.addItem("Lead Pipe");
		wpnBox.addItem("Revolver");
		wpnBox.addItem("Knife");
		wpnBox.addItem("Candlestick");
		wpnBox.addItem("Wrench");
		wpnBox.addItem("Rope");
		wpnBox.setLocation(400, 140);
		wpnBox.setSize(150, 25);
		getContentPane().add(wpnBox);

		// Rooms and Buttons
		JLabel rooms = new JLabel("Rooms");
		rooms.setLocation(20, 220);
		rooms.setSize(150, 25);
		getContentPane().add(rooms);

		JCheckBoxMenuItem cons = new JCheckBoxMenuItem("Conservatory");
		cons.setLocation(20, 240);
		cons.setSize(125, 25);
		getContentPane().add(cons);

		JCheckBoxMenuItem ball = new JCheckBoxMenuItem("Ballroom");
		ball.setLocation(20, 260);
		ball.setSize(125, 25);
		getContentPane().add(ball);

		JCheckBoxMenuItem bRoom = new JCheckBoxMenuItem("Billiard Room");
		bRoom.setLocation(20, 280);
		bRoom.setSize(125, 25);
		getContentPane().add(bRoom);

		JCheckBoxMenuItem lib = new JCheckBoxMenuItem("Library");
		lib.setLocation(150, 220);
		lib.setSize(125, 25);
		getContentPane().add(lib);

		JCheckBoxMenuItem study = new JCheckBoxMenuItem("Study");
		study.setLocation(150, 240);
		study.setSize(125, 25);
		getContentPane().add(study);

		JCheckBoxMenuItem dRoom = new JCheckBoxMenuItem("Dining Room");
		dRoom.setLocation(150, 260);
		dRoom.setSize(125, 25);
		getContentPane().add(dRoom);

		JCheckBoxMenuItem lounge = new JCheckBoxMenuItem("Lounge");
		lounge.setLocation(150, 280);
		lounge.setSize(125, 25);
		getContentPane().add(lounge);

		JLabel roomChoice = new JLabel("Room Choice");
		roomChoice.setLocation(300, 260);
		roomChoice.setSize(150, 25);
		getContentPane().add(roomChoice);

		JComboBox<String> roomBox = new JComboBox<String>();
		roomBox.addItem("Conservatory");
		roomBox.addItem("Kitchen");
		roomBox.addItem("Ballroom");
		roomBox.addItem("Billiard Room");
		roomBox.addItem("Library");
		roomBox.addItem("Study");
		roomBox.addItem("Dining Room");
		roomBox.addItem("Lounge");
		roomBox.setLocation(400, 260);
		roomBox.setSize(150, 25);
		getContentPane().add(roomBox);
	}
}
