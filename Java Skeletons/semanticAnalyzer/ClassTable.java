package semanticAnalyzer;

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

// This is a project skeleton file

import generalHelpers.ListNode;
import generalHelpers.TreeConstants;
import symbolHandling.AbstractSymbol;
import symbolHandling.AbstractTable;
import treeNodes.*;

/**
 * This class may be used to contain the semantic information such as the
 * inheritance graph. You may use it or not as you like: it is only here to
 * provide a container for the supplied Methods.
 */
public class ClassTable {

	public ClassTable(ListNode<Class_> cls) {

		// TODO: do some work here
	
	}

	/**
	 * Creates data structures representing basic Cool classes (Object, IO, Int,
	 * Bool, String). Please note: as is this Method does not do anything
	 * useful; you will need to edit it to make if do what you want.
	 */
	private void installBasicClasses() {
		AbstractSymbol filename = AbstractTable.stringtable
				.addString("<basic class>");

		// The following demonstrates how to create dummy parse trees to
		// refer to basic Cool classes. There's no need for Method
		// bodies -- these are already built into the runtime system.

		// IMPORTANT: The results of the following expressions are
		// stored in local variables. You will want to do something
		// with those variables at the end of this method to make this
		// code meaningful.

		// The Object class has no parent class. Its Methods are
		// cool_abort() : Object aborts the program
		// type_name() : Str returns a string representation
		// of class name
		// copy() : SELF_TYPE returns a copy of the object

		Class_ Object_class = new Class_(0, TreeConstants.Object_,
				TreeConstants.No_class,
				new ListNode<AbstractFeature>(0).appendElement(
						new Method(0, TreeConstants.cool_abort,
								new ListNode<Formal>(0), TreeConstants.Object_,
								new NoExpression(0))).appendElement(
						new Method(0, TreeConstants.type_name,
								new ListNode<Formal>(0), TreeConstants.Str,
								new NoExpression(0))).appendElement(
						new Method(0, TreeConstants.copy, new ListNode<Formal>(
								0), TreeConstants.SELF_TYPE,
								new NoExpression(0))), filename);

		// The IO class inherits from Object. Its Methods are
		// out_string(Str) : SELF_TYPE writes a string to the output
		// out_int(Int) : SELF_TYPE " an int " " "
		// in_string() : Str reads a string from the input
		// in_int() : Int " an int " " "

		Class_ IO_class = new Class_(
				0,
				TreeConstants.IO,
				TreeConstants.Object_,
				new ListNode<AbstractFeature>(0)
						.appendElement(
								new Method(0, TreeConstants.out_string,
										new ListNode<Formal>(0)
												.appendElement(new Formal(0,
														TreeConstants.arg,
														TreeConstants.Str)),
										TreeConstants.SELF_TYPE,
										new NoExpression(0)))
						.appendElement(
								new Method(0, TreeConstants.out_int,
										new ListNode<Formal>(0)
												.appendElement(new Formal(0,
														TreeConstants.arg,
														TreeConstants.Int)),
										TreeConstants.SELF_TYPE,
										new NoExpression(0)))
						.appendElement(
								new Method(0, TreeConstants.in_string,
										new ListNode<Formal>(0),
										TreeConstants.Str, new NoExpression(0)))
						.appendElement(
								new Method(0, TreeConstants.in_int,
										new ListNode<Formal>(0),
										TreeConstants.Int, new NoExpression(0))),
				filename);

		// The Int class has no Methods and only a single Attributeibute, the
		// "val" for the integer.

		Class_ Int_class = new Class_(0, TreeConstants.Int,
				TreeConstants.Object_, new ListNode<AbstractFeature>(0)
						.appendElement(new Attribute(0, TreeConstants.val,
								TreeConstants.prim_slot, new NoExpression(0))),
				filename);

		// Bool also has only the "val" slot.
		Class_ Bool_class = new Class_(0, TreeConstants.Bool,
				TreeConstants.Object_, new ListNode<AbstractFeature>(0)
						.appendElement(new Attribute(0, TreeConstants.val,
								TreeConstants.prim_slot, new NoExpression(0))),
				filename);

		// The class Str has a number of slots and operations:
		// val the length of the string
		// str_field the string itself
		// length() : Int returns length of the string
		// concat(arg: Str) : Str performs string concatenation
		// substr(arg: Int, arg2: Int): Str substring selection

		Class_ Str_class = new Class_(
				0,
				TreeConstants.Str,
				TreeConstants.Object_,
				new ListNode<AbstractFeature>(0)
						.appendElement(
								new Attribute(0, TreeConstants.val,
										TreeConstants.Int, new NoExpression(0)))
						.appendElement(
								new Attribute(0, TreeConstants.str_field,
										TreeConstants.prim_slot,
										new NoExpression(0)))
						.appendElement(
								new Method(0, TreeConstants.length,
										new ListNode<Formal>(0),
										TreeConstants.Int, new NoExpression(0)))
						.appendElement(
								new Method(0, TreeConstants.concat,
										new ListNode<Formal>(0)
												.appendElement(new Formal(0,
														TreeConstants.arg,
														TreeConstants.Str)),
										TreeConstants.Str, new NoExpression(0)))
						.appendElement(
								new Method(
										0,
										TreeConstants.substr,
										new ListNode<Formal>(0)
												.appendElement(
														new Formal(
																0,
																TreeConstants.arg,
																TreeConstants.Int))
												.appendElement(
														new Formal(
																0,
																TreeConstants.arg2,
																TreeConstants.Int)),
										TreeConstants.Str, new NoExpression(0))),
				filename);

		// TODO: Here should go some code to use the Class_-objects defined
	}
}