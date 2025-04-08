package exceptions;

public class UndersizeException extends Exception {
	private static final long serialVersionUID = 1L;

	public UndersizeException() {
	    super("There are not 20 applicable words!");
	}
}