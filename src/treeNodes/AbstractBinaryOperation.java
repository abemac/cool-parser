package treeNodes;

import generalHelpers.Utilities;

import java.io.IOException;
import java.io.Writer;


/**
 * This class is used to join the common code of all binary operations.
 * From this class all binary operations (arithmetical, logical and 
 * comparative) are derived. 
 */
public abstract class AbstractBinaryOperation extends AbstractExpression {
	protected AbstractExpression e1;
	protected AbstractExpression e2;

	
	public AbstractBinaryOperation(int lineNumber, AbstractExpression a1, AbstractExpression a2) {
		super(lineNumber);
		e1 = a1;
		e2 = a2;
	}
	
	public AbstractExpression getExpr1() {
		return e1;
	}

	public AbstractExpression getExpr2() {
		return e2;
	}
	
	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_" + getTag() + "\n");
		e1.dumpWithTypes(out, n + 2);
		e2.dumpWithTypes(out, n + 2);
		dumpType(out, n);
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + getTag() + "\n");
		e1.dump(out, n + 2);
		e2.dump(out, n + 2);
	}
	
	/**
	 * This function is used by the dump-functions to specialize
	 * output to the subclass.
	 * 
	 * @return a tag specific to the subclass
	 */
	protected abstract String getTag();

}
