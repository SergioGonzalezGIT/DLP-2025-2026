grammar TSmm;

//tendra todas las reglas gramaticas y léxicas
//primero reglas gramáticas y luego léxicas


//getLine();
//getCharPositionInLine(x+1);
//text

@header{
import ast.expression.Expression;
import ast.expression.IntLiteral;
import ast.*;
import ast.expression.Variable;
import ast.expression.*;
import ast.definition.*;
import ast.expression.Expression;
import ast.expression.IntLiteral;
import ast.*;
import ast.expression.Variable;
import ast.expression.*;
import ast.definition.*;
import ast.statement.*;
import ast.type.*;
import java.util.List;
import java.util.ArrayList;
}



program returns [Program ast = null]
    : { $ast = new Program(new ArrayList<>()); }
      ( d1=definition { $ast.addDefinitions($d1.ast); } )*
      m=mainFunction
      {
          List<Definition> mainList = new ArrayList<>();
          mainList.add($m.ast);
          $ast.addDefinitions(mainList);
      }
      EOF
    ;


definition returns [List<Definition> ast = new ArrayList<>()]
    : v=varDefinition { $ast.addAll($v.ast); }
    | f=functionDefinition { $ast.add($f.ast); }
    ;


//TIENE QUE DEVOLVER UNA LISTA DE VARDEFINITIONS O DEFINITIONS,
//let i, j, k: int;
varDefinition returns [List<VarDefinition> ast = new ArrayList<>()]
    locals [ List<Token> idList = new ArrayList<>() ]
    : OP='let' id1=ID { $idList.add($id1); }
       ( ',' id2=ID { $idList.add($id2); } )* ':' t=type ';'

    {
        for (Token idToken : $idList) {
            $ast.add(new VarDefinition($OP.getLine(), $OP.getCharPositionInLine() + 1, idToken.getText(), $t.ast));
        }
    }
    ;

//function sumar(a: int, b: int): int { let c: int; c = a + b; return c; }
functionDefinition returns [FunctionDefinition ast = null]
    locals [ List<VarDefinition> paramsList = new ArrayList<>(), Type retType = VoidType.getInstance() ]

    : OP='function' ID '('
      ( p=parameters { $paramsList = $p.ast; } )? ')'

      (':' r=returnType { $retType = $r.ast; } )?

      {
          Type funcType = new FunctionType($paramsList, $retType);
          $ast = new FunctionDefinition($OP.getLine(), $OP.getCharPositionInLine() + 1, $ID.getText(), funcType, new ArrayList<>());
      }
      '{'
          ( v=varDefinition {
              for(VarDefinition varDef : $v.ast) {
                  $ast.addStatement(varDef);
              }
          } )*

          ( s=statement { $ast.addStatement($s.ast); } )*
      '}'
    ;

//function main() { log "Hola mundo"; }
mainFunction returns [FunctionDefinition ast = null]
    : OP='function' 'main' '(' ')' (':' 'void')?
      {
          Type funcType = new FunctionType(new ArrayList<>(), VoidType.getInstance());
          $ast = new FunctionDefinition($OP.getLine(), $OP.getCharPositionInLine() + 1, "main", funcType, new ArrayList<>());
      }
      '{'
          ( v=varDefinition {
              for(VarDefinition varDef : $v.ast) {
                  $ast.addStatement(varDef);
              }
          } )*
          ( s=statement { $ast.addStatement($s.ast); } )*
      '}'
    ;

// Ejemplo: x: int, y: char, z: number
parameters returns [List<VarDefinition> ast = new ArrayList<>()]
    : p1=param { $ast.add($p1.ast); } ( ',' p2=param { $ast.add($p2.ast); } )* ;

// Ejemplo: x: int
param returns [VarDefinition ast = null]
    : ID ':' t=simpleType
      { $ast = new VarDefinition($ID.getLine(), $ID.getCharPositionInLine() + 1, $ID.getText(), $t.ast); }
    ;

returnType returns [Type ast = null]
    : t=simpleType { $ast = $t.ast; }
    | 'void'       { $ast = VoidType.getInstance(); }
    ;

simpleType returns [Type ast = null]
    : 'char'   { $ast = CharType.getInstance(); }
    | 'int'    { $ast = IntType.getInstance(); }
    | 'number' { $ast = NumberType.getInstance(); }
    ;


type returns [Type ast = null]
    : simpleType { $ast = $simpleType.ast; }
    | array      { $ast = $array.ast; }
    | struct     { $ast = $struct.ast; }
    ;

// [10] int
array returns [Type ast = null]
    : '[' INT_CONSTANT ']' t=type
      {
          $ast = new ArrayType(LexerHelper.lexemeToInt($INT_CONSTANT.getText()), $t.ast);
      }
    ;

// [ let x:int; let y:char; ]
struct returns [Type ast = null]
    : '['
      { $ast = new RecordType(new ArrayList<>()); }

      ( v=varDefinition { ((RecordType)$ast).addFields($v.ast); } )+
      ']'
    ;

