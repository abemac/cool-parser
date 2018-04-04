package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

/**
 * Defines AST constructor 'loop'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Loop extends AbstractExpression {
	protected AbstractExpression pred;

	protected AbstractExpression body;

	/**
	 * Creates "loop" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for pred
	 * @param a1
	 *            initial value for body
	 */
	public Loop(int lineNumber, AbstractExpression a1, AbstractExpression a2) {
		super(lineNumber);
		pred = a1;
		body = a2;
	}

	public TreeNode copy() {
		return new Loop(lineNumber, (AbstractExpression) pred.copy(),
				(AbstractExpression) body.copy());
	}

	public AbstractExpression getPred() {
		return pred;
	}
	
	public AbstractExpression getBody() {
		return body;
	}
	
	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "loop\n");
		pred.dump(out, n + 2);
		body.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_loop\n");
		pred.dumpWithTypes(out, n + 2);
		body.dumpWithTypes(out, n + 2);
		dumpType(out, n);
	}

}
