package ast;

import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

public interface Visitor<TP, TR> {


    TR visit(FunctionDefinition functionDefinition, TP param);
    TR visit(VarDefinition varDefinition, TP param);

    TR visit(Arithmetic arithmetic, TP param);
    TR visit(ArrayAccess arrayAccess, TP param);
    TR visit(Cast cast, TP param);
    TR visit(CharLiteral charLiteral, TP param);
    TR visit(Comparison comparison, TP param);
    TR visit(FieldAccess fieldAccess, TP param);
    TR visit(FunctionInvocation functionInvocation, TP param);
    TR visit(IntLiteral intLiteral, TP param);
    TR visit(Logical logical, TP param);
    TR visit(NumberLiteral numberLiteral, TP param);
    TR visit(UnaryMinus unaryMinus, TP param);
    TR visit(UnaryNot unaryNot, TP param);
    TR visit(Variable variable, TP param);

    TR visit(Assignment assignment, TP param);
    TR visit(IfElse ifElse, TP param);
    TR visit(Input input, TP param);
    TR visit(Invocation invocation, TP param);
    TR visit(Log log, TP param);
    TR visit(Return returnClass, TP param);
    TR visit(While whileClass, TP param);

    TR visit(ArrayType arrayType, TP param);
    TR visit(CharType charType, TP param);
    TR visit(ErrorType errorType, TP param);
    TR visit(FunctionType functionType, TP param);
    TR visit(IntType intType, TP param);
    TR visit(NumberType numberType, TP param);
    TR visit(RecordField recordField, TP param);
    TR visit(RecordType recordType, TP param);
    TR visit(VoidType voidType, TP param);

    TR visit(Program program, TP param);



}
