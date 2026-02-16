package ast.expression;

public class Variable implements Expression {

    private int line;
    private int column;
    private String name;

    public Variable(int line, int column, String name) {
        this.line = line;
        this.column = column;
        this.name = name;
    }

    public String getName() {
        return name;
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
        return name;
    }
}