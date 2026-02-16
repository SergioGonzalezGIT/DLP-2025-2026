package ast.statement;

import ast.expression.Expression;
import java.util.List;

public class While implements Statement {

    private int line;
    private int column;
    private Expression condition;
    private List<Statement> body;

    public While(int line, int column, Expression condition, List<Statement> body) {
        this.line = line;
        this.column = column;
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getBody() {
        return body;
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
        return "while (" + condition + ") ...";
    }
}