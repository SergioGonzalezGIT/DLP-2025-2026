package ast.statement;

import ast.Visitor;
import ast.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public class IfElse implements Statement {

    private int line;
    private int column;
    private Expression condition;
    private List<Statement> ifBody;
    private List<Statement> elseBody;

    public IfElse(int line, int column, Expression condition, List<Statement> ifBody, List<Statement> elseBody) {
        this.line = line;
        this.column = column;
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getIfBody() {
        return ifBody;
    }

    public List<Statement> getElseBody() {
        return elseBody;
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
        return "if (" + condition + ") ...";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}