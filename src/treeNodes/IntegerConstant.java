package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;


import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'int_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class IntegerConstant extends AbstractExpression {
	protected AbstractSymbol token;

	/**
	 * Creates "int_const" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for token
	 */
	public IntegerConstant(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		token = a1;
	}

	public TreeNode copy() {
		return new IntegerConstant(lineNumber, copyAbstractSymbol(token));
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "int_const\n");
		dumpAbstractSymbol(out, n + 2, token);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_int\n");
		dumpAbstractSymbol(out, n + 2, token);
		dumpType(out, n);
	}
}