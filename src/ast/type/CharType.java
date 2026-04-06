package ast.type;

import ast.Locatable;
import ast.Visitor;

public class CharType extends  AbstractType{

    private static CharType instance = new CharType();

    private CharType() {}

    public static CharType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "char";
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
        if (other instanceof CharType || other instanceof ErrorType) {
            return;
        }
        new ErrorType("El tipo " + this.toString() + " no es compatible con " + other.toString(), locatable);
    }

    @Override
    public Type arithmetic(Type other, Locatable locatable) {
        if (other instanceof CharType) {
            return this;
        }
        if (other instanceof ErrorType) {
            return other;
        }
        return super.arithmetic(other, locatable);
    }

    @Override
    public Type comparison(Type other, Locatable locatable) {
        if (other instanceof CharType) {
            return this;
        }
        if (other instanceof ErrorType) return other;

        return super.comparison(other, locatable);
    }

    @Override
    public Type logical(Type other, Locatable locatable) {
        if (other instanceof CharType) {
            return this;
        }
        if (other instanceof ErrorType) return other;

        return super.logical(other, locatable);
    }

    @Override
    public Type unaryMinus(Locatable locatable) {
        return this;
    }

    @Override
    public Type unaryNot(Locatable locatable) {
        return this;
    }
}
