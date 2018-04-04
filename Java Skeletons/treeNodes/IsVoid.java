package treeNodes;

import generalHelpers.*;


/**
 * Defines AST constructor 'isvoid'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class IsVoid extends AbstractUnaryOperation {
	/**
	 * Creates "isvoid" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for e1
	 */
	public IsVoid(int lineNumber, AbstractExpression a1) {
		super(lineNumber, a1);
	}

	public TreeNode copy() {
		return new IsVoid(lineNumber, (AbstractExpression) e1.copy());
	}


	protected String getTag() {
		return "isvoid";
	}
}