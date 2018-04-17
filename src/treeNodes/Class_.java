package treeNodes;


import java.io.IOException;
import java.io.Writer;

import generalHelpers.ListNode;
import generalHelpers.TreeNode;
import generalHelpers.Utilities;
import semanticAnalyzer.ClassTable;
import semanticAnalyzer.Info;
import symbolHandling.AbstractSymbol;
import symbolHandling.SymbolTable;

/** Defines AST constructor 'class_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
public class Class_ extends TreeNode {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected ListNode<AbstractFeature> features;
    protected AbstractSymbol filename;
    /** Creates "class_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public Class_(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, ListNode<AbstractFeature> a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new Class_(lineNumber, copyAbstractSymbol(name), copyAbstractSymbol(parent), features.copy(), copyAbstractSymbol(filename));
    }
    public void dump(Writer out, int n) throws IOException {
        out.write(Utilities.pad(n) + "class_\n");
        dumpAbstractSymbol(out, n+2, name);
        dumpAbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dumpAbstractSymbol(out, n+2, filename);
    }

    
    public AbstractSymbol getFilename() { return filename; }
    
    public void dumpWithTypes(Writer out, int n) throws IOException {
        dumpLine(out, n);
        out.write(Utilities.pad(n) + "_class\n");
        dumpAbstractSymbol(out, n + 2, name);
        dumpAbstractSymbol(out, n + 2, parent);
        out.write(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.write("\"\n" + Utilities.pad(n + 2) + "(\n");
        for(AbstractFeature f : features) {
        	f.dumpWithTypes(out, n + 2);
        }
        out.write(Utilities.pad(n + 2) + ")\n");
    }
    
	public ListNode<AbstractFeature> getFeatures() {
		return features;
	}
	public AbstractSymbol getName() {
		return name;
	}
	public AbstractSymbol getParent() {
		return parent;
	}
	
	public void semant(SymbolTable<Info> info, ClassTable ct, Class_ c) {
		
	}
}

