package treeNodes;

import generalHelpers.TreeNode;

/**
 * Defines AST constructor 'neg'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Negate extends AbstractUnaryOperation {
	/**
	 * Creates "neg" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for e1
	 */
	public Negate(int lineNumber, AbstractExpression a1) {
		super(lineNumber, a1);
	}

	public TreeNode copy() {
		return new Negate(lineNumber, (AbstractExpression) e1.copy());
	}

	protected String getTag() {
		return "neg";
	}
}
