package testEnvironment;

import generalHelpers.Flags;
import generalHelpers.Utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import parser.ParserFactory;
import parser.SolutionParserFactory;
import lexer.Lexer;
import lexer.LexerFactory;
import lexer.SolutionLexerFactory;
import astLexerAndParser.*;

/**
 * This class contains the pipe for a complete cool compiler. For each phase it
 * can be chosen whether to use the predesigned code, or whether the student
 * solution should be used.
 * 
 * All necessary information for adjusting the code is given right at the main
 * function.
 * 
 * The general idea goes as follows:
 * 
 * All input-files are lexed by the specified lexer. All tokens are then dumped
 * into a string. The "CoolTokenLexer" is then used for generating appropriate
 * input into the specified parser. The resulting AST is again dumped into a
 * string. Semantic analysis is done by parsing the AST from the string using
 * either the predesigned treeNodeSolution classes as AST-nodes, or the classes
 * from treeNodes (student designed), where "Program.semant()" is the entry
 * point of the semantic analyzer. The resulting AST is again dumped into a
 * string. Code generation is done by parsing the AST from the string again
 * either with the predesigned classes or the student classes. The entry point
 * for the code generation is "Program.cgen(PrintWriter)". The output is written
 * into a file.
 */
public class CoolCompiler {
	private LexerFactory lexer;

	private ParserFactory parser;

	private boolean solSemant;

	private boolean solCGen;

	/**
	 * Generates a cool-compiler using predesigned/student code as specified by
	 * the arguments. Actual compilation is done in
	 * CoolCompiler.compile(String[] files).
	 * 
	 * @param lexer -
	 *            the lexer to be used
	 * @param parser -
	 *            the parser to be used
	 * @param useSolutionSemant -
	 *            true, to use predesigned code
	 * @param useSolutionCGen -
	 *            true, to use predesigned code
	 */
	public CoolCompiler(LexerFactory lexer, ParserFactory parser,
			boolean useSolutionSemant, boolean useSolutionCGen) {
		this.lexer = lexer;
		this.parser = parser;
		solSemant = useSolutionSemant;
		solCGen = useSolutionCGen;

	}

	private FileWriter outFile = null;

	private void setupOutputFile() {
		String outFilename = null;
		if (Flags.out_filename == null) {
			if (Flags.in_filename != null) {
				outFilename = Flags.in_filename.substring(0, Flags.in_filename
						.lastIndexOf('.'))
						+ ".s";
			}
		}

		Flags.out_filename = outFilename;

		if (Flags.out_filename != null) {
			try {
				outFile = new FileWriter(Flags.out_filename);
			} catch (IOException ex) {
				Utilities.fatalError("Cannot open output file "
						+ Flags.out_filename);
			}
		}

	}

	/**
	 * Compiles with the settings as determined in the construction of the
	 * CoolCompiler object the files given as the argument. Generated code is
	 * written to file.
	 * 
	 * @param inFiles -
	 *            files to be compiled as one big cool program.
	 * @throws Exception -
	 *             various IO and lex/parse/semantic errors
	 */
	public void compile(String[] inFiles) throws Exception {
		String ast = lexAndParseToDumpedAST(lexer, parser, inFiles);

		// System.out.println(ast);

		if (solSemant)
			ast = solutionSemant(ast);
		else
			ast = studentSemant(ast);

		setupOutputFile();
		if (solCGen)
			solutionCGen(ast, new PrintWriter(outFile));
		else
			studentCGen(ast, new PrintWriter(outFile));
		
		outFile.close();
	}

	/**
	 * Lexes and parses the specified files and returns a dumped ast.
	 * @param lexer - the lexer to be used
	 * @param parser - the parser to be used
	 * @param inFiles - the files to be lexed and parsed
	 * @returns a dumped ast of the program
	 * @throws Exception - various IO, lex and parse errors
	 */
	public static Object lexAndParseToAST(LexerFactory lexer,
			ParserFactory parser, String[] inFiles) throws Exception {
		
		parser.setLexer(Lexer.getTokensFromFiles(lexer, inFiles));
		parser.parse();

		return parser.getResultAST();
	}

