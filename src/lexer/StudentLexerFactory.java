package lexer;


public class StudentLexerFactory implements LexerFactory {
	
	public MyLexer getLexer(java.io.Reader reader) {
		return new CoolLexer(reader);
	}
	public MyLexer getLexer(java.io.InputStream instream) {
		return new CoolLexer(instream);
	}
}