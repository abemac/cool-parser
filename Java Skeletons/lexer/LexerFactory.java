package lexer;

public interface LexerFactory {
	public MyLexer getLexer(java.io.Reader reader);
	public MyLexer getLexer(java.io.InputStream instream);
}