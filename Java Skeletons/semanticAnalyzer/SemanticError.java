package semanticAnalyzer;

import generalHelpers.TreeNode;

import java.io.PrintStream;

import symbolHandling.AbstractSymbol;
import treeNodes.Class_;

/**
 * This class is used as a general exception class for whatever semantic error
 * can occur. Feel free to derive specialized classes or use only this one.
 * Whenever this class is instanciated, the static error-count will be incremented
 * to keep track of the total number of errors.
 * After semantic analysis, exitIfErrorOccured() can be used to generate a final
 * error message and to reset the counter.
 */
@SuppressWarnings("serial")
public class SemanticError extends Exception {
	private static int semantErrors = 0;

	private static PrintStream errorStream = System.err;

	public SemanticError(String errorMessage) {
		super(errorMessage);
		semantError();
	}

	public SemanticError(String errorMessage, Class_ c) {
		super(makeErrorMessage(c) + errorMessage);
		semantError();
	}

	public SemanticError(String errorMessage, AbstractSymbol filename,
			TreeNode t) {
		super(makeErrorMessage(filename, t) + errorMessage);
		semantError();
	}

	/**
	 * Constructor to create an exception which will reset the class and serves
	 * as a general "there were semantic errors" note
	 */
	private SemanticError() {
		super("Total error-count: " + semantErrors);
		semantErrors = 0;
	}
	
	/**
	 * Increments semantic error count and prints the error message.
	 */
	private void semantError() {
		semantErrors++;
		errorStream.println(getMessage());
	}

	/**
	 * Returns an error-information-string with the line number and file name of
	 * the given class.
	 * 
	 * @param c
	 *            the class
	 * @return a string which can be used as (part of) the error message
	 * 
	 */
	protected static String makeErrorMessage(Class_ c) {
		return makeErrorMessage(c.getFilename(), c) + "in class "
				+ c.getName().getString() + ": ";
	}

	/**
	 * Returns a string containing the file name and the line number of the
	 * given tree node.
	 * 
	 * @param filename
	 *            the file name
	 * @param t
	 *            the tree node
	 * @return a string which can be used as (part of) the error message
	 * 
	 */
	protected static String makeErrorMessage(AbstractSymbol filename, TreeNode t) {
		return filename + ":" + t.getLineNumber() + ": ";
	}

	/**
	 * Checks if any errors occured. If so, an information is printed and the
	 * program is stopped.
	 * @throws SemanticError 
	 */
	public static void exitIfErrorsOccured() throws SemanticError {
		if (semantErrors != 0) {
			errorStream
					.println("Compilation halted due to static semantic errors.");
			throw new SemanticError();
		}
	}
}