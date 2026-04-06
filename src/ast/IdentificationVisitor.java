package ast;

import ast.definition.Definition;
import ast.definition.FunctionDefinition;
import ast.definition.VarDefinition;
import ast.expression.Variable;
import ast.statement.Statement;
import ast.type.ErrorType;
import symboltable.SymbolTable;

public class IdentificationVisitor extends AbstractVisitor<Void, Void> {

    private SymbolTable st = new SymbolTable();

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

}