	/**
	 * Lexes and parses the specified files and returns a dumped ast.
	 * @param lexer - the lexer to be used
	 * @param parser - the parser to be used
	 * @param inFiles - the files to be lexed and parsed
	 * @returns a dumped ast of the program
	 * @throws Exception - various IO, lex and parse errors
	 */
	public static String lexAndParseToDumpedAST(LexerFactory lexer,
			ParserFactory parser, String[] inFiles) throws Exception {

		parser.setLexer(Lexer.getTokensFromFiles(lexer, inFiles));
		parser.parse();

		return parser.getDumpedAST();
	}
	
	

	/**
	 * This method takes a dumped ast and types it using predesigned code.
	 * 
	 * @param ast -
	 *            the ast to be typed
	 * @returns the typed AST in string representation
	 * @throws Exception
	 */
	public static String solutionSemant(String ast) throws Exception {
		StringReader in = new StringReader(ast);
		StringWriter out = new StringWriter();
		ASTSolutionParser parser = new ASTSolutionParser(new ASTLexer(in));

		treeNodesSolution.Program p = (treeNodesSolution.Program) parser
				.parse().value;

		p.semant();
		p.dumpWithTypes(out, 0);
		return out.toString();
	}

	
	public static treeNodes.Program lexAndParseToStudentAST(LexerFactory lexer,
			ParserFactory parser, String[] inFiles) throws Exception {
		String ast = lexAndParseToDumpedAST(lexer, parser, inFiles);
		
		StringReader in = new StringReader(ast);
		ASTStudentParser astParser = new ASTStudentParser(new ASTLexer(in));

		return (treeNodes.Program) astParser.parse().value;
	}
	
	
	/**
	 * This method takes a dumped ast and types it using student code.
	 * 
	 * @param ast -
	 *            the ast to be typed
	 * @returns the typed AST in string representation
	 * @throws Exception
	 */
	public static String studentSemant(String ast) throws Exception {
		StringReader in = new StringReader(ast);
		StringWriter out = new StringWriter();
		ASTStudentParser parser = new ASTStudentParser(new ASTLexer(in));

		treeNodes.Program p = (treeNodes.Program) parser.parse().value;

		p.semant();
		p.dumpWithTypes(out, 0);
		return out.toString();
	}

	public static void solutionCGen(String ast, PrintWriter w) throws Exception {
		StringReader in = new StringReader(ast);
		ASTSolutionParser parser = new ASTSolutionParser(new ASTLexer(in));

		treeNodesSolution.Program p = (treeNodesSolution.Program) parser
				.parse().value;

		p.cgen(w);
	}

	public static void studentCGen(String ast, PrintWriter w) throws Exception {
		StringReader in = new StringReader(ast);
		ASTStudentParser parser = new ASTStudentParser(new ASTLexer(in));

		treeNodes.Program p = (treeNodes.Program) parser.parse().value;

		p.cgen(w);
	}

	/**
	 * @param args --
	 *            arguments expected as for coolc
	 */
	public static void main(String[] args) {
		String[] inputFiles = Flags.handleFlags(args);

		/*
		 * The following command adjusts the output of the PrintWriter-class to
		 * UNIX-format (so that UNIX-spim can read it).
		 */
		System.setProperty("line.separator", "\n");

		/**
		 * Modify the following command to use your code instead of the given
		 * one. Each argument corresponds to a phase of the compiler. Use -
		 * SolutionLexerFactory for predesigned lexer, or - StudentLexerFactory
		 * for your own lexer. For the second phase, use - SolutionParserFactory
		 * for predesigned parser, or - StudentParserFactory for your own
		 * parser. The arguments for the third and the fourth phase are boolean,
		 * where - true indicates the use of predesigned code, and - false
		 * indicates the use of your own code.
		 */
		CoolCompiler coolc = new CoolCompiler(new SolutionLexerFactory(),
				new SolutionParserFactory(), true, true);

		try {
			coolc.compile(inputFiles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}