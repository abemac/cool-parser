package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
public class Block extends AbstractExpression {
    protected ListNode<AbstractExpression> body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public Block(int lineNumber, ListNode<AbstractExpression> a1) {
        super(lineNumber);
        body = a1;
    }
    
    public ListNode<AbstractExpression> getExprs() {
    	return body;
    }
    
    public TreeNode copy() {
        return new Block(lineNumber, body.copy());
    }
    public void dump(Writer out, int n) throws IOException {
        out.write(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dumpWithTypes(Writer out, int n) throws IOException {
        dumpLine(out, n);
        out.write(Utilities.pad(n) + "_block\n");
        for(AbstractExpression e : body) {
        	e.dumpWithTypes(out, n + 2);
        }
        
        dumpType(out, n);
    }

}
