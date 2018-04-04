package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;


import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'attr'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Attribute extends AbstractFeature {
	protected AbstractSymbol type_decl;

	protected AbstractExpression init;

	/**
	 * Creates "attr" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for type_decl
	 * @param a2
	 *            initial value for init
	 */
	public Attribute(int lineNumber, AbstractSymbol a1, AbstractSymbol a2,
			AbstractExpression a3) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
		init = a3;
	}
	
	public AbstractSymbol getType() {
		return type_decl;
	}

	public AbstractExpression getInit() {
		return init;
	}
	
	public TreeNode copy() {
		return new Attribute(lineNumber, copyAbstractSymbol(name),
				copyAbstractSymbol(type_decl), (AbstractExpression) init.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "attr\n");
		dumpAbstractSymbol(out, n + 2, name);
		dumpAbstractSymbol(out, n + 2, type_decl);
		init.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_attr\n");
		dumpAbstractSymbol(out, n + 2, name);
		dumpAbstractSymbol(out, n + 2, type_decl);
		init.dumpWithTypes(out, n + 2);
	}

}
