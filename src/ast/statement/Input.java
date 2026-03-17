package ast.statement;

import ast.Visitor;
import ast.expression.Expression;

import java.util.List;

public class Input implements Statement {

    private int line;
    private int column;
    private List<Expression> expressions;

    public Input(int line, int column, List<Expression> expressions) {
        this.line = line;
        this.column = column;
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public void addExpression(Expression exp) {
        this.expressions.add(exp);
    }

    @Override
    public String toString() {
        return "input " + expressions + ";";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}