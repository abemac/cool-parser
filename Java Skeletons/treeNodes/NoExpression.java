package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

/**
 * Defines AST constructor 'no_expr'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class NoExpression extends AbstractExpression {
	/**
	 * Creates "no_expr" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 */
	public NoExpression(int lineNumber) {
		super(lineNumber);
	}

	public TreeNode copy() {
		return new NoExpression(lineNumber);
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "no_expr\n");
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_no_expr\n");
		dumpType(out, n);
	}

}