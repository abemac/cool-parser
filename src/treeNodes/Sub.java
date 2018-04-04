package treeNodes;

import generalHelpers.*;

/**
 * Defines AST constructor 'sub'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Sub extends AbstractBinaryOperation {

	/**
	 * Creates "sub" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for e1
	 * @param a1
	 *            initial value for e2
	 */
	public Sub(int lineNumber, AbstractExpression a1, AbstractExpression a2) {
		super(lineNumber, a1, a2);
	}

	public TreeNode copy() {
		return new Sub(lineNumber, (AbstractExpression) e1.copy(),
				(AbstractExpression) e2.copy());
	}

	protected String getTag() {
		return "sub";
	}

}
