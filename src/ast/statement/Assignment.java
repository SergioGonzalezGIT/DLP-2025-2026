package ast.statement;

import ast.Visitor;
import ast.expression.Expression;

public class Assignment implements Statement {

    private int line;
    private int column;
    private Expression left;
    private Expression right;

    public Assignment(int line, int column, Expression left, Expression right) {
        this.line = line;
        this.column = column;
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
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
        return left + " = " + right + ";";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}