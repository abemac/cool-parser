package parser;

/*
 Copyright (c) 2000 The Regents of the University of California.
 All rights reserved.

 Permission to use, copy, modify, and distribute this software for any
 purpose, without fee, and without written agreement is hereby granted,
 provided that the above copyright notice and the following two
 paragraphs appear in all copies of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
 DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
 CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
 PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

import generalHelpers.Flags;
import generalHelpers.Utilities;

import java.io.OutputStreamWriter;

import lexer.Lexer;
import lexer.SolutionLexerFactory;
import lexer.StudentLexerFactory;
import astLexerAndParser.CoolTokenLexer;

import treeNodes.Program;

/** The parser driver class */
public class Parser {

	public static <F extends ParserFactory> F lexAndParse(F f,
			CoolTokenLexer lexer) throws LexParseError {
		f.setLexer(lexer);

		try {
			if (Flags.parser_debug)
				f.debug_parse();
			else
				f.parse();
		} catch (Exception e) {
			e.printStackTrace();
			throw new LexParseError(e);
		}

		if (f.errorsDuringParse()) {
			throw new LexParseError("There were lex/parse errors...");
		}

		return f;
	}

	public static <F extends ParserFactory> F lexAndParseCoolToken(F f, String[] files) throws Exception {
		return lexAndParse(f, Lexer.getTokensFromFiles(new SolutionLexerFactory(), files));
	}
	
	public static <F extends ParserFactory> F lexAndParseCoolSource(F f, String[] files) throws Exception {
		return lexAndParse(f, Lexer.getTokensFromFiles(new StudentLexerFactory(), files));
	}

	/** 
	 * Reads lexed tokens from consosle, and outputs the parse tree 
	 */
	public static void main(String[] args) {
		args = Flags.handleFlags(args);
		try {
			Program result = lexAndParseCoolSource(new StudentParserFactory(), args)
				.getResultAST();
			OutputStreamWriter out = new OutputStreamWriter(System.out);
			result.dumpWithTypes(out, 0);
			out.flush();
		} catch (LexParseError e) {
			System.err.println("Compilation halted due to lex and parse errors");
			System.exit(1);
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			Utilities.fatalError("Unexpected exception");
		}
	}
}