cd bin
java lexer.JLex lexer/CoolLexer.lex
javac lexer/CoolLexer.java

java parser.JavaCup parser/cool.cup
javac parser/Sym.java
javac parser/CoolParser.java