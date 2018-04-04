package semanticAnalyzer;

import testEnvironment.CoolCompiler;
import treeNodes.Program;
import generalHelpers.Flags;

import java.io.OutputStreamWriter;

import parser.SolutionParserFactory;
import parser.SolutionStudentParserFactory;

import lexer.SolutionLexerFactory;

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



/**
 *  Static semantics driver class 
 *  
 *	The entry point of your semantic checker is in Program.semant().   
 *  
 */
public class Semant {

	/** Reads AST from from consosle, and outputs the new AST */
	public static void main(String[] args) {
		args = Flags.handleFlags(args);
		try {
			Program result = CoolCompiler.lexAndParseToStudentAST(new SolutionLexerFactory(), 
					new SolutionStudentParserFactory(), 
					args);
			
			// The call to the semantic analyzer
			result.semant();
			
			OutputStreamWriter out = new OutputStreamWriter(System.out); 
			result.dumpWithTypes(out, 0);
			out.flush();
			out.close();
		} catch (SemanticError e) {
			System.err.println(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}
