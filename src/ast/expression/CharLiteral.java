package ast.expression;

public class CharLiteral implements Expression {

    private int line;
    private int column;
    private char value;

    public CharLiteral(int line, int column, char value) {
        this.line = line;
        this.column = column;
        this.value = value;
    }

    public char getValue() {
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
        return "'" + value + "'";
    }
}