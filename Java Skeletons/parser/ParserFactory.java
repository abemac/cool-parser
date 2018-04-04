package parser;

import astLexerAndParser.CoolTokenLexer;

public interface ParserFactory {
	/**
	 * This method is used to specify the input to the parser.
	 * @param lexer -- input to parser
	 */
	public void setLexer(CoolTokenLexer lexer);
	
	/**
	 * After setting the lexer, this parse() is the command to parse
	 * the stream coming from the lexer, debug_parse() does so in debug mode. 
	 * @throws Exception -- if unrecoverable exception occured during parsing.
	 */
	public void parse() throws Exception;
	public void debug_parse() throws Exception;
	
	/**
	 * After parsing, the return value of this method will be valid. 
	 * @returns whether there were recoverable errors during parse.
	 */
	public boolean errorsDuringParse();
		
	/**
	 * @returns the AST resulting from the parse.
	 */
	public Object getResultAST();
	
	/**
	 * Runs the semantic checker on the AST accessible by getResultAST().
	 * @throws Exception -- if there are exceptions during semantic checking.
	 */
	public void semant() throws Exception; 
	
	/**
	 * Returns the AST from the parse as dumped into a string.
	 */
	public String getDumpedAST();
}
