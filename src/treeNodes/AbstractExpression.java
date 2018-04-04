package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;

/** 
 * Defines simple phylum AbstractExpression 
 */
abstract public class AbstractExpression extends TreeNode {
	protected AbstractExpression(int lineNumber) {
		super(lineNumber);
	}

	private AbstractSymbol type = null;
	
	public AbstractSymbol getType() {
		return type;
	}

	public AbstractExpression setType(AbstractSymbol s) {
		type = s;
		return this;
	}

	public abstract void dumpWithTypes(Writer out, int n) throws IOException;

	protected void dumpType(Writer out, int n) throws IOException {
		if (type != null) {
			out.write(Utilities.pad(n) + ": " + type.getString() + "\n");
		} else {
			out.write(Utilities.pad(n) + ": _no_type\n");
		}
	}
}
