package ast.expression;

public class IntLiteral implements Expression {

    private int line;
    private int column;
    private int value;

    public IntLiteral(int line, int column, int value) {
        this.line = line;
        this.column = column;
        this.value = value;
    }

    public int getValue() {
        return value;
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
        return String.valueOf(value);
    }
}