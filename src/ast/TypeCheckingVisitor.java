package ast;


import ast.definition.Definition;
import ast.definition.FunctionDefinition;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

import java.util.ArrayList;
import java.util.List;

public class TypeCheckingVisitor extends AbstractVisitor<Type, Boolean> {

    @Override
    public Boolean visit(IntLiteral intLiteral, Type param) {
        intLiteral.setType(IntType.getInstance());
        return true;
    }

    @Override
    public Boolean visit(CharLiteral charLiteral, Type param) {
        charLiteral.setType(CharType.getInstance());
        return true;
    }

    @Override
    public Boolean visit(NumberLiteral numberLiteral, Type param) {
        numberLiteral.setType(NumberType.getInstance());
        return true;
    }

    @Override
    public Boolean visit(Variable variable, Type param) {
        Type type = variable.getDefinition().getType();
        variable.setType(type);
        if (param != null) variable.getType().mustPromoteTo(param, variable);
        return true;
    }


    @Override
    public Boolean visit(Arithmetic arithmetic, Type param) {
        arithmetic.getLeft().accept(this, null);
        arithmetic.getRight().accept(this, null);

        Type izq = arithmetic.getLeft().getType();
        Type der = arithmetic.getRight().getType();

        Type resultado = izq.arithmetic(der, arithmetic);
        arithmetic.setType(resultado);

        if (param != null) arithmetic.getType().mustPromoteTo(param, arithmetic);
        return true;
    }

    @Override
    public Boolean visit(Comparison comparison, Type param) {
        comparison.getLeft().accept(this, null);
        comparison.getRight().accept(this, null);

        Type izq = comparison.getLeft().getType();
        Type der = comparison.getRight().getType();

        Type resultado = izq.comparison(der, comparison);
        comparison.setType(resultado);

        if (param != null) comparison.getType().mustPromoteTo(param, comparison);
        return true;
    }

    @Override
    public Boolean visit(Logical logical, Type param) {
        logical.getLeft().accept(this, null);
        logical.getRight().accept(this, null);

        Type izq = logical.getLeft().getType();
        Type der = logical.getRight().getType();

        Type resultado = izq.logical(der, logical);
        logical.setType(resultado);

        if (param != null) logical.getType().mustPromoteTo(param, logical);
        return true;
    }

    @Override
    public Boolean visit(UnaryMinus unaryMinus, Type param) {
        unaryMinus.getExpression().accept(this, null);

        Type tipo = unaryMinus.getExpression().getType();
        Type resultado = tipo.unaryMinus(unaryMinus);

        unaryMinus.setType(resultado);

        if (param != null) unaryMinus.getType().mustPromoteTo(param, unaryMinus);
        return true;
    }

    @Override
    public Boolean visit(UnaryNot unaryNot, Type param) {
        unaryNot.getExpression().accept(this, null);

        Type tipo = unaryNot.getExpression().getType();
        Type resultado = tipo.unaryNot(unaryNot);

        unaryNot.setType(resultado);

        if (param != null) unaryNot.getType().mustPromoteTo(param, unaryNot);
        return true;
    }

    @Override
    public Boolean visit(ArrayAccess arrayAccess, Type param) {
        arrayAccess.getLeft().accept(this, null);
        arrayAccess.getIndex().accept(this, null);

        Type tipoArray = arrayAccess.getLeft().getType();
        Type tipoIndice = arrayAccess.getIndex().getType();

        Type resultado = tipoArray.squareBrackets(tipoIndice, arrayAccess);
        arrayAccess.setType(resultado);

        if (param != null) arrayAccess.getType().mustPromoteTo(param, arrayAccess);
        return true;
    }

    @Override
    public Boolean visit(FieldAccess fieldAccess, Type param) {
        fieldAccess.getExpression().accept(this, null);

        Type tipoExpr = fieldAccess.getExpression().getType();
        Type resultado = tipoExpr.dot(fieldAccess.getFieldName(), fieldAccess);

        fieldAccess.setType(resultado);

        if (param != null) fieldAccess.getType().mustPromoteTo(param, fieldAccess);
        return true;
    }

    @Override
    public Boolean visit(FunctionInvocation invocation, Type param) {
        invocation.getVariable().accept(this, null);

        List<Type> argumentTypes = new ArrayList<>();
        for (Expression arg : invocation.getArguments()) {
            arg.accept(this, null);
            argumentTypes.add(arg.getType());
        }

        Type tipoFuncion = invocation.getVariable().getType();
        Type resultado = tipoFuncion.parenthesis(argumentTypes, invocation);

        invocation.setType(resultado);

        if (param != null) invocation.getType().mustPromoteTo(param, invocation);
        return true;
    }

    @Override
    public Boolean visit(Assignment assignment, Type param) {
        assignment.getLeft().accept(this, null);
        assignment.getRight().accept(this, null);

        if (!assignment.getLeft().getLValue()) {
            new ErrorType("Se requiere un L-Value a la izquierda de la asignación.", assignment.getLeft());
        }

        Type tipoIzq = assignment.getLeft().getType();
        Type tipoDer = assignment.getRight().getType();

        tipoDer.mustPromoteTo(tipoIzq, assignment);

        return true;
    }

    @Override
    public Boolean visit(IfElse ifElse, Type param) {
        ifElse.getCondition().accept(this, null);

        ifElse.getCondition().getType().mustBeLogical(ifElse.getCondition());

        for (Statement s : ifElse.getIfBody()) {
            s.accept(this, param);
        }
        for (Statement s : ifElse.getElseBody()) {
            s.accept(this, param);
        }

        return true;
    }

    @Override
    public Boolean visit(While whileStmt, Type param) {
        whileStmt.getCondition().accept(this, null);

        whileStmt.getCondition().getType().mustBeLogical(whileStmt.getCondition());

        for (Statement s : whileStmt.getBody()) {
            s.accept(this, param);
        }

        return true;
    }

    @Override
    public Boolean visit(Input input, Type param) {
        for (Expression expr : input.getExpressions()) {
            expr.accept(this, null);
            expr.getType().mustBeBuiltIn(expr);
        }
        return true;
    }

    @Override
    public Boolean visit(Log log, Type param) {
        for (Expression expr : log.getExpressions()) {
            expr.accept(this, null);
            expr.getType().mustBeBuiltIn(expr);
        }
        return true;
    }
    @Override
    public Boolean visit(Return returnStmt, Type param) {
        if (returnStmt.getExpression() != null) {
            returnStmt.getExpression().accept(this, null);
            Type tipoRetorno = returnStmt.getExpression().getType();

            if (param != null) {
                tipoRetorno.mustPromoteTo(param, returnStmt);
            }
        } else {
            if (param != null) {
                VoidType.getInstance().mustPromoteTo(param, returnStmt);
            }
        }
        return true;
    }

    @Override
    public Boolean visit(FunctionDefinition funcDef, Type param) {
        funcDef.getType().accept(this, null);

        Type returnType = ((FunctionType) funcDef.getType()).getReturnType();

        for (Statement s : funcDef.getStatements()) {
            s.accept(this, returnType);
        }

        return true;
    }

    @Override
    public Boolean visit(Program program, Type param) {
        for (Definition def : program.getDefinitions()) {
            def.accept(this, null);
        }
        return true;
    }
}



