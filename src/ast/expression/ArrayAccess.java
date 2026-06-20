package ast.expression;

import visitor.Visitor;

public class ArrayAccess extends AbstractExpression {

    private Expression left;
    private Expression index;

    public ArrayAccess(int line, int column, Expression left, Expression index) {
        super(line, column);
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
    public String toString() {
        return left + "[" + index + "]";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}