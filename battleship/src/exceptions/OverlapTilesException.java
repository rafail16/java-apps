package exceptions;

public class OverlapTilesException extends Exception {
	private static final long serialVersionUID = 1L;

	public OverlapTilesException() {
        super("Overlaping ships in grid");
    }
}
