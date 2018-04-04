package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'assign'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Assign extends AbstractExpression {
	protected AbstractSymbol name;

	protected AbstractExpression expr;

	/**
	 * Creates "assign" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for expr
	 */
	public Assign(int lineNumber, AbstractSymbol a1, AbstractExpression a2) {
		super(lineNumber);
		name = a1;
		expr = a2;
	}

	public AbstractExpression getExpr() {
		return expr;
	}

	public TreeNode copy() {
		return new Assign(lineNumber, copyAbstractSymbol(name),
				(AbstractExpression) expr.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "assign\n");
		dumpAbstractSymbol(out, n + 2, name);
		expr.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_assign\n");
		dumpAbstractSymbol(out, n + 2, name);
		expr.dumpWithTypes(out, n + 2);
		dumpType(out, n);
	}

}