package symbolHandling;


/**
 * This exception is used by the symbol table whenever an 
 * operation would require the presence of a scope without
 * there being one.
 */
@SuppressWarnings("serial")
public class NoScopePresentException extends Error {

	public NoScopePresentException() {
	}

	public NoScopePresentException(String arg0) {
		super(arg0);
	}

	public NoScopePresentException(Throwable arg0) {
		super(arg0);
	}

	public NoScopePresentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
