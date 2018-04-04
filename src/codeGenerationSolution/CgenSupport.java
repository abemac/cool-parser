package codeGenerationSolution;

/*
 Copyright (c) 2000 The Regents of the University of California.
 All rights reserved.

 Permission to use, copy, modify, and distribute this software for any
 purpose, without fee, and without written agreement is hereby granted,
 provided that the above copyright notice and the following two
 paragraphs appear in all copies of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
 DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
 CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
 PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 */

// This is a project skeleton file

import generalHelpers.Flags;
import generalHelpers.ListNode;
import generalHelpers.TreeConstants;

import java.io.PrintWriter;

import symbolHandling.AbstractSymbol;
import symbolHandling.AbstractTable;
import symbolHandling.BoolConst;
import symbolHandling.IntSymbol;
import symbolHandling.StringSymbol;
import treeNodesSolution.AbstractExpression;

/**
 * This class aggregates all kinds of support routines and constants for the
 * code generator; all routines are statics, so no instance of this class is
 * even created.
 */
public class CgenSupport {
	/** Runtime constants for controlling the garbage collector. */
	final public static String[] gcInitNames = { "_NoGC_Init", "_GenGC_Init",
			"_ScnGC_Init" };

	/** Runtime constants for controlling the garbage collector. */
	final public static String[] gcCollectNames = { "_NoGC_Collect", "_GenGC_Collect",
			"_ScnGC_Collect" };

	final public static int MAXINT = 100000000;

	final public static int WORD_SIZE = 4;

	final public static int LOG_WORD_SIZE = 2; // for logical shifts

	// Global names
	final public static String CLASSNAMETAB = "class_nameTab";

	final public static String CLASSOBJTAB = "class_objTab";

	final public static String INTTAG = "_int_tag";

	final public static String BOOLTAG = "_bool_tag";

	final public static String STRINGTAG = "_string_tag";

	final public static String HEAP_START = "heap_start";

	// Naming conventions
	final public static String DISPTAB_SUFFIX = "_dispTab";

	final public static String METHOD_SEP = ".";

	final public static String CLASSINIT_SUFFIX = "_init";

	final public static String PROTOBJ_SUFFIX = "_protObj";

	final public static String OBJECTPROTOBJ = "Object" + PROTOBJ_SUFFIX;

	final public static String INTCONST_PREFIX = "int_const";

	final public static String STRCONST_PREFIX = "str_const";

	final public static String BOOLCONST_PREFIX = "bool_const";

	final public static int EMPTYSLOT = 0;

	final public static String LABEL = ":\n";

	// information about object headers
	final public static int DEFAULT_OBJFIELDS = 3;

	final public static int TAG_OFFSET = 0;

	final public static int SIZE_OFFSET = 1;

	final public static int DISPTABLE_OFFSET = 2;

	final public static int STRING_SLOTS = 1;

	final public static int INT_SLOTS = 1;

	final public static int BOOL_SLOTS = 1;

	final public static String GLOBAL = "\t.globl\t";

	final public static String ALIGN = "\t.align\t2\n";

	final public static String WORD = "\t.word\t";

	// register names,
	final public static String ZERO = "$zero"; // Zero register

	final public static String ACC = "$a0"; // Accumulator

	final public static String A1 = "$a1"; // For arguments to prim funcs

	final public static String SELF = "$s0"; // Ptr to self (callee saves)

	final public static String T1 = "$t1"; // Temporary 1

	final public static String T2 = "$t2"; // Temporary 2

	final public static String T3 = "$t3"; // Temporary 3

	final public static String SP = "$sp"; // Stack pointer

	final public static String FP = "$fp"; // Frame pointer

	final public static String RA = "$ra"; // Return address

	// Opcodes
	final public static String JALR = "\tjalr\t";

	final public static String JAL = "\tjal\t";

	final public static String RET = "\tjr\t" + RA + "\t";

	final public static String SW = "\tsw\t";

	final public static String LW = "\tlw\t";

	final public static String LI = "\tli\t";

	final public static String LA = "\tla\t";

	final public static String MOVE = "\tmove\t";

	final public static String NEG = "\tneg\t";

	final public static String ADD = "\tadd\t";

	final public static String ADDI = "\taddi\t";

	final public static String ADDU = "\taddu\t";

	final public static String ADDIU = "\taddiu\t";

	final public static String DIV = "\tdiv\t";

	final public static String MUL = "\tmul\t";

	final public static String SUB = "\tsub\t";

