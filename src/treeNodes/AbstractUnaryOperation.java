package treeNodes;

import java.io.IOException;
import java.io.Writer;

import generalHelpers.Utilities;


/**
 * This class is used to join the common code of all unary operations.
 * From class all unary operations (arithmetical, logical and other) are
 * derived. 
 */
public abstract class AbstractUnaryOperation extends AbstractExpression {
	protected AbstractExpression e1;

	public AbstractUnaryOperation(int lineNumber, AbstractExpression a1) {
		super(lineNumber);
		e1 = a1;
	}

	public AbstractExpression getExpr() {
		return e1;
	}
	
	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + getTag() + "\n");
		e1.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_" + getTag() + "\n");
		e1.dumpWithTypes(out, n + 2);
		dumpType(out, n);
	}
	
	/**
	 * This function is used by the dump-functions to specialize
	 * output to the subclass
	 * @return a tag specific to the subclass
	 */
	protected abstract String getTag();
}
