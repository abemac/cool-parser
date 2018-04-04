package treeNodesSolution;

import generalHelpers.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import codeGenerationSolution.CgenClassTable;

import semanticAnalyzerSolution.ClassTable;
import semanticAnalyzerSolution.Info;
import semanticAnalyzerSolution.SemanticError;
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
		for (Class_ c : classes) {
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
	 * the type field in each Expression node. (see the class
	 * AbstractExpression)
	 * </ol>
	 * <p>
	 * You are free to first do (1) and make sure you catch all semantic errors.
	 * Part (2) can be done in a second stage when you want to test the complete
	 * compiler.
	 * 
	 * @throws SemanticError
	 */
	public void semant() throws SemanticError {
		/* ClassTable constructor may do some semantic analysis */
		ClassTable classTable = new ClassTable(classes);

		// analyze semantics of every class in the program
		for (Class_ c : classes) {
			c.semant(new SymbolTable<Info>(), classTable, c);
		}

		/* some semantic analysis code may go here */

		SemanticError.exitIfErrorsOccured();
	}

	/**
	 * This method is the entry point to the code generator. All of the work of
	 * the code generator takes place within CgenClassTable constructor.
	 * 
	 * @param s
	 *            the output stream
	 * @see CgenClassTable
	 */
	public void cgen(PrintWriter s) {
		// all work is done in the constructor of the CGenClassTable
		// CgenClassTable codegen_classtable =
		new CgenClassTable(classes, s);
	}

	public void check(treeNodes.Program other, PrintWriter w) {
		for (treeNodes.Class_ c : other.getClasses()) {
			try{
				findclass(c.getName().getString()).check(c, w);
			} catch (Exception e) {
				w.println("Student parsed class " + c.getName() + " not found.");
			}
		}
		Boolean found;
		for (treeNodesSolution.Class_ c : classes) {
			found = false;
			for(treeNodes.Class_ c1 : other.getClasses()) {
				if(c.getName().getString().compareTo(c1.getName().getString())==0) found = true;
			}
			if(!found) w.println("Solver parsed class " + c.getName() + " not found.");
		}
	}

	private Class_ findclass(String classname) throws Exception {
		for (Class_ c : classes) {
			if (c.getName().getString().equals(classname))
				return c;
		}
		
		throw new Exception("Class not found");
	}
}