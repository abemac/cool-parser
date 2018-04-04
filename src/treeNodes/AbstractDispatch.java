package treeNodes;

import symbolHandling.AbstractSymbol;
import generalHelpers.ListNode;


/**
 * This class is used to join the common code the two dispatch classes.
 * From this class "Dispatch" and "StaticDisatch" are derived.
 */

public abstract class AbstractDispatch extends AbstractExpression {
	protected AbstractExpression expr;

	protected AbstractSymbol name;

	protected ListNode<AbstractExpression> actual;

	protected AbstractDispatch(int lineNumber, AbstractExpression expression,
			AbstractSymbol name, ListNode<AbstractExpression> actuals) {
		super(lineNumber);
		expr = expression;
		this.name = name;
		actual = actuals;
	}
	
	public ListNode<AbstractExpression> getActuals() {
		return actual;
	}
	
	public AbstractExpression getExpression() {
		return expr;
	}
}
