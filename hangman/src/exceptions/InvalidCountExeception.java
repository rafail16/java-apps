package exceptions;

public class InvalidCountExeception extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCountExeception() {
	    super("A word appear two times!");
	}
}