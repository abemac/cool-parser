package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;

/**
 * Defines AST constructor 'object'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Object_ extends AbstractExpression {
	protected AbstractSymbol name;

	/**
	 * Creates "object" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for name
	 */
	public Object_(int lineNumber, AbstractSymbol a1) {
		super(lineNumber);
		name = a1;
	}

	public TreeNode copy() {
		return new Object_(lineNumber, copyAbstractSymbol(name));
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "object\n");
		dumpAbstractSymbol(out, n + 2, name);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_object\n");
		dumpAbstractSymbol(out, n + 2, name);
		dumpType(out, n);
	}
}