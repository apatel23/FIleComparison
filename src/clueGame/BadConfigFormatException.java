package clueGame;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {
	String message;

	public BadConfigFormatException(String message) {
		super(message);
		this.message = message;
	}
}
