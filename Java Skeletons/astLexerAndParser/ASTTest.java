package astLexerAndParser;

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

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;




import treeNodesSolution.Program;

/** The parser driver class */
public class ASTTest {

	/** Reads lexed tokens from consosle, and outputs the parser tree */
	public static void main(String[] args) {
		args = Flags.handleFlags(args);
		try {
			ASTLexer lexer = new ASTLexer(new InputStreamReader(System.in));
			ASTSolutionParser parser = new ASTSolutionParser(lexer);
			Object result = (Flags.parser_debug ? parser.debug_parse() : parser
					.parse()).value;
			((Program) result).dumpWithTypes(new OutputStreamWriter(System.out), 0);
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}