package ast.type;

import ast.Locatable;
import ast.Visitor;

import java.util.List;

public class ErrorType implements Type {

    private String message;
    private int linea;
    private int columna;

    public ErrorType(String message, Locatable locatable) {
        this.message = message;
        this.linea = locatable.getLine();
        this.columna = locatable.getColumn();
        ErrorHandler.getInstance().addError(this);
    }

    @Override
    public String toString() {
        return "ErrorType{" +
                "message='" + message + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    @Override
    public void mustBeLogical(Locatable locatable) {

    }

    @Override
    public void mustBeBuiltIn(Locatable locatable) {

    }

    @Override
    public void mustPromoteTo(Type other, Locatable locatable) {

    }

    @Override
    public Type canBeCastTo(Type type, Locatable locatable) {
        return this;
    }

    @Override
    public Type arithmetic(Type other, Locatable locatable) {
        return this;
    }

    @Override
    public Type comparison(Type other, Locatable locatable) {
        return this;
    }

    @Override
    public Type logical(Type other, Locatable locatable) {
        return this;
    }

    @Override
    public Type unaryMinus(Locatable locatable) {
        return this;
    }

    @Override
    public Type unaryNot(Locatable locatable) {
        return this;
    }

    @Override
    public Type squareBrackets(Type indexType, Locatable locatable) {
        return this;
    }

    @Override
    public Type dot(String field, Locatable locatable) {
        return this;
    }

    @Override
    public Type parenthesis(List<Type> args, Locatable locatable) {
        return this;
    }

    @Override
    public int numberOfBytes() {
        throw new UnsupportedOperationException();
    }
}
