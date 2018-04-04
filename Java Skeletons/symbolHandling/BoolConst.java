package symbolHandling;


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

import generalHelpers.TreeConstants;

import java.io.PrintWriter;

import codeGenerationSolution.CgenSupport;



/**
 * This clas encapsulates all aspects of code generation for boolean constants.
 * String constants and Int constants are handled by StringTable and IntTable
 * respectively, but since there are only two boolean constants, we handle them
 * here.
 */

public class BoolConst {
	private boolean val;

	/**
	 * Creates a new boolean constant.
	 * 
	 * @param val
	 *            the value
	 */
	public BoolConst(boolean val) {
		this.val = val;
	}

	/**
	 * Creates a new boolean constant.
	 * 
	 * @param val
	 *            the value
	 */
	public BoolConst(Boolean val) {
		this.val = val.booleanValue();
	}

	public final static BoolConst truebool = new BoolConst(true);

	public final static BoolConst falsebool = new BoolConst(false);

	/**
	 * Emits a reference to this boolean constant.
	 * 
	 * @param s
	 *            the output stream
	 */
	public void codeRef(PrintWriter s) {
		s.print(CgenSupport.BOOLCONST_PREFIX + (val ? "1" : "0"));
	}

	/**
	 * Generates code for the boolean constant definition.
	 * 
	 * @param boolclasstag
	 *            the class tag for string object
	 * @param s
	 *            the output stream
	 * 
	 */
	public void codeDef(int boolclasstag, PrintWriter s) {
		// Add -1 eye catcher
		s.println(CgenSupport.WORD + "-1");
		codeRef(s);
		s.print(CgenSupport.LABEL); // label
		s.println(CgenSupport.WORD + boolclasstag); // tag
		s.println(CgenSupport.WORD
				+ (CgenSupport.DEFAULT_OBJFIELDS + CgenSupport.BOOL_SLOTS)); // size
		s.println(CgenSupport.WORD + TreeConstants.Bool + CgenSupport.DISPTAB_SUFFIX);
		s.println(CgenSupport.WORD + (val ? "1" : "0")); // value (0 or 1)
	}
}
