package treeNodes;

import generalHelpers.TreeNode;

/**
 * Defines AST constructor 'comp'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Complement extends AbstractUnaryOperation {

	/**
	 * Creates "comp" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for e1
	 */
	public Complement(int lineNumber, AbstractExpression a1) {
		super(lineNumber, a1);
	}

	public TreeNode copy() {
		return new Complement(lineNumber, (AbstractExpression) e1.copy());
	}

	protected String getTag() {
		return "comp";
	}
}
