package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;


import symbolHandling.AbstractSymbol;


/**
 * Defines AST constructor 'dispatch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Dispatch extends AbstractDispatch {
	/**
	 * Creates "dispatch" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param expression
	 *            initial value for expression to dispatch on
	 * @param name
	 *            initial value for name of method to be called
	 * @param actuals
	 *            initial value for actuals
	 */
	public Dispatch(int lineNumber, AbstractExpression expression,
			AbstractSymbol name, ListNode<AbstractExpression> actuals) {
		super(lineNumber, expression, name, actuals);
	}

	public Dispatch copy() {
		return new Dispatch(lineNumber, (AbstractExpression) expr.copy(),
				copyAbstractSymbol(name), actual.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "dispatch\n");
		expr.dump(out, n + 2);
		dumpAbstractSymbol(out, n + 2, name);
		actual.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_dispatch\n");
		expr.dumpWithTypes(out, n + 2);
		dumpAbstractSymbol(out, n + 2, name);
		out.write(Utilities.pad(n + 2) + "(\n");
		for(AbstractExpression e : actual) {
			e.dumpWithTypes(out, n + 2);
		}
		out.write(Utilities.pad(n + 2) + ")\n");
		dumpType(out, n);
	}

}
