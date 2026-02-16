package ast.definition;

import ast.statement.Statement;
import ast.type.Type;
import java.util.List;

public class FunctionDefinition implements Definition {

    private int line;
    private int column;
    private String name;

    private List<VarDefinition> parameters;
    private List<VarDefinition> localVariables;
    private List<Statement> statements;

    public FunctionDefinition(int line, int column, String name, List<VarDefinition> parameters, List<VarDefinition> localVariables, List<Statement> statements) {
        this.line = line;
        this.column = column;
        this.name = name;
        this.parameters = parameters;
        this.localVariables = localVariables;
        this.statements = statements;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return null;
    }

    public List<VarDefinition> getParameters() {
        return parameters;
    }

    public List<VarDefinition> getLocalVariables() {
        return localVariables;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "function " + name + " (" + parameters + ") { ... }";
    }
}