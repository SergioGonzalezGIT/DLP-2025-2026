package ast;

import ast.expression.*;
import ast.statement.*;
import ast.type.ErrorType;

public class LValueVisitor extends AbstractVisitor<Void, Void> {

    @Override
    public Void visit(Variable variable, Void param) {
        variable.setLValue(true);
        return null;
    }

    @Override
    public Void visit(ArrayAccess arrayAccess, Void param) {
        super.visit(arrayAccess, param);
        arrayAccess.setLValue(true);
        return null;
    }

    @Override
    public Void visit(FieldAccess fieldAccess, Void param) {
        super.visit(fieldAccess, param);
        fieldAccess.setLValue(true);
        return null;
    }

    @Override
    public Void visit(Arithmetic arithmetic, Void param) {
        super.visit(arithmetic, param);
        arithmetic.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Comparison comparison, Void param) {
        super.visit(comparison, param);
        comparison.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Logical logical, Void param) {
        super.visit(logical, param);
        logical.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Cast cast, Void param) {
        super.visit(cast, param);
        cast.setLValue(false);
        return null;
    }

    @Override
    public Void visit(FunctionInvocation functionInvocation, Void param) {
        super.visit(functionInvocation, param);
        functionInvocation.setLValue(false);
        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Void param) {
        super.visit(unaryMinus, param);
        unaryMinus.setLValue(false);
        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Void param) {
        super.visit(unaryNot, param);
        unaryNot.setLValue(false);
        return null;
    }

    @Override
    public Void visit(IntLiteral intLiteral, Void param) {
        intLiteral.setLValue(false);
        return null;
    }

    @Override
    public Void visit(CharLiteral charLiteral, Void param) {
        charLiteral.setLValue(false);
        return null;
    }

    @Override
    public Void visit(NumberLiteral numberLiteral, Void param) {
        numberLiteral.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Assignment assignment, Void param) {
        super.visit(assignment, param);

        if (!assignment.getLeft().getLValue()) {
            new ErrorType("El lado izquierdo de la asignación no es una dirección de memoria válida (L-Value).",
                    assignment.getLeft());
        }
        return null;
    }

    @Override
    public Void visit(Input input, Void param) {
        super.visit(input, param);

        for (Expression e : input.getExpressions()) {
            if (!e.getLValue()) {
                new ErrorType("La expresión del input debe ser una dirección de memoria válida (L-Value).",
                        e);
            }
        }
        return null;
    }
}