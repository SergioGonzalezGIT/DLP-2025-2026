package ast.statement;

import ast.expression.Expression;
import java.util.List;

public class Log implements Statement {

    private int line;
    private int column;
    private List<Expression> expressions;

    public Log(int line, int column, List<Expression> expressions) {
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

    @Override
    public String toString() {
        return "log " + expressions + ";";
    }
}