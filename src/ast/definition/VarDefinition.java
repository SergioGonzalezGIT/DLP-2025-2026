package ast.definition;

import ast.Visitor;
import ast.statement.Statement;
import ast.type.Type;

public class VarDefinition implements Definition, Statement {

    private int line;
    private int column;
    private String name;
    private Type type;

    private int scope;

    public VarDefinition(int line, int column, String name, Type type) {
        this.line = line;
        this.column = column;
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public int getScope() {
        return scope;
    }

    @Override
    public void setScope(int scope) {
        this.scope=scope;
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
        return "let " + name + " : " + type + ";";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}