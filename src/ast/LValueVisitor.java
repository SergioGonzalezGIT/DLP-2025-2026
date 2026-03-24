package ast;

import ast.definition.Definition;
import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

public class LValueVisitor implements Visitor<Void,Void> {

    // 1. EXPRESIONES QUE SÍ SON L-VALUE (true)
    @Override
    public Void visit(Variable variable, Void param) {
        variable.setLValue(true);
        return null;
    }

    @Override
    public Void visit(ArrayAccess arrayAccess, Void param) {
        arrayAccess.getLeft().accept(this, param); // Visita el array
        arrayAccess.getIndex().accept(this, param); // Visita el índice
        arrayAccess.setLValue(true);
        return null;
    }

    @Override
    public Void visit(FieldAccess fieldAccess, Void param) {
        fieldAccess.getExpression().accept(this, param); // Visita el struct
        fieldAccess.setLValue(true);
        return null;
    }


    // 2. EXPRESIONES QUE NO SON L-VALUE (false)
    @Override
    public Void visit(Arithmetic arithmetic, Void param) {
        arithmetic.getLeft().accept(this, param);
        arithmetic.getRight().accept(this, param);
        arithmetic.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Comparison comparison, Void param) {
        comparison.getLeft().accept(this, param);
        comparison.getRight().accept(this, param);
        comparison.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Logical logical, Void param) {
        logical.getLeft().accept(this, param);
        logical.getRight().accept(this, param);
        logical.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Cast cast, Void param) {
        cast.getExpression().accept(this, param);
        cast.setLValue(false);
        return null;
    }

    @Override
    public Void visit(FunctionInvocation functionInvocation, Void param) {
        // Una llamada a función (que devuelve algo) no es una dirección de memoria
        for (Expression arg : functionInvocation.getArguments()) {
            arg.accept(this, param);
        }
        functionInvocation.setLValue(false);
        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Void param) {
        unaryMinus.getExpression().accept(this, param);
        unaryMinus.setLValue(false);
        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Void param) {
        unaryNot.getExpression().accept(this, param);
        unaryNot.setLValue(false);
        return null;
    }

    // --- Literales (Nunca son L-Values) ---
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


    // 3. SENTENCIAS (Validadores y Propagadores)
    @Override
    public Void visit(Assignment assignment, Void param) {
        assignment.getLeft().accept(this, param);
        assignment.getRight().accept(this, param);

        if (!assignment.getLeft().getLValue()) {
            System.err.println("Error LValue [Línea " + assignment.getLine() + "]: El lado izquierdo de la asignación no es una dirección de memoria válida.");
        }
        return null;
    }

    @Override
    public Void visit(Input input, Void param) {
        //No puedes ser 'input 5;', tiene que ser una variable 'input a;'
        for (Expression e : input.getExpressions()) {
            e.accept(this, param);
            if (!e.getLValue()) {
                System.err.println("Error LValue [Línea " + input.getLine() + "]: La expresión del input debe ser una dirección de memoria válida.");
            }
        }
        return null;
    }

    @Override
    public Void visit(Log log, Void param) {
        for (Expression e : log.getExpressions()) {
            e.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(IfElse ifElse, Void param) {
        ifElse.getCondition().accept(this, param);
        for (Statement s : ifElse.getIfBody()) {
            s.accept(this, param);
        }
        for (Statement s : ifElse.getElseBody()) {
            s.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(While whileClass, Void param) {
        whileClass.getCondition().accept(this, param);
        for (Statement s : whileClass.getBody()) {
            s.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(Return returnClass, Void param) {
        if (returnClass.getExpression() != null) {
            returnClass.getExpression().accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(Invocation invocation, Void param) {
        for (Expression arg : invocation.getArguments()) {
            arg.accept(this, param);
        }
        return null;
    }


    // 4. NODOS ESTRUCTURALES Y TIPOS
    @Override
    public Void visit(Program program, Void param) {
        for (Definition def : program.getDefinitions()) {
            def.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition functionDefinition, Void param) {
        // Visitamos el cuerpo de la función (statements y var definitions)
        for (Statement s : functionDefinition.getStatements()) {
            s.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(VarDefinition varDefinition, Void param) {
        // Las definiciones no alteran el LValue, pero las visitamos por si acaso
        return null;
    }

    @Override public Void visit(ArrayType arrayType, Void param) { return null; }
    @Override public Void visit(CharType charType, Void param) { return null; }
    @Override public Void visit(ErrorType errorType, Void param) { return null; }
    @Override public Void visit(FunctionType functionType, Void param) { return null; }
    @Override public Void visit(IntType intType, Void param) { return null; }
    @Override public Void visit(NumberType numberType, Void param) { return null; }
    @Override public Void visit(RecordField recordField, Void param) { return null; }
    @Override public Void visit(RecordType recordType, Void param) { return null; }
    @Override public Void visit(VoidType voidType, Void param) { return null; }

}
