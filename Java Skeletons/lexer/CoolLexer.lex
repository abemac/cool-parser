/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

%{
    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    int curr_lineno() {
	return yyline;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
%}

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

%init{
    yyline = 1;
%init}

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

%eofval{
    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
    }
    return new Symbol(TokenConstants.EOF); 
%eofval}

/* Do not modify the following three jlex directives */

%class CoolLexer
%cup
%line

/* Define names for regular expressions here */

DARROW = "=>"

/* Define lexical rules after the %% separator.  Don't forget that:
   - Comments should be properly nested
   - Keywords are case-insensitive except for the values true and false,
     which must begin with a lower-case letter
   - String constants adhere to C syntax and may contain escape sequences:
     \c is accepted for all characters c; except for \n \t \b \f,
     the result is c.
   - The complete Cool lexical specification is given in the 
     Cool Reference Manual */

%%

<YYINITIAL>{DARROW}		{ return new Symbol(TokenConstants.DARROW); }

<YYINITIAL>\n|.                 { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules.
                                     The default action is to print the
                                     unmatched text to the console */
                                  System.err.print(yytext()); }
