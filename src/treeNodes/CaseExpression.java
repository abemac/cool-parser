package treeNodes;

import java.io.IOException;
import java.io.Writer;

import generalHelpers.*;


/**
 * Defines AST constructor 'typcase'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class CaseExpression extends AbstractExpression {
	protected AbstractExpression expr;

	protected ListNode<Branch> cases;

	/**
	 * Creates "typcase" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for expr
	 * @param a1
	 *            initial value for cases
	 */
	public CaseExpression(int lineNumber, AbstractExpression a1, ListNode<Branch> a2) {
		super(lineNumber);
		expr = a1;
		cases = a2;
	}

	public TreeNode copy() {
		return new CaseExpression(lineNumber, (AbstractExpression) expr.copy(),
				cases.copy());
	}
	
	public ListNode<Branch> getBranches() {
		return cases;
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "typcase\n");
		expr.dump(out, n + 2);
		cases.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_typcase\n");
		expr.dumpWithTypes(out, n + 2);
		for(Branch c : cases) {
			c.dumpWithTypes(out, n + 2);
		}
		dumpType(out, n);
	}
}