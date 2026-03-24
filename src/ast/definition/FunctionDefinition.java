package ast.definition;

import ast.Visitor;
import ast.statement.Statement;
import ast.type.Type;
import java.util.ArrayList;
import java.util.List;

public class FunctionDefinition implements Definition {

    private int line;
    private int column;
    private String name;

    private Type type;
    private List<Statement> statements;

    private int scope;

    public FunctionDefinition(int line, int column, String name, Type type, List<Statement> statements) {
        this.line = line;
        this.column = column;
        this.name = name;
        this.type = type;
        this.statements = statements;
    }

    public void addStatement(Statement st) {
        this.statements.add(st);
    }

    @Override
    public String getName() { return name; }

    @Override
    public Type getType() { return type; }

    @Override
    public int getScope() {
        return scope;
    }

    @Override
    public void setScope(int scope) {
        this.scope=scope;
    }

    public List<Statement> getStatements() { return statements; }

    @Override
    public int getLine() { return line; }

    @Override
    public int getColumn() { return column; }

    @Override
    public String toString() {
        return "FunctionDefinition{" +
                "line=" + line +
                ", column=" + column +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", statements=" + statements +
                '}';
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}