package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'branch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Branch extends TreeNode {
	protected AbstractSymbol name;

	protected AbstractSymbol type_decl;

	protected AbstractExpression expr;

	/**
	 * Creates "branch" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for type_decl
	 * @param a2
	 *            initial value for expr
	 */
	public Branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2,
			AbstractExpression a3) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
		expr = a3;
	}
	
	public AbstractExpression getExpr() {
		return expr;
	}

	public TreeNode copy() {
		return new Branch(lineNumber, copyAbstractSymbol(name),
				copyAbstractSymbol(type_decl), (AbstractExpression) expr.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "branch\n");
		dumpAbstractSymbol(out, n + 2, name);
		dumpAbstractSymbol(out, n + 2, type_decl);
		expr.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_branch\n");
		dumpAbstractSymbol(out, n + 2, name);
		dumpAbstractSymbol(out, n + 2, type_decl);
		expr.dumpWithTypes(out, n + 2);
	}
}