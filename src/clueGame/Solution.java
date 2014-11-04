package clueGame;

public class Solution {
	public String person, weapon, room;

	public Solution() {
		person = "";
		weapon = "";
		room = "";
	}

	// Constructor used for testing purposes
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	// Getters and setters used purely for testing purposes.
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
