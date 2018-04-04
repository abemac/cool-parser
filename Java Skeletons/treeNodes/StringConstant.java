package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;


import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'string_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class StringConstant extends AbstractExpression {
	protected AbstractSymbol token;

	/**
	 * Creates "string_const" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for token
	 */
	public StringConstant(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		token = a1;
	}

	public TreeNode copy() {
		return new StringConstant(lineNumber, copyAbstractSymbol(token));
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "string_const\n");
		dumpAbstractSymbol(out, n + 2, token);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_string\n");
		out.write(Utilities.pad(n + 2) + "\"");
		Utilities.printEscapedString(out, token.getString());
		out.write("\"\n");
		dumpType(out, n);
	}
}