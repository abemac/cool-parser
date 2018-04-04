package parser;

@SuppressWarnings("serial")
public class LexParseError extends Exception {

	public LexParseError() {
	}

	public LexParseError(String arg0) {
		super(arg0);
	}

	public LexParseError(Throwable arg0) {
		super(arg0);
	}

	public LexParseError(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
