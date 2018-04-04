package lexer;


public class SolutionLexerFactory implements LexerFactory {

	public MyLexer getLexer(java.io.Reader reader) {
		return new SolutionCoolLexer(reader);
	}
	public MyLexer getLexer(java.io.InputStream instream) {
		return new SolutionCoolLexer(instream);
	}
}