package treeNodes;

import generalHelpers.*;

import java.io.IOException;
import java.io.Writer;

import symbolHandling.AbstractSymbol;



/**
 *  Defines simple phylum AbstractFeature 
 */
abstract public class AbstractFeature extends TreeNode {
	protected AbstractSymbol name;

	protected AbstractFeature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dumpWithTypes(Writer out, int n) throws IOException;
    public AbstractSymbol getName() { return name; }
}