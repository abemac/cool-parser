package treeNodes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import generalHelpers.ListNode;
import generalHelpers.TreeNode;
import generalHelpers.Utilities;
import semanticAnalyzer.ClassTable;
import semanticAnalyzer.Info;
import semanticAnalyzer.SemanticError;
import symbolHandling.SymbolTable;



/**
 * Defines AST constructor 'program'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
public class Program extends TreeNode {
	protected ListNode<Class_> classes;

	/**
	 * Creates "program" AST node.
	 * 
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 * @param a0
	 *            initial value for classes
	 */
	public Program(int lineNumber, ListNode<Class_> a1) {
		super(lineNumber);
		classes = a1;
	}
	
	public ListNode<Class_> getClasses() {
		return classes;
	}

	public TreeNode copy() {
		return new Program(lineNumber, classes.copy());
	}

	public void dump(Writer out, int n) throws IOException {
		out.write(Utilities.pad(n) + "program\n");
		classes.dump(out, n + 2);
	}

	public void dumpWithTypes(Writer out, int n) throws IOException {
		dumpLine(out, n);
		out.write(Utilities.pad(n) + "_program\n");
		for(Class_ c : classes) {
			c.dumpWithTypes(out, n + 1);
		}
	}

	/**
	 * This method is the entry point to the semantic checker. You will need to
	 * complete it in programming assignment 4.
	 * <p>
	 * Your checker should do the following two things:
	 * <ol>
	 * <li>Check that the program is semantically correct
	 * <li>Decorate the abstract syntax tree with type information by setting
	 * the type field in each Expression node. (see the class AbstractExpression)
	 * </ol>
	 * <p>
	 * You are free to first do (1) and make sure you catch all semantic errors.
	 * Part (2) can be done in a second stage when you want to test the complete
	 * compiler.
	 * @throws SemanticError to signal error in semantic checking
	 */
	public void semant() throws SemanticError {
		/* ClassTable constructor may do some semantic analysis */
		ClassTable classTable = new ClassTable(classes);

		/* TODO: some semantic analysis code may go here */
		for (Class_ c : classes) {
			c.semant(new SymbolTable<Info>(), classTable, c);
		}

		SemanticError.exitIfErrorsOccured();
	}

	/**
	 * This is the entry method for the code generation.
	 * 
	 * @param s -- writer to write the generated code to
	 */
	public void cgen(PrintWriter s) {
		// TODO code generation
	}
}