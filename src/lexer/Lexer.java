package lexer;

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

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import parser.TokenConstants;

import java_cup.runtime.Symbol;
import astLexerAndParser.CoolTokenLexer;

/**
 * The lexer driver class
 */
public class Lexer {

	public static CoolTokenLexer getTokensFromFile(LexerFactory lexer,
			String file) throws Exception {
		String[] files = { file };
		return new CoolTokenLexer(new StringReader(lexFiles(lexer, files)));
	}

	public static CoolTokenLexer getTokensFromFiles(LexerFactory lexer,
			String[] files) throws Exception {
		return new CoolTokenLexer(new StringReader(lexFiles(lexer, files)));
	}

	/**
	 * Loops over lexed tokens, printing them out to the string
	 */
	public static String lexFiles(LexerFactory lexer, String[] files)
			throws Exception {
		StringWriter writer = new StringWriter();
		for (int i = 0; i < files.length; i++) {
			FileReader file = null;
			file = new FileReader(files[i]);

			writer.write("#name \"" + files[i] + "\"\n");

			MyLexer myLexer = lexer.getLexer(file);
			myLexer.set_filename(files[i]);
			Symbol s = null;
			PrintWriter w = new PrintWriter(writer);
			try {
				while ((s = myLexer.next_token()).sym != TokenConstants.EOF) {
					Utilities.dumpToken(w, myLexer.curr_lineno(), s);
				}
				w.flush();
				w.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return writer.toString();
	}

	public static void main(String[] args) {
		args = Flags.handleFlags(args);

		try {
			System.out.print(lexFiles(new StudentLexerFactory(), args));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}