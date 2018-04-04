package parser;

import java.io.IOException;
import java.io.StringWriter;

import astLexerAndParser.CoolTokenLexer;

import semanticAnalyzer.SemanticError;
import treeNodes.Program;

/**
 * This class works as an interface for the parsing with the solution AST.
 */
public class SolutionStudentParserFactory implements ParserFactory {
	private Program ast = null;
	private CoolParserSolutionStudent parser = null;
	
	public void setLexer(CoolTokenLexer lexer) {
		parser = new CoolParserSolutionStudent(lexer);
	}
	public void parse() throws Exception {
		ast = (Program) parser.parse().value;
	}
	public void debug_parse() throws Exception {
		ast = (Program) parser.debug_parse().value;
	}
	public boolean errorsDuringParse() {
		return (parser.omerrs > 0);
	}

	public Program getResultAST() {
		return ast;
	}	
	public void semant() throws SemanticError {
		ast.semant();
	}
	

	public String getDumpedAST()  {
		StringWriter out = new StringWriter();
		
		try {
			ast.dumpWithTypes(out, 0);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
