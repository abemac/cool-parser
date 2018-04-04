package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

/**
 * Defines AST constructor 'bool_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */

public class BoolConstant extends AbstractExpression {
	protected Boolean val;

	/**
	 * Creates "bool_const" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for val
	 */
	public BoolConstant(int lineNumber, Boolean a1) {
		super(lineNumber);
		val = a1;
	}

	public TreeNode copy() {
		return new BoolConstant(lineNumber, copyBoolean(val));
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "bool_const\n");
		dumpBoolean(out, n + 2, val);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_bool\n");
		dumpBoolean(out, n + 2, val);
		dumpType(out, n);
	}

}
