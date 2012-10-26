grammar Calc;

@members {
	private int[] store = new int[26];
	// ... storage for variables 'a', ..., 'z'
}

// Programs 

prog
	:	com* EOF
	;

// Commands

com
	:	PUT v=expr EOL       { System.out.println(v); }
	|	SET ID ASSN
		  v=expr EOL         { int a =
		                         $ID.text.charAt(0) - 'a'; 
		                       store[a] = v; }
    |   SCOM com* FCOM
	;

// Expressions

expr		                     returns [int value]
	:	v1=term              { $value = v1; }
		( PLUS v2=term       { $value += v2; }
		| MINUS v2=term      { $value -= v2; }
		| TIMES v2=term      { $value *= v2; }
        | DIV v2=term        { $value /= v2; }
		)*
	;

term		                     returns [int value]
	:	NUM                  { $value = Integer.parseInt(
		                         $NUM.text); }
	|	ID                   { int a =
		                         $ID.text.charAt(0) - 'a'; 
		                       $value = store[a]; }
	|	LPAR v=expr RPAR     { $value = v; }
	;

// Lexicon

PUT	:	'put' ;
SET	:	'set' ;

ASSN	:	'=' ;
PLUS	:	'+' ;
MINUS	:	'-' ;
TIMES	:	'*' ;
DIV     :   '/' ;
LPAR	:	'(' ;
RPAR	:	')' ;
SCOM    :   '/*';
FCOM    :   '*/';

ID	:	'a'..'z' ;
NUM	:	'0'..'9'+ ;

EOL	:	'\r'? '\n' ;

SPACE	:	(' ' | '\t')+        { skip(); } ;
