grammar TSmm;

//tendra todas las reglas gramaticas y léxicas
//primero reglas gramáticas y luego léxicas

program: (varDefinition | functionDefinition)* mainFunction EOF
             ;

varDefinition: 'let' ID (','ID)* ':' type ';'
    ;

functionDefinition: 'function' ID '(' parameters?  ')' (':' returnType)?
                    '{'
                        varDefinition*
                        statement*
                    '}'
                    ;

mainFunction: 'function' 'main' '(' ')' (':' 'void')?
                '{'
                    varDefinition* statement*
                '}'
    ;

parameters : param (',' param)* ;
param      : ID ':' simpleType ;
returnType : simpleType | 'void' ;

simpleType: 'char'
    | 'int'
    | 'number'
    ;

type: simpleType
    | array
    | struct
    ;

array: '[' INT_CONSTANT ']' type
    ;

struct: '[' varDefinition +']'
    ;

statement:  expression'=' expression';'
            | 'while' '(' expression ')' statement
            | '{' statement* '}'
            | 'if' '(' expression ')' statement ('else' statement)?
            | 'return' expression ';'
            | 'log' expression (','expression)* ';'
            | 'input' expression (','expression)* ';'
            | expression ';'
;


expression:   '(' expression ')'
            | expression '[' expression ']'
            | expression '.' expression

            | ID '(' (expression (',' expression)*)? ')'

            | '(' expression 'as' type ')'
            | '-' expression
            | '!' expression

            | expression ('*' | '%' | '/') expression
            | expression ('+' | '-') expression
            | expression ('>' | '>=' | '<' | '<=' | '!=' | '==') expression
            | expression ('&&' | '||') expression

            | INT_CONSTANT
            | ID
            | REAL_CONSTANT
            | CHAR_CONSTANT
            ;






//-------------------------------------------------

INT_CONSTANT: '0' | [1-9][0-9]*     //esto es para que el 0120, me detecte 0 y 120 y me detecte un 0 a pelo
            ;

REAL_CONSTANT: ([0-9]+ '.' [0-9]* |   '.' [0-9]+ ) EXPONENT?
                | [0-9]+ EXPONENT
            ;

//USar la palabra fragment para que no genere token
fragment EXPONENT: ('e' | 'E') [+-]? [0-9]+
            ;

//si o si que empiece y acabe por ',
//puede contener una \ y un int:constant o \t o \n o cualquier otro caracter (el .).
CHAR_CONSTANT:   '\''(
|          '\\'INT_CONSTANT
|           '\\t'
|           '\\n'
|           .
        )'\''
        ;


//primer caracter letra o _, luego numero letras guiones infinitas veces
ID: [a-zA-Z_] [a-zA-Z0-9_]*
  ;

//despues de // detecta que es esta norma, luego pilla cualquier cosa except \r o \n
COMMENT_UNALINEA: '//' ~[\r\n]* -> skip
            ;

//Cualquier caracter que va desde /* hasta otro */
COMMENT_VARIASLINEAS: '/*' .*? '*/' -> skip
            ;

WHITESPACES: [ \n\r\t]+ -> skip //Para descartar con un skip
            ;



