package exceptions;

public class AdjacentTilesException extends Exception {
	private static final long serialVersionUID = 1L;

	public AdjacentTilesException() {
	    super("Two Ships are adjacent");
	}
}