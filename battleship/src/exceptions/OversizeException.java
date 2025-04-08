package exceptions;

public class OversizeException extends Exception {
	private static final long serialVersionUID = 1L;

	public OversizeException() {
        super("Ship placement out of bounds");
    }
}
