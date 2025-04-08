package exceptions;

public class InvalidCountExeception extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCountExeception() {
	    super("More than one same ships");
	}
}