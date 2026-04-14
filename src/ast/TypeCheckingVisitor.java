package ast;

import ast.definition.Definition;
import ast.definition.FunctionDefinition;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

import java.util.ArrayList;
import java.util.List;

public class TypeCheckingVisitor extends AbstractVisitor<Type, Void> {

    @Override
    public Void visit(IntLiteral intLiteral, Type param) {
        intLiteral.setType(IntType.getInstance());
        return null;
    }

    @Override
    public Void visit(CharLiteral charLiteral, Type param) {
        charLiteral.setType(CharType.getInstance());
        return null;
    }

    @Override
    public Void visit(NumberLiteral numberLiteral, Type param) {
        numberLiteral.setType(NumberType.getInstance());
        return null;
    }

    @Override
    public Void visit(Variable variable, Type param) {
        Type type = variable.getDefinition().getType();
        variable.setType(type);
        if (param != null) variable.getType().mustPromoteTo(param, variable);
        return null;
    }

    // En TypeCheckingVisitor.java
    @Override
    public Void visit(Cast cast, Type param) {
        // 1. Inferimos el tipo de la expresión interna
        cast.getExpression().accept(this, null);

        // 2. Comprobamos si el tipo original admite el casteo al tipo destino
        Type originalType = cast.getExpression().getType();
        Type targetType = cast.getCastType();

        Type resultType = originalType.canBeCastTo(targetType, cast);

        // 3. Le asignamos al nodo Cast el tipo resultante (que será targetType o ErrorType)
        cast.setType(resultType);

        // 4. Propagación de tipos hacia arriba (si es necesario)
        if (param != null) {
            cast.getType().mustPromoteTo(param, cast);
        }

        return null;
    }

    @Override
    public Void visit(Arithmetic arithmetic, Type param) {
        arithmetic.getLeft().accept(this, null);
        arithmetic.getRight().accept(this, null);

        Type izq = arithmetic.getLeft().getType();
        Type der = arithmetic.getRight().getType();

        Type resultado = izq.arithmetic(der, arithmetic);
        arithmetic.setType(resultado);

        if (param != null) arithmetic.getType().mustPromoteTo(param, arithmetic);
        return null;
    }

    @Override
    public Void visit(Comparison comparison, Type param) {
        comparison.getLeft().accept(this, null);
        comparison.getRight().accept(this, null);

        Type izq = comparison.getLeft().getType();
        Type der = comparison.getRight().getType();

        Type resultado = izq.comparison(der, comparison);
        comparison.setType(resultado);

        if (param != null) comparison.getType().mustPromoteTo(param, comparison);
        return null;
    }

    @Override
    public Void visit(Logical logical, Type param) {
        logical.getLeft().accept(this, null);
        logical.getRight().accept(this, null);

        Type izq = logical.getLeft().getType();
        Type der = logical.getRight().getType();

        Type resultado = izq.logical(der, logical);
        logical.setType(resultado);

        if (param != null) logical.getType().mustPromoteTo(param, logical);
        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Type param) {
        unaryMinus.getExpression().accept(this, null);

        Type tipo = unaryMinus.getExpression().getType();
        Type resultado = tipo.unaryMinus(unaryMinus);

        unaryMinus.setType(resultado);

        if (param != null) unaryMinus.getType().mustPromoteTo(param, unaryMinus);
        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Type param) {
        unaryNot.getExpression().accept(this, null);

        Type tipo = unaryNot.getExpression().getType();
        Type resultado = tipo.unaryNot(unaryNot);

        unaryNot.setType(resultado);

        if (param != null) unaryNot.getType().mustPromoteTo(param, unaryNot);
        return null;
    }

    @Override
    public Void visit(ArrayAccess arrayAccess, Type param) {
        arrayAccess.getLeft().accept(this, null);
        arrayAccess.getIndex().accept(this, null);

        Type tipoArray = arrayAccess.getLeft().getType();
        Type tipoIndice = arrayAccess.getIndex().getType();

        Type resultado = tipoArray.squareBrackets(tipoIndice, arrayAccess);
        arrayAccess.setType(resultado);

        if (param != null) arrayAccess.getType().mustPromoteTo(param, arrayAccess);
        return null;
    }

    @Override
    public Void visit(FieldAccess fieldAccess, Type param) {
        fieldAccess.getExpression().accept(this, null);

        Type tipoExpr = fieldAccess.getExpression().getType();
        Type resultado = tipoExpr.dot(fieldAccess.getFieldName(), fieldAccess);

        fieldAccess.setType(resultado);

        if (param != null) fieldAccess.getType().mustPromoteTo(param, fieldAccess);
        return null;
    }

    @Override
    public Void visit(FunctionInvocation inv, Type param) {
        inv.getVariable().accept(this, null);

        List<Type> argTypes = new ArrayList<>();
        for (Expression arg : inv.getArguments()) {
            arg.accept(this, null);
            argTypes.add(arg.getType());
        }

        Type resultType = inv.getVariable().getType().parenthesis(argTypes, inv);

        inv.setType(resultType);


        return null;
    }

    @Override
    public Void visit(Assignment assignment, Type param) {
        assignment.getLeft().accept(this, null);
        assignment.getRight().accept(this, null);

        if (!assignment.getLeft().getLValue()) {
            new ErrorType("Se requiere un L-Value a la izquierda de la asignación.", assignment.getLeft());
        }

        Type tipoIzq = assignment.getLeft().getType();
        Type tipoDer = assignment.getRight().getType();

        tipoDer.mustPromoteTo(tipoIzq, assignment);

        return null;
    }

    @Override
    public Void visit(IfElse ifElse, Type param) {
        ifElse.getCondition().accept(this, null);

        ifElse.getCondition().getType().mustBeLogical(ifElse.getCondition());

        for (Statement s : ifElse.getIfBody()) {
            s.accept(this, param);
        }
        for (Statement s : ifElse.getElseBody()) {
            s.accept(this, param);
        }

        return null;
    }

    @Override
    public Void visit(While whileStmt, Type param) {
        whileStmt.getCondition().accept(this, null);

        whileStmt.getCondition().getType().mustBeLogical(whileStmt.getCondition());

        for (Statement s : whileStmt.getBody()) {
            s.accept(this, param);
        }

        return null;
    }

    @Override
    public Void visit(Input input, Type param) {
        for (Expression expr : input.getExpressions()) {
            expr.accept(this, null);
            expr.getType().mustBeBuiltIn(expr);
        }
        return null;
    }

    @Override
    public Void visit(Log log, Type param) {
        for (Expression expr : log.getExpressions()) {
            expr.accept(this, null);
            expr.getType().mustBeBuiltIn(expr);
        }
        return null;
    }

    @Override
    public Void visit(Return returnStmt, Type param) {
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
        return null;
    }

    @Override
    public Void visit(FunctionDefinition funcDef, Type param) {
        funcDef.getType().accept(this, null);

        Type returnType = ((FunctionType) funcDef.getType()).getReturnType();

        for (Statement s : funcDef.getStatements()) {
            s.accept(this, returnType);
        }

        return null;
    }

    @Override
    public Void visit(Program program, Type param) {
        for (Definition def : program.getDefinitions()) {
            def.accept(this, null);
        }
        return null;
    }

    @Override
    public Void visit(Invocation invocation, Type param) {
        invocation.getVariable().accept(this, null);

        List<Type> argTypes = new ArrayList<>();
        for (Expression arg : invocation.getArguments()) {
            arg.accept(this, null);
            argTypes.add(arg.getType());
        }

        invocation.getVariable().getType().parenthesis(argTypes, invocation);
        return null;
    }
}