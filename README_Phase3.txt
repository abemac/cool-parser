TEAM MEMBERS:
Abraham McIlvaine
Boris Huang

HOW TO RUN:

Our generated Sym.java constants were different than those in TokenContants.java, 
so we had to modify TokenContants.java to have the correct values. For this reason,
we have included our entire Eclipse project for you to run. You can either make a
run configuration manually, or run the build.xml ant file with target="Parser".

The above steps will run "test.cl" . This file contains tests for all the features 
in the rubric.

For error catching, an example of it in action can be seen by running "test-errors.cl"
This can be done from the bin directory by:
java parser.Parser ../test-errors.cl
WRITE UP (first due date):

We made a complete grammer specification for COOL. To do this, we 
basically just followed Section 11 of the COOL manual, although there
were some things that needed to be slightly modifed. Section 11 of 
the COOL manual contains some regular expressions that have to be 
converted into a grammer. For example, whenever the COOL manual had 
a + or * regex, we had to define a list in the grammer. 

To handle the let statements, we treated multiple variable declarations as
nested let statements, and giving each let statement's expression body as 
the next let statement in the series. This seemed to avoid the shift-reduce
conflict described in the intructuctions. We did not come across the shift-
reduce confict, but our grammer works for let statments any number of variables
at least 1 or greater, as specified by the cool manual.

Initially, we ran into some shift reduce conflicts because of bad grammer 
definitions. Instead of adding precedences to fix this, we fixed our grammer 
to be unambiguous. We did add precedences for the operators described 
in the COOL manual.

To test our parser, we used our programs from project 1, the example programs 
from project 2, and also some simple programs we wrote to test specific
features. In all cases, our parser matches the solution parser exactly.
It only differs in the error messages produces if the input is invalid.

WRITE UP (second due date):

We completed most our work for the second due date for during the first due date,
so see the previous write up (above) for most of our design process.

For this phase, we did have to add error handling using the "error" non terminal.
We found this a bit tricky to use, with unexpected behavoir sometimes. However,
We have used it in a way that meets the minimum requirements listed on the rubric
for error handling, and also have used it in other places to handle as many
errors gracefully as possible. See our cool.cup file, and wherever we have 
System.err.println(), we have used the error handling non terminal. For example,
in addition to the minimun requirments, we have also implemented error handling for
if statements, while loops, assignment statements, and others.

Our error handling can be tested by running "test-errors.cl" 