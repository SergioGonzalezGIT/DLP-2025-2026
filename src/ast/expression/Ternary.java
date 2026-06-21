package ast.expression;

import visitor.Visitor;

public class Ternary extends AbstractExpression {

    private Expression condition;
    private Expression trueExpr;
    private Expression falseExpr;

    public Ternary(int line, int column, Expression condition, Expression trueExpr, Expression falseExpr) {
        super(line, column);
        this.condition = condition;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }

    public Expression getCondition() { return condition; }
    public Expression getTrueExpr() { return trueExpr; }
    public Expression getFalseExpr() { return falseExpr; }

    @Override
    public String toString() {
        return condition + " ? " + trueExpr + " : " + falseExpr;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}