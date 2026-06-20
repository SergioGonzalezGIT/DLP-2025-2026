package ast.expression;

import ast.expression.literals.Variable;
import visitor.Visitor;

import java.util.List;

public class FunctionInvocation extends AbstractExpression {

    private Variable variable;
    private List<Expression> arguments;


    public FunctionInvocation(int line, int column, Variable variable, List<Expression> arguments) {
        super(line, column);
        this.variable = variable;
        this.arguments = arguments;
    }

    public void addArgument(Expression arg) {
        this.arguments.add(arg);
    }

    public Variable getVariable() {
        return variable;
    }

    public List<Expression> getArguments() {
        return arguments;
    }


    @Override
    public String toString() {
        return variable + "(" + arguments + ")";
    }


    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}