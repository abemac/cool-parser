package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;


import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'method'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Method extends AbstractFeature {
	protected ListNode<Formal> formals;

	protected AbstractSymbol return_type;

	protected AbstractExpression expr;

	/**
	 * Creates "method" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for formals
	 * @param a2
	 *            initial value for return_type
	 * @param a3
	 *            initial value for expr
	 */
	public Method(int lineNumber, AbstractSymbol a1, ListNode<Formal> a2,
			AbstractSymbol a3, AbstractExpression a4) {
		super(lineNumber);
		name = a1;
		formals = a2;
		return_type = a3;
		expr = a4;
	}

	public TreeNode copy() {
		return new Method(lineNumber, copyAbstractSymbol(name),
				formals.copy(), copyAbstractSymbol(return_type),
				(AbstractExpression) expr.copy());
	}
	
	public AbstractExpression getExpr() {
		return expr;
	}

	public ListNode<Formal> getFormals() {
		return formals;
	}
	
	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "method\n");
		dumpAbstractSymbol(out, n + 2, name);
		formals.dump(out, n + 2);
		dumpAbstractSymbol(out, n + 2, return_type);
		expr.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_method\n");
		dumpAbstractSymbol(out, n + 2, name);
		for(Formal f : formals) {
			f.dumpWithTypes(out, n + 2);
		}
		dumpAbstractSymbol(out, n + 2, return_type);
		expr.dumpWithTypes(out, n + 2);
	}

}
