package ast.expression;

public class NumberLiteral implements Expression {

    private int line;
    private int column;
    private double value;

    public NumberLiteral(int line, int column, double value) {
        this.line = line;
        this.column = column;
        this.value = value;
    }

    public double getValue() {
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