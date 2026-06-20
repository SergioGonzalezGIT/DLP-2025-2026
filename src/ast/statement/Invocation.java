package ast.statement;

import visitor.Visitor;
import ast.expression.Expression;
import ast.expression.literals.Variable;

import java.util.List;

public class Invocation extends AbstractStatement {

    private Variable variable;
    private List<Expression> arguments;

    public Invocation(int line, int column, Variable variable, List<Expression> arguments) {
        super(line, column);
        this.variable = variable;
        this.arguments = arguments;
    }

    public Variable getVariable() {
        return variable;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void addArgument(Expression arg) {
        this.arguments.add(arg);
    }

    @Override
    public String toString() {
        return variable + "(" + arguments + ");";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}