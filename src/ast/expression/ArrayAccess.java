package ast.expression;

public class ArrayAccess implements Expression {

    private int line;
    private int column;
    private Expression left;
    private Expression index;

    public ArrayAccess(int line, int column, Expression left, Expression index) {
        this.line = line;
        this.column = column;
        this.left = left;
        this.index = index;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getIndex() {
        return index;
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
        return left + "[" + index + "]";
    }
}