	final public static String SLL = "\tsll\t";

	final public static String BEQZ = "\tbeqz\t";

	final public static String BRANCH = "\tb\t";

	final public static String BEQ = "\tbeq\t";

	final public static String BNE = "\tbne\t";

	final public static String BLEQ = "\tble\t";

	final public static String BLT = "\tblt\t";

	final public static String BGT = "\tbgt\t";

	/**
	 * Emits an LW instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param offset
	 *            the word offset from source register
	 * @param source_reg
	 *            the source register
	 * @param s
	 *            the output stream
	 */
	public static void emitLoad(String dest_reg, int offset, String source_reg,
			PrintWriter s) {
		s.println(LW + dest_reg + " " + offset * WORD_SIZE + "(" + source_reg
				+ ")");
	}

	public static void emitPartialLoadWord(String dest_reg, PrintWriter s) {
		s.print(LW + dest_reg + " ");
	}

	/**
	 * Emits an SW instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param offset
	 *            the word offset from source register
	 * @param source_reg
	 *            the source register
	 * @param s
	 *            the output stream
	 */
	public static void emitStore(String source_reg, int offset, String dest_reg,
			PrintWriter s) {
		s.println(SW + source_reg + " " + offset * WORD_SIZE + "(" + dest_reg
				+ ")");
	}
	
	//This functions is to produce code to jump to the routine of 
	//garbage collection.For any operation which stores something
	//into memory this function should be called
	public static void emitStoreGC(String source_reg, int offset, String dest_reg,
			PrintWriter s) {
		emitStore(source_reg, offset, dest_reg, s);
			
		// check if GC is enabled produce appropriate code to activate
		//garbage collection routine
		if (Flags.GC_GENGC == Flags.cgen_Memmgr) {
			emitAddiu(A1, dest_reg, offset * WORD_SIZE, s);
			emitGCAssign(s);
		}
	}

	/*
	 * public static void emitPartialStore(String source_reg, PrintWriter s) {
	 * s.print(SW + source_reg + " "); }
	 */
	/**
	 * Emits the LI instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param val
	 *            the integer value
	 * @param s
	 *            the output stream
	 */
	public static void emitLoadImm(String dest_reg, int val, PrintWriter s) {
		s.println(LI + dest_reg + " " + val);
	}

	/**
	 * Emits an LA instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param address
	 *            the address from which a word is loaded
	 * @param s
	 *            the output stream
	 */
	public static void emitLoadAddress(String dest_reg, String address, PrintWriter s) {
		s.println(LA + dest_reg + " " + address);
	}

	/**
	 * Emits an LA instruction without the address part.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param s
	 *            the output stream
	 */
	public static void emitPartialLoadAddress(String dest_reg, PrintWriter s) {
		s.print(LA + dest_reg + " ");
	}

	/**
	 * Emits an instruction to load a boolean constant into a register.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param b
	 *            the boolean constant
	 * @param s
	 *            the output stream
	 */
	public static void emitLoadBool(String dest_reg, BoolConst b, PrintWriter s) {
		emitPartialLoadAddress(dest_reg, s);
		b.codeRef(s);
		s.println("");
	}

	/**
	 * Emits an instruction to load a string constant into a register.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param str
	 *            the string constant
	 * @param s
	 *            the output stream
	 */
	public static void emitLoadString(String dest_reg, StringSymbol str, PrintWriter s) {
		emitPartialLoadAddress(dest_reg, s);
		str.codeRef(s);
		s.println("");
	}

	/**
	 * Emits an instruction to load an integer constant into a register.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param i
	 *            the integer constant
	 * @param s
	 *            the output stream
	 */
	public static void emitLoadInt(String dest_reg, IntSymbol i, PrintWriter s) {
		emitPartialLoadAddress(dest_reg, s);
		i.codeRef(s);
		s.println("");
	}

	/**
	 * Emits a MOVE instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param source_reg
	 *            the source register
	 * @param s
	 *            the output stream
	 */
	public static void emitMove(String dest_reg, String source_reg, PrintWriter s) {
		s.println(MOVE + dest_reg + " " + source_reg);
	}

	/**
	 * Emits a NEG instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param source_reg
	 *            the source register
	 * @param s
	 *            the output stream
	 */
	public static void emitNeg(String dest_reg, String source_reg, PrintWriter s) {
		s.println(NEG + dest_reg + " " + source_reg);
	}

