package generalHelpers;

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

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Vector;

/**
 * Base class for lists of AST elements.
 * 
 * <p>
 * 
 * (See <a href="TreeNode.html">TreeNode</a> for a discussion of AST nodes in
 * general)
 * 
 * <p>
 * 
 * List phyla have a distinct set of operations for constructing and accessing
 * lists. For each phylum named <em>X</em> there is a phylum called <em>ListNode<X>
 * </em>.
 * 
 * <p>
 * 
 * An empty list is created with <code>new ListNode<X>(lineno)</code>. Elements 
 * may be appended to the list using either <code>addElement()</code> or
 * <code>appendElement()</code>. <code>appendElement</code> returns the
 * list itself, so calls to it may be chained, as in <code>
 * list.appendElement(Foo).appendElement(Bar).appendElement(Baz)</code>.
 * 
 * <p>
 * 
 * ListNode implements the interface Iterable to iterate through lists. If
 * you are not familiar with that interface, look it up in the Java API
 * documentation. Here's an example of iterating through a list:
 * 
 * <pre>
 *   for (Class_ c : classes) {
 *   ... do something with c ...
 *   }
 * </pre>
 * 
 * If you need to have an integer index:
 * 
 * <pre>
 * 	 int i = -1;
 *   for (Class_ c : classes) {
 *   i++;
 *   ... do something with c and i...
 *   }
 * </pre>
 * 
 * To loop through two lists of equal length in parallel:
 * 
 * <pre>
 * 	 Iterator<Class_> i1, i2;
 * 	 Class_ c1, c2;
 * 	 i1 = myListNode1.iterator();
 * 	 i2 = myListNode2.iterator();
 * 
 * 	 for(; i1.hasNext(); ) {
 * 	   c1 = i1.next();
 * 	   c2 = i2.next();
 *     ... do something with c1 and c2...
 *   }
 * </pre>
 * 
 */

public class ListNode<T extends TreeNode>  extends TreeNode  implements Iterable<T>{
	private Vector<T> elements;

	private ListNode(int lineNumber, Vector<T> elements) {
		super(lineNumber);
		this.elements = elements;
	}

	/**
	 * Builds a new list node
	 * 
	 * @param lineNumber
	 *            line in the source file from which this node came.
	 */
	public ListNode(int lineNumber) {
		super(lineNumber);
		elements = new Vector<T>();
	}

	/**
	 * Creates a deep copy of this list.
	 * 
	 * None of the elements are shared between the lists, e.g. all elements are
	 * duplicated (which is what "deep copy" means).
	 * 
	 * @return a copy of this elements vector
	 */
	public Vector<T> copyElements() {
		Vector<TreeNode> cp = new Vector<TreeNode>();
		for (T e : elements) {
			cp.addElement(e.copy());
		}
		return (Vector<T>) cp;
	}


	/**
	 * Retreives nth element of the list.
	 * 
	 * @param n
	 *            the index of the element
	 * @return the element
	 */
	private T getNth(int n) {
		return  elements.elementAt(n);
	}

	/**
	 * Retreives the length of the list.
	 * 
	 * @return the length of the list
	 */
	public int getLength() {
		return elements.size();
	}

	/**
	 * Appends an element to the list.
	 * 
	 * <p>
	 * The appendElement() method returns the list modified list of the appropriate
	 * type, so that it can be used like this:
	 * <code>l.appendElement(i).appendElement(j).appendElement(k);</code>
	 * 
	 * @param element
	 *            a node to append
	 */
	public ListNode<T> appendElement(T element) {
		elements.addElement(element);
		return this;
	}

	/**
	 *  no type-safety provided by the copy-method
	 */
	public ListNode<T> copy() {
		return (ListNode<T>) new ListNode(lineNumber, copyElements());
	}
	
	/**
	 * Pretty-prints this list to this output stream.
	 * 
	 * @param out
	 *            the output stream
	 * @param n
	 *            the number of spaces to indent the output
	 * @throws IOException 
	 */
	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n));
		out.write("list\n");
		for (T e : elements) {
			e.dump(out, n + 2);
		}
		out.write(Utilities.pad(n));
		out.write("(end_of_list)\n");
	}

	/**
	 * Returns a string representation of this list.
	 * 
	 * @return a string representation
	 */
	public String toString() {
		return elements.toString();
	}
	
	/**
	 * Returns an iterator for easy data-access.
	 * 
	 * @return an iterator over the elements in the ListNode
	 */
	public Iterator<T> iterator() {
		return elements.iterator();
	}
}
