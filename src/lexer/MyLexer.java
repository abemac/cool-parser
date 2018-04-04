package lexer;

import java_cup.runtime.Symbol;

public interface MyLexer {
	public void set_filename(String filename);
	public int curr_lineno();
	public Symbol next_token() throws java.io.IOException;
}
