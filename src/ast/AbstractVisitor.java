package ast;

import ast.definition.*;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

public abstract class AbstractVisitor<TP, TR> implements Visitor<TP, TR> {

    @Override
    public TR visit(Program program, TP param) {
        for (Definition def : program.getDefinitions()) {
            def.accept(this, param);
        }
        return null;
    }

    @Override
    public TR visit(FunctionDefinition functionDefinition, TP param) {
        functionDefinition.getType().accept(this, param);
        for (Statement s : functionDefinition.getStatements()) {
            s.accept(this, param);
        }
        return null;
    }

    @Override
    public TR visit(VarDefinition varDefinition, TP param) {
        varDefinition.getType().accept(this, param);
        return null;
    }


    @Override
    public TR visit(Arithmetic arithmetic, TP param) {
        arithmetic.getLeft().accept(this, param);
        arithmetic.getRight().accept(this, param);
        return null;
    }

    @Override
    public TR visit(Comparison comparison, TP param) {
        comparison.getLeft().accept(this, param);
        comparison.getRight().accept(this, param);
        return null;
    }

    @Override
    public TR visit(Logical logical, TP param) {
        logical.getLeft().accept(this, param);
        logical.getRight().accept(this, param);
        return null;
    }

    @Override
    public TR visit(ArrayAccess arrayAccess, TP param) {
        arrayAccess.getLeft().accept(this, param);
        arrayAccess.getIndex().accept(this, param);
        return null;
    }

    @Override
    public TR visit(Cast cast, TP param) {
        cast.getExpression().accept(this, param);
        cast.getCastType().accept(this, param);
        return null;
    }

    @Override
    public TR visit(FieldAccess fieldAccess, TP param) {
        fieldAccess.getExpression().accept(this, param);
        return null;
    }

    @Override
    public TR visit(FunctionInvocation functionInvocation, TP param) {
        functionInvocation.getVariable().accept(this, param);
        for (Expression arg : functionInvocation.getArguments()) {
            arg.accept(this, param);
        }
        return null;
    }

    @Override
    public TR visit(UnaryMinus unaryMinus, TP param) {
        unaryMinus.getExpression().accept(this, param);
        return null;
    }

    @Override
    public TR visit(UnaryNot unaryNot, TP param) {
        unaryNot.getExpression().accept(this, param);
        return null;
    }

    @Override public TR visit(Variable variable, TP param) { return null; }
    @Override public TR visit(IntLiteral intLiteral, TP param) { return null; }
    @Override public TR visit(CharLiteral charLiteral, TP param) { return null; }
    @Override public TR visit(NumberLiteral numberLiteral, TP param) { return null; }

    @Override
    public TR visit(Assignment assignment, TP param) {
        assignment.getLeft().accept(this, param);
        assignment.getRight().accept(this, param);
        return null;
    }

    @Override
    public TR visit(IfElse ifElse, TP param) {
        ifElse.getCondition().accept(this, param);
        for (Statement s : ifElse.getIfBody()) s.accept(this, param);
        for (Statement s : ifElse.getElseBody()) s.accept(this, param);
        return null;
    }

    @Override
    public TR visit(While whileClass, TP param) {
        whileClass.getCondition().accept(this, param);
        for (Statement s : whileClass.getBody()) s.accept(this, param);
        return null;
    }

    @Override
    public TR visit(Input input, TP param) {
        for (Expression e : input.getExpressions()) e.accept(this, param);
        return null;
    }

    @Override
    public TR visit(Log log, TP param) {
        for (Expression e : log.getExpressions()) e.accept(this, param);
        return null;
    }

    @Override
    public TR visit(Return returnClass, TP param) {
        if (returnClass.getExpression() != null) {
            returnClass.getExpression().accept(this, param);
        }
        return null;
    }

    @Override
    public TR visit(Invocation invocation, TP param) {
        invocation.getVariable().accept(this, param);
        for (Expression arg : invocation.getArguments()) arg.accept(this, param);
        return null;
    }

    @Override
    public TR visit(ArrayType arrayType, TP param) {
        arrayType.getOf().accept(this, param);
        return null;
    }

    @Override
    public TR visit(FunctionType functionType, TP param) {
        for (VarDefinition p : functionType.getParameters()) p.accept(this, param);
        functionType.getReturnType().accept(this, param);
        return null;
    }

    @Override
    public TR visit(RecordType recordType, TP param) {
        for (RecordField f : recordType.getFields()) f.accept(this, param);
        return null;
    }

    @Override
    public TR visit(RecordField recordField, TP param) {
        recordField.getType().accept(this, param);
        return null;
    }

    @Override public TR visit(CharType charType, TP param) { return null; }
    @Override public TR visit(ErrorType errorType, TP param) { return null; }
    @Override public TR visit(IntType intType, TP param) { return null; }
    @Override public TR visit(NumberType numberType, TP param) { return null; }
    @Override public TR visit(VoidType voidType, TP param) { return null; }
}