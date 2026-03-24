package ast;

import ast.definition.Definition;
import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;
import symboltable.SymbolTable;

public class IdentificationVisitor implements Visitor<Void,Void>{

    private SymbolTable st = new SymbolTable();

    @Override
    public Void visit(Program program, Void param) {
        for (Definition def : program.getDefinitions()) {
            def.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition functionDefinition, Void param) {
        if (!st.insert(functionDefinition)) {
            new ErrorType("Función ya definida: " + functionDefinition.getName(), functionDefinition);
        }

        st.set();

        functionDefinition.getType().accept(this, param);

        for (Statement s : functionDefinition.getStatements()) {
            s.accept(this, param);
        }

        st.reset();
        return null;
    }

    @Override
    public Void visit(VarDefinition varDefinition, Void param) {
        if (!st.insert(varDefinition)) {
            new ErrorType("Variable ya definida en este ámbito: " + varDefinition.getName(), varDefinition);
        }

        varDefinition.getType().accept(this, param);
        return null;
    }


    @Override
    public Void visit(Variable variable, Void param) {
        Definition def = st.find(variable.getName());

        if (def != null) {
            variable.setDefinition(def);
        } else {
            ErrorType error = new ErrorType("Variable no declarada: " + variable.getName(), variable);

            variable.setDefinition(new VarDefinition(variable.getLine(), variable.getColumn(), variable.getName(), error));
        }
        return null;
    }

    @Override
    public Void visit(Arithmetic arithmetic, Void param) {
        arithmetic.getLeft().accept(this, param);
        arithmetic.getRight().accept(this, param);
        return null;
    }

    @Override
    public Void visit(ArrayAccess arrayAccess, Void param) {
        arrayAccess.getLeft().accept(this, param);
        arrayAccess.getIndex().accept(this, param);
        return null;
    }

    @Override
    public Void visit(Cast cast, Void param) {
        cast.getExpression().accept(this, param);
        cast.getCastType().accept(this, param);
        return null;
    }

    @Override
    public Void visit(Comparison comparison, Void param) {
        comparison.getLeft().accept(this, param);
        comparison.getRight().accept(this, param);
        return null;
    }

    @Override
    public Void visit(FieldAccess fieldAccess, Void param) {
        fieldAccess.getExpression().accept(this, param);
        return null;
    }

    @Override
    public Void visit(FunctionInvocation functionInvocation, Void param) {
        functionInvocation.getVariable().accept(this, param);
        for (Expression arg : functionInvocation.getArguments()) {
            arg.accept(this, param);
        }
        return null;
    }

    @Override
    public Void visit(Logical logical, Void param) {
        logical.getLeft().accept(this, param);
        logical.getRight().accept(this, param);
        return null;
    }

    @Override
    public Void visit(UnaryMinus unaryMinus, Void param) {
        unaryMinus.getExpression().accept(this, param);
        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Void param) {
        unaryNot.getExpression().accept(this, param);
        return null;
    }

    @Override
    public Void visit(Assignment assignment, Void param) {
        assignment.getLeft().accept(this, param);
        assignment.getRight().accept(this, param);
        return null;
    }

    @Override
    public Void visit(IfElse ifElse, Void param) {
        ifElse.getCondition().accept(this, param);
        for (Statement s : ifElse.getIfBody()) s.accept(this, param);
        for (Statement s : ifElse.getElseBody()) s.accept(this, param);
        return null;
    }

    @Override
    public Void visit(Input input, Void param) {
        for (Expression e : input.getExpressions()) e.accept(this, param);
        return null;
    }

    @Override
    public Void visit(Invocation invocation, Void param) {
        invocation.getVariable().accept(this, param);
        for (Expression arg : invocation.getArguments()) arg.accept(this, param);
        return null;
    }

    @Override
    public Void visit(Log log, Void param) {
        for (Expression e : log.getExpressions()) e.accept(this, param);
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
    public Void visit(While whileClass, Void param) {
        whileClass.getCondition().accept(this, param);
        for (Statement s : whileClass.getBody()) s.accept(this, param);
        return null;
    }


    @Override public Void visit(CharLiteral charLiteral, Void param) { return null; }
    @Override public Void visit(IntLiteral intLiteral, Void param) { return null; }
    @Override public Void visit(NumberLiteral numberLiteral, Void param) { return null; }

    @Override public Void visit(ArrayType arrayType, Void param) {
        arrayType.getOf().accept(this, param);
        return null;
    }
    @Override public Void visit(FunctionType functionType, Void param) {
        for (VarDefinition p : functionType.getParameters()) p.accept(this, param);
        functionType.getReturnType().accept(this, param);
        return null;
    }
    @Override public Void visit(RecordType recordType, Void param) {
        for (RecordField f : recordType.getFields()) f.accept(this, param);
        return null;
    }
    @Override public Void visit(RecordField recordField, Void param) {
        recordField.getType().accept(this, param);
        return null;
    }

    @Override public Void visit(CharType charType, Void param) { return null; }
    @Override public Void visit(ErrorType errorType, Void param) { return null; }
    @Override public Void visit(IntType intType, Void param) { return null; }
    @Override public Void visit(NumberType numberType, Void param) { return null; }
    @Override public Void visit(VoidType voidType, Void param) { return null; }
}
