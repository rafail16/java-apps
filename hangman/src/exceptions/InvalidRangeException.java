package exceptions;

public class InvalidRangeException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRangeException() {
	    super("There are words with less than 6 letters!");
	}
}