	/**
	 * Emits an ADD instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param s
	 *            the output stream
	 */
	public static void emitAdd(String dest_reg, String src1, String src2, PrintWriter s) {
		s.println(ADD + dest_reg + " " + src1 + " " + src2);
	}

	/**
	 * Emits an ADDU instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param s
	 *            the output stream
	 */
	public static void emitAddu(String dest_reg, String src1, String src2,
			PrintWriter s) {
		s.println(ADDU + dest_reg + " " + src1 + " " + src2);
	}

	/**
	 * Emits an ADDIU instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param src
	 *            the source register
	 * @param imm
	 *            the immediate
	 * @param s
	 *            the output stream
	 */
	public static void emitAddiu(String dest_reg, String src, int imm, PrintWriter s) {
		s.println(ADDIU + dest_reg + " " + src + " " + imm);
	}

	/**
	 * Emits a DIV instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param s
	 *            the output stream
	 */
	public static void emitDiv(String dest_reg, String src1, String src2, PrintWriter s) {
		s.println(DIV + dest_reg + " " + src1 + " " + src2);
	}

	/**
	 * Emits a MUL instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param s
	 *            the output stream
	 */
	public static void emitMul(String dest_reg, String src1, String src2, PrintWriter s) {
		s.println(MUL + dest_reg + " " + src1 + " " + src2);
	}

	/**
	 * Emits a SUB instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param s
	 *            the output stream
	 */
	public static void emitSub(String dest_reg, String src1, String src2, PrintWriter s) {
		s.println(SUB + dest_reg + " " + src1 + " " + src2);
	}

	/**
	 * Emits an SLL instruction.
	 * 
	 * @param dest_reg
	 *            the destination register
	 * @param src1
	 *            the source register 1
	 * @param num
	 *            the number of bits to shift
	 * @param s
	 *            the output stream
	 */
	public static void emitSll(String dest_reg, String src1, int num, PrintWriter s) {
		s.println(SLL + dest_reg + " " + src1 + " " + num);
	}

	/**
	 * Emits a JALR instruction.
	 * 
	 * @param dest_reg
	 *            the register with target address
	 * @param s
	 *            the output stream
	 */
	public static void emitJalr(String dest_reg, PrintWriter s) {
		s.println(JALR + dest_reg);
	}

	/**
	 * Emits a JAL instruction.
	 * 
	 * @param dest
	 *            the target address or label
	 * @param s
	 *            the output stream
	 */
	public static void emitJal(String dest, PrintWriter s) {
		s.println(JAL + dest);
	}

	/**
	 * Emits a RET instruction.
	 * 
	 * @param s
	 *            the output stream
	 */
	public static void emitReturn(PrintWriter s) {
		s.println(RET);
	}

	/**
	 * Emits a call to gc_assign.
	 * 
	 * @param s
	 *            the output stream
	 */
	public static void emitGCAssign(PrintWriter s) {
		s.println(JAL + "_GenGC_Assign");
	}

	/**
	 * Emits a reference to dispatch table.
	 * 
	 * @param sym
	 *            the name of the class
	 * @param s
	 *            the output stream
	 */
	public static void emitDispTableRef(AbstractSymbol sym, PrintWriter s) {
		s.print(sym + DISPTAB_SUFFIX);
	}

	/**
	 * Emits a reference to class' init() method.
	 * 
	 * @param sym
	 *            the name of the class
	 * @param s
	 *            the output stream
	 */
	public static void emitInitRef(AbstractSymbol sym, PrintWriter s) {
		s.print(sym + CLASSINIT_SUFFIX);
	}

	/**
	 * Emits a reference to class' prototype object.
	 * 
	 * @param sym
	 *            the name of the class
	 * @param s
	 *            the output stream
	 */
	public static void emitProtObjRef(AbstractSymbol sym, PrintWriter s) {
		s.print(sym + PROTOBJ_SUFFIX);
	}

	/**
	 * Emits a reference to a method in a class
	 * 
	 * @param classname
	 *            the name of the class
	 * @param methodname
	 *            the name of the method
	 * @param s
	 *            the output stream
	 */
	public static void emitMethodRef(AbstractSymbol classname,
			AbstractSymbol methodname, PrintWriter s) {
		s.print(classname + METHOD_SEP + methodname);
	}

	/**
	 * Emits a reference to a label
	 * 
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitLabelRef(int label, PrintWriter s) {
		s.print("label" + label);
	}

	/**
	 * Emits a definition of a label
	 * 
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitLabelDef(int label, PrintWriter s) {
		emitLabelRef(label, s);
		s.println(":");
	}

	/**
	 * Emits a BEQZ instruction.
	 * 
	 * @param src
	 *            the source register
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBeqz(String src, int label, PrintWriter s) {
		s.print(BEQZ + src + " ");
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emits a BEQ instruction.
	 * 
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBeq(String src1, String src2, int label, PrintWriter s) {
		s.print(BEQ + src1 + " " + src2 + " ");
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emits a BNE instruction.
	 * 
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBne(String src1, String src2, int label, PrintWriter s) {
		s.print(BNE + src1 + " " + src2 + " ");
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emits a BLEQ instruction.
	 * 
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBleq(String src1, String src2, int label, PrintWriter s) {
		s.print(BLEQ + src1 + " " + src2 + " ");
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emits a BLT instruction.
	 * 
	 * @param src1
	 *            the source register 1
	 * @param src2
	 *            the source register 2
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBlt(String src1, String src2, int label, PrintWriter s) {
		s.print(BLT + src1 + " " + src2 + " ");
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emits a BLTI instruction.
	 * 
	 * @param src
	 *            the source register
	 * @param imm
	 *            the immediate
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBlti(String src, int imm, int label, PrintWriter s) {
		s.print(BLT + src + " " + imm + " ");
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emits a BGTI instruction.
	 * 
	 * @param src
	 *            the source register
	 * @param imm
	 *            the immediate
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBgti(String src, int imm, int label, PrintWriter s) {
		s.print(BGT + src + " " + imm + " ");
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emits a BRANCH instruction.
	 * 
	 * @param label
	 *            the label number
	 * @param s
	 *            the output stream
	 */
	public static void emitBranch(int label, PrintWriter s) {
		s.print(BRANCH);
		emitLabelRef(label, s);
		s.println("");
	}

	/**
	 * Emit a sequence of instructions to push a register onto stack. Stack
	 * grows toward smaller addresses.
	 * 
	 * @param reg
	 *            the register
	 * @param s
	 *            the output stream
	 */
	public static void emitPush(String reg, PrintWriter s) {
		emitStore(reg, 0, SP, s);
		emitAddiu(SP, SP, -4, s);
	}

	/**
	 * Emits code to fetch the integer value of the Integer object.
	 * 
	 * @param source
	 *            a pointer to the Integer object
	 * @param dest
	 *            the destination register for the value
	 * @param s
	 *            the output stream
	 */
	public static void emitFetchInt(String dest, String source, PrintWriter s) {
		emitLoad(dest, DEFAULT_OBJFIELDS, source, s);
	}

	/**
	 * Emits code to store the integer value of the Integer object.
	 * 
	 * @param source
	 *            an integer value
	 * @param dest
	 *            the pointer to an Integer object
	 * @param s
	 *            the output stream
	 */
	public static void emitStoreInt(String source, String dest, PrintWriter s) {
		emitStore(source, DEFAULT_OBJFIELDS, dest, s);
	}

	/**
	 * Emits code to manipulate garbage collector
	 * 
	 * @param s
	 *            the output stream
	 */
	public static void emitTestCollector(PrintWriter s) {
		emitPush(ACC, s);
		emitMove(ACC, SP, s);
		emitMove(A1, ZERO, s);
		s.println(JAL + gcCollectNames[Flags.cgen_Memmgr]);
		emitAddiu(SP, SP, 4, s);
		emitLoad(ACC, 0, SP, s);
	}

	/**
	 * Emits code to check the garbage collector
	 * 
	 * @param s
	 *            the output stream
	 */
	public static void emitGCCheck(String source, PrintWriter s) {
		if (source != A1)
			emitMove(A1, source, s);
		s.println(JAL + "_gc_check");
	}

	private static boolean ascii = false;

	/**
	 * Switch output mode to ASCII.
	 * 
	 * @param s
	 *            the output stream
	 */
	public static void asciiMode(PrintWriter s) {
		if (!ascii) {
			s.print("\t.ascii\t\"");
			ascii = true;
		}
	}

	/**
	 * Switch output mode to BYTE
	 * 
	 * @param s
	 *            the output stream
	 */
	public static void byteMode(PrintWriter s) {
		if (ascii) {
			s.println("\"");
			ascii = false;
		}
	}

