/*
 *  The scanner definition for COOL.
 */
package lexer;
import java_cup.runtime.Symbol;
import parser.TokenConstants;
import symbolHandling.AbstractSymbol;
import symbolHandling.AbstractTable;


class CoolLexer implements java_cup.runtime.Scanner,MyLexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    static long MAX_INT=2147483647;//max int size for 32 bit signed (2^31-1)
    static boolean badString=false;//flag for when detected an invalid string
    static int commentDepth=0;//keep track of the depth of nested comments
    StringBuffer builder = new StringBuffer();// For assembling string constants
    private AbstractSymbol filename;
    public void set_filename(String fname) {filename = AbstractTable.stringtable.addString(fname);}
	AbstractSymbol curr_filename() { return filename; }
	public int curr_lineno() { return yyline; }
	static boolean EOFerror=false;//flag that indicates error has been returned at EOF
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

    yyline = 1;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 2;
	private final int BLOCK_COMMENT = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0,
		93,
		96
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_END,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_END,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NOT_ACCEPT,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"38,66:7,63,65,39,65:2,40,66:18,65,66,37,66:4,64,1,32,2,26,35,27,20,25,59:10" +
",33,36,28,29,34,66,21,5,58,3,24,11,8,58,10,7,58:2,4,58,12,15,16,58,13,6,9,1" +
"8,23,14,58:3,66,61,66:2,60,66,42,62,44,45,46,19,43,47,48,43:2,49,43,50,51,5" +
"2,43,53,54,17,55,56,57,43:3,30,66,31,22,66,0,41")[0];

	private int yy_rmap[] = unpackFromString(1,179,
"0,1,2,3,4,5,1:5,6,7,8,1:4,9,1:4,10,1:3,11,12,11:2,1:3,13,11:3,1,14,11:6,15," +
"11:2,15,11:2,16,1:11,17,18,19,13,15,20,15:2,21,15:3,1,15:10,22,23,21,24,25," +
"1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,5" +
"0,51,52,53,54,55,56,57,58,59,60,61,62,63,11,64,65,15,66,67,68,69,70,71,72,7" +
"3,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,9" +
"8,11,15,99,100,101,102,103,104,105,106,65")[0];

	private int yy_nxt[][] = unpackFromString(107,67,
"1,2,3,4,125,168:2,65,90,170,168,172,127,168,174,94,176,5,168,66,6,7,8,168:2" +
",9,10,11,12,13,14,15,16,17,18,19,20,21,18,22:2,1,169:2,171,169,173,169,91,1" +
"26,128,95,175,169:4,177,168,23,24:2,169,24,67,22,18,-1:69,25,-1:96,26,-1:37" +
",168,178,129,168:11,131,168,131,-1:3,168:2,-1:17,129,131:6,133,131:8,168,13" +
"1:2,-1,131,-1:7,169:7,130,169:2,132,169:3,134,169,134,-1:3,169:2,-1:17,134:" +
"5,130,134:5,132,134:4,169,134:2,-1,134,-1:31,64,-1:66,31,-1,32,-1:71,33,-1:" +
"66,18,-1:3,18,-1:25,18,-1,18,-1:59,23,-1:10,168:14,131,168,131,-1:3,168:2,-" +
"1:17,131:16,168,131:2,-1,131,-1:7,168:7,153,168:6,131,168,131,-1:3,168:2,-1" +
":17,131:5,153,131:10,168,131:2,-1,131,-1:5,89:33,67,89:3,67,-1:3,89:22,34,8" +
"9,67,-1:39,76,-1:30,169:14,134,169,134,-1:3,169:2,-1:17,134:16,169,134:2,-1" +
",134,-1:6,53,-1:65,64:38,38,39,76,64:25,-1:3,168:3,137,168,27,168:3,28,168:" +
"4,131,168,27,-1:3,168:2,-1:17,131:8,28,131:3,137,131:3,168,131:2,-1,131,-1:" +
"7,169:2,136,169,70,169:9,134,169,134,-1:3,169:2,-1:17,136,134:5,70,134:9,16" +
"9,134:2,-1,134,-1:7,169:7,160,169:6,134,169,134,-1:3,169:2,-1:17,134:5,160," +
"134:10,169,134:2,-1,134,-1:5,89:38,-1:3,89:22,72,89:2,-1:32,54,-1:35,58:16," +
"59,58,60,58:19,61,-1:2,58:8,62,58:11,63,58:4,-1:3,168:4,29,168:9,131,168,13" +
"1,-1:3,168:2,-1:17,131:6,29,131:9,168,131:2,-1,131,-1:7,169:3,146,169,68,16" +
"9:3,69,169:4,134,169,68,-1:3,169:2,-1:17,134:8,69,134:3,146,134:3,169,134:2" +
",-1,134,-1:4,1,52,87,92:37,-1,1,92:25,-1:3,168:5,30,168:8,131,168,30,-1:3,1" +
"68:2,-1:17,131:16,168,131:2,-1,131,-1:7,169:5,71,169:8,134,169,71,-1:3,169:" +
"2,-1:17,134:16,169,134:2,-1,134,-1:4,1,55:36,56,-1,57,55,1,55:19,88,55:5,-1" +
":3,168:6,35,168:7,35,168,131,-1:3,168:2,-1:17,131:16,168,131:2,-1,131,-1:7," +
"169:6,73,169:7,73,169,134,-1:3,169:2,-1:17,134:16,169,134:2,-1,134,-1:7,168" +
":11,36,168:2,131,168,131,-1:3,168:2,-1:17,131:15,36,168,131:2,-1,131,-1:7,1" +
"69:11,74,169:2,134,169,134,-1:3,169:2,-1:17,134:15,74,169,134:2,-1,134,-1:7" +
",168:6,37,168:7,37,168,131,-1:3,168:2,-1:17,131:16,168,131:2,-1,131,-1:7,16" +
"9:6,75,169:7,75,169,134,-1:3,169:2,-1:17,134:16,169,134:2,-1,134,-1:7,168:8" +
",40,168:5,131,168,131,-1:3,168:2,-1:17,131:4,40,131:11,168,131:2,-1,131,-1:" +
"7,169:9,79,169:4,134,169,134,-1:3,169:2,-1:17,134:8,79,134:7,169,134:2,-1,1" +
"34,-1:7,168:13,41,131,168,131,-1:3,168:2,-1:17,131:10,41,131:5,168,131:2,-1" +
",131,-1:7,169:8,46,169:5,134,169,134,-1:3,169:2,-1:17,134:4,46,134:11,169,1" +
"34:2,-1,134,-1:7,168:9,42,168:4,131,168,131,-1:3,168:2,-1:17,131:8,42,131:7" +
",168,131:2,-1,131,-1:7,169:8,77,169:5,134,169,134,-1:3,169:2,-1:17,134:4,77" +
",134:11,169,134:2,-1,134,-1:7,168:8,43,168:5,131,168,131,-1:3,168:2,-1:17,1" +
"31:4,43,131:11,168,131:2,-1,131,-1:7,169:8,80,169:5,134,169,134,-1:3,169:2," +
"-1:17,134:4,80,134:11,169,134:2,-1,134,-1:7,44,168:13,131,168,131,-1:3,168:" +
"2,-1:17,131:2,44,131:13,168,131:2,-1,131,-1:7,81,169:13,134,169,134,-1:3,16" +
"9:2,-1:17,134:2,81,134:13,169,134:2,-1,134,-1:7,168,45,168:12,131,168,131,-" +
"1:3,168:2,-1:17,131:7,45,131:8,168,131:2,-1,131,-1:7,169:13,78,134,169,134," +
"-1:3,169:2,-1:17,134:10,78,134:5,169,134:2,-1,134,-1:7,168:3,47,168:10,131," +
"168,131,-1:3,168:2,-1:17,131:12,47,131:3,168,131:2,-1,131,-1:7,169,82,169:1" +
"2,134,169,134,-1:3,169:2,-1:17,134:7,82,134:8,169,134:2,-1,134,-1:7,168:8,4" +
"8,168:5,131,168,131,-1:3,168:2,-1:17,131:4,48,131:11,168,131:2,-1,131,-1:7," +
"169:8,49,169:5,134,169,134,-1:3,169:2,-1:17,134:4,49,134:11,169,134:2,-1,13" +
"4,-1:7,168:14,131,168,131,-1:3,168,50,-1:17,131:3,50,131:12,168,131:2,-1,13" +
"1,-1:7,169:3,83,169:10,134,169,134,-1:3,169:2,-1:17,134:12,83,134:3,169,134" +
":2,-1,134,-1:7,168:3,51,168:10,131,168,131,-1:3,168:2,-1:17,131:12,51,131:3" +
",168,131:2,-1,131,-1:7,169:8,84,169:5,134,169,134,-1:3,169:2,-1:17,134:4,84" +
",134:11,169,134:2,-1,134,-1:7,169:14,134,169,134,-1:3,169,85,-1:17,134:3,85" +
",134:12,169,134:2,-1,134,-1:7,169:3,86,169:10,134,169,134,-1:3,169:2,-1:17," +
"134:12,86,134:3,169,134:2,-1,134,-1:7,168:8,97,168:3,135,168,131,168,131,-1" +
":3,168:2,-1:17,131:4,97,131:4,135,131:6,168,131:2,-1,131,-1:7,169:8,98,169:" +
"3,148,169,134,169,134,-1:3,169:2,-1:17,134:4,98,134:4,148,134:6,169,134:2,-" +
"1,134,-1:7,168:8,99,168:3,101,168,131,168,131,-1:3,168:2,-1:17,131:4,99,131" +
":4,101,131:6,168,131:2,-1,131,-1:7,169:8,100,169:3,102,169,134,169,134,-1:3" +
",169:2,-1:17,134:4,100,134:4,102,134:6,169,134:2,-1,134,-1:7,168:3,103,168:" +
"10,131,168,131,-1:3,168:2,-1:17,131:12,103,131:3,168,131:2,-1,131,-1:7,169:" +
"8,104,169:5,134,169,134,-1:3,169:2,-1:17,134:4,104,134:11,169,134:2,-1,134," +
"-1:7,169:14,134,106,134,-1:3,169:2,-1:17,134:13,106,134:2,169,134:2,-1,134," +
"-1:7,168:2,149,168:11,131,168,131,-1:3,168:2,-1:17,149,131:15,168,131:2,-1," +
"131,-1:7,168:12,105,168,131,168,131,-1:3,168:2,-1:17,131:9,105,131:6,168,13" +
"1:2,-1,131,-1:7,169,154,169:12,134,169,134,-1:3,169:2,-1:17,134:7,154,134:8" +
",169,134:2,-1,134,-1:7,168:14,131,168,131,-1:3,151,168,-1:17,131:14,151,131" +
",168,131:2,-1,131,-1:7,169:2,156,169:11,134,169,134,-1:3,169:2,-1:17,156,13" +
"4:15,169,134:2,-1,134,-1:7,168:8,107,168:5,131,168,131,-1:3,168:2,-1:17,131" +
":4,107,131:11,168,131:2,-1,131,-1:7,169:3,108,169:10,134,169,134,-1:3,169:2" +
",-1:17,134:12,108,134:3,169,134:2,-1,134,-1:7,168:3,109,168:10,131,168,131," +
"-1:3,168:2,-1:17,131:12,109,131:3,168,131:2,-1,131,-1:7,169:3,110,169:10,13" +
"4,169,134,-1:3,169:2,-1:17,134:12,110,134:3,169,134:2,-1,134,-1:7,168:2,111" +
",168:11,131,168,131,-1:3,168:2,-1:17,111,131:15,168,131:2,-1,131,-1:7,169:2" +
",112,169:11,134,169,134,-1:3,169:2,-1:17,112,134:15,169,134:2,-1,134,-1:7,1" +
"68:4,155,168:9,131,168,131,-1:3,168:2,-1:17,131:6,155,131:9,168,131:2,-1,13" +
"1,-1:7,169:14,134,169,134,-1:3,158,169,-1:17,134:14,158,134,169,134:2,-1,13" +
"4,-1:7,168:12,113,168,131,168,131,-1:3,168:2,-1:17,131:9,113,131:6,168,131:" +
"2,-1,131,-1:7,169:12,114,169,134,169,134,-1:3,169:2,-1:17,134:9,114,134:6,1" +
"69,134:2,-1,134,-1:7,168:3,115,168:10,131,168,131,-1:3,168:2,-1:17,131:12,1" +
"15,131:3,168,131:2,-1,131,-1:7,169:12,116,169,134,169,134,-1:3,169:2,-1:17," +
"134:9,116,134:6,169,134:2,-1,134,-1:7,168:12,157,168,131,168,131,-1:3,168:2" +
",-1:17,131:9,157,131:6,168,131:2,-1,131,-1:7,169:4,162,169:9,134,169,134,-1" +
":3,169:2,-1:17,134:6,162,134:9,169,134:2,-1,134,-1:7,168:8,159,168:5,131,16" +
"8,131,-1:3,168:2,-1:17,131:4,159,131:11,168,131:2,-1,131,-1:7,169:3,118,169" +
":10,134,169,134,-1:3,169:2,-1:17,134:12,118,134:3,169,134:2,-1,134,-1:7,168" +
",117,168:12,131,168,131,-1:3,168:2,-1:17,131:7,117,131:8,168,131:2,-1,131,-" +
"1:7,169:3,120,169:10,134,169,134,-1:3,169:2,-1:17,134:12,120,134:3,169,134:" +
"2,-1,134,-1:7,168:4,119,168:9,131,168,131,-1:3,168:2,-1:17,131:6,119,131:9," +
"168,131:2,-1,131,-1:7,169:12,164,169,134,169,134,-1:3,169:2,-1:17,134:9,164" +
",134:6,169,134:2,-1,134,-1:7,168:10,161,168:3,131,168,131,-1:3,168:2,-1:17," +
"131:11,161,131:4,168,131:2,-1,131,-1:7,169:8,165,169:5,134,169,134,-1:3,169" +
":2,-1:17,134:4,165,134:11,169,134:2,-1,134,-1:7,168:4,163,168:9,131,168,131" +
",-1:3,168:2,-1:17,131:6,163,131:9,168,131:2,-1,131,-1:7,169,122,169:12,134," +
"169,134,-1:3,169:2,-1:17,134:7,122,134:8,169,134:2,-1,134,-1:7,168:6,121,16" +
"8:7,121,168,131,-1:3,168:2,-1:17,131:16,168,131:2,-1,131,-1:7,169:4,123,169" +
":9,134,169,134,-1:3,169:2,-1:17,134:6,123,134:9,169,134:2,-1,134,-1:7,169:1" +
"0,166,169:3,134,169,134,-1:3,169:2,-1:17,134:11,166,134:4,169,134:2,-1,134," +
"-1:7,169:4,167,169:9,134,169,134,-1:3,169:2,-1:17,134:6,167,134:9,169,134:2" +
",-1,134,-1:7,169:6,124,169:7,124,169,134,-1:3,169:2,-1:17,134:16,169,134:2," +
"-1,134,-1:7,168:7,139,168:6,131,168,131,-1:3,168:2,-1:17,131:5,139,131:10,1" +
"68,131:2,-1,131,-1:7,169,138,140,169:11,134,169,134,-1:3,169:2,-1:17,140,13" +
"4:6,138,134:8,169,134:2,-1,134,-1:7,168,141,168,143,168:10,131,168,131,-1:3" +
",168:2,-1:17,131:7,141,131:4,143,131:3,168,131:2,-1,131,-1:7,169,142,169,14" +
"4,169:10,134,169,134,-1:3,169:2,-1:17,134:7,142,134:4,144,134:3,169,134:2,-" +
"1,134,-1:7,168:7,145,168:6,131,168,131,-1:3,168:2,-1:17,131:5,145,131:10,16" +
"8,131:2,-1,131,-1:7,169:12,150,169,134,169,134,-1:3,169:2,-1:17,134:9,150,1" +
"34:6,169,134:2,-1,134,-1:7,168:12,147,168,131,168,131,-1:3,168:2,-1:17,131:" +
"9,147,131:6,168,131:2,-1,131,-1:7,169:7,152,169:6,134,169,134,-1:3,169:2,-1" +
":17,134:5,152,134:10,169,134:2,-1,134,-1:4");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	case STRING:
		this.badString=true;
		if(!EOFerror){
			EOFerror=true;
			System.err.println("LEXICAL ERROR: EOF in string constant");
			return new Symbol(TokenConstants.ERROR,"EOF in string constant"); 
		}
	break;
	case BLOCK_COMMENT:
		if(!EOFerror){
			EOFerror=true;
			System.err.println("LEXICAL ERROR: unmatched (*, EOF reached");
			return new Symbol(TokenConstants.ERROR,"unmatched (*, EOF reached"); 
		}
    }
    return new Symbol(TokenConstants.EOF); 
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -3:
						break;
					case 3:
						{ return new Symbol(TokenConstants.MULT); }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -5:
						break;
					case 5:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.DOT); }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.AT); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.NEG); }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.DIV); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.PLUS); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.MINUS); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.LT); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.EQ); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.COLON); }
					case -18:
						break;
					case 18:
						{ System.err.println("LEXICAL ERROR: Invalid character(s): line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid character(s): line "+yyline+": "+yytext());}
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.COMMA); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.SEMI); }
					case -21:
						break;
					case 21:
						{ this.builder.setLength(0);this.badString=false;yybegin(STRING); }
					case -22:
						break;
					case 22:
						{ }
					case -23:
						break;
					case 23:
						{
						 		long val;
						 		try { val=Long.parseLong(yytext()); } catch(NumberFormatException e){
									System.err.println("LEXICAL ERROR: Integer Constant invalid or too big: line "+yyline+": "+yytext());
									return new Symbol(TokenConstants.ERROR,"Integer Constant invalid or too big: line "+yyline+": "+yytext());
						 		}
						 		if ( val> MAX_INT) {  System.err.println("LEXICAL ERROR: Integer Constant too Big: line "+yyline+": "+yytext()); return new Symbol(TokenConstants.ERROR,"Integer Constant too Big: line "+yyline+": "+yytext()); }
						        else return new Symbol(TokenConstants.INT_CONST,AbstractTable.inttable.addString(yytext()));
					  		}
					case -24:
						break;
					case 24:
						{ System.err.println("LEXICAL ERROR: Invalid character: line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid character: line "+yyline+": "+yytext());}
					case -25:
						break;
					case 25:
						{ this.commentDepth=1;yybegin(BLOCK_COMMENT); }
					case -26:
						break;
					case 26:
						{System.err.println("LEXICAL ERROR: Invalid Closing comment *) at line "+yyline); return new Symbol(TokenConstants.ERROR,"Invalid Closing comment *) at line "+yyline); }
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.IF); }
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.IN); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.FI); }
					case -30:
						break;
					case 30:
						{ return new Symbol(TokenConstants.OF); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.LE); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.DARROW); }
					case -34:
						break;
					case 34:
						{ System.err.println("LEXICAL ERROR: Invalid String at line (single quotes) at line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid String at line (single quotes) at line "+yyline+": "+yytext() ); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.LET); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.NEW); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.NOT); }
					case -38:
						break;
					case 38:
						{}
					case -39:
						break;
					case 39:
						{System.err.println("LEXICAL ERROR: EOF in comment: line "+yyline); return new Symbol(TokenConstants.ERROR,"EOF in comment: line "+yyline);}
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.CASE); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.LOOP); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.THEN); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.ELSE); }
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.ESAC); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.POOL); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.BOOL_CONST,true); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.CLASS); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.WHILE); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.BOOL_CONST,false); }
					case -50:
						break;
					case 50:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -51:
						break;
					case 51:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -52:
						break;
					case 52:
						{ }
					case -53:
						break;
					case 53:
						{this.commentDepth++;}
					case -54:
						break;
					case 54:
						{this.commentDepth--;if(this.commentDepth==0){yybegin(YYINITIAL);}}
					case -55:
						break;
					case 55:
						{ builder.append(yytext());}
					case -56:
						break;
					case 56:
						{ 
						yybegin(YYINITIAL); 
						if ( this.badString ){ System.err.println("LEXICAL ERROR: Invalid String Constant at line "+yyline);return new Symbol(TokenConstants.ERROR,"Invalid String Constant at line "+yyline ); }
						else return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(builder.toString())); 
			 		 }
					case -57:
						break;
					case 57:
						{ this.badString=true;}
					case -58:
						break;
					case 58:
						{ builder.append(yytext().substring(1,yytext().length()));}
					case -59:
						break;
					case 59:
						{builder.append('\t');}
					case -60:
						break;
					case 60:
						{builder.append('\f');}
					case -61:
						break;
					case 61:
						{builder.append('\n');}
					case -62:
						break;
					case 62:
						{builder.append('\n');}
					case -63:
						break;
					case 63:
						{builder.append('\b');}
					case -64:
						break;
					case 65:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -65:
						break;
					case 66:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -66:
						break;
					case 67:
						{ System.err.println("LEXICAL ERROR: Invalid character(s): line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid character(s): line "+yyline+": "+yytext());}
					case -67:
						break;
					case 68:
						{ return new Symbol(TokenConstants.IF); }
					case -68:
						break;
					case 69:
						{ return new Symbol(TokenConstants.IN); }
					case -69:
						break;
					case 70:
						{ return new Symbol(TokenConstants.FI); }
					case -70:
						break;
					case 71:
						{ return new Symbol(TokenConstants.OF); }
					case -71:
						break;
					case 72:
						{ System.err.println("LEXICAL ERROR: Invalid String at line (single quotes) at line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid String at line (single quotes) at line "+yyline+": "+yytext() ); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.LET); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.NEW); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.NOT); }
					case -75:
						break;
					case 76:
						{System.err.println("LEXICAL ERROR: EOF in comment: line "+yyline); return new Symbol(TokenConstants.ERROR,"EOF in comment: line "+yyline);}
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.CASE); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.LOOP); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.THEN); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.ELSE); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.ESAC); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.POOL); }
					case -82:
						break;
					case 83:
						{ return new Symbol(TokenConstants.CLASS); }
					case -83:
						break;
					case 84:
						{ return new Symbol(TokenConstants.WHILE); }
					case -84:
						break;
					case 85:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -85:
						break;
					case 86:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -86:
						break;
					case 87:
						{ }
					case -87:
						break;
					case 88:
						{ builder.append(yytext());}
					case -88:
						break;
					case 90:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -89:
						break;
					case 91:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -90:
						break;
					case 92:
						{ }
					case -91:
						break;
					case 94:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -92:
						break;
					case 95:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -93:
						break;
					case 97:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -94:
						break;
					case 98:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -95:
						break;
					case 99:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -96:
						break;
					case 100:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -97:
						break;
					case 101:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -98:
						break;
					case 102:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -99:
						break;
					case 103:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -100:
						break;
					case 104:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -101:
						break;
					case 105:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -102:
						break;
					case 106:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -103:
						break;
					case 107:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -104:
						break;
					case 108:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -105:
						break;
					case 109:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -106:
						break;
					case 110:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -107:
						break;
					case 111:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -108:
						break;
					case 112:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -109:
						break;
					case 113:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -110:
						break;
					case 114:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -111:
						break;
					case 115:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -112:
						break;
					case 116:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -113:
						break;
					case 117:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -114:
						break;
					case 118:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -115:
						break;
					case 119:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -116:
						break;
					case 120:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -117:
						break;
					case 121:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -118:
						break;
					case 122:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -119:
						break;
					case 123:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -120:
						break;
					case 124:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -121:
						break;
					case 125:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -122:
						break;
					case 126:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -123:
						break;
					case 127:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -124:
						break;
					case 128:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -125:
						break;
					case 129:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -126:
						break;
					case 130:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -127:
						break;
					case 131:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -128:
						break;
					case 132:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -129:
						break;
					case 133:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -130:
						break;
					case 134:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -131:
						break;
					case 135:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -132:
						break;
					case 136:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -133:
						break;
					case 137:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -134:
						break;
					case 138:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -135:
						break;
					case 139:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -136:
						break;
					case 140:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -137:
						break;
					case 141:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -138:
						break;
					case 142:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -139:
						break;
					case 143:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -140:
						break;
					case 144:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -141:
						break;
					case 145:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -142:
						break;
					case 146:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -143:
						break;
					case 147:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -144:
						break;
					case 148:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -145:
						break;
					case 149:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -146:
						break;
					case 150:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -147:
						break;
					case 151:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -148:
						break;
					case 152:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -149:
						break;
					case 153:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -150:
						break;
					case 154:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -151:
						break;
					case 155:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -152:
						break;
					case 156:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -153:
						break;
					case 157:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -154:
						break;
					case 158:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -155:
						break;
					case 159:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -156:
						break;
					case 160:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -157:
						break;
					case 161:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -158:
						break;
					case 162:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -159:
						break;
					case 163:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -160:
						break;
					case 164:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -161:
						break;
					case 165:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -162:
						break;
					case 166:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -163:
						break;
					case 167:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -164:
						break;
					case 168:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -165:
						break;
					case 169:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -166:
						break;
					case 170:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -167:
						break;
					case 171:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -168:
						break;
					case 172:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -169:
						break;
					case 173:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -170:
						break;
					case 174:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -171:
						break;
					case 175:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -172:
						break;
					case 176:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -173:
						break;
					case 177:
						{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
					case -174:
						break;
					case 178:
						{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
					case -175:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
