package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'static_dispatch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class StaticDispatch extends AbstractDispatch {
	protected AbstractSymbol type_name;

	/**
	 * Creates "dispatch" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param expression
	 *            initial value for expression to dispatch on
	 * @param type
	 *            explicit class to dispatch at
	 * @param name
	 *            initial value for name of method to be called
	 * @param actuals
	 *            initial value for actuals
	 */
	public StaticDispatch(int lineNumber, AbstractExpression expression,
			AbstractSymbol type, AbstractSymbol name,
			ListNode<AbstractExpression> actuals) {
		super(lineNumber, expression, name, actuals);
		type_name = type;
	}

	public TreeNode copy() {
		return new StaticDispatch(lineNumber, (AbstractExpression) expr.copy(),
				copyAbstractSymbol(type_name), copyAbstractSymbol(name), actual
						.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "static_dispatch\n");
		expr.dump(out, n + 2);
		dumpAbstractSymbol(out, n + 2, type_name);
		dumpAbstractSymbol(out, n + 2, name);
		actual.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_static_dispatch\n");
		expr.dumpWithTypes(out, n + 2);
		dumpAbstractSymbol(out, n + 2, type_name);
		dumpAbstractSymbol(out, n + 2, name);
		out.write(Utilities.pad(n + 2) + "(\n");
		for (AbstractExpression e : actual) {
			e.dumpWithTypes(out, n + 2);
		}
		out.write(Utilities.pad(n + 2) + ")\n");
		dumpType(out, n);
	}
}