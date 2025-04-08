package exceptions;

public class UnbalancedException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnbalancedException() {
	    super("9-letter or more words are not more than 20% of the dictionary!");
	}
}