statement returns [Statement ast = null]:
                //a = 5;
                e1=expression'=' e2=expression';' {$ast = new Assignment($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast);}

            //while (i < 10) { i = i + 1; }
            | OP='while' '(' e1=expression ')' b1=block
                          { $ast = new While($OP.getLine(), $OP.getCharPositionInLine() + 1, $e1.ast, $b1.ast); }

            //if (a > b) log a; else log b;
            | OP='if' '(' e1=expression ')' b1=block ('else' b2=block { $ast = new IfElse($OP.getLine(), $OP.getCharPositionInLine() + 1, $e1.ast, $b1.ast, $b2.ast); } )?
                          { if ($ast == null) $ast = new IfElse($OP.getLine(), $OP.getCharPositionInLine() + 1, $e1.ast, $b1.ast, new ArrayList<>()); }

            //return a + b;
            | OP='return' e1=expression ';' {$ast = new Return ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast);}

            //log "El resultado es", a * 2;
            | OP='log'
                          { $ast = new Log($OP.getLine(), $OP.getCharPositionInLine() + 1, new ArrayList<>()); }
                          ex1=expression { ((Log)$ast).addExpression($ex1.ast); }
                          ( ',' ex2=expression { ((Log)$ast).addExpression($ex2.ast); } )* ';'

            //input nombre, edad;
            | OP='input'
                          { $ast = new Input($OP.getLine(), $OP.getCharPositionInLine() + 1, new ArrayList<>()); }
                          ex1=expression { ((Input)$ast).addExpression($ex1.ast); }
                          ( ',' ex2=expression { ((Input)$ast).addExpression($ex2.ast); } )* ';'

            //imprimirMenu(); o calcularDatos(x, y);
            | ID '('
                          { $ast = new Invocation($ID.getLine(), $ID.getCharPositionInLine() + 1, new Variable($ID.getLine(), $ID.getCharPositionInLine() + 1, $ID.getText()), new ArrayList<>()); }
                          ( ex1=expression { ((Invocation)$ast).addArgument($ex1.ast); }
                            ( ',' ex2=expression { ((Invocation)$ast).addArgument($ex2.ast); } )* )?
                          ')' ';'
;

block returns [List<Statement> ast = new ArrayList<Statement>()] :
          //ej: a = 2;
          s1=statement { $ast.add($s1.ast); }

          //ej: { a = 2; log a; }
        | '{' ( s2=statement { $ast.add($s2.ast); } )* '}'
        ;

expression returns [Expression ast = null]:
                // Ejemplo: (2 + 3)
                OP='(' e1=expression ')' { $ast = $e1.ast; }

            // Ejemplo: vector[5] o matriz[i][j]
            | e1=expression OP='[' e2=expression ']' {$ast = new ArrayAccess ($e1.ast.getLine(),$e1.ast.getColumn(), $e1.ast, $e2.ast );}

            // Ejemplo: p.hola
            | e1=expression '.' ID { $ast = new FieldAccess($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $ID.getText()); }

            // Ejemplo: f(1, 2.2) o p()
            | ID '('
                          { $ast = new FunctionInvocation($ID.getLine(), $ID.getCharPositionInLine() + 1, new Variable($ID.getLine(), $ID.getCharPositionInLine() + 1, $ID.getText()), new ArrayList<>()); }
                          ( ex1=expression { ((FunctionInvocation)$ast).addArgument($ex1.ast); }
                            ( ',' ex2=expression { ((FunctionInvocation)$ast).addArgument($ex2.ast); } )* )?
                          ')'

            // Ejemplo: (4.3 as int)
            | OP='(' e1=expression 'as' t1=type ')' {$ast = new Cast ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast, $t1.ast );}

            // Ejemplo: -5 o -i
            | OP='-' e1=expression {$ast = new UnaryMinus ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast);}

            // Ejemplo: !true o !p.hola
            | OP='!' e1=expression {$ast = new UnaryNot ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast);}

            // Ejemplo: 2 * 3, i % 2
            | e1=expression OP=('*' | '%' | '/') e2=expression {$ast = new Arithmetic ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}

            // Ejemplo: i + j
            | e1=expression OP=('+' | '-') e2=expression {$ast = new Arithmetic ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}

            // Ejemplo: i < 10, i == 0
            | e1=expression OP=('>' | '>=' | '<' | '<=' | '!=' | '==') e2=expression {$ast = new Comparison ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}

            // Ejemplo: a && b
            | e1=expression OP=('&&' | '||') e2=expression {$ast = new Logical ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}


            | INT_CONSTANT {$ast= new IntLiteral ($INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToInt( $INT_CONSTANT.getText()));}
            | ID {$ast= new Variable ($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.getText());}
            | REAL_CONSTANT  {$ast= new NumberLiteral ($REAL_CONSTANT.getLine(), $REAL_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToReal( $REAL_CONSTANT.getText()));}
            | CHAR_CONSTANT  {$ast= new CharLiteral ($CHAR_CONSTANT.getLine(), $CHAR_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToChar( $CHAR_CONSTANT.getText()));}
            ;






//-------------------------------------------------

INT_CONSTANT: '0' | [1-9][0-9]*     //esto es para que el 0120, me detecte 0 y 120 y me detecte un 0 a pelo
            ;

//3.14, 3., .14 con exponente opcional
//10e3 entero con exponente obligatorio
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



