package codeGeneration;

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
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import treeNodes.Program;

import astLexerAndParser.ASTLexer;
import astLexerAndParser.ASTStudentParser;

/** Static semantics driver class */
public class Cgen {

	/** Reads AST from from consosle, and outputs the assembly code */
	public static void main(String[] args) {
		args = Flags.handleFlags(args);
		/*
		 * The following command adjusts the output of the PrintWriter-class to
		 * UNIX-format (so that UNIX-spim can read it).
		 */
		System.setProperty("line.separator", "\n");

		try {
//			ASTLexer lexer = new ASTLexer(new FileReader(Flags.in_filename));
			ASTLexer lexer = new ASTLexer(new InputStreamReader(System.in));
			ASTStudentParser parser = new ASTStudentParser(lexer);
			Program result = (Program) parser.parse().value;

			PrintWriter output = null;
			FileWriter foutput = null;
			String filename = null;
			if (Flags.out_filename == null) {
				if (Flags.in_filename != null) {
					filename = Flags.in_filename.substring(0, Flags.in_filename
							.lastIndexOf('.'))
							+ ".s";
				}
			} else {
				filename = Flags.out_filename;
			}

			if (filename != null) {
				try {
					foutput = new FileWriter(filename);
					output = new PrintWriter(foutput);
				
					// This line needs to be activated for use of cgen
					result.cgen(output);
					
					foutput.flush();
					output.flush();
				} catch (IOException ex) {
					Utilities.fatalError("Cannot open output file " + filename);
				}
			}
			
			output.close();
			foutput.close();
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}
