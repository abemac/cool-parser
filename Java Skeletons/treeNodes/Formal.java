package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'formal'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Formal extends TreeNode {
	protected AbstractSymbol name;

	protected AbstractSymbol type_decl;

	/**
	 * Creates "formal" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 * @param a1
	 *            initial value for type_decl
	 */
	public Formal(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
		super(lineNumber);
		name = a1;
		type_decl = a2;
	}

	public TreeNode copy() {
		return new Formal(lineNumber, copyAbstractSymbol(name),
				copyAbstractSymbol(type_decl));
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "formal\n");
		dumpAbstractSymbol(out, n + 2, name);
		dumpAbstractSymbol(out, n + 2, type_decl);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_formal\n");
		dumpAbstractSymbol(out, n + 2, name);
		dumpAbstractSymbol(out, n + 2, type_decl);
	}
	
	public AbstractSymbol getName() {
		return name;
	}
	public AbstractSymbol getTypeDeclaration() {
		return type_decl;
	}
	

}