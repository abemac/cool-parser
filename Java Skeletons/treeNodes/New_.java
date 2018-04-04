package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'new_'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class New_ extends AbstractExpression {
	protected AbstractSymbol type_name;

	/**
	 * Creates "new_" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for type_name
	 */
	public New_(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		type_name = a1;
	}

	public TreeNode copy() {
		return new New_(lineNumber, copyAbstractSymbol(type_name));
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "new_\n");
		dumpAbstractSymbol(out, n + 2, type_name);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_new\n");
		dumpAbstractSymbol(out, n + 2, type_name);
		dumpType(out, n);
	}

}
