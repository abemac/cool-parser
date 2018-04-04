package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

/**
 * Defines AST constructor 'cond'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Conditional extends AbstractExpression {
	protected AbstractExpression pred;

	protected AbstractExpression then_exp;

	protected AbstractExpression else_exp;

	/**
	 * Creates "cond" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for pred
	 * @param a1
	 *            initial value for then_exp
	 * @param a2
	 *            initial value for else_exp
	 */
	public Conditional(int lineNumber, AbstractExpression a1,
			AbstractExpression a2, AbstractExpression a3) {
		super(lineNumber);
		pred = a1;
		then_exp = a2;
		else_exp = a3;
	}

	public AbstractExpression getPred() {
		return pred;
	}

	public AbstractExpression getElseExpression() {
		return else_exp;
	}

	public AbstractExpression getThenExpression() {
		return then_exp;
	}

	public TreeNode copy() {
		return new Conditional(lineNumber, (AbstractExpression) pred.copy(),
				(AbstractExpression) then_exp.copy(),
				(AbstractExpression) else_exp.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "cond\n");
		pred.dump(out, n + 2);
		then_exp.dump(out, n + 2);
		else_exp.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_cond\n");
		pred.dumpWithTypes(out, n + 2);
		then_exp.dumpWithTypes(out, n + 2);
		else_exp.dumpWithTypes(out, n + 2);
		dumpType(out, n);
	}

}
