package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java_cup.runtime.Symbol;

import astLexerAndParser.CoolTokenLexer;

import semanticAnalyzer.SemanticError;
import testEnvironment.MultiStudentTest;
import treeNodes.Program;

/**
 * This class takes a filename of a JavaCup-file and creates a Parser from it
 */
public class DynamicParserFactory implements ParserFactory {
	private static final String sep = File.separator;

	private static final String curDir = System.getProperty("user.dir") + sep
			+ ".." + sep + ".." + sep;

	private Program ast = null;
	
	private Object myParser;

	private Class parserClass;
	private Constructor parserConstructor;
	private Method parse;
	private Method debug_parse;
	private Field omerrs;

	private static final String tempDir = "tempDir";
	private static final String parserFilename = tempDir + sep
			+ "cool.cup";

	private static final File temp = new File(tempDir);

	/**
	 * @param filename --
	 *            JavaCup specification file
	 * @throws  
	 * @throws Exception 
	 */
	public DynamicParserFactory(String filename) throws Exception {
		System.out.println("Check 1");
		// create temporary directory and copy file over
		temp.mkdir();
		System.out.println("Check 1.5");

		MultiStudentTest.copyFile(new File(filename), new File(parserFilename));
		System.out.println("Check 2");

		// compile JavaCup specification file to .java-files
		String[] arguments = { "-package", tempDir, "-parser",
				"CoolParser", "-symbols", "Sym", parserFilename };
		System.out.println("Check 3");

		java_cup.Main.main(arguments);
		System.out.println("Check 4");

		// compile "CoolLexer.java"
		com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
		String[] args = { tempDir + sep + "CoolParser.java" };
		int status = javac.compile(args);
		System.out.println("Check 5");

		if(status != 0) throw new Exception("Compilation of CoolParser.java terminated with status " + status);
		
		// bind methods
		parserClass = Class.forName("tempDir.CoolParser");
        parse = parserClass.getMethod("parse", new Class[0]);
		debug_parse = parserClass.getMethod("debug_parse", new Class[0]);
        parserConstructor = parserClass.getDeclaredConstructor(new Class[] {java_cup.runtime.Scanner.class});
//        omerrs = parserClass.getField("omerrs"); 
		
		System.out.println("Check 6");

    }

	public static void cleanup() {
		if (temp.exists()) {
			File[] files = temp.listFiles();

			for (File f : files)
				f.delete();
		}
		temp.delete();
	}

	public void setLexer(CoolTokenLexer lexer) {
		try {
			myParser = parserConstructor.newInstance(new Object[] {lexer});
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("Fatal error occurred");
		}
	}

	public void parse() throws Exception {
		ast = (Program) ((Symbol) parse.invoke(myParser, new Object[0])).value;
	}

	public void debug_parse() throws Exception {
		ast = (Program) ((Symbol) debug_parse.invoke(myParser, new Object[0])).value;
	}

	public boolean errorsDuringParse() {
		return false;
/*		try {
			return ((Integer) omerrs.get(myParser)).intValue() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("Fatal error occurred");
		}
*/	}

	public Program getResultAST() {
		return ast;
	}

	public void semant() throws SemanticError {
		ast.semant();
	}

	public String getDumpedAST() {
		StringWriter out = new StringWriter();

		try {
			ast.dumpWithTypes(out, 0);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}