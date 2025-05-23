package exceptions;

public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String errorType) {
        super("Invalid "+ errorType + " input");
    }
}
