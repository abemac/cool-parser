package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;


import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'let'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Let extends AbstractExpression {
	protected AbstractSymbol identifier;

	protected AbstractSymbol type_decl;

	protected AbstractExpression init;

	protected AbstractExpression body;

	/**
	 * Creates "let" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for identifier
	 * @param a1
	 *            initial value for type_decl
	 * @param a2
	 *            initial value for init
	 * @param a3
	 *            initial value for body
	 */
	public Let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2,
			AbstractExpression a3, AbstractExpression a4) {
		super(lineNumber);
		identifier = a1;
		type_decl = a2;
		init = a3;
		body = a4;
	}

	public AbstractExpression getInit() {
		return init;
	}
	
	public AbstractExpression getBody() {
		return body;
	}
	
	public TreeNode copy() {
		return new Let(lineNumber, copyAbstractSymbol(identifier),
				copyAbstractSymbol(type_decl), (AbstractExpression) init
						.copy(), (AbstractExpression) body.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "let\n");
		dumpAbstractSymbol(out, n + 2, identifier);
		dumpAbstractSymbol(out, n + 2, type_decl);
		init.dump(out, n + 2);
		body.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_let\n");
		dumpAbstractSymbol(out, n + 2, identifier);
		dumpAbstractSymbol(out, n + 2, type_decl);
		init.dumpWithTypes(out, n + 2);
		body.dumpWithTypes(out, n + 2);
		dumpType(out, n);
	}
}