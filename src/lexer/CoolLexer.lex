/*
 *  The scanner definition for COOL.
 */
package lexer;
import java_cup.runtime.Symbol;
import parser.TokenConstants;
import symbolHandling.AbstractSymbol;
import symbolHandling.AbstractTable;
%%
%{
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
%}
%init{
    yyline = 1;
%init}
%state BLOCK_COMMENT,STRING
%eofval{
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
%eofval}
%class CoolLexer
%cup
%implements java_cup.runtime.Scanner,MyLexer
%line	
class=["C""c"]["L""l"]["A""a"]["S""s"]["S""s"]
if=["I""i"]["F""f"]
fi=["F""f"]["I""i"]
then=["T""t"]["H""h"]["E""e"]["N""n"]
inherits=["I""i"]["N""n"]["H""h"]["E""e"]["R""r"]["I""i"]["T""t"]["S""s"]
else=["E""e"]["L""l"]["S""s"]["E""e"]
while=["W""w"]["H""h"]["I""i"]["L""l"]["E""e"]
loop=["L""l"]["O""o"]["O""o"]["P""p"]
pool=["P""p"]["O""o"]["O""o"]["L""l"]
let=["L""l"]["E""e"]["T""t"]
in=["I""i"]["N""n"]
case=["C""c"]["A""a"]["S""s"]["E""e"]
esac=["E""e"]["S""s"]["A""a"]["C""c"]
of=["O""o"]["F""f"]
new=["N""n"]["E""e"]["W""w"]
isvoid=["I""i"]["S""s"]["V""v"]["O""o"]["I""i"]["D""d"]
not=["N""n"]["O""o"]["T""t"]
true=["t"]["R""r"]["U""u"]["E""e"]
false=["f"]["A""a"]["L""l"]["S""s"]["E""e"]
digit=[0-9]
integer={digit}+
letterUpper=[A-Z]
letterLower=[a-z]
anyLetter={letterUpper}|{letterLower}
typeID={letterUpper}({anyLetter}|{digit}|\_)*
objectID={letterLower}({anyLetter}|{digit}|\_)*
comment=\-\-.*\n
badcomment=\-\-.*$
whitespace=[\f\r\t\n\013\040]
notascii=[^\x00-\x7F]
invalidCharacter=[\x00-\x07\x0E-\x1F\x21\x23-\x26\x3F\x5B\x5D-\x5E\x60\x7C\x7F\x27\x3E]
%%

<YYINITIAL> 	"(*" 		{ this.commentDepth=1;yybegin(BLOCK_COMMENT); }
<YYINITIAL> 	{class}		{ return new Symbol(TokenConstants.CLASS); }
<YYINITIAL> 	{if}		{ return new Symbol(TokenConstants.IF); }
<YYINITIAL> 	{fi}		{ return new Symbol(TokenConstants.FI); }
<YYINITIAL> 	{then}		{ return new Symbol(TokenConstants.THEN); }
<YYINITIAL> 	{inherits}	{ return new Symbol(TokenConstants.INHERITS); }
<YYINITIAL> 	{else}		{ return new Symbol(TokenConstants.ELSE); }
<YYINITIAL> 	{while}		{ return new Symbol(TokenConstants.WHILE); }
<YYINITIAL> 	{loop}		{ return new Symbol(TokenConstants.LOOP); }
<YYINITIAL> 	{pool}		{ return new Symbol(TokenConstants.POOL); }
<YYINITIAL> 	{let}		{ return new Symbol(TokenConstants.LET); }
<YYINITIAL> 	{in}		{ return new Symbol(TokenConstants.IN); }
<YYINITIAL> 	{case}		{ return new Symbol(TokenConstants.CASE); }
<YYINITIAL> 	{esac}		{ return new Symbol(TokenConstants.ESAC); }
<YYINITIAL> 	{of}		{ return new Symbol(TokenConstants.OF); }
<YYINITIAL> 	{new}		{ return new Symbol(TokenConstants.NEW); }
<YYINITIAL> 	{true}		{ return new Symbol(TokenConstants.BOOL_CONST,true); }
<YYINITIAL> 	{false}		{ return new Symbol(TokenConstants.BOOL_CONST,false); }
<YYINITIAL> 	"."			{ return new Symbol(TokenConstants.DOT); }
<YYINITIAL> 	"@"			{ return new Symbol(TokenConstants.AT); }
<YYINITIAL> 	"~"			{ return new Symbol(TokenConstants.NEG); }
<YYINITIAL> 	{isvoid}	{ return new Symbol(TokenConstants.ISVOID); }
<YYINITIAL> 	"*"			{ return new Symbol(TokenConstants.MULT); }
<YYINITIAL> 	"/"			{ return new Symbol(TokenConstants.DIV); }
<YYINITIAL> 	"+"			{ return new Symbol(TokenConstants.PLUS); }
<YYINITIAL> 	"-"			{ return new Symbol(TokenConstants.MINUS); }
<YYINITIAL> 	"<="		{ return new Symbol(TokenConstants.LE); }
<YYINITIAL> 	"<"			{ return new Symbol(TokenConstants.LT); }
<YYINITIAL> 	"="			{ return new Symbol(TokenConstants.EQ); }
<YYINITIAL> 	{not}		{ return new Symbol(TokenConstants.NOT); }
<YYINITIAL> 	"<-"		{ return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL> 	"{"			{ return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL> 	"}"			{ return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL> 	"("			{ return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL> 	")"			{ return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL> 	":"			{ return new Symbol(TokenConstants.COLON); }
<YYINITIAL> 	"=>"		{ return new Symbol(TokenConstants.DARROW); }
<YYINITIAL> 	","			{ return new Symbol(TokenConstants.COMMA); }
<YYINITIAL> 	";"			{ return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>		"\""		{ this.builder.setLength(0);this.badString=false;yybegin(STRING); }
<YYINITIAL> 	"*)" 		{System.err.println("LEXICAL ERROR: Invalid Closing comment *) at line "+yyline); return new Symbol(TokenConstants.ERROR,"Invalid Closing comment *) at line "+yyline); }
<YYINITIAL> 	{comment} 	{}
<YYINITIAL> 	{badcomment} 	{System.err.println("LEXICAL ERROR: EOF in comment: line "+yyline); return new Symbol(TokenConstants.ERROR,"EOF in comment: line "+yyline);}
<YYINITIAL> 	{objectID} 	{ return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); }
<YYINITIAL>		{typeID} 	{ return new Symbol(TokenConstants.TYPEID,AbstractTable.idtable.addString(yytext())); }
<YYINITIAL> 	{integer} 	{
						 		long val;
						 		try { val=Long.parseLong(yytext()); } catch(NumberFormatException e){
									System.err.println("LEXICAL ERROR: Integer Constant invalid or too big: line "+yyline+": "+yytext());
									return new Symbol(TokenConstants.ERROR,"Integer Constant invalid or too big: line "+yyline+": "+yytext());
						 		}
						 		if ( val> MAX_INT) {  System.err.println("LEXICAL ERROR: Integer Constant too Big: line "+yyline+": "+yytext()); return new Symbol(TokenConstants.ERROR,"Integer Constant too Big: line "+yyline+": "+yytext()); }
						        else return new Symbol(TokenConstants.INT_CONST,AbstractTable.inttable.addString(yytext()));
					  		}
					  		
<BLOCK_COMMENT> 	"*)" 	{this.commentDepth--;if(this.commentDepth==0){yybegin(YYINITIAL);}}
<BLOCK_COMMENT> 	"(*" 	{this.commentDepth++;}
<BLOCK_COMMENT>		\n|. 	{ }
<STRING> 	"\b" 	{builder.append('\b');}
<STRING> 	"\t" 	{builder.append('\t');}
<STRING> 	"\n" 	{builder.append('\n');}
<STRING> 	\\\n 	{builder.append('\n');}
<STRING> 	"\f" 	{builder.append('\f');}

<STRING> 	"\""	{ 
						yybegin(YYINITIAL); 
						if ( this.badString ){ System.err.println("LEXICAL ERROR: Invalid String Constant at line "+yyline);return new Symbol(TokenConstants.ERROR,"Invalid String Constant at line "+yyline ); }
						else return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(builder.toString())); 
			 		 }
			 		
<STRING> 	\\.		 	{ builder.append(yytext().substring(1,yytext().length()));}
<STRING> 	{notascii} 	{ this.badString=true;}
<STRING> 	\n 			{ this.badString=true;}
<STRING> 	[^\n\x00] 	{ builder.append(yytext());}
<YYINITIAL> 	\'.*\'		{ System.err.println("LEXICAL ERROR: Invalid String at line (single quotes) at line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid String at line (single quotes) at line "+yyline+": "+yytext() ); }
<YYINITIAL> {whitespace} { }
<YYINITIAL> 	{invalidCharacter}+   	{ System.err.println("LEXICAL ERROR: Invalid character(s): line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid character(s): line "+yyline+": "+yytext());}
<YYINITIAL> 	.   	{ System.err.println("LEXICAL ERROR: Invalid character: line "+yyline+": "+yytext());return new Symbol(TokenConstants.ERROR,"Invalid character: line "+yyline+": "+yytext());}
						