	/**
	 * Emits a string constant.
	 * 
	 * @param str
	 *            the string constant
	 * @param s
	 *            the output stream
	 */
	public static void emitStringConstant(String str, PrintWriter s) {
		ascii = false;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			switch (c) {
			case '\n':
				asciiMode(s);
				s.print("\\n");
				break;
			case '\t':
				asciiMode(s);
				s.print("\\t");
				break;
			case '\\':
				byteMode(s);
				s.println("\t.byte\t" + (byte) '\\');
				break;
			case '"':
				asciiMode(s);
				s.print("\\\"");
				break;
			default:
				if (c >= 0x20 && c <= 0x7f) {
					asciiMode(s);
					s.print(c);
				} else {
					byteMode(s);
					s.println("\t.byte\t" + (byte) c);
				}
			}
		}
		byteMode(s);
		s.println("\t.byte\t0\t");
	}

	
	
	//This parameter is used to keep track of # of labels we have
	private static int labelCounter = 0;

	//While producing a code including a label, this function is called
	//to determine the name(index) of the label to be coded
	public  static int getLabel() {
		return labelCounter++;
	}

	
	public static void emitLoadBaseInstance(AbstractSymbol type, PrintWriter s) {
		if (type.equals(TreeConstants.Str)) {
			emitPartialLoadAddress(ACC, s);
			((StringSymbol) AbstractTable.stringtable.lookup("")).codeRef(s);
			s.println();
		} else if (type.equals(TreeConstants.Int)) {
			emitPartialLoadAddress(ACC, s);
			((IntSymbol) AbstractTable.inttable.lookup("0")).codeRef(s);
			s.println();
		} else if (type.equals(TreeConstants.Bool)) {
			emitPartialLoadAddress(ACC, s);
			BoolConst.falsebool.codeRef(s);
			s.println();
		} else {
			emitMove(ACC, ZERO, s);
		}
	}

	
	// function calls -- helpers

	public static void emitDispatchPreparation(CgenNode cn, AbstractExpression expr,
			ListNode<AbstractExpression> actual, CgenClassTable st, int lineNo, PrintWriter s) {

		if (actual.iterator().hasNext()) {
			// Reserve space for the parameters to be passed to the function
			emitAddiu(SP, SP, -1 * actual.getLength() * WORD_SIZE, s);
			
			int i = 0;
			// Store all the actuals to be passed to the function into memory
			for (AbstractExpression e : actual) {
				e.code(cn, st, s);
				emitStore(ACC, actual.getLength() - i, SP, s);
				i++;
			}
		}
		
		// Code the expression part of dispatch after execution of what
		// Accumulator will hold a pointer to the Object to be dispatched
		expr.code(cn, st, s);

		// below is to produce runtime-error-catching code : dispatch to void
		
		// Have a number to code a new label
		int labelNum = getLabel();

		// If the reference is not to void Object no 
		//prob skip the part of runtime dispatch error code
		emitBne(ACC, ZERO, labelNum, s);

		//The rest of the generated code directs the processor to
		//the routine for _dispatch_abort because of a null reference
		emitPartialLoadAddress(ACC, s);
		((StringSymbol) cn.getFilename()).codeRef(s);
		s.println("");
		emitLoadImm(T1, lineNo, s); /*Identify the line of error*/
		emitJal("_dispatch_abort", s); /*Go to abort routine for dispatch*/

		//code label in case of no runtime-error
		emitLabelDef(labelNum, s);
	}
	// Startup a called function: save registers and reserve space for local variables
	public static void emitFunctionInit(int extraWords, PrintWriter s) {
		emitAddiu(SP, SP, -12 - extraWords * WORD_SIZE, s);
		emitStore(FP, 3 + extraWords, SP, s);
		emitStore(SELF, 2 + extraWords, SP, s);
		emitStore(RA, 1 + extraWords, SP, s);
		emitAddiu(FP, SP, 4, s);
		emitMove(SELF, ACC, s);
	}

	//Produces code for epilogue, restores registers after execution of a function
	public static void emitEndFunction(int extraWords, int numParams, PrintWriter s) {
		emitLoad(FP, 3 + extraWords, SP, s);
		emitLoad(SELF, 2 + extraWords, SP, s);
		emitLoad(RA, 1 + extraWords, SP, s);
		emitAddiu(SP, SP, 12 + (extraWords+numParams) * WORD_SIZE, s);
		emitReturn(s);
	}
	
	//No additional cleanup is needed
	public static void emitDispatchCleanup(int numActuals, PrintWriter s) {
		
	}
}
