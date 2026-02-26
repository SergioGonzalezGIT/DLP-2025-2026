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
    :
      { $ast = new Program(new ArrayList<>()); }
            ( d=definition { $ast.addDefinitions($d.ast); } )* EOF
    ;



definition returns [List<Definition> ast = new ArrayList<>()]
    : v=varDefinition { $ast.addAll($v.ast); }
    | f=functionDefinition { $ast.add($f.ast); }
    | m=mainFunction { $ast.add($m.ast); }
    ;

//TIENE QUE DEVOLVER UNA LISTA DE VARDEFINITIONS O DEFINITIONS,
varDefinition returns [List<VarDefinition> ast = new ArrayList<>()]
    : OP = 'let' ids+=ID (',' ids+=ID)* ':' t=type ';'
    {
        for (Token idToken : $ids) {
            $ast.add(new VarDefinition($OP.getLine(), $OP.getCharPositionInLine() + 1, idToken.getText(), $t.ast));
        }
    }
    ;

functionDefinition returns [FunctionDefinition ast = null]
    : OP='function' ID '('
      { $ast = new FunctionDefinition($OP.getLine(), $OP.getCharPositionInLine() + 1, $ID.getText(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); }

      ( p=parameters { $ast.addParameters($p.ast); } )? ')' (':' returnType)?
      '{'
          ( v=varDefinition { $ast.addLocalVariables($v.ast); } )*
          ( s=statement { $ast.addStatement($s.ast); } )*
      '}'
    ;

mainFunction returns [FunctionDefinition ast = null]
    : OP='function' 'main' '(' ')' (':' 'void')?
      { $ast = new FunctionDefinition($OP.getLine(), $OP.getCharPositionInLine() + 1, "main", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); }
      '{'
          // 2. Le inyectamos variables locales y sentencias sobre la marcha
          ( v=varDefinition { $ast.addLocalVariables($v.ast); } )*
          ( s=statement { $ast.addStatement($s.ast); } )*
      '}'
    ;

parameters returns [List<VarDefinition> ast = new ArrayList<>()]
    : p1=param { $ast.add($p1.ast); } ( ',' p2=param { $ast.add($p2.ast); } )* ;

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

array returns [Type ast = null]
    : '[' INT_CONSTANT ']' t=type
      {
          $ast = new ArrayType(LexerHelper.lexemeToInt($INT_CONSTANT.getText()), $t.ast);
      }
    ;

struct returns [Type ast = null]
    : '['
      { $ast = new RecordType(new ArrayList<>(), new ArrayList<>()); }

      ( v=varDefinition { ((RecordType)$ast).addFields($v.ast); } )+
      ']'
    ;

statement returns [Statement ast = null]:

                e1=expression'=' e2=expression';' {$ast = new Assignment($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast);}
            | OP='while' '(' e1=expression ')' b1=block
                          { $ast = new While($OP.getLine(), $OP.getCharPositionInLine() + 1, $e1.ast, $b1.ast); }
            | OP='if' '(' e1=expression ')' b1=block ('else' b2=block { $ast = new IfElse($OP.getLine(), $OP.getCharPositionInLine() + 1, $e1.ast, $b1.ast, $b2.ast); } )?
                          { if ($ast == null) $ast = new IfElse($OP.getLine(), $OP.getCharPositionInLine() + 1, $e1.ast, $b1.ast, new ArrayList<>()); }
            | OP='return' e1=expression ';' {$ast = new Return ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast);}
            | OP='log'
                          { $ast = new Log($OP.getLine(), $OP.getCharPositionInLine() + 1, new ArrayList<>()); }
                          ex1=expression { ((Log)$ast).addExpression($ex1.ast); }
                          ( ',' ex2=expression { ((Log)$ast).addExpression($ex2.ast); } )* ';'
            | OP='input'
                          { $ast = new Input($OP.getLine(), $OP.getCharPositionInLine() + 1, new ArrayList<>()); }
                          ex1=expression { ((Input)$ast).addExpression($ex1.ast); }
                          ( ',' ex2=expression { ((Input)$ast).addExpression($ex2.ast); } )* ';'
            | ID '('
                          { $ast = new Invocation($ID.getLine(), $ID.getCharPositionInLine() + 1, new Variable($ID.getLine(), $ID.getCharPositionInLine() + 1, $ID.getText()), new ArrayList<>()); }
                          ( ex1=expression { ((Invocation)$ast).addArgument($ex1.ast); }
                            ( ',' ex2=expression { ((Invocation)$ast).addArgument($ex2.ast); } )* )?
                          ')' ';'
;

block returns [List<Statement> ast = new ArrayList<Statement>()] :
          s1=statement { $ast.add($s1.ast); }
        | '{' ( s2=statement { $ast.add($s2.ast); } )* '}'
        ;

expression returns [Expression ast = null]:
                OP='(' e1=expression ')' { $ast = $e1.ast; }
            | e1=expression OP='[' e2=expression ']' {$ast = new ArrayAccess ($e1.ast.getLine(),$e1.ast.getColumn(), $e1.ast, $e2.ast );}
            | e1=expression '.' ID { $ast = new FieldAccess($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $ID.getText()); }

            | ID '('
                          { $ast = new FunctionInvocation($ID.getLine(), $ID.getCharPositionInLine() + 1, new Variable($ID.getLine(), $ID.getCharPositionInLine() + 1, $ID.getText()), new ArrayList<>()); }
                          ( ex1=expression { ((FunctionInvocation)$ast).addArgument($ex1.ast); }
                            ( ',' ex2=expression { ((FunctionInvocation)$ast).addArgument($ex2.ast); } )* )?
                          ')'

            | OP='(' e1=expression 'as' t1=type ')' {$ast = new Cast ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast, $t1.ast );}
            | OP='-' e1=expression {$ast = new UnaryMinus ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast);}
            | OP='!' e1=expression {$ast = new UnaryNot ($OP.getLine(), $OP.getCharPositionInLine()+1, $e1.ast);}

            | e1=expression OP=('*' | '%' | '/') e2=expression {$ast = new Arithmetic ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}
            | e1=expression OP=('+' | '-') e2=expression {$ast = new Arithmetic ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}
            | e1=expression OP=('>' | '>=' | '<' | '<=' | '!=' | '==') e2=expression {$ast = new Comparison ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}
            | e1=expression OP=('&&' | '||') e2=expression {$ast = new Logical ($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast, $OP.getText() );}

            | INT_CONSTANT {$ast= new IntLiteral ($INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToInt( $INT_CONSTANT.getText()));}
            | ID {$ast= new Variable ($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.getText());}
            | REAL_CONSTANT  {$ast= new NumberLiteral ($REAL_CONSTANT.getLine(), $REAL_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToReal( $REAL_CONSTANT.getText()));}
            | CHAR_CONSTANT  {$ast= new CharLiteral ($CHAR_CONSTANT.getLine(), $CHAR_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToChar( $CHAR_CONSTANT.getText()));